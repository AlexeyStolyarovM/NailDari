package org.example.naildari.controllers;

import org.example.naildari.models.Client;
import org.example.naildari.models.Services;
import org.example.naildari.services.ClientService;
import org.example.naildari.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final ServiceService serviceService;

    public ClientController(ClientService clientService, ServiceService serviceService) {
        this.clientService = clientService;
        this.serviceService = serviceService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Services> services = serviceService.getAllServices();
        List<Client> clients = clientService.getAllClients();

        model.addAttribute("title", "Маникюрный Салон");
//        model.addAttribute("services", services); // Список услуг
        model.addAttribute("clients", clients);   // Список клиентов

        return "home"; // имя шаблона: home.html
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  // Отображаем страницу регистрации
    }

    @PostMapping("/register")
    public String registerClient(@ModelAttribute Client client, RedirectAttributes redirectAttributes) {
        clientService.addClient(client);
        redirectAttributes.addFlashAttribute("message", "Регистрация прошла успешно! Ваш ID клиента: " + client.getId());
        return "redirect:/";
    }

    @GetMapping("/update/{clientId}")
    public String showUpdateForm(@PathVariable Long clientId, Model model) {
        Client client = clientService.getClientById(clientId);
        model.addAttribute("client", client);
        return "update";  // Возвращает имя шаблона для отображения
    }

    @PostMapping("/update")
    public String updateClient(@RequestParam("clientId") Long id, @ModelAttribute("client") Client clientUpdate, RedirectAttributes redirectAttributes) {
        Client client = clientService.getClientById(id);
        client.setFirstName(clientUpdate.getFirstName());
        client.setName(clientUpdate.getName());
        client.setPhone(clientUpdate.getPhone());
        clientService.updateClient(client);
        redirectAttributes.addFlashAttribute("message", "Данные клиента успешно обновлены!");
        return "redirect:/";
    }

}
