package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.AdminRepository;
import com.epam.training.ticketservice.repository.RepositoryException.AdminAccountNotExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.AdminIsNotLoggedInException;
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
        Admin admin = adminRepository.getAdminByName(name);
        if (admin.getPassword().matches(password)) {
            adminRepository.updatePriviliged(name, true);
            currentAdmin = adminRepository.getAdminByName(name);
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

    public void signOut(Admin loggedAdmin) throws AdminIsNotLoggedInException, AdminAccountNotExistsException{
        if(loggedAdmin != null) {
            try {
                Admin admin = adminRepository.getAdminByName(loggedAdmin.getName());
                adminRepository.updatePriviliged(admin.getName(), false);
            } catch (AdminAccountNotExistsException e) {
                throw new AdminAccountNotExistsException("Account not exists");
            }
        } else {
            throw new AdminIsNotLoggedInException("You are not logged in");
        }
    }
}
