/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;



import java.sql.Date;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author TECHN
 */
public class Categorie {
   private int id_cat,rate;
   private String nom,
    description_cat;
private SimpleObjectProperty <Date>   date_event ;
    public Categorie(int id_cat, int rate, String nom,Date date_event, String description_event) {
        this.id_cat = id_cat;
        this.rate = rate;
        this.nom = nom;
          this.date_event=new SimpleObjectProperty <Date> (date_event);
        this.description_cat = description_event;
    }

    public Categorie() {
        
    }

   
      public Categorie(int id_cat, int rate, String nom, String description_event) {
        this.id_cat = id_cat;
        this.rate = rate;
        this.nom = nom;
     
        this.description_cat = description_event;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

//    public int getrate() {
//        return rate;
//    }

    public Integer getrate() {
    return rate;
}

    public void setrate(int rate) {
        this.rate = rate;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    
     public SimpleObjectProperty getDateProperty(){
        return date_event;
    }
  

  public Date getDate_event() {
       return date_event.get();
   }

     public void setDate(Date date_event) {
       this.date_event =  new SimpleObjectProperty(date_event);
  }
    
    public String getDescription_cat() {
        return description_cat;
    }

    public void setDescription_cat(String description_cat) {
        this.description_cat = description_cat;
    }

   @Override
public String toString() {
    return  " rate= " + rate + ",   nom= " + nom + ",   date_event= " + date_event + ",   description_cat= " + description_cat;
}

    




}
