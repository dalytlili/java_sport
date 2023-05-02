package gui;
 
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import Entities.Article;
import Services.ServiceArticle;
 
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
 
 

public class ListeDesArticleController implements Initializable{
     
	  
  

    @FXML
    private Button btn1;
    @FXML
    private TableColumn<Article, String> commentaire;

    @FXML
    private TableColumn<Article, String> emailcolumn;

    @FXML
    private TableColumn<Article, String> nom;
    @FXML
    private TableColumn<Article, String> date;
    @FXML
    private TableColumn<Article, String> respond;

 

    @FXML
    private TableView<Article> table;
 
   
 @FXML
 private TextField filtre ;
    @FXML
    private Button btn11;
    
    

    
     
    
    
     
    
    ObservableList<Article> listeB = FXCollections.observableArrayList();
    @FXML
    public void show(){
    ServiceArticle bs=new ServiceArticle();
    listeB=bs.afficherTous();
        nom.setCellValueFactory(new PropertyValueFactory<>("id"));
        commentaire.setCellValueFactory(new PropertyValueFactory<>("title"));
        date.setCellValueFactory(new PropertyValueFactory<>("content"));
     
       

        respond.setCellValueFactory(new PropertyValueFactory<>("like"));
  
    
 
        table.setItems(listeB);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
    show();
}

 
 
    
    @FXML
    void Creer(ActionEvent event) {

    }
    private boolean isAscendingOrder = true;

    @FXML
    public void tri() {
        ObservableList<Article> productList = table.getItems();
        
        if (isAscendingOrder) {
            productList.sort(Comparator.comparing(Article::getLike));
            isAscendingOrder = false;
        } else {
            productList.sort(Comparator.comparing(Article::getLike).reversed());
            isAscendingOrder = true;
        }
        
        table.setItems(productList);
    }
    @FXML
    public void Acceuil(ActionEvent event) {
        try {
        	 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.close();
            // Load the Modif.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AcceuilAdmin.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.show();

        } catch (IOException e) {
            e.printStackTrace();
        }}
    @FXML
    public void handleSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String searchText = filtre.getText().trim();
            if (searchText.isEmpty()) {
                table.setItems(listeB);
            } else {
                ObservableList<Article> filteredList = FXCollections.observableArrayList();
                boolean productFound = false;
                for (Article b : listeB) {
                    // search for name or description
                    if ((b.getTitle().toLowerCase().contains(searchText.toLowerCase())) 
                            || (b.getContent().toLowerCase().contains(searchText.toLowerCase()))) {
                        filteredList.add(b);
                        productFound = true;
                    }
                }
                if (!productFound) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle(" Article non trouvé");
                    alert.setHeaderText("Aucun Article ne correspond à votre recherche");
                    alert.setContentText("Veuillez essayer une autre recherche.");
                    alert.showAndWait();
                }
                table.setItems(filteredList);
            }
        }
    }
}

