package com.tienda.catalogo_videojuegos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tienda.catalogo_videojuegos.model.Proveedor;

@Repository
public interface  ProveedorRepository  extends JpaRepository<Proveedor, Integer>{

    boolean existsByNombreIgnoreCase(String nombre);

}
