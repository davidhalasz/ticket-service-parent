package com.epam.training.ticketservice.repository.impl;

import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.RepositoryException.AdminAccountNotExistsException;
import com.epam.training.ticketservice.repository.mapper.ScreeningMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.attribute.standard.MediaSize;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaAdminRepositoryTest {

    @InjectMocks
    JpaAdminRepository adminRepository;

    @Mock
    AdminDao adminDao;

    @Mock
    ScreeningMapper mapper;

    private static final String NAME = "admin";
    private static final boolean PRIVILIGED = false;
    private static final String PASSWORD = "pswd";
    private static final Admin ADMIN = new Admin(NAME, PASSWORD, false);
    private static final AdminEntity ADMIN_ENTITY = new AdminEntity(NAME, PASSWORD, false);


    private Admin mapAdmin(AdminEntity adminEntity) {
        return new Admin(adminEntity.getName(),
                adminEntity.getPassword(),
                adminEntity.isPriviliged());
    }

    @Test
    public void testGetAdminByNameShouldReturnsExceptionWhenAccountNotFound()
            throws AdminAccountNotExistsException {
        // Given
        given(adminDao.findByName(any())).willReturn(null);

        // Then
        assertThrows(AdminAccountNotExistsException.class, () -> {
            // When
            adminRepository.getAdminByName(NAME);
        });
    }

}