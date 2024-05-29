package com.example.shoes.backend.model;

import java.util.Objects;

public class Shoes {
    private int id;
    private String name;
    private double size;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shoes shoes = (Shoes) o;
        return id == shoes.id && Objects.equals(name, shoes.name) && Objects.equals(size, shoes.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, size);
    }
}
