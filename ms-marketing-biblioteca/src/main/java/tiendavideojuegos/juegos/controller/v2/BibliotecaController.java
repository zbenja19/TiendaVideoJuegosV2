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

import tiendavideojuegos.juegos.model.Biblioteca;
import tiendavideojuegos.juegos.assemblers.BibliotecaModelAssembler;
import tiendavideojuegos.juegos.service.BibliotecaService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("bibliotecaControllerV2")
@RequestMapping("/api/v2/bibliotecas")
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private BibliotecaModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Biblioteca>>> listarTodos() {
        List<EntityModel<Biblioteca>> bibliotecas = bibliotecaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (bibliotecas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                bibliotecas,
                linkTo(methodOn(BibliotecaController.class).listarTodos()).withSelfRel()
        ));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Biblioteca>> obtenerPorId(@PathVariable Integer id) {
        try {
            Biblioteca biblio = bibliotecaService.buscarPorId(id);
            if (biblio == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(biblio));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Biblioteca>> crearBiblioteca(@Valid @RequestBody Biblioteca biblioteca) {
        try {
            Biblioteca guardada = bibliotecaService.guardar(biblioteca);
            return ResponseEntity
                    .created(linkTo(methodOn(BibliotecaController.class).obtenerPorId(guardada.getId())).toUri())
                    .body(assembler.toModel(guardada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Biblioteca>> actualizar(@PathVariable Integer id, @Valid @RequestBody Biblioteca datosNuevos) {
        try {
            Biblioteca actualizada = bibliotecaService.actualizar(id, datosNuevos);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        String resultado = bibliotecaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.badRequest().body(resultado);
        }
    }
}