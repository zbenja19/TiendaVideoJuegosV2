package com.tiendavideojuegos.tiendavideojuegos.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.tiendavideojuegos.tiendavideojuegos.DTO.DetallePedidoDTO;
import com.tiendavideojuegos.tiendavideojuegos.controller.v2.DetallePedidoController;



@Component
public class DetallePedidoModelAssembler implements RepresentationModelAssembler<DetallePedidoDTO, EntityModel<DetallePedidoDTO>> {

    @Override
    public EntityModel<DetallePedidoDTO> toModel(DetallePedidoDTO detallepedido){
        return EntityModel.of(detallepedido,
        linkTo(methodOn(DetallePedidoController.class).buscarPorId(detallepedido.getIdDetallePedido())).withSelfRel(),
        linkTo(methodOn(DetallePedidoController.class).todosLosPedidos()).withRel("detallepedido")
        );
    }
}
