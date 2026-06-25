package tiendavideojuegos.juegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import tiendavideojuegos.juegos.DTO.OfertaDTO;
import tiendavideojuegos.juegos.model.Oferta;
import tiendavideojuegos.juegos.repository.OfertaRepository;

@SpringBootTest
public class OfertaServiceTest {

    @Autowired
    private OfertaService ofertaService;

    @MockitoBean
    private OfertaRepository ofertaRepository;

    private Oferta createOferta() {
        Oferta oferta = new Oferta();
        oferta.setIdOferta(1);
        oferta.setDescuento(15.0);
        oferta.setFechaInicio(LocalDate.now());
        oferta.setFechaTermino(LocalDate.now().plusDays(10));
        return oferta;
    }

    @Test
    public void testObtenerTodos() {
        when(ofertaRepository.findAll()).thenReturn(List.of(createOferta()));

        List<OfertaDTO> resultado = ofertaService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getIdOferta());
        assertEquals(15.0, resultado.get(0).getDescuento());
    }

    @Test
    public void testBuscarPorId() {
        when(ofertaRepository.findById(1)).thenReturn(Optional.of(createOferta()));

        OfertaDTO resultado = ofertaService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdOferta());
    }

    @Test
    public void testGuardar() {
        Oferta ofertaParaGuardar = createOferta();
        when(ofertaRepository.save(any(Oferta.class))).thenReturn(ofertaParaGuardar);

        OfertaDTO resultado = ofertaService.guardar(ofertaParaGuardar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getIdOferta());
        assertEquals(15.0, resultado.getDescuento());
    }
}