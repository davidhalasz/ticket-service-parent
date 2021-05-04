package com.epam.training.ticketservice.repository.impl;


import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.dataaccess.entity.MovieEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.domain.Movie;
import com.epam.training.ticketservice.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaAdminRepository implements AdminRepository {

    private AdminDao adminDao;

    @Autowired
    public JpaAdminRepository(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Admin findAdminByName(String name) {
        Optional<AdminEntity> adminEntity = Optional.ofNullable(adminDao.findByName((name)));
        return mapAdmin(adminEntity.get());
    }

    private Admin mapAdmin(AdminEntity adminEntity) {
        return new Admin(adminEntity.getName(),
                adminEntity.getPassword(),
                adminEntity.isPriviliged());
    }


    @Override
    public void updatePriviliged(String name, boolean priviliged) {
        AdminEntity adminEntity = adminDao.findByName(name);
        adminEntity.setPriviliged(priviliged);
        adminDao.save(adminEntity);
    }

}
