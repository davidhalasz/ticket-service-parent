package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.service.AdminService;
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
    public String adminLogIn(String name, String password) {
        Admin Admin = adminService.checkAccount(name, password);
        return "Signed in with privileged account " + name;
    }

    @ShellMethod(value = "Admin sign out", key = "sign out")
    public String adminLogout() {
        adminService.signOut();
        return "Logged out";
    }
}
