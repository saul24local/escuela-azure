package org.gerdoc.service;

import org.gerdoc.model.Materia;

import java.util.List;

public interface MateriaService
{
    List<Materia> findAll( );
    Materia findById( Long id );
    void save( Materia materia );
    void deleteById( Long id );
    void update( Materia materia );
}
