package com.tiendavideojuegos.tiendavideojuegos.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tiendavideojuegos.tiendavideojuegos.DTO.DetallePedidoDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.DetallePedido;
import com.tiendavideojuegos.tiendavideojuegos.repository.DetallePedidoRepository;

@SpringBootTest
public class DetallePedidoServiceTest {

    @Autowired
    private DetallePedidoService detallepedidoservice;

    @MockitoBean
    private DetallePedidoRepository detallepedidorepository;

    private DetallePedido createDetallePedido(Double precio){
        DetallePedido detallepedido = new DetallePedido();
        detallepedido.setIdDetallePedido(1);
        detallepedido.setPrecio(precio);
        return detallepedido;
    }

    @Test 
    public void testObtenerTodos(){
        when(detallepedidorepository.findAll()).thenReturn(List.of(createDetallePedido(5000.0)));

        List<DetallePedidoDTO> resultado = detallepedidoservice.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testBuscarPorId_existente(){
        DetallePedido detallePedido = createDetallePedido(5000.0);
        when(detallepedidorepository.findById(1)).thenReturn(Optional.of(detallePedido));
        
        DetallePedidoDTO resultado = detallepedidoservice.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDetallePedido());
        assertEquals(5000.0, resultado.getPrecio());
    }

     @Test
    public void testBuscarPorId_noExistente() {
        when(detallepedidorepository.findById(99)).thenReturn(Optional.empty());
 
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            detallepedidoservice.buscarPorId(99);
        });
 
        assertEquals("DetallePedido no encontrado", ex.getMessage());
    }

    @Test
    public void testGuardar() {
        DetallePedido nuevoDetalle = createDetallePedido(3000.0);
 
        when(detallepedidorepository.save(any(DetallePedido.class))).thenReturn(nuevoDetalle);
 
        DetallePedidoDTO resultado = detallepedidoservice.guardar(nuevoDetalle);
 
        assertNotNull(resultado);
        assertEquals(3000.0, resultado.getPrecio());
        verify(detallepedidorepository, times(1)).save(nuevoDetalle);
    }
}
