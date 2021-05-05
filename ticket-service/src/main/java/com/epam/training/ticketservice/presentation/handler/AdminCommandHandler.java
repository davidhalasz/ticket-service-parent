package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.repository.RepositoryException.AdminAccountNotExistsException;
import com.epam.training.ticketservice.repository.RepositoryException.InvalidPasswordException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Command handler for 'admin' command
 */

@ShellComponent
public class AdminCommandHandler {

    private AdminService adminService;

    public AdminCommandHandler(AdminService adminService) {
        this.adminService = adminService;
    }

    @ShellMethod(value = "Admin sign in", key = "sign in priviliged")
    public String adminLogIn(String name, String password) throws AdminAccountNotExistsException, InvalidPasswordException {
        String result;
        try {
            Admin Admin = adminService.checkAccount(name, password);
            result = "Signed in with privileged account " + name;
        } catch (AdminAccountNotExistsException | InvalidPasswordException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "Admin sign out", key = "sign out")
    public String adminLogout() throws AdminAccountNotExistsException {
        adminService.signOut();
        return "Logged out";
    }
}
