package org.gerdoc.dao.impl;

import org.gerdoc.dao.AlumnoDao;
import org.gerdoc.model.Alumno;

public class AlumnoDaoImpl extends GenericDao<Alumno> implements AlumnoDao
{
    public AlumnoDaoImpl( )
    {
        super(Alumno.class);
    }
}
