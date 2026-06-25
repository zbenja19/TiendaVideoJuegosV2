package com.tiendavideojuegos.tiendavideojuegos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendavideojuegos.tiendavideojuegos.DTO.ClientesDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Clientes;
import com.tiendavideojuegos.tiendavideojuegos.repository.ClientesRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ClientesService {

    
    private ClienteValidaciones clienteValidaciones;

    @Autowired
    private ClientesRepository clientesRepository;

    public List<ClientesDTO> obtenerTodos() {
        List<ClientesDTO> listaDTOs = new ArrayList<>();
        for (Clientes c : clientesRepository.findAll()) {
            listaDTOs.add(convertirAClienteDTO(c));
        }
        return listaDTOs;
    }

    public ClientesDTO buscarPorId(Integer idCliente) {
        Clientes cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertirAClienteDTO(cliente);
    }

    public ClientesDTO guardar(Clientes nuevocliente) {
        if (clienteValidaciones.validarNullVacio(nuevocliente)) {
            Clientes clienteGuardado = clientesRepository.save(nuevocliente);
            return convertirAClienteDTO(clienteGuardado);
        }
        return null;
    }

    public ClientesDTO actualizarCliente(Integer id, ClientesDTO datosNuevos) {
        Clientes clienteExistente = clientesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡El cliente no existe!"));
        
        if(datosNuevos.getNombre() != null) {
            clienteExistente.setNombre(datosNuevos.getNombre());
        }
        if(datosNuevos.getCorreo() != null) {
            clienteExistente.setCorreo(datosNuevos.getCorreo());
        }
        if(datosNuevos.getTelefono() != null) {
            clienteExistente.setTelefono(datosNuevos.getTelefono());
        }
        if(datosNuevos.getContrasena() != null) {
            clienteExistente.setContrasena(datosNuevos.getContrasena());
        }
        
        return convertirAClienteDTO(clientesRepository.save(clienteExistente));
    }

    private ClientesDTO convertirAClienteDTO(Clientes cliente) {
        ClientesDTO dto = new ClientesDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setCorreo(cliente.getCorreo());
        dto.setTelefono(cliente.getTelefono());
        dto.setContrasena(cliente.getContrasena());
        return dto;

    }

}