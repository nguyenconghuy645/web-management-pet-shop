package dao.admin;

import dao.DBConnection;
import dao.IDao;
import model.admin.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDao implements IDao<Customer> {
    private final Connection conn;

    public CustomerDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean add(Customer obj) {
        int result = 0;
        try {
            String query = "INSERT INTO customers (customer_name, email, phone" +
                    ", address, gender) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, obj.getName());
            stmt.setString(2, obj.getEmail());
            stmt.setString(3, obj.getPhone());
            stmt.setString(4, obj.getAddress());
            stmt.setInt(5, obj.getGender());
            System.out.println(stmt);
            result = stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public boolean remove(int id) {
        int result = 0;
        try {
            String query = "DELETE FROM customers WHERE customer_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            result = stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public boolean edit(Customer customer) throws SQLException {
        int result = 0;
        PreparedStatement stmt;
        String query;
        try {
            Date date = new Date();

            query = "UPDATE customers SET customer_name = ?, email = ?, phone = ?" +
                    ", address = ?, gender = ? WHERE customer_id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAddress());
            stmt.setInt(5, customer.getGender());

            result = stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    @Override
    public List<Customer> getRecords(int currentPage, double recordsPerPage) {
        List<Customer> customers = new ArrayList<>();
        try {
            int start = (int) ((currentPage - 1) * recordsPerPage);
            String query = "SELECT * FROM customers ORDER BY customer_id DESC LIMIT ?, ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, start);
            stmt.setInt(2, (int) recordsPerPage);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = extractUserResultset(rs);
                customers.add(customer);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer findById(int id) {
        Customer customer = null;
        try {
            String query = "SELECT * FROM customers WHERE customer_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = extractUserResultset(rs);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public List<Customer> findByName(String search, int currentPage, double recordsPerPage) {
        List<Customer> customers = new ArrayList<>();
        try {
            int start = (int) ((currentPage - 1) * recordsPerPage);
            String query = "SELECT * FROM customers WHERE customers.customer_name LIKE ? ORDER BY customers.customer_name DESC LIMIT ?, ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, '%' + search + '%');
            stmt.setInt(2, start);
            stmt.setInt(3, (int) recordsPerPage);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Customer customer = extractUserResultset(rs);
                customers.add(customer);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public boolean checkExistEmail(String email) {
        try {
            String query = "SELECT email FROM customers WHERE customers.email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Customer extractUserResultset(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("customer_id"));
        customer.setName(rs.getString("customer_name"));
        customer.setEmail(rs.getString("email"));
        customer.setPhone(rs.getString("phone"));
        customer.setAddress(rs.getString("address"));
        customer.setGender(rs.getInt("gender"));
        return customer;
    }

    public int getNumberOfRows() {
        int numOfRows = 0;
        try {
            Statement stmt = conn.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );
            String query = "SELECT COUNT(*) FROM customers";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                numOfRows = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numOfRows;
    }
}
