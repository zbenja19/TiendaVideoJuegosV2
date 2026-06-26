package com.tiendavideojuegos.tiendavideojuegos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiendavideojuegos.tiendavideojuegos.DTO.PagoDTO;
import com.tiendavideojuegos.tiendavideojuegos.model.Pago;
import com.tiendavideojuegos.tiendavideojuegos.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<PagoDTO> obtenerTodos() {
        List<PagoDTO> pagos = new ArrayList<>();
        for (Pago pago : pagoRepository.findAll()) {
            pagos.add(convertirAPagoDTO(pago));
        }
        return pagos;
    }

    public PagoDTO buscarPorId(Integer idPago) {
        Pago pago = pagoRepository.findById(idPago)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        return convertirAPagoDTO(pago);
    }

    public PagoDTO guardar(Pago pago) {
        Pago pagoGuardado = pagoRepository.save(pago);
        return convertirAPagoDTO(pagoGuardado);
    }

    private PagoDTO convertirAPagoDTO(Pago pago) {
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setIdPago(pago.getIdPago());
        pagoDTO.setEstadoPago(pago.getEstadoPago());
        return pagoDTO;
    }
}