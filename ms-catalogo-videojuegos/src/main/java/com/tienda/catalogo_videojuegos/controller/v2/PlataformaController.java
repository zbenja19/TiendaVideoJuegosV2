package com.tienda.catalogo_videojuegos.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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
import jakarta.validation.Valid;

import com.tienda.catalogo_videojuegos.DTO.PlataformaDTO;
import com.tienda.catalogo_videojuegos.assemblers.PlataformaModelAssembler;
import com.tienda.catalogo_videojuegos.model.Plataforma;
import com.tienda.catalogo_videojuegos.service.PlataformaService;


@RestController("plataformaControllerV2")
@RequestMapping("/api/v2/plataformas")
public class PlataformaController {

    @Autowired
    private PlataformaService plataformaService;

    @Autowired
    private PlataformaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<PlataformaDTO>>> listar() {
        List<EntityModel<PlataformaDTO>> plataformas = plataformaService.obtenerTodas().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (plataformas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                plataformas,
                linkTo(methodOn(PlataformaController.class).listar()).withSelfRel()

                )   
            
            );
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PlataformaDTO>> buscarPorId(@Valid @PathVariable Integer id) {
        try {
           PlataformaDTO plataformaDTO = plataformaService.buscarPorId(id);

            return ResponseEntity.ok(assembler.toModel(plataformaDTO));
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PlataformaDTO>> guardar(@Valid @RequestBody Plataforma plataforma) {
        try {
            PlataformaDTO plataformaGuardada = plataformaService.guardar(plataforma);
            return ResponseEntity
                    .created(linkTo(methodOn(PlataformaController.class)
                    .buscarPorId(plataformaGuardada.getIdPlataforma())).toUri())
                    .body(assembler.toModel(plataformaGuardada));


        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PlataformaDTO>> actualizar (@PathVariable Integer id,@Valid  @RequestBody Plataforma plataforma){
        try {
            PlataformaDTO actualizada = plataformaService.actualizarPlataforma(id, plataforma);
            return ResponseEntity.ok(assembler.toModel(actualizada));
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

   
}
