<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 생산계획</title>
    <script src="./asset/js/common.js" defer></script>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/page.css" />
</head>
<body>
   <header class="header">
		<div class="header-left">
			<a href="./index.html" class="logo"><span class="logo-mark">AM</span><span>AUTO
					MES</span></a>
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
					<li><a href="./mypage.html">마이페이지</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">MASTER</div>
				<ul class="snb-menu">
					<li><a href="./product.html">제품관리</a></li>
					<li class="active"><a href="./item.html">품목관리</a></li>
					<li><a href="./bom.html">BOM관리</a></li>
					<li><a href="./process.html">공정관리</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">OPERATION</div>
				<ul class="snb-menu">
					<li><a href="./workorder.html">작업지시 <span
							class="menu-badge">4</span></a></li>
					<li><a href="./production.html">생산실적</a></li>
					<li><a href="./quality.html">품질관리 <span class="menu-badge">2</span></a></li>
					<li><a href="./stock.html">재고관리</a></li>
					<li><a href="./machine.html">설비</a></li>
					<li><a href="./plan.html">생산계획</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">SYSTEM</div>
				<ul class="snb-menu">
					<li><a href="./report.html">리포트</a></li>
					<li><a href="./user.html">사용자관리</a></li>
				</ul>
			</div>
		</aside>
     <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>재고관리</h1>
                    <p>원자재, 부품, 완제품 재고를 확인하고 부족 재고를 점검합니다.</p>
                </div>
                <div class="page-actions"><button class="btn primary">신규 등록</button></div>
            </div>

            <section class="card mb-20">
                <div class="section-title"><h2>검색 조건</h2></div>
                <form action="stockList" method="get">
                    <div class="search-row">
                        <input class="input" type="text" name="searchCode" placeholder="코드 입력" value="${param.searchCode}">
                        <input class="input" type="text" name="searchName" placeholder="명칭 입력" value="${param.searchName}">
                        <button class="btn primary">조회</button>
                    </div>
                </form>
            </section>

            <div class="panel-grid">
                <div class="card">
                    <div class="section-title" style="display: flex; justify-content: space-between;">
                        <h2>재고관리 목록</h2><button class="btn btn-sm">삭제</button>
                    </div>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <%-- [수정] 테이블 제목을 상민님 새 사진의 데이터 순서로 맞춤 --%>
                                <tr>
                                    <th>No</th>
                                    <th>품목 코드</th>
                                    <th>입고량</th>
                                    <th>출고량</th>
                                    <th>현재고</th>
                                    <th>안전재고</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%-- [수정] 데이터가 안 나오던 이유: 이름표(${dto.lot_key})가 틀렸음. 
                                     새 DB 사진의 컬럼명인 item_key, current_qty 등으로 이름표를 교체함 --%>
                                <c:forEach var="dto" items="${list}">
                                    <tr>
                                        <td>${dto.stock_key}</td>
                                        <td>${dto.item_key}</td>
                                        <td>${dto.in_qty}</td>
                                        <td>${dto.out_qty}</td>
                                        <td>${dto.current_qty}</td>
                                        <td>${dto.safe_qty}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="card">
                    <div class="section-title"><h2>요약 / 상태</h2><span>오늘 기준</span></div>
                    <ul class="summary-list">
                        <li><div><strong>안전재고 미만</strong></div><span class="badge danger">부족</span></li>
                        <li><div><strong>창고 상태</strong></div><span class="badge ok">완료</span></li>
                    </ul>
                </div>
            </div>
        </main>
    </div>
</body>
</html>