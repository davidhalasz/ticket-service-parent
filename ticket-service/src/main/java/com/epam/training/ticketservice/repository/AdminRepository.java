package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.domain.Admin;

public interface AdminRepository {

    Admin findAdminByName(String name);

    void updatePriviliged(String name, boolean priviliged);
}
