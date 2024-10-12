package org.example.naildari.controllers;

import org.example.naildari.services.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/services")
    public String service(Model model) {
        model.addAttribute("service", serviceService.getAllServices());
        return "services";
    }
}
