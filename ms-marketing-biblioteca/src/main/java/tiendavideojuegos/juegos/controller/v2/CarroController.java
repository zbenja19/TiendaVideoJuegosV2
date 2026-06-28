package tiendavideojuegos.juegos.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tiendavideojuegos.juegos.model.Carro;
import tiendavideojuegos.juegos.assemblers.CarroModelAssembler;
import tiendavideojuegos.juegos.service.CarroService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("carroControllerV2")
@RequestMapping("/api/v2/carro")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @Autowired
    private CarroModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Carro>>> listarTodos() {
        List<EntityModel<Carro>> items = carroService.obtenerTodo().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (items.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                items,
                linkTo(methodOn(CarroController.class).listarTodos()).withSelfRel(),
                linkTo(methodOn(CarroController.class).vaciarCarrito()).withRel("vaciar-carrito") // Link extra de la colección
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Carro>> obtenerPorId(@PathVariable Integer id) {
        try {
            Carro item = carroService.buscarPorId(id);
            if (item == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(item));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Carro>> crearCarro(@Valid @RequestBody Carro carro) {
        try {
            Carro guardado = carroService.guardar(carro);
            return ResponseEntity
                    .created(linkTo(methodOn(CarroController.class).obtenerPorId(guardado.getId())).toUri())
                    .body(assembler.toModel(guardado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Carro>> actualizar(@PathVariable Integer id, @Valid @RequestBody Carro carroDetalles) {
        try {
            Carro actualizado = carroService.actualizar(id, carroDetalles);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String resultado = carroService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<Void> vaciarCarrito() {
        carroService.vaciarCarrito();
        return ResponseEntity.noContent().build();
    }
}