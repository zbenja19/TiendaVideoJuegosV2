package tiendavideojuegos.juegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tiendavideojuegos.juegos.DTO.DetalleOfertaDTO;
import tiendavideojuegos.juegos.model.DetalleOfertas;
import tiendavideojuegos.juegos.repository.DetalleOfertaRepository;

@SpringBootTest
public class DetalleOfertaServiceTest {

    @Autowired
    private DetalleOfertaService detalleOfertaService;

    @MockitoBean
    private DetalleOfertaRepository detalleOfertaRepository;

    private DetalleOfertas createDetalleOferta() {
        DetalleOfertas detalle = new DetalleOfertas();
        detalle.setIdDetalleOferta(1);
        return detalle;
    }

    @Test
    public void testObtenerTodos() {
        when(detalleOfertaRepository.findAll()).thenReturn(List.of(createDetalleOferta()));

        List<DetalleOfertaDTO> resultado = detalleOfertaService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdDetalleOferta());
    }

    @Test
    public void testBuscarPorId() {
        when(detalleOfertaRepository.findById(1)).thenReturn(Optional.of(createDetalleOferta()));

        DetalleOfertaDTO resultado = detalleOfertaService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDetalleOferta());
    }

    @Test
    public void testGuardar() {
        DetalleOfertas detalleParaGuardar = createDetalleOferta();
        when(detalleOfertaRepository.save(any(DetalleOfertas.class))).thenReturn(detalleParaGuardar);

        DetalleOfertaDTO resultado = detalleOfertaService.guardar(detalleParaGuardar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdDetalleOferta());
    }
}