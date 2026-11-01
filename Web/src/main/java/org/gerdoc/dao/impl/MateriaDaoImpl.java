package org.gerdoc.dao.impl;

import org.gerdoc.dao.MateriaDao;
import org.gerdoc.model.Materia;

public class MateriaDaoImpl extends GenericDao<Materia> implements MateriaDao
{
    public MateriaDaoImpl( )
    {
        super(Materia.class);
    }
}
