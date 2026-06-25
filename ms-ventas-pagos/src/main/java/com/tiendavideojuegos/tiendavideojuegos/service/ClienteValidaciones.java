package com.tiendavideojuegos.tiendavideojuegos.service;

import org.springframework.web.reactive.function.client.WebClient;

import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;

public class ClienteValidaciones {

    
    private WebClient.Builder webClientBuilder;

    public Boolean validarNullVacio(Clientes cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().trim().length() == 0) {
            return false;
        }
        if (cliente.getId() == null || cliente.getId() <= 0) {
            return false;
        }
        return true;
    }

}
