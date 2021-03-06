package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.AdminRepository;
import com.epam.training.ticketservice.exceptions.AdminAccountNotExistsException;
import com.epam.training.ticketservice.exceptions.AdminIsNotLoggedInException;
import com.epam.training.ticketservice.exceptions.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private Admin currentAdmin;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin checkAccount(String name, String password)
            throws AdminAccountNotExistsException, InvalidPasswordException {

        Admin admin = adminRepository.getAdminByName(name);
        if (admin.getPassword().matches(password)) {
            adminRepository.updatePrivileged(name, true);
            currentAdmin = adminRepository.getAdminByName(name);
        } else {
            throw new InvalidPasswordException("Login failed due to incorrect credentials");
        }
        return admin;
    }

    public boolean loggedAdmin() throws AdminAccountNotExistsException {
        if (currentAdmin != null) {
            currentAdmin = adminRepository.getAdminByName(currentAdmin.getName());
            return currentAdmin.isPriviliged();
        } else {
            return false;
        }
    }

    public void signOut(Admin loggedAdmin)
            throws AdminIsNotLoggedInException, AdminAccountNotExistsException {

        if (loggedAdmin != null) {
            try {
                Admin admin = adminRepository.getAdminByName(loggedAdmin.getName());
                adminRepository.updatePrivileged(admin.getName(), false);
            } catch (AdminAccountNotExistsException e) {
                throw new AdminAccountNotExistsException("Account not exists");
            }
        } else {
            throw new AdminIsNotLoggedInException("You are not logged in");
        }
    }
}
