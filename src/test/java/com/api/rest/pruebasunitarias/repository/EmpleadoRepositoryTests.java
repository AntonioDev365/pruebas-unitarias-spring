package com.api.rest.pruebasunitarias.repository;

import com.api.rest.pruebasunitarias.model.Empleado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest //Buscara las clases que tenga las anotaciones entity y jpaRepository
public class EmpleadoRepositoryTests {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    void setup(){
        System.out.println("Hola mundo");
        empleado=Empleado.builder().nombre("Christian").apellido("Ramirez").email("c1@gmail.com").build();
    }

    @DisplayName("Test para guardar un empleado ")
    @Test
    void testGuardarEmpleado(){
        //given - dado o condición previa o configuración
        Empleado empleado1= Empleado.builder().nombre("Pepe").apellido("Lopez")
                .email("pepe@gmail.com").build();

        //when - accion o el comportamiento que vamos a probar
        Empleado empleadoGuardado=empleadoRepository.save(empleado1);

        //then - verificar la salida
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar a los empleados")
    @Test
    void testListarEmpleados(){
        //given - Configuracion previa o preconfiguracion
        Empleado empleado1= Empleado.builder().nombre("Julen").apellido("Oliva")
                .email("j2@gmail.com").build();
        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);

        //when - Condición a probar
        List<Empleado> listaEmpleados=empleadoRepository.findAll();

        //then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }
    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpleadoPorId(){
        //Given
        empleadoRepository.save(empleado);

        //when
        Empleado empleadoBd=empleadoRepository.findById(empleado.getId()).get();

        //then
        assertThat(empleadoBd).isNotNull();
    }

    @DisplayName("Test para actualizar un empleado")
    @Test
    void testActualizarEmpleado(){
        empleadoRepository.save(empleado);

        //when
        Empleado empleadoGuardado=empleadoRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("c232@gmail.com");
        empleadoGuardado.setNombre("Cristian Raul");
        empleadoGuardado.setApellido("Ramirez Cucitini");
        Empleado empleadoActualizado=empleadoRepository.save(empleadoGuardado);

        //then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("c232@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Christian Raul");
    }
    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        empleadoRepository.save(empleado);

        //when
        empleadoRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadoOptional=empleadoRepository.findById(empleado.getId());

        //then
        assertThat(empleadoOptional).isEmpty();

    }
}
