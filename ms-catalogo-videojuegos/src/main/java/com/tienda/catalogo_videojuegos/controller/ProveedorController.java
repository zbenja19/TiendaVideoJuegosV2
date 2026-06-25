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

@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {
    
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public List<ProveedorDTO> listar(){
        return proveedorService.listarTodos();
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> buscarPorId (@PathVariable Integer id){
        try {
            ProveedorDTO proveedorDTO = proveedorService.buscarPorId(id);
            return new ResponseEntity<>(proveedorDTO,HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Proveedor proveedor){
        try {
            Proveedor proveedorGuardado = proveedorService.guardarProveedor(proveedor);
            return new ResponseEntity<>(proveedorGuardado,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar:" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar (@PathVariable Integer id, @RequestBody Proveedor nvoProveedor){
        try {
            ProveedorDTO actualizado = proveedorService.actualizarProveedor(id, nvoProveedor);
            return new ResponseEntity<>(actualizado,HttpStatus.OK);
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminar (@PathVariable Integer id){
        try {
            String mensajeEliminar = proveedorService.eliminar(id);
            return new ResponseEntity<>(mensajeEliminar,HttpStatus.OK);

        } catch (RuntimeException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
