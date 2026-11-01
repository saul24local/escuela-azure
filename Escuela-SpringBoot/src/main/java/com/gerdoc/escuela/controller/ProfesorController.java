package com.gerdoc.escuela.controller;

import com.gerdoc.escuela.model.Profesor;
import com.gerdoc.escuela.repository.ProfesorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag( name = "Profesores", description = "Operaciones CRUD para la administración de profesores")
@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController
{
    private final ProfesorRepository profesorRepository;

    @Operation(summary = "Consultar todos los profesores")
    @GetMapping
    public ResponseEntity<List<Profesor>> findAll()
    {
        return ResponseEntity.ok(profesorRepository.findAll());
    }

    @Operation(summary = "Consultar un profesor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> findById(@PathVariable Long id)
    {
        return profesorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo profesor")
    @PostMapping
    public ResponseEntity<Profesor> create(@RequestBody Profesor profesor)
    {
        profesor.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(profesorRepository.save(profesor));
    }

    @Operation(summary = "Actualizar un profesor existente")
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> update(@PathVariable Long id, @RequestBody Profesor profesor)
    {
        return profesorRepository.findById(id)
                .map(existente -> {
                    existente.setNombre(profesor.getNombre());
                    existente.setEspecialidad(profesor.getEspecialidad());
                    return ResponseEntity.ok(profesorRepository.save(existente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un profesor por ID",
               description = "Responde 409 si el profesor tiene materias asignadas")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (!profesorRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        profesorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
