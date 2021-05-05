package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.RepositoryException.AdminAccountNotExistsException;

public interface AdminRepository {

    Admin findAdminByName(String name) throws AdminAccountNotExistsException;

    void updatePriviliged(String name, boolean priviliged);
}
