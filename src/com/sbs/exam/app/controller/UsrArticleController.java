package com.sbs.exam.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sbs.exam.app.Rq;
import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Article;
import com.sbs.exam.app.dto.Board;
import com.sbs.exam.app.dto.Member;
import com.sbs.exam.app.service.ArticleService;
import com.sbs.exam.app.service.BoardService;
import com.sbs.exam.app.service.MemberService;
import com.sbs.exam.util.Util;

public class UsrArticleController extends Controller {
	private BoardService boardService;
	private MemberService memberService;
	private ArticleService articleService;
	private Scanner sc;

	public UsrArticleController() {
		boardService = Container.getBoardService();
		memberService = Container.getMemberService();
		articleService = Container.getArticleService();
		sc = Container.getSc();

	}

	private void makeTestData() {
		articleService.makeTestData();
		boardService.makeTestData();
	}

	@Override
	public void performAction(Rq rq) {
		if (rq.getActionPath().equals("/usr/article/write")) {
			actionWrite(rq);
		} else if (rq.getActionPath().equals("/usr/article/list")) {
			actionList(rq);
		} else if (rq.getActionPath().equals("/usr/article/detail")) {
			actionDetail(rq);
		} else if (rq.getActionPath().equals("/usr/article/delete")) {
			actionDelete(rq);
		} else if (rq.getActionPath().equals("/usr/article/modify")) {
			actionModify(rq);
		}
	}

	private void actionModify(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.println("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("새 제목 : ");
		article.setTitle(sc.nextLine().trim());
		System.out.printf("새 내용 : ");
		article.setBody(sc.nextLine().trim());
		article.setUpdateDate(Util.getNowDateStr());

		System.out.printf("%d번 게시물을 수정하였습니다.\n", id);
	}

	private void actionDelete(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.println("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		articleService.deleteArticleById(article.getId());

		System.out.printf("%d번 게시물을 삭제하였습니다.\n", id);
	}

	private void actionDetail(Rq rq) {
		int id = rq.getIntParam("id", 0);

		if (id == 0) {
			System.out.println("id를 입력해주세요.");
			return;
		}

		Article article = articleService.getArticleById(id);

		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
			return;
		}

		System.out.printf("번호 : %d\n", article.getId());
		System.out.printf("작성날짜 : %s\n", article.getRegDate());
		System.out.printf("수정날짜 : %s\n", article.getUpdateDate());
		System.out.printf("제목 : %s\n", article.getTitle());
		System.out.printf("내용 : %s\n", article.getBody());
	}

	private void actionList(Rq rq) {
		int boardId = rq.getIntParam("boardId", 0);
		String searchKeyword = rq.getStrParam("searchKeyword", "");
		String searchKeywordTypeCode = rq.getStrParam("searchKeywordTypeCode", "");
		
		List<Article> articles =new ArrayList<>();
		if(boardId == 0) {
			articles = articleService.getArticles();
		}else {
			if(boardService.getBoardById(boardId) == null) {
				System.out.println("존재하지 않는 게시판 번호입니다.");
				return;
			}
		}
		
		articles = articleService.getFilteredArticles(boardId, searchKeyword, searchKeywordTypeCode);

		System.out.printf("번호 / 게시판 / 작성자 / 작성날짜 / 제목\n");

		for (int i = articles.size() - 1; i >= 0; i--) {
			Article article = articles.get(i);
			String boardName = boardService.getBoardById(article.getBoardId()).getName();
			String writer = memberService.getMemberById(article.getMemberId()).getNickname();
			System.out.printf("%d / %s / %s / %s / %s\n", article.getId(), boardName, writer, article.getRegDate(), article.getTitle());
		}
	}

	private void actionWrite(Rq rq) {
		int boardId = rq.getIntParam("boardId", 0);
		if(boardId == 0) {
			System.out.println("게시판 번호를 입력해주세요.");
			return;
		}
		Board board = boardService.getBoardById(boardId);
		
		if(board == null) {
			System.out.println("존재하지 않는 게시판 번호입니다.");
			return;
		}
		
		int memberId = rq.getLoginedMember().getId();
		
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();

		int id = articleService.write(boardId, memberId, title, body);
		
		Article article = articleService.getArticleById(id);
		
		System.out.println("id:" + article.getId() + " regDate:" + article.getRegDate() + 
				" updateDate:" + article.getUpdateDate() + " boardId:" + article.getBoardId() + " memberId:" + article.getMemberId() + 
				" title:" + article.getTitle() + " body:" + article.getBody());

		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
	}

}
