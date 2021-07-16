package com.example.board.controller;

import com.example.board.domain.Member;
import com.example.board.dto.BoardDto;
import com.example.board.dto.MemberDto;
import com.example.board.repository.MemberRepository;
import com.example.board.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MemberController {

    private MemberService memberService;

    @GetMapping("/home")
    public String index() {
        return "home/index.html";
    }

    @GetMapping("/home/signupForm")
    public String signupForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "member/signupForm.html";
    }

    @PostMapping("/home/signupForm")
    public String signUp(MemberDto memberDto) {
        memberService.signUp(memberDto);
        return "redirect:/home";
    }

    @GetMapping("/home/loginForm")
    public String loginForm() {
        return "member/loginForm.html";
    }


    @GetMapping("/home/signupForm/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        MemberDto memberDto = memberService.getPost(id);
        model.addAttribute("post", memberDto);
        return "member/signupForm.html";
    }

    @DeleteMapping("/home/signupForm/{id}")
    public String delete(@PathVariable("id") Long id) {
        memberService.loadDeletePost(id);
        return "redirect:/";
    }
}
