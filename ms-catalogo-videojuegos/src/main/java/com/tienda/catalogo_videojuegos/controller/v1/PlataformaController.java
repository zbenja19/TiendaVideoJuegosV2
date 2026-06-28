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
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.tienda.catalogo_videojuegos.DTO.PlataformaDTO;
import com.tienda.catalogo_videojuegos.model.Plataforma;
import com.tienda.catalogo_videojuegos.service.PlataformaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("plataformaControllerV1")
@RequestMapping("/api/v1/plataformas")
@Tag(name = "Plataformas",description = "Operaciones para la gestion de plataformas")
public class PlataformaController {

   
    @Autowired
    private PlataformaService plataformaService;


    @GetMapping
    @Operation(summary="Obtiene una lista" , description="Retorna una lista de todos las plataformas")
    @ApiResponse(responseCode="200", description= "Lista de plataformas obtenida correctamente")
    @ApiResponse(responseCode="204", description= "No existen plataformas registradas")
    public ResponseEntity<List<PlataformaDTO>> listar() {
        List<PlataformaDTO> plataformas = plataformaService.obtenerTodas();
        if (plataformas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(plataformas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar plataforma por id", description = "Retorna una plataforma segun su id")
    @ApiResponse(responseCode = "200", description = "Plataforma encontrada")
    @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    public ResponseEntity<PlataformaDTO> buscarPorId(@PathVariable Integer id) {
        try {
            PlataformaDTO plataformaDTO = plataformaService.buscarPorId(id);
            return new ResponseEntity<>(plataformaDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear plataforma", description = "Crea una nueva plataforma")
    @ApiResponse(responseCode = "201", description = "Plataforma creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos invalidos")
    public ResponseEntity<?> guardar(@Valid @RequestBody Plataforma plataforma) {
        try {
            PlataformaDTO guardada = plataformaService.guardar(plataforma);
            return new ResponseEntity<>(guardada, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza plataforma", description = "Actualiza la plataforma existente")
    @ApiResponse(responseCode = "200", description = "Actualizada exitosamente")
    @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    public ResponseEntity<?> actualizar (@PathVariable Integer id,@Valid @RequestBody Plataforma plataforma){
        try {
         PlataformaDTO actualizada = plataformaService.actualizarPlataforma(id, plataforma);
            return new ResponseEntity<>(actualizada,HttpStatus.OK);
            
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina plataforma", description = "Elimina la plataforma por id")
    @ApiResponse(responseCode = "200", description = "Plataforma eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "Plataforma no encontrada")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            String mensajeEliminar = plataformaService.eliminar(id);
            return new ResponseEntity<>(mensajeEliminar,HttpStatus.OK);

        } catch (RuntimeException e) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
