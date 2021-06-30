package com.sbs.exam.app.interceptor;

import com.sbs.exam.app.Rq;

public class NeedLogoutInterceptor implements Interceptor {

	@Override
	public boolean run(Rq rq) {
		if (rq.isLogined() == false) {
			return true;
		}

		switch (rq.getActionPath()) {
		case "/usr/member/login":
		case "/usr/member/join":
		case "/usr/member/findLoginId":
		case "/usr/member/findLoginPw":
			System.out.printf("이미 로그인 되었습니다.\n");
			return false;
		}

		return true;
	}

}
