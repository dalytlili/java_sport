 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package categorie;

import Entities.Categorie;
import Entities.Activite;
import Interface.ICategorieService;
import MyConnection.MyConnection;
import Service.CategorieService;
import Service.ActiviteService;
import Interface.IActiviteService;

/**
 *
 * @author TECHN
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyConnection mc = MyConnection.getInstance();
        System.out.println(mc.hashCode());
        
     
       
    //   Categorie e5= new Categorie(6,5,"y",","y");  //instanve
       ICategorieService es = new CategorieService();
       
        //es.Categorie(e5);
       //es.Categorie(e3);
      //es.Categorie(e3);
       //es.Categorie();
       
   
        //Activite r1=new Activite(1,1,false);
        IActiviteService rs = new ActiviteService();
        
      //rs.Activite(r1);
        //rs.Activite(r1);
       //rs.Activite(r1);
       //rs.Activite();
    }
    
}
