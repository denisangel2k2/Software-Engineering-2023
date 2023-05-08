package Monitoring.Interfaces;

public interface IRepository<T>{
    void add(T entity);
    void remove(T entity);
    T findById(int id);
    Iterable<T> getAll();
}
