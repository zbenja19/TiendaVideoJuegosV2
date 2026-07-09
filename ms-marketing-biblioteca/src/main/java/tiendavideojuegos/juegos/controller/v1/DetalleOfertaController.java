package tiendavideojuegos.juegos.controller.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tiendavideojuegos.juegos.DTO.DetalleOfertaDTO;
import tiendavideojuegos.juegos.service.DetalleOfertaService;
@RestController("detalleOfertaControllerV1")
@RequestMapping("/api/v1/detalle-ofertas")
public class DetalleOfertaController {
    
    @Autowired
    private DetalleOfertaService detalleOfertaService;

    @GetMapping
    public ResponseEntity<?> todosLosDetallesOfertas(){
        List<DetalleOfertaDTO> detalleOfertas = detalleOfertaService.obtenerTodos();
        if(!detalleOfertas.isEmpty()){
            return new ResponseEntity<>(detalleOfertas, HttpStatus.OK);
    }
        return new ResponseEntity<>("No se encontraron detalles de ofertas", HttpStatus.NOT_FOUND);
    }

}
