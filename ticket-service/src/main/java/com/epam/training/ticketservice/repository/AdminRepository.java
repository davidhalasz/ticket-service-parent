package com.epam.training.ticketservice.repository;

import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;

public interface AdminRepository {

    AdminEntity findAdminByName(String name);

    void updatePriviliged(String name, boolean priviliged);
}
