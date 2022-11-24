package com.api.rest.pruebasunitarias.service;

import com.api.rest.pruebasunitarias.exception.ResourceNotFoundException;
import com.api.rest.pruebasunitarias.model.Empleado;
import com.api.rest.pruebasunitarias.repository.EmpleadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    @Mock //Crea un simulacro
    private EmpleadoRepository empleadoRepository;

    @InjectMocks //Injecto el mock
    private EmpleadoServiceImpl empleadoService;

    private Empleado empleado;

    @BeforeEach
    void setup() {
        System.out.println("Hola mundo");
        empleado = Empleado.builder().id(1L).nombre("Christian").apellido("Ramirez").email("c1@gmail.com").build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado() {
        //given
        given(empleadoRepository.findByEmail(empleado.getEmail())).willReturn(Optional.empty()); //Si ese email ya existe me va a devolver un optional vacio
        given(empleadoRepository.save(empleado)).willReturn(empleado); //Si ese email no existe me va a devolver el empleado

        //when
        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        //then
        assertThat(empleadoGuardado).isNotNull();

    }

    /*
    @DisplayName("Test para guardar un empleado con Throw Exception")
    @Test
    void testGuardarEmpleadoConThrowException(){
        //given
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado)); //Estamos forzando una excepcion,
        // si el empleado con dicho email existe por lo que no podriamos guardar dicho empleado debido a que ya existe.

        //when
        assertThrows(ResourceNotFoundException.class,()->{
            empleadoService.saveEmpleado(empleado);
        });

        //then
        verify(empleadoRepository, never()).save(any(Empleado.class));

    }*/
    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados() {

        //given
        Empleado empleado1 = Empleado.builder().id(2L).nombre("Julen").apellido("Oliva").email("j2@gmail.com").build();
        given(empleadoRepository.findAll()).willReturn(List.of(empleado, empleado1)); //No estoy mas que simulando

        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para retornar una lista vacia")
    @Test
    void testListarColeccionEmpleadosVacia() {

        //given
        Empleado empleado1 = Empleado.builder().id(2L).nombre("Julen").apellido("Oliva").email("j2@gmail.com").build();
        given(empleadoRepository.findAll()).willReturn(Collections.emptyList());//Estoy simulando el comportamiento,
        // no dependemos de nadie, estoy simulando que retornará una lista vacia

        //when
        List<Empleado> listaEmpleados = empleadoService.getAllEmpleados();

        //then
        assertThat(listaEmpleados).isEmpty();
        assertThat(listaEmpleados.size()).isEqualTo(0);

    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpleadoPorId(){
        //given
        given(empleadoRepository.findById(1L))
                .willReturn(Optional.of(empleado)); //Optional.of(empleado) significa que me va a retornar a dicho empleado
        //Tu simulas e indicas como quiere que se comporte

        //when
        Empleado empleadoGuardado=empleadoService.getEmpleadoById(empleado.getId()).get();

        //then
        assertThat(empleadoGuardado).isNotNull();
    }
    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){

        //given
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        empleado.setEmail("chr2@gmail.com");
        empleado.setNombre("Christian Raul");

        //when
        Empleado empleadoActualizado=empleadoService.updateEmpleado(empleado);

        //then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("chr2@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Christian Raul");

    }
    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){

        //given
        long empleadoId=1L;
        willDoNothing().given(empleadoRepository).deleteById(empleadoId); //No hara nada, no va a retornar nada ya que va a eliminar
        //por ello colocamos el willDoNothing

        //when
        empleadoService.deleteEmpleado(empleadoId);

        //then
        verify(empleadoRepository,times(1)).deleteById(empleadoId); //Voy a verificar que el deleteById
        // se haya llamado solo una vez
    }
}
