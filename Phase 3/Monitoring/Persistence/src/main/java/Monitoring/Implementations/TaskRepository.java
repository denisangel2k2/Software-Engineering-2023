package Monitoring.Implementations;

import Monitoring.Interfaces.ITaskRepository;
import Monitoring.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class TaskRepository implements ITaskRepository {

    private static SessionFactory sessionFactory;

    public TaskRepository() {
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

    @Override
    public void add(Task entity) {
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
    public void remove(Task entity) {
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
    public void update(Task task) {
        try(Session session=sessionFactory.openSession()){
            try{
                session.beginTransaction();
                session.update(task);
                session.getTransaction().commit();
            }
            catch (RuntimeException ex){
                session.getTransaction().rollback();
            }
        }
    }

    @Override
    public Task findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Task task = session.createQuery("from Task where id=:id", Task.class)
                        .setParameter("id", id)
                        .setMaxResults(1)
                        .uniqueResult();
                session.getTransaction().commit();
                return task;
            } catch (RuntimeException ex) {
                session.getTransaction().rollback();
            }
            return null;
        }
    }

    @Override
    public Iterable<Task> getAll() {
        try(Session session=sessionFactory.openSession()){
            try{
                session.beginTransaction();
                Iterable<Task> tasks=session.createQuery("from Task",Task.class).list();
                session.getTransaction().commit();
                return tasks;
            }
            catch (RuntimeException ex){
                session.getTransaction().rollback();
            }
            return null;
        }

    }
}
