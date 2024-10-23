package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.MemberShipVO;


@Repository
public class MemberMapper {
	
	@Autowired
	private SqlSession session;
	
	public List<MemberShipVO> selectList(MemberShipVO memberShipVO){
		return session.selectList("member.selectList");
	}

	/**
	 * 회원 정보
	 * @param memberVO
	 * @return
	 */
	public MemberShipVO selectOne(MemberShipVO memberShipVO) {
		return session.selectOne("member.selectOne", memberShipVO);
	}
	
	/**
	 * 회원 탈퇴 처리
	 * @param idx
	 */
	public void memberDrop(MemberShipVO memberShipVO) {
		session.update("member.memberDrop", memberShipVO);
	}
	
	/**
	 * 아이디 중복 체크
	 * @param userID
	 * @return
	 */
	public int checkUserID(String userID) {
		return session.selectOne("member.checkUserID", userID);
	}
	
	/**
	 * 핸드폰 중복 체크
	 * @param phonnumber
	 * @return
	 */
	public int checkPhone(String phonnumber) {
		return session.selectOne("member.checkPhone", phonnumber);
	}
	
	/**
	 * email 중복 체크
	 * @param userID
	 * @return
	 */
	public int checkEmail(String email) {
		return session.selectOne("member.checkEmail", email);
	}
	
	/**
	 * 이메일로 아이디 찾기
	 * @param email
	 * @return
	 */
	public String findID(String email) {
		return session.selectOne("member.findID", email);
	}
	
	/**
	 * 아이디와 이메일로 사용자의 PK값 얻어내기
	 * @param memberVO
	 * @return
	 */
	public Long findPW(MemberShipVO memberShipVO) {
		return session.selectOne("member.findPW", memberShipVO);
	}
	
	/**
	 * PK값으로 비밀번호 변경
	 * @param memberVO
	 */
	public void updatePW(MemberShipVO memberShipVO) {
		session.update("member.updatePW", memberShipVO);
	}
	
	/**
	 * 회원정보 변경
	 * @param memberVO
	 */
	public void updateInfo(MemberShipVO memberShipVO) {
		session.update("member.updateInfo", memberShipVO);
	}
}
