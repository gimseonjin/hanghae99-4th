package com.security.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.security.model.User;
import com.security.security.model.dto.request.RegisterRequestDto;
import com.security.security.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.xml.bind.ValidationException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class SecurityApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;


	@DisplayName("1. 회원가입 성공")
	@Test
	public void test() throws Exception {
        //Given
		RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
				.username("testUser")
				.password("password")
				.password2("password")
				.build();

		User user = User.builder()
				.username("testUser")
				.password("password")
				.build();

		when(userService.save(registerRequestDto)).thenReturn(user);

        //When
        //Then
		mockMvc.perform(post("/account/register")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(registerRequestDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("username").value("testUser"));
	}

    @DisplayName("2. 회원가입 성공2")
    @Test
    public void test2() throws Exception {
        //Given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser123")
                .password("password")
                .password2("password")
                .build();

        User user = User.builder()
                .username("testUser123")
                .password("password")
                .build();

        when(userService.save(registerRequestDto)).thenReturn(user);

        //When
        //Then
        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("username").value("testUser123"));
    }

    @DisplayName("3. 회원가입 실패 - 아이디 한글 포함")
    @Test
    public void test3() throws Exception {
        //Given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("테스트당")
                .password("password")
                .password2("password")
                .build();

        //When
        //Then
        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
    }

    @DisplayName("4. 회원가입 실패 - 비밀번호 같은 값 포함")
    @Test
    public void test4() throws Exception {
        //Given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("testUser")
                .password("password1")
                .password2("password")
                .build();


        //When
        //Then
        when(userService.save(registerRequestDto)).thenThrow(new ValidationException("비밀번호가 일치하지 않습니다."));

        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDto)))
                        .andExpect(status().isBadRequest());
    }

    @DisplayName("5. 회원가입 실패 - 동일 아이디 존재")
    @Test
    public void test5() throws Exception {
        //Given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("password")
                .password("password")
                .password2("password")
                .build();

        when(userService.save(registerRequestDto)).thenThrow(new ValidationException("아이디가 비빌번호에 포함되어 있습니다."));

        //When
        //Then
        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDto)))
                        .andExpect(status().isBadRequest());
    }

    @DisplayName("6. 회원가입 실패 - 이미 존재하는 아이디")
    @Test
    public void test6() throws Exception {
        //Given
        RegisterRequestDto registerRequestDto = RegisterRequestDto.builder()
                .username("exist")
                .password("password")
                .password2("password")
                .build();

        //When
        //Then
        when(userService.save(registerRequestDto)).thenThrow(new ValidationException("중복된 유저네임 입니다."));

        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequestDto)))
                        .andExpect(status().isBadRequest());
    }

}
