package tiendavideojuegos.juegos.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import tiendavideojuegos.juegos.model.Carro;
import tiendavideojuegos.juegos.controller.v2.CarroController; // <-- Apunta a v2

@Component
public class CarroModelAssembler implements RepresentationModelAssembler<Carro, EntityModel<Carro>> {

    @Override
    public EntityModel<Carro> toModel(Carro carro) {
        return EntityModel.of(carro,
            linkTo(methodOn(CarroController.class).obtenerPorId(carro.getId())).withSelfRel(),
            linkTo(methodOn(CarroController.class).listarTodos()).withRel("carros"),
            linkTo(methodOn(CarroController.class).actualizar(carro.getId(), carro)).withRel("actualizar"),
            linkTo(methodOn(CarroController.class).eliminar(carro.getId())).withRel("eliminar")
        );
    }
}