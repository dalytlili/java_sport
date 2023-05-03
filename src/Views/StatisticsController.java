package Views;

import Entities.Categorie;
import Service.CategorieService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class StatisticsController implements Initializable {

    @FXML
    private BarChart<String, Number> StatsChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    CategorieService rs = new CategorieService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            displayStatistics();
        } catch (SQLException ex) {
            Logger.getLogger(StatisticsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void displayStatistics() throws SQLException {
        // Récupérer toutes les activiter de la base de données
        List<Categorie> Activite = rs.afficherCategories();
        // Regrouper les activiter par type
        Map<String, Long> ActiviteParNom = Activite.stream()
                .collect(Collectors.groupingBy(Categorie::getNom, Collectors.counting()));

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Nombre de chaque nom");

        // Ajouter les éléments de données à la série
        ActiviteParNom.forEach((nom, nombre) -> dataSeries.getData().add(new XYChart.Data<>(nom, nombre)));

        // Ajouter la série à la chart
        StatsChart.getData().add(dataSeries);
        StatsChart.lookupAll(".default-color0.chart-bar").forEach(n -> n.setStyle("-fx-bar-fill: green;"));

        // Configurer l'animation pour les données de la chart
        StatsChart.getData().forEach(series ->
                series.getData().forEach(data ->
                        
                        data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                            data.getNode().setScaleX(1.1);
                            data.getNode().setScaleY(1.1);
                        })
                )
        );
        StatsChart.getData().forEach(series ->
                series.getData().forEach(data ->
                        data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                            data.getNode().setScaleX(1.0);
                            data.getNode().setScaleY(1.0);
                        })
                )
        );
        // Configurer l'interactivité pour les données de la chart
        StatsChart.getData().forEach(series ->
                series.getData().forEach(data ->
                        data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                            // Obtenir la valeur actuelle de l'élément data
                            long currentValue = (long) data.getYValue();

                            // Afficher un message avec la valeur de data
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Statistique Abonnement(s)");
                            alert.setHeaderText(data.getXValue());
                            alert.setContentText("Nombre d'Abonnement : " + currentValue);
                            alert.showAndWait();
                        })
                )
        );
    }
}
