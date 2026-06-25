package com.tienda.catalogo_videojuegos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.catalogo_videojuegos.model.VideoJuego;

@Repository
public interface VideoJuegoRepository extends JpaRepository<VideoJuego, Integer> {

    @Query("SELECT v FROM VideoJuego v WHERE v.categoria.idCategoria = :idCategoria")
    List<VideoJuego> buscarVideoJuegos(@Param("idCategoria") Integer idCategoria);

    List<VideoJuego>findByNombreContainingIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);
}
