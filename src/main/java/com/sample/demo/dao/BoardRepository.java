package com.sample.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sample.demo.entity.Board;

public interface BoardRepository extends CrudRepository<Board, Integer> {

	
//	@Query(value="select b from B b where B.aId.id=?1")
//    List<Board> findAllBByA_aId(String aId);
	
	
}
