package com.tienda.catalogo_videojuegos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tienda.catalogo_videojuegos.DTO.ProveedorDTO;
import com.tienda.catalogo_videojuegos.model.Proveedor;
import com.tienda.catalogo_videojuegos.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/proveedores")
@Tag(name = "Proveedores",description = "Operaciones para la gestion de proveedores")
public class ProveedorController {
    
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary="Obtiene una lista" , description="Retorna una lista de todos los proveedores")
    @ApiResponse(responseCode="200", description= "Lista de proveedores obtenida correctamente")
    @ApiResponse(responseCode="204", description= "No existen proveedores registrados")
    public ResponseEntity <List<ProveedorDTO>> listar(){
        List<ProveedorDTO> proveedores = proveedorService.listarTodos();
        if (proveedores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    @Operation(summary = "Buscar proveedor por id", description = "Retorna un proveedor segun su id")
    @ApiResponse(responseCode = "200", description = "Proveedor encontrado")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    public ResponseEntity<?> buscarPorId (@PathVariable Integer id){
        try {
            ProveedorDTO proveedorDTO = proveedorService.buscarPorId(id);
            return new ResponseEntity<>(proveedorDTO,HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear proveedor", description = "Crea un nuevo proveedor")
    @ApiResponse(responseCode = "201", description = "Proveedor creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    public ResponseEntity<?> guardar(@RequestBody Proveedor proveedor){
        try {
            Proveedor proveedorGuardado = proveedorService.guardarProveedor(proveedor);
            return new ResponseEntity<>(proveedorGuardado,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar:" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza proveedor", description = "Actualiza el proveedor existente")
    @ApiResponse(responseCode = "200", description = "Actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    public ResponseEntity<?> actualizar (@PathVariable Integer id, @RequestBody Proveedor nvoProveedor){
        try {
            ProveedorDTO actualizado = proveedorService.actualizarProveedor(id, nvoProveedor);
            return new ResponseEntity<>(actualizado,HttpStatus.OK);
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina proveedor", description = "Elimina el proveedor por id")
    @ApiResponse(responseCode = "200", description = "Proveedor eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    public ResponseEntity<?>eliminar (@PathVariable Integer id){
        try {
            String mensajeEliminar = proveedorService.eliminar(id);
            return new ResponseEntity<>(mensajeEliminar,HttpStatus.OK);

        } catch (RuntimeException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
