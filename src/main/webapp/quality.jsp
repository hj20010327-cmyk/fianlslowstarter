<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 품질관리</title>
    <script src="./asset/js/common.js" defer></script>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/page.css" />
</head>

<body>
    <header class="header">
        <div class="header-left">
            <a href="./index.html" class="logo"><span class="logo-mark">AM</span><span>AUTO MES</span></a>
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
                <div class="snb-title">재고관리</div>
                <ul class="snb-menu">
                    <li><a href="stockList">재고</a></li>
                    <li><a href="./product.html">완제품</a></li>
                    <li><a href="./item.html">자재</a></li>
                </ul>
            </div>
            <div class="snb-section">
                <div class="snb-title">품질관리</div>
                <ul class="snb-menu">
                    <li class="active"><a href="qualityList">품질 <span class="menu-badge">2</span></a></li>
                </ul>
            </div>
            </aside>

        <div class="snb-overlay" id="snbOverlay"></div>
        
        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>품질관리</h1>
                    <p>검사 결과와 불량 유형, 조치 현황을 관리합니다.</p>
                </div>
                <div class="page-actions">
                    <button class="btn primary" type="button">신규 등록</button>
                </div>
            </div>

            <section class="card" style="margin-bottom:20px">
                <div class="section-title">
                    <h2>검색 조건</h2><span>기준 조건을 선택하세요</span>
                </div>
                <form action="qualityList" method="get">
                    <div class="search-row">
                        <input class="input" type="text" name="searchCode" placeholder="검사번호 입력" value="${param.searchCode}" />
                        <input class="input" type="text" name="searchName" placeholder="제품명 입력" value="${param.searchName}" />
                        <select class="select" name="searchResult">
                            <option value="">전체 판정</option>
                            <option value="합격" ${param.searchResult == '합격' ? 'selected' : ''}>합격</option>
                            <option value="재검" ${param.searchResult == '재검' ? 'selected' : ''}>재검</option>
                        </select>
                        <button class="btn primary" type="submit">조회</button>
                    </div>
                </form>
            </section>

            <section class="panel-grid">
                <div class="card">
                    <div class="section-title" style="display: flex; justify-content: space-between; align-items: center;">
                        <h2>품질관리 목록</h2>
                        <button class="btn" type="button" style="background-color: #ffffff; color: #333333; border: 1px solid #dddddd;">삭제</button>
                    </div>
                    <div class="table-wrap">
                        <table style="width: 100%; border-collapse: collapse;">
                            <thead>
                                <tr style="border-bottom: 2px solid #f1f1f1; text-align: left;">
                                    <th style="padding: 12px; color: #666;">검사번호</th>
                                    <th style="padding: 12px; color: #666;">제품명</th>
                                    <th style="padding: 12px; color: #666;">검사수량</th>
                                    <th style="padding: 12px; color: #666;">불량수량</th>
                                    <th style="padding: 12px; color: #666;">판정</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="dto" items="${list}">
                                    <tr style="border-bottom: 1px solid #f8f9fa;">
                                        <td style="padding: 15px 12px; color: #4a90e2;">${dto.inspect_code}</td>
                                        <td style="padding: 15px 12px; font-weight: 500;">${dto.item_name}</td>
                                        <td style="padding: 15px 12px;">${dto.inspect_qty}</td>
                                        <td style="padding: 15px 12px; color: #e74c3c;">${dto.bad_qty}</td>
                                        <td style="padding: 15px 12px;">
                                            <span style="color: ${dto.result == '재검' ? '#f39c12' : '#2ecc71'}; font-weight: bold;">
                                                ${dto.result}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty list}">
                                    <tr>
                                        <td colspan="5" style="text-align:center; padding:40px; color: #999;">조회된 데이터가 없습니다.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="card">
                    <div class="section-title">
                        <h2>요약 / 상태</h2><span>오늘 기준</span>
                    </div>
                    <ul class="summary-list">
                        <li>
                            <div><strong>재검 건수</strong><p>재검 대상이 1건 있습니다.</p></div>
                            <span class="badge warn">1건</span>
                        </li>
                        <li>
                            <div><strong>주요 불량</strong><p>압력 불량 유형이 가장 많습니다.</p></div>
                            <span class="badge danger">확인</span>
                        </li>
                        <li>
                            <div><strong>합격률</strong><p>금일 평균 합격률 96.5%입니다.</p></div>
                            <span class="badge ok">양호</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>
    </div>
</body>
</html>