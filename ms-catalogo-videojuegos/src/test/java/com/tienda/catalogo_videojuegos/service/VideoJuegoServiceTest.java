package com.tienda.catalogo_videojuegos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

    @MockitoBean
    private VideoJuegoValidaciones videoJuegoValidaciones;


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

    private VideoJuegoDTO createVideoJuegoDTO() {
        VideoJuegoDTO dto = new VideoJuegoDTO();
        dto.setIdVideoJuego(1);
        dto.setNombre("The Legend of Zelda");
        dto.setDescripcion("Aventura");
        dto.setPrecio(59990.0);
        dto.setStock(10);
        dto.setGenero("Acción");
        dto.setNombreCategoria(null);
        dto.setNombrePlataforma(null);
        dto.setNombreProveedor(null);
        
        return dto;
    }

    @Test
    void testListarTodos() {
        // Given
        when(videojuegoRepository.findAll()).thenReturn(List.of(createVideoJuego()));
        when(videoJuegoValidaciones.convertirADTO(any(VideoJuego.class)))
            .thenReturn(createVideoJuegoDTO());


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

        when(videoJuegoValidaciones.validarNullVacio(any(VideoJuego.class))).thenReturn(true);
        when(videojuegoRepository.save(any(VideoJuego.class))).thenReturn(vj);
        when(videoJuegoValidaciones.convertirADTO(any(VideoJuego.class)))
            .thenReturn(createVideoJuegoDTO());

        // When
        VideoJuegoDTO resultado = videojuegoService.guardar(vj);

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
        assertEquals("Videojuego The Legend of Zelda eliminado exitosamente", resultado);
    }

    @Test
    void testBuscarPorId() {
        // Given
        when(videojuegoRepository.findById(1)).thenReturn(Optional.of(createVideoJuego()));
        when(videoJuegoValidaciones.convertirADTO(any(VideoJuego.class)))
        .thenReturn(createVideoJuegoDTO());

        // When
        VideoJuegoDTO resultado = videojuegoService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals("The Legend of Zelda", resultado.getNombre());
    }

    @Test
    void testBuscarPornombre() {
        // Given
        when(videojuegoRepository.findByNombreContainingIgnoreCase("Zelda")).thenReturn(List.of(createVideoJuego()));

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
        VideoJuegoDTO dto = createVideoJuegoDTO();
        dto.setNombre("Zelda");
        when(videoJuegoValidaciones.convertirADTO(any(VideoJuego.class))).thenReturn(dto);

        // When
        VideoJuegoDTO resultado = videojuegoService.actualizarVideoJuego(1, nvoVideoJuego);

        // Then
        assertNotNull(resultado);
        assertEquals("Zelda", resultado.getNombre());
    }
}