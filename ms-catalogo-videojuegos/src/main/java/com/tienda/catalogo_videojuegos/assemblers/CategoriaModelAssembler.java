package com.tienda.catalogo_videojuegos.assemblers;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import com.tienda.catalogo_videojuegos.DTO.CategoriaDTO;
import com.tienda.catalogo_videojuegos.controller.v2.CategoriaController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoriaModelAssembler implements  RepresentationModelAssembler<CategoriaDTO, EntityModel<CategoriaDTO>>{

    @Override
    public EntityModel<CategoriaDTO> toModel(CategoriaDTO categoria){
        return EntityModel.of(categoria,
        linkTo(methodOn(CategoriaController.class).buscarPorId(categoria.getIdCategoria())).withSelfRel(),
        linkTo(methodOn(CategoriaController.class).listar()).withRel("categoria")
        );

    }

}
