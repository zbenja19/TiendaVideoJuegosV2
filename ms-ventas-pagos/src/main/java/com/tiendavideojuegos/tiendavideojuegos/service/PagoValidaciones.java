package com.tiendavideojuegos.tiendavideojuegos.service;

import org.springframework.stereotype.Service;

import com.tiendavideojuegos.tiendavideojuegos.model.Pago;

@Service
public class PagoValidaciones {

    public Boolean validarNullVacio(Pago pago) {
        if (pago.getEstadoPago() == null || pago.getEstadoPago().trim().length() == 0) {
            return false;
        }
        if (pago.getEstadoPago().trim().length() < 10 || pago.getEstadoPago().trim().length() > 20) {
            return false;
        }
        if (pago.getDetallePedido() == null || pago.getDetallePedido().getIdDetallePedido() == null) {
            return false;
        }
        return true;
    }
}