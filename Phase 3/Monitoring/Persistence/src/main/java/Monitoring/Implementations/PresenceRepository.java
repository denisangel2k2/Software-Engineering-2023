package Monitoring.Implementations;

import Monitoring.Interfaces.IPresenceRepository;
import Monitoring.Presence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class PresenceRepository implements IPresenceRepository {
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
    public PresenceRepository(){
        initialize();
    }

    @Override
    public void update(Presence presence) {
        try(Session session=sessionFactory.openSession()){
            try{
                session.beginTransaction();
                session.update(presence);
                session.getTransaction().commit();
            }
            catch (RuntimeException ex){
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public void add(Presence entity) {

        try(Session session=sessionFactory.openSession()){
            try{
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            }
            catch (RuntimeException ex){
                session.getTransaction().rollback();
            }
        }

    }

    @Override
    public void remove(Presence entity) {
        try(Session session=sessionFactory.openSession()){
            try{
                session.beginTransaction();
                session.delete(entity);
                session.getTransaction().commit();
            }
            catch (RuntimeException ex){
                session.getTransaction().rollback();
            }

        }

    }

    @Override
    public Presence findById(int id) {
        try(Session session=sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Presence presence=session.createQuery("from Presence where id=:id",Presence.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.getTransaction().commit();
                return presence;
            }
            catch (RuntimeException ex){
                session.getTransaction().rollback();
            }

        }
        return null;

    }

    @Override
    public Iterable<Presence> getAll() {
        try(Session session=sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Iterable<Presence> presences=session.createQuery("from Presence",Presence.class)
                        .list();
                session.getTransaction().commit();
                return presences;
            }
            catch (RuntimeException ex){
                session.getTransaction().rollback();
            }
        }
        return null;
    }
}
