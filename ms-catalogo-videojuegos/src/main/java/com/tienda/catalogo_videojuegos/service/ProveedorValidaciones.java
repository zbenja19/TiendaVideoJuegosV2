package com.tienda.catalogo_videojuegos.service;

import org.springframework.stereotype.Service;

import com.tienda.catalogo_videojuegos.DTO.ProveedorDTO;
import com.tienda.catalogo_videojuegos.model.Proveedor;

@Service
public class ProveedorValidaciones {

    public Boolean validarNullVacio (Proveedor proveedor) {
        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()){
            return false;
        }
        return true;
    }

    public void validarTelefono(String telefono){
        if(telefono != null && telefono.length() > 20){
            throw new RuntimeException("Debes ingresar un telefono valido");
        }
    }

    public void validarEmail (String email){
        if(email == null || !email.contains("@")){
            throw new RuntimeException("Debes ingresar un correo valido");
        }
    }

    public  ProveedorDTO convertirADTO(Proveedor proveedor) {
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        
        proveedorDTO.setNombre(proveedor.getNombre());
        proveedorDTO.setEmail(proveedor.getEmail());
        proveedorDTO.setTelefono(proveedor.getTelefono());

        if (proveedor.getIdProveedor() != null) {
            proveedorDTO.setIdProveedor(proveedor.getIdProveedor());
        } else {
            proveedorDTO.setIdProveedor(null);
        }
        return proveedorDTO;
    }

}
