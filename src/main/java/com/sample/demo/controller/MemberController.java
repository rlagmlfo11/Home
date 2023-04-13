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

	@RequestMapping("detail{no}")
	public String detail(HttpSession session) {
		if (session.getAttribute("name") != null) {
			return "detail";
		}
		return "redirect:login";
	}

	@GetMapping("detail/{no}")
	public String showDetail(@PathVariable("no") int no, Model model) {
		Board board = boardService.getBoardNo(no);
		model.addAttribute("board", board);
		System.out.println(board.getNo());
		System.out.println(board.getMember_name());
		System.out.println(board.getTitle());
		System.out.println(board.getContent());
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
		return "detail";
	}

//	@GetMapping("detail/{no}")
//	public String getBoard(@PathVariable("no") int no, Model model) {
//		Board board = boardService.getBoardNo(no);
//		model.addAttribute("board", board);
//		return "detail";
//	}

//	@GetMapping("/detail/{no}")// 들어가도 에러남
//	public String showDetail(@PathVariable("no") String no, Model model) {
//	    int intNo;
//	    try {
//	        intNo = Integer.parseInt(no);
//	    } catch (NumberFormatException e) {
//	        // Handle invalid input
//	        return "redirect:/error";
//	    }
//	    Board board = boardService.getBoardNo(intNo);
//	    model.addAttribute("board", board);
//	    return "detail/{no}";
//	}

//	@GetMapping("detail/{no}") // 들어가도 에러남
//	public void getBoardNo(Board board, Model model, @PathVariable("no") int no) {
//		Board result = boardService.getBoardNo(no);
//		model.addAttribute("result", result);
//	}

//	@GetMapping("board") board에 안들어감
//	public String getBoard(Model model, @PathVariable int no) {
//		Iterable<Board> board = boardService.getBoardList();
//		model.addAttribute("board", board);
//		return "detail";
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
			return "redirect:whattodo";
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
