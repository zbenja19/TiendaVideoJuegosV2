package tiendavideojuegos.juegos.controller.v2;

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

import tiendavideojuegos.juegos.DTO.OfertaDTO;
import tiendavideojuegos.juegos.model.Oferta;
import tiendavideojuegos.juegos.assemblers.OfertaModelAssembler;
import tiendavideojuegos.juegos.service.OfertaService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("ofertaControllerV2")
@RequestMapping("/api/v2/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @Autowired
    private OfertaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<OfertaDTO>>> listarTodos() {
        List<EntityModel<OfertaDTO>> ofertas = ofertaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (ofertas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                ofertas,
                linkTo(methodOn(OfertaController.class).listarTodos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<OfertaDTO>> obtenerPorId(@PathVariable("id") Integer idOferta) {
        try {
            OfertaDTO ofertaDTO = ofertaService.buscarPorId(idOferta);
            if (ofertaDTO == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(ofertaDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<OfertaDTO>> crearOferta(@Valid @RequestBody Oferta oferta) {
        try {
            OfertaDTO guardado = ofertaService.guardar(oferta);
            return ResponseEntity
                    .created(linkTo(methodOn(OfertaController.class).obtenerPorId(guardado.getIdOferta())).toUri())
                    .body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}