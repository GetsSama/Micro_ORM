package edu.zhuravlev.sql.exampleTest;

import com.google.common.base.Objects;

public class Car {
    private String id;
    private String model;
    private int maxSpeed;
    private int horsPower;

    public Car(){};

    public Car(String id, String model, int maxSpeed, int horsPower) {
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.horsPower = horsPower;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getHorsPower() {
        return horsPower;
    }

    public void setHorsPower(int horsPower) {
        this.horsPower = horsPower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return maxSpeed == car.maxSpeed && horsPower == car.horsPower && Objects.equal(id, car.id) && Objects.equal(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, model, maxSpeed, horsPower);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", Model='" + model + '\'' +
                ", maxSpeed=" + maxSpeed +
                ", horsPower=" + horsPower +
                '}';
    }
}
