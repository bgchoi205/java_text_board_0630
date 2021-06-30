package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.Rq;
import com.sbs.exam.app.dto.Article;
import com.sbs.exam.util.Util;

public class ArticleRepository {
	private List<Article> articles;
	private int lastId;

	public ArticleRepository() {
		articles = new ArrayList<>();
		lastId = 0;
	}

	public int write(String title, String body) {
		Rq rq = new Rq();
		
		int id = lastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;
		int memberId = rq.getLoginedMember().getId();

		Article article = new Article(id, regDate, updateDate, 1, memberId, title, body);
		articles.add(article);

		lastId = id;

		return id;
	}

	public void deleteArticleById(int id) {
		Article article = getArticleById(id);
		
		if ( article != null ) {
			articles.remove(article);
		}
	}

	public Article getArticleById(int id) {
		for ( Article article : articles ) {
			if ( article.getId() == id ) {
				return article;
			}
		}
		
		return null;
	}

	public List<Article> getArticles() {
		return articles;
	}

}
