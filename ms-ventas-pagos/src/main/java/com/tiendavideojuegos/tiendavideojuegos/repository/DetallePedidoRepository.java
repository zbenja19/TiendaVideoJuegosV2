package com.tiendavideojuegos.tiendavideojuegos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiendavideojuegos.tiendavideojuegos.model.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    @Query("SELECT p FROM DetallePedido p WHERE p.idDetallePedido = :idDetallePedido")
    List<DetallePedido> buscarPedidos(@Param("idDetallePedido") Integer idDetallePedido);
}
