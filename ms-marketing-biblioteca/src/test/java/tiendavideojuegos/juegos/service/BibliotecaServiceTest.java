package tiendavideojuegos.juegos.service;

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


import tiendavideojuegos.juegos.model.Biblioteca;
import tiendavideojuegos.juegos.repository.BibliotecaRepository;

@SpringBootTest
public class BibliotecaServiceTest {

    @Autowired
    private BibliotecaService bibliotecaService;

    @MockitoBean
    private BibliotecaRepository bibliotecaRepository;

    private Biblioteca createBiblioteca() {
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setId(1);
        biblioteca.setFechaAgregada(LocalDate.now());
        return biblioteca;
    }

    @Test
    public void testObtenerTodas() {
        when(bibliotecaRepository.findAll()).thenReturn(List.of(createBiblioteca()));
        
        List<Biblioteca> resultado = bibliotecaService.obtenerTodas();
        
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testBuscarPorId() {
        when(bibliotecaRepository.findById(1)).thenReturn(Optional.of(createBiblioteca()));
        
        Biblioteca resultado = bibliotecaService.buscarPorId(1);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    public void testGuardar() {
        Biblioteca biblioteca = createBiblioteca();
        when(bibliotecaRepository.save(any(Biblioteca.class))).thenReturn(biblioteca);
        
        Biblioteca resultado = bibliotecaService.guardar(biblioteca);
        
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    public void testActualizar() {
        Biblioteca existingBiblioteca = createBiblioteca();
        Biblioteca patchData = new Biblioteca();
        LocalDate nuevaFecha = LocalDate.now().plusDays(5);
        patchData.setFechaAgregada(nuevaFecha);

        when(bibliotecaRepository.findById(1)).thenReturn(Optional.of(existingBiblioteca));
        when(bibliotecaRepository.save(any(Biblioteca.class))).thenReturn(existingBiblioteca);

        Biblioteca resultado = bibliotecaService.actualizar(1, patchData);
        
        assertNotNull(resultado);
        assertEquals(nuevaFecha, resultado.getFechaAgregada());
    }

    @Test
    public void testEliminar() {
        Biblioteca biblioteca = createBiblioteca();
        when(bibliotecaRepository.findById(1)).thenReturn(Optional.of(biblioteca));
        doNothing().when(bibliotecaRepository).delete(any(Biblioteca.class));

        String resultado = bibliotecaService.eliminar(1);
        
        assertEquals("Biblioteca eliminada exitosamente.", resultado);
        verify(bibliotecaRepository, times(1)).delete(any(Biblioteca.class));
    }
}