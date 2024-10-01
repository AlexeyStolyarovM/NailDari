package org.example.naildari.controllers;

import org.example.naildari.models.Client;
import org.example.naildari.models.Services;
import org.example.naildari.services.ClientService;
import org.example.naildari.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {


    private final ServiceService serviceService;
    private final ClientService clientService;

    public HomeController(ServiceService serviceService, ClientService clientService) {
        this.serviceService = serviceService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Services> services = serviceService.getAllServices();
        model.addAttribute("title", "Добро пожаловать в Маникюрный Салон");
        model.addAttribute("services", services);
        return "home";
    }
}
