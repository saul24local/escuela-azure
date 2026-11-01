package com.gerdoc.escuela.dto;

import java.math.BigDecimal;

public record AlumnoMateriaRequest(Long alumnoId, Long materiaId, BigDecimal calificacion)
{
}
