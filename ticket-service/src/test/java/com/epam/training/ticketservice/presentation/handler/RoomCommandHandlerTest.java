package com.epam.training.ticketservice.presentation.handler;

import com.epam.training.ticketservice.domain.Room;
import com.epam.training.ticketservice.exceptions.*;
import com.epam.training.ticketservice.service.AdminService;
import com.epam.training.ticketservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RoomCommandHandlerTest {

    @InjectMocks
    private RoomCommandHandler roomCommandHandler;
    @Mock
    private RoomService roomService;
    @Mock
    private AdminService adminService;


    private final static String UNPRIVILIGED_MSG = "You are not signed in";
    private final static String ROOM_ADDED = "Room added";
    private final static String ROOM_UPDATED = "Room updated";
    private final static String ROOM_DELETED = "Room deleted";
    private final static String INVALID_PARAMETER = "Room's rows and columns cannot be null";
    private final static String INVALID_RUNTIME_MSG = "Runtime cannot be null";
    private final static String ROOM_NOT_FOUND = "Room not found";
    private final static String NAME = "room name";
    private final static int ROWS = 20;
    private final static int COLUMNS = 20;
    private final static String ROOM_EXISTS_MSG = "Room already exists";
    private final static String EMPTY_LIST_MSG = "There are no rooms at the moment";


    private static final RoomAlreadyExistsException ROOM_ALREADY_EXISTS_EXCEPTION
            = new RoomAlreadyExistsException(ROOM_EXISTS_MSG);
    private static final RoomNotFoundException ROOM_NOT_FOUND_EXCEPTION
            = new RoomNotFoundException(ROOM_NOT_FOUND);
    private static final InvalidRoomParameterException INVALID_ROOM_PARAMETER_EXCEPTION
            = new InvalidRoomParameterException(INVALID_PARAMETER);
    private final static Room room = new Room(NAME, ROWS, COLUMNS);

    private final static List<Room> rooms = List.of(room, room);



    @Test
    void testCreateRoomWhenAdminIsSignedIn()
            throws RoomAlreadyExistsException, InvalidRoomParameterException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);

        // When
        String actualResult = roomCommandHandler.createRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(roomService, times(1)).createRoom(NAME, ROWS, COLUMNS);
        assertThat(actualResult, equalTo(ROOM_ADDED));
    }

    @Test
    void testCreateRoomReturnErrorWhenAdminIsNotSignedIn()
            throws RoomAlreadyExistsException, InvalidRoomParameterException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(false);

        // When
        String actualResult = roomCommandHandler.createRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(actualResult, equalTo(UNPRIVILIGED_MSG));
    }

    @Test
    void testCreateRoomShouldReturnExceptionWhenRoomAlreadyExists()
            throws RoomAlreadyExistsException, InvalidRoomParameterException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(ROOM_ALREADY_EXISTS_EXCEPTION)
                .when(roomService)
                .createRoom(anyString(), anyInt(), anyInt());

        // When
        String actualResult = roomCommandHandler.createRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(roomService, times(1)).createRoom(NAME, ROWS, COLUMNS);
        assertThat(actualResult, equalTo(ROOM_EXISTS_MSG));
    }

    @Test
    void testCreateRoomShouldReturnExceptionWhenRoomColumnsOrRowsIsInvalid()
            throws RoomAlreadyExistsException, InvalidRoomParameterException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(INVALID_ROOM_PARAMETER_EXCEPTION)
                .when(roomService)
                .createRoom(anyString(), anyInt(), anyInt());

        // When
        String actualResult = roomCommandHandler.createRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(roomService, times(1)).createRoom(NAME, ROWS, COLUMNS);
        assertThat(actualResult, equalTo(INVALID_PARAMETER));
    }

    @Test
    void testListRoomsShouldReturnEmptyRoomListMessage() {
        // Given
        given(roomService.getAllRooms()).willReturn(List.of());

        // When
        String actualResult = roomCommandHandler.listRooms();

        // Then
        verify(roomService, times(1)).getAllRooms();
        assertThat(actualResult, equalTo(EMPTY_LIST_MSG));
    }

    @Test
    void testUpdateRoomWhenAdminIsSignedIn()
            throws RoomNotFoundException, InvalidRoomParameterException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);

        // When
        String actualResult = roomCommandHandler.updateRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(roomService, times(1)).updateRoom(NAME, ROWS, COLUMNS);
        assertThat(actualResult, equalTo(ROOM_UPDATED));
    }

    @Test
    void testUpdateRoomReturnErrorWhenAdminIsNotSignedIn()
            throws RoomNotFoundException, InvalidRoomParameterException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(false);

        // When
        String actualResult = roomCommandHandler.updateRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(actualResult, equalTo(UNPRIVILIGED_MSG));
    }

    @Test
    void testUpdateRoomShouldReturnExceptionWhenRoomIsNotExist()
            throws RoomNotFoundException, InvalidRoomParameterException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(ROOM_NOT_FOUND_EXCEPTION)
                .when(roomService)
                .updateRoom(anyString(), anyInt(), anyInt());

        // When
        String actualResult = roomCommandHandler.updateRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(roomService, times(1)).updateRoom(NAME, ROWS, COLUMNS);
        assertThat(actualResult, equalTo(ROOM_NOT_FOUND));
    }

    @Test
    void testUpdateRoomShouldReturnExceptionWhenRowsOrColumnsIsInvalid()
            throws InvalidRoomParameterException, RoomNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        doThrow(INVALID_ROOM_PARAMETER_EXCEPTION)
                .when(roomService)
                .updateRoom(anyString(), anyInt(), anyInt());

        // When
        String actualResult = roomCommandHandler.updateRoom(NAME, ROWS, COLUMNS);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(roomService, times(1)).updateRoom(NAME, ROWS, COLUMNS);
        assertThat(actualResult, equalTo(INVALID_PARAMETER));
    }

    @Test
    void testDeleteRoomWhenAdminIsSignedIn()
            throws RoomNotFoundException, DeleteException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);

        // When
        String actualResult = roomCommandHandler.deleteRoom(NAME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        verify(roomService, times(1)).deleteRoom(NAME);
        assertThat(actualResult, equalTo(ROOM_DELETED));
    }

    @Test
    void testDeleteRoomShouldReturnRoomNotFoundException() throws RoomNotFoundException, DeleteException {
        Exception exception = null;
        // Given
        when(adminService.loggedAdmin()).thenReturn(true);
        when(roomService.deleteRoom(anyString())).thenThrow(RoomNotFoundException.class);


        // When
        try {
            adminService.loggedAdmin();
            roomService.deleteRoom(NAME);
        } catch (RoomNotFoundException e) {
            exception = e;
        }

        // Then
        assertNotNull(exception);

    }

    @Test
    void testDeleteRoomReturnErrorWhenAdminIsNotSignedIn()
            throws RoomNotFoundException {
        // Given
        when(adminService.loggedAdmin()).thenReturn(false);

        // When
        String current = roomCommandHandler.deleteRoom(NAME);

        // Then
        verify(adminService, times(1)).loggedAdmin();
        assertThat(current, equalTo(UNPRIVILIGED_MSG));
    }

    @Test
    void testListRoomsShouldReturnListOfRooms() {
        // Given
        when(roomService.getAllRooms()).thenReturn(rooms);

        // When
        String actual = roomCommandHandler.listRooms();

        // Then
        StringBuilder builder = new StringBuilder();
        for (Room room : rooms) {
            builder.append(room);
        }
        assertThat(actual, equalTo(builder.toString()));
    }

}