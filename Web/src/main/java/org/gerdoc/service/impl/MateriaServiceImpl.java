package org.gerdoc.service.impl;

import org.gerdoc.dao.MateriaDao;
import org.gerdoc.dao.impl.MateriaDaoImpl;
import org.gerdoc.model.Materia;
import org.gerdoc.service.MateriaService;

import java.util.List;

public class MateriaServiceImpl implements MateriaService
{
    private MateriaDao materiaDao;

    public MateriaServiceImpl( )
    {
        this.materiaDao = new MateriaDaoImpl( );
    }

    @Override
    public List<Materia> findAll()
    {
        return materiaDao.findAll( );
    }

    @Override
    public Materia findById(Long id)
    {
        return materiaDao.findById( id );
    }

    @Override
    public void save(Materia materia)
    {
        materiaDao.save( materia );
    }

    @Override
    public void deleteById(Long id)
    {
        materiaDao.deleteById( id );
    }

    @Override
    public void update(Materia materia)
    {
        materiaDao.update( materia );
    }
}
