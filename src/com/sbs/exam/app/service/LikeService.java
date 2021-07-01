package com.sbs.exam.app.service;

import com.sbs.exam.app.dto.Like;
import com.sbs.exam.app.repository.LikeRepository;
import com.sbs.exam.app.repository.MemberRepository;
import com.sbs.exam.app.container.Container;

public class LikeService {
	private LikeRepository likeRepository;

	public LikeService() {
		likeRepository = Container.getLikeRepository();
	}

	public Like getLikeByRelTypeAndRelId(String relTypeCode, int relId) {
		return likeRepository.getLikeByRelTypeAndRelId(relTypeCode, relId);
	}

	public void addLike(String relTypeCode, int relId, int loginedMemberId, boolean isLike, boolean isDislike) {
		likeRepository.addLike(relTypeCode, relId, loginedMemberId, isLike, isDislike);
	}

	public void removeLike(Like like) {
		likeRepository.removeLike(like);
	}

}
