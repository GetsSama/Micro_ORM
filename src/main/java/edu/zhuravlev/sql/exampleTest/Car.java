package edu.zhuravlev.sql.exampleTest;

import com.google.common.base.Objects;
import edu.zhuravlev.sql.micro_orm.annotations.Entity;
import edu.zhuravlev.sql.micro_orm.annotations.Id;

@Entity("Machines")
public class Car {
    @Id
    private String gosNumber;
    private String model;
    private int maxSpeed;
    private int horsPower;

    public Car(){};

    public Car(String gos_number, String model, int maxSpeed, int horsPower) {
        this.gosNumber = gos_number;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.horsPower = horsPower;
    }

    public String getGosNumber() {
        return gosNumber;
    }

    public void setGosNumber(String gosNumber) {
        this.gosNumber = gosNumber;
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
        return maxSpeed == car.maxSpeed && horsPower == car.horsPower && Objects.equal(gosNumber, car.gosNumber) && Objects.equal(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(gosNumber, model, maxSpeed, horsPower);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + gosNumber + '\'' +
                ", Model='" + model + '\'' +
                ", maxSpeed=" + maxSpeed +
                ", horsPower=" + horsPower +
                '}';
    }
}
