package com.tiendavideojuegos.tiendavideojuegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tiendavideojuegos.tiendavideojuegos.DTO.ClientesDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;
import com.tiendavideojuegos.tiendavideojuegos.repository.ClientesRepository;

@SpringBootTest
public class ClientesServiceTest {

    @Autowired
    private ClientesService clientesService;

    @MockitoBean
    private ClientesRepository clientesRepository;

    private Clientes createClientes() {
        Clientes cliente = new Clientes();
        cliente.setId(1);
        cliente.setNombre("Juan Perez");
        cliente.setCorreo("juan@correo.com");
        cliente.setTelefono("912345678");
        cliente.setContrasena("123456");
        return cliente;
    }

    @Test
    public void testObtenerTodos() {
        when(clientesRepository.findAll()).thenReturn(List.of(createClientes()));

        List<ClientesDTO> resultado = clientesService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
        assertEquals("Juan Perez", resultado.get(0).getNombre());
    }

    @Test
    public void testBuscarPorId() {
        when(clientesRepository.findById(1)).thenReturn(Optional.of(createClientes()));

        ClientesDTO resultado = clientesService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    public void testGuardar() {
        Clientes clienteParaGuardar = createClientes();
        when(clientesRepository.save(any(Clientes.class))).thenReturn(clienteParaGuardar);

        ClientesDTO resultado = clientesService.guardar(clienteParaGuardar);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Perez", resultado.getNombre());
    }

    @Test
    public void testActualizarCliente() {
        Clientes clienteExistente = createClientes();
        ClientesDTO datosNuevos = new ClientesDTO();
        datosNuevos.setNombre("Carlos Gomez");
        datosNuevos.setCorreo("carlos@correo.com");

        when(clientesRepository.findById(1)).thenReturn(Optional.of(clienteExistente));
        when(clientesRepository.save(any(Clientes.class))).thenReturn(clienteExistente);

        ClientesDTO resultado = clientesService.actualizarCliente(1, datosNuevos);

        assertNotNull(resultado);
        assertEquals("Carlos Gomez", resultado.getNombre());
        assertEquals("carlos@correo.com", resultado.getCorreo());
    }
}