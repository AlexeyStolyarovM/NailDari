package org.example.naildari.controllers;

import lombok.RequiredArgsConstructor;
import org.example.naildari.models.Appointment;
import org.example.naildari.models.Client;
import org.example.naildari.models.Services;
import org.example.naildari.services.AppointmentService;
import org.example.naildari.services.ClientService;
import org.example.naildari.services.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final ClientService clientService;

    private final ServiceService serviceService;


    @GetMapping("/appointments/new")
    public String showAppointmentForm(Model model) {
        List<Services> services = serviceService.getAllServices();
        List<Client> clients = clientService.getAllClients(); // Получаем всех клиентов

        model.addAttribute("services", services);
        model.addAttribute("clients", clients);

        return "appointment-form";
    }

    @PostMapping("/appointments/new")
    public String createAppointment(
            @RequestParam("clientId") Long clientId,
            @RequestParam("serviceId") Long serviceId,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            RedirectAttributes redirectAttributes) {

        LocalDate appointmentDate = LocalDate.parse(date);
        LocalDate today = LocalDate.now();
        LocalTime appointmentTime = LocalTime.parse(time);
        LocalTime minTime = LocalTime.of(10, 0);
        LocalTime maxTime = LocalTime.of(22, 0);

        // Проверка на прошедшие даты и время
        if (appointmentDate.isBefore(today) ||
                appointmentDate.getMonth() != today.getMonth() ||
                appointmentDate.getYear() != today.getYear() ||
                (appointmentDate.isEqual(today) && appointmentTime.isBefore(minTime)) ||
                appointmentTime.isAfter(maxTime)) {

            redirectAttributes.addFlashAttribute("error", "Дата должна быть в текущем месяце, не может быть прошедшей, и время должно быть между 10:00 и 22:00.");
            return "redirect:/appointments/new"; // Вернуться к форме
        }
        Client client = clientService.getClientById(clientId);
        Services service = serviceService.getServiceById(serviceId).orElseThrow();

        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setService(service);
        appointment.setDate(LocalDate.parse(date));
        appointment.setTime(LocalTime.parse(time));

        appointmentService.saveAppointment(appointment);

        redirectAttributes.addFlashAttribute("message", "Запись успешно создана!");
        return "redirect:/";
    }

    @GetMapping("/appointments/client")
    public String viewClientAppointments(@RequestParam Long clientId, Model model, RedirectAttributes redirectAttributes) {
        Client client = clientService.getClientById(clientId);
        List<Appointment> appointments = appointmentService.getAppointmentsByClient(clientId);
        model.addAttribute("client", client);
        model.addAttribute("appointments", appointments);
        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Клиент не найден."); // Сообщение об ошибке
            return "home";
        }
        return "client_appointments";  // Страница с отображением записей
    }

    @PostMapping("/appointments/delete/{appointmentId}")
    public String deleteAppointment(@PathVariable Long appointmentId, RedirectAttributes redirectAttributes) {
        appointmentService.deleteAppointment(appointmentId);
        redirectAttributes.addFlashAttribute("message", "Запись успешна удалена!");
        return "redirect:/appointments/client?clientId=" + appointmentId;
    }

}

