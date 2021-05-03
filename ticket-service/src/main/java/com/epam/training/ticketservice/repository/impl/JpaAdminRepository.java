package com.epam.training.ticketservice.repository.impl;


import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JpaAdminRepository implements AdminRepository {

    private AdminDao adminDao;

    @Autowired
    public JpaAdminRepository(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Override
    public AdminEntity findAdminByName(String name) {
        return adminDao.findByName(name);
    }

    @Override
    public void updatePriviliged(String name, boolean privilige) {
        AdminEntity adminEntity = adminDao.findByName(name);
        adminEntity.setPriviliged(privilige);
        adminDao.save(adminEntity);
    }
}
