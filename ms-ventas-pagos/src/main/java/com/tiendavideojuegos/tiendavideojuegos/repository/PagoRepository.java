package com.tiendavideojuegos.tiendavideojuegos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tiendavideojuegos.tiendavideojuegos.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    @Query("SELECT COALESCE(SUM(d.precio), 0) FROM Pago p JOIN p.detallePedido d")
    Double calcularTotalPagos();

    @Query("SELECT p FROM Pago p WHERE p.estadoPago = :estadoPago")
    List<Pago> buscarPagosPorEstado(@Param("estadoPago") String estadoPago);

}
