package com.tiendavideojuegos.tiendavideojuegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PagoDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Pago;
import com.tiendavideojuegos.tiendavideojuegos.repository.PagoRepository;

@SpringBootTest
public class PagoServiceTest {

    @Autowired
    private PagoService pagoService;

    @MockitoBean
    private PagoRepository pagoRepository;

    private Pago createPago() {
        Pago pago = new Pago();
        pago.setIdPago(1);
        pago.setEstadoPago("Aprobado");
        return pago;
    }

    @Test
    public void testObtenerTodos() {
        when(pagoRepository.findAll()).thenReturn(List.of(createPago()));

        List<PagoDTO> resultado = pagoService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdPago());
        assertEquals("Aprobado", resultado.get(0).getEstadoPago());
    }

    @Test
    public void testBuscarPorId() {
        when(pagoRepository.findById(1)).thenReturn(Optional.of(createPago()));

        PagoDTO resultado = pagoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdPago());
        assertEquals("Aprobado", resultado.getEstadoPago());
    }

    @Test
    public void testGuardar() {
        Pago pagoParaGuardar = createPago();
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoParaGuardar);

        PagoDTO resultado = pagoService.guardar(pagoParaGuardar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdPago());
        assertEquals("Aprobado", resultado.getEstadoPago());
    }
}