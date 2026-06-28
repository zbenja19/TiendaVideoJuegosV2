package com.tienda.catalogo_videojuegos.controller.v1;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.tienda.catalogo_videojuegos.DTO.VideoJuegoDTO;
import com.tienda.catalogo_videojuegos.model.VideoJuego;
import com.tienda.catalogo_videojuegos.service.VideoJuegoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("videoJuegoControllerV1")
@RequestMapping("/api/v1/videojuegos")
@Tag(name = "Videojuegos",description = "Operaciones para la gestion del catalogo de videojuegos")
public class VideoJuegoController {

    @Autowired
    private VideoJuegoService videoJuegoService;

    @GetMapping
    @Operation(summary = "Obtiene una lista", description = "Retorna una lista de todos los videojuegos")
    @ApiResponse(responseCode = "200", description = "Lista de videojuegos obtenida correctamente")
    @ApiResponse(responseCode="204", description= "No existen videojuegos registrados")
    public ResponseEntity<List<VideoJuegoDTO>> listar(){
        List<VideoJuegoDTO> videoJuegos = videoJuegoService.listarTodos();
        if (videoJuegos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(videoJuegos, HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    @Operation(summary = "Buscar videojuego por id", description = "Retorna un videojuego segun su id")
    @ApiResponse(responseCode = "200", description = "Videojuego encontrado")
    @ApiResponse(responseCode = "404", description = "Videojuego no encontrado")
    public ResponseEntity<?> buscarPorId (@PathVariable Integer id){
        try {
            VideoJuegoDTO videoJuegoDTO = videoJuegoService.buscarPorId(id);
            return new ResponseEntity<>(videoJuegoDTO,HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar videojuego por nombre", description = "Retorna unael videojuego que coincide con el nombre buscado")
    @ApiResponse (responseCode = "200", description = "Busqueda efectuada correctamente")
    public List<VideoJuego> buscarPorNombre (@RequestParam String nombre){
        return videoJuegoService.buscarPornombre(nombre);
    }

    @PostMapping
    @Operation(summary = "Crear videojuego", description = "Crea un nuevo videojuego")
    @ApiResponse(responseCode = "201", description = "Videojuego creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    public ResponseEntity<?> guardar(@Valid @RequestBody VideoJuego videoJuego){
        try {
            VideoJuegoDTO videoJuegoGuardado = videoJuegoService.guardar(videoJuego);
            return new ResponseEntity<>(videoJuegoGuardado,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar:" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza videojuego", description = "Actualiza el videojuego existente")
    @ApiResponse(responseCode = "200", description = "Actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Videojuego no encontrado")
    public ResponseEntity<?> actualizar (@PathVariable Integer id,@Valid @RequestBody VideoJuego videoJuego){
        try {
            VideoJuegoDTO actualizado = videoJuegoService.actualizarVideoJuego(id, videoJuego);
            return new ResponseEntity<>(actualizado,HttpStatus.OK);
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina videojuego", description = "Elimina el videojuego por id")
    @ApiResponse(responseCode = "200", description = "Videojuego eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Videojuego no encontrado")
    public ResponseEntity<?>eliminar (@PathVariable Integer id){
        try {
            String mensajeEliminar = videoJuegoService.eliminar(id);
            return new ResponseEntity<>(mensajeEliminar,HttpStatus.OK);

        } catch (RuntimeException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
