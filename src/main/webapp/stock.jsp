<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 재고관리</title>
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
                    <li class="active"><a href="stockList">재고</a></li>
                    <li><a href="./product.html">완제품</a></li>
                    <li><a href="./item.html">자재</a></li>
                </ul>
            </div>
        </aside>

        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>재고관리</h1>
                    <p>원자재, 부품, 완제품 재고를 확인하고 부족 재고를 점검합니다.</p>
                </div>
                <div class="page-actions">
                    <button class="btn primary" type="button">신규 등록</button>
                </div>
            </div>

            <section class="card" style="margin-bottom:20px">
                <div class="section-title">
                    <h2>검색 조건</h2><span>기준 조건을 선택하세요</span>
                </div>
                <form action="stockList" method="get">
                    <div class="search-row">
                        <input class="input" type="text" name="searchCode" placeholder="코드 또는 번호 입력" value="${param.searchCode}" />
                        <input class="input" type="text" name="searchName" placeholder="명칭 입력" value="${param.searchName}" />
                        <select class="select" name="searchType">
                            <option value="">전체</option>
                            <option value="원자재" ${param.searchType == '원자재' ? 'selected' : ''}>원자재</option>
                            <option value="부자재" ${param.searchType == '부자재' ? 'selected' : ''}>부자재</option>
                            <option value="반제품" ${param.searchType == '반제품' ? 'selected' : ''}>반제품</option>
                        </select>
                        <button class="btn primary" type="submit">조회</button>
                    </div>
                </form>
            </section>

            <div class="panel-grid">
                <div class="card">
                    <div class="section-title" style="display: flex; justify-content: space-between; align-items: center;">
                        <h2>재고관리 목록</h2>
                        <button class="btn" type="button" style="background-color: #ffffff; color: #333333; border: 1px solid #dddddd;">삭제</button>
                    </div>
                    <div class="table-wrap">
                        <table style="width: 100%; border-collapse: collapse;">
                            <thead>
                                <tr style="border-bottom: 2px solid #f1f1f1; text-align: left;">
                                    <th style="padding: 12px; color: #666;">품목 코드</th>
                                    <th style="padding: 12px; color: #666;">품목명</th>
                                    <th style="padding: 12px; color: #666;">모델</th> 
                                    <th style="padding: 12px; color: #666;">현재고</th>
                                    <th style="padding: 12px; color: #666;">공급업체</th>
                                    <th style="padding: 12px; color: #666;">상태</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="dto" items="${list}">
                                    <tr style="border-bottom: 1px solid #f8f9fa;">
                                        <td style="padding: 15px 12px; color: #4a90e2;">${dto.lot_key}</td>
                                        <td style="padding: 15px 12px; font-weight: 500;">${dto.name}</td>
                                        <td style="padding: 15px 12px; color: #666;">${dto.type}</td>
                                        <td style="padding: 15px 12px; font-weight: bold;">${dto.remain}</td>
                                        <td style="padding: 15px 12px; color: #888;">${dto.vender}</td>
                                        <td style="padding: 15px 12px;">
                                            <span style="color: ${dto.status == '부족' ? '#e74c3c' : '#2ecc71'};">
                                                ${dto.status != null ? dto.status : '사용'}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty list}">
                                    <tr>
                                        <td colspan="6" style="text-align:center; padding:40px; color: #999;">조회된 데이터가 없습니다.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="card">
                    <div class="section-title">
                        <h2>재고관리 상태 요약</h2><span>오늘 기준</span>
                    </div>
                    <ul class="summary-list">
                        <li>
                            <div><strong>안전재고 미만</strong><p>샤프트 품목이 부족합니다.</p></div>
                            <span class="badge danger">부족</span>
                        </li>
                        <li>
                            <div><strong>창고 상태</strong><p>전체 재고 데이터 동기화 완료.</p></div>
                            <span class="badge ok">완료</span>
                        </li>
                    </ul>
                </div>
            </div>
        </main>
    </div>
</body>
</html>