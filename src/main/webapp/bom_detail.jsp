
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
<title>BOM 상세</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/bom-detail.css" />


<script src="${pageContext.request.contextPath}/asset/js/common.js"
	defer></script>
<script src="${pageContext.request.contextPath}/asset/js/bom_detail.js"
	defer></script>



<style>

/* ===== 2분할 컨테이너 ===== */
.bom-container {
	display: grid;
	grid-template-columns: 320px 1fr;
	gap: 20px;
	height: calc(100vh - 160px);
}

/* LEFT */
.bom-left {
	overflow-y: auto;
}

/* RIGHT */
.bom-right {
	overflow-y: auto;
}

/* ===== 제품 리스트 ===== */
.product-list {
	margin-top: 10px;
	display: flex;
	flex-direction: column;
	gap: 8px;
}

.product-list li {
	padding: 12px 14px;
	border: 1px solid #e2e8f0;
	border-radius: 10px;
	cursor: pointer;
	background: #fff;
	transition: 0.2s;
	font-size: 14px;
}

.product-list li:hover {
	background: #f1f5f9;
	border-color: #4a90e2;
}

.product-list li.active {
	background: #eaf2ff;
	border-color: #4a90e2;
	font-weight: 700;
}

.tree {
	padding: 10px;
}

/* 노드 기본 */
.tree ul {
	list-style: none;
	margin-left: 20px;
	padding-left: 10px;
	border-left: 1px dashed #cbd5e1;
}

.tree li {
	margin: 8px 0;
	position: relative;
	font-size: 14px;
}

/* 노드 버튼 */
.node {
	display: flex;
	align-items: center;
	gap: 8px;
	cursor: pointer;
	padding: 6px 10px;
	border-radius: 8px;
	transition: 0.2s;
}

.node:hover {
	background: #f1f5f9;
}

/* expand icon */
.toggle {
	width: 18px;
	height: 18px;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	border: 1px solid #cbd5e1;
	border-radius: 4px;
	font-size: 12px;
	background: #fff;
}

.node.leaf .toggle {
	visibility: hidden;
}

.node strong {
	color: #1f2937;
}

.qty {
	margin-left: auto;
	font-size: 12px;
	color: #64748b;
}
</style>

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
					<li class="active"><a href="/slowstarter/bom">BOM</a></li>
					<li><a href="/slowstarter/process">공정</a></li>
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
					<li><a href="./user">사용자관리</a></li>
					<li><a href="./mypage">마이페이지</a></li>
				</ul>
			</div>
		</aside>


		<!-- ===== PAGE HEADER ===== -->
		<div class="snb-overlay" id="snbOverlay"></div>
		<main class="content">
			<div class="page-head">
				<div class="page-head-left">
					<h1>BOM관리</h1>
					<p>제품별 자재 구성과 소요량을 관리하는 페이지입니다.</p>
				</div>
			</div>


			<!-- ===== 2분할 시작 ===== -->
			<div class="bom-container">

				<!-- LEFT -->



				<section class="card bom-left">
					<div class="section-title">
						<h2>완제품 목록</h2>
						<span>클릭 시 우측 표시</span>
					</div>

					<ul class="product-list">
						<c:forEach var="p" items="${itemList}">
							<li><a href="/slowstarter/bom/detail?item_key=${p.item_key}">
									${p.item_name} </a></li>
						</c:forEach>
					</ul>

					<div class="bom-area">



						<div id="bomTree"></div>

					</div>
				</section>

				<!-- RIGHT -->
				<section class="card bom-right">
					<div class="section-title">
						<h2>BOM 구조</h2>
						<span id="selectedName">완제품을 선택하세요</span>
					</div>


					<c:forEach var="p" items="${itemList}">
						<div class="bom-block" id="bom-${p.item_key}"
							style="display: none;">

							<!-- 완제품 제목 -->
							<h3 class="product-title">${p.item_name}</h3>

							<!-- 자재 테이블 -->
							<table class="bom-table">
								<thead>
									<tr>
										<th>코드</th>
										<th>이름</th>
										<th>규격</th>
										<th>수량</th>
										<th>단위</th>
									</tr>
								</thead>

								<tbody>

									<c:forEach var="m" items="${material}">

										<c:if test="${m.parent_item_key == p.item_key}">

											<tr>
												<td>${m.item_code}</td>
												<td>${m.item_name}</td>
												<td>${m.spec}</td>
												<td>${m.safe_qty}</td>
												<td>${m.unit}</td>
											</tr>

										</c:if>


									</c:forEach>
									
								

								</tbody>
							</table>

							<br />
						</div>
					</c:forEach>





				</section>

			</div>
			<!-- ===== 2분할 끝 ===== -->
</body>
</html>