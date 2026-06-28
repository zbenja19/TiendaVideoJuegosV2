package com.tienda.catalogo_videojuegos.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.tienda.catalogo_videojuegos.DTO.VideoJuegoDTO;
import com.tienda.catalogo_videojuegos.controller.v2.VideoJuegoController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class VideoJuegoModelAssembler implements RepresentationModelAssembler<VideoJuegoDTO, EntityModel<VideoJuegoDTO>>{

    @Override
    public EntityModel<VideoJuegoDTO> toModel(VideoJuegoDTO videoJuego){
        return EntityModel.of(videoJuego,
        linkTo(methodOn(VideoJuegoController.class).buscarPorId(videoJuego.getIdVideoJuego())).withSelfRel(),
        linkTo(methodOn(VideoJuegoController.class).listar()).withRel("categoria")
        );

    }
    

}
