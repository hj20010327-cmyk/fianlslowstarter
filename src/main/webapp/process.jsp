<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>AUTO MES | 공정관리</title>
<script src="./asset/js/common.js" defer></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
</head>

<body>
	<header class="header">
		<div class="header-left">
			<a href="./index.jsp" class="logo"><span class="logo-mark">AM</span><span>AUTO
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
					<li class="active"><a href="./process.jsp">공정</a></li>
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
					<li><a href="./report.html">리포트</a></li>
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
					<h1>공정관리</h1>
					<p>제품 제조 공정 흐름과 표준 작업정보를 관리합니다</p>
				</div>
				<div class="page-actions">
					<button class="btn" type="button">조회</button>
					<button class="btn primary" type="button">신규 등록</button>
				</div>
			</div>
			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>기준 조건을 선택하세요</span>
				</div>
				<div class="search-row">
					<input class="input" type="text" placeholder="코드 또는 번호 입력" /><input
						class="input" type="text" placeholder="명칭 입력" /><select
						class="select">
						<option>전체</option>
						<option>사용</option>
						<option>미사용</option>
					</select>
					<button class="btn primary" type="button">조회</button>
				</div>
			</section>
			<section class="panel-grid">
				<div class="card">
					<div class="section-title">
						<h2>공정관리 목록</h2>
						<span>샘플 데이터</span>
					</div>
					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>공정코드</th>
									<th>공정명</th>
									<th>설비</th>
									<th>표준시간</th>
									<th>상태</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="process" items="${list}">
									<tr>
										<td>${ process. }</td>
										<td>${ process. }</td>
										<td>${ process. }</td>
										<td>${ process. }</td>
										<td>${ process. }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="card">
					<div class="section-title">
						<h2>요약 / 상태</h2>
						<span>오늘 기준</span>
					</div>
					<ul class="summary-list">
						<li>
							<div>
								<strong>병목 공정</strong>
								<p>조립 공정이 평균 3분 지연됩니다.</p>
							</div>
							<span class="badge warn">주의</span>
						</li>
						<li>
							<div>
								<strong>표준시간 갱신</strong>
								<p>검사 공정 표준시간이 수정되었습니다.</p>
							</div>
							<span class="badge ok">반영</span>
						</li>
						<li>
							<div>
								<strong>비사용 공정</strong>
								<p>비사용 공정 1건이 있습니다.</p>
							</div>
							<span class="badge danger">1건</span>
						</li>
					</ul>
				</div>
			</section>
		</main>
	</div>
</body>

</html>