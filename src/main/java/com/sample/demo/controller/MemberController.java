package com.sample.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.sample.demo.entity.Board;
import com.sample.demo.entity.Member;
import com.sample.demo.service.BoardService;
import com.sample.demo.service.MemberService;

@SessionAttributes("name")
@Controller
public class MemberController {

	@Autowired
	private MemberService se;
	@Autowired
	private BoardService boardService;

	@RequestMapping("detail/{no}")    //여기서부터
	public String detail(HttpSession session, @PathVariable int no, Model model) {
		if (session.getAttribute("name") != null) {
			model.addAttribute("detail", boardService.getBoardNo(no));			
			return "detail";
		}
		return "redirect:login";
	}

//	@GetMapping("detail/{no}")
//	public Board getBoardNo (@PathVariable int no, Model model) {
//		Board result = boardService.getBoardNo(no);
//	}

//	@GetMapping("detail/{no}") // 들어가도 에러남
//	public void getBoardNo(Board board, Model model, @PathVariable("no") int no) {
//		Board result = boardService.getBoardNo(no);
//		model.addAttribute("result", result);
//	}

	@GetMapping("board")
	public Iterable<Board> getBoardList(Model model) {
		Iterable<Board> board = boardService.getBoardList();
		model.addAttribute("board", board);
		return board;
	}

	/////////////////////

	@RequestMapping("board")
	public String board(HttpSession session) {
		if (session.getAttribute("name") != null) {
			return "board";
		}
		return "redirect:login";
	}

	@RequestMapping("posting")
	public String posting(HttpSession session, Model model) {
		if (session.getAttribute("name") != null) {
			String id = (String) session.getAttribute("name");
			Member member = se.getMemberName(id);
			model.addAttribute("member", member);
			return "posting";
		}
		return "redirect:login";
	}

	@PostMapping("posting")
	public String postBoard(Board board, HttpSession session) {
		String id = (String) session.getAttribute("name");
		Member member = se.getMemberName(id);
		board.setMember_name(member.getName());
		Board result = boardService.postBoard(board);
		System.out.println(result);
		return "redirect:board";
	}

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
		System.out.println(result.getEmail());
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
		System.out.println(result);
		return "redirect:login";
	}

}
