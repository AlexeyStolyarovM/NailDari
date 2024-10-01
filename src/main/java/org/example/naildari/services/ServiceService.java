package org.example.naildari.services;

import org.example.naildari.models.Services;
import org.example.naildari.repositories.ServiceRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }
    public List<Services> getAllServices() {
        return serviceRepository.findAll();
    }
    public Optional<Services> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }
}
