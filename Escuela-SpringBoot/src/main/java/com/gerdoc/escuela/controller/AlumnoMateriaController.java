package com.gerdoc.escuela.controller;

import com.gerdoc.escuela.dto.AlumnoMateriaRequest;
import com.gerdoc.escuela.model.Alumno;
import com.gerdoc.escuela.model.AlumnoMateria;
import com.gerdoc.escuela.model.Materia;
import com.gerdoc.escuela.repository.AlumnoMateriaRepository;
import com.gerdoc.escuela.repository.AlumnoRepository;
import com.gerdoc.escuela.repository.MateriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag( name = "Alumno-Materia", description = "Inscripción de alumnos a materias y su calificación")
@RestController
@RequestMapping("/api/alumno-materias")
@RequiredArgsConstructor
public class AlumnoMateriaController
{
    private final AlumnoMateriaRepository alumnoMateriaRepository;
    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;

    @Operation(summary = "Consultar todas las inscripciones alumno-materia")
    @GetMapping
    public ResponseEntity<List<AlumnoMateria>> findAll()
    {
        return ResponseEntity.ok(alumnoMateriaRepository.findAll());
    }

    @Operation(summary = "Consultar una inscripción por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoMateria> findById(@PathVariable Long id)
    {
        return alumnoMateriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una inscripción",
               description = "Recibe alumnoId, materiaId y calificacion (opcional). Responde 409 si el alumno ya está inscrito en esa materia")
    @PostMapping
    public ResponseEntity<AlumnoMateria> create(@RequestBody AlumnoMateriaRequest request)
    {
        AlumnoMateria nueva = new AlumnoMateria();
        asignar(nueva, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoMateriaRepository.save(nueva));
    }

    @Operation(summary = "Actualizar una inscripción existente")
    @PutMapping("/{id}")
    public ResponseEntity<AlumnoMateria> update(@PathVariable Long id, @RequestBody AlumnoMateriaRequest request)
    {
        return alumnoMateriaRepository.findById(id)
                .map(existente -> {
                    asignar(existente, request);
                    return ResponseEntity.ok(alumnoMateriaRepository.save(existente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una inscripción por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        if (!alumnoMateriaRepository.existsById(id))
        {
            return ResponseEntity.notFound().build();
        }
        alumnoMateriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void asignar(AlumnoMateria destino, AlumnoMateriaRequest request)
    {
        if (request.alumnoId() == null || request.materiaId() == null)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "alumnoId y materiaId son obligatorios");
        }
        Alumno alumno = alumnoRepository.findById(request.alumnoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "El alumno no existe"));
        Materia materia = materiaRepository.findById(request.materiaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La materia no existe"));
        destino.setAlumno(alumno);
        destino.setMateria(materia);
        destino.setCalificacion(request.calificacion());
    }
}
