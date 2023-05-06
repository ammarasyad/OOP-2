package com.tll.backend.datastore.loader.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Transaction;

import java.util.List;

@RequiredArgsConstructor
public class HibernateDataStore {

    public <T> void save(T object) {
        try (var session = HibernateSessionFactory.getSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(object);
            tx.commit();
        }
    }

    public <T> void saveAll(Iterable<T> objects) {
        try (var session = HibernateSessionFactory.getSession()) {
            Transaction tx = session.beginTransaction();
            for (var object: objects) {
                session.merge(object);
            }
            tx.commit();
        }
    }

    public <T> List<T> getAll(Class<T> clazz) {
        try (var session = HibernateSessionFactory.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(clazz);
            criteria.from(clazz);
            return session.createQuery(criteria).getResultList();
        }
    }

}
