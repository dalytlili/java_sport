/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;


import Entities.Activite;
import MyConnection.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Interface.IActiviteService;

/**
 *
 * @author TECHN
 */
public class ActiviteService implements IActiviteService<Activite>{
    @Override
    public void ajouterActivite(Activite r) {
        
        try {
            String requete= "INSERT INTO activite (nom,nom_ac,description)"
                    + "VALUES (?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            
            
            pst.setString(1,r.getNom());
            pst.setString(2,r.getNom_ac());
            pst.setString(3,r.getDescription());
            pst.executeUpdate();
                System.out.println("Activite ajoutée");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
    
    
    @Override
    public void supprimerActivite(Activite r) {
        try {
            String requete = "DELETE FROM activite where id_activite=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1, r.getId_activite());
            pst.executeUpdate();
            System.out.println("Activite supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifierActivite(Activite r) {
        try {
            String requete = "UPDATE activite SET nom=?,nom_ac=?,description=? WHERE id_activite=?";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            
            pst.setString(1, r.getNom());
            pst.setString(2, r.getNom_ac());
            pst.setInt(3, r.getId_activite());
            pst.setString(4, r.getDescription());
            
            
            pst.executeUpdate();
            System.out.println("activite modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    } 
 
    @Override
     public List<Activite> afficherActivites() {        
         List<Activite> ActivitesList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM activite r ";
            Statement st = MyConnection.getInstance().getCnx()
                    .createStatement();
            ResultSet rs =  st.executeQuery(requete); 
            while(rs.next()){
                Activite r = new Activite();
                r.setId_activite(rs.getInt("id_activite"));
                r.setNom(rs.getString("nom"));
                r.setNom_ac(rs.getString("nom_ac"));   
                r.setDescription(rs.getString("description")); 
                System.out.println("added activites to list: " + r.toString());
                ActivitesList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return ActivitesList;
     
     
     
     }
   
    
}
