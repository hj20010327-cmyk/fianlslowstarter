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
<title>AUTO MES | 생산계획</title>
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

.search-row {
	display: flex;
	align-items: flex-end;
	gap: 12px;
}

.search-item {
	display: flex;
	flex-direction: column;
}

.search-label {
	font-size: 12px;
	color: #666;
	margin-bottom: 4px;
}

@media ( max-width : 770px) {
	.search-row {
		flex-direction: column;
		align-items: stretch;
	}
}

.error-msg {
	color: red;
	font-size: 12px;
	margin-top: 4px;
}
</style>
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
					<li class="active"><a href="/slowstarter/plan">생산계획</a></li>
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
					<h1>생산계획</h1>
					<p>생산계획 등록 및 진행상태를 확인합니다.</p>
				</div>
				<div class="page-actions">
					<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
						<button class="btn primary" type="button"
							onclick="openInsertModal()">신규 등록</button>
					</c:if>
				</div>
			</div>

			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>※이전 계획은 검색을 이용하세요.</span>
				</div>
				<form id="planSearchForm" action="/slowstarter/plan" method="get">
					<div class="search-row">
						<select class="select" name="item_key">
							<option value="" <c:if test="${empty item_key}">selected</c:if>>품목명 검색</option>
							<c:forEach var="item" items="${itemList}">
								<option value="${item.item_key}"
									<c:if test="${item_key == item.item_key}">selected</c:if>>
									${item.item_name}</option>
							</c:forEach>
						</select> <select class="select" name="status">
							<option value="" <c:if test="${empty status}">selected</c:if>>상태 검색</option>
							<option value="계획" <c:if test="${status == '계획'}">selected</c:if>>계획</option>
							<option value="진행중"
								<c:if test="${status == '진행중'}">selected</c:if>>진행중</option>
							<option value="완료" <c:if test="${status == '완료'}">selected</c:if>>완료</option>
						</select>

						<div class="search-item">
							<span class="search-label">마감일</span> <input class="input"
								type="date" name="dueDate" value="${dueDate}" />
						</div>

						<button class="btn primary" type="submit">조회</button>
						<a href="/slowstarter/plan?page=1" class="btn">초기화</a>
					</div>
				</form>
			</section>

			<section class="panel-grid">
				<div class="card">
					<form action="/slowstarter/plan/delete" method="post">
						<div class="section-title">
							<h2>생산계획 목록</h2>
							<c:if
								test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
								<span>계획코드를 클릭하면 수정할 수 있습니다.</span>
								<button type="submit" class="btn">삭제</button>
							</c:if>
						</div>
						<div class="table-wrap">
							<table>
								<tr>
									<c:if
										test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
										<th>선택</th>
									</c:if>
									<th>계획코드</th>
									<th>제품명</th>
									<th>계획일</th>
									<th>마감일</th>
									<th>계획수량</th>
									<th>등록자</th>
									<th>우선순위</th>
									<th>상태</th>
								</tr>

								<c:forEach var="p" items="${list}">
									<tr>
									
									<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
										<td>
										<input type="checkbox" name="plan_key"
											value="${p.plan_key}">
										</td>
									</c:if>
										<td>
									
											<!--  관리자 /슈퍼바이저 일때 계획명 누르면 수정가능하게 --> <c:if
												test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
												<!-- javascript:void(0) 이거는 아무동작하지말라고 넣음-->
												<a href="javascript:void(0);"
													onclick="openEditModal(
													'${p.plan_key}',
													'${p.plan_code}',
													'${p.item_key}',
													'${p.plan_date}',
													'${p.due_date}',
													'${p.plan_qty}',
													'${p.status}',
													'${p.priority}'
												)">
													${p.plan_code}</a>
											</c:if> <c:if
												test="${not (dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저')}">
											${p.plan_code}
										</c:if>
										</td>
										<td>${p.item_name}</td>
										<td>${p.plan_date}</td>
										<td>${p.due_date}</td>
										<td>${p.plan_qty}</td>
										<td>${p.user_name}</td>
										<td><c:if test="${p.priority == 1}">높음</c:if> <c:if
												test="${p.priority == 2}">보통</c:if> <c:if
												test="${p.priority == 3}">낮음</c:if></td>
										<td>${p.status}</td>

									</tr>
								</c:forEach>
							</table>
							<div class="pagination">
								<c:forEach var="i" begin="1" end="${totalPage}">
									<c:if test="${page == i}">
										<a
											href="plan?page=${i}&item_key=${item_key}&status=${status}&dueDate=${dueDate}"
											class="active">${i}</a>
									</c:if>

									<c:if test="${page != i}">
										<a
											href="plan?page=${i}&item_key=${item_key}&status=${status}&dueDate=${dueDate}">${i}</a>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</form>
				</div>

				<!-- 				<div class="card"> -->
				<!-- 					<div class="section-title"> -->
				<!-- 						<h2>요약 / 상태</h2> -->
				<!-- 						<span>오늘 기준</span> -->
				<!-- 					</div> -->
				<!-- 					<ul class="summary-list"> -->
				<!-- 						<li> -->
				<!-- 							<div> -->
				<!-- 								<strong>진행중 계획</strong> -->
				<!-- 								<p>현재 생산계획 진행상태를 확인합니다.</p> -->
				<!-- 							</div> <span class="badge ok">확인</span> -->
				<!-- 						</li> -->
				<!-- 						<li> -->
				<!-- 							<div> -->
				<!-- 								<strong>금일 마감</strong> -->
				<!-- 								<p>오늘 마감 예정 생산계획을 확인합니다.</p> -->
				<!-- 							</div> <span class="badge warn">확인</span> -->
				<!-- 						</li> -->
				<!-- 						<li> -->
				<!-- 							<div> -->
				<!-- 								<strong>지연 계획</strong> -->
				<!-- 								<p>지연된 생산계획 여부를 확인합니다.</p> -->
				<!-- 							</div> <span class="badge ok">정상</span> -->
				<!-- 						</li> -->
				<!-- 					</ul> -->
				<!-- 				</div> -->
			</section>
		</main>
	</div>

	<div id="commonModal" class="modal">
		<div class="modal-box">
			<form id="planForm" action="/slowstarter/plan/add" method="post"
				onsubmit="return validatePlanForm()">
				<div class="modal-header">
					<h3 id="modalTitle">생산계획 신규 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<div class="modal-body">
					<div class="form-grid">
						<input type="hidden" id="plan_key" name="plan_key" />

						<!-- 		<div class="form-group"> -->
						<!-- 			<label>계획 코드</label> -->
						<!-- 			<input type="text" class="input" id="plan_code" name="plan_code" -->
						<!-- 				placeholder="자동 생성" readonly /> -->
						<!-- 		</div> -->

						<div class="form-group">
							<label>제품명</label> <select class="select" id="item_key"
								name="item_key">
								<option value="">선택</option>
								<c:forEach var="item" items="${itemList}">
									<option value="${item.item_key}">${item.item_name}</option>
								</c:forEach>
							</select> <span class="error-msg" id="itemError"></span>
						</div>

						<div class="form-group">
							<label>계획일</label> <input type="date" class="input"
								id="plan_date" name="plan_date"/>
							 <span class="error-msg" id="planDateError"></span>
						</div>

						<div class="form-group">
							<label>마감일</label> <input type="date" class="input" id="due_date"
								name="due_date"/> <span class="error-msg" id="dueDateError"></span>
						</div>

						<div class="form-group">
							<label>계획 수량</label> <input type="number" class="input"
								id="plan_qty" name="plan_qty" placeholder="계획 수량 입력" /> <span
								class="error-msg" id="qtyError"></span>
						</div>

						<div class="form-group">
							<label>계획 상태</label> 
							<select class="select" id="status"
								name="status">
								<option value="계획">계획</option>
								<option value="진행중">진행중</option>
								<option value="완료">완료</option>
								
							</select>
						</div>

						<div class="form-group">
							<label>등록자</label> <input type="text" class="input"
								id="user_name" value="${dto.user_name}" readonly />
						</div>

						<div class="form-group">
							<label>우선순위</label> <select class="select" id="priority"
								name="priority">
								<option value="">선택</option>
								<option value="1">높음</option>
								<option value="2">보통</option>
								<option value="3">낮음</option>
							</select> <span class="error-msg" id="priorityError"></span>
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
			 document.querySelectorAll(".error-msg").forEach(e => e.innerText = "");
			document.getElementById("modalTitle").innerText = "생산계획 신규 등록";
			document.getElementById("planForm").action = "/slowstarter/plan/add";

			document.getElementById("plan_key").value = "";
			document.getElementById("item_key").value = "";
			document.getElementById("plan_date").value = "";
			document.getElementById("due_date").value = "";
			document.getElementById("plan_qty").value = "";
			document.getElementById("status").value = "계획";
			document.getElementById("priority").value = "";

			document.getElementById("commonModal").classList.add("show");
		}

		function openEditModal(plan_key, plan_code, item_key, plan_date, due_date, plan_qty, status, priority) {
			document.querySelectorAll(".error-msg").forEach(e => e.innerText = "");
			document.getElementById("modalTitle").innerText = "생산계획 수정";
			document.getElementById("planForm").action = "/slowstarter/plan/update";

			document.getElementById("plan_key").value = plan_key;
			document.getElementById("item_key").value = item_key;
			document.getElementById("plan_date").value = plan_date;
			document.getElementById("due_date").value = due_date;
			document.getElementById("plan_qty").value = plan_qty;
			document.getElementById("status").value = status;
			document.getElementById("priority").value = priority;

			document.getElementById("commonModal").classList.add("show");
		}

		function closeModal() {
			document.getElementById("commonModal").classList.remove("show");
		}
		
		function validatePlanForm() {

		    let isValid = true;

		    // 에러 초기화
		    document.querySelectorAll(".error-msg").forEach(e => e.innerText = "");

		    let itemKey = document.getElementById("item_key").value;
		    let planDate = document.getElementById("plan_date").value;
		    let dueDate = document.getElementById("due_date").value;
		    let priority = document.getElementById("priority").value;
		    let planQty = document.getElementById("plan_qty").value;

		    // 제품 선택
		    if (!itemKey) {
		        document.getElementById("itemError").innerText = "제품을 선택해주세요.";
		        isValid = false;
		    }
		    
		    //  계획일 필수
		    if (!planDate) {
		        document.getElementById("planDateError").innerText = "계획일을 입력해주세요.";
		        isValid = false;
		    }

		    //  마감일 필수
		    if (!dueDate) {
		        document.getElementById("dueDateError").innerText = "마감일을 입력해주세요.";
		        isValid = false;
		    }

		    // 마감일 < 계획일
		    if (planDate && dueDate && dueDate < planDate) {
		        document.getElementById("dueDateError").innerText = "마감일은 계획일보다 빠를 수 없습니다.";
		        isValid = false;
		    }

		    // 우선순위
		    if (!priority) {
		        document.getElementById("priorityError").innerText = "우선순위를 선택해주세요.";
		        isValid = false;
		    }
		    
		    // 계획수량 0 방지 
		    if (!planQty || planQty <= 0) {
		        document.getElementById("qtyError").innerText = "수량은 1 이상 입력해주세요.";
		        isValid = false;
		    }
		    return isValid;
		}
		
		window.onload = function() {

		    // 제품
		    document.getElementById("item_key").addEventListener("change", function() {
		        if (this.value) document.getElementById("itemError").innerText = "";
		    });

		    // 날짜
		    document.getElementById("due_date").addEventListener("input", function() {
		        let planDate = document.getElementById("plan_date").value;
		        let dueDate = this.value;
		        if (planDate && dueDate && dueDate >= planDate) {
		            document.getElementById("dueDateError").innerText = "";
		        }
		    });

		    // 우선순위
		    document.getElementById("priority").addEventListener("change", function() {
		        if (this.value) document.getElementById("priorityError").innerText = "";
		    });

		    // 수량
		    document.getElementById("plan_qty").addEventListener("input", function() {
		        if (this.value > 0) document.getElementById("qtyError").innerText = "";
		    });
		};
		
			
		
	</script>
</body>
</html>