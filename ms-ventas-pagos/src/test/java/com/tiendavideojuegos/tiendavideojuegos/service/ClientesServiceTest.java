package com.tiendavideojuegos.tiendavideojuegos.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tiendavideojuegos.tiendavideojuegos.DTO.ClientesDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;
import com.tiendavideojuegos.tiendavideojuegos.repository.ClientesRepository;

@SpringBootTest
public class ClientesServiceTest {

    @Autowired 
    private ClientesService clientesservice;

    @MockitoBean
    private ClientesRepository clientesrepository;

    @MockitoBean
    private ClienteValidaciones clienteValidaciones;

    private Clientes createClientes(String nombre, String correo, String telefono){
        Clientes clientes = new Clientes();
        clientes.setId(1);
        clientes.setNombre(nombre);
        clientes.setCorreo(correo);
        clientes.setTelefono(telefono);
        return clientes;
    }

    @Test
    public void testObtenerTodos(){
        when(clientesrepository.findAll()).thenReturn(List.of(createClientes("Juan Mondaca", "juanMondaca@gmail.com", "123456789")));

        List<ClientesDTO> resultado = clientesservice.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testBuscarPorId_existente() {
        Clientes cliente = createClientes("Juan Mondaca", "juanMondaca@gmail.com", "123456789");
        when(clientesrepository.findById(1)).thenReturn(Optional.of(cliente));
 
        ClientesDTO resultado = clientesservice.buscarPorId(1);
 
        assertNotNull(resultado);
        assertEquals("Juan Mondaca", resultado.getNombre());
        assertEquals("juanMondaca@gmail.com", resultado.getCorreo());
    }

    
    @Test
    public void testGuardar_valido() {
        Clientes nuevoCliente = createClientes("Pedro Soto", "pedroSoto@gmail.com", "987654321");
 
        when(clienteValidaciones.validarNullVacio(any(Clientes.class))).thenReturn(true);
        when(clientesrepository.save(any(Clientes.class))).thenReturn(nuevoCliente);
 
        ClientesDTO resultado = clientesservice.guardar(nuevoCliente);
 
        assertNotNull(resultado);
        assertEquals("Pedro Soto", resultado.getNombre());
        verify(clientesrepository, times(1)).save(nuevoCliente);
    }

    @Test
    public void testGuardar_invalido() {
        Clientes clienteInvalido = createClientes(null, null, null);
 
        when(clienteValidaciones.validarNullVacio(any(Clientes.class))).thenReturn(false);
 
        ClientesDTO resultado = clientesservice.guardar(clienteInvalido);
 
        assertNull(resultado);
        verify(clientesrepository, never()).save(any(Clientes.class));
    }

    @Test
    public void testActualizarCliente_existente() {
        Clientes clienteExistente = createClientes("Juan Mondaca", "juanMondaca@gmail.com", "123456789");
 
        ClientesDTO datosNuevos = new ClientesDTO();
        datosNuevos.setNombre("Juan Actualizado");
        datosNuevos.setCorreo("juanIgnacioMondaca@gmail.com");
 
        when(clientesrepository.findById(1)).thenReturn(Optional.of(clienteExistente));
        when(clientesrepository.save(any(Clientes.class))).thenReturn(clienteExistente);
 
        ClientesDTO resultado = clientesservice.actualizarCliente(1, datosNuevos);
 
        assertNotNull(resultado);
        assertEquals("Juan Actualizado", resultado.getNombre());
        assertEquals("juanIgnacioMondaca@gmail.com", resultado.getCorreo());
    }

     @Test
    public void testActualizarCliente_noExistente() {
        ClientesDTO datosNuevos = new ClientesDTO();
        datosNuevos.setNombre("No importa");
 
        when(clientesrepository.findById(99)).thenReturn(Optional.empty());
 
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clientesservice.actualizarCliente(99, datosNuevos);
        });
 
        assertEquals("¡El cliente no existe!", ex.getMessage());
    }
}
