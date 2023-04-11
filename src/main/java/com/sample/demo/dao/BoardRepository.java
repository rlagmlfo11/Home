package com.sample.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.sample.demo.entity.Board;

public interface BoardRepository extends CrudRepository<Board, Integer> {

}
