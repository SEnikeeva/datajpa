package repository;

import context.Component;

import java.util.List;

public interface CrudRepository<T>  extends Component {
    void save(T model);
    T findByID(int id);
    void delete(T model);
    List<T> findAll();
    void update();
}
