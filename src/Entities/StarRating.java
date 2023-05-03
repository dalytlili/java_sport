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
public class StarRating {
    
    private int rating;

  public StarRating(int rating) {
    this.rating = rating;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String display() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < rating; i++) {
      sb.append("\u2605");
    }
    for (int i = rating; i < 5; i++) {
      sb.append("\u2606");
    }
    return sb.toString();
  }


    public StarRating() {
    }
    
    
}
