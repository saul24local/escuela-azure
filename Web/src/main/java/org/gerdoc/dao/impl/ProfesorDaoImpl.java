package org.gerdoc.dao.impl;

import org.gerdoc.dao.ProfesorDao;
import org.gerdoc.model.Profesor;

public class ProfesorDaoImpl extends GenericDao<Profesor> implements ProfesorDao
{
    public ProfesorDaoImpl( )
    {
        super(Profesor.class);
    }
}
