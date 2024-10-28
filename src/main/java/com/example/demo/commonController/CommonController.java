package com.example.demo.commonController;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import com.example.demo.payload.request.ChangePwRequest;
import com.example.demo.payload.request.JoinRequest;
import com.example.demo.service.StoreService2;
import com.example.demo.service.impl.MemberService;
import com.example.demo.vo.MemberShipVO;
import com.example.demo.vo.StoreInfoVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

/**
 * test2
 */
@Controller
@RequestMapping("/member")
@Slf4j
public class CommonController {
    
    @Autowired
    private MemberService memberService;

    @Autowired
    private StoreService2 storeService;

    @GetMapping("/storeList")
    public String showStoreList(Model model ,
    				@RequestParam(value= "shopText", required = false, defaultValue = "") String shopText
    		) {
        List<StoreInfoVO> storeList = storeService.getAllStores();
        model.addAttribute("shopText",shopText);
        model.addAttribute("storeList", storeList);
        return "common/storeList"; // JSP 템플릿 이름  
    }
    
    
    // 로그인 페이지
    @RequestMapping("/login")
    public ModelAndView login(
            @RequestParam(value = "userID", required = false, defaultValue = "") String userID,
            @RequestParam(value = "password", required = false, defaultValue = "") String password) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/login");
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
            
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            if (redirectUrl != null) {
                mav.setViewName("redirect:" + redirectUrl);  // 세션에 저장된 URL로 리다이렉트
                session.removeAttribute("redirectUrl");  // 리다이렉트 후 세션에서 URL 삭제
            } else {
                mav.setViewName("redirect:/");  // 기본 페이지로 리다이렉트
            }
        
        } else {
            log.info("로그인 실패");
            mav.setViewName("forward:/member/login");
        }
        return mav;
    }
    
    /**
     * 로그아웃
     * @param request
     * @return
     */
    @GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 세션 삭제
		session.invalidate();
		return "redirect:/";
	}
    
    /**
	 * 회원가입 양식
	 * @return
	 */
	@GetMapping("/join")
	public ModelAndView join() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/join");
		return mav;
	}
	
	/**
	 * 회원가입 처리
	 * @param memberVO
	 */
	@PostMapping("/joinProc")
	public String joinProc(@ModelAttribute MemberShipVO memberShipVO) {
		memberService.insert(memberShipVO);
		return "redirect:/member/login";
	}
	
	/**
	 * 회원가입 비동기 처리
	 * @param joinRequest
	 * @RequestBody 어노테이션이 있어야 post형식의 데이터를 받을 수 있다
	 * @return
	 */
	@PostMapping("/joinProc2")
	@ResponseBody
	public ResponseEntity<?> joinProc2(@RequestBody JoinRequest joinRequest) {
		log.info(joinRequest.toString());
		return ResponseEntity.ok(memberService.memberJoin(joinRequest));
	}

	/**
	 * 마이 페이지 
	 * @param request
	 * @return
	 */
    @GetMapping("/myPage")
    public ModelAndView myPage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/myPage");
        mav.addObject("title", "마이 페이지");
        return mav;
    }

    /**
	 * 비동기 통신 아이디 중복 확인
	 * @return
	 */
	@GetMapping("/checkUserID/{userID}")
	@ResponseBody
	public ResponseEntity<?> checkUserID(
			@PathVariable(value = "userID") String userID
			) {
		HashMap<String, Object> result = memberService.checkUserID(userID);
		
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 비동기 통신 이메일 중복 확인
	 * @param email
	 * @return
	 */
	@GetMapping("/checkEmail/{email}")
	@ResponseBody
	public ResponseEntity<?> checkEmail(
			@PathVariable(value = "email") String email
			) {
		HashMap<String, Object> result = memberService.checkEmail(email);
		return ResponseEntity.ok(result);
	}

    @GetMapping("/findID")
    public String findID() {
        return "common/findID";
    }
    
    /**
	 * 이메일로 아이디 찾기
	 * @param email
	 * @return
	 */
	@GetMapping("/findID/{email}")
	@ResponseBody
	public ResponseEntity<?> findIDByEmail(
			@PathVariable(value = "email") String email
			) {
		HashMap<String, Object> result = memberService.findID(email);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/findPW")
	public String findPW() {
		return "common/findPW";
	}
	
	/**
	 * 비밀번호 찾기에서 임시비밀번호 발급하기
	 * @param changePwRequest
	 * @return
	 */
	@PostMapping("/changePW")
	@ResponseBody
	public ResponseEntity<?> changePW(
			@RequestBody ChangePwRequest changePwRequest) {
		return ResponseEntity.ok(memberService.changePW(changePwRequest));
	}
	
	/**
	 * 회원정보 수정 본인 이메일 중복 확인
	 * @param email
	 * @return
	 */
	@GetMapping("/isEmailAvailable/{email}")
	@ResponseBody
	public ResponseEntity<?> isEmailAvailable(
			@PathVariable(value = "email") String email, HttpServletRequest request
			) {
		HashMap<String, Object> result = memberService.isEmailAvailable(email, request);
		return ResponseEntity.ok(result);
	}

	/**
	 * 회원정보 수정 페이지
	 * @param request
	 * @return
	 */
    @RequestMapping("/myInfo")
    public ModelAndView myInfo(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/myInfo");
        
        HttpSession session = request.getSession();
        MemberShipVO memberShipVO = (MemberShipVO) session.getAttribute("userInfo");
        
        mav.addObject("userInfo", memberShipVO);
        
        return mav;
    }

    /**
     * 회원정보 수정
     * @param request
     * @param memberVO
     * @return
     */
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
    public ModelAndView findOften(HttpServletRequest request, Model model, 
                                   @RequestParam(value = "shopName", required = false, defaultValue = "") String shopName) {
        ModelAndView mav = new ModelAndView();

        // 세션에서 사용자 정보 확인
        HttpSession session = request.getSession();
        MemberShipVO memberShipVO = (MemberShipVO) session.getAttribute("userInfo");

        // 로그인 여부 확인
        if (memberShipVO == null) {
            // 로그인되어 있지 않으면 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
        } else {
            // 로그인되어 있으면 즐겨찾기 페이지로 이동
        	mav.addObject("userIdx", memberShipVO.getIdx());
            mav.setViewName("common/findOften");
            mav.addObject("title", "즐겨찾기 페이지");
            mav.addObject("shopName", shopName);
        }

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
    
    
    /**
     * 회원 탈퇴 
     * @return
     */
    @RequestMapping("/memberDrop")
    public ModelAndView memberDrop() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/memberDrop");
        mav.addObject("title", "회원 탈퇴");
        return mav;
    }
    /**
     * 회원 탈퇴 처리 isuse 0처리 
     * @param userID
     * @param password
     * @return
     */
    @PostMapping("/memberDropProc")
    public ModelAndView memberDropProc(
            @RequestParam(value = "userID", required = true, defaultValue = "") String userID,
            @RequestParam(value = "password", required = true, defaultValue = "") String password) {
        ModelAndView mav = new ModelAndView();
        boolean result = memberService.memberDrop(userID, password);
        log.info(String.valueOf(result));
        if (result) {
            mav.setViewName("redirect:/member/login");
        } else {
            mav.setViewName("forward:/member/memberDrop");
            mav.addObject("message", "계정이 없습니다.");
        }
        return mav;
    }

    private String hashPassword(String password) {
		// TODO Auto-generated method stub
		return null;
	}


	@GetMapping("/search")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/search");
        mav.addObject("title", "검색");
        return mav;
    }

    @GetMapping("/searchHistory")
    public ModelAndView searchHistory() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/searchHistory");
        mav.addObject("title", "검색히스토리");
        return mav;
    }
    
    @GetMapping("/favoriteList")
    public ModelAndView favoriteList() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("common/favoriteList");
        mav.addObject("title", "즐겨찾기 리스트");
        return mav;
    }
    

}
