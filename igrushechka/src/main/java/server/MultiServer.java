package server;


import context.ApplicationContext;
import context.ApplicationContextReflectionBased;
import dto.AuthorizationDto;
import model.User;
import model.Game;
import repository.UserRepository;
import service.SignInService;
import service.SignUpService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiServer {

    private List<ClientHandler> clients;
    private ArrayList<Game> games = new ArrayList<>();
    private ApplicationContext context = new ApplicationContextReflectionBased();

    public MultiServer() {
        clients = new CopyOnWriteArrayList<>();
    }

    public void start(int port) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        while (true) {
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class ClientHandler extends Thread {
        SignInService signInService = context.getComponent(SignInService.class);
        UserRepository userRepository = context.getComponent(UserRepository.class);
        SignUpService signUpService = context.getComponent(SignUpService.class);

        private Socket clientSocket;
        private BufferedReader in;
        private boolean inGame = false;
        private PrintWriter out;
        private Game game;

        public void setRequested(boolean requested) {
            this.requested = requested;
        }

        private boolean requested = false;

        public User getUser() {
            return user;
        }

        private User user;

        ClientHandler(Socket socket) {
            this.clientSocket = socket;
            clients.add(this);
            System.out.println("New client");
        }

        public void run() {
            try {
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.equals("select")) {
                        out.println(getActiveClients());
                    } else if (inputLine.startsWith("user")) {
                        out.println(getUser(inputLine));
                    } else if (inputLine.startsWith("new")) {
                        saveUser(inputLine);
                    } else if (inputLine.startsWith("start")) {
                        gameStarter(inputLine);
                    } else if (inputLine.equals("should i start?")) {
                        startOrNot();
                    } else if (inputLine.startsWith("y")) {
                        sendCoordinates(inputLine);
                    }
                }
                in.close();
                clientSocket.close();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        private String getActiveClients() {
            StringBuilder activeClients = new StringBuilder("select:");
            for (ClientHandler client : clients) {
                if (!client.inGame && client.getUser() != null) {
                    activeClients.append(client.getUser().getLogin() + ":");
                }
            }
            return String.valueOf(activeClients);
        }
        private void saveUser(String inputLine) {
            String[] userInfo = inputLine.split(":");
            signUpService.signUp(userInfo[1], userInfo[2]);
        }
        private String getUser(String inputLine) {
            String[] userInfo = inputLine.split(":");
            AuthorizationDto user = signInService.signIn(userInfo[1], userInfo[2]);
            this.user = userRepository.findByLogin(userInfo[1]);
            if (user == null) {
                return "error";
            }
            return "success";
        }

        private void gameStarter(String inputLine) {
            String login = inputLine.split(":")[1];
            inGame = true;
            User p2 = userRepository.findByLogin(login);
            for (ClientHandler client : clients) {
                if (client.user.getLogin().equals(login) & !client.inGame) {
                    client.setRequested(true);
                }
            }
            game = new Game(user, p2);
            games.add(game);
        }

        private void startOrNot() {
            if (!requested) {
                out.println("no");
            } else {
                for (Game game : games) {
                    if (game.getP2().equals(user)) {
                        this.game = game;
                    }
                }
                inGame = true;
                out.println("let it snow");
            }
        }

        private void sendCoordinates(String inputLine) {
            if (user.equals(game.getP1())) {
                game.setP1Y(Integer.parseInt(inputLine.split(":")[1].substring(0, inputLine.length() - 4)));
                out.println("x:" + game.getP2Y());
            } else {
                game.setP2Y(Integer.parseInt(inputLine.split(":")[1].substring(0, inputLine.length() - 4)));
                out.println("x:" + game.getP1Y());
            }
        }
    }
}
