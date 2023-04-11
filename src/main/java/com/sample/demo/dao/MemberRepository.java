package com.sample.demo.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sample.demo.entity.Member;

public interface MemberRepository extends CrudRepository<Member, Integer> {

	@Query(value = "select * from member where member_name = ?", nativeQuery = true)
	Member findMemberName(String name);

}
