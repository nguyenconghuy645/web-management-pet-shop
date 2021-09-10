package model.admin;

import java.util.Date;

public class Customer extends Person {
    private int id;

    public Customer() {
    }

    public Customer(String name, String phone, String email, String address, int gender) {
        super(name, phone, email, address, gender);
    }

    public Customer(String name, String phone, String email, String address, int gender, int id) {
        super(name, phone, email, address, gender);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
