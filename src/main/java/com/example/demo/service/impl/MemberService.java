package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.payload.request.ChangePwRequest;
import com.example.demo.payload.request.DropRequest;
import com.example.demo.payload.request.JoinRequest;
import com.example.demo.service.CrudService;
import com.example.demo.util.StringUtil;
import com.example.demo.vo.MemberShipVO;
import com.example.demo.vo.StoreVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class MemberService implements CrudService<MemberShipVO> {
	
	@Autowired
	private MemberMapper mapper;
	
	@Override
	public List<MemberShipVO> selectList(MemberShipVO e) {
		return mapper.selectList(e);
	}
	@Override
	public MemberShipVO selectOne(MemberShipVO e) {
		return mapper.selectOne(e);
	}
		
	public MemberShipVO selectOne(String userID, String password) {
		MemberShipVO vo = new MemberShipVO();
		vo.setUserID(userID);
		vo.setPassword(password);
		return mapper.selectOne(vo);
	}
		
	@Override
	public void insert(MemberShipVO e) {
		mapper.insert(e);
	}
	@Override
	public void update(MemberShipVO e) {
		
	}
	@Override
	public void delete(MemberShipVO e) {
		
	}
	/**
	 * 회원 탈퇴
	 * @param dropRequest
	 * @return
	 */
	public boolean memberDrop(String userID, String password) {
		int cnt = mapper.checkUserID(userID);
		if (cnt == 0) {
	        // 사용자 ID가 존재하지 않는 경우 예외 발생
	        return false;
	    } 
		MemberShipVO vo = new MemberShipVO();
		vo.setUserID(userID);
		vo.setPassword(password);
		mapper.memberDrop(vo);
	    return true;
	}
	
	/** 체크 폰 넘버
	 * 
	 */
	public boolean isPhoneAvailable(String phoneNumber) {
        int count = mapper.checkPhone(phoneNumber);
        return count == 0 ? true : false; // 이메일이 사용 가능한 경우 0 반환
    }
	
	/** 체크 이메일
	 * 
	 */
	public boolean isEmailAvailable(String email) {
        int count = mapper.checkEmail(email);
        return count == 0 ? true : false; // 이메일이 사용 가능한 경우 0 반환
    }
	
	public HashMap<String, Object> checkUserID(String userID) {
		int cnt = mapper.checkUserID(userID);
		HashMap<String, Object> map = new HashMap<>();
		map.put("isExist", cnt == 0? false: true);
		return map;
	}
	
	public HashMap<String, Object> checkEmail(String email) {
		int cnt = mapper.checkEmail(email);
		HashMap<String, Object> map = new HashMap<>();
		map.put("isExist", cnt == 0? false: true);
		return map;
	}
	
	public HashMap<String, Object> checkPhoneNumber(String phoneNumber) {
		int cnt = mapper.checkPhone(phoneNumber);
		HashMap<String, Object> map = new HashMap<>();
		map.put("isExist", cnt == 0? false: true);
		return map;
	}
	
	public HashMap<String, Object> memberJoin(JoinRequest joinRequest) {
		// 리턴(받는것 포함)할 객체가 특정하기 어려운 경우 hashmap을 사용한다
		HashMap<String, Object> map = new HashMap<>();

		// hashMap 리턴 시 아래의 내용으로 구성
		// 가입 성공 여부 true, false
		// 메시지 "성공", "실패", "무언가 사유로 인한 실패"
		
		// 1. 아이디 중복 체크
		HashMap<String, Object> idMap = this.checkUserID(joinRequest.getUserID());
		boolean idExist = (boolean)idMap.get("isExist");
		
		if (idExist) {
			map.put("result", false);
			return map;
		}
		// 2. 이메일 중복 체크
		HashMap<String, Object> emailMap = this.checkEmail(joinRequest.getEmail());
		boolean emailExist = (boolean)emailMap.get("isExist");
		
		if (emailExist) {
			map.put("result", false);
			return map;
		}
		// 3. 비밀번호
		String pw = joinRequest.getPassword();
		// 4. 사용자 이름
		String username = joinRequest.getUsername();
		
		MemberShipVO memberShipVO = MemberShipVO.builder()
				.email(joinRequest.getEmail())
				.userID(joinRequest.getUserID())
				.password(pw)
				.username(username)
				.phoneNumber(joinRequest.getPhoneNumber())
				.build();
		
		this.insert(memberShipVO);
		
		map.put("result", true);
		map.put("message", "회원가입 완료");
		
		return map;
	}
	
	public HashMap<String, Object> findID(String email) {
		HashMap<String, Object> map = new HashMap<>();
		String userID = mapper.findID(email);
		
		map.put("result", userID == null ? false : true);
		map.put("message", userID == null ? "찾으시는 아이디가 없습니다." : "찾으시는 아이디는 " + userID + "입니다.");
		return map;
	}
	
	/**
	 * 아이디와 이메일로 idx 찾고 임시비밀번호 발급
	 * @param changePwRequest
	 * @return
	 */
	public Object changePW(ChangePwRequest changePwRequest) {
		MemberShipVO memberShipVO = MemberShipVO.builder()
				.email(changePwRequest.getEmail())
				.userID(changePwRequest.getUserID())
				.build();
		Long idx = mapper.findPW(memberShipVO);
		
		HashMap<String, Object> map = new HashMap<>();
		
		// row가 없으면 계정 못찾는 메시지 리턴
		if (idx == null) {
			map.put("result", false);
			map.put("message", "계정을 찾을 수 없습니다.");
			return map;
		}
		
		// 계정이 있으면 랜덤하게 문자열을 생성해서 idx값에 해당하는 비밀번호 변경
		String randomPw = StringUtil.generateRandomString(6);
		
		// 기존에 memberVO가 있기때문에 별도로 생성 하지 않고 기존 변수명 활용
		memberShipVO = MemberShipVO.builder()
				.password(randomPw)
				.idx(idx).build();
		mapper.updatePW(memberShipVO);
		
		// 완료 메시지에 비밀번호를 넣어서 리턴
		map.put("result", true);
		map.put("message", "임시 비밀번호는 " + randomPw + "입니다. 반드시 복사해주세요.");
		
		return map;
	}
		
	/**
	 * 회원정보 수정
	 * @param request
	 * @param memberVO
	 * @return
	 */
	public boolean updateInfo(HttpServletRequest request, MemberShipVO memberShipVO) {
		HttpSession session = request.getSession();
		MemberShipVO userInfo = (MemberShipVO) session.getAttribute("userInfo");
		
		if (userInfo == null) {
			return false;
		}
		
		// pk값이 세션에 있는거랑 다르면 로직을 추가할 수도 있다.
		if (!memberShipVO.getIdx().equals(userInfo.getIdx()) ) {
			return false;
		}
		
		// 세션에 있는 userID와 파라미터로 넘어온 userID가 다르면 이 역시 로직을 추가하는 것도 방법이다.
		if (!memberShipVO.getUserID().equals(userInfo.getUserID())) {
			return false;
		}
		
		// 이메일 사용 여부 체크
	    int emailCount = mapper.checkEmail(memberShipVO.getEmail());
	    log.info(String.valueOf(emailCount));
	    if (emailCount > 0) {
	        return true; // 이메일이 이미 사용 중일 경우 false 반환
	    }
		
		
//		if (!memberShipVO.getPassword().equals("")) {
//			memberShipVO.setPassword("");
//		}
		
		
		log.info("226");
		mapper.updateInfo(memberShipVO);
		
		return true;
	}
	
	@Override
	public List<StoreVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
