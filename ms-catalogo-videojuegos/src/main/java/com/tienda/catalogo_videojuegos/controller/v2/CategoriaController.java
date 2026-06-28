package com.tienda.catalogo_videojuegos.controller.v2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.tienda.catalogo_videojuegos.DTO.CategoriaDTO;
import com.tienda.catalogo_videojuegos.assemblers.CategoriaModelAssembler;
import com.tienda.catalogo_videojuegos.model.Categoria;
import com.tienda.catalogo_videojuegos.service.CategoriaService;

@RestController("categoriaControllerV2")
@RequestMapping("/api/v2/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private CategoriaModelAssembler assembler;


    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<CategoriaDTO>>> listar(){
        List<EntityModel<CategoriaDTO>> categorias = categoriaService.listarTodos().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                categorias,
                linkTo(methodOn(CategoriaController.class).listar()).withSelfRel()

                )
            
            );
    }

    @GetMapping (value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> buscarPorId (@PathVariable Integer id){
        try {
            CategoriaDTO categoriaDTO = categoriaService.buscarPorId(id);

            return ResponseEntity.ok(assembler.toModel(categoriaDTO));
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<CategoriaDTO>> guardarCategoria(@Valid @RequestBody Categoria categoria){ 
        try {
            CategoriaDTO categoriaGuardada = categoriaService.guardar(categoria);
            return ResponseEntity
                    .created(linkTo(methodOn(CategoriaController.class)
                    .buscarPorId(categoriaGuardada.getIdCategoria())).toUri())
                    .body(assembler.toModel(categoriaGuardada));


        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CategoriaDTO>> actualizar (@PathVariable Integer id,@Valid @RequestBody Categoria categoria){
        try {
            CategoriaDTO actualizada = categoriaService.actualizarCategoria(id, categoria);
            return ResponseEntity.ok(assembler.toModel(actualizada));
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
