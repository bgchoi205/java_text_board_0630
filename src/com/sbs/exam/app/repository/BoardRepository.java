package com.sbs.exam.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.sbs.exam.app.dto.Board;
import com.sbs.exam.app.dto.Member;
import com.sbs.exam.util.Util;

public class BoardRepository {
	private List<Board> boards;
	private int lastId;

	public BoardRepository() {
		boards = new ArrayList<>();
		lastId = 0;
	}

	public int add(String code, String name) {
		lastId++;
		int id = lastId;
		String regDate = Util.getNowDateStr();
		String updateDate = regDate;

		Board board = new Board(id, regDate, updateDate, code, name);
		boards.add(board);
		
		return id;
		
	}

	public Board getBoardById(int boardId) {
		for(Board board : boards) {
			if(board.getId() == boardId) {
				return board;
			}
		}
		return null;
	}
}
