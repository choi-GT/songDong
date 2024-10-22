package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.vo.MemberShipVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor{
	 
	@Override
	    public boolean preHandle(HttpServletRequest request, 
	                             HttpServletResponse response, 
	                             Object handler) throws Exception {
	        // 1. 세션 체크 (세션이 없으면 null 반환)
	        HttpSession session = request.getSession(false);

	        // 2. 세션이 존재하고, "userInfo"라는 세션 속성이 있으면 로그인된 상태로 판단
	        if (session != null) {
	        	MemberShipVO userInfo = (MemberShipVO) session.getAttribute("userInfo");
	            if (userInfo != null) {
	                return true; // 요청을 계속 진행함 (컨트롤러로 넘어감)
	            }
	        }

	        // 3. 세션이 없거나, "userInfo" 속성이 없으면 로그인 페이지로 리다이렉트
	        response.sendRedirect(request.getContextPath() + "/member/login");
	        return false; // 요청을 차단함 (컨트롤러로 넘어가지 않음)

}
}
