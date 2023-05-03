/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entities.CarteFidelite;
import Entities.abonnement;
import Entities.promotion;
import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import Services.promotionService;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import Services.abonnementService;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.twilio.rest.api.v2010.account.message.Media;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.MediaPlayer;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import Services.Pdf2;

import javafx.scene.media.MediaPlayer;
import java.nio.file.Paths;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;















/**
 * FXML Controller class
 *
 * @author asus
 */
public class AjouterabonnementController implements Initializable {

    @FXML
    private TextField descriptionabonnField;
    @FXML
    private DatePicker dateabonnField;
    @FXML
    private TextField typeabonnField;
    @FXML
    private TextField imageabonnField;
    @FXML
    private TextField nom_abonnField;
    @FXML
    private Button supprimerBoutton;
    @FXML
    private Button ajouterButton;
    @FXML
    private Button afficherBoutton;
    @FXML
    private Button goToPong;
  
    @FXML
    private TableView<abonnement> abonnementTv;
    @FXML
    private TableColumn<abonnement, String> nomabonnTv;
    @FXML
    private TableColumn<abonnement, String> typeabonnTv;
    @FXML
    private TableColumn<abonnement, String> imageabonnTv;
    @FXML
    private TableColumn<abonnement, String> dateabonnTv;
    @FXML
    private TableColumn<abonnement, String> descriptionabonnTv;
     @FXML
    private TableColumn<abonnement, Integer> code_promoTv;
     @FXML
private VBox root;

    @FXML
    private TextField code_promoField;
    
    private Date date1;
    @FXML
    private Label partError;
    @FXML
    private Label idLabel;
 
    ObservableList<abonnement> abonns;
    abonnementService Ev=new abonnementService();
    promotionService Pservice =new promotionService();
    Pdf2 oo=new Pdf2();
    
    @FXML
    private TextField idmodifierField;
    @FXML
    private Button participerbutton;
    @FXML
    private ImageView imageview;
    @FXML
    private TextField rechercher;
    @FXML
    private ImageView QrCode;
    @FXML
    private ImageView GoBackBtn;
    @FXML
    private Canvas myCanvas;

    
    /**
     * Initializes the controller class.
     */
@Override
public void initialize(URL url, ResourceBundle rb) {
 

    partError.setVisible(false);
    //idLabel.setText("");
    getabonns(); 
    getabonnss();
}




    
  
     private boolean NoDate() {
         LocalDate currentDate = LocalDate.now();     
         LocalDate myDate = dateabonnField.getValue(); 
         int comparisonResult = myDate.compareTo(currentDate);      
         boolean test = true;
        if (comparisonResult < 0) {
        // myDate est antérieure à currentDate
        test = true;
        } else if (comparisonResult > 0) {
         // myDate est postérieure à currentDate
         test = false;
        }
        return test;
    }
          @FXML
    private void ajouterabonnement(ActionEvent abonn) {
    
         int part=0;
        if ((nom_abonnField.getText().length() == 0) || (typeabonnField.getText().length() == 0) || (imageabonnField.getText().length() == 0) || (code_promoField.getText().length() == 0)|| (descriptionabonnField.getText().length() == 0)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be empty");
            alert.showAndWait();
        }
       else if (NoDate() == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("la date date doit être aprés la date d'aujourd'hui");
            alert.showAndWait();
        }
       else{     
            try {
                part = Integer.parseInt(code_promoField.getText());
                partError.setVisible(false);
            } catch (Exception exc) {
                System.out.println("Number of code_promo int");
                partError.setVisible(true);
                return;
            }
            if(part<10)
            {Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("le code promo doit être suupp ou egale  à 10");
            alert.showAndWait();
            partError.setVisible(true);}
            else
            {
        abonnement e = new abonnement();
        if (typeabonnField.getText().equals("Gold")) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText("Prix de l'abonnement");
    alert.setContentText("Le prix de cette abonnement est 250 DT.");
    alert.showAndWait();
} else if (typeabonnField.getText().equals("SILVER")) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText("Prix de l'abonnement");
    alert.setContentText("Le prix de cette abonnement est 150 DT.");
    alert.showAndWait();
} else if (typeabonnField.getText().equals("BRONZE")) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText("Prix de l'abonnement");
    alert.setContentText("Le prix de cette abonnement est 100 DT.");
    alert.showAndWait();
}

        e.setNom_abonn(nom_abonnField.getText());
        e.setType_abonn(typeabonnField.getText());
        e.setDescription_abonn(descriptionabonnField.getText());
        java.util.Date date_debut=java.util.Date.from(dateabonnField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date sqlDate = new Date(date_debut.getTime());
        e.setDate(sqlDate);
        e.setCode_promo(Integer.valueOf(code_promoField.getText()));
        
        //lel image
        e.setImage_abonn(imageabonnField.getText());      
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information ");
            alert.setHeaderText("abonnement add");
            alert.setContentText("abonnement added successfully!");
            alert.showAndWait();      
        try {
            Ev.ajouterabonnement(e);
            reset();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }      
        getabonns();
        getabonnss();
        

    }}}
    
    //fin d ajout d'un abonnement
    private void reset() {
        nom_abonnField.setText("");
        typeabonnField.setText("");
        descriptionabonnField.setText("");
        imageabonnField.setText("");
        code_promoField.setText("");
        dateabonnField.setValue(null);    
    }
    
   public void getabonns() {  
         try {
            // TODO
            List<abonnement> abonnements = Ev.recupererabonnement();
            ObservableList<abonnement> olp = FXCollections.observableArrayList(abonnements);
            abonnementTv.setItems(olp);
            nomabonnTv.setCellValueFactory(new PropertyValueFactory("nom_abonn"));
            typeabonnTv.setCellValueFactory(new PropertyValueFactory("type_abonn"));
            imageabonnTv.setCellValueFactory(new PropertyValueFactory("image_abonn"));
            dateabonnTv.setCellValueFactory(new PropertyValueFactory("date"));
            descriptionabonnTv.setCellValueFactory(new PropertyValueFactory("description_abonn"));
            code_promoTv.setCellValueFactory(new PropertyValueFactory("code_promo"));
            
            
           // this.delete();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//get abonns
   public void getabonnss() {  
    try {
        List<abonnement> abonnements = Ev.recupererabonnement();
        int pageSize = 5;
        int pageCount = (int) Math.ceil((double) abonnements.size() / pageSize);
        Pagination pagination = new Pagination(pageCount, 0);
        
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            int startIndex = newIndex.intValue() * pageSize;
            int endIndex = Math.min(startIndex + pageSize, abonnements.size());
            ObservableList<abonnement> page = FXCollections.observableArrayList(abonnements.subList(startIndex, endIndex));
            abonnementTv.setItems(page);
        });
        nomabonnTv.setCellValueFactory(new PropertyValueFactory("nom_abonn"));
        typeabonnTv.setCellValueFactory(new PropertyValueFactory("type_abonn"));
        imageabonnTv.setCellValueFactory(new PropertyValueFactory("image_abonn"));
        dateabonnTv.setCellValueFactory(new PropertyValueFactory("date"));
        descriptionabonnTv.setCellValueFactory(new PropertyValueFactory("description_abonn"));
        code_promoTv.setCellValueFactory(new PropertyValueFactory("code_promo"));
        VBox root = new VBox(abonnementTv, pagination);
this.root.getChildren().setAll(root);
    } catch (SQLException ex) {
        System.out.println("error" + ex.getMessage());
    }
}
     
     @FXML
   private void modifierabonnement(ActionEvent abonn) throws SQLException {
        abonnement e = new abonnement();
        e.setId_ab(Integer.valueOf(idmodifierField.getText()));
        e.setNom_abonn(nom_abonnField.getText());
        e.setType_abonn(typeabonnField.getText());
        e.setDescription_abonn(descriptionabonnField.getText()); 
        Date d=Date.valueOf(dateabonnField.getValue());
        e.setDate(d);
        e.setImage_abonn(imageabonnField.getText());
        e.setCode_promo(Integer.valueOf(code_promoField.getText()));         
        Ev.modifierabonnement(e);
        reset();
        getabonns();   
        getabonnss();
    }

    @FXML
    private void supprimerabonnement(ActionEvent abonn) {
           abonnement e = abonnementTv.getItems().get(abonnementTv.getSelectionModel().getSelectedIndex());
        try {
            Ev.supprimerabonnement(e);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterabonnementController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("abonnement delete");
        alert.setContentText("abonnement deleted successfully!");
        alert.showAndWait();        
        getabonns(); 
        getabonnss();
    }

    @FXML
    private void afficherabonnement(ActionEvent abonn) {
         try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("afficherabonnement.fxml"));
            typeabonnField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

  
    @FXML
    //ta3 tablee bch nenzel 3ala wehed ya5tarou w yet3abew textfield
    private void choisirabonn(MouseEvent abonn) throws IOException {
        abonnement e = abonnementTv.getItems().get(abonnementTv.getSelectionModel().getSelectedIndex());     
        //idLabel.setText(String.valueOf(e.getid_ab()));
        idmodifierField.setText(String.valueOf(e.getId_ab()));
        nom_abonnField.setText(e.getNom_abonn());
        typeabonnField.setText(e.getType_abonn());
        imageabonnField.setText(e.getImage_abonn());
        descriptionabonnField.setText(e.getDescription_abonn());
        //dateabonnField.setValue((e.getDate()));
        code_promoField.setText(String.valueOf(e.getCode_promo()));       
        //lel image
        String path = e.getImage_abonn();
               File file=new File(path);
              Image img = new Image(file.toURI().toString());
                imageview.setImage(img);
                
        //////////////      
            String filename = Ev.GenerateQrabonn(e);
            System.out.println("filename lenaaa " + filename);
            String path1="C:\\xampp\\htdocs\\xchangex\\imgQr\\qrcode"+filename;
             File file1=new File(path1);
              Image img1 = new Image(file1.toURI().toString());
              //Image image = new Image(getClass().getResourceAsStream("src/utils/img/" + filename));
            QrCode.setImage(img1); 
            
    }

    private void participer(ActionEvent abonn) {

        CarteFidelite u=new CarteFidelite();
        LocalDate dateActuelle = LocalDate.now();
        Date dateSQL = Date.valueOf(dateActuelle);
        promotion p=new promotion();
        p.setDate_part(dateSQL);
        //p.setabonnement();
        p.setId_abonnement(Integer.parseInt(idmodifierField.getText()));
        p.setId_CarteFidelite(u.getId());
        Pservice.ajouterpromotion(p);
    }

    @FXML
    private void afficherpromotions(ActionEvent abonn) {     
         try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("afficherpromotion.fxml"));
            typeabonnField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }    
    }

    @FXML
    private void uploadImage(ActionEvent abonn)throws FileNotFoundException, IOException  {

        Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String DBPath = "C:\\\\xampp\\\\htdocs\\\\imageP\\\\"  + x + ".jpg";
        if (file != null) {
            FileInputStream Fsource = new FileInputStream(file.getAbsolutePath());
            FileOutputStream Fdestination = new FileOutputStream(DBPath);
            BufferedInputStream bin = new BufferedInputStream(Fsource);
            BufferedOutputStream bou = new BufferedOutputStream(Fdestination);
            System.out.println(file.getAbsoluteFile());
            String path=file.getAbsolutePath();
            Image img = new Image(file.toURI().toString());
            imageview.setImage(img);    
            imageabonnField.setText(DBPath);
            int b = 0;
            while (b != -1) {
                b = bin.read();
                bou.write(b);
            }
            bin.close();
            bou.close();          
        } else {
            System.out.println("error");
        }
    }

   @FXML
private void excelabonn(ActionEvent abonn) {
try {
String filename = "C://xampp//htdocs//fichierExcelJava//dabonn.xls";
HSSFWorkbook hwb = new HSSFWorkbook();
HSSFSheet sheet = hwb.createSheet("new sheet");                                                                                                                             // Création de l'entête
    HSSFRow rowhead = sheet.createRow(0);
    rowhead.createCell(0).setCellValue("Nom Abonnement");
    rowhead.createCell(1).setCellValue("Type d'abonnement");
    rowhead.createCell(2).setCellValue("Description");

    // Récupération des abonnements
    List<abonnement> abonnements = Ev.recupererabonnement();

    // Remplissage du tableau
    for (int i = 0; i < abonnements.size(); i++) {
        HSSFRow row = sheet.createRow(i + 1);
        row.createCell(0).setCellValue(abonnements.get(i).getNom_abonn());
        row.createCell(1).setCellValue(abonnements.get(i).getType_abonn());
        row.createCell(2).setCellValue(abonnements.get(i).getDescription_abonn());
    }

    // Écriture du fichier Excel
    FileOutputStream fileOut = new FileOutputStream(filename);
    hwb.write(fileOut);
    fileOut.close();
    System.out.println("Your excel file has been generated!");

    // Ouverture du fichier Excel
    File file = new File(filename);
    if (file.exists()) {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        }
    }
} catch (Exception ex) {
    System.out.println(ex);
}
}
    
    @FXML
    private void pdfabonn(ActionEvent abonn) throws FileNotFoundException, SQLException, IOException {        
       // abonnement tab_Recselected = abonnementTv.getSelectionModel().getSelectedItem();
               long millis = System.currentTimeMillis();
        java.sql.Date DateRapport = new java.sql.Date(millis);

        String DateLyoum = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH).format(DateRapport);//yyyyMMddHHmmss
        System.out.println("Date d'aujourdhui : " + DateLyoum);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(DateLyoum + ".pdf")));//yyyy-MM-dd
            document.open();
            Paragraph ph1 = new Paragraph("Voici un rapport détaillé de notre application qui contient tous les Abonnements . Pour chaque Abonnement, nous fournissons des informations telles que la date d'Aujourd'hui :" + DateRapport );
            Paragraph ph2 = new Paragraph(".");
            PdfPTable table = new PdfPTable(4);
            //On créer l'objet cellule.
            PdfPCell cell;
            //contenu du tableau.
            table.addCell("nom_abonn");
            table.addCell("type_abonn");
            table.addCell("description_abonn");
            table.addCell("image_abonn");
             
            abonnement r = new abonnement();
            Ev.recupererabonnement().forEach(new Consumer<abonnement>() {
                @Override
                public void accept(abonnement e) {
                    table.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(String.valueOf(e.getNom_abonn()));
                    table.addCell(String.valueOf(e.getType_abonn()));
                    table.addCell(String.valueOf(e.getDescription_abonn()));
                    try {
    // Créer un objet Image à partir de l'image
    String path = e.getImage_abonn();
    com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(path);
    
    // Définir la taille de l'image dans le tableau
    img.scaleToFit(100, 100); // Définir la largeur et la hauteur de l'image
    
    // Ajouter l'image à la cellule du tableau
    PdfPCell cell = new PdfPCell(img);
    table.addCell(cell);
} catch (Exception ex) {
    table.addCell("Erreur lors du chargement de l'image");
}
         }
            });
            document.add(ph1);
            document.add(ph2);
            document.add(table);
             } catch (Exception e) {
            System.out.println(e);
        }
        document.close();

        ///Open FilePdf
        File file = new File(DateLyoum + ".pdf");
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) //checks file exists or not  
        {
            desktop.open(file); //opens the specified file   
        }
    }
    
    @FXML
    private void rechercherabonn(KeyEvent abonn) {
        
        abonnementService bs=new abonnementService(); 
        abonnement b= new abonnement();
        ObservableList<abonnement>filter= bs.chercherabonn(rechercher.getText());
        populateTable(filter);
    }
     private void populateTable(ObservableList<abonnement> branlist){
       abonnementTv.setItems(branlist);
   
       }
       @FXML
    private void GoBk(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Statistics.fxml"));
            Parent root = loader.load();

            // Set the root of the current scene to the new FXML file
            GoBackBtn.getScene().setRoot(root);
    }
  
@FXML
private void goToPong(ActionEvent abonn) {
    try {
            //navigation
            Parent loader = FXMLLoader.load(getClass().getResource("pppppp.fxml"));
            goToPong.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } // canvas est le nom de votre composant Canvas
}
@FXML
    public void openModifInterface6(ActionEvent event) {
        try {
            // Load the Modif.fxml file
        	 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AcceuilAdmin.fxml"));
            Parent root = loader.load();

            // Show the Modif.fxml interface
            Scene scene = new Scene(root);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
}


    }


    





    

