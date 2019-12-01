package com.example.mcert;

import java.util.ArrayList;

public class Model {
    public ArrayList<Incident> getIncidentsarray() {
        return Incidentsarray;
    }

    //setter for arraylist
    public void setIncidentsarray(ArrayList<Incident> Incidentsarray) {

        this.Incidentsarray = Incidentsarray;
    }

    //creating a class for words in recyclerview
    public static class Incident {
        public String Incident;


        public Incident(String Incident) {
            this.Incident = Incident;


        }
    }


    private static Model my_Model = null;

    public static Model getModel() {
        if (my_Model == null) {
            my_Model = new Model();
        }
        return my_Model;
    }

    private ArrayList<Incident> Incidentsarray;

    private Model() {
        setIncidentsarray(new ArrayList<Incident>());
//        loadModel();
    }


}
