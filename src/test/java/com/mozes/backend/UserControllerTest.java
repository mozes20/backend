package com.mozes.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozes.backend.dto.UserDto;
import com.mozes.backend.dto.UserResponsDto;
import com.mozes.backend.dto.UserRoleDto;
import com.mozes.backend.services.UserService;
import org.hibernate.service.spi.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUserSuccess() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setPassword("password");
        userDto.setEmail("email");
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setRoles("ADMIN");
        userDto.setUserRole(userRoleDto);

        String jsonRequest = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("Data saved successfully"));
    }

    @Test
    public void testCreateUserBadRequest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setPassword("password");
        userDto.setEmail("email");
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setRoles("ADMIN");
        userDto.setUserRole(userRoleDto);

        String jsonRequest = objectMapper.writeValueAsString(userDto);

        doThrow(DataIntegrityViolationException.class)
                .when(userService).createUser(userDto, userRoleDto);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isConflict())
                .andExpect(content().string("A felhasználó már létezik!"));
    }

    @Test
    public void testCreateUserServiceException() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setPassword("password");
        userDto.setEmail("email");
        UserRoleDto userRoleDto = new UserRoleDto();
        userRoleDto.setRoles("ADMIN");
        userDto.setUserRole(userRoleDto);

        String jsonRequest = objectMapper.writeValueAsString(userDto);

        doThrow(ServiceException.class)
                .when(userService).createUser(userDto, userRoleDto);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUsersSuccess() throws Exception {
        List<UserResponsDto> users = new ArrayList<>();
        UserResponsDto userResponsDto1 = new UserResponsDto();
        userResponsDto1.setId(1);
        userResponsDto1.setUsername("user1");
        userResponsDto1.setEmail("user1@example.com");
        users.add(userResponsDto1);
        UserResponsDto userResponsDto2 = new UserResponsDto();
        userResponsDto2.setId(2);
        userResponsDto2.setUsername("user2");
        userResponsDto2.setEmail("user2@example.com");
        users.add(userResponsDto2);

        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.length()", is(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].id", is(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].username", is("user1")))
                .andExpect((ResultMatcher) jsonPath("$[0].email", is("user1@example.com")))
                .andExpect((ResultMatcher) jsonPath("$[1].id", is(2)))
                .andExpect((ResultMatcher) jsonPath("$[1].username", is("user2")))
                .andExpect((ResultMatcher) jsonPath("$[1].email", is("user2@example.com")));
    }
    @Test
    public void testGetUsersServiceException() throws Exception {
        when(userService.getUsers()).thenThrow(ServiceException.class);

        mockMvc.perform(get("/user"))
                .andExpect(status().isBadRequest());
    }

}