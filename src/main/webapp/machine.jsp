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
<title>AUTO MES | 설비정보</title>
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
					<h1>설비정보</h1>
					<p>설비 상태 및 운영 정보를 관리합니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn">삭제</button>
					<button class="btn primary">설비 등록</button>
				</div>
			</div>

			<section class="card">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>설비 조회 조건</span>
				</div>
				<div class="search-row">
					<input class="input" type="text" placeholder="설비명 입력" /> <select
						class="select">
						<option>전체</option>
						<option>가동중</option>
						<option>점검중</option>
						<option>고장</option>
					</select>
					<button class="btn primary">조회</button>
				</div>
			</section>

			<section class="panel-grid">
				<div class="card">
					<div class="section-title">
						<h2>설비 목록</h2>
						<span>현재 상태</span>
					</div>
					<div class="table-wrap">
						<table>
							<tr>
								<th>설비번호</th>
								<th>코드</th>
								<th>이름</th>
								<th>상태</th>
							</tr>

							<c:forEach var="m" items="${list}">
								<tr>
									<td>${m.machineKey}</td>
									<td>${m.machineName}</td>
									<td>${m.machineCode}</td>
									<td>${m.machineStatus}</td>
									<td>${m.processKey}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>

				<div class="card">
					<div class="section-title">
						<h2>설비 상태 요약</h2>
						<span>실시간 기준</span>
					</div>
					<ul class="summary-list">
						<li>
							<div>
								<strong>가동 설비</strong>
								<p>총 12대 중 9대 가동중</p>
							</div> <span class="badge ok">정상</span>
						</li>
						<li>
							<div>
								<strong>점검 필요</strong>
								<p>3대 점검 필요</p>
							</div> <span class="badge warn">주의</span>
						</li>
						<li>
							<div>
								<strong>고장 설비</strong>
								<p>1대 고장 발생</p>
							</div> <span class="badge danger">긴급</span>
						</li>
					</ul>
				</div>
			</section>
		</main>
</body>
</html>