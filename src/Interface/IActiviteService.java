/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.List;

/**
 *
 * @author TECHN
 * @param <Activite>
 */
public interface IActiviteService<Activite> {
    public void ajouterActivite(Activite r);
    public void supprimerActivite(Activite r);
    public void modifierActivite(Activite r);
    public List<Activite> afficherActivites();
    
}
