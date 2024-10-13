package com.cloud.spring.tp1.controller;

import com.cloud.spring.tp1.entity.Car;
import com.cloud.spring.tp1.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Resource
    private MockMvc mvc;

    @Resource
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;


    @Test
    void hello() throws Exception {

        mvc.perform(request(HttpMethod.GET,"/"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String content = result.getResponse().getContentAsString();
                    assertEquals("Hello", content);
                })
                .andDo(print())
                .andReturn();
    }

    @Test
    void addCar() throws Exception {

        String jsonData = "{" +
                " \"plateNumber\":\"QR-890-AB\"," +
                " \"price\": 5000.0," +
                " \"brand\": \"Audi\"," +
                " \"isRent\": false," +
                " \"beginDate\": \"2024-10-13 14:20:00\"," +
                " \"endDate\": \"2024-10-15 14:20:00\"" +
                "}";

        Car car = objectMapper.readValue(jsonData, Car.class);

        when(carService.addCar(any(Car.class))).thenReturn(car);

        mvc.perform(request(HttpMethod.POST,"/cars/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(status().isOk())
                .andExpect(result -> {

                    String content = result.getResponse().getContentAsString();

                    String verifyData = objectMapper.writeValueAsString(car);
                    assertEquals(verifyData, content);

                })
                .andDo(print())
                .andReturn();

    }
}