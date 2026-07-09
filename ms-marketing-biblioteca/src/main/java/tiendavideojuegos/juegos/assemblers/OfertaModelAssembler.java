package tiendavideojuegos.juegos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import tiendavideojuegos.juegos.DTO.OfertaDTO;
import tiendavideojuegos.juegos.controller.v2.OfertaController;

@Component
public class OfertaModelAssembler implements RepresentationModelAssembler<OfertaDTO, EntityModel<OfertaDTO>> {

    @Override
    public EntityModel<OfertaDTO> toModel(OfertaDTO ofertaDTO) {
        return EntityModel.of(ofertaDTO,
            linkTo(methodOn(OfertaController.class).buscarPorId(ofertaDTO.getIdOferta())).withSelfRel(),
            linkTo(methodOn(OfertaController.class).todasLasOfertas()).withRel("ofertas")
        );
    }
}