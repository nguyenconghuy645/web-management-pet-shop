package dao;

import java.sql.SQLException;
import java.util.List;

public interface IDao<T> {
    boolean add(T obj) throws SQLException;
    boolean remove(int id) throws SQLException;
    boolean edit(T obj) throws SQLException;
    List<T> getRecords(int currentPage, double recordsPerPage) throws SQLException;
    List<T> findByName(String query, int currentPage, double recordsPerPage);
    T findById(int id) throws SQLException;
}
