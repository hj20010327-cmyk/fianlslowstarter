<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="ko">
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
					<li><a href="./workorder.html">작업지시 <span
							class="menu-badge">4</span></a></li>
					<li class="active"><a href="./plan.html">생산계획 <span
							class="menu-badge">2</span></a></li>
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
					<li><a href="./user.html">사용자관리</a></li>
					<li><a href="./mypage.html">마이페이지</a></li>
				</ul>
			</div>
		</aside>
		<main class="content">
			<div class="page-head">
				<div class="page-head-left">
					<h1>생산계획</h1>
					<p>생산 일정 및 목표 수량을 관리합니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn">조회</button>
					<button class="btn primary">신규 등록</button>
				</div>
			</div>
			<section class="card">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span></span>
				</div>
				<div class="search-row">
					<input class="input" type="date" /> <input class="input"
						type="text" placeholder="제품명 입력" /> <select class="select">
						<option>전체</option>
						<option>진행중</option>
						<option>완료</option>
					</select>
					<button class="btn primary">조회</button>
				</div>
			</section>

			<section class="card">
				<div class="section-title">
					<h2>생산계획 목록</h2>
					<span></span>
				</div>
				<div class="table-wrap">
					<table>
						<tr>
							<th>바꿔야함</th>
							<th>바꿔야함</th>
							<th>바꿔야함</th>
							<th>바꿔야함</th>
							<th>바꿔야함</th>
						</tr>

						<c:forEach var="m" items="${list}">
							<tr>
								<td>${m.priority}</td>
								<td>${m.plan_key}</td>
								<td>${m.plan_code}</td>
								<td>${m.status}</td>
								<td>${m.item_key}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
			</section>
		</main>
</body>
</html>