package com.example.demo.commonController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.StoreService;
import com.example.demo.vo.StoreVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private  StoreService storeService;
	

	 @GetMapping("/getStoreCoords")
	    public ResponseEntity<?> getStoreCoords(@RequestParam String sitewhladdr) {
	        StoreVO store = storeService.getStoreByAddress(sitewhladdr); // 주소로 좌표 검색

	        if (store != null) {
	            return ResponseEntity.ok(store); // 좌표를 포함한 데이터를 반환
	        } else {
	            return ResponseEntity.status(404).body("Store not found");
	        }
	    }

	
	 @GetMapping("/searchShop")
	    public ResponseEntity<?> searchShopByKeyword(@RequestParam(value = "keyword") String keyword) {
		 log.info(keyword);
	        return ResponseEntity.ok(storeService.getShopsByKeyword(keyword));
	    }

}
