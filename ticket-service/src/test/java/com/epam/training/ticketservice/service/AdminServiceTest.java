package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.dao.AdminDao;
import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.AdminRepository;
import com.epam.training.ticketservice.exceptions.AdminAccountNotExistsException;
import com.epam.training.ticketservice.exceptions.AdminIsNotLoggedInException;
import com.epam.training.ticketservice.exceptions.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    private AdminService underTest;
    private final static String NAME = "admin";
    private final static String PASSWORD = "admin";
    private static final Admin ADMIN_FALSE = new Admin(NAME, PASSWORD, false);
    private static final Admin PRIVILEGED_ADMIN = new Admin(NAME, PASSWORD, true);
    private final static String INVALID_NAME = "Skywalker";
    private final static String INVALID_PSW = "psw";
    private final static Admin INVALID_ADMIN = new Admin(NAME, INVALID_PSW, false);
    private static final Admin NULL_ADMIN = null;
    private final static List<Admin> admins = List.of(PRIVILEGED_ADMIN, PRIVILEGED_ADMIN);
    private final static AdminEntity adminEntity = new AdminEntity(NAME, PASSWORD, true);

    @Mock
    private AdminRepository adminRepository;


    @InjectMocks
    private AdminService adminService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AdminService(adminRepository);
    }

    @Test
    public void testCheckAccountShouldReturnLoggedAdmin()
            throws AdminAccountNotExistsException, InvalidPasswordException {

        // Given
        given(adminRepository.getAdminByName(NAME)).willReturn(PRIVILEGED_ADMIN);

        // When
        Admin actualResult = underTest.checkAccount(NAME, PASSWORD);

        // Then
        assertThat(actualResult, equalTo(PRIVILEGED_ADMIN));
    }

    @Test
    public void testCheckAccountShouldReturnExceptionWhenLogInWithNonExistingAccount()
            throws AdminAccountNotExistsException {

        // Given
        doThrow(AdminAccountNotExistsException.class)
                .when(adminRepository)
                .getAdminByName(anyString());
        Exception exception = null;

        // When
        try {
            adminRepository.getAdminByName(INVALID_NAME);
        } catch (AdminAccountNotExistsException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testCheckAccountShouldReturnExceptionWhenPasswordIsInvalid()
            throws AdminAccountNotExistsException {

        // Given
        given(adminRepository.getAdminByName(anyString())).willReturn(INVALID_ADMIN);

        // Then
        assertThrows(InvalidPasswordException.class, () -> {
            // When
            underTest.checkAccount(NAME, PASSWORD);
        });
    }

    @Test
    void testSignOutShouldReturnExceptionWhenAdminIsNotLoggedIn()
            throws AdminIsNotLoggedInException, AdminAccountNotExistsException {

        Exception exception = null;

        // When
        try {
            adminService.signOut(NULL_ADMIN);
        } catch (AdminIsNotLoggedInException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testSignOutShouldReturnNullPointerExceptionWhenAdminIsNotExists()
            throws AdminIsNotLoggedInException, AdminAccountNotExistsException {

        Exception exception = null;

        // When
        try {
            adminService.signOut(INVALID_ADMIN);
        } catch (NullPointerException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);
    }

    @Test
    void testSignOutShouldBeSuccess() throws AdminAccountNotExistsException, AdminIsNotLoggedInException {
        // Given
        given(adminRepository.getAdminByName(NAME)).willReturn(PRIVILEGED_ADMIN);

        // When
        underTest.signOut(PRIVILEGED_ADMIN);

        // Then
        verify(adminRepository, times(1)).updatePrivileged(PRIVILEGED_ADMIN.getName(), false);

    }
    
}