package com.example.grcpclient.dto;

import interfaces.Car;
import java.util.List;

public class CarDto {

    private Long id;
    private String name;
    private String model;
    private String color;
    private List<String> doors;

    public CarDto() {
    }

    public CarDto(Long id, String name, String model, String color, List<String> doors) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.color = color;
        this.doors = doors;
    }

    public List<String> getDoors() {
        return doors;
    }

    public void setDoors(List<String> doors) {
        this.doors = doors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static CarDto fromProto(Car car) {
        return new CarDto(
                car.getId(),
                car.getName(),
                car.getModel(),
                car.getColor(),
                car.getDoorsList()
        );

    }
}
