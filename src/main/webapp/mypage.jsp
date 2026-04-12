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
    <title>AUTO MES | 마이페이지</title>
    <script src="./asset/js/common.js" defer></script>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/page.css" />
    <style>
        .msg-box {
            margin-bottom: 16px;
            padding: 12px 14px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 500;
        }
        .msg-success { background:#e8f7ee; color:#1d7a46; border:1px solid #b9e3c9; }
        .msg-error { background:#fff1f0; color:#c62828; border:1px solid #f0b8b8; }
    </style>
</head>
<body>
    <header class="header">
        <div class="header-left">
            <a href="./index.jsp" class="logo">
                <span class="logo-mark">AM</span>
                <span>AUTO MES</span>
            </a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip date"></div>
            <div class="header-chip">${dto.user_name}</div>
            <div class="header-chip">${dto.user_role}</div>
        </div>
        <button type="button" class="menu-toggle" id="menuToggle">☰</button>
    </header>

    <div class="layout">
        <aside class="snb" id="snb">
            <div class="snb-section">
                <div class="snb-title">MAIN</div>
                <ul class="snb-menu">
                    <li><a href="./index.html">대시보드</a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">기준관리</div>
                <ul class="snb-menu">
                    <li><a href="./master.html">기준관리</a></li>
                    <li><a href="./bom.html">BOM</a></li>
                    <li><a href="./process.html">공정</a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">생산관리</div>
                <ul class="snb-menu">
                    <li><a href="./workorder.html">작업지시 <span class="menu-badge">4</span></a></li>
                    <li><a href="./plan.html">생산계획 <span class="menu-badge">2</span></a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">재고관리</div>
                <ul class="snb-menu">
                    <li><a href="./stock.html">재고</a></li>
                    <li><a href="./product.html">완제품</a></li>
                    <li><a href="./item.html">자재</a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">품질관리</div>
                <ul class="snb-menu">
                    <li><a href="./quality.html">품질 <span class="menu-badge">2</span></a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">리포트</div>
                <ul class="snb-menu">
                    <li><a href="./report.html">리포트</a></li>
                    <li><a href="./production.html">생산실적</a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">시스템</div>
                <ul class="snb-menu">
                    <li><a href="./user">사용자관리</a></li>
                    <li class="active"><a href="./mypage">마이페이지</a></li>
                </ul>
            </div>
        </aside>

        <div class="snb-overlay" id="snbOverlay"></div>

        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>마이페이지</h1>
                    <p>내 계정 정보와 비밀번호를 변경할 수 있습니다.</p>
                </div>
            </div>

            <c:if test="${param.msg == 'success'}">
                <div class="msg-box msg-success">회원 정보가 정상적으로 수정되었습니다.</div>
            </c:if>
            <c:if test="${param.msg == 'fail'}">
                <div class="msg-box msg-error">수정에 실패했습니다. 다시 시도해주세요.</div>
            </c:if>
            <c:if test="${param.msg == 'wrongPw'}">
                <div class="msg-box msg-error">현재 비밀번호가 일치하지 않습니다.</div>
            </c:if>
            <c:if test="${param.msg == 'pwMismatch'}">
                <div class="msg-box msg-error">새 비밀번호와 비밀번호 확인이 일치하지 않습니다.</div>
            </c:if>
            <c:if test="${param.msg == 'pwShort'}">
                <div class="msg-box msg-error">새 비밀번호는 8자 이상이어야 합니다.</div>
            </c:if>
            <c:if test="${param.msg == 'emptyPw'}">
                <div class="msg-box msg-error">현재 비밀번호를 입력해주세요.</div>
            </c:if>

            <form method="post" action="mypage">
                <section class="two-col">
                    <div class="card">
                        <div class="section-title">
                            <h2>내 정보</h2>
                            <span>기본 프로필</span>
                        </div>

                        <div class="form-grid">
                            <div class="form-group">
                                <label>아이디</label>
                                <input class="input" type="text" value="${dto.user_id}" readonly />
                            </div>

                            <div class="form-group">
                                <label>사번</label>
                                <input class="input" type="text" value="${dto.emp_no}" readonly />
                            </div>

                            <div class="form-group">
                                <label>이름</label>
                                <input name="name" class="input" type="text" value="${dto.user_name}" required />
                            </div>

                            <div class="form-group">
                                <label>이메일</label>
                                <input name="email" class="input" type="email" value="${dto.user_email}" />
                            </div>

                            <div class="form-group">
                                <label>연락처</label>
                                <input name="phone" class="input" type="text" value="${dto.user_phone}" />
                            </div>

                            <div class="form-group">
                                <label>직책</label>
                                <input class="input" type="text" value="${dto.user_role}" readonly />
                            </div>
                        </div>
                    </div>

                    <div class="card">
                        <div class="section-title">
                            <h2>비밀번호 변경</h2>
                            <span>비밀번호를 바꾸지 않으면 비워두세요</span>
                        </div>

                        <div class="form-grid">
                            <div class="form-group">
                                <label>현재 비밀번호</label>
                                <input name="pw" class="input" type="password"/>
                            </div>
                            <div class="form-group">
                                <label>새 비밀번호</label>
                                <input name="npw" class="input" type="password" />
                            </div>
                            <div class="form-group">
                                <label>새 비밀번호 확인</label>
                                <input name="cnpw" class="input" type="password" />
                            </div>
                        </div>
                    </div>
                </section>

                <div class="page-actions" style="margin-top:20px;">
                    <input style="cursor:pointer;" class="btn primary" type="submit" value="저장">
                </div>
            </form>
        </main>
    </div>
</body>
</html>