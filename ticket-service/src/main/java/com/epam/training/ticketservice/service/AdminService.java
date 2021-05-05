package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.AdminRepository;
import com.epam.training.ticketservice.repository.RepositoryException.AdminAccountNotExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidPasswordException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private AdminRepository adminRepository;
    private Admin currentAdmin;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin checkAccount(String name, String password) throws AdminAccountNotExistsException, InvalidPasswordException {
        Admin admin = adminRepository.findAdminByName(name);
        if (admin.getPassword().matches(password)) {
            adminRepository.updatePriviliged(name, true);
            currentAdmin = adminRepository.findAdminByName(name);
        } else {
            throw new InvalidPasswordException("Invalid password");
        }
        return admin;
    }

    public boolean loggedAdmin() {
        if (currentAdmin != null) {
            return currentAdmin.isPriviliged();
        } else {
            return false;
        }
    }

    public void signOut() throws AdminAccountNotExistsException{
        try {
            Admin admin = adminRepository.findAdminByName(currentAdmin.getName());
            adminRepository.updatePriviliged(admin.getName(), false);
            currentAdmin = null;
        } catch (NullPointerException e) {
            throw new NullPointerException("Something wrong");
        }

    }

}
