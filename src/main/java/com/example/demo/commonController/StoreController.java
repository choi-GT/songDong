package com.example.demo.commonController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.StoreService;
import com.example.demo.vo.MemberShipVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class StoreController {
	
	private final  StoreService storeService;
	//db에 저장된 좌표값 가져오기 + 카카오 api로 검색
	
	
	public StoreController(StoreService storeService) {
		this.storeService = storeService;
	}
	
	@GetMapping("/")
	public String index(Model model, HttpServletRequest request) {
//		List<HashMap<String, Object>> list = storeService.getAllStore();
//		
//		model.addAttribute("list", list);
		HttpSession session = request.getSession();
		MemberShipVO vo = (MemberShipVO) session.getAttribute("userInfo");
		
		model.addAttribute("userInfo", vo);
		
		
		return "index";
	}


}