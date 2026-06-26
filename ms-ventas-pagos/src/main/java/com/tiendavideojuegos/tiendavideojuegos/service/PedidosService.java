
package com.tiendavideojuegos.tiendavideojuegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PedidosDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;
import com.tiendavideojuegos.tiendavideojuegos.model.Pedidos;
import com.tiendavideojuegos.tiendavideojuegos.repository.ClientesRepository;
import com.tiendavideojuegos.tiendavideojuegos.repository.PedidosRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private ClientesRepository clientesRepository;

    public List<PedidosDTO> obtenerTodos() {
        return pedidosRepository.findAll().stream()
                .map(this::convertirAPedidosDTO)
                .toList();
    }

    public PedidosDTO buscarPorId(Integer id) {
        Pedidos pedido = pedidosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("¡Pedido no encontrado!"));
        return convertirAPedidosDTO(pedido);
    }

    public PedidosDTO guardar(PedidosDTO pedidosDTO) {
        if (pedidosDTO.getClienteId() == null) {
            throw new RuntimeException("El clienteId es obligatorio para guardar un pedido");
        }

        Clientes cliente = clientesRepository.findById(pedidosDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Pedidos pedido = convertirAPedidos(pedidosDTO, cliente);
        if (pedido.getEstado() == null) {
            pedido.setEstado(false);
        }

        Pedidos guardado = pedidosRepository.save(pedido);
        return convertirAPedidosDTO(guardado);
    }

    public Pedidos guardar(Pedidos pedido) {
        if (pedido.getEstado() == null) {
            pedido.setEstado(false);
        }
        return pedidosRepository.save(pedido);
    }

    public List<PedidosDTO> buscarPorEstado(Boolean estado) {
        return pedidosRepository.findByEstado(estado).stream()
                .map(this::convertirAPedidosDTO)
                .toList();
    }

    public Double gastosTotalescliente(Integer clienteId) {
        Double total = pedidosRepository.moneycalculeitor(clienteId);
        return (total != null) ? total : 0.0;
    }

    public void alternarEstado(Integer id) {
        Pedidos pedido = pedidosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el pedido para cambiar estado"));

        pedido.setEstado(!pedido.getEstado());
        pedidosRepository.save(pedido);
    }

    

    private Pedidos convertirAPedidos(PedidosDTO pedidosDTO, Clientes cliente) {
        Pedidos pedido = new Pedidos();
        pedido.setId(pedidosDTO.getId());
        pedido.setFechaAgregada(pedidosDTO.getFechaAgregada());
        pedido.setMontoTotal(pedidosDTO.getMontoTotal());
        pedido.setEstado(pedidosDTO.getEstado());
        pedido.setCliente(cliente);
        return pedido;
    }

    private PedidosDTO convertirAPedidosDTO(Pedidos pedido) {
        PedidosDTO pedidosDTO = new PedidosDTO();
        pedidosDTO.setId(pedido.getId());
        pedidosDTO.setClienteId(pedido.getCliente() != null ? pedido.getCliente().getId() : null);
        pedidosDTO.setFechaAgregada(pedido.getFechaAgregada());
        pedidosDTO.setMontoTotal(pedido.getMontoTotal());
        pedidosDTO.setEstado(pedido.getEstado());
        return pedidosDTO;
    }

}