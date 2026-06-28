package tiendavideojuegos.juegos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tiendavideojuegos.juegos.DTO.OfertaDTO;
import tiendavideojuegos.juegos.model.Oferta;
import tiendavideojuegos.juegos.service.OfertaService;
@RestController
@RequestMapping("/api/v1/ofertas")
public class OfertaController {
    @Autowired
    private OfertaService ofertaService;

    @GetMapping
    public ResponseEntity<?> todasLasOfertas(){
        List<OfertaDTO> ofertas = ofertaService.obtenerTodos();
        if(!ofertas.isEmpty()){
            return new ResponseEntity<>(ofertas, HttpStatus.OK);
    }
        return new ResponseEntity<>("No se encontraron ofertas", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(Integer idOferta){
        try {
            OfertaDTO ofertaDTO = ofertaService.buscarPorId(idOferta);
            return new ResponseEntity<>(ofertaDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<OfertaDTO> agregarOferta(@RequestBody Oferta oferta){
        try{
            OfertaDTO guardado = ofertaService.guardar(oferta);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
