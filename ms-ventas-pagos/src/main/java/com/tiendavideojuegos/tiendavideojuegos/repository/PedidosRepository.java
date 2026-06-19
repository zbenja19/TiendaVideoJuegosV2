package com.tiendavideojuegos.tiendavideojuegos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiendavideojuegos.tiendavideojuegos.model.Pedidos;

@Repository
public interface PedidosRepository extends JpaRepository<Pedidos, Integer> {

    List<Pedidos> findByEstado(Boolean estado);

    @Query("SELECT SUM(p.montoTotal) FROM Pedidos p WHERE p.cliente.id = :clienteId")
    Double moneycalculeitor(@Param("clienteId") Integer clienteId);

}
