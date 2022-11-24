package com.api.rest.pruebasunitarias.repository;

import com.api.rest.pruebasunitarias.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado,Long> {

    @Query
    Optional<Empleado> findByEmail(String email);
}
