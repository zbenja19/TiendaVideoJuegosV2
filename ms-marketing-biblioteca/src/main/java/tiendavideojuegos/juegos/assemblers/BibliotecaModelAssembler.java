package tiendavideojuegos.juegos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import tiendavideojuegos.juegos.controller.v2.BibliotecaController;
import tiendavideojuegos.juegos.model.Biblioteca;


@Component
public class BibliotecaModelAssembler implements RepresentationModelAssembler<Biblioteca, EntityModel<Biblioteca>> {

    @Override
    public EntityModel<Biblioteca> toModel(Biblioteca biblioteca) {
        return EntityModel.of(biblioteca,
            linkTo(methodOn(BibliotecaController.class).buscarPorId(biblioteca.getId())).withSelfRel(),
            linkTo(methodOn(BibliotecaController.class).obtenerTodas()).withRel("bibliotecas"),
            linkTo(methodOn(BibliotecaController.class).actualizar(biblioteca.getId(), biblioteca)).withRel("actualizar"),
            linkTo(methodOn(BibliotecaController.class).eliminar(biblioteca.getId())).withRel("eliminar")
        );
    }
}