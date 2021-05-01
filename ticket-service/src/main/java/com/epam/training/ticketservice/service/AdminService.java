package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminRepository adminRepository;
    private AdminEntity currentAdmin;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public AdminEntity checkAccount(String name, String password) {
        AdminEntity adminEntity = adminRepository.findAdmin(name);
        if (adminEntity.getPassword().matches(password)) {
            adminRepository.updatePriviliged(name, true);
            currentAdmin = adminRepository.findAdmin(name);
        }
        return adminEntity;
    }

    public boolean loggedAdmin() {
        if (currentAdmin != null) {
            return currentAdmin.isPriviliged();
        } else {
            return false;
        }
    }
}
