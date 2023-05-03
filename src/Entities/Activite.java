/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author TECHN
 */
public class Activite {
             private int id_activite;
             private String nom;
             private String nom_ac;
             private String description;
             private String image_abonn;

    public Activite(int id_activite, String nom, String nom_ac, String description,String image_abonn) {
        this.id_activite = id_activite;
        this.nom = nom;
        this.nom_ac = nom_ac;
        this.description=description;
        this.image_abonn=image_abonn;
    }

    public Activite() {
    }

    public int getId_activite() {
        return id_activite;
    }

    public void setId_activite(int id_activite) {
        this.id_activite = id_activite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setImage_abonn(String image_abonn) {
        this.image_abonn = image_abonn;
    }
    public String getImage_abonn() {
        return image_abonn;
    }


    public String getNom_ac() {
        return nom_ac;
    }
    public String getDescription() {
        return description;
    }

    public void setNom_ac(String nom_ac) {
        this.nom_ac = nom_ac;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Activite{" + "id_activite=" + id_activite + ", nom=" + nom + ", nom_ac=" + nom_ac + ", image_abonn=" + image_abonn + ", description=" + description + '}';
    }
             
    
             
    
            
}
