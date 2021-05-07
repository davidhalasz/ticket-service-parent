package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.exceptions.AdminAccountNotExistsException;
import com.epam.training.ticketservice.exceptions.InvalidPasswordException;
import com.epam.training.ticketservice.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class AdminCommandHandlerTest {

    @InjectMocks
    private AdminCommandHandler adminCommandHandler;
    @Mock
    private AdminService adminService;

    private final static String NAME = "admin";
    private final static String PASSWORD = "pasw";
    private final static String SUCCES_SIGN_IN = "Signed in with privileged account " + NAME;
    private final static String FAIL_SIGN_IN = null;
    private final static String LOGOUT = "Logged out";

    @Test
    void testAdminLoginIsSuccessfulWhenLogInWhitCorrectNameAndPassword()
            throws AdminAccountNotExistsException, InvalidPasswordException {
        // When
        String current = adminCommandHandler.adminLogIn(NAME, PASSWORD);

        // Then
        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(adminService, times(1)).checkAccount(usernameCaptor.capture(),
                passwordCaptor.capture());
        assertThat(usernameCaptor.getValue(), equalTo(NAME));
        assertThat(passwordCaptor.getValue(), equalTo(PASSWORD));
        assertThat(current, equalTo(SUCCES_SIGN_IN));
    }

    @Test
    void testAdminLoginReturnExceptionWhenLogInWhitInorrectName()
            throws AdminAccountNotExistsException, InvalidPasswordException {
        //When
        doThrow(AdminAccountNotExistsException.class)
                .when(adminService)
                .checkAccount(any(), any());
        String current = adminCommandHandler.adminLogIn(NAME, PASSWORD);

        //Then
        ArgumentCaptor<String> usernameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        verify(adminService, times(1)).checkAccount(usernameCaptor.capture(),
                passwordCaptor.capture());
        assertThat(usernameCaptor.getValue(), equalTo(NAME));
        assertThat(passwordCaptor.getValue(), equalTo(PASSWORD));
        assertThat(current, equalTo(FAIL_SIGN_IN));
    }

}