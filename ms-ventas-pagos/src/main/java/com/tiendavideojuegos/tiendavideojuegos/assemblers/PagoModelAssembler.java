package com.tiendavideojuegos.tiendavideojuegos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PagoDTO;
import com.tiendavideojuegos.tiendavideojuegos.controller.v2.PagoController;

@Component
public class PagoModelAssembler implements RepresentationModelAssembler<PagoDTO, EntityModel<PagoDTO>> {

    @Override
    public EntityModel<PagoDTO> toModel(PagoDTO pago){
        return EntityModel.of(pago,
        linkTo(methodOn(PagoController.class).buscarPorId(pago.getIdPago())).withSelfRel(),
        linkTo(methodOn(PagoController.class).todosLosPagos()).withRel("pago")
        );
    }
}
