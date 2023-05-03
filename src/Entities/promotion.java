/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author asus
 */
public class promotion extends abonnement{
    private int id_promo;        
private Date date_part;    
private int id_CarteFidelite;
public int id_abonnement; 
public abonnement abonnement;

    public promotion() {
    }

    public promotion(int id_promo, Date date_promotion, int id_CarteFidelite, int id_abonnement) {
        this.id_promo = id_promo;
        this.date_part = date_promotion;
        this.id_CarteFidelite = id_CarteFidelite;
        this.id_abonnement = id_abonnement;
    }
    public promotion(Date date_promotion, int id_CarteFidelite, int id_abonnement) {
        this.date_part = date_promotion;
        this.id_CarteFidelite = id_CarteFidelite;
        this.id_abonnement = id_abonnement;
    }

    public promotion(int id_promo, Date date_promotion, int id_CarteFidelite, int id_abonnement, abonnement abonnement) {
        this.id_promo = id_promo;
        this.date_part = date_promotion;
        this.id_CarteFidelite = id_CarteFidelite;
        this.id_abonnement = id_abonnement;
        this.abonnement = abonnement;
    }

    public int getId_promo() {
        return id_promo;
    }

    public Date getDate_part() {
        return date_part;
    }

    public int getId_CarteFidelite() {
        return id_CarteFidelite;
    }

    public int getId_abonnement() {
        return id_abonnement;
    }

    public abonnement getabonnement() {
        return abonnement;
    }

    public void setId_promo(int id_promo) {
        this.id_promo = id_promo;
    }

    public void setDate_part(Date date_promotion) {
        this.date_part = date_promotion;
    }

    public void setId_CarteFidelite(int id_CarteFidelite) {
        this.id_CarteFidelite = id_CarteFidelite;
    }

    public void setId_abonnement(int id_abonnement) {
        this.id_abonnement = id_abonnement;
    }

    public void setAbonnement(abonnement abonnement) {
        this.abonnement = abonnement;
    }

    @Override
    public String toString() {
        return "promotion{" + "id_promo=" + id_promo + ", date_promotion=" + date_part + ", id_CarteFidelite=" + id_CarteFidelite + ", id_abonnement=" + id_abonnement +  '}';
    }
    
    
    




}
