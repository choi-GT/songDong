<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>마이 페이지</title>
    <style>
      /* CSS 스타일은 그대로 유지 */
      * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
      }
      header {
        background-color: #00D7FF;
        position: relative; /* header가 다른 요소 위에 표시되도록 설정 */
        z-index: 1; /* header의 z-index를 높여서 맨 위에 위치하게 함 */
      }
      .nav-menu {
        list-style: none;
      }
      .nav-branding,
      .nav-link {
        color: #fff;
        text-decoration: none;
      }
      .nav-bar,
      .nav-menu {
        display: flex;
      }
      .nav-bar {
        justify-content: space-between;
        padding: 20px 0;
        align-items: center;
      }
      .nav-branding {
        font-size: 30px;
      }
      .hamburger {
        display: none;
      }
      .nav-menu {
        gap: 20px;
      }
      .container {
        width: 800px;
        margin-left: auto;
        margin-right: auto;
      }
      .bar {
        background-color: #fff;
        width: 25px;
        height: 2px;
        display: block;
        margin: 6px auto;
        transition: all 0.3s;
      }
      .hamburger.active .bar:nth-child(2) {
        opacity: 0;
      }
      .hamburger.active .bar:nth-child(1) {
        transform: rotate(45deg) translateY(11px);
      }
      .hamburger.active .bar:nth-child(3) {
        transform: rotate(-45deg) translateY(-11px);
      }
      @media (max-width: 767px) {
        .container {
          width: auto;
          padding-left: 25px;
          padding-right: 25px;
        }
        .nav-menu {
          position: fixed;
          left: -100%;
          top: 73px;
          width: 100%;
          display: flex;
          flex-direction: column;
          text-align: center;
          background-color: rgba(0, 215, 255, 0.9);
          transition: all 0.5s;
        }
        .hamburger {
          display: block;
        }
        .nav-menu.active {
          left: 0;
        }
      }

      /* 추가하는 CSS */
      #map {
        position: absolute; /* 절대 위치 설정 */
        top: 0; /* 페이지 상단부터 시작 */
        left: 0; /* 페이지 왼쪽부터 시작 */
        width: 100%; /* 전체 너비 */
        height: 100vh; /* 전체 화면 높이 */
        z-index: -1; /* 지도를 배경으로 설정 */
      }

      body {
        position: relative; /* 자식 요소인 #map이 absolute로 위치할 수 있도록 설정 */
      }
    </style>
  </head>
  <body>
    <header>
      <div class="container">
        <nav class="nav-bar">
          <a href="#" class="nav-branding">마이 페이지</a>
          <ul class="nav-menu" id="nav-menu">
            <li class="nav-item">
              <a href="/member/serchHistory" class="nav-link">검색히스토리</a>
            </li>
            <li class="nav-item">
              <a href="/member/findOften" class="nav-link">즐겨찾기</a>
            </li>
            <li class="nav-item">
              <a href="/member/myInfo" class="nav-link">회원정보수정</a>
            </li>
            <li class="nav-item">
              <a href="/member/memberDrop" class="nav-link" onclick="confirmDelete()"
                >회원탈퇴</a
              >
            </li>
          </ul>
          <div class="hamburger" id="hamburger">
            <span class="bar"></span>
            <span class="bar"></span>
            <span class="bar"></span>
          </div>
        </nav>
      </div>
    </header>
    <!-- 지도를 표시할 div 입니다 -->
    <div id="map"></div>
    <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ba6a7d46b758f0604fe9336c5e9a6747&libraries=services"></script>
    <script>
      var mapContainer = document.getElementById("map"),
        mapOption = {
          center: new kakao.maps.LatLng(33.450701, 126.570667),
          level: 3,
        };

      var map = new kakao.maps.Map(mapContainer, mapOption);
      var geocoder = new kakao.maps.services.Geocoder();

      // 주소로 좌표를 검색하는 함수
      function searchAddressToCoordinates(address) {
        geocoder.addressSearch(address, function (result, status) {
          if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

            // 마커를 표시합니다
            var marker = new kakao.maps.Marker({
              map: map,
              position: coords,
            });

            // 인포윈도우를 표시합니다
            var infowindow = new kakao.maps.InfoWindow({
              content:
                '<div style="width:150px;text-align:center;padding:6px 0;">시모네빵집</div>',
            });
            infowindow.open(map, marker);

            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);
          }
        });
      }

      // 주소를 입력하여 좌표를 검색합니다
      searchAddressToCoordinates("서울특별시 성동구 송정동 66-259번지");

      // 햄버거 메뉴 기능
      const hamburger = document.querySelector("#hamburger");
      const navMenu = document.querySelector("#nav-menu");
      hamburger.addEventListener("click", () => {
        hamburger.classList.toggle("active");
        navMenu.classList.toggle("active");
      });
    </script>
  </body>
</html>