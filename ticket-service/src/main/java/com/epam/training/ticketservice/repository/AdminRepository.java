package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.exceptions.AdminAccountNotExistsException;

public interface AdminRepository {

    Admin getAdminByName(String name) throws AdminAccountNotExistsException;

    void updatePrivileged(String name, boolean priviliged);
}
