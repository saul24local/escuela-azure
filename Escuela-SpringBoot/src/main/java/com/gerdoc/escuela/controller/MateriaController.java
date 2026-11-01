package com.gerdoc.escuela.controller;

import com.gerdoc.escuela.model.Materia;
import com.gerdoc.escuela.repository.MateriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag( name = "Materias", description = "Operaciones CRUD para la administración de materias")
@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController
{
    private final MateriaRepository materiaRepository;

    @Operation(summary = "Consultar todas las materias")
    @GetMapping
    public ResponseEntity<List<Materia>> findAll()
    {
        return ResponseEntity.ok(materiaRepository.findAll());
    }

    @Operation(summary = "Consultar una materia por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Materia> findById(@PathVariable Long id)
    {
        return materiaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva materia")
    @PostMapping
    public ResponseEntity<Materia> create(@RequestBody Materia materia)
    {
        materia.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(materiaRepository.save(materia));
    }

    @Operation(summary = "Actualizar una materia existente")
    @PutMapping("/{id}")
    public ResponseEntity<Materia> update(@PathVariable Long id, @RequestBody Materia materia)
    {
        return materiaRepository.findById(id)
                .map(existente -> {
                    existente.setNombre(materia.getNombre());
                    existente.setCreditos(materia.getCreditos());
                    return ResponseEntity.ok(materiaRepository.save(existente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una materia por ID",
               description = "Responde 409 si la materia está asignada a profesores o alumnos")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (!materiaRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        materiaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
