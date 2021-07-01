package com.sbs.exam.app.comparator;

import java.util.Comparator;

import com.sbs.exam.app.dto.Article;

import lombok.Data;

@Data
public class Asc implements Comparator<Article>{
	String columnType;
	
	public Asc(String getColumnType) {
		columnType = getColumnType;
	}

	
	@Override
	public int compare(Article a, Article b)
	{	
		switch(columnType) {
		case  "id" :
			return Integer.compare(a.getId(), b.getId());
		case  "title" :
			return a.getTitle().compareTo(b.getTitle());
		case  "hitCount" :
			return Integer.compare(a.getHitCount(), b.getHitCount());
		case  "likeCount" :
			return Integer.compare(a.getLikeCount(), b.getLikeCount());
		case  "dislikeCount" :
			return Integer.compare(a.getDislikeCount(), b.getDislikeCount());
		}
		
		return Integer.compare(a.getId(), b.getId());
	}
	
}
