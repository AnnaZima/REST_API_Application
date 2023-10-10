package net.annakat.restapp.repository.impl;

import net.annakat.restapp.model.UFile;
import net.annakat.restapp.repository.FileRepository;
import net.annakat.restapp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class FileRepositoryImpl implements FileRepository {
    @Override
    public UFile create(UFile object) {
        UFile savedFile;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(object);
            savedFile = session.get(UFile.class, object.getId());
            transaction.commit();

        }
        return savedFile;
    }

    @Override
    public UFile get(Integer id) {
        UFile uFile;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
          uFile =  session.get(UFile.class, id);
        }
        return uFile;
    }

    @Override
    public UFile update(UFile object) {
        UFile uFile;
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            uFile = (UFile)session.merge(object);
            transaction.commit();
        }
        return uFile;
    }

    @Override
    public void delete(Integer id) {
        UFile uFile = new UFile();
        uFile.setId(id);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(uFile);
            transaction.commit();
        }
    }

    @Override
    public List<UFile> getAll() {
        List<UFile> fileList;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UFile> files = session.createQuery("From UFile", UFile.class);
            fileList = files.list();
        }
        return fileList;
    }
}
