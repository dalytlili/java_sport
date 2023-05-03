 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Entities.Categorie;
import Entities.Activite;
import Interface.ICategorieService;
import MyConnection.MyConnection;
import Service.CategorieService;
import Service.ActiviteService;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Interface.IActiviteService;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import static javax.xml.bind.DatatypeConverter.parseString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author TECHN
 */
public class ActiviteController implements Initializable {

    @FXML
    private TableView<Activite> tableRes;
    @FXML
    private TableColumn<Activite, String> id;
    @FXML
    private TableColumn<Activite, Integer> idactivite;
    @FXML
    private TableColumn<Activite, String> nom_ac;
    @FXML
    private TableColumn<Activite, String> description;
    @FXML
    private TextArea idActivite;
    @FXML
    private TextArea nom_acActivite;
    @FXML
    private TextArea descriptionActivite;
    @FXML
    private ComboBox<String> idEvent;
    @FXML
    private Button ajouterRes;
    @FXML
    private Button supprimerRes;
    @FXML
    private Button modifierRes;
    Connection mc;
    PreparedStatement ste;
    ObservableList<Activite>resList;
    @FXML
    private Button fermerRes;
    @FXML
    private Button btn_showimage;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Choix de l'action");
    alert.setHeaderText("Que voulez-vous faire ?");
    alert.setContentText("Voulez-vous passer à l'application ou choisir une musique ?");

    ButtonType buttonTypeApp = new ButtonType("Aller à l'application");
    ButtonType buttonTypeMusic = new ButtonType("Choisir une musique");

    alert.getButtonTypes().setAll(buttonTypeApp, buttonTypeMusic);

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == buttonTypeApp){
        // Faire quelque chose pour passer à l'application
    } else if (result.get() == buttonTypeMusic) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un fichier audio");
        File defaultDirectory = new File("C:\\Users\\Ragheb_Harguem\\Desktop\\sport");
        fileChooser.setInitialDirectory(defaultDirectory);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers audio (*.mp3, *.wav, *.flac)", "*.mp3", "*.wav", "*.flac");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            javafx.scene.media.Media javafxMedia = new javafx.scene.media.Media(selectedFile.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(javafxMedia);
            mediaPlayer.setAutoPlay(true);
        } else {
            System.out.println("Aucun fichier audio sélectionné.");
        }
    }
        try {
            String req="select nom from categorie";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(req);
            ResultSet rs=pst.executeQuery();
            ObservableList<String> id = null;
            List<String> list = new ArrayList<>();
            while(rs.next()){
                
                list.add(rs.getString("nom"));
                
            }
            id = FXCollections
                    .observableArrayList(list);
            idEvent.setItems(id);
        } catch (SQLException ex) {
            Logger.getLogger(ActiviteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        
        afficherActivites();
    }  

    void afficherActivites(){
            mc=MyConnection.getInstance().getCnx();
            resList = FXCollections.observableArrayList();
      
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
                
                System.out.println("the added activites :" +r.toString());
                resList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
  id.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getNom()));
  idactivite.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getId_activite()));
  
  nom_ac.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom_ac()));
  description.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription()));
  
  
  
 
   tableRes.setItems(resList);
  
  refresh();
    
  }
  
  
    
    public void refresh(){
            resList.clear();
            mc=MyConnection.getInstance().getCnx();
            resList = FXCollections.observableArrayList();
      
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
                System.out.println("the added activites :" +r.toString());
                resList.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        tableRes.setItems(resList);
       
  }
    
    @FXML
    private void selected(MouseEvent res) {
         Activite clicked = tableRes.getSelectionModel().getSelectedItem();
         
         idEvent.setValue(clicked.getNom());
         idActivite.setText(String.valueOf(clicked.getId_activite()));
        
        nom_acActivite.setText(String.valueOf(clicked.getNom_ac()));
         descriptionActivite.setText(String.valueOf(clicked.getDescription()));
    }


    @FXML
    private void addRes(MouseEvent event) {
        
        String idactivite =idActivite.getText();
        String idevent = idEvent.getSelectionModel().getSelectedItem().toString();
        String nom_ac =nom_acActivite.getText();
        String description =descriptionActivite.getText();
        
         Activite r= new Activite(1,parseString(idevent),parseString(nom_ac),parseString(description));
        IActiviteService rs= new ActiviteService();
        rs.ajouterActivite(r);
        refresh();
        
        
        idActivite.setText(null);
        nom_acActivite.setText("");
        descriptionActivite.setText("");
        
    
    }

    @FXML
    private void deleteRes(MouseEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setHeaderText("Warning");
       alert.setContentText("Es-tu sûre de supprimer!");
       
       
       String Value1 = idActivite.getText();
        String idevent = idEvent.getSelectionModel().getSelectedItem().toString();
        String nom_ac =nom_acActivite.getText();
        String description =descriptionActivite.getText();
    
          Optional<ButtonType>result =  alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
        
        Activite r= new Activite(Integer.parseInt(Value1),parseString(idevent),parseString(nom_ac),parseString(description));
        IActiviteService rs= new ActiviteService();
        rs.supprimerActivite(r);
        refresh();
        }
    }

    @FXML
    private void updateRes(MouseEvent event) {
        String Value1 = idActivite.getText();
        String idevent = idEvent.getSelectionModel().getSelectedItem().toString();
        String nom_ac =nom_acActivite.getText();
        String description =descriptionActivite.getText();
    
        
        
        Activite r= new Activite(Integer.parseInt(Value1),parseString(idevent),parseString(nom_ac),parseString(description));
        IActiviteService rs= new ActiviteService();
        rs.modifierActivite(r);
        refresh();
        
        
        
    }
     @FXML
   private void pdfabonn(ActionEvent event) {
    try {
        String filename="C:\\xampp\\htdocs\\fichierExcelJava\\dataabonn.xls" ;
        HSSFWorkbook hwb=new HSSFWorkbook();
        HSSFSheet sheet =  hwb.createSheet("new sheet");
        HSSFRow rowhead = sheet.createRow((short)0);
        rowhead.createCell((short) 0).setCellValue("nom_Categori ");
        rowhead.createCell((short) 1).setCellValue("nom_activiter");
        rowhead.createCell((short) 2).setCellValue("Description ");
        ActiviteService st = new ActiviteService();
        List<Activite> teams = st.afficherActivites();
        for (int i = 0; i < teams.size(); i++) {          
            HSSFRow row = sheet.createRow((short)i+1);
            row.createCell((short) 0).setCellValue(teams.get(i).getNom());
            row.createCell((short) 1).setCellValue(teams.get(i).getNom_ac());
            row.createCell((short) 2).setCellValue(teams.get(i).getDescription());
        }
        FileOutputStream fileOut =  new FileOutputStream(filename);
        hwb.write(fileOut);
        fileOut.close();
        System.out.println("Your excel file has been generated!");
        File file = new File(filename);
        if (file.exists() && Desktop.isDesktopSupported()){ 
            Desktop.getDesktop().open(file);
        }       
    } catch ( Exception ex ) {
        System.out.println(ex);
    }
}

    
    
    
    
    
    
    
    
    @FXML
    private void closeRes(MouseEvent event) {
        Stage stage =(Stage) fermerRes.getScene().getWindow();
        stage.close(); 
    }

     @FXML
    private void go_showimage(ActionEvent event)throws IOException  {
          Parent root3 = FXMLLoader .load(getClass().getResource("FXMLDocument2.fxml"));
    Stage window = (Stage) btn_showimage.getScene().getWindow();
    window.setScene(new Scene(root3));
    }










    
}
