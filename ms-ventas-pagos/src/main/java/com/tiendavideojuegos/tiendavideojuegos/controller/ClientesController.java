package com.tiendavideojuegos.tiendavideojuegos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendavideojuegos.tiendavideojuegos.DTO.ClientesDTO;
import com.tiendavideojuegos.tiendavideojuegos.service.ClientesService;

@RestController
@RequestMapping("/api/v1/Clientes")
public class ClientesController {
    
    @Autowired
    private ClientesService clientesservice;

    @GetMapping
    public List<ClientesDTO> listarTodos() {
        return clientesservice.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientesDTO> obtenerPorId(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(clientesservice.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ClientesDTO> crearCliente(@RequestBody ClientesDTO Clientes) {
        return ResponseEntity.ok(clientesservice.guardar(Clientes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientesDTO> actualizar(@PathVariable Integer id, @RequestBody ClientesDTO datosNuevos) {
        try {
            return ResponseEntity.ok(clientesservice.actualizarCliente(id, datosNuevos));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    
}