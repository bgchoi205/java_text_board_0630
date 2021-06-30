package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.dto.Member;
import com.sbs.exam.util.Util;

public class MemberRepository {
	
	private List<Member> members;
	private int lastId;

	public MemberRepository() {
		members = new ArrayList<>();
		lastId = 0;
	}

	public int join(String loginId, String loginPw, String name, String nickname) {
		int id = lastId + 1;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		Member member = new Member(id, regDate, updateDate, loginId, loginPw, name, nickname);
		members.add(member);

		lastId = id;

		return id;
	}

	public Member getMemberByLoginId(String loginId) {
		for ( Member member : members ) {
			if ( member.getLoginId().equals(loginId) ) {
				return member;
			}
		}
		
		return null;
	}

	public Member getMemberById(int id) {
		for(Member member : members) {
			if(member.getId() == id) {
				return member;
			}
		}
		return null;
	}

}
