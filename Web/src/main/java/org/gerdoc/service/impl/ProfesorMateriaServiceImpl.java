package org.gerdoc.service.impl;

import org.gerdoc.dao.ProfesorMateriaDao;
import org.gerdoc.dao.impl.ProfesorMateriaDaoImpl;
import org.gerdoc.model.ProfesorMateria;
import org.gerdoc.service.ProfesorMateriaService;

import java.util.List;

public class ProfesorMateriaServiceImpl implements ProfesorMateriaService
{
    private ProfesorMateriaDao profesorMateriaDao;

    public ProfesorMateriaServiceImpl( )
    {
        this.profesorMateriaDao = new ProfesorMateriaDaoImpl( );
    }

    @Override
    public List<ProfesorMateria> findAll()
    {
        return profesorMateriaDao.findAll( );
    }

    @Override
    public ProfesorMateria findById(Long id)
    {
        return profesorMateriaDao.findById( id );
    }

    @Override
    public void save(ProfesorMateria profesorMateria)
    {
        profesorMateriaDao.save( profesorMateria );
    }

    @Override
    public void deleteById(Long id)
    {
        profesorMateriaDao.deleteById( id );
    }

    @Override
    public void update(ProfesorMateria profesorMateria)
    {
        profesorMateriaDao.update( profesorMateria );
    }
}
