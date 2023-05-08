package Monitoring.Implementations;

import Monitoring.Employee;
import Monitoring.Interfaces.IEmployeeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class EmployeeRepository implements IEmployeeRepository {

    private static SessionFactory sessionFactory;

    public EmployeeRepository(){
        initialize();
    }

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

    @Override
    public Employee findByUsernameAndPassword(String username_find, String password_find) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                Employee employee=session.createQuery("from Employee where username=:username and password=:password",Employee.class)
                        .setParameter("username",username_find)
                        .setParameter("password",password_find)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return employee;
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public void add(Employee entity) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                session.save(entity);
                tx.commit();
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public void remove(Employee entity) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                session.remove(entity);
                tx.commit();
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
    }


    @Override
    public Employee findById(int id) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                Employee employee=session.createQuery("from Employee where id=:id",Employee.class)
                        .setParameter("id",id)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return employee;
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Employee> getAll() {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                Iterable<Employee> employee=session.createQuery("from Employee",Employee.class).list();
                tx.commit();
            }
            catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }
}
