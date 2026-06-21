package com.tienda.catalogo_videojuegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda.catalogo_videojuegos.model.Plataforma;

@Repository
public interface  PlataformaRepository extends  JpaRepository<Plataforma, Integer>{

    boolean existsByNombreIgnoreCase(String nombre);
}
