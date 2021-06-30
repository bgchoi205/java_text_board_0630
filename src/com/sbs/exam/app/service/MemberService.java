package com.sbs.exam.app.service;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Member;
import com.sbs.exam.app.repository.MemberRepository;

public class MemberService {

	private MemberRepository memberRepository;

	public MemberService() {
		memberRepository = Container.getMemberRepository();
	}

	public void makeTestData() {
		for (int i = 0; i < 2; i++) {
			String loginId = "user" + (i + 1);
			String loginPw = loginId;
			String name = "홍길동" + (i + 1);
			String nickname = "강바랑" + (i + 1);

			join(loginId, loginPw, name, nickname);
		}
	}

	private int join(String loginId, String loginPw, String name, String nickname) {
		return memberRepository.join(loginId, loginPw, name, nickname);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}
