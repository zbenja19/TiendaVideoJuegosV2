package com.tienda.catalogo_videojuegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tienda.catalogo_videojuegos.DTO.VideoJuegoDTO;
import com.tienda.catalogo_videojuegos.model.VideoJuego;
import com.tienda.catalogo_videojuegos.repository.VideoJuegoRepository;

@SpringBootTest
class VideoJuegoServiceTest {

    @Autowired
    private VideoJuegoService videojuegoService;

    @MockitoBean
    private VideoJuegoRepository videojuegoRepository;

    private VideoJuego createVideoJuego() {
        VideoJuego vj = new VideoJuego();
        vj.setIdVideoJuego(1);
        vj.setNombre("The Legend of Zelda");
        vj.setDescripcion("Aventura");
        vj.setPrecio(59990.0);
        vj.setStock(10);
        vj.setGenero("Acción");
        return vj;
    }

    @Test
    void testListarTodos() {
        // Given
        when(videojuegoRepository.findAll()).thenReturn(List.of(createVideoJuego()));

        // When
        List<VideoJuegoDTO> resultado = videojuegoService.listarTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("The Legend of Zelda", resultado.get(0).getNombre());
    }

    @Test
    void testGuardar() {
        // Given
        VideoJuego vj = createVideoJuego();
        when(videojuegoRepository.save(any(VideoJuego.class))).thenReturn(vj);

        // When
        VideoJuego resultado = videojuegoService.guardar(vj);

        // Then
        assertNotNull(resultado);
        assertEquals("The Legend of Zelda", resultado.getNombre());
    }

    @Test
    void testEliminar_Exitoso() {
        // Given
        when(videojuegoRepository.findById(1)).thenReturn(Optional.of(createVideoJuego()));
        doNothing().when(videojuegoRepository).delete(any(VideoJuego.class));

        // When
        String resultado = videojuegoService.eliminar(1);

        // Then
        assertEquals("VideojuegoThe Legend of ZeldaEliminado exitosamente", resultado);
    }

    @Test
    void testBuscarPorId() {
        // Given
        when(videojuegoRepository.findById(1)).thenReturn(Optional.of(createVideoJuego()));

        // When
        VideoJuegoDTO resultado = videojuegoService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals("The Legend of Zelda", resultado.getNombre());
    }

    @Test
    void testBuscarPornombre() {
        // Given
        when(videojuegoRepository.findByNombreContaining("Zelda")).thenReturn(List.of(createVideoJuego()));

        // When
        List<VideoJuego> resultado = videojuegoService.buscarPornombre("Zelda");

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("The Legend of Zelda", resultado.get(0).getNombre());
    }

    @Test
    void testActualizarVideoJuego() {
        // Given
        VideoJuego existente = createVideoJuego();
        VideoJuego nvoVideoJuego = new VideoJuego();
        nvoVideoJuego.setNombre("Zelda");

        when(videojuegoRepository.findById(1)).thenReturn(Optional.of(existente));
        when(videojuegoRepository.save(any(VideoJuego.class))).thenReturn(existente);

        // When
        VideoJuegoDTO resultado = videojuegoService.actualizarVideoJuego(1, nvoVideoJuego);

        // Then
        assertNotNull(resultado);
        assertEquals("Zelda TotK", resultado.getNombre());
    }
}