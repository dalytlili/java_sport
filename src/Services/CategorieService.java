/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

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
    
     
     
      public List<Categorie> rechercher(String recherche) {
    List<Categorie> produits = afficherCategories().stream()
            .filter(x -> 
                      String.valueOf(x.getrate()).contains(recherche) ||
               // x.getrate().contains(recherche) ||
                x.getNom().contains(recherche) ||
                x.getDescription_cat().contains(recherche))
            .collect(Collectors.toList());
    return produits;       
}
      
      
      
      
        public List<Categorie> filtrerParDate(java.util.Date startDate, java.util.Date endDate) {
    List<Categorie> events = afficherCategories().stream()
            .filter(e -> e.getDate_event().compareTo(startDate) >= 0 && e.getDate_event().compareTo(endDate) <= 0)
            .collect(Collectors.toList());
    return events;
}
  
        
        
     
}
