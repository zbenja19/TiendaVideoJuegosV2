package com.tiendavideojuegos.tiendavideojuegos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PedidosDTO;
import com.tiendavideojuegos.tiendavideojuegos.controller.v2.PedidosController;


@Component
public class PedidosModelAssembler implements RepresentationModelAssembler<PedidosDTO, EntityModel<PedidosDTO>> {

    @Override
    public EntityModel<PedidosDTO> toModel(PedidosDTO pedidos) { 
        return EntityModel.of(pedidos,  
            linkTo(methodOn(PedidosController.class).buscarPorId(pedidos.getId())).withSelfRel(),
            linkTo(methodOn(PedidosController.class).obtenerTodos()).withRel("pedidos")
        );
    }
}
