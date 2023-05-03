package gui;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Article;
import Entities.Comment;
import Entities.User;
import Services.ServiceArticle;
import db.DataBaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AfficherUserController implements Initializable {
	Connection connection = DataBaseConnection.getInstance().getConnection();

	public ObservableList<Comment> afficherT() {

		ObservableList<Comment> users = FXCollections.observableArrayList();

		try {
			String query = "SELECT comment.*, user.first_name FROM comment JOIN user ON comment.user_id = user.id";
			;
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int article_id = resultSet.getInt("article_id");
				int user_id = resultSet.getInt("user_id");
				String content = resultSet.getString("content");
				Timestamp timestamp = resultSet.getTimestamp("created_at");
				LocalDateTime localDateTime = timestamp.toLocalDateTime();
				String firstName = resultSet.getString("first_name");
				Comment comment = new Comment(id, user_id, article_id, content, localDateTime);
				comment.setUserName(firstName);
				users.add(comment);
				;
			}
		} catch (SQLException e) {
			System.err.println("Error executing SQL query: " + e.getMessage());
		}
		return users;
	}
 
	@FXML
	private ListView<Article> listView;

	public void initialize(URL location, ResourceBundle resources) {
		ServiceArticle es = new ServiceArticle();
		List<Article> Article = es.afficherTous();

		listView.setCellFactory(
				(Callback<ListView<Article>, ListCell<Article>>) new Callback<ListView<Article>, ListCell<Article>>() {
					public ListCell<Article> call(ListView<Article> param) {
						return new ListCell<Article>() {
							@Override
							protected void updateItem(Article item, boolean empty) {
								super.updateItem(item, empty);

								if (empty || item == null) {
									setText(null);
									setGraphic(null);
								} else {
									VBox vbox = new VBox();
									vbox.setSpacing(10);

									Label titleLabel = new Label(item.getTitle());
									titleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

									Label contentLabel = new Label(" Likes: " + item.getLike());
									contentLabel.setStyle("-fx-font-size: 14;");
									FontAwesomeIconView likeBtn = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);

									// D�finir la couleur de l'ic�ne en bleu
									likeBtn.setFill(Color.BLUE);
									likeBtn.setGlyphSize(30); // D�finir la taille de l'ic�ne du bouton � 30 pixels
									likeBtn.setOnMouseClicked(arg0 -> {
										ServiceArticle es = new ServiceArticle();
										es.increment_like(item.getId());

										// update like count for the article
										item.setLike(item.getLike() + 1);

										// update UI to reflect the change
										contentLabel.setText(" Likes: " + item.getLike());

										// disable the like button
										likeBtn.setDisable(true);
									});
									
									ListView<Comment> commentListView = new ListView<Comment>();
									commentListView.setItems(afficherT());
									commentListView.setPrefHeight(200);
									commentListView
											.setCellFactory((ListView<Comment> param) -> new ListCell<Comment>() {
												@Override
												protected void updateItem(Comment comment, boolean empty) {
													super.updateItem(comment, empty);

													if (empty || comment == null) {
														setText(null);
														setGraphic(null);
													} else {
														VBox vbox = new VBox();
														vbox.setSpacing(5);

														Label contentLabel = new Label(comment.getContent());
														contentLabel.setWrapText(true);

														Label authorLabel = new Label("By " + comment.getUserName()
																+ " at " + comment.getCreatedAt().toString());

														vbox.getChildren().addAll(contentLabel, authorLabel);

														setGraphic(vbox);
													}
												}
											});

									TextArea commentTextArea = new TextArea();
									commentTextArea.setPromptText("Add a comment...");
									commentTextArea.setWrapText(true);
									commentTextArea.setPrefHeight(50);

									Button commentButton = new Button("Comment");
									commentButton.setOnAction(event -> {
										String commentText = commentTextArea.getText();

										LocalDateTime now = LocalDateTime.now();
										Timestamp timestamp = Timestamp.valueOf(now);
										// update database to store the comment
										Connection connection = DataBaseConnection.getInstance().getConnection();
										String query = "INSERT INTO comment (article_id, user_id, content, created_at) VALUES (?, ?, ?, ?)";
										try (PreparedStatement statement = connection.prepareStatement(query)) {
											statement.setInt(1, item.getId());
											statement.setInt(2, item.getUserId());
											statement.setString(3, commentText);
											statement.setTimestamp(4, timestamp);
											statement.executeUpdate();
											System.out.println("  adding comment: ");
											commentListView.setItems(afficherT());
											commentListView.refresh();
										} catch (SQLException ex) {
											System.out.println("Error adding comment: " + ex.getMessage());

										}
									});

									vbox.getChildren().addAll(titleLabel, contentLabel, likeBtn, commentListView,
											commentTextArea, commentButton);

									setGraphic(vbox);
								}
							}

						};

					}
				});

		// Ajouter les �v�nements � la liste
		listView.getItems().addAll(Article);
                
	}
        @FXML
    public void openModifInterface4(ActionEvent event) {
        try {
            // Load the Modif.fxml file
        	 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherabonnement.fxml"));
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
       @FXML
    public void openModifInterface5(ActionEvent event) {
        try {
            // Load the Modif.fxml file
        	 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Activite.fxml"));
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
