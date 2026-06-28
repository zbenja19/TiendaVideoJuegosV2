package com.tiendavideojuegos.tiendavideojuegos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 
import com.tiendavideojuegos.tiendavideojuegos.DTO.PedidosDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;
import com.tiendavideojuegos.tiendavideojuegos.model.Pedidos;
import com.tiendavideojuegos.tiendavideojuegos.repository.ClientesRepository;
import com.tiendavideojuegos.tiendavideojuegos.repository.PedidosRepository;
 
@SpringBootTest
public class PedidosServiceTest {
 
    @Autowired
    private PedidosService pedidosservice;
 
    @MockitoBean
    private PedidosRepository pedidosrepository;
 
    @MockitoBean
    private ClientesRepository clientesrepository;
 
    private Pedidos createPedidos(LocalDate fechaAgregada, Double montoTotal, Boolean estado, Clientes cliente){
        Pedidos pedidos = new Pedidos();
        pedidos.setId(1);
        pedidos.setFechaAgregada(fechaAgregada);
        pedidos.setMontoTotal(montoTotal);
        pedidos.setEstado(estado);
        pedidos.setCliente(cliente);
        return pedidos;
    }
 
    private Clientes createCliente(Integer id){
        Clientes cliente = new Clientes();
        cliente.setId(id);
        return cliente;
    }
 
    private PedidosDTO createPedidosDTO(Integer clienteId, LocalDate fechaAgregada, Double montoTotal, Boolean estado){
        PedidosDTO dto = new PedidosDTO();
        dto.setClienteId(clienteId);
        dto.setFechaAgregada(fechaAgregada);
        dto.setMontoTotal(montoTotal);
        dto.setEstado(estado);
        return dto;
    }
 
    
 
    @Test
    public void testObtenerTodos(){
        Clientes cliente = createCliente(5);
        when(pedidosrepository.findAll()).thenReturn(List.of(createPedidos(LocalDate.of(2026, 12, 21), 60000.0, true, cliente)));
 
        List<PedidosDTO> resultado = pedidosservice.obtenerTodos();
 
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
 
    
 
    @Test
    public void testBuscarPorId_existente(){
        Clientes cliente = createCliente(5);
        Pedidos pedido = createPedidos(LocalDate.of(2026, 12, 21), 60000.0, true, cliente);
        when(pedidosrepository.findById(1)).thenReturn(Optional.of(pedido));
 
        PedidosDTO resultado = pedidosservice.buscarPorId(1);
 
        assertNotNull(resultado);
        assertEquals(5, resultado.getClienteId());
        assertEquals(60000.0, resultado.getMontoTotal());
    }
 
    @Test
    public void testBuscarPorId_noExistente(){
        when(pedidosrepository.findById(99)).thenReturn(Optional.empty());
 
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pedidosservice.buscarPorId(99);
        });
 
        assertEquals("¡Pedido no encontrado!", ex.getMessage());
    }
 
   
 
    @Test
    public void testGuardarDTO_clienteIdNulo(){
        PedidosDTO dto = createPedidosDTO(null, LocalDate.now(), 1000.0, true);
 
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pedidosservice.guardar(dto);
        });
 
        assertEquals("El clienteId es obligatorio para guardar un pedido", ex.getMessage());
        verify(clientesrepository, never()).findById(any());
    }
 
    @Test
    public void testGuardarDTO_clienteNoEncontrado(){
        PedidosDTO dto = createPedidosDTO(5, LocalDate.now(), 1000.0, true);
        when(clientesrepository.findById(5)).thenReturn(Optional.empty());
 
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pedidosservice.guardar(dto);
        });
 
        assertEquals("Cliente no encontrado", ex.getMessage());
        verify(pedidosrepository, never()).save(any());
    }
 
    @Test
    public void testGuardarDTO_valido(){
        Clientes cliente = createCliente(5);
        PedidosDTO dto = createPedidosDTO(5, LocalDate.of(2026, 12, 21), 60000.0, true);
 
        when(clientesrepository.findById(5)).thenReturn(Optional.of(cliente));
        when(pedidosrepository.save(any(Pedidos.class))).thenAnswer(invocation -> invocation.getArgument(0));
 
        PedidosDTO resultado = pedidosservice.guardar(dto);
 
        assertNotNull(resultado);
        assertEquals(5, resultado.getClienteId());
        assertEquals(60000.0, resultado.getMontoTotal());
        assertEquals(true, resultado.getEstado());
        verify(pedidosrepository, times(1)).save(any(Pedidos.class));
    }
 
    @Test
    public void testGuardarDTO_estadoNuloAsignaFalse(){
        Clientes cliente = createCliente(5);
        PedidosDTO dto = createPedidosDTO(5, LocalDate.now(), 1000.0, null);
 
        when(clientesrepository.findById(5)).thenReturn(Optional.of(cliente));
        when(pedidosrepository.save(any(Pedidos.class))).thenAnswer(invocation -> invocation.getArgument(0));
 
        PedidosDTO resultado = pedidosservice.guardar(dto);
 
        assertFalse(resultado.getEstado());
    }
 
  
 
    @Test
    public void testGuardarPedidos_estadoNuloAsignaFalse(){
        Pedidos pedido = createPedidos(LocalDate.now(), 1000.0, null, null);
 
        when(pedidosrepository.save(any(Pedidos.class))).thenAnswer(invocation -> invocation.getArgument(0));
 
        Pedidos resultado = pedidosservice.guardar(pedido);
 
        assertNotNull(resultado);
        assertFalse(resultado.getEstado());
    }
 
   
 
    @Test
    public void testBuscarPorEstado(){
        Clientes cliente = createCliente(5);
        when(pedidosrepository.findByEstado(true)).thenReturn(List.of(createPedidos(LocalDate.now(), 2000.0, true, cliente)));
 
        List<PedidosDTO> resultado = pedidosservice.buscarPorEstado(true);
 
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }
 
    
 
    @Test
    public void testGastosTotalesCliente_conValor(){
        when(pedidosrepository.moneycalculeitor(5)).thenReturn(15000.0);
 
        Double resultado = pedidosservice.gastosTotalescliente(5);
 
        assertEquals(15000.0, resultado);
    }
 
    @Test
    public void testGastosTotalesCliente_nulo(){
        when(pedidosrepository.moneycalculeitor(5)).thenReturn(null);
 
        Double resultado = pedidosservice.gastosTotalescliente(5);
 
        assertEquals(0.0, resultado);
    }
 
    
 
    @Test
    public void testAlternarEstado_existente(){
        Pedidos pedido = createPedidos(LocalDate.now(), 1000.0, true, null);
        when(pedidosrepository.findById(1)).thenReturn(Optional.of(pedido));
 
        pedidosservice.alternarEstado(1);
 
        assertFalse(pedido.getEstado());
        verify(pedidosrepository, times(1)).save(pedido);
    }
 
    @Test
    public void testAlternarEstado_noExistente(){
        when(pedidosrepository.findById(99)).thenReturn(Optional.empty());
 
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            pedidosservice.alternarEstado(99);
        });
 
        assertEquals("No se encontro el pedido para cambiar estado", ex.getMessage());
    }
}
