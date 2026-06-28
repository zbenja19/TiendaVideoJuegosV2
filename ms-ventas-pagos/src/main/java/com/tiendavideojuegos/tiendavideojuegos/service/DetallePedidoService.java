package com.tiendavideojuegos.tiendavideojuegos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendavideojuegos.tiendavideojuegos.DTO.DetallePedidoDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.DetallePedido;
import com.tiendavideojuegos.tiendavideojuegos.repository.DetallePedidoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class DetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedidoDTO> obtenerTodos(){
        List<DetallePedidoDTO> detallePedidos = new ArrayList<>();
        for (DetallePedido detallePedido : detallePedidoRepository.findAll()){
            detallePedidos.add(convertirADetallePedidoDTO(detallePedido));
        }
        return detallePedidos;
    }

    public DetallePedidoDTO buscarPorId(Integer idDetallePedido){
        DetallePedido detallePedido = detallePedidoRepository.findById(idDetallePedido).orElseThrow(() -> new RuntimeException("DetallePedido no encontrado"));
        return convertirADetallePedidoDTO(detallePedido);
    }

    public DetallePedidoDTO guardar(DetallePedido detallePedido){
        DetallePedido detallePedidoGuardado = detallePedidoRepository.save(detallePedido);
        return convertirADetallePedidoDTO(detallePedidoGuardado);
    }

    private DetallePedidoDTO convertirADetallePedidoDTO(DetallePedido detallepedido){
        DetallePedidoDTO detallePedidoDTO = new DetallePedidoDTO();
        detallePedidoDTO.setIdDetallePedido(detallepedido.getIdDetallePedido());
        detallePedidoDTO.setPrecio(detallepedido.getPrecio());
        return detallePedidoDTO;
    }
}