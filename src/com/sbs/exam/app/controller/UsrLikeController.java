package com.sbs.exam.app.controller;

import com.sbs.exam.app.Rq;
import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Article;
import com.sbs.exam.app.dto.Like;
import com.sbs.exam.app.dto.Member;
import com.sbs.exam.app.service.ArticleService;
import com.sbs.exam.app.service.BoardService;
import com.sbs.exam.app.service.LikeService;
import com.sbs.exam.app.service.MemberService;

public class UsrLikeController extends Controller {
		private LikeService likeService;
		private ArticleService articleService;
		private MemberService memberService;
		

		public UsrLikeController() {
			likeService = Container.getLikeService();
			articleService = Container.getArticleService();
			memberService = Container.getMemberService();
		}
	
	@Override
	public void performAction(Rq rq) {
		switch(rq.getActionPath()) {
			case "/usr/like/like" :
				actionLike(rq);
				return;
			case "/usr/like/cancelLike" :
				actionCancelLike(rq);
				return;
		}
		
	}

	
	// 좋아요 취소 눌렀을 때
	private void actionCancelLike(Rq rq) {
		String relTypeCode = rq.getStrParam("relTypeCode", "");
		int relId = rq.getIntParam("id", 0);
		
		if(relTypeCode.isEmpty() || relId == 0) {
			System.out.println("좋아요(싫어요) 취소 대상을 입력해주세요.");
			return;
		}
		
		doCancelLikeByRelTypeAndRelId(relTypeCode, relId);
	}

	//좋아요 취소 실행
	private void doCancelLikeByRelTypeAndRelId(String relTypeCode, int relId) {
		if(relTypeCode.equals("article")) {
			Article article = articleService.getArticleById(relId);
			if(article == null) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}
			
			Like like = likeService.getLikeByRelTypeAndRelId("article", relId);
			Rq rq = new Rq();
			int loginedMemberId = rq.getLoginedMember().getId();
			
			if(like == null) {
				System.out.println("좋아요(싫어요)를 누르지 않은 게시물입니다.");
				return;
			} else {
				if(like.getMemberId() == loginedMemberId) {
					if(like.isLike() == false) {
						System.out.println("좋아요를 누르지 않은 게시물 입니다.");
						return;
					}else {
						likeService.removeLike(like);
						article.setLikeCount(article.getLikeCount() - 1);
						System.out.println(relId + "번 게시물 좋아요 취소");
						return;
					}
				}
			}
			
		}
		System.out.println("올바른 대상을 입력해주세요.");
		return;
	}
	
	

	// 좋아요 눌렀을때
	private void actionLike(Rq rq) {
		String relTypeCode = rq.getStrParam("relTypeCode", "");
		int relId = rq.getIntParam("id", 0);
		
		if(relTypeCode.isEmpty() || relId == 0) {
			System.out.println("좋아요(싫어요) 대상을 입력해주세요.");
			return;
		}
		
		doLikeByRelTypeAndRelId(relTypeCode, relId);
		
	}

	// 좋아요 실행
	private void doLikeByRelTypeAndRelId(String relTypeCode, int relId) {
		if(relTypeCode.equals("article")) {
			Article article = articleService.getArticleById(relId);
			if(article == null) {
				System.out.println("존재하지 않는 게시물입니다.");
				return;
			}
			
			Like like = likeService.getLikeByRelTypeAndRelId("article", relId);
			Rq rq = new Rq();
			int loginedMemberId = rq.getLoginedMember().getId();
			
			if(like != null) {
				if(like.getMemberId() == loginedMemberId) {
					if(like.isLike()) {
						System.out.println("이미 좋아요를 누른 게시물 입니다.");
						return;
					}else if(like.isDislike()) {
						System.out.println("이미 싫어요를 누른 게시물 입니다.");
						return;
					}else {
						like.setLike(true);
						article.setLikeCount(article.getLikeCount() + 1);
						System.out.println(relId + "번 게시물 좋아요 표시");
						return;
					}
				}
			}
			
			likeService.addLike("article", relId, loginedMemberId, true, false);
			article.setLikeCount(article.getLikeCount() + 1);
			
			System.out.println(relId + "번 게시물 좋아요 표시");
			return;
		}
		System.out.println("올바른 대상을 입력해주세요.");
		return;
	}

	
	
	
	
}
