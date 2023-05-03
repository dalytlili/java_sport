/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Entities.Categorie;
import Entities.StarRating;
import Interface.ICategorieService;
import MyConnection.MyConnection;
import Service.CategorieService;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TECHN
 */
public class CategorieFXMLController implements Initializable {

    @FXML
    private ListView<Categorie> tableEvent2;
    @FXML
    private TableColumn<Categorie, Integer> id;
    @FXML
    private TableColumn<Categorie, Integer> rate;
    @FXML
    private TableColumn<Categorie, String> nom;
    @FXML
    private TableColumn<Categorie, String> date;
    @FXML
    private TableColumn<Categorie, String> description;
    @FXML
    private TextArea idEvent;
    
    @FXML
    private TextArea rateEvent;
    @FXML
    private TextArea nomEvent;
    @FXML
    private TextArea descriptionEvent;
    @FXML
    private Button ajouterEvent;
    @FXML
    private Button fermerEvent;
    @FXML
    private Button supprimerEvent;
    @FXML
    private Button modifierEvent;
    
    Connection mc;
    PreparedStatement ste;
    ObservableList<Categorie>eventList=FXCollections.observableArrayList();
   
    @FXML
    private TextArea rechercher;
    @FXML
    private TableView<Categorie> tableEvent;
    @FXML
    private Button btStat;
    @FXML
    private DatePicker dd;
    @FXML
    private DatePicker df;
  
   

    

   
    
    
    
    
    
    class StarRatingCell extends TableCell<Categorie, Integer> {  //pour mettre les etoiles au graphique
    @Override
    protected void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            StarRating starRating = new StarRating(item);
            setText(starRating.display());
        } else {
            setText(null);
        }
    }
}
    
    
    
  
  


    
  
  
  
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       afficherCategories();
     
     
     //******************************
     rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
  
   ICategorieService categorieService = new CategorieService();
    List<Categorie> categoriesList = categorieService.rechercher(newValue);
    ObservableList<Categorie>CList = FXCollections.observableArrayList(categoriesList);
  
   tableEvent2.setItems(CList);


tableEvent2.setCellFactory(param -> new ListCell<Categorie>() {
    @Override
    protected void updateItem(Categorie item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.toString()); // or any other method that returns a string representation of the Reclamation object
            setFont(Font.font("Arial", FontWeight.BOLD, 24));
        }
    }
});
  });   
     //******************************
     
     
 ICategorieService categorieService = new CategorieService();
    List<Categorie> categoriesList = categorieService.afficherCategories();

    // ajouter les éléments de la liste retournée à l'ObservableList eventList
    eventList.addAll(categoriesList);
     tableEvent2.setItems(eventList);
    }
     
 
    
    
    
    void afficherCategories(){
            mc=MyConnection.getInstance().getCnx();
            eventList = FXCollections.observableArrayList();
      
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
               // eventList.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
 id.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getId_cat()));
  
  rate.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getrate())); //rate avec les etoiles
  rate.setCellFactory(col -> new StarRatingCell());
  
  nom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
 // date.setCellValueFactory(data -> new SimpleObjectProperty(data.getValue().getDate_event()));


  description.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescription_cat()));
  
  //tableEvent2.setItems(eventList);
   tableEvent.setItems(eventList);
   
  
 // refresh();
  
  }
  



    

    @FXML
    private void addEvent(MouseEvent event) throws IOException {
        String rate =rateEvent.getText();
        String nom =nomEvent.getText();
      //  String date =dateEvent.getText();
        String description =descriptionEvent.getText();
        
        if (rate.isEmpty() || nom.isEmpty()|| description.isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Il y a un champ vide !!"); // controle de saisie vide
             alert.showAndWait();   
             
         }
        
  
        
        
        else if(Integer.parseInt(rate)>5)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("MAL RATING !!"); // controle de saisie aal rate 
             alert.showAndWait();
        
        }
        
         else{
        
         java.sql.Date currentDate = new java.sql.Date( System.currentTimeMillis() );

        Categorie e= new Categorie(1,Integer.parseInt(rate),nom,currentDate,description);
        
        ICategorieService es= new CategorieService();
        es.ajouterCategorie(e);                                //appel de la fonction ajouter        
        
        rateEvent.setText(null);
        nomEvent.setText(null);                               //yrodlik les text area vide baad AJOUT
      // dateEvent.setText(null);
        descriptionEvent.setText(null);
          Parent root3 = FXMLLoader .load(getClass().getResource("CategorieFXML.fxml"));
    Stage window = (Stage) btStat.getScene().getWindow();
    window.setScene(new Scene(root3));
        
     //   refresh();
    }
    }
    
    
    
    @FXML
    private void closeEvent(MouseEvent event) {
        Stage stage =(Stage) fermerEvent.getScene().getWindow();
        stage.close();  
        }
    
    
    
    @FXML
    private void deleteEvent(MouseEvent event) throws IOException {
      
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setHeaderText("Warning");
       alert.setContentText("Es-tu sûre de supprimer!");
 
        String Value1 = idEvent.getText();
        String rate =rateEvent.getText();
        String nom =nomEvent.getText();
       // String date =dateEvent.getText();
       Date date = null;
        String description =descriptionEvent.getText();
        
        
        Optional<ButtonType>result =  alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
               java.sql.Date currentDate = new java.sql.Date( System.currentTimeMillis() );

         Categorie e= new Categorie(Integer.parseInt(Value1),Integer.parseInt(rate),nom,date,description);
        ICategorieService es= new CategorieService();
        es.supprimerCategorie(e);
     //   refresh();
      
        rateEvent.setText(null);
        nomEvent.setText(null);
      //  dateEvent.setText(null);
        descriptionEvent.setText(null);
               Parent root3 = FXMLLoader .load(getClass().getResource("CategorieFXML.fxml"));
    Stage window = (Stage) btStat.getScene().getWindow();
    window.setScene(new Scene(root3));   
        }
        
        }
    
  
    
    
    
    @FXML
    private void updateEvent(MouseEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Es-tu sûre de modifier!");
        
        
        
        String Value1 = idEvent.getText();
        String rate =rateEvent.getText();
        String nom =nomEvent.getText();
     //   String date =dateEvent.getText();
        String description =descriptionEvent.getText();
        
        if (rate.isEmpty() || nom.isEmpty()|| description.isEmpty()){
            
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("Il y a un champ vide !!"); // controle de saisie
             alert.showAndWait();          
         }
        
       
        
        
        else if(Integer.parseInt(rate)>5)
        {
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setContentText("MAL RATING !!"); // controle de saisie
             alert.showAndWait();
        
        }
        
         else{
            Optional<ButtonType>result =  alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
                         //  java.sql.Date currentDate = new java.sql.Date( System.currentTimeMillis() );

         Categorie e= new Categorie(Integer.parseInt(Value1),Integer.parseInt(rate),nom,description);
        ICategorieService es= new CategorieService();
        es.modifierCategorie(e);
          Parent root3 = FXMLLoader .load(getClass().getResource("CategorieFXML.fxml"));
    Stage window = (Stage) btStat.getScene().getWindow();
    window.setScene(new Scene(root3));
        
      //  refresh();
    }
    }
    }
    
    
    
    @FXML
    private void selected(MouseEvent event) {
        Categorie clicked = tableEvent.getSelectionModel().getSelectedItem();
        idEvent.setText(String.valueOf(clicked.getId_cat()));
        rateEvent.setText(String.valueOf(clicked.getrate()));
        nomEvent.setText(String.valueOf(clicked.getNom()));
     //   dateEvent.setText(String.valueOf(clicked.getDate_event()));
        descriptionEvent.setText(String.valueOf(clicked.getDescription_cat()));
    }
    
    
   
    
    
//    private void search() {      
//        FilteredList<Categorie>filteredData = new FilteredList<>(eventList, b->true);
//        rechercher.textProperty().addListener((observable, oldValue, newValue)->{
//            filteredData.setPredicate(Categorie->{
//                if(newValue == null || newValue.isEmpty()){
//                    return true;
//                }
//               
//                String lowerCaseFilter = newValue.toLowerCase(); 
//                 if(String.valueOf(Categorie.getNom()).indexOf(lowerCaseFilter) != -1){
//                    return true;
//                }
//                  else if(String.valueOf(Categorie.getDate_event()).indexOf(lowerCaseFilter) != -1){
//                    return true;
//                }else{
//                return false;
//                }
//            });          
//        });
//        SortedList<Categorie>sortedData = new SortedList<>(filteredData);
//        sortedData.comparatorProperty().bind(tableEvent.comparatorProperty());
//        tableEvent2.setItems(sortedData);
//    }
    
         @FXML
    private void btStat(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("Statistics.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println("error" + ex.getMessage());
    }
}
    
    
     @FXML
    private void Filtrer(ActionEvent event) {
           //EvenementService es = new EvenementService();
    ObservableList<Categorie> eventsList = FXCollections.observableArrayList();
   // java.sql.Date currentDate = new java.sql.Date( System.currentTimeMillis() );
  //  ReclamationDao pdao = ReclamationDao.getInstance();
    ICategorieService categorieService = new CategorieService();
    java.sql.Date startDate = java.sql.Date.valueOf(dd.getValue());
    java.sql.Date endDate = java.sql.Date.valueOf(df.getValue());
    List<Categorie> filteredEvents = categorieService.filtrerParDate(startDate, endDate);
    eventsList.clear();
    eventsList.addAll(filteredEvents);
    tableEvent2.setItems(eventsList);
    }
    
    
    
    
    
   
    
    
}
