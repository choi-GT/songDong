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
public class StoreInfoVO {

    private String opnsfteamcode; // 개방자치단체코드
    private String mgtNo; // 관리번호(pk)
    private String apvpermymd; // 인허가일자
    private String apvcancelymd; // 인허가취소일자
    private String trdstategbn; // 영업상태코드
    private String trdstatenm; // 영업상태명
    private String dtlstategbn; //상세영업상태코드
    private String dtlstatenm; // 상세영업상태명
    private String dcbymd; // 폐업일자
    private String clgstdt; // 휴업시작일자

    private String clgenddt; // 휴업종료일자
    private String ropnymd; // 재개업일자
    private String sitetel; // 전화번호
    private String sitearea; // 소재지면적
    private String sitepostno; // 소재지우편번호
    private String sitewhladdr; // 지번주소
    private String rdnwhladdr; // 도로명우편번호
    private String bplcnm; // 사업장명
    private LocalDateTime lastmodts; // 최종수정일자

    private String updategbn; // 데이터갱신구분
    private String updatedt; // 데이터갱신일자
    private String uptaenm; // 업태구분명
    private String x; // 좌표정보(X)
    private String y; // 좌표정보(Y)
    private String sntuptaenm; // 위생업태명
    private String maneipcnt; // 남성종사자수
    private String wmeipcnt; // 여성종사자
    private String trdpjubnsenm; // 영업장주변 구분명
    private String lvsenm; // 등급구분명

    private String wtrsplyfacilsenm; // 급수시설구분명
    private String totepnum; // 총인원
    private String hoffepcnt; // 본사종업원수
    private String fctyowkepcnt; // 공장사무직종업원수
    private String fctysiljobepcnt; // 공장판매직종업원수
    private String fctypdtjobepcnt; // 공장생산직종업원수
    private String bdngownsenm; // 건물소유구분명
    private String isream; // 보증액
    private String monam; // 월세액
    private String multusnupsoyn; // 다중이용업소여부

    private String faciltotscp; // 시설총규모
    private String jtupsoasgnno; // 전통업소지정번호
    private String jtupsomainedf; // 전통업소주된음식
    private String homepage; // 홈페이지


}
