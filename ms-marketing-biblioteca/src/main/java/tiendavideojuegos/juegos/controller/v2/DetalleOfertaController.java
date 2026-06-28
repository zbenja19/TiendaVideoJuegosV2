package tiendavideojuegos.juegos.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tiendavideojuegos.juegos.DTO.DetalleOfertaDTO;
import tiendavideojuegos.juegos.assemblers.DetalleOfertaModelAssembler;
import tiendavideojuegos.juegos.service.DetalleOfertaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("detalleOfertaControllerV2")
@RequestMapping("/api/v2/detalle-ofertas")
public class DetalleOfertaController {

    @Autowired
    private DetalleOfertaService detalleOfertaService;

    @Autowired
    private DetalleOfertaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<DetalleOfertaDTO>>> listarTodos() {
        List<EntityModel<DetalleOfertaDTO>> detalleOfertas = detalleOfertaService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (detalleOfertas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                detalleOfertas,
                linkTo(methodOn(DetalleOfertaController.class).listarTodos()).withSelfRel()
        ));
    }
}