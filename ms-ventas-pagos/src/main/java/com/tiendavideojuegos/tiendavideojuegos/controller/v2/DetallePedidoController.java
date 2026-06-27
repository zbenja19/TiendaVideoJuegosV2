package com.tiendavideojuegos.tiendavideojuegos.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiendavideojuegos.tiendavideojuegos.DTO.DetallePedidoDTO;
import com.tiendavideojuegos.tiendavideojuegos.assemblers.DetallePedidoModelAssembler;
import com.tiendavideojuegos.tiendavideojuegos.model.DetallePedido;
import com.tiendavideojuegos.tiendavideojuegos.service.DetallePedidoService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("DetallePedidoControllerV2")
@RequestMapping("/api/v2/detalle-pedido")
public class DetallePedidoController {

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    private DetallePedidoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<DetallePedidoDTO>>> todosLosPedidos() {
        List<EntityModel<DetallePedidoDTO>> detalles = detallePedidoService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (detalles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                detalles,
                linkTo(methodOn(DetallePedidoController.class).todosLosPedidos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetallePedidoDTO>> buscarPorId(@PathVariable Integer id) {
        try {
            DetallePedidoDTO dto = detallePedidoService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<DetallePedidoDTO>> agregarDetallePedido(@Valid @RequestBody DetallePedido detallePedido) {
        try {
            DetallePedidoDTO guardado = detallePedidoService.guardar(detallePedido);
            return ResponseEntity
                    .created(linkTo(methodOn(DetallePedidoController.class).buscarPorId(guardado.getIdDetallePedido())).toUri())
                    .body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}