package com.example.demo.commonController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.impl.MemberService;
import com.example.demo.vo.MemberShipVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
public class CommonController {
    
    @Autowired
    private MemberService memberService;


    // 로그인 페이지
    @GetMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "userID", required = false, defaultValue = "") String userID,
            @RequestParam(value = "password", required = false, defaultValue = "") String password) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/login");
        mav.addObject("title", "로그인 페이지");
        mav.addObject("userID", userID);
        mav.addObject("password", password);
        return mav;
    }

    @PostMapping("/loginProc")
    public ModelAndView loginProc(
            @ModelAttribute MemberShipVO memberShipVO, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        MemberShipVO result = memberService.selectOne(memberShipVO);
        if (result != null) {
            log.info("로그인 성공");
            HttpSession session = request.getSession();
            session.setAttribute("userInfo", result);
            mav.setViewName("redirect:/");
        } else {
            log.info("로그인 실패");
            mav.setViewName("forward:/member/login");
        }
        return mav;
    }

    @GetMapping("/myPage")
    public ModelAndView myPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/myPage");
        mav.addObject("title", "마이 페이지");
        return mav;
    }

    @GetMapping("/checkEmail/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        boolean isAvailable = memberService.isEmailAvailable(email);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/findID")
    public String findID() {
        return "common/findID";
    }

    @RequestMapping("/myInfo")
    public ModelAndView myInfo(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/myInfo");
        
        HttpSession session = request.getSession();
        MemberShipVO memberShipVO = (MemberShipVO) session.getAttribute("userInfo");
        
        mav.addObject("userInfo", memberShipVO);
        
        return mav;
    }

    @PostMapping("/updateInfo")
    public ModelAndView updateInfo(
            HttpServletRequest request,
            @ModelAttribute MemberShipVO memberVO) {
        log.info(memberVO.toString());
        
        boolean result = memberService.updateInfo(request, memberVO);
        
        ModelAndView mav = new ModelAndView();
        
        if (result) {
            mav.setViewName("redirect:/member/myPage");
        } else {
            mav.setViewName("forward:/member/myInfo");
        }
        
        return mav;
    }

    @GetMapping("/findOften")
    public ModelAndView findOften() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/findOften");
        mav.addObject("title", "즐겨찾기 페이지");
        return mav;
    }

    @GetMapping("/map")
    public ModelAndView map() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/map");
        mav.addObject("title", "지도&메인");
        return mav;
    }

    @GetMapping("/memberShip")
    public ModelAndView memberShip() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/memberShip");
        mav.addObject("title", "회원가입");
        return mav;
    }

    @RequestMapping("/memberDrop")
    public ModelAndView memberDrop() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/memberDrop");
        mav.addObject("title", "회원 탈퇴");
        return mav;
    }

    @PostMapping("/memberDropProc")
    public ModelAndView memberDropProc(
            @RequestParam(value = "userID", required = true, defaultValue = "") String userID,
            @RequestParam(value = "password", required = true, defaultValue = "") String password) {
        ModelAndView mav = new ModelAndView();
        boolean result = memberService.memberDrop(userID, password);
        if (result) {
            mav.setViewName("redirect:/member/login");
        } else {
            mav.setViewName("forward:/member/memberDrop");
            mav.addObject("message", "계정이 없습니다.");
        }
        return mav;
    }

    @GetMapping("/serch")
    public ModelAndView serch() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/serch");
        mav.addObject("title", "검색");
        return mav;
    }

    @GetMapping("/serchHistory")
    public ModelAndView serchHistory() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/serchHistory");
        mav.addObject("title", "검색히스토리");
        return mav;
    }
}
