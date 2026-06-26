package com.tiendavideojuegos.tiendavideojuegos.service;

import org.springframework.stereotype.Service;

import com.tiendavideojuegos.tiendavideojuegos.model.Pedidos;

@Service
public class PedidosValidaciones {

    public Boolean validarNullVacio(Pedidos pedido) {
        if (pedido.getFechaAgregada() == null) {
            return false;
        }
        if (pedido.getMontoTotal() == null || pedido.getMontoTotal() < 0 || pedido.getMontoTotal() > 1000000) {
            return false;
        }
        if (pedido.getEstado() == null) {
            return false;
        }
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            return false;
        }
        return true;
    }
}
