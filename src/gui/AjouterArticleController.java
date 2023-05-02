package gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.GapContent;

import Entities.Article;
import Entities.User;
import Services.ServiceArticle;
import Services.ServiceUser;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
   

public class AjouterArticleController implements Initializable {

	@FXML
	private Label nom;
	@FXML
	private Button btnAjouter;
  
    
    String image="";
	@FXML
	private Button btnChoisir;
	@FXML
	private ImageView imageview;
	@FXML
	private TextField textdesc;
	 

	Article nouvelleArticle = new Article();

	@FXML
	private TextField txtnom;

	@FXML
	private TextField txtnumF;

	@FXML
	private TextField txtnumT;
	@FXML
	private ComboBox<User> comboBoxUser;
	@FXML
	private Stage stage;
	private ServiceUser serviceUser;
	private ObservableList<String>UserList;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO
		serviceUser = new ServiceUser();
		UserList = FXCollections.observableArrayList();
		setComboBoxItems();
 
		};
		private void setComboBoxItems() {
			// Retrieve all reclamations and their corresponding users
			ObservableList<User> users = serviceUser.afficherTous();

			// Add the user names to the list

			// Set the items of the combo box
			comboBoxUser.setItems(users);
			
			comboBoxUser.setConverter(new StringConverter<>() {
	            @Override
	            public String toString(User object) {
	                if (object != null) {
	                    return object.getFirstName();
	                } else return "";
	            }

	            @Override
	            public User fromString(String string) {
	                return comboBoxUser.getSelectionModel().getSelectedItem();
	            }
	        });
		}

	@FXML
	void ajouter(ActionEvent event) {
		boolean test = true;
		String title;
		String content ;
		title = txtnom.getText();
 
		content = textdesc.getText();
	 
 

		if ((txtnom.getText().isEmpty()) || (textdesc.getText().isEmpty()) ) {
		 
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("oops");
				alert1.setHeaderText(null);
				alert1.setContentText("remplir tous les champs");
				alert1.showAndWait();
				return;

			}
		 

 
	 

		else {

			this.nouvelleArticle.setTitle(title);
 
 			 
			this.nouvelleArticle.setContent(content);
			
			this.nouvelleArticle.setUserId( comboBoxUser.getSelectionModel().getSelectedItem().getId());
		      String text = "Code QR pour le produit " + title;
	            ByteArrayOutputStream out = QRCode.from(text).to(ImageType.PNG).stream();

	            // Convertir l'image en une chaîne de caractères encodée en base64
	            String base64Image = Base64.getEncoder().encodeToString(out.toByteArray());
	            
	            System.out.println("Base64 avant : " + base64Image);
	            this.nouvelleArticle.setImage_code_qr(base64Image);	
	            ServiceArticle bs = new ServiceArticle();
	            image=base64Image;
	            // Afficher l'image dans une alerte
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Code QR pour  article " + title);
	            alert.setHeaderText(null);

	            // Créer un ImageView avec l'image du code QR encodée en base64
	            ImageView imageView = new ImageView();
	            imageView.setImage(new Image(new ByteArrayInputStream(Base64.getDecoder().decode( base64Image))));

	            // Ajouter l'ImageView à la boîte de dialogue de l'alerte
	            alert.getDialogPane().setContent(imageView);

	            // Afficher l'alerte
	            alert.showAndWait();
			bs.ajouter(this.nouvelleArticle );
			
			   Alert alert1 = new Alert(AlertType.INFORMATION);
	            alert1.setTitle("Confirmation");
	            alert1.setHeaderText(null);
	            alert1.setContentText("Ajouter Article est avec succer   " );
	            alert1.showAndWait();
	    	    try {
			    	// Fermer la première interface
			        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			        stage.close();
			        
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListeDesArticles.fxml"));
			        Parent root = loader.load();
			        Scene scene = new Scene(root);
			        Stage stage2 = new Stage();
			        stage2.setScene(scene);
			        stage2.show();
			    } catch (IOException ex) 
			    {
			        ex.printStackTrace();
			    }
			 
			}
		}
 

	

 
	 

	
		
		
		
 
	 

}
