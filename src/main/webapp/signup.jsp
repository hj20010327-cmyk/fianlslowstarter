<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 회원가입</title>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/auth.css" />
</head>

<body class="auth-body">
    <div class="auth-wrap">
        <section class="auth-visual">
            <div><a href="./login.html" class="logo" style="color:#fff"><span class="logo-mark"
                        style="background:rgba(255,255,255,.18)">AM</span><span>AUTO MES</span></a>
                <h1>자동차 콤프레셔 제조 공장을 위한 MES</h1>
                <p>생산, 품질, 자재, 설비 정보를 한곳에서 관리하는 통합 시스템 샘플입니다.</p>
                <ul class="auth-bullets">
                    <li>작업지시와 생산실적을 빠르게 확인</li>
                    <li>불량 이력과 품질 현황 즉시 점검</li>
                    <li>재고 부족과 설비 상태를 한눈에 파악</li>
                </ul>
            </div>
            <p style="font-size:13px;color:rgba(255,255,255,.72)">AUTO MES Sample UI · 2026</p>
        </section>
        <section class="auth-card">
            <div class="auth-title">
                <h2>회원가입</h2>
                <p>기본 정보를 입력해 계정을 신청하세요.</p>
            </div>
            <form class="auth-form" method="post" action="">
                <div class="auth-split">
                    <div class="auth-row"><label>이름</label><input class="auth-input" type="text" placeholder="이름 입력" />
                    </div>
                    <div class="auth-row"><label>휴대폰 번호</label><input class="auth-input" type="text" placeholder="-제외" />
                    </div>
                </div>
                <div class="auth-split">
                    <div class="auth-row"><label>부서</label><input class="auth-input" type="text" placeholder="부서 입력" />
                    </div>
                    <div class="auth-row"><label>직책</label>
                    <select class="auth-input">
                    	<option>작업자</option>
                    	<option>관리자</option>
                    	<option>슈퍼바이저</option>
                    </select>
                    </div>
                </div>
                    <div class="auth-row"><label>아이디</label><input class="auth-input" type="text"
                            placeholder="아이디 입력" /></div>
                <div class="auth-split">
                    <div class="auth-row"><label>비밀번호</label><input class="auth-input" type="password"
                            placeholder="비밀번호 입력" /></div>
                    <div class="auth-row"><label>비밀번호 확인</label><input class="auth-input" type="password"
                            placeholder="비밀번호 다시 입력" /></div>
                </div>
                <div class="auth-row"><label>이메일</label><input class="auth-input" type="email" placeholder="이메일 입력" />
                </div>
                <input class="auth-btn" type="submit" value="가입 신청"></button>
                <div class="auth-bottom">이미 계정이 있나요? <a href="./login.html">로그인으로 이동</a></div>
            </form>
        </section>
    </div>
</body>

</html>