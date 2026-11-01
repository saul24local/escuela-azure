package org.gerdoc.dao.impl;

import org.gerdoc.dao.ProfesorMateriaDao;
import org.gerdoc.model.ProfesorMateria;

public class ProfesorMateriaDaoImpl extends GenericDao<ProfesorMateria> implements ProfesorMateriaDao
{
    public ProfesorMateriaDaoImpl( )
    {
        super(ProfesorMateria.class);
    }
}
