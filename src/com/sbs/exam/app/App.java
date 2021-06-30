package com.sbs.exam.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.controller.Controller;
import com.sbs.exam.app.dto.Member;
import com.sbs.exam.app.interceptor.Interceptor;
import com.sbs.exam.app.service.ArticleService;
import com.sbs.exam.app.service.MemberService;

public class App {
	Scanner sc;
	MemberService memberService;
	ArticleService articleService;

	App() {
		sc = Container.getSc();
		memberService = Container.getMemberService();
		articleService = Container.getArticleService();
	}

	public void run() {
		System.out.println("== 텍스트 게시판 시작 ==");
		
		new Rq().login(memberService.getMemberById(1));
		articleService.makeTestData();

		while (true) {
			String promprName = "명령어";

			Rq rq = new Rq();

			if (rq.isLogined()) {
				Member loginedMember = rq.getLoginedMember();
				promprName = loginedMember.getNickname();
			}

			System.out.printf("%s) ", promprName);

			String command = sc.nextLine().trim();

			rq.setCommand(command);

			if (rq.isValid() == false) {
				System.out.printf("명령어가 올바르지 않습니다.\n");
				continue;
			}

			if (runInterceptors(rq) == false) {
				continue;
			}

			Controller controller = getControllerByRequestUri(rq);
			controller.performAction(rq);

			if (rq.getActionPath().equals("/usr/system/exit")) {
				break;
			}
		}

		System.out.println("== 텍스트 게시판 끝 ==");
	}

	private boolean runInterceptors(Rq rq) {
		List<Interceptor> interceptors = new ArrayList<>();

		interceptors.add(Container.getNeedLoginInterceptor());
		interceptors.add(Container.getNeedLogoutInterceptor());

		for (Interceptor interceptor : interceptors) {
			if (interceptor.run(rq) == false) {
				return false;
			}
		}

		return true;
	}

	private Controller getControllerByRequestUri(Rq rq) {
		switch (rq.getControllerTypeCode()) {
		case "usr":
			switch (rq.getControllerName()) {
			case "article":
				return Container.getUsrArticleController();
			case "member":
				return Container.getUsrMemberController();
			case "system":
				return Container.getUsrSystemController();
			}

			break;
		}

		return null;
	}

}
