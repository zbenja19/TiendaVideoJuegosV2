package com.tiendavideojuegos.tiendavideojuegos.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PedidosDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;

import reactor.core.publisher.Mono;

@Service
public class ClienteValidaciones {

    
    private WebClient.Builder webClientBuilder;

    public Boolean validarNullVacio(Clientes cliente) {
        if(cliente.getNombre() == null || cliente.getNombre().trim().length() == 0) {
            return false;
        }
        if(cliente.getId() == null || cliente.getId() <= 0) {
            return false;
        }
        return true;
    }

    public PedidosDTO obtenerPedido(Integer id) {
    PedidosDTO pedidoRecuperado = new PedidosDTO();
    try {
        PedidosDTO resultado = webClientBuilder.build()
            .get()
            .uri("http://pedidos/api/v1/pedidos/buscar-por-cliente/" + id)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.empty())
            .bodyToMono(PedidosDTO.class)
            .block();

        if (resultado != null) {
            return resultado;
        }
        pedidoRecuperado.setId(0);
        pedidoRecuperado.setClienteId(id);
        pedidoRecuperado.setEstado(false); 
        return pedidoRecuperado;

    } catch (Exception e) {
        pedidoRecuperado.setId(0);
        pedidoRecuperado.setClienteId(id);
        pedidoRecuperado.setEstado(false); 
        return pedidoRecuperado;
    }
}
}