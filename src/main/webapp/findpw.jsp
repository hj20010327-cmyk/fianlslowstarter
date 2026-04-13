<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 비밀번호 찾기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/common.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/auth.css" />
</head>
<body class="auth-body">
    <div class="auth-wrap">
        <section class="auth-visual">
            <div>
                <a href="${pageContext.request.contextPath}/login.jsp" class="logo" style="color:#fff">
                    <span class="logo-mark" style="background:rgba(255,255,255,.18)">AM</span>
                    <span>AUTO MES</span>
                </a>
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
                <h2>비밀번호 찾기</h2>
                <p>가입한 아이디와 이메일을 입력하면 임시 비밀번호로 재설정됩니다.</p>
            </div>

            <c:if test="${not empty error}">
                <div style="margin-bottom:16px; padding:12px; border-radius:12px; background:#fff1f0; color:#c62828; border:1px solid #f0b8b8;">
                    ${error}
                </div>
            </c:if>

            <c:if test="${not empty success}">
                <div style="margin-bottom:16px; padding:12px; border-radius:12px; background:#e8f7ee; color:#1d7a46; border:1px solid #b9e3c9;">
                    ${success}<br>
                    <strong>임시 비밀번호: ${tempPw}</strong>
                </div>
            </c:if>

            <form class="auth-form" method="post" action="${pageContext.request.contextPath}/findpw">
                <div class="auth-row">
                    <label>아이디</label>
                    <input class="auth-input" type="text" name="id" placeholder="아이디 입력" value="${id}" />
                </div>

                <div class="auth-row">
                    <label>이메일</label>
                    <input class="auth-input" type="email" name="email" placeholder="이메일 입력" value="${email}" />
                </div>

                <button class="auth-btn" type="submit">임시 비밀번호 요청</button>

                <div class="auth-links">
                    <a href="${pageContext.request.contextPath}/login.jsp">로그인</a>
                    <a href="${pageContext.request.contextPath}/signup.jsp">회원가입</a>
                </div>
            </form>
        </section>
    </div>
</body>
</html>