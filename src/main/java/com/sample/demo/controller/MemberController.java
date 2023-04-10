package com.sample.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.sample.demo.entity.Member;
import com.sample.demo.service.MemberService;

@SessionAttributes("name")
@Controller
public class MemberController {

	@Autowired
	private MemberService se;

	@RequestMapping("login")
	public String login() {
		return "login";
	}

	@RequestMapping("delete")
	public String memberDelete(HttpSession session) {
		if (session.getAttribute("name") != null) {
			String id = (String) session.getAttribute("name");
			Member member = se.getMemberName(id);
			se.deleteMember(member.getNo());
			return "delete";
		}
		return "redirect:login";
	}

	@RequestMapping("logout")
	public String logout(HttpSession session, SessionStatus status) {
		if (session.getAttribute("name") != null) {
			status.setComplete();
			return "logout";
		}
		return "redirect:login";
	}

	@RequestMapping("board")
	public String board(HttpSession session) {
		if (session.getAttribute("name") != null) {
			return "board";
		}
		return "redirect:login";
	}

	@RequestMapping("update")
	public String update(HttpSession session, Model model) {
		if (session.getAttribute("name") != null) {
			String id = (String) session.getAttribute("name");
			Member member = se.getMemberName(id);
			model.addAttribute("member", member);
			return "update";
		}
		return "redirect:login";
	}

	@PostMapping("update")
	public String memberUpdate(Member member) {
		Member result = se.postMember(member);
		System.out.println(result.getEmail() + "123123123123123123123");
		return "info";
	}

	@RequestMapping("info")
	public String info(HttpSession session, Model model) {
		if (session.getAttribute("name") != null) {
			String id = (String) session.getAttribute("name");
			Member member = se.getMemberName(id);
			model.addAttribute("member", member);
			return "info";
		}
		return "redirect:login";
	}

	@PostMapping("login")
	public String memberLogin(Member member, @RequestParam String name, Model model) {
		if (se.login(member)) {
			model.addAttribute("name", name);
			return "whattodo";
		}
		return "login";
	}

	@RequestMapping("whattodo")
	public String whattodo(HttpSession session) {
		if (session.getAttribute("name") != null) {
			return "whattodo";
		}
		return "redirect:login";
	}

	@RequestMapping("join")
	public String join() {
		return "join";
	}

	@PostMapping("join")
	public String postMember(Member member) {
		Member result = se.postMember(member);
		result.setEmail(null);
		result.setName(null);
		result.setPassword(null);
		return "redirect:login";
	}

}
