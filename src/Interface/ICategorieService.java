/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Entities.Categorie;
import java.util.List;

/**
 *
 * @author TECHN
 * @param <Categorie>
 */
public interface ICategorieService<categorie> {
    
    /**
     *
     * @param e
     */
    public void ajouterCategorie(Categorie e);
    public void supprimerCategorie(Categorie e);
    public void modifierCategorie(Categorie e);
    public List<Categorie> afficherCategories();
     public List<Categorie> rechercher(String recherche);
      public List<Categorie> filtrerParDate(java.util.Date startDate, java.util.Date endDate);
}
