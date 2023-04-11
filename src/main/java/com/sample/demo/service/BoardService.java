package com.sample.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.demo.dao.BoardRepository;
import com.sample.demo.entity.Board;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	public Board postBoard(Board board) {
		Board result = boardRepository.save(board);
		return result;
	}

}
