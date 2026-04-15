<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AUTO MES | 리포트</title>
<script src="./asset/js/common.js" defer></script>
<script src="./asset/js/report.js"></script>
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
					<li><a href="./index">대시보드</a></li>
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
					<li class="active"><a href="./report">리포트</a></li>
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
					<h1>리포트</h1>
					<p>생산계획, 작업지시, 생산실적, 품질 데이터를 통합 조회합니다.</p>
				</div>

				<div class="page-actions">
					<a href="${pageContext.request.contextPath}/report" class="btn">초기화</a>
				</div>
			</div>

			<c:if test="${not empty errorMsg}">
				<div class="card"
					style="margin-bottom: 20px; color: #d64545; font-weight: 700;">
					${errorMsg}</div>
			</c:if>

			<div class="card" style="margin-bottom: 20px;">
				<form action="${pageContext.request.contextPath}/report"
					method="get" class="search-row">
					<input type="date" name="startDate" class="input"
						value="${startDate}" /> <input type="date" name="endDate"
						class="input" value="${endDate}" />
					<button type="submit" class="btn primary">조회</button>
				</form>
			</div>

			<section class="kpi-grid report-kpi-grid">
				<div class="card report-kpi-grid">
					<div class="card-label">총 계획수량</div>
					<div class="card-value">
						<fmt:formatNumber value="${summary.totalPlanQty}" pattern="#,##0" /><span style="font-size: 20px">EA</span>
					</div>
				</div>

				<div class="card report-kpi-grid">
					<div class="card-label">총 양품수량</div>
					<div class="card-value">
						<fmt:formatNumber value="${summary.totalGoodQty}" pattern="#,##0" /><span style="font-size: 20px">EA</span>
					</div>
				</div>

				<div class="card report-kpi-grid">
					<div class="card-label">총 불량수량</div>
					<div class="card-value">
						<fmt:formatNumber value="${summary.totalDefectQty}"
							pattern="#,##0" /><span style="font-size: 20px">EA</span>
					</div>
				</div>

				<div class="card report-kpi-grid">
					<div class="card-label">달성률</div>
					<div class="card-value">
						<fmt:formatNumber value="${summary.achievementRate}"
							pattern="0.00" />
						%
					</div>
				</div>


				<div class="card report-kpi-grid">
					<div class="card-label">불량률</div>
					<div class="card-value">
						<fmt:formatNumber value="${summary.defectRate}" pattern="0.00" />
						%
					</div>
				</div>
			</section>

			<div class="two-col" style="margin-top: 20px;">
				<div class="card">
					<div class="section-title">
						<h2>일자별 계획 대비 실적</h2>
						<span>${startDate} ~ ${endDate}</span>
					</div>

					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>일자</th>
									<th>계획수량</th>
									<th>지시수량</th>
									<th>투입수량</th>
									<th>양품수량</th>
									<th>불량수량</th>
									<th>달성률</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty dailyList}">
										<c:forEach var="d" items="${dailyList}">
											<tr>
												<td>${d.workDay}</td>
												<td><fmt:formatNumber value="${d.planQty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${d.orderQty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${d.inputQty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${d.goodQty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${d.defectQty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${d.achievementRate}"
														pattern="0.00" />%</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="7">조회 결과가 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>

				<div class="card">
					<div class="section-title">
						<h2>불량 사유별 집계</h2>
					</div>

					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>불량사유</th>
									<th>건수</th>
									<th>불량수량</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty defectList}">
										<c:forEach var="d" items="${defectList}">
											<tr>
												<td>${d.defectReason}</td>
												<td><fmt:formatNumber value="${d.defectCount}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${d.totalDefectQty}"
														pattern="#,##0" /></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="3">조회 결과가 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="card" style="margin-top: 20px;">
				<div class="section-title">
					<h2>품목별 계획 / 실적 현황</h2>
				</div>

				<div class="table-wrap">
					<table>
						<thead>
							<tr>
								<th>품목코드</th>
								<th>품목명</th>
								<th>계획수량</th>
								<th>지시수량</th>
								<th>양품수량</th>
								<th>불량수량</th>
								<th>달성률</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty itemList}">
									<c:forEach var="i" items="${itemList}">
										<tr>
											<td>${i.itemCode}</td>
											<td>${i.itemName}</td>
											<td><fmt:formatNumber value="${i.planQty}"
													pattern="#,##0" /></td>
											<td><fmt:formatNumber value="${i.orderQty}"
													pattern="#,##0" /></td>
											<td><fmt:formatNumber value="${i.goodQty}"
													pattern="#,##0" /></td>
											<td><fmt:formatNumber value="${i.defectQty}"
													pattern="#,##0" /></td>
											<td><fmt:formatNumber value="${i.achievementRate}"
													pattern="0.00" />%</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="7">조회 결과가 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</main>
	</div>
</body>
</html>