package com.tiendavideojuegos.tiendavideojuegos.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PedidosDTO;
import com.tiendavideojuegos.tiendavideojuegos.assemblers.PedidosModelAssembler;
import com.tiendavideojuegos.tiendavideojuegos.service.PedidosService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("PedidosControllerV2")
@RequestMapping("/api/v2/Pedidos")
public class PedidosController {

    @Autowired
    private PedidosService pedidoService;

    @Autowired
    private PedidosModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PedidosDTO>>> obtenerTodos() {
        List<EntityModel<PedidosDTO>> pedidos = pedidoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                pedidos,
                linkTo(methodOn(PedidosController.class).obtenerTodos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/cliente/{clienteId}/total", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Double> obtenerTotalCliente(@PathVariable Integer clienteId) {
        Double total = pedidoService.gastosTotalescliente(clienteId);
        return ResponseEntity.ok(total);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PedidosDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            PedidosDTO dto = pedidoService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PedidosDTO>> guardar(@Valid @RequestBody PedidosDTO pedidos) {
        try {
            PedidosDTO guardado = pedidoService.guardar(pedidos);
            return ResponseEntity
                    .created(linkTo(methodOn(PedidosController.class).buscarPorId(guardado.getId())).toUri())
                    .body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/{id}/estado", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<Void> alternarEstado(@PathVariable Integer id) {
        try {
            pedidoService.alternarEstado(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}