package com.example.demo.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SearchHistoryVO {
	
	private Long idx; // PK
	private int ranking; // 순위
	private String storeName; // 상호명
	private String address; // 가계 주소
	private String note; // 비고
	
	

}
