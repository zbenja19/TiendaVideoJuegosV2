package com.tiendavideojuegos.tiendavideojuegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PedidosDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;
import com.tiendavideojuegos.tiendavideojuegos.model.Pedidos;
import com.tiendavideojuegos.tiendavideojuegos.repository.ClientesRepository;
import com.tiendavideojuegos.tiendavideojuegos.repository.PedidosRepository;

@SpringBootTest
public class PedidosServiceTest {

    @Autowired
    private PedidosService pedidosService;

    @MockitoBean
    private PedidosRepository pedidosRepository;

    @MockitoBean
    private ClientesRepository clientesRepository;

    private Clientes createCliente() {
        Clientes cliente = new Clientes();
        cliente.setId(1);
        cliente.setNombre("Juan");
        return cliente;
    }

    private Pedidos createPedido(Clientes cliente) {
        Pedidos pedido = new Pedidos();
        pedido.setId(1);
        pedido.setMontoTotal(15000.0);
        pedido.setEstado(true);
        pedido.setFechaAgregada(LocalDate.now());
        pedido.setCliente(cliente);
        return pedido;
    }

    private PedidosDTO createPedidosDTO() {
        PedidosDTO dto = new PedidosDTO();
        dto.setId(1);
        dto.setClienteId(1);
        dto.setMontoTotal(15000.0);
        dto.setEstado(true);
        dto.setFechaAgregada(LocalDate.now());
        return dto;
    }

    @Test
    public void testObtenerTodos() {
        Clientes cliente = createCliente();
        when(pedidosRepository.findAll()).thenReturn(List.of(createPedido(cliente)));

        List<PedidosDTO> resultado = pedidosService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
        assertEquals(1, resultado.get(0).getClienteId());
    }

    @Test
    public void testBuscarPorId() {
        Clientes cliente = createCliente();
        when(pedidosRepository.findById(1)).thenReturn(Optional.of(createPedido(cliente)));

        PedidosDTO resultado = pedidosService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(15000.0, resultado.getMontoTotal());
    }

    @Test
    public void testGuardar_ConDTO() {
        Clientes cliente = createCliente();
        PedidosDTO dtoParaGuardar = createPedidosDTO();
        Pedidos pedidoGuardado = createPedido(cliente);

        when(clientesRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(pedidosRepository.save(any(Pedidos.class))).thenReturn(pedidoGuardado);

        PedidosDTO resultado = pedidosService.guardar(dtoParaGuardar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(1, resultado.getClienteId());
    }

    @Test
    public void testGuardar_ConEntidad() {
        Clientes cliente = createCliente();
        Pedidos pedidoParaGuardar = createPedido(cliente);

        when(pedidosRepository.save(any(Pedidos.class))).thenReturn(pedidoParaGuardar);

        Pedidos resultado = pedidosService.guardar(pedidoParaGuardar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertTrue(resultado.getEstado());
    }

    @Test
    public void testBuscarPorEstado() {
        Clientes cliente = createCliente();
        when(pedidosRepository.findByEstado(true)).thenReturn(List.of(createPedido(cliente)));

        List<PedidosDTO> resultado = pedidosService.buscarPorEstado(true);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getEstado());
    }

    @Test
    public void testGastosTotalescliente() {
        when(pedidosRepository.moneycalculeitor(1)).thenReturn(45000.0);

        Double resultado = pedidosService.gastosTotalescliente(1);

        assertEquals(45000.0, resultado);
    }

    @Test
    public void testAlternarEstado() {
        Clientes cliente = createCliente();
        Pedidos pedidoExistente = createPedido(cliente);
        pedidoExistente.setEstado(true);

        when(pedidosRepository.findById(1)).thenReturn(Optional.of(pedidoExistente));
        when(pedidosRepository.save(any(Pedidos.class))).thenReturn(pedidoExistente);

        pedidosService.alternarEstado(1);

        assertFalse(pedidoExistente.getEstado());
        verify(pedidosRepository, times(1)).save(pedidoExistente);
    }
}