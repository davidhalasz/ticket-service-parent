package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.exceptions.AdminIsNotLoggedInException;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.exceptions.AdminAccountNotExistsException;
import com.epam.training.ticketservice.exceptions.InvalidPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@Slf4j
public class AdminCommandHandler {

    private final AdminService adminService;
    private Admin loggedAdmin = null;

    public AdminCommandHandler(AdminService adminService) {
        this.adminService = adminService;
    }

    @ShellMethod(value = "Admin sign in", key = "sign in priviliged")
    public String adminLogIn(String name, String password)
            throws AdminAccountNotExistsException, InvalidPasswordException {

        String result;
        try {
            loggedAdmin = adminService.checkAccount(name, password);
            result = "Signed in with privileged account " + name;
        } catch (AdminAccountNotExistsException | InvalidPasswordException e) {
            result = e.getMessage();
        }
        return result;
    }

    @ShellMethod(value = "Admin sign out", key = "sign out")
    public String adminLogout()
            throws AdminIsNotLoggedInException, AdminAccountNotExistsException {

        try {
            adminService.signOut(loggedAdmin);
            return "Logged out";
        } catch (AdminIsNotLoggedInException | AdminAccountNotExistsException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Describe account", key = "describe account")
    public String describeAccount() {
        String result = "";
        if (adminService.loggedAdmin()) {
            result = "Signed in with privileged account " + loggedAdmin.getName();
        } else {
            result = "You are not signed in";
        }
        return result;
    }
}
