package com.tienda.catalogo_videojuegos.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.tienda.catalogo_videojuegos.DTO.ProveedorDTO;
import com.tienda.catalogo_videojuegos.controller.v2.ProveedorController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class ProveedorModelAssembler  implements  RepresentationModelAssembler<ProveedorDTO, EntityModel<ProveedorDTO>>{

    @Override
    public EntityModel<ProveedorDTO> toModel(ProveedorDTO proveedor){
        return EntityModel.of(proveedor,
        linkTo(methodOn(ProveedorController.class).buscarPorId(proveedor.getIdProveedor())).withSelfRel(),
        linkTo(methodOn(ProveedorController.class).listar()).withRel("proveedores"),
        linkTo(methodOn(ProveedorController.class).guardar(null)).withRel("guardar"),
        linkTo(methodOn(ProveedorController.class).actualizar(proveedor.getIdProveedor(),null)).withRel("actualizar"),
        linkTo(methodOn(ProveedorController.class).eliminar(proveedor.getIdProveedor())).withRel("eliminar")
        );

    }


}
