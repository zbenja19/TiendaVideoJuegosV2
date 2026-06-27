package com.tiendavideojuegos.tiendavideojuegos.controller.v2;

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

import com.tiendavideojuegos.tiendavideojuegos.DTO.ClientesDTO;
import com.tiendavideojuegos.tiendavideojuegos.assemblers.ClientesModelAssembler;
import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;
import com.tiendavideojuegos.tiendavideojuegos.service.ClientesService;

import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("clientesControllerV2")
@RequestMapping("/api/v2/Clientes")
public class ClientesController {
    
    @Autowired
    private ClientesService clientesservice;

    @Autowired
    private ClientesModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ClientesDTO>>> listarTodos(){
        List<EntityModel<ClientesDTO>> clientes = clientesservice.obtenerTodos().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

         if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                clientes,
                linkTo(methodOn(ClientesController.class).listarTodos()).withSelfRel()
        ));
    }    

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClientesDTO>> obtenerPorId(@PathVariable Integer id) {
        try {
            ClientesDTO dto = clientesservice.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClientesDTO>> crearCliente(@Valid @RequestBody Clientes clientes){
        try{
            ClientesDTO nuevoCliente = clientesservice.guardar(clientes);
            return ResponseEntity
                    .created(linkTo(methodOn(ClientesController.class).obtenerPorId(nuevoCliente.getId())).toUri())
                    .body(assembler.toModel(nuevoCliente));
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ClientesDTO>> actualizar(@PathVariable Integer id, @Valid @RequestBody ClientesDTO datosNuevos) {
        try {
            ClientesDTO actualizado = clientesservice.actualizarCliente(id, datosNuevos);
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}