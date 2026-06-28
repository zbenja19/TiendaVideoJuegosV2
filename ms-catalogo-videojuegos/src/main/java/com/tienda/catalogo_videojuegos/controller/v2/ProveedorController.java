package com.tienda.catalogo_videojuegos.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.tienda.catalogo_videojuegos.DTO.ProveedorDTO;
import com.tienda.catalogo_videojuegos.assemblers.ProveedorModelAssembler;
import com.tienda.catalogo_videojuegos.model.Proveedor;
import com.tienda.catalogo_videojuegos.service.ProveedorService;


@RestController("proveedorControllerV2")
@RequestMapping("/api/v2/proveedores")
public class ProveedorController {
    
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProveedorModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity <CollectionModel<EntityModel<ProveedorDTO>>> listar(){
        List<EntityModel<ProveedorDTO>> proveedores = proveedorService.listarTodos().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(CollectionModel.of(
                proveedores,
                linkTo(methodOn(ProveedorController.class).listar()).withSelfRel()

                )   
            
            );
    }

    @GetMapping (value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProveedorDTO>> buscarPorId (@PathVariable Integer id){
        try {
            ProveedorDTO proveedorDTO = proveedorService.buscarPorId(id);

            return ResponseEntity.ok(assembler.toModel(proveedorDTO));
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ProveedorDTO>> guardar(@Valid @RequestBody Proveedor proveedor){
        try {
            ProveedorDTO proveedorGuardado = proveedorService.guardarProveedor(proveedor);
            return ResponseEntity
                    .created(linkTo(methodOn(ProveedorController.class)
                    .buscarPorId(proveedorGuardado.getIdProveedor())).toUri())
                    .body(assembler.toModel(proveedorGuardado));


        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProveedorDTO>> actualizar (@PathVariable Integer id,@Valid @RequestBody Proveedor nvoProveedor){
        try {
            ProveedorDTO actualizado = proveedorService.actualizarProveedor(id, nvoProveedor);
            return ResponseEntity.ok(assembler.toModel(actualizado));
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
