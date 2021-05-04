package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.dataaccess.entity.AdminEntity;
import com.epam.training.ticketservice.domain.Admin;
import com.epam.training.ticketservice.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;



class AdminServiceTest {
    private AdminService underTest;
    private final static String NAME = "admin";
    private final static String PASSWORD = "admin";
    private static final Admin ADMIN = new Admin(NAME, PASSWORD, false);
    private static final Admin PRIVILIGED_ADMIN = new Admin(NAME, PASSWORD, true);
    private final static String INVALID_NAME = "Skywalker";

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminEntity adminEntity;

    @Mock
    private AdminService adminService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AdminService(adminRepository);
    }

    @Test
    public void testCheckAccountShouldReturnLoggedAdmin() {
        // Given
        given(adminRepository.findAdminByName(NAME)).willReturn(PRIVILIGED_ADMIN);

        // When
        Admin result = underTest.checkAccount(NAME, PASSWORD);

        // Then
        assertThat(result, equalTo(PRIVILIGED_ADMIN));
    }

    @Test
    public void testCheckAccountShouldReturnExceptionWhenLogInWithNonExistingAccount() {
        // Given
        given(adminRepository.findAdminByName(NAME)).willThrow(NullPointerException.class);

        // Then
        assertThrows(NullPointerException.class, () -> {
            // When
            underTest.checkAccount(INVALID_NAME, PASSWORD);
        });
    }
}