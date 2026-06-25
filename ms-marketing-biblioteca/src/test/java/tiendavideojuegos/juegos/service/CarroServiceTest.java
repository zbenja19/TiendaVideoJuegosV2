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

import tiendavideojuegos.juegos.model.Carro;
import tiendavideojuegos.juegos.repository.CarritoRepository;

@SpringBootTest
public class CarroServiceTest {

    @Autowired
    private CarroService carroService;

    @MockitoBean
    private CarritoRepository carroRepository;

    private Carro createCarro() {
        Carro carro = new Carro();
        carro.setId(1);
        carro.setCantidad(2);
        carro.setFechaAgregada(LocalDate.now());
        return carro;
    }

    @Test
    public void testObtenerTodo() {
        when(carroRepository.findAll()).thenReturn(List.of(createCarro()));

        List<Carro> resultado = carroService.obtenerTodo();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(2, resultado.get(0).getCantidad());
    }

    @Test
    public void testBuscarPorId() {
        when(carroRepository.findById(1)).thenReturn(Optional.of(createCarro()));

        Carro resultado = carroService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    public void testGuardar() {
        Carro carroParaGuardar = createCarro();
        when(carroRepository.save(any(Carro.class))).thenReturn(carroParaGuardar);

        Carro resultado = carroService.guardar(carroParaGuardar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(2, resultado.getCantidad());
    }

    @Test
    public void testActualizar() {
        Carro carroExistente = createCarro();
        Carro datosNuevos = new Carro();
        datosNuevos.setCantidad(5);
        LocalDate nuevaFecha = LocalDate.now().plusDays(2);
        datosNuevos.setFechaAgregada(nuevaFecha);

        when(carroRepository.findById(1)).thenReturn(Optional.of(carroExistente));
        when(carroRepository.save(any(Carro.class))).thenReturn(carroExistente);

        Carro resultado = carroService.actualizar(1, datosNuevos);

        assertNotNull(resultado);
        assertEquals(5, resultado.getCantidad());
        assertEquals(nuevaFecha, resultado.getFechaAgregada());
    }

    @Test
    public void testEliminar_Exitoso() {
        Carro carroAEliminar = createCarro();
        when(carroRepository.findById(1)).thenReturn(Optional.of(carroAEliminar));
        doNothing().when(carroRepository).delete(any(Carro.class));

        String resultado = carroService.eliminar(1);

        assertEquals("Producto eliminado del carrito exitosamente.", resultado);
        verify(carroRepository, times(1)).delete(any(Carro.class));
    }
}