package Monitoring.Implementations;

import Monitoring.Boss;
import Monitoring.Interfaces.IBossRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class BossRepository implements IBossRepository{



    private static SessionFactory sessionFactory;

    private void initialize(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    public BossRepository(){
        initialize();
    }

    @Override
    public Boss findByUsernameAndPassword(String username, String password) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                Boss boss=session.createQuery("from Boss where username=:username and password=:password",Boss.class)
                        .setParameter("username",username)
                        .setParameter("password",password)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return boss;
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public void add(Boss entity) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                session.save(entity);
                tx.commit();

            }
            catch (RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }


    @Override
    public void remove(Boss entity) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                session.remove(entity);
                tx.commit();
            }
            catch (RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }


    @Override
    public Boss findById(int id) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                Boss boss=session.createQuery("from Boss where id=:id",Boss.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return boss;
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Boss> getAll() {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try {
                tx=session.beginTransaction();
                Iterable<Boss> bosses=session.createQuery("from Boss",Boss.class)
                        .list();
                tx.commit();
                return bosses;
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }
}
