package com.example.demo.vo;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberShipVO {
	private Long idx;
	private Long isAdmin;
	private String userID;
	private String password;
	private String username;
	private String email;
	private String phoneNumber;
	private LocalDateTime regDate;
	private int isUse;
	private LocalDateTime dropDate;
	
	@Builder
	public MemberShipVO(Long idx, Long isAdmin, String userID, String password, String username, String email,
			String phoneNumber, LocalDateTime regDate, int isUse, LocalDateTime dropDate) {
		
		this.idx = idx;
		this.isAdmin = isAdmin;
		this.userID = userID;
		this.password = password;
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.regDate = regDate;
		this.isUse = isUse;
		this.dropDate = dropDate;
	}
	
	
	
	
}
