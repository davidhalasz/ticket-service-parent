package com.epam.training.ticketservice.repository.impl;


import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.AdminRepository;
import com.epam.training.ticketservice.service.ServiceException.AdminAccountNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaAdminRepository implements AdminRepository {

    private AdminDao adminDao;

    @Autowired
    public JpaAdminRepository(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public Admin findAdminByName(String name) throws AdminAccountNotExistsException {
        Optional<AdminEntity> adminEntity = Optional.ofNullable(adminDao.findByName((name)));
        if (!adminEntity.isEmpty()) {
            return mapAdmin(adminEntity.get());
        } else {
            throw new AdminAccountNotExistsException("There is no such name");
        }

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
