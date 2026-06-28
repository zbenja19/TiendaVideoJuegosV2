package com.tienda.catalogo_videojuegos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tienda.catalogo_videojuegos.DTO.ProveedorDTO;
import com.tienda.catalogo_videojuegos.model.Proveedor;
import com.tienda.catalogo_videojuegos.repository.ProveedorRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProveedorValidaciones proveedorValidaciones;

    //Metodos
    public List <ProveedorDTO> listarTodos(){
        return proveedorRepository.findAll().stream()
                        .map(proveedorValidaciones::convertirADTO)
                        .toList();
    }

    public ProveedorDTO guardarProveedor(Proveedor proveedor){
        if(!proveedorValidaciones.validarNullVacio(proveedor)){
            throw new RuntimeException("Debes ingresar el nombre del proveedor");
        }
        proveedorValidaciones.validarEmail(proveedor.getEmail());
        proveedorValidaciones.validarTelefono(proveedor.getTelefono());

        proveedor.setNombre(proveedor.getNombre().trim());   
        boolean existeProveedor = proveedorRepository.existsByNombreIgnoreCase(proveedor.getNombre());
        
        if (existeProveedor){
            throw new RuntimeException("El Proveedor " + proveedor.getNombre() + " ya se encuentra registrado.");
            
        }
        
        Proveedor proveedorGuardado = proveedorRepository.save(proveedor);
        return proveedorValidaciones.convertirADTO(proveedorGuardado);     
    }

    public String eliminar(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

            proveedorRepository.delete(proveedor);
            return "El proveedor " + proveedor.getNombre() + " Eliminado exitosamente";
    }

    public ProveedorDTO buscarPorId(Integer id){
        Proveedor proveedor = proveedorRepository.findById(id)
           .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return proveedorValidaciones.convertirADTO(proveedor);
    }

    public ProveedorDTO actualizarProveedor(Integer id,Proveedor nvoProveedor){
        Proveedor proveedordDto = proveedorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        if(nvoProveedor.getNombre() != null){
            proveedordDto.setNombre(nvoProveedor.getNombre().trim());
        }
        if(nvoProveedor.getEmail() != null){
            proveedordDto.setEmail(nvoProveedor.getEmail());
        }
        if(nvoProveedor.getTelefono() != null){
            proveedordDto.setTelefono(nvoProveedor.getTelefono());
        }
        Proveedor proveedorActualizado = proveedorRepository.save(proveedordDto);
        return proveedorValidaciones.convertirADTO(proveedorActualizado);
    }
    
}
