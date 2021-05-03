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
        try {
            AdminEntity adminEntity = adminRepository.findAdminByName(name);
            if (adminEntity.getPassword().matches(password)) {
                adminRepository.updatePriviliged(name, true);
                currentAdmin = adminRepository.findAdminByName(name);
            }
            return adminEntity;
        } catch (NullPointerException e) {
            throw new NullPointerException("There is no such name.");
        }
    }

    public boolean loggedAdmin() {
        if (currentAdmin != null) {
            return currentAdmin.isPriviliged();
        } else {
            return false;
        }
    }

    public void signOut() {
        try {
            AdminEntity adminEntity = adminRepository.findAdminByName(currentAdmin.getName());
            adminRepository.updatePriviliged(adminEntity.getName(), false);
            currentAdmin = adminRepository.findAdminByName(adminEntity.getName());
        } catch (NullPointerException e) {
            throw new NullPointerException("Something wrong");
        }

    }
}
