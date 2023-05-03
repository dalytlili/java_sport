/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MainActiviteController implements Initializable {

    @FXML
    private Button btActivite;
    @FXML
    private Button btCategorie;

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
        // TODO
    }    

    @FXML
    private void btActivite(ActionEvent event) {
        try {
          Parent root = FXMLLoader.load(getClass().getResource("Activite.fxml"));
                    Scene sence = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(sence);
                    stage.show();
            
        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
       
    

    @FXML
    private void btCategorie(ActionEvent event) {
               try {
          Parent root = FXMLLoader.load(getClass().getResource("CategorieFXML.fxml"));
                    Scene sence = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(sence);
                    stage.show();
            
        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
    }
    

