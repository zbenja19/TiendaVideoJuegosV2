package com.tienda.catalogo_videojuegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tienda.catalogo_videojuegos.model.Plataforma;
import com.tienda.catalogo_videojuegos.repository.PlataformaRepository;

@SpringBootTest
class PlataformaServiceTest {

    @Autowired
    private PlataformaService plataformaService;

    @MockitoBean
    private PlataformaRepository plataformaRepository;

    private Plataforma createPlataforma() {
        Plataforma plata = new Plataforma();
        plata.setId(1);
        plata.setNombre("PlayStation 5");
        return plata;
    }

    @Test
    void testObtenerTodas() {
        // Given
        when(plataformaRepository.findAll()).thenReturn(List.of(createPlataforma()));

        // When
        List<Plataforma> resultado = plataformaService.obtenerTodas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void testBuscarPorId_Exitoso() {
        // Given
        when(plataformaRepository.findById(1)).thenReturn(Optional.of(createPlataforma()));

        // When
        Plataforma resultado = plataformaService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals("PlayStation 5", resultado.getNombre());
    }

    @Test
    void testGuardar() {
        // Given
        Plataforma p = createPlataforma();
        when(plataformaRepository.save(any(Plataforma.class))).thenReturn(p);

        // When
        Plataforma resultado = plataformaService.guardar(p);

        // Then
        assertNotNull(resultado);
        assertEquals("PlayStation 5", resultado.getNombre());
    }

    @Test
    void testEliminar_Exitoso() {
        // Given
        when(plataformaRepository.findById(1)).thenReturn(Optional.of(createPlataforma()));
        doNothing().when(plataformaRepository).delete(any(Plataforma.class));

        // When
        String resultado = plataformaService.eliminar(1);

        // Then
        assertEquals("La plataforma 'PlayStation 5' ha sido eliminada.", resultado);
    }

    @Test
    void testActualizarPlataforma() {
        // Given
        Plataforma existente = createPlataforma();
        Plataforma nvaPlataforma = new Plataforma();
        nvaPlataforma.setNombre("PS5 Pro");

        when(plataformaRepository.findById(1)).thenReturn(Optional.of(existente));
        when(plataformaRepository.save(any(Plataforma.class))).thenReturn(existente);

        // When
        Plataforma resultado = plataformaService.actualizarPlataforma(1, nvaPlataforma);

        // Then
        assertNotNull(resultado);
        assertEquals("PS5 Pro", resultado.getNombre());
    }
}