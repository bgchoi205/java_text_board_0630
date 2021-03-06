package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sbs.exam.app.Rq;
import com.sbs.exam.app.comparator.Asc;
import com.sbs.exam.app.dto.Article;
import com.sbs.exam.app.dto.Member;
import com.sbs.exam.util.Util;

public class ArticleRepository {
	private List<Article> articles;
	private int lastId;

	public ArticleRepository() {
		articles = new ArrayList<>();
		lastId = 0;
	}

	public int write(int boardId, int memberId, String title, String body, int hitCount, int likeCount, int dislikeCount) {
		
		int id = lastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;
		

		Article article = new Article(id, regDate, updateDate, boardId, memberId, title, body, hitCount, likeCount, dislikeCount);
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

	public List<Article> getFilteredArticles(int boardId, String searchKeyword, String searchKeywordTypeCode) {
		List<Article> FilteredArticles = getFiltered1Articles(boardId, searchKeyword, searchKeywordTypeCode);
		
		return FilteredArticles;
	}

	private List<Article> getFiltered1Articles(int boardId, String searchKeyword, String searchKeywordTypeCode) {
		List<Article> Filtered1Articles = new ArrayList<>();
		if(boardId == 0) {
			Filtered1Articles =  articles;
		} else {
			for(Article article : articles) {
				if(article.getBoardId() == boardId) {
					Filtered1Articles.add(article);
				}
			}
		}
		Filtered1Articles = getFiltered2Articles(Filtered1Articles, searchKeyword, searchKeywordTypeCode);
		
		return Filtered1Articles;
	}

	private List<Article> getFiltered2Articles(List<Article> filtered1Articles, String searchKeyword, String searchKeywordTypeCode) {
		List<Article> Filtered2Articles = new ArrayList<>();
		if(searchKeyword.isEmpty()) {
			Filtered2Articles =  filtered1Articles;
		}else {
			if(searchKeywordTypeCode.equals("title")) {
				for(Article article : filtered1Articles) {
					if(article.getTitle().contains(searchKeyword)) {
						Filtered2Articles.add(article);
					}
				}
			} else if(searchKeywordTypeCode.equals("body")) {
				for(Article article : filtered1Articles) {
					if(article.getBody().contains(searchKeyword)) {
						Filtered2Articles.add(article);
					}
				}
			}
		}
//		Filtered2Articles = getFiltered3Articles(Filtered2Articles, orderByColumn, orderAscTypeCode);
		
		return Filtered2Articles;
	}

	
	public List<Article> getOrderedArticles(List<Article> articles2, String orderByColumn, String orderAscTypeCode) {
		if(orderAscTypeCode.equals("desc")) {
			Collections.sort(articles2, new Asc(orderByColumn));
		}else if(orderAscTypeCode.equals("asc")) {
			Collections.sort(articles2, new Asc(orderByColumn).reversed());
		}
		return articles2;
	}


}
