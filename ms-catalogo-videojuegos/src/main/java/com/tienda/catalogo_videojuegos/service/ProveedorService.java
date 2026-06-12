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

    //Metodos
    public List <ProveedorDTO> listarTodos(){
        return proveedorRepository.findAll().stream()
                        .map(this::convertirADTO)
                        .toList();
    }

    public Proveedor guardarProveedor(Proveedor proveedor){
        return proveedorRepository.save(proveedor);       
    }

    public String eliminar(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se ha podido eliminar, el ID " + id + " no existe."));
            proveedorRepository.delete(proveedor);
            return "El proveedor '" + proveedor.getNombre() + "' ha sido eliminado exitosamente";
    }

    public ProveedorDTO buscarPorId(Integer id){
        Proveedor proveedor = proveedorRepository.findById(id)
           .orElseThrow(() -> new RuntimeException("¡El Proveedor no esta registrado!"));
        return convertirADTO(proveedor);
    }

    public ProveedorDTO actualizarProveedor(Integer id,Proveedor nvoProveedor){
        Proveedor proveedordDto = proveedorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("¡El Proveedor no esta registrado!"));
        if(nvoProveedor.getNombre() != null){
            proveedordDto.setNombre(nvoProveedor.getNombre());
        }
        if(nvoProveedor.getEmail() != null){
            proveedordDto.setEmail(nvoProveedor.getEmail());
        }
        if(nvoProveedor.getTelefono() != null){
            proveedordDto.setTelefono(nvoProveedor.getTelefono());
        }
        Proveedor proveedorActualizado = proveedorRepository.save(proveedordDto);
        return convertirADTO(proveedorActualizado);
    }
    
    private ProveedorDTO convertirADTO(Proveedor proveedor) {
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
