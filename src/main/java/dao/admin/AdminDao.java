package dao.admin;

import dao.DBConnection;
import dao.IDao;
import model.admin.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDao implements IDao<Admin> {

    private Connection conn;

    public AdminDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean add(Admin obj) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean edit(Admin obj) throws SQLException {
        return false;
    }

    @Override
    public List<Admin> getRecords(int currentPage, double recordsPerPage) throws SQLException {
        return null;
    }

    @Override
    public List<Admin> findByName(String query, int currentPage, double recordsPerPage) {
        return null;
    }

    @Override
    public Admin findById(int id) throws SQLException {
        return null;
    }

    public Admin getLoggedCustomer(String username, String password) {
        Admin admin = null;
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM administrator WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                admin = extractUserResultset(rs);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }



    public Admin extractUserResultset(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setId(rs.getInt("id"));
        admin.setPassword(rs.getString("password"));
        admin.setRoles(rs.getInt("roles"));
        return admin;
    }
}
