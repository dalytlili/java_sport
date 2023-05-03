/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entities.Categorie;
import Interface.ICategorieService;
import MyConnection.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author TECHN
 */
public class CategorieService implements ICategorieService <Categorie>{
    
    @Override
    public void ajouterCategorie(Categorie e) {
        
        try {
            String requete= "INSERT INTO categorie (rate,nom,date_event,description_cat)"
                    + "VALUES (?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            
            pst.setInt(1, e.getrate());
            pst.setString(2, e.getNom());
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//String dateString = sdf.format(e.getDateProperty());
//pst.setString(3, dateString);
           pst.setDate(3,e.getDate_event());
            pst.setString(4,e.getDescription_cat());
            pst.executeUpdate();
            System.out.println("Categorie ajoutée");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
    
    
//        @Override
//    public void delete(Reclamation o) {
//        String req="delete from Reclamation where id="+o.getId();
//        Reclamation p=displayById(o.getId());
//        
//          if(p!=null)
//              try {
//           
//            st.executeUpdate(req);
//             
//        } catch (SQLException ex) {
//            Logger.getLogger(ReclamationDao.class.getName()).log(Level.SEVERE, null, ex);
//        }else System.out.println("n'existe pas");
//    }
    
    @Override
    public void supprimerCategorie(Categorie e) {
        try {
            String requete = "DELETE FROM categorie where id_cat=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, e.getId_cat());
            pst.executeUpdate();
            System.out.println("Categorie supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifierCategorie(Categorie e) {
        try {
            String requete = "UPDATE categorie SET rate=?,nom=?,description_cat=?  WHERE id_cat=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, e.getrate());
            pst.setString(2, e.getNom());
          //  pst.setString(3, e.getDate_event());
            pst.setString(3,e.getDescription_cat());
            pst.setInt(4,e.getId_cat());
            
            pst.executeUpdate();
            System.out.println("Categorie modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 
 
    @Override
     public List<Categorie> afficherCategories() {        
         List<Categorie> CategoriesList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM categorie e ";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                Categorie e = new Categorie();
                e.setId_cat(rs.getInt("id_cat"));
                e.setrate(rs.getInt("rate"));
                e.setNom(rs.getString("nom"));
                e.setDate(rs.getDate("date_event"));
                e.setDescription_cat(rs.getString("description_cat")); 
                System.out.println("the added events :" +e.toString());
                CategoriesList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return CategoriesList;
     
     
     
     }
    
     
     
// Cette méthode recherche une liste de catégories en fonction d'une chaîne de recherche passée en paramètre
public List<Categorie> rechercher(String recherche) {
    // On utilise la méthode "afficherCategories()" pour récupérer toutes les catégories disponibles
    List<Categorie> produits = afficherCategories().stream()
            // On filtre cette liste en utilisant la méthode "filter" de Java 8
            .filter(x -> 
                      String.valueOf(x.getrate()).contains(recherche) || // On vérifie si la chaîne de recherche est présente dans le taux de la catégorie
               // x.getrate().contains(recherche) || // Ancienne méthode qui ne fonctionne pas car "getrate()" ne retourne pas une chaîne de caractères
                x.getNom().contains(recherche) || // On vérifie si la chaîne de recherche est présente dans le nom de la catégorie
                x.getDescription_cat().contains(recherche)) // On vérifie si la chaîne de recherche est présente dans la description de la catégorie
            .collect(Collectors.toList()); // On récupère les catégories filtrées dans une liste
    return produits; // On renvoie la liste des catégories trouvées
}


      
      
      
      
// Cette méthode filtre une liste de catégories en fonction de la date de début et de fin passée en paramètre
public List<Categorie> filtrerParDate(java.util.Date startDate, java.util.Date endDate) {
    // On utilise également la méthode "afficherCategories()" pour récupérer toutes les catégories disponibles
    List<Categorie> events = afficherCategories().stream()
            // On filtre cette liste en utilisant la méthode "filter" de Java 8
            .filter(e -> e.getDate_event().compareTo(startDate) >= 0 && e.getDate_event().compareTo(endDate) <= 0) // On vérifie si la date de l'événement est comprise entre la date de début et la date de fin passées en paramètre
            .collect(Collectors.toList()); // On récupère les catégories filtrées dans une liste
    return events; // On renvoie la liste des catégories trouvées
}
   
        
     
}
