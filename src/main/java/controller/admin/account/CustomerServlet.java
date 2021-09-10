package controller.admin.account;

import dao.admin.CustomerDao;
import dao.DBConnection;
import model.admin.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/administrator/customers")
public class CustomerServlet extends HttpServlet {
    private CustomerDao customerDAO;
    private int currentPage = 1;
    private final double recordsPerPage = 15;
    private double countCustomers;
    private double countPages;

    @Override
    public void init() throws ServletException {
        Connection conn = DBConnection.getConnection();
        customerDAO = new CustomerDao(conn);
        System.out.println("Init Customer DAO...");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        try {
            // Kiểm tra trạng thái đăng nhập
//            if (session.getAttribute("user") == null) {
//                resp.sendRedirect("/login");
//            } else {
            String action = (req.getParameter("act") != null) ? req.getParameter("act") : "";
            switch (action) {
                case "create":
                    createCustomer(req, resp);
                    break;
                case "store":
                    storeCustomer(req, resp);
                    break;
                case "edit":
                    editCustomer(req, resp);
                    break;
                case "update":
                    updateCustomer(req, resp);
                    break;
                case "delete":
                    deleteCustomer(req, resp);
                    break;
                case "search":
                    searchCustomer(req, resp);
                    break;
                default:
                    showAllCustomers(req, resp);
            }
//            }
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void createCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/admin/customer/create.jsp").forward(req, resp);
    }

    private void storeCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<String> message = new ArrayList<>();

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        int gender = Integer.parseInt(req.getParameter("gender"));

        if (customerDAO.checkExistEmail(email)) {
            message.add("Email is already exists");
        }
        if (message.size() == 0) {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setPhone(phone);
            customer.setEmail(email);
            customer.setAddress(address);
            customer.setGender(gender);
            System.out.println(customer);

            if (customerDAO.add(customer)) {
                session.setAttribute("msg", "<div class=\"alert alert alert-success\">New customer created successfully.</div>");
            } else {
                session.setAttribute("msg", "<div class=\"alert alert alert-danger\">INSERT fails.</div>");
            }
        } else {
            String errors = "<div class=\"alert alert-danger\">\n" +
                    "<ul>\n";
            for (String msg: message) {
                errors += "<li>" + msg + "</li>\n";
            }
            errors += "</ul>\n" +
                    "</div>";
            session.setAttribute("msg", errors);
        }
        resp.sendRedirect("/administrator/customers?act=create");
    }

    private void editCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("id"));
        Customer customer = customerDAO.findById(id);
        if (customer == null) {
            session.setAttribute("msg", "<div class=\"alert alert alert-danger\">Customer not found.</div>");
            resp.sendRedirect("/administrator/customers?act=index");
        } else {
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/admin/customer/edit.jsp").forward(req, resp);
        }
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        HttpSession session = req.getSession();
        List<String> message = new ArrayList<>();

        int id = Integer.parseInt(req.getParameter("id"));
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(req.getParameter("name"));
        customer.setEmail(req.getParameter("email"));
        customer.setPhone(req.getParameter("phone"));
        customer.setAddress(req.getParameter("address"));
        customer.setGender(Integer.parseInt(req.getParameter("gender")));

        Customer oldCustomer = customerDAO.findById(id);
        if (customerDAO.checkExistEmail(customer.getEmail()) && !oldCustomer.getEmail().equals(customer.getEmail())) {
            message.add("Email is already exists");
        }
        if (message.size() == 0) {
            if (customerDAO.edit(customer)) {
                session.setAttribute("msg", "<div class=\"alert alert alert-success\">Record updated successfully.</div>");
            } else {
                session.setAttribute("msg", "<div class=\"alert alert alert-danger\">UPDATE fails.</div>");
            }
        } else {
            String errors = "<div class=\"alert alert-danger\">\n" +
                    "<ul>\n";
            for (String msg: message) {
                errors += "<li>" + msg + "</li>\n";
            }
            errors += "</ul>\n" +
                    "</div>";
            session.setAttribute("msg", errors);
        }
        resp.sendRedirect("/administrator/customers?act=edit&id=" + id);
    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("id"));
        Customer customer = customerDAO.findById(id);
        if (customer == null) {
            session.setAttribute("msg", "<div class=\"alert alert alert-danger\">Customer not found.</div>");
        } else {
            if (customerDAO.remove(id)) {
                session.setAttribute("msg", "<div class=\"alert alert alert-success\">Record deleted successfully.</div>");
            } else {
                session.setAttribute("msg", "<div class=\"alert alert alert-danger\">DELETE fails.</div>");
            }
        }
        resp.sendRedirect("/administrator/customers?act=index");
    }

    public void showAllCustomers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.countCustomers = customerDAO.getNumberOfRows();
        this.countPages = (int) Math.ceil(countCustomers/recordsPerPage);
        if (req.getParameter("currentPage") != null && (currentPage >= 1 && currentPage <= countPages)) {
            this.currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        List<Customer> customers = customerDAO.getRecords(currentPage, recordsPerPage);
        extractCustomerList(req, resp, customers);
    }

    private void searchCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.countCustomers = customerDAO.getNumberOfRows();
        String search = req.getParameter("query");
        if (req.getParameter("currentPage") != null && (currentPage >= 1 && currentPage <= countPages)) {
            this.currentPage = Integer.parseInt(req.getParameter("currentPage"));
        }
        List<Customer> customers = customerDAO.findByName(search, currentPage, recordsPerPage);
        extractCustomerList(req, resp, customers);
    }

    private void extractCustomerList(HttpServletRequest req, HttpServletResponse resp, List<Customer> customers) throws ServletException, IOException {
        req.setAttribute("customers", customers);
        req.setAttribute("countPages", (int) countPages);
        req.setAttribute("countCustomers", (int) countCustomers);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("recordsPerPage", (int) recordsPerPage);
        req.getRequestDispatcher("/admin/customer/index.jsp").forward(req, resp);
    }
}
