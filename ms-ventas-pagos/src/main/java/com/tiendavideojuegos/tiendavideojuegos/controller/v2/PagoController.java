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

import com.tiendavideojuegos.tiendavideojuegos.DTO.PagoDTO;
import com.tiendavideojuegos.tiendavideojuegos.assemblers.PagoModelAssembler;
import com.tiendavideojuegos.tiendavideojuegos.model.Pago;
import com.tiendavideojuegos.tiendavideojuegos.service.PagoService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("PagoControllerV2")
@RequestMapping("/api/v2/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Autowired PagoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PagoDTO>>> todosLosPagos(){
        List<EntityModel<PagoDTO>> pagos = pagoService.obtenerTodos().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        if (pagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                pagos,
                linkTo(methodOn(PagoController.class).todosLosPagos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PagoDTO>> buscarPorId(@PathVariable Integer idPago) {
        try {
            PagoDTO pagoDTO = pagoService.buscarPorId(idPago);
            if (pagoDTO == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(pagoDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PagoDTO>> agregarPago(@Valid @RequestBody Pago pago) {
        try {
            PagoDTO guardado = pagoService.guardar(pago);
            return ResponseEntity
                    .created(linkTo(methodOn(PagoController.class).buscarPorId(guardado.getIdPago())).toUri())
                    .body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
