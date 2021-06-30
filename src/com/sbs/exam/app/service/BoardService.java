package com.sbs.exam.app.service;

import com.sbs.exam.app.container.Container;
import com.sbs.exam.app.dto.Board;
import com.sbs.exam.app.repository.BoardRepository;

public class BoardService {
	private BoardRepository boardRepository;

	public BoardService() {
		boardRepository = Container.getBoardRepository();
	}

	public void makeTestData() {
		boardRepository.add("notice", "공지");
		boardRepository.add("free", "자유");
	}

	public Board getBoardById(int boardId) {
		return boardRepository.getBoardById(boardId);
	}

	
}
