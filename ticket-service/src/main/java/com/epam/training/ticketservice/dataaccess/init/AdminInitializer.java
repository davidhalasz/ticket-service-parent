package com.epam.training.ticketservice.dataaccess.init;


import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdminInitializer {

    private AdminDao adminDao;

    public AdminInitializer(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @PostConstruct
    public void initDatabase() {
        AdminEntity adminEntity = new AdminEntity("admin", "admin", false);
        adminDao.save(adminEntity);
    }
}
