package Monitoring;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static SessionFactory sessionFactory;


    public static void main(String[] args) {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }

        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                Employee employee=session.createQuery("from Employee where username=:username and password=:password",Employee.class)
                        .setParameter("username","denis")
                        .setParameter("password","denis")
                        .setMaxResults(1)
                        .uniqueResult();

                System.out.println(employee.getName());
                tx.commit();
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
    }
}