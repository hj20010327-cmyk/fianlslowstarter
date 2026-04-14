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

<body>
	<header class="header">
		<div class="header-left">

			<a href="./index" class="logo"> <span class="logo-mark">AM</span>
				<span>AUTO MES</span>
			</a>

			<div class="header-title">자동차 콤프레셔 제조 MES</div>
		</div>

		<script>
    		const contextPath = '${pageContext.request.contextPath}';
		</script>

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
					<li><a href="./master">기준관리</a></li>
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
					<li><a href="./product">완제품</a></li>
					<li><a href="./item">자재</a></li>
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
					<li><a href="./user">사용자관리</a></li>
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
					<div class="card-value">${dashboard.todayProductionQty}</div>
					<div class="card-meta">금일 생산 누적 수량</div>
				</div>

				<div class="card">
					<div class="card-label">작업지시 진행</div>
					<div class="card-value">${dashboard.workorderTotal}건</div>
					<div class="card-meta">진행중 ${dashboard.workorderInProgress}건
						/ 대기 ${dashboard.workorderWaiting}건</div>
				</div>

				<div class="card">
					<div class="card-label">불량 수량</div>
					<div class="card-value">${dashboard.todayDefectQty}</div>
					<div class="card-meta">금일 검사 기준</div>
				</div>

				<div class="card">
					<div class="card-label">재고 경고 품목</div>
					<div class="card-value">${dashboard.lowStockCount}개</div>
					<div class="card-meta">안전재고 이하 품목 수</div>
				</div>
			</section>

			<section class="grid-2">
				<div class="card">
					<div class="section-title">
						<h2>주간 생산 실적</h2>
						<span>최근 7일 기준</span>
					</div>
					<div style="height: 320px;">
						<canvas id="productionChart"></canvas>
					</div>
				</div>

				<div class="card">
					<div class="section-title">
						<h2>운영 비율</h2>
						<span>오늘 기준</span>
					</div>
					<div style="height: 320px;">
						<canvas id="rateChart"></canvas>
					</div>
				</div>
			</section>

			<section class="grid-bottom">
				<div class="card">
					<div class="section-title">
						<h2>대시보드 요약</h2>
						<span>실시간 KPI</span>
					</div>

					<ul class="notice-list">
						<li>
							<div>
								<strong>오늘 생산량</strong>
								<p>현재까지 ${dashboard.todayProductionQty}개 생산</p>
							</div> <span class="badge ok">생산</span>
						</li>
						<li>
							<div>
								<strong>오늘 불량 수량</strong>
								<p>현재까지 ${dashboard.todayDefectQty}건 집계</p>
							</div> <span class="badge warn">품질</span>
						</li>
						<li>
							<div>
								<strong>재고 경고 품목</strong>
								<p>안전재고 이하 ${dashboard.lowStockCount}개</p>
							</div> <span class="badge danger">재고</span>
						</li>
					</ul>
				</div>

				<div class="card">
					<div class="section-title">
						<h2>오늘의 비율</h2>
						<span>자동 계산</span>
					</div>

					<ul class="summary-list">
						<li>
							<div>
								<strong>품질 합격률</strong>
								<div class="progress-wrap">
									<div class="progress-bar"
										style="width:${dashboard.qualityPassRate}%;"></div>
								</div>
								<p>${dashboard.qualityPassRate}%</p>
							</div>
						</li>
						<li>
							<div>
								<strong>설비 가동률</strong>
								<div class="progress-wrap">
									<div class="progress-bar"
										style="width:${dashboard.equipmentRunRate}%;"></div>
								</div>
								<p>${dashboard.equipmentRunRate}%</p>
							</div>
						</li>
					</ul>
				</div>
			</section>

			<section class="card" style="margin-top: 20px;">
				<div class="section-title">
					<h2>바로가기</h2>
					<span>주요 메뉴 이동</span>
				</div>

				<div class="master-grid">
					<a class="master-card"
						href="${pageContext.request.contextPath}/workorder.jsp"> <strong>작업지시</strong>
						<p>오늘 작업지시 등록 및 진행 상태를 확인합니다.</p> <span>바로 이동</span>
					</a> <a class="master-card"
						href="${pageContext.request.contextPath}/production.jsp"> <strong>생산실적</strong>
						<p>라인별 생산량과 작업 결과를 입력하고 조회합니다.</p> <span>바로 이동</span>
					</a> <a class="master-card"
						href="${pageContext.request.contextPath}/quality.jsp"> <strong>품질관리</strong>
						<p>불량 수량, 검사 결과, 조치 내역을 관리합니다.</p> <span>바로 이동</span>
					</a> <a class="master-card"
						href="${pageContext.request.contextPath}/stock.jsp"> <strong>재고관리</strong>
						<p>자재/제품 재고와 부족 품목을 빠르게 확인합니다.</p> <span>바로 이동</span>
					</a>
				</div>
			</section>
		</main>
	</div>

	<script>
	window.addEventListener('load', function () {
	    const productionCtx = document.getElementById('productionChart');
	    const rateCtx = document.getElementById('rateChart');

	    const productionLabels = [
	        <c:forEach var="label" items="${dashboard.weekLabels}" varStatus="status">
	            "${label}"<c:if test="${!status.last}">,</c:if>
	        </c:forEach>
	    ];

	    const productionData = [
	        <c:forEach var="qty" items="${dashboard.weekProductionQtys}" varStatus="status">
	            ${qty}<c:if test="${!status.last}">,</c:if>
	        </c:forEach>
	    ];

	    const qualityPassRate = ${dashboard.qualityPassRate};
	    const equipmentRunRate = ${dashboard.equipmentRunRate};

	    if (productionCtx && typeof Chart !== 'undefined') {
	        new Chart(productionCtx, {
	            type: 'bar',
	            data: {
	                labels: productionLabels,
	                datasets: [{
	                    label: '양품 생산량',
	                    data: productionData,
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

	    if (rateCtx && typeof Chart !== 'undefined') {
	        new Chart(rateCtx, {
	            type: 'doughnut',
	            data: {
	                labels: ['품질 합격률', '설비 가동률'],
	                datasets: [{
	                    data: [qualityPassRate, equipmentRunRate],
	                    borderWidth: 1
	                }]
	            },
	            options: {
	                responsive: true,
	                maintainAspectRatio: false
	            }
	        });
	    }
	});
</script>
</body>

</html>