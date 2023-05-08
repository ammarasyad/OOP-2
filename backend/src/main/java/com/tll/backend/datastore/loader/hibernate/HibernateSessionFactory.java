package com.tll.backend.datastore.loader.hibernate;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.model.barang.KategoriBarang;
import com.tll.backend.model.bill.Bill;
import com.tll.backend.model.bill.FixedBill;
import com.tll.backend.model.bill.TemporaryBill;
import com.tll.backend.model.hibernatespecific.Cart;
import com.tll.backend.model.hibernatespecific.TemporaryCart;
import com.tll.backend.model.user.Customer;
import com.tll.backend.model.user.Member;
import com.tll.backend.model.user.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    private static SessionFactory factory;

    public static Session getSession() {
        return factory.openSession();
    }

    static {
        Class<?>[] models = {
                Barang.class,
                KategoriBarang.class,
                    FixedBill.class,
                    Cart.class,
                    Bill.class,
                    TemporaryBill.class,
                    TemporaryCart.class,
                    Customer.class,
                    Member.class
        };

        init(models);
    }

    private static void init(Class<?>[] classes) {
        var config = new Configuration();
        for (var claz: classes) {
            config.addAnnotatedClass(claz);
        }
        factory = config.configure().buildSessionFactory();
    }

}
