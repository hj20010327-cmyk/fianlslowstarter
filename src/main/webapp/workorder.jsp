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
<title>AUTO MES | 작업지시</title>
<script src="./asset/js/common.js" defer></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
</head>
<style>
/*  페이지네이션  */
.pagination {
	display: flex;
	justify-content: center;
	align-items: center;
	margin-top: 25px;
	margin-bottom: 10px;
	gap: 8px;
}

.pagination a {
	padding: 10px 15px;
	border: 1px solid #dee2e6;
	text-decoration: none;
	color: #495057;
	border-radius: 5px;
	font-size: 14px;
	transition: all 0.2s;
}

.pagination a.active {
	background-color: #0d6efd;
	color: white;
	border-color: #0d6efd;
	font-weight: bold;
}
</style>
<body>
	<header class="header">
		<div class="header-left">
			<a href="./index.jsp" class="logo">
				<span class="logo-mark">AM</span>
				<span>AUTO MES</span>
			</a>
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
					<li class="active"><a href="/slowstarter/workorder">작업지시</a></li>
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
					<h1>작업지시</h1>
					<p>생산 작업지시 등록 및 진행상태를 확인합니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn" type="submit" form="workOrderSearchForm">조회</button>
					<button class="btn primary" type="button" onclick="openInsertModal()">신규 등록</button>
				</div>
			</div>

			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>기준 조건을 선택하세요</span>
				</div>
				<form id="workOrderSearchForm" action="/slowstarter/workorder" method="get">
					<div class="search-row">
						<input class="input" type="text" name="workOrderCode" placeholder="작업지시 코드 입력" />
						<input class="input" type="text" name="planKey" placeholder="계획 키 입력" />
						<button class="btn primary" type="submit">조회</button>
					</div>
				</form>
			</section>

			<section class="panel-grid">
				<div class="card">
					<form action="/slowstarter/workorder/delete" method="post">
						<div class="section-title">
							<h2>작업지시 목록</h2>
							<span>작업코드를 클릭하면 수정할 수 있습니다.</span>
							<button type="submit" class="btn">삭제</button>
						</div>

						<div class="table-wrap">
							<table>
								<tr>
									<th>선택</th>
									<th>작업코드</th>
									<th>계획Key</th>
									<th>작업일</th>
									<th>수량</th>
									<th>지시자</th>
								</tr>

								<c:forEach var="w" items="${list}">
									<tr>
										<td><input type="checkbox" name="work_order_key" value="${w.work_order_key}"></td>
										<td>
											<a href="javascript:void(0);"
												onclick="openEditModal(
													'${w.work_order_key}',
													'${w.work_order_code}',
													'${w.order_user_key}',
													'${w.work_user_key}',
													'${w.order_qty}',
													'${w.work_date}',
													'${w.plan_key}'
												)">
												${w.work_order_code}
											</a>
										</td>
										<td>${w.plan_key}</td>
										<td>${w.work_date}</td>
										<td>${w.order_qty}</td>
										<td>${w.order_user_key}</td>
									</tr>
								</c:forEach>
							</table>
							<div class="pagination">
								<a href="workorder?page=1">1</a>
								<a href="workorder?page=2">2</a> 
								<a href="workorder?page=3">3</a> 
								<a href="workorder?page=4">4</a>
								<a href="workorder?page=5">5</a>
							</div>
						</div>
					</form>
				</div>

				<div class="card">
					<div class="section-title">
						<h2>요약 / 상태</h2>
						<span>오늘 기준</span>
					</div>
					<ul class="summary-list">
						<li>
							<div>
								<strong>진행중 작업</strong>
								<p>현재 8건의 작업이 진행 중입니다.</p>
							</div>
							<span class="badge ok">8건</span>
						</li>
						<li>
							<div>
								<strong>금일 마감</strong>
								<p>오늘 납기 작업이 3건 남았습니다.</p>
							</div>
							<span class="badge warn">3건</span>
						</li>
						<li>
							<div>
								<strong>지연 작업</strong>
								<p>현재 지연 작업은 없습니다.</p>
							</div>
							<span class="badge ok">정상</span>
						</li>
					</ul>
				</div>
			</section>
		</main>
	</div>

	<div id="commonModal" class="modal">
		<div class="modal-box">
			<form id="workOrderForm" action="/slowstarter/workorder/add" method="post">
				<div class="modal-header">
					<h3 id="modalTitle">작업지시 신규 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<div class="modal-body">
					<div class="form-grid">
						<input type="hidden" id="work_order_key" name="work_order_key" />

						<div class="form-group">
							<label>작업지시 코드</label>
							<input type="text" class="input" id="work_order_code" name="work_order_code" placeholder="작업지시 코드 입력" />
						</div>

						<div class="form-group">
							<label>지시자</label>
							<input type="number" class="input" id="order_user_key" name="order_user_key" placeholder="지시자 키 입력" />
						</div>

						<div class="form-group">
							<label>작업자</label>
							<input type="number" class="input" id="work_user_key" name="work_user_key" placeholder="작업자 키 입력" />
						</div>

						<div class="form-group">
							<label>지시 수량</label>
							<input type="number" class="input" id="order_qty" name="order_qty" placeholder="지시 수량 입력" />
						</div>

						<div class="form-group">
							<label>작업일</label>
							<input type="date" class="input" id="work_date" name="work_date" />
						</div>

						<div class="form-group">
							<label>계획 키</label>
							<input type="number" class="input" id="plan_key" name="plan_key" placeholder="계획 키 입력" />
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button type="button" class="btn" onclick="closeModal()">취소</button>
					<button type="submit" class="btn primary">저장</button>
				</div>
			</form>
		</div>
	</div>

	<script>
		function openInsertModal() {
			document.getElementById("modalTitle").innerText = "작업지시 신규 등록";
			document.getElementById("workOrderForm").action = "/slowstarter/workorder/add";

			document.getElementById("work_order_key").value = "";
			document.getElementById("work_order_code").value = "";
			document.getElementById("order_user_key").value = "";
			document.getElementById("work_user_key").value = "";
			document.getElementById("order_qty").value = "";
			document.getElementById("work_date").value = "";
			document.getElementById("plan_key").value = "";

			document.getElementById("commonModal").classList.add("show");
		}

		function openEditModal(work_order_key, work_order_code, order_user_key, work_user_key, order_qty, work_date, plan_key) {
			document.getElementById("modalTitle").innerText = "작업지시 수정";
			document.getElementById("workOrderForm").action = "/slowstarter/workorder/update";

			document.getElementById("work_order_key").value = work_order_key;
			document.getElementById("work_order_code").value = work_order_code;
			document.getElementById("order_user_key").value = order_user_key;
			document.getElementById("work_user_key").value = work_user_key;
			document.getElementById("order_qty").value = order_qty;
			document.getElementById("work_date").value = work_date;
			document.getElementById("plan_key").value = plan_key;

			document.getElementById("commonModal").classList.add("show");
		}

		function closeModal() {
			document.getElementById("commonModal").classList.remove("show");
		}
	</script>
</body>
</html>