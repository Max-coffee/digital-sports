package com.example.spring_boot.domain;



public class School {
    private int col_id;
    private String col_name;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return col_name;
    }

    public void setName(String col_name) {
        this.col_name = col_name;
    }



    public int getCol_id() {
        return col_id;
    }

    public void setCol_id(int col_id) {
        this.col_id = col_id;
    }

    @Override
    public String toString() {
        return "school{" +
                "col_id=" + col_id +
                ", col_name='" + col_name + '\'' +
                ", user=" + user +
                '}';
    }
}
