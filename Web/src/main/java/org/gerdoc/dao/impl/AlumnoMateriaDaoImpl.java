package org.gerdoc.dao.impl;

import org.gerdoc.dao.AlumnoMateriaDao;
import org.gerdoc.model.AlumnoMateria;

public class AlumnoMateriaDaoImpl extends GenericDao<AlumnoMateria> implements AlumnoMateriaDao
{
    public AlumnoMateriaDaoImpl( )
    {
        super(AlumnoMateria.class);
    }
}
