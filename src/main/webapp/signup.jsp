<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            <div>
                <a href="./login.html" class="logo" style="color: #fff">
                    <span class="logo-mark" style="background: rgba(255, 255, 255, .18)">AM</span>
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

            <p style="font-size: 13px; color: rgba(255, 255, 255, .72)">
                AUTO MES Sample UI · 2026
            </p>
        </section>

        <section class="auth-card">
            <div class="auth-title">
                <h2>회원가입</h2>
                <p>기본 정보를 입력해 계정을 신청하세요.</p>
            </div>

            <form class="auth-form" method="post" action="signup">
                <div class="auth-split">
                    <div class="auth-row">
                        <label for="name">이름</label>
                        <input id="name" name="name" class="auth-input" type="text" placeholder="이름 입력" value="${name }"/>
                    </div>

                    <div class="auth-row">
                        <label for="role">직책</label>
                        <select id="role" class="auth-input" name="role">
                            <option value="작업자">작업자</option>
                            <option value="관리자">관리자</option>
                            <option value="슈퍼바이저">슈퍼바이저</option>
                        </select>
                    </div>
                </div>

                <div class="auth-row">
                    <label for="phone1">휴대폰 번호</label>

                    <div class="phone-group">
                        <select id="phone1" name="phone1" class="phone-select">
                            <option value="010" selected>010</option>
                        </select>

                        <span class="phone-dash">-</span>

                        <input
                            type="text"
                            id="phone2"
                            name="phone2"
                            class="phone-input"
                            maxlength="4"
                            inputmode="numeric"
                            placeholder="1234"
                            value="${phone2 }"
                        />

                        <span class="phone-dash">-</span>

                        <input
                            type="text"
                            id="phone3"
                            name="phone3"
                            class="phone-input"
                            maxlength="4"
                            inputmode="numeric"
                            placeholder="5678"
                            value="${phone3 }"
                        />
                    </div>
                </div>

                <div class="auth-row">
                    <label for="userId">아이디</label>
                    <input id="userId" name="id" class="auth-input" type="text" placeholder="아이디 입력" value="${id}" />
                </div>

                <div class="auth-split">
                    <div class="auth-row">
                        <label for="pw">비밀번호</label>
                        <input id="pw" name="pw" class="auth-input" type="password" placeholder="비밀번호 입력" />
                    </div>

                    <div class="auth-row">
                        <label for="pwCheck">비밀번호 확인</label>
                        <input id="pwCheck" name="pwCheck" class="auth-input" type="password" placeholder="비밀번호 다시 입력" />
                    </div>
                </div>

                <div class="auth-row">
                    <label for="email">이메일</label>
                    <input id="email" name="email" class="auth-input" type="email" placeholder="이메일 입력" value="${email }"/>
                </div>

                <input class="auth-btn" type="submit" value="가입 신청" />

                <div class="auth-bottom">
                    이미 계정이 있나요? <a href="./login.html">로그인으로 이동</a>
                </div>
            </form>
        </section>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const phone2 = document.getElementById("phone2");
            const phone3 = document.getElementById("phone3");

            function onlyNumber(value) {
                return value.replace(/[^0-9]/g, "");
            }

            phone2.addEventListener("input", function () {
                this.value = onlyNumber(this.value);
                if (this.value.length === 4) {
                    phone3.focus();
                }
            });

            phone3.addEventListener("input", function () {
                this.value = onlyNumber(this.value);
            });

            phone3.addEventListener("keydown", function (e) {
                if (e.key === "Backspace" && this.value.length === 0) {
                    phone2.focus();
                }
            });
        });
    </script>
</body>
</html>