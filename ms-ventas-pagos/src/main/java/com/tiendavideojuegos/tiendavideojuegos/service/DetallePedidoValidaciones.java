package com.tiendavideojuegos.tiendavideojuegos.service;

import org.springframework.stereotype.Service;

import com.tiendavideojuegos.tiendavideojuegos.model.DetallePedido;

@Service
public class DetallePedidoValidaciones {

    public Boolean validarNullVacio(DetallePedido detalle) {
        if (detalle.getPrecio() == null || detalle.getPrecio() < 0) {
            return false;
        }
        if (detalle.getPedido() == null || detalle.getPedido().getId() == null) {
            return false;
        }
        return true;
    }
}