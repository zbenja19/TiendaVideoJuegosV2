package com.tiendavideojuegos.tiendavideojuegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tiendavideojuegos.tiendavideojuegos.DTO.DetallePedidoDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.DetallePedido;
import com.tiendavideojuegos.tiendavideojuegos.repository.DetallePedidoRepository;

@SpringBootTest
public class DetallePedidoServiceTest {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @MockitoBean
    private DetallePedidoRepository detallePedidoRepository;

    private DetallePedido createDetallePedido() {
        DetallePedido detalle = new DetallePedido();
        detalle.setIdDetallePedido(1);
        detalle.setPrecio(29990.0);
        return detalle;
    }

    @Test
    public void testObtenerTodos() {
        when(detallePedidoRepository.findAll()).thenReturn(List.of(createDetallePedido()));

        List<DetallePedidoDTO> resultado = detallePedidoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdDetallePedido());
        assertEquals(29990.0, resultado.get(0).getPrecio());
    }

    @Test
    public void testBuscarPorId() {
        when(detallePedidoRepository.findById(1)).thenReturn(Optional.of(createDetallePedido()));

        DetallePedidoDTO resultado = detallePedidoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDetallePedido());
        assertEquals(29990.0, resultado.getPrecio());
    }

    @Test
    public void testGuardar() {
        DetallePedido detalleParaGuardar = createDetallePedido();
        when(detallePedidoRepository.save(any(DetallePedido.class))).thenReturn(detalleParaGuardar);

        DetallePedidoDTO resultado = detallePedidoService.guardar(detalleParaGuardar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDetallePedido());
        assertEquals(29990.0, resultado.getPrecio());
    }
}