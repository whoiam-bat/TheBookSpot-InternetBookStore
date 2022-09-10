package com.my.store.model;

public class Step {
    private int id;

    private String nameStep;

    public Step(String nameStep) {
        this.nameStep = nameStep;
    }

    public Step(int id, String nameStep) {
        this.id = id;
        this.nameStep = nameStep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameStep() {
        return nameStep;
    }

    public void setNameStep(String nameStep) {
        this.nameStep = nameStep;
    }


    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", nameStep='" + nameStep + '\'' +
                '}';
    }
}
