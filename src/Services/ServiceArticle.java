package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import Entities.Article;
 import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import db.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServiceArticle {

	private Connection connection = DataBaseConnection.getInstance().getConnection();;

	public ServiceArticle() {
	}

	public void ajouter(Article article) {
		try {
			String query = "INSERT INTO article (user_id, title, content,likes,image_code_qr) VALUES (?, ?, ?, ?,?);";

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, article.getUserId());
			preparedStatement.setString(2, article.getTitle());
			preparedStatement.setString(3, article.getContent());
			preparedStatement.setInt(4, article.getLike());
			preparedStatement.setString(5, article.getImage_code_qr());
			preparedStatement.executeUpdate();
			System.out.println("L'article a �t� ajout� avec succ�s !");
                         // Call function to send email
               
           
                String accountSid = "AC37b0be76e041951e9ed53dfc9035d5ca";
               String authToken = "163686bef95fe3680eb8ff15c79738f0";
               Twilio.init(accountSid, authToken);
               String fromPhoneNumber = "+16813256343"; // Change this to your Twilio phone number
               String toPhoneNumber = "+21655947170"; // Change this to the user's phone number
               String smsBody = "Bonjour, un nouvelle Article a ajouter pour vous. Cordialement, L'�quipe de support";
        Message message = Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(fromPhoneNumber), smsBody).create();
               System.out.println("SMS sent successfully to " + toPhoneNumber);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de l'ajout de l'article.");
		}
                    // Send SMS to user
               
	}

	public void supprimer(int id) {
		try {
			String query = "DELETE FROM article WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			System.out.println("L'article a �t� supprim� avec succ�s !");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de la suppression de l'article.");
		}
	}

	public void modifier(Article article, int id) {
		try {
			String query = "UPDATE article SET user_id = ?, title = ?, content = ?, likes = ? WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, article.getUserId());
			preparedStatement.setString(2, article.getTitle());
			preparedStatement.setString(3, article.getContent());
			preparedStatement.setInt(4, article.getLike());
			preparedStatement.setInt(5, id);
			preparedStatement.executeUpdate();
			System.out.println("L'article a �t� modifi� avec succ�s !");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de la modification de l'article.");
		}
	}

	public ObservableList<Article> afficherTous() {
		ObservableList<Article> articles = FXCollections.observableArrayList();
		try {

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM article");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				int userId = resultSet.getInt("user_id");
				String title = resultSet.getString("title");
				String content = resultSet.getString("content");
				int like = resultSet.getInt("likes");
				Article article = new Article(id, userId, title, content, like);
				articles.add(article);
				System.out.println(article.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}

	public Article rechercherUserParId(int id) throws SQLException {
		Article article = null;
		String query = "SELECT * FROM article WHERE id = ?";

		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int like = rs.getInt("likes");
				article = new Article(id, userId, title, content, like);
			}

			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return article;
	}

	public void increment_like(int id) {
		try {
			String query = "UPDATE article SET likes = likes +1 WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			System.out.println("L'article a �t� modifi� avec succ�s !");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erreur lors de la modification de l'article.");
		}
	}

}
