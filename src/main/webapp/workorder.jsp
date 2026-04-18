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
@media (max-width: 770px) {
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
					<li><a href="./master">기준관리</a></li>
					<li><a href="./bom">BOM</a></li>
					<li><a href="./process">공정</a></li>
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
					<h1>작업지시</h1>
					<p>생산 작업지시 등록 및 진행상태를 확인합니다.</p>
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
					<span>※ 작업지시 목록은 오늘 이후 작업만 표시됩니다. 이전 작업은 검색을 이용하세요.</span>
				</div>
				<form id="workOrderSearchForm" action="/slowstarter/workorder"
					method="get">
					<div class="search-row">
						<input class="input" type="text" name="workOrderCode" value="${workOrderCode}"
							placeholder="작업지시 코드 입력" /> 
						
						<select class="select" name="itemName">
							<option value="">전체</option>
							<c:forEach var="item" items="${itemList}">
								<option value="${item.item_name}"
									<c:if test="${itemName == item.item_name}">selected</c:if>>
									${item.item_name}
								</option>
							</c:forEach>
						</select>

						<div class="search-item">
							<span class="search-label">작업일</span> <input class="input"
								type="date" name="workDate" value="${workDate}" />
						</div>

						<button class="btn primary" type="submit">조회</button>
						<a href="/slowstarter/workorder?page=1" class="btn">초기화</a>
					</div>
				</form>
			</section>

			<section class="panel-grid">
				<div class="card">
					<form action="/slowstarter/workorder/delete" method="post">
						<div class="section-title">
							<h2>작업지시 목록</h2>
							<c:if
								test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
								<span>작업코드를 클릭하면 수정할 수 있습니다.</span>
								<button type="submit" class="btn">삭제</button>
							</c:if>
						</div>

						<div class="table-wrap">
							<table>
								<tr>
									<th>선택</th>
									<th>작업코드</th>
									<th>계획코드</th>
									<th>제품명</th>
									<th>작업일</th>
									<th>수량</th>
									<th>지시자</th>
									<th>작업자</th>
								</tr>

								<c:forEach var="w" items="${list}">
									<tr>
										<td><input type="checkbox" name="work_order_key"
											value="${w.work_order_key}"></td>
										<td>
											<!--  관리자/슈퍼바이저는 클릭하면 수정모달 열리게 --> <c:if
												test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
												<!-- javascript:void(0) 이거는 아무동작하지말라고 넣음-->
												<a href="javascript:void(0);"
													onclick="openEditModal(
													'${w.work_order_key}',
													'${w.work_order_code}',
													'${w.order_user_name}',
													'${w.work_user_key}',
													'${w.order_qty}',
													'${w.work_date}',
													'${w.plan_key}',
													'${w.plan_code}'
												)">
													${w.work_order_code} </a>
											</c:if> <!--  작업자는 안 열림 --> <c:if
												test="${not (dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저')}">
											${w.work_order_code}
										</c:if>
										</td>


										<td>${w.plan_code}</td>
										<td>${w.item_name}</td>
										<td>${w.work_date}</td>
										<td>${w.order_qty}</td>
										<td>${w.order_user_name}</td>
										<td>${w.work_user_name}</td>
									</tr>
								</c:forEach>
							</table>
							<div class="pagination">
								<c:forEach var="i" begin="1" end="${totalPage}">
		
									<c:if test="${page == i}">
										<a href="workorder?page=${i}&workOrderCode=${workOrderCode}&itemName=${itemName}&workDate=${workDate}" class="active">${i}</a>
									</c:if>
									
									<c:if test="${page != i}">
										<a href="workorder?page=${i}&workOrderCode=${workOrderCode}&itemName=${itemName}&workDate=${workDate}">${i}</a>
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
<!-- 								<strong>진행중 작업</strong> -->
<!-- 								<p>현재 8건의 작업이 진행 중입니다.</p> -->
<!-- 							</div> <span class="badge ok">8건</span> -->
<!-- 						</li> -->
<!-- 						<li> -->
<!-- 							<div> -->
<!-- 								<strong>금일 마감</strong> -->
<!-- 								<p>오늘 납기 작업이 3건 남았습니다.</p> -->
<!-- 							</div> <span class="badge warn">3건</span> -->
<!-- 						</li> -->
<!-- 						<li> -->
<!-- 							<div> -->
<!-- 								<strong>지연 작업</strong> -->
<!-- 								<p>현재 지연 작업은 없습니다.</p> -->
<!-- 							</div> <span class="badge ok">정상</span> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</section>
		</main>
	</div>

	<div id="commonModal" class="modal">
		<div class="modal-box">
			<form id="workOrderForm" action="/slowstarter/workorder/add"
				method="post" onsubmit="return validateWorkOrder()">
				<div class="modal-header">
					<h3 id="modalTitle">작업지시 신규 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<div class="modal-body">
					<input type="hidden" id="work_order_key" name="work_order_key" />

					<!-- 등록용 -->
					<div id="insertFields">
						<div class="form-grid">

							<div class="form-group">
								<label>지시자</label> <input type="text" class="input"
									id="order_user_name" readonly />
							</div>

							<div class="form-group">
								<label>작업자</label> <select class="input"
									id="insert_work_user_key" name="work_user_key">
									<option value="">작업자 선택</option>
									<c:forEach var="u" items="${userList}">
										<option value="${u.work_user_key}">${u.work_user_name}</option>
									</c:forEach>
								</select>
								<span class="error-msg" id="workerError"></span>
							</div>

							<div class="form-group">
								<label>지시 수량</label> <input type="number" class="input"
									id="order_qty" name="order_qty" placeholder="지시 수량 입력" min="1"/>
							</div>

							<div class="form-group">
								<label>품목명</label> <input type="text" class="input"
									id="item_name" readonly />
							</div>

							<div class="form-group">
								<label>작업일</label> <input type="date" class="input"
									id="work_date" name="work_date" />
								<span class="error-msg" id="dateError"></span>
							</div>

							<div class="form-group">
								<label>생산계획</label> <select class="input" id="plan_key"
									name="plan_key">
									<option value="">생산계획 선택</option>
									<c:forEach var="p" items="${planList}">
										<option value="${p.plan_key}" 
										data-qty="${p.plan_qty}"
										data-date="${p.plan_date}" 
										data-item="${p.item_name}">
										${p.plan_code}</option>
									</c:forEach>
								</select>
								<span class="error-msg" id="planError"></span>
							</div>

						</div>
					</div>

					<!-- 수정용  -->
					<div id="updateFields" style="display: none;">
						<div class="form-grid">

							<div class="form-group">
								<label>작업지시 코드</label> <input type="text" class="input"
									id="edit_work_order_code" readonly />
							</div>

							<div class="form-group">
								<label>지시자</label> <input type="text" class="input"
									id="edit_order_user_name" readonly />
							</div>

							<div class="form-group">
								<label>작업자</label> <select class="input" id="edit_work_user_key"
									name="edit_work_user_key">
									<option value="">작업자 선택</option>
									<c:forEach var="u" items="${userList}">
										<option value="${u.work_user_key}">${u.work_user_name}</option>
									</c:forEach>
								</select>
								<span class="error-msg" id="editWorkerError"></span>
							</div>

							<div class="form-group">
								<label>지시 수량</label> <input type="number" class="input"
									id="edit_order_qty" name="edit_order_qty" min="1"/>
									<span class="error-msg" id="editQtyError"></span>
							</div>

							<div class="form-group">
								<label>작업일</label> <input type="date" class="input"
									id="edit_work_date" name="edit_work_date"/>
								<span class="error-msg" id="editDateError"></span>
							</div>

							<div class="form-group">
								<label>계획 코드</label> <input type="text" class="input"
									id="edit_plan_code" readonly />
							</div>

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
			document.getElementById("modalTitle").innerText = "등록";
			document.getElementById("workOrderForm").action = "/slowstarter/workorder/add";

			document.getElementById("insertFields").style.display = "block";
			document.getElementById("updateFields").style.display = "none";

			document.getElementById("work_order_key").value = "";
			document.getElementById("order_user_name").value = "${dto.user_name}";
			document.getElementById("order_qty").value = "";
			document.getElementById("work_date").value = "";
			document.getElementById("plan_key").value = "";
			document.getElementById("item_name").value = "";
			document.getElementById("insert_work_user_key").value = "";

			document.getElementById("commonModal").classList.add("show");
		}

		function openEditModal(key, code, userName, workUserKey, qty, date,
				planKey, planCode) {
			document.querySelectorAll(".error-msg").forEach(e => e.innerText = "");
			document.getElementById("modalTitle").innerText = "수정";
			document.getElementById("workOrderForm").action = "/slowstarter/workorder/update";

			document.getElementById("insertFields").style.display = "none";
			document.getElementById("updateFields").style.display = "block";

			document.getElementById("work_order_key").value = key;

			document.getElementById("edit_work_order_code").value = code;
			document.getElementById("edit_order_user_name").value = userName;
			document.getElementById("edit_order_qty").value = qty;
			document.getElementById("edit_work_date").value = date;
			document.getElementById("edit_plan_code").value = planCode;

			document.getElementById("edit_work_user_key").value = workUserKey;

			document.getElementById("commonModal").classList.add("show");
		}

		function closeModal() {
			document.getElementById("commonModal").classList.remove("show");
		}
		
		document.getElementById("plan_key").addEventListener("change", function() {

			var select = document.getElementById("plan_key");
			var selectedOption = select.options[select.selectedIndex];

			var qty = selectedOption.getAttribute("data-qty");
			var date = selectedOption.getAttribute("data-date");
			var itemName = selectedOption.getAttribute("data-item");

			if (qty) {
				document.getElementById("order_qty").value = qty;
			} else {
				document.getElementById("order_qty").value = "";
			}

			if (date) {
				document.getElementById("work_date").value = date;
			} else {
				document.getElementById("work_date").value = "";
			}

			if (itemName) {
				document.getElementById("item_name").value = itemName;
			} else {
				document.getElementById("item_name").value = "";
			}
		});
		
		function validateWorkOrder() {

		    let isValid = true;

		    document.querySelectorAll(".error-msg").forEach(e => e.innerText = "");

		    let formAction = document.getElementById("workOrderForm").action;

		    // 등록일 때
		    if (formAction.includes("/workorder/add")) {
		        let planKey = document.getElementById("plan_key").value;
		        let workUserKey = document.getElementById("insert_work_user_key").value;
		        let workDate = document.getElementById("work_date").value;

		        if (!planKey) {
		            document.getElementById("planError").innerText = "생산계획을 선택해주세요.";
		            isValid = false;
		        }

		        if (!workUserKey) {
		            document.getElementById("workerError").innerText = "작업자를 선택해주세요.";
		            isValid = false;
		        }

		        if (!workDate) {
		            document.getElementById("dateError").innerText = "작업일을 입력해주세요.";
		            isValid = false;
		        }
		    }

		    // 수정일 때
		    if (formAction.includes("/workorder/update")) {
		        let workUserKey = document.getElementById("edit_work_user_key").value;
		        let orderQty = document.getElementById("edit_order_qty").value;
		        let workDate = document.getElementById("edit_work_date").value;

		        if (!workUserKey) {
		            document.getElementById("editWorkerError").innerText = "작업자를 선택해주세요.";
		            isValid = false;
		        }

		        if (!orderQty || orderQty <= 0) {
		            document.getElementById("editQtyError").innerText = "지시수량은 1 이상 입력해주세요.";
		            isValid = false;
		        }

		        if (!workDate) {
		            document.getElementById("editDateError").innerText = "작업일을 입력해주세요.";
		            isValid = false;
		        }
		    }

		    return isValid;
		}
		
		// 등록 - 생산계획 선택하면 에러 제거
		document.getElementById("plan_key").addEventListener("change", function() {
		    if (this.value) {
		        document.getElementById("planError").innerText = "";
		    }
		});

		// 등록 - 작업자 선택하면 에러 제거
		document.getElementById("insert_work_user_key").addEventListener("change", function() {
		    if (this.value) {
		        document.getElementById("workerError").innerText = "";
		    }
		});

		// 등록 - 작업일 입력하면 에러 제거
		document.getElementById("work_date").addEventListener("input", function() {
		    if (this.value) {
		        document.getElementById("dateError").innerText = "";
		    }
		});

		// 수정 - 작업자 선택하면 에러 제거
		document.getElementById("edit_work_user_key").addEventListener("change", function() {
		    if (this.value) {
		        document.getElementById("editWorkerError").innerText = "";
		    }
		});

		// 수정 - 지시수량 1 이상이면 에러 제거
		document.getElementById("edit_order_qty").addEventListener("input", function() {
		    if (this.value && Number(this.value) > 0) {
		        document.getElementById("editQtyError").innerText = "";
		    }
		});

		// 수정 - 작업일 입력하면 에러 제거
		document.getElementById("edit_work_date").addEventListener("input", function() {
		    if (this.value) {
		        document.getElementById("editDateError").innerText = "";
		    }
		});
		
		
	</script>
</body>
</html>