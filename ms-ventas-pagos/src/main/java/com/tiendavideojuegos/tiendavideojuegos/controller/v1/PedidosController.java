package com.tiendavideojuegos.tiendavideojuegos.controller.v1;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PedidosDTO;
import com.tiendavideojuegos.tiendavideojuegos.service.PedidosService;

@RestController("PedidosControllerV1")
@RequestMapping("/api/v1/Pedidos")
public class PedidosController {
    @Autowired
    private PedidosService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidosDTO>> obtenerTodos() {
        List<PedidosDTO> pedidos = pedidoService.obtenerTodos();
        if (pedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/cliente/{clienteId}/total")
    public ResponseEntity<Double> obtenerTotalCliente(@PathVariable Integer clienteId) {
    Double total = pedidoService.gastosTotalescliente(clienteId); 
    return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidosDTO> buscarPorId(@PathVariable Integer id) {
        try {
            PedidosDTO dto = pedidoService.buscarPorId(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PedidosDTO> guardar(@RequestBody PedidosDTO pedidos) {
        try {
            PedidosDTO guardado = pedidoService.guardar(pedidos);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> alternarEstado(@PathVariable Integer id) {
        try {
            pedidoService.alternarEstado(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
}