package org.gerdoc.dao;

import org.gerdoc.model.Materia;

import java.util.List;

public interface MateriaDao
{
    List<Materia> findAll( );
    Materia findById(Long id);
    Materia save(Materia materia);
    void deleteById(Long id);
    Materia update(Materia materia);
}
