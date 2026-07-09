package tiendavideojuegos.juegos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import tiendavideojuegos.juegos.DTO.DetalleOfertaDTO;
import tiendavideojuegos.juegos.controller.v2.DetalleOfertaController;

@Component
public class DetalleOfertaModelAssembler implements RepresentationModelAssembler<DetalleOfertaDTO, EntityModel<DetalleOfertaDTO>> {

    @Override
    public EntityModel<DetalleOfertaDTO> toModel(DetalleOfertaDTO detalleOfertaDTO) {
        return EntityModel.of(detalleOfertaDTO,
            linkTo(methodOn(DetalleOfertaController.class).todosLosDetallesOfertas()).withSelfRel()
        );
    }
}