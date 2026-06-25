package com.tienda.catalogo_videojuegos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.tienda.catalogo_videojuegos.DTO.ProveedorDTO;
import com.tienda.catalogo_videojuegos.model.Proveedor;
import com.tienda.catalogo_videojuegos.repository.ProveedorRepository;

@SpringBootTest
class ProveedorServiceTest {

    @Autowired
    private ProveedorService proveedorService;

    @MockitoBean
    private ProveedorRepository proveedorRepository;

    private Proveedor createProveedor() {
        Proveedor prov = new Proveedor();
        prov.setIdProveedor(1);
        prov.setNombre("Distribuidora Máxima");
        prov.setEmail("contacto@maxima.cl");
        prov.setTelefono("+56912345678");
        return prov;
    }

    @Test
    void testListarTodos() {
        // Given
        when(proveedorRepository.findAll()).thenReturn(List.of(createProveedor()));

        // When
        List<ProveedorDTO> resultado = proveedorService.listarTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Distribuidora Máxima", resultado.get(0).getNombre());
    }

    @Test
    void testGuardarProveedor() {
        // Given
        Proveedor prov = createProveedor();
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(prov);

        // When
        Proveedor resultado = proveedorService.guardarProveedor(prov);

        // Then
        assertNotNull(resultado);
        assertEquals("Distribuidora Máxima", resultado.getNombre());
    }

    @Test
    void testEliminar_Exitoso() {
        // Given
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(createProveedor()));
        doNothing().when(proveedorRepository).delete(any(Proveedor.class));

        // When
        String resultado = proveedorService.eliminar(1);

        // Then
        assertEquals("El proveedor 'Distribuidora Máxima' ha sido eliminado exitosamente", resultado);
    }

    @Test
    void testBuscarPorId() {
        // Given
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(createProveedor()));

        // When
        ProveedorDTO resultado = proveedorService.buscarPorId(1);

        // Then
        assertNotNull(resultado);
        assertEquals("Distribuidora Máxima", resultado.getNombre());
    }

    @Test
    void testActualizarProveedor() {
        // Given
        Proveedor existente = createProveedor();
        Proveedor nvoProveedor = new Proveedor();
        nvoProveedor.setNombre("Máxima SpA");

        when(proveedorRepository.findById(1)).thenReturn(Optional.of(existente));
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(existente);

        // When
        ProveedorDTO resultado = proveedorService.actualizarProveedor(1, nvoProveedor);

        // Then
        assertNotNull(resultado);
        assertEquals("Máxima SpA", resultado.getNombre());
    }
}