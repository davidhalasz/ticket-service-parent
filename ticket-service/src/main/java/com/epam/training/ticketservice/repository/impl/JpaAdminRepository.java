package com.epam.training.ticketservice.repository.impl;


import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.AdminRepository;
import com.epam.training.ticketservice.exceptions.AdminAccountNotExistsException;
import com.epam.training.ticketservice.repository.mapper.MapperInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JpaAdminRepository implements AdminRepository {

    private final AdminDao adminDao;
    private final MapperInterface mapper;

    @Override
    public Admin getAdminByName(String name)
            throws AdminAccountNotExistsException {

        Optional<AdminEntity> adminEntity = Optional.ofNullable(adminDao.findByName((name)));
        if (adminEntity.isPresent()) {
            return mapper.mapAdmin(adminEntity.get());
        } else {
            throw new AdminAccountNotExistsException("There is no such name");
        }
    }

    @Override
    public void updatePriviliged(String name, boolean priviliged) {

        AdminEntity adminEntity = adminDao.findByName(name);
        adminEntity.setPriviliged(priviliged);
        adminDao.save(adminEntity);
    }
}
