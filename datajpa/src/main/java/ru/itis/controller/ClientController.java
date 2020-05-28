package ru.itis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.model.Client;
import ru.itis.repository.ClientJpaRepository;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientJpaRepository repository;



    @RequestMapping("/client")
    public String clients(HttpServletRequest request, @ModelAttribute("model") ModelMap model) {

        String path = request.getContextPath();

        model.addAttribute("app_path", path);


        List<Client> clients = repository.findAll();

        model.addAttribute("clients", clients);

        return "/clients";
    }


    @RequestMapping("/addclient")
    public String clientByName(HttpServletRequest request,
                               @RequestParam(value = "id", required = false) Long id,
                               @RequestParam(value = "name", required = false) String name,
                               @RequestParam(value = "address", required = false) String address,
                               @RequestParam(value = "passport", required = false) String passport,
                               @ModelAttribute("model") ModelMap model)
    {

        String path = request.getContextPath();
        model.addAttribute("app_path", path);

        Client client = new Client();
        // Если получили непустой id - пытаемся найти
        if (id != null) {
            client = repository.findById(id).get();
        }


        if ( address != null || name != null || passport != null) {
            // Используем параметры формы
            assert client != null;
            client.setAddress(address);
            client.setName(name);
            client.setPassport(passport);

            repository.save(client);
        }



        model.addAttribute("client", client);


        return "/client";
    }




}
