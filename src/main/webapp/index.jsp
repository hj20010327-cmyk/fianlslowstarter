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
<title>AUTO MES | 대시보드</title>
<script src="./asset/js/common.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
</head>

<body data-auto-logout="true">
	<header class="header">
		<script>
							const contextPath = "${pageContext.request.contextPath}";
							const sessionTimeoutSeconds = <%=session.getMaxInactiveInterval()%>;
						</script>
		<div class="header-left">

			<a href="./index" class="logo"> <span class="logo-mark">AM</span>
				<span>AUTO MES</span>
			</a>

			<div class="header-title">자동차 콤프레셔 제조 MES</div>
		</div>

		<div class="header-right">
			<div class="header-chip date"></div>
			<div class="header-chip">${dto.user_name}님</div>
			<button class="btn logout-btn" onclick="logout()">로그아웃</button>
		</div>
		<button type="button" class="menu-toggle" id="menuToggle">☰</button>
	</header>

	<div class="layout">
		<aside class="snb" id="snb">
			<div class="snb-section">
				<div class="snb-title">MAIN</div>
				<ul class="snb-menu">
					<li class="active"><a href="./index">대시보드</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">기준관리</div>
				<ul class="snb-menu">
					<li><a href="./item">품목관리</a></li>
					<li><a href="./bom">BOM</a></li>
					<li><a href="./process">공정</a></li>
					<li><a href="/slowstarter/machine">설비</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">생산관리</div>
				<ul class="snb-menu">
					<li><a href="/slowstarter/workorder">작업지시</a></li>
					<li><a href="/slowstarter/plan">생산계획</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">재고관리</div>
				<ul class="snb-menu">
					<li><a href="./stock">재고</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">품질관리</div>
				<ul class="snb-menu">
					<li><a href="qualityList">품질</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">리포트</div>
				<ul class="snb-menu">
					<li><a href="./report">리포트</a></li>
					<li><a href="./production">생산실적</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">시스템</div>
				<ul class="snb-menu">
					<li><a href="./board">게시판</a></li>
					<c:if test='${dto.user_role eq "슈퍼바이저"}'>
						<li><a href="./user">사용자관리</a></li>
					</c:if>
					<li><a href="./mypage">마이페이지</a></li>
				</ul>
			</div>
		</aside>

		<div class="snb-overlay" id="snbOverlay"></div>

		<main class="content">
			<div class="page-head">
				<div class="page-head-left">
					<h1>대시보드</h1>
					<p>생산, 품질, 재고, 작업 현황을 한눈에 확인할 수 있습니다.</p>
				</div>
				<div class="page-actions">
					<a href="${pageContext.request.contextPath}/index"
						class="btn primary">현황 새로고침</a>
				</div>
			</div>

			<section class="top-cards">
				<div class="card">
					<div class="card-label">오늘 생산량</div>
					<div class="card-value">${dashboard.todayProdQty}</div>
					<div class="card-meta">양품 기준</div>
				</div>
				<div class="card">
				<a href="#workOrder">
					<div class="card-label">오늘 작업지시</div>
					<div class="card-value">${dashboard.todayWorkorderCnt}</div>
					<div class="card-meta">당일 등록 기준</div>
					</a>
				</div>
				<div class="card">
					<div class="card-label">오늘 불량 수량</div>
					<div class="card-value">${dashboard.todayDefectQty}</div>
					<div class="card-meta">검사 기준</div>
				</div>
				<div class="card">
					<a href="#stockdetail">
						<div class="card-label">재고 부족 품목</div>
						<div class="card-value">${dashboard.lowStockCnt}</div>
						<div class="card-meta">안전재고 이하</div>
					</a>
				</div>
			</section>

			<section class="grid-2">
				<div class="card">
					<div class="section-title">
						<h2>최근 7일 생산량</h2>
					</div>
					<div style="height: 320px;">
						<canvas id="prodChart"></canvas>
					</div>
				</div>

				<div class="card">
					<div class="section-title">
						<h2>최근 7일 불량 수량</h2>
					</div>
					<div style="height: 320px;">
						<canvas id="defectChart"></canvas>
					</div>
				</div>
			</section>

				<div class="card">
					<div id="workOrder" class="section-title">
						<c:choose>
							<c:when test="${dto.user_role eq '작업자'}">
								<h2>오늘 나의 작업지시</h2>
							</c:when>
							<c:otherwise>
								<h2>오늘 작업지시</h2>
							</c:otherwise>
						</c:choose>
					</div>

					<table>
						<thead>
							<tr>
								<th>코드</th>
								<th>품목명</th>
								<th>수량</th>
								<th>작업일</th>
								<th>지시자</th>
								<th>작업자</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="w" items="${dashboard.workorderList}">
								<tr>
									<td>${w.work_order_code}</td>
									<td>${w.item_name}</td>
									<td>${w.order_qty}</td>
									<td><fmt:formatDate value="${w.work_date}"
											pattern="yyyy-MM-dd" /></td>
									<td>${w.order_user_name}</td>
									<td>${w.work_user_name}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

			<section class="grid-bottom">
				<div class="card" style="margin-top: 20px;">
					<div id="stockdetail" class="section-title">
						<h2>재고 부족 품목</h2>
						<a href="./stock" style="font-size:14px; color:gray">더보기</a>
					</div>
					<ul class="status-list">
						<c:forEach var="s" items="${dashboard.lowStockList}">
							<li><a href="/slowstarter/stock?p=1&itemType=all&lotKeyword=&itemCodeKeyword=${s.item_code}&itemNameKeyword=">
							<span>${s.item_code} / ${s.item_name}</span> <span
								class="badge danger">${s.current_qty} / ${s.safe_qty}</span></a></li>
						</c:forEach>
					</ul>
				</div>

			<div class="card" style="margin-top: 20px;">
				<div class="section-title">
					<h2>최근 공지</h2>
						<a href="./board" style="font-size:14px; color:gray">더보기</a>
				</div>
				<ul class="notice-list">
					<c:forEach var="n" items="${dashboard.noticeList}">
						<li>
							<a href="./board?action=detail&board_key=${n.board_key}">
								<div>
									<strong>${n.title}</strong>
									<p>
										<fmt:formatDate value="${n.created_at}" pattern="yyyy-MM-dd" />
										/ 조회수 ${n.view_count}
									</p>
								</div>
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			</section>
		</main>
	</div>

	<script>
						const prodLabels = [
							<c:forEach var="label" items="${dashboard.prodLabels}" varStatus="st">
								"${label}"<c:if test="${!st.last}">,</c:if>
							</c:forEach>
						];

						const prodData = [
							<c:forEach var="d" items="${dashboard.prodData}" varStatus="st">
								${d}<c:if test="${!st.last}">,</c:if>
							</c:forEach>
						];

						const defectLabels = [
							<c:forEach var="label" items="${dashboard.defectLabels}" varStatus="st">
								"${label}"<c:if test="${!st.last}">,</c:if>
							</c:forEach>
						];

						const defectData = [
							<c:forEach var="d" items="${dashboard.defectData}" varStatus="st">
								${d}<c:if test="${!st.last}">,</c:if>
							</c:forEach>
						];

						window.addEventListener('load', function () {
							const prodCtx = document.getElementById('prodChart');
							const defectCtx = document.getElementById('defectChart');

							if (prodCtx && typeof Chart !== 'undefined') {
								new Chart(prodCtx, {
									type: 'bar',
									data: {
										labels: prodLabels,
										datasets: [{
											label: '생산량',
											data: prodData,
											borderWidth: 1,
											borderRadius: 8
										}]
									},
									options: {
										responsive: true,
										maintainAspectRatio: false,
										scales: {
											y: { beginAtZero: true }
										}
									}
								});
							}

							if (defectCtx && typeof Chart !== 'undefined') {
								new Chart(defectCtx, {
									type: 'line',
									data: {
										labels: defectLabels,
										datasets: [{
											label: '불량 수량',
											data: defectData,
											tension: 0.3,
											fill: false,
											borderWidth: 2
										}]
									},
									options: {
										responsive: true,
										maintainAspectRatio: false,
										scales: {
											y: { beginAtZero: true }
										}
									}
								});
							}
						});
					</script>
</body>

</html>