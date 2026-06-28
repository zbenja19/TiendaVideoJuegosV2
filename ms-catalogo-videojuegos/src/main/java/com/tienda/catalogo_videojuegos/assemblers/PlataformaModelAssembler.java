package com.tienda.catalogo_videojuegos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.tienda.catalogo_videojuegos.DTO.PlataformaDTO;
import com.tienda.catalogo_videojuegos.controller.v2.PlataformaController;



@Component
public class PlataformaModelAssembler implements  RepresentationModelAssembler<PlataformaDTO, EntityModel<PlataformaDTO>>{

    @Override
    public EntityModel<PlataformaDTO> toModel(PlataformaDTO plataforma){
        return EntityModel.of(plataforma,
        linkTo(methodOn(PlataformaController.class).buscarPorId(plataforma.getIdPlataforma())).withSelfRel(),
        linkTo(methodOn(PlataformaController.class).listar()).withRel("plataforma")
        );

    }


}
