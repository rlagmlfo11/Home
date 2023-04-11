package com.sample.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.demo.dao.MemberRepository;
import com.sample.demo.entity.Member;

@Service
public class MemberService {

	@Autowired
	private MemberRepository re;

	public Member getMemberNo(int no) {
		Member result = re.findById(no).orElse(null);
		return result;
	}

	public Member getMemberName(String name) {
		Member result = re.findMemberName(name);
		return result;
	}

	public boolean login(Member member) {
		Member result = re.findMemberName(member.getName());
		if (result == null) {
			return false;
		}
		if (!result.getPassword().equals(member.getPassword())) {
			return false;
		}
		return true;
	}

	public Member postMember(Member member) {
		Member result = re.save(member);
		return result;
	}

	public void deleteMember(int no) {
		re.deleteById(no);
	}

}
