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
</head>

<body>
    <header class="header">
        <div class="header-left">
            <a href="./index.html" class="logo">
                <span class="logo-mark">AM</span>
                <span>AUTO MES</span>
            </a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip">2026-03-30</div>
            <div class="header-chip">생산 1라인 가동중</div>
            <div class="header-chip">관리자</div>
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
                    <li><a href="./user.html">사용자관리</a></li>
                    <li class="active"><a href="./mypage.html">마이페이지</a></li>
                </ul>
            </div>
        </aside>

        <div class="snb-overlay" id="snbOverlay"></div>

        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>마이페이지</h1>
                    <p>내 계정 정보와 최근 작업 내역을 확인할 수 있습니다.</p>
                </div>
                <div class="page-actions">
                    <button class="btn" type="button">취소</button>
                    <button class="btn primary" type="button">저장</button>
                </div>
            </div>

            <section class="two-col">
                <div class="card">
                    <div class="section-title">
                        <h2>내 정보</h2>
                        <span>기본 프로필</span>
                    </div>
                    <div class="form-grid">
                        <div class="form-group">
                            <label>이름</label>
                            <input class="input" type="text" value="관리자" />
                        </div>
                        <div class="form-group">
                            <label>사번</label>
                            <input class="input" type="text" value="ADM001" />
                        </div>
                        <div class="form-group">
                            <label>부서</label>
                            <input class="input" type="text" value="생산관리팀" />
                        </div>
                        <div class="form-group">
                            <label>이메일</label>
                            <input class="input" type="text" value="admin@automes.com" />
                        </div>
                        <div class="form-group">
                            <label>연락처</label>
                            <input class="input" type="text" value="010-1234-5678" />
                        </div>
                        <div class="form-group">
                            <label>권한</label>
                            <input class="input" type="text" value="시스템 관리자" />
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="section-title">
                        <h2>최근 활동</h2>
                        <span>최근 7일</span>
                    </div>
                    <ul class="summary-list">
                        <li>
                            <div>
                                <strong>작업지시 2건 등록</strong>
                                <p>2026-03-30 09:10</p>
                            </div>
                            <span class="badge ok">등록</span>
                        </li>
                        <li>
                            <div>
                                <strong>품목 정보 수정</strong>
                                <p>2026-03-29 15:20</p>
                            </div>
                            <span class="badge warn">수정</span>
                        </li>
                        <li>
                            <div>
                                <strong>사용자 권한 변경</strong>
                                <p>2026-03-28 11:08</p>
                            </div>
                            <span class="badge danger">권한</span>
                        </li>
                    </ul>
                </div>
            </section>

            <section class="card" style="margin-top:20px">
                <div class="section-title">
                    <h2>비밀번호 변경</h2>
                    <span>보안 설정</span>
                </div>
                <div class="form-grid">
                    <div class="form-group">
                        <label>현재 비밀번호</label>
                        <input class="input" type="password" />
                    </div>
                    <div class="form-group">
                        <label>새 비밀번호</label>
                        <input class="input" type="password" />
                    </div>
                    <div class="form-group">
                        <label>새 비밀번호 확인</label>
                        <input class="input" type="password" />
                    </div>
                </div>
            </section>
        </main>
    </div>
</body>
</html>