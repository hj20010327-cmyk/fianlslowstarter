<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 사용자관리</title>
    <script src="./asset/js/common.js" defer></script>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/page.css" />
</head>

<body>
    <header class="header">
        <div class="header-left"><a href="./index.html" class="logo"><span class="logo-mark">AM</span><span>AUTO
                    MES</span></a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip date"></div>
            <div class="header-chip">${dto.user_name}</div>
            <div class="header-chip">${dto.user_role }</div>
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
                    <li><a href="./quality.html">품질<span class="menu-badge">2</span></a></li>
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
                    <li class="active"><a href="./user.html">사용자관리</a></li>
                    <li><a href="./mypage.html">마이페이지</a></li>
                </ul>
            </div>
        </aside>

        <div class="snb-overlay" id="snbOverlay"></div>
        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>사용자관리</h1>
                    <p>시스템 사용자 계정과 권한을 관리합니다.</p>
                </div>
                <div class="page-actions"><button class="btn" type="button">권한 설정</button><button class="btn primary"
                        type="button">사용자 등록</button></div>
            </div>
            <section class="card" style="margin-bottom:20px">
                <div class="section-title">
                    <h2>사용자 조회</h2><span>권한 / 부서별 검색</span>
                </div>
                <div class="search-row"><input class="input" type="text" placeholder="이름 또는 사번 입력" /><input
                        class="input" type="text" placeholder="부서 입력" /><select class="select">
                        <option>전체 권한</option>
                        <option>관리자</option>
                        <option>작업자</option>
                        <option>조회자</option>
                    </select><button class="btn primary" type="button">조회</button></div>
            </section>
            <section class="two-col">
                <div class="card">
                    <div class="section-title">
                        <h2>사용자 목록</h2><span>총 18명</span>
                    </div>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th>사번</th>
                                    <th>이름</th>
                                    <th>부서</th>
                                    <th>권한</th>
                                    <th>상태</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>ADM001</td>
                                    <td>김관리</td>
                                    <td>생산관리팀</td>
                                    <td>관리자</td>
                                    <td>사용</td>
                                </tr>
                                <tr>
                                    <td>PRD021</td>
                                    <td>이현장</td>
                                    <td>생산팀</td>
                                    <td>작업자</td>
                                    <td>사용</td>
                                </tr>
                                <tr>
                                    <td>QC014</td>
                                    <td>박품질</td>
                                    <td>품질팀</td>
                                    <td>작업자</td>
                                    <td>사용</td>
                                </tr>
                                <tr>
                                    <td>STK009</td>
                                    <td>최자재</td>
                                    <td>자재팀</td>
                                    <td>조회자</td>
                                    <td>잠금</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card">
                    <div class="section-title">
                        <h2>권한 요약</h2><span>현재 기준</span>
                    </div>
                    <ul class="summary-list">
                        <li>
                            <div><strong>관리자</strong>
                                <p>전체 메뉴 접근 및 설정 변경 가능</p>
                            </div><span class="badge ok">2명</span>
                        </li>
                        <li>
                            <div><strong>작업자</strong>
                                <p>생산/품질/재고 업무 처리</p>
                            </div><span class="badge warn">12명</span>
                        </li>
                        <li>
                            <div><strong>조회자</strong>
                                <p>조회 전용 권한</p>
                            </div><span class="badge danger">4명</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>
    </div>
</body>

</html>