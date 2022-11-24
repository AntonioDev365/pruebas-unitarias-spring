package com.api.rest.pruebasunitarias.controller;
import com.api.rest.pruebasunitarias.model.Empleado;
import com.api.rest.pruebasunitarias.service.EmpleadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest //  Esta anotacion sirve para poder comprobar los controladores, tambien autoconfigura mockMvc
public class EmpleadoControllerTests {

    @Autowired
    private MockMvc mockMvc; //Objeto MockMvc para poder crear peticiones http

    @MockBean //Para agregar objetos simulados al contexto de la aplicacion mockBean
    private EmpleadoService empleadoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGuardarEmpleado() throws Exception {
        //given
        Empleado empleado=empleado = Empleado.builder().id(1L).nombre("Christian").apellido("Ramirez").email("c1@gmail.com").build();

        given(empleadoService.saveEmpleado(any(Empleado.class)))
                .willAnswer(invocation ->invocation.getArgument(0));
        //when
        ResultActions response=mockMvc.perform(post("/api/empleados").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleado)));

        //then
        response.andDo(print() )
    }

}
