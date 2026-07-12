package com.tienda.catalogo_videojuegos.controller.v2;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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

import com.tienda.catalogo_videojuegos.DTO.VideoJuegoDTO;
import com.tienda.catalogo_videojuegos.model.VideoJuego;
import com.tienda.catalogo_videojuegos.service.VideoJuegoService;
import com.tienda.catalogo_videojuegos.assemblers.VideoJuegoModelAssembler;


@RestController("videoJuegoControllerV2")
@RequestMapping("/api/v2/videojuegos")
public class VideoJuegoController {

    @Autowired
    private VideoJuegoService videoJuegoService;

    @Autowired
    private VideoJuegoModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<VideoJuegoDTO>>> listar(){
        List<EntityModel<VideoJuegoDTO>> videoJuegos = videoJuegoService.listarTodos().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (videoJuegos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                videoJuegos,
                linkTo(methodOn(VideoJuegoController.class).listar()).withSelfRel()
                )
            
            );
    }

    @GetMapping (value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<VideoJuegoDTO>> buscarPorId (@PathVariable Integer id){
        try {
            VideoJuegoDTO videoJuegoDTO = videoJuegoService.buscarPorId(id);

            return ResponseEntity.ok(assembler.toModel(videoJuegoDTO ));

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<VideoJuegoDTO>> guardar(@Valid @RequestBody VideoJuego videoJuego){
        try {
            VideoJuegoDTO videoJuegoGuardado = videoJuegoService.guardar(videoJuego);
            return ResponseEntity
                    .created(linkTo(methodOn(VideoJuegoController.class)
                    .buscarPorId(videoJuegoGuardado.getIdVideoJuego())).toUri())
                    .body(assembler.toModel(videoJuegoGuardado));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<VideoJuegoDTO>> actualizar (@PathVariable Integer id,@Valid @RequestBody VideoJuego videoJuego){
        try {
            VideoJuegoDTO actualizado = videoJuegoService.actualizarVideoJuego(id, videoJuego);
            return ResponseEntity.ok(assembler.toModel(actualizado));
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Integer id){
        try {
            String mensajeEliminar = videoJuegoService.eliminar(id);
            return ResponseEntity.ok(mensajeEliminar);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
