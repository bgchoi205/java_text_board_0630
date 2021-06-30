package com.sbs.exam.app.controller;

import com.sbs.exam.app.Rq;

public class UsrSystemController extends Controller {
	@Override
	public void performAction(Rq rq) {
		if (rq.getActionPath().equals("/usr/system/exit")) {
			actionExit(rq);
		}
	}

	private void actionExit(Rq rq) {
		System.out.println("프로그램을 종료합니다.");
	}
}
