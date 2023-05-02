package Services;

import java.util.List;

import Entities.Article;

public interface IServiceArticle {
	
	void ajouter(Article article);

	void supprimer(int id);

	void modifier(Article article, int id);

	List<Article> afficherTous();

	Article rechercherUserParId(int id);

}
