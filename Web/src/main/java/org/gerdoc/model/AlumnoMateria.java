package org.gerdoc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ALUMNO_MATERIA")
public class AlumnoMateria
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ALUMNO_ID")
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "MATERIA_ID")
    private Materia materia;

    @Column(name = "CALIFICACION")
    private Double calificacion;
}
