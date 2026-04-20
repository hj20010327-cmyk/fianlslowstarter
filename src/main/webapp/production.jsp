<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AUTO MES | 생산실적</title>
<script src="./asset/js/common.js" defer></script>
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
					<li class="active"><a href="./production">생산실적</a></li>
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
					<h1>생산실적</h1>
					<p>생산 결과와 계획 대비 실적을 조회합니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn" type="button"
						onclick="location.href='${pageContext.request.contextPath}/production'">초기화</button>
				</div>
			</div>

			<c:if test="${not empty errorMsg}">
				<div class="card"
					style="margin-bottom: 20px; color: #d64545; font-weight: 700;">
					${errorMsg}</div>
			</c:if>

			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
				</div>

				<form action="${pageContext.request.contextPath}/production"
					method="get" class="search-row">
					<input class="input" type="date" name="startDate"
						value="${startDate}" /> <input class="input" type="date"
						name="endDate" value="${endDate}" /> <input class="input"
						type="text" name="keyword" value="${keyword}"
						placeholder="품목코드 / 품목명 / 실적코드" />

					<button class="btn primary" type="submit">조회</button>
				</form>
			</section>

			<section class="panel-grid">
				<div class="card">
					<div class="section-title">
						<h2>생산실적 목록</h2>
						<span>${startDate} ~ ${endDate}</span>
					</div>
					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>실적코드</th>
									<th>생산일</th>
									<th>작업지시</th>
									<th>품목명</th>
									<th>작업자</th>
									<th>생산수량</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="p" items="${list}">
									<tr>
										<td>${p.prod_code}</td>
										<td>${p.prod_date}</td>
										<td>${p.work_order_code}</td>
										<td>${p.item_name}</td>
										<td>${p.work_user_name}</td>
										<td>${p.good_qty}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

			</section>
		</main>
	</div>

	<div id="commonModal" class="modal">
		<div class="modal-box">

			<form method="post"
				action="${pageContext.request.contextPath}/productionsave"
				id="productionForm">

				<div class="modal-header">
					<h3 id="modalTitle">생산실적 신규 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<div class="modal-body">
					<div class="form-grid">

						<!-- 작업지시 -->
						<div class="form-group">
							<label>작업지시</label> <select name="work_order_key" class="input"
								id="workOrderSelect" required>
								<option value="">선택하세요</option>
								<c:forEach var="o" items="${optionList}">
									<option value="${o.work_order_key}"
										data-plan-key="${o.plan_key}" data-item-name="${o.item_name}"
										data-work-user-name="${o.work_user_name}"
										data-plan-qty="${o.plan_qty}"
										data-quality-key="${o.quality_key}"
										data-good-qty="${o.good_qty}" data-qc-status="${o.qc_status}">
										${o.work_order_code}</option>
								</c:forEach>
							</select>
						</div>

						<!-- 생산코드 -->
						<div class="form-group">
							<label>생산코드</label> <input type="text" name="prod_code" class="input"
								id="prod_code" required />
						</div>

						<!-- 생산일 -->
						<div class="form-group">
							<label>생산일</label> <input type="date" name="prod_date" class="input"
								id="prod_date" required />
						</div>

						<!-- 품목명 -->
						<div class="form-group">
							<label>품목명</label> <input type="text" id="item_name" class="input" readonly>
						</div>

						<!-- 작업자 -->
						<div class="form-group">
							<label>작업자</label> <input type="text" class="input" id="work_user_name" >
						</div>

						<!-- 계획수량 -->
						<div class="form-group">
							<label>계획수량</label> <input type="text" class="input" id="plan_qty" readonly>
						</div>

						<!-- 양품수량 -->
						<div class="form-group">
							<label>양품수량</label> <input type="text" class="input" id="good_qty">
						</div>

						<!-- 품질상태 -->
						<div class="form-group">
							<label>품질상태</label> <input type="text" class="input" id="qc_status" readonly>
						</div>

					</div>
				</div>

				<div class="modal-footer">
					<button class="btn" onclick="closeModal()" type="button">취소</button>
					<button class="btn primary" type="submit">저장</button>

					<!-- hidden -->
					<input type="hidden" name="cmd" id="cmd" value="insert"> 
					<input type="hidden" name="prod_key" id="prod_key"> 
					<input type="hidden" name="plan_key" id="plan_key"> 
					<input type="hidden" name="quality_key" id="quality_key">
				</div>

			</form>

		</div>
	</div>
	<script src="./asset/js/Production.js"></script>
</body>
</html>