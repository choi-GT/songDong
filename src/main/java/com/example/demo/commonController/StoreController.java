package com.example.demo.commonController;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.StoreService;
import com.example.demo.vo.StoreVO;

import lombok.AllArgsConstructor;
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
	public String index(Model model) {
//		List<HashMap<String, Object>> list = storeService.getAllStore();
//		
//		model.addAttribute("list", list);
		return "index";
	}

	 @GetMapping("/api/getStoreCoords")
	    public ResponseEntity<?> getStoreCoords(@RequestParam String sitewhladdr) {
	        StoreVO store = storeService.getStoreByAddress(sitewhladdr); // 주소로 좌표 검색

	        if (store != null) {
	            return ResponseEntity.ok(store); // 좌표를 포함한 데이터를 반환
	        } else {
	            return ResponseEntity.status(404).body("Store not found");
	        }
	    }

	
	 @GetMapping("/api/searchShop/{keyword}")
	 @ResponseBody
	    public ResponseEntity<List<HashMap<String, Object>>> searchShopByKeyword(@PathVariable String keyword) {
	        List<HashMap<String, Object>> shops = storeService.getShopsByKeyword(keyword);
	        return ResponseEntity.ok(shops);
	    }

}