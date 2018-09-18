package com.example.zunnorain.telcard_khokha.Classes;

import java.util.List;

/**
 * Created by Zunnorain on 03/06/2018.
 */

public class OperatorClass {


    public static List<OperatorClass> operatorClassList;


    private String name ;
    private int logo;
    private int id;

    public OperatorClass() {
    }

    public OperatorClass(String name, int logo, int id) {
        this.name = name;
        this.logo = logo;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static List<OperatorClass> getOperatorClassList() {
        return operatorClassList;
    }

    public static void setOperatorClassList(List<OperatorClass> operatorClassList) {
        OperatorClass.operatorClassList = operatorClassList;
    }

}
