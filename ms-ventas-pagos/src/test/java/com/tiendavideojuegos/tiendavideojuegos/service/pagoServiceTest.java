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
 
import com.tiendavideojuegos.tiendavideojuegos.DTO.PagoDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Pago;
import com.tiendavideojuegos.tiendavideojuegos.repository.PagoRepository;

@SpringBootTest
public class pagoServiceTest {

    @Autowired
    private PagoService pagoservice;

    @MockitoBean
    private PagoRepository pagorepository;

    private Pago createPago(String estadoPago){
        Pago pago = new Pago();
        pago.setIdPago(1);
        pago.setEstadoPago("aceptado");
        return pago;
    }
    
    @Test
    public void testObtenerTodos(){
        when(pagorepository.findAll()).thenReturn(List.of(createPago("aceptado")));

        List<PagoDTO> resultado = pagoservice.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1,resultado.size());
    }

    @Test
    public void testBuscarPorId_existente(){
        Pago pago = createPago("APROBADO");
        when(pagorepository.findById(1)).thenReturn(Optional.of(pago));
 
        PagoDTO resultado = pagoservice.buscarPorId(1);
 
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdPago());
        assertEquals("APROBADO", resultado.getEstadoPago());
    }

    @Test
    public void testBuscarPorId_noExistente(){
        when(pagorepository.findById(99)).thenReturn(Optional.empty());
 
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pagoservice.buscarPorId(99);
        });
 
        assertEquals("Pago no encontrado", ex.getMessage());
    }

    @Test
    public void testGuardar(){
        Pago nuevoPago = createPago("PENDIENTE");
 
        when(pagorepository.save(any(Pago.class))).thenReturn(nuevoPago);
 
        PagoDTO resultado = pagoservice.guardar(nuevoPago);
 
        assertNotNull(resultado);
        assertEquals("PENDIENTE", resultado.getEstadoPago());
        verify(pagorepository, times(1)).save(nuevoPago);
    }

    
}
