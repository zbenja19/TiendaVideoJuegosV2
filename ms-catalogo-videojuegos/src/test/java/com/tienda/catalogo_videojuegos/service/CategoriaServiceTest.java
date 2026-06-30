package com.tienda.catalogo_videojuegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tienda.catalogo_videojuegos.DTO.CategoriaDTO;
import com.tienda.catalogo_videojuegos.model.Categoria;
import com.tienda.catalogo_videojuegos.model.VideoJuego;
import com.tienda.catalogo_videojuegos.repository.CategoriaRepository;
import com.tienda.catalogo_videojuegos.repository.VideoJuegoRepository;


@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @MockitoBean
    private CategoriaRepository categoriaRepository;

    @MockitoBean
    private VideoJuegoRepository videojuegoRepository;

    @MockitoBean
    private VideoJuegoValidaciones videoJuegoValidaciones;

    private Categoria createCategoria() {
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNombre("Acción");
        categoria.setDescripcion("Juegos llenos de adrenalina y combates");
        return categoria;
    }

    @Test
    public void testListarTodos() {
        //GIVEN

        when(categoriaRepository.findAll()).thenReturn(List.of(createCategoria()));
        // WHEN

        List<CategoriaDTO> resultado = categoriaService.listarTodos();
        // THEN

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Acción", resultado.get(0).getNombre());
    }

    @Test
    public void testBuscarPorId_Exitoso() {
        // GIVEN: El repositorio encuentra la categoría con ID 1
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(createCategoria()));

        // WHEN: Buscamos por ID
        CategoriaDTO resultado = categoriaService.buscarPorId(1);
        // THEN
        
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdCategoria());
        assertEquals("Acción", resultado.getNombre());
    }

    @Test
    public void testBuscarPorId_NoEncontrado() {
        // GIVEN 

        when(categoriaRepository.findById(2)).thenReturn(Optional.empty());
        // WHEN

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoriaService.buscarPorId(2);
        });

        assertEquals("¡La categoria no esta registrada!", exception.getMessage());
    }

    @Test
    public void testGuardar() {
        // GIVEN

        Categoria categoriaInput = createCategoria();
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaInput);
        // WHEN

        CategoriaDTO resultado = categoriaService.guardar(categoriaInput);
        // THEN

        assertNotNull(resultado);
        assertEquals("Acción", resultado.getNombre());
    }

    @Test
    public void testActualizarCategoria() {
        // GIVEN
        Categoria categoriaOriginal = createCategoria();
        Categoria nuevosDatos = new Categoria();
        nuevosDatos.setNombre("RPG");
        nuevosDatos.setDescripcion("Juegos de rol y aventura");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaOriginal));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaOriginal);

        CategoriaDTO resultado = categoriaService.actualizarCategoria(1, nuevosDatos);

        assertNotNull(resultado);
        assertEquals("RPG", resultado.getNombre());
        assertEquals("Juegos de rol y aventura", resultado.getDescripcion());
    }

    @Test
    public void testEliminar_Exitoso() {
        
        Categoria categoriaAEliminar = createCategoria();
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaAEliminar));
        when(videojuegoRepository.buscarVideoJuegos(1)).thenReturn(Collections.emptyList());

    
        String resultado = categoriaService.eliminar(1);


        assertNotNull(resultado);
        assertTrue(resultado.contains("Eliminada exitosamente"));
        verify(categoriaRepository, times(1)).delete(categoriaAEliminar);
    }

    @Test
    public void testEliminar_ConJuegosAsociados_LanzaExcepcion() {
        
        Categoria categoriaAEliminar = createCategoria();
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaAEliminar));
        
        when(videojuegoRepository.buscarVideoJuegos(1)).thenReturn(List.of(new VideoJuego())); 

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoriaService.eliminar(1);
        });

        assertEquals("La categoria no se puede eliminar, tiene juegos asociados", exception.getMessage());
        
        verify(categoriaRepository, never()).delete(any(Categoria.class));
    }
}
