package com.tiendavideojuegos.tiendavideojuegos.assemblers;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tiendavideojuegos.tiendavideojuegos.DTO.ClientesDTO;
import com.tiendavideojuegos.tiendavideojuegos.controller.v2.ClientesController;

@Component
public class ClientesModelAssembler implements RepresentationModelAssembler<ClientesDTO, EntityModel<ClientesDTO>>  {

    @Override
    public EntityModel<ClientesDTO> toModel(ClientesDTO clientes){
        return EntityModel.of(clientes,
        linkTo(methodOn(ClientesController.class).obtenerPorId(clientes.getId())).withSelfRel(),
        linkTo(methodOn(ClientesController.class).listarTodos()).withRel("clientes")
        );
    }
}
