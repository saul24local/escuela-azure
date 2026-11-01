package com.gerdoc.escuela.controller;

import com.gerdoc.escuela.dto.ProfesorMateriaRequest;
import com.gerdoc.escuela.model.Materia;
import com.gerdoc.escuela.model.Profesor;
import com.gerdoc.escuela.model.ProfesorMateria;
import com.gerdoc.escuela.repository.MateriaRepository;
import com.gerdoc.escuela.repository.ProfesorMateriaRepository;
import com.gerdoc.escuela.repository.ProfesorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag( name = "Profesor-Materia", description = "Asignación de materias a profesores")
@RestController
@RequestMapping("/api/profesor-materias")
@RequiredArgsConstructor
public class ProfesorMateriaController
{
    private final ProfesorMateriaRepository profesorMateriaRepository;
    private final ProfesorRepository profesorRepository;
    private final MateriaRepository materiaRepository;

    @Operation(summary = "Consultar todas las asignaciones profesor-materia")
    @GetMapping
    public ResponseEntity<List<ProfesorMateria>> findAll()
    {
        return ResponseEntity.ok(profesorMateriaRepository.findAll());
    }

    @Operation(summary = "Consultar una asignación por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProfesorMateria> findById(@PathVariable Long id)
    {
        return profesorMateriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una asignación", description = "Recibe profesorId y materiaId existentes")
    @PostMapping
    public ResponseEntity<ProfesorMateria> create(@RequestBody ProfesorMateriaRequest request)
    {
        ProfesorMateria nueva = new ProfesorMateria();
        asignar(nueva, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(profesorMateriaRepository.save(nueva));
    }

    @Operation(summary = "Actualizar una asignación existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProfesorMateria> update(@PathVariable Long id, @RequestBody ProfesorMateriaRequest request)
    {
        return profesorMateriaRepository.findById(id)
                .map(existente -> {
                    asignar(existente, request);
                    return ResponseEntity.ok(profesorMateriaRepository.save(existente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una asignación por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (!profesorMateriaRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        profesorMateriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void asignar(ProfesorMateria destino, ProfesorMateriaRequest request)
    {
        if (request.profesorId() == null || request.materiaId() == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "profesorId y materiaId son obligatorios");
        }
        Profesor profesor = profesorRepository.findById(request.profesorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El profesor no existe"));
        Materia materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La materia no existe"));
        destino.setProfesor(profesor);
        destino.setMateria(materia);
    }
}
