package com.tll.backend.datastore.loader.hibernate;

import com.tll.backend.datastore.loader.hikari.HikariConfig;
import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.model.hibernatespecific.Cart;
import com.tll.backend.model.hibernatespecific.TemporaryCart;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.javatuples.Pair;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.tll.backend.datastore.TableDropper.dropTable;

public class HibernateDataStore {

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public HibernateDataStore() {
        try {
            dropTable(HikariConfig.INSTANCE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> void save(T object) {
        executor.submit(() -> {
            try (var session = HibernateSessionFactory.getSession()) {
                Transaction tx = session.beginTransaction();
                session.persist(object);
                tx.commit();
            }
        });
    }

    public <T> void saveAll(Iterable<T> objects) {
        executor.submit(() -> {
            try (var session = HibernateSessionFactory.getSession()) {
                Transaction tx = session.beginTransaction();
                for (var object: objects) {
                    if (object instanceof TemporaryBill) {
                        ((TemporaryBill) object).getCart().forEach(el -> {
                            TemporaryCart temporaryCart = new TemporaryCart();
                            temporaryCart.setIdBarang(el.getValue0().getId());
                            temporaryCart.setJumlahBarang(el.getValue1());
                            temporaryCart.setIdBill(((TemporaryBill) object).getId());

                            session.merge(temporaryCart);
                        });
                        continue;
                    }
                    if (object instanceof FixedBill) {
                        ((FixedBill) object).getCart().forEach(el -> {
                            Cart cart = new Cart();
                            cart.setIdBarang(el.getValue0().getId());
                            cart.setJumlahBarang(el.getValue1());
                            cart.setIdBill(((FixedBill) object).getId());

                            session.merge(cart);
                        });
                        continue;
                    }
                    session.merge(object);
                }
                tx.commit();
            }
        });
    }

    public <T> List<T> getAll(Class<T> clazz) {
        try (var session = HibernateSessionFactory.getSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(clazz);
            criteria.from(clazz);
            List<T> list = session.createQuery(criteria).getResultList();
            if (clazz.equals(TemporaryBill.class)) {
                list.forEach(el -> {
                    var idB = ((TemporaryBill) el).getId();
                    String hql = String.format("FROM TemporaryCart t WHERE t.idBill = %d ", idB);
                    Query query = session.createQuery(hql);
                    List<TemporaryCart> listT =  query.list();
                    listT.forEach(cart -> {
                        ((TemporaryBill) el).addToBill(Barang.builder().id(cart.getIdBarang()).build(), cart.getJumlahBarang());
                    });
                });
            }
            if (clazz.equals(FixedBill.class)) {
                list.forEach(el -> {
                    var idB = ((FixedBill) el).getId();
                    String hql = String.format("FROM Cart t WHERE t.idBill = %d ", idB);
                    Query query = session.createQuery(hql);
                    List<Cart> listT =  query.list();

                    ((FixedBill) el).setCart(listT.stream().map(cart -> {
                        Barang barang = Barang.builder().id(cart.getIdBarang()).build();
                        return Pair.with(barang, cart.getJumlahBarang());
                    }).toList());
                });
            }
            return list ;
        }
    }

}
