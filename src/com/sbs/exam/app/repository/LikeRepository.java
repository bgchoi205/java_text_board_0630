package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.dto.Like;
import com.sbs.exam.app.dto.Member;

public class LikeRepository {
	private List<Like> likes;

	public LikeRepository() {
		likes = new ArrayList<>();
	}

	public Like getLikeByRelTypeAndRelId(String relTypeCode, int relId) {
		for(Like like : likes) {
			if(like.getRelTypeCode().equals(relTypeCode) && like.getRelId() == relId) {
				return like;
			}
		}
		return null;
	}

	public void addLike(String relTypeCode, int relId, int loginedMemberId, boolean isLike, boolean isDislike) {
		Like newLike = new Like(relTypeCode, relId, loginedMemberId, isLike, isDislike);
		likes.add(newLike);
	}

}
