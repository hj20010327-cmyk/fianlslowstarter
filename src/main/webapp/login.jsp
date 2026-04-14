<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>AUTO MES | 로그인</title>
<script src="./asset/js/auth.js"></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/auth.css" />
</head>

<body class="auth-body">
	<div class="auth-wrap">
		<section class="auth-visual">
			<div>
				<a href="./login.html" class="logo" style="color: #fff"><span
					class="logo-mark" style="background: rgba(255, 255, 255, .18)">AM</span><span>AUTO
						MES</span></a>
				<h1>자동차 콤프레셔 제조 공장을 위한 MES</h1>
				<p>생산, 품질, 자재, 설비 정보를 한곳에서 관리하는 통합 시스템 샘플입니다.</p>
				<ul class="auth-bullets">
					<li>작업지시와 생산실적을 빠르게 확인</li>
					<li>불량 이력과 품질 현황 즉시 점검</li>
					<li>재고 부족과 설비 상태를 한눈에 파악</li>
				</ul>
			</div>
			<p style="font-size: 13px; color: rgba(255, 255, 255, .72)">AUTO
				MES Sample UI · 2026</p>
		</section>
		<section class="auth-card">
			<div class="auth-title">
				<h2>로그인</h2>
				<p>계정 정보를 입력하고 시스템에 접속하세요.</p>
			</div>
			<form class="auth-form" method="post" action="login">
				<div class="auth-row">
					<label>아이디</label><input name="id" class="auth-input" type="text"
						placeholder="아이디 입력" />
				</div>
				<div class="auth-row">
					<label>비밀번호</label><input name="pw" class="auth-input"
						type="password" placeholder="비밀번호 입력" />
				</div>
				<c:if test="${not empty error}">
				<div style=color:red;>${error}</div>
				</c:if>
				<label class="checkbox-row"><input type="checkbox" /> 아이디
					저장</label>
				<input class="auth-btn" type="submit" value="로그인">
				<button class="auth-subbtn" type="button"
					onclick="location.href='./signup'">회원가입</button>
				<div class="auth-links">
					<a href="./findpw">비밀번호 찾기</a>
				</div>
			</form>
		</section>
	</div>
</body>

</html>