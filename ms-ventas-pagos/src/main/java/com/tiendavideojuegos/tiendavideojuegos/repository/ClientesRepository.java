package com.tiendavideojuegos.tiendavideojuegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Integer> {

}
