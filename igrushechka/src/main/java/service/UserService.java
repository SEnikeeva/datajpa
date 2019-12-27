package service;

import context.Component;

public interface UserService extends Component {
    boolean checkUser(String login);
    public boolean checkPassword(String login, String password);
}
