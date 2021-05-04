package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminRepository adminRepository;
    private Admin currentAdmin;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin checkAccount(String name, String password) {
        try {
            Admin admin = adminRepository.findAdminByName(name);
            if (admin.getPassword().matches(password)) {
                adminRepository.updatePriviliged(name, true);
                currentAdmin = adminRepository.findAdminByName(name);
            }
            return admin;
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
            Admin admin = adminRepository.findAdminByName(currentAdmin.getName());
            adminRepository.updatePriviliged(admin.getName(), false);
            currentAdmin = adminRepository.findAdminByName(admin.getName());
        } catch (NullPointerException e) {
            throw new NullPointerException("Something wrong");
        }

    }

}
