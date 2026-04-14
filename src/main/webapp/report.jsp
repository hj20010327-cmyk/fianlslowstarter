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
					<li><a href="./index.jsp">대시보드</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">기준관리</div>
				<ul class="snb-menu">
					<li><a href="./master.jsp">기준관리</a></li>
					<li><a href="./BOM">BOM</a></li>
					<li><a href="./process.jsp">공정</a></li>
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
					<li><a href="./stock.jsp">재고</a></li>
					<li><a href="./product.jsp">완제품</a></li>
					<li><a href="./item.jsp">자재</a></li>
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
					<li class="active"><a href="./report.html">리포트</a></li>
					<li><a href="./production.html">생산실적</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">시스템</div>
				<ul class="snb-menu">
					<li><a href="./board.jsp">게시판</a></li>
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
					<p>생산, 품질, 재고 데이터를 요약 보고서 형태로 확인합니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn" type="button">기간 설정</button>
					<button class="btn primary" type="button">보고서 출력</button>
				</div>
			</div>
			<section class="kpi-grid">
				<div class="card">
					<div class="card-label">월 생산량</div>
					<div class="card-value">28,400EA</div>
					<div class="card-meta">전월 대비 +6.2%</div>
				</div>
				<div class="card">
					<div class="card-label">월 불량률</div>
					<div class="card-value">2.9%</div>
					<div class="card-meta">전월 대비 -0.4%</div>
				</div>
				<div class="card">
					<div class="card-label">재고 회전율</div>
					<div class="card-value">4.7회</div>
					<div class="card-meta">기준월 2026-03</div>
				</div>
			</section>
			<section class="two-col">
				<div class="card">
					<div class="section-title">
						<h2>주간 생산 요약</h2>
						<span>최근 4주</span>
					</div>
					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>주차</th>
									<th>계획</th>
									<th>실적</th>
									<th>달성률</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1주차</td>
									<td>6,800</td>
									<td>6,520</td>
									<td>96%</td>
								</tr>
								<tr>
									<td>2주차</td>
									<td>6,900</td>
									<td>6,640</td>
									<td>96%</td>
								</tr>
								<tr>
									<td>3주차</td>
									<td>7,000</td>
									<td>6,480</td>
									<td>93%</td>
								</tr>
								<tr>
									<td>4주차</td>
									<td>7,100</td>
									<td>6,760</td>
									<td>95%</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="card">
					<div class="section-title">
						<h2>리포트 바로가기</h2>
						<span>다운로드 준비</span>
					</div>
					<div class="link-list">
						<div class="link-item">
							<span>일일 생산 보고서</span><span class="badge ok">PDF</span>
						</div>
						<div class="link-item">
							<span>월간 품질 분석표</span><span class="badge warn">XLSX</span>
						</div>
						<div class="link-item">
							<span>재고 소진 예측표</span><span class="badge ok">CSV</span>
						</div>
						<div class="link-item">
							<span>라인별 가동률 보고</span><span class="badge ok">PDF</span>
						</div>
					</div>
				</div>
			</section>
		</main>
	</div>
</body>
</html>