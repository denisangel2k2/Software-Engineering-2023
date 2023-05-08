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
    public void add(Presence entity) {

        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        catch (RuntimeException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void remove(Presence entity) {

    }

    @Override
    public Presence findById(int id) {
        return null;
    }

    @Override
    public Iterable<Presence> getAll() {
        return null;
    }
}
