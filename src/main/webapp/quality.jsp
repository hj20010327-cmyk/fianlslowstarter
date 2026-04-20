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
<title>AUTO MES | 품질관리</title>

<script src="./asset/js/common.js" defer></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />

<style>
/* =========================
   [수정] 전체적으로 조금 작게
========================= */
.content {
	font-size: 14px;
}

.page-head h1 {
	font-size: 22px;
}

.page-head p {
	font-size: 13px;
}

.section-title h2 {
	font-size: 18px;
}

.btn {
	padding: 8px 14px;
	font-size: 13px;
}

.input, .select, .textarea {
	font-size: 13px;
	padding: 10px 12px;
}

table th, table td {
	font-size: 13px;
	padding: 12px 10px;
}

.pagination {
	display: flex;
	justify-content: center;
	align-items: center;
	margin-top: 20px;
	margin-bottom: 8px;
	gap: 6px;
}

.pagination a {
	padding: 8px 12px;
	border: 1px solid #dee2e6;
	text-decoration: none;
	color: #495057;
	border-radius: 5px;
	font-size: 13px;
	transition: all 0.2s;
}

.pagination a.active {
	background-color: #0d6efd;
	color: white;
	border-color: #0d6efd;
	font-weight: bold;
}

.status-pass {
	color: green;
	font-weight: bold;
}

.status-fail {
	color: red;
	font-weight: bold;
}

.status-re {
	color: orange;
	font-weight: bold;
}

.search-inline-wrap {
	display: flex;
	align-items: flex-end;
	gap: 10px;
	flex-wrap: nowrap;
}

.search-inline-item {
	flex: 1;
	min-width: 0;
}

.search-inline-item.date-area {
	flex: 0 0 165px;
	display: flex;
	flex-direction: column;
}

.search-small-label {
	font-size: 12px;
	color: #666;
	margin-bottom: 6px;
}

.search-inline-btns {
	display: flex;
	align-items: flex-end;
	gap: 8px;
	flex: 0 0 auto;
}

.search-inline-item .input,
.search-inline-item .select,
.search-inline-item.date-area .input {
	width: 100%;
}

.click-row {
	cursor: pointer;
}

.click-row:hover {
	background-color: #f8fbff;
}

/* 유효성 검사 빨간 문구 */
.error-text {
	display: block;
	margin-top: 5px;
	font-size: 11px;
	color: red;
	font-weight: 600;
}

.input-error,
.select-error,
.textarea-error {
	border: 1px solid red !important;
}

/* [수정] 의미 없는 가로 스크롤 제거 */
.table-wrap {
	overflow-x: visible;
}

/* [수정] 모달 스크롤 제거 */
#commonModal {
	overflow: hidden;
}

#commonModal .modal-box {
	width: 660px;
	max-height: none !important;
	overflow: visible !important;
}

#commonModal .modal-body {
	max-height: none !important;
	overflow: visible !important;
	padding-bottom: 0;
}

/* [수정] 전체 모달 틀과 폰트 조금 축소 */
#commonModal .modal-header h3 {
	font-size: 24px;
}

#commonModal .modal-header,
#commonModal .modal-body,
#commonModal .modal-footer {
	font-size: 13px;
}

#commonModal .modal-close {
	font-size: 24px;
}

/* [수정] 품목명이 잘리지 않게 왼쪽 칸 조금 넓게 */
.form-grid {
	display: grid;
	grid-template-columns: minmax(0, 1.2fr) minmax(0, 0.8fr);
	gap: 14px 14px;
	align-items: end;
}

/* [수정] 상태, 담당자명 칸은 너무 넓지 않게 */
.form-group.status-group,
.form-group.user-group {
	max-width: 190px;
}

.form-group label {
	font-size: 13px;
	margin-bottom: 6px;
	display: block;
}

.form-group.item-group input,
.form-group.prod-group select,
.form-group.prod-view-group input {
	width: 100%;
}

.top-action-wrap {
	display: flex;
	gap: 8px;
	align-items: center;
}

th.action-cell,
td.action-cell {
	text-align: center;
	white-space: nowrap;
}
</style>
</head>

<body>
	<header class="header">
		<div class="header-left">
			<a href="./index" class="logo">
				<span class="logo-mark">AM</span>
				<span>AUTO MES</span>
			</a>
			<div class="header-title">자동차 콤프레셔 제조 MES</div>
		</div>

		<script>
			const contextPath = '${pageContext.request.contextPath}';
			const loginUserRole = '${dto.user_role}';
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
					<li class="active"><a href="${pageContext.request.contextPath}/qualityList">품질</a></li>
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
					<h1>품질관리</h1>
					<p>검사 결과 및 품질 상태 정보를 관리합니다.</p>
				</div>

				<div class="page-actions">
					<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
						<button class="btn primary" type="button" onclick="openInsertModal()">신규 검사 등록</button>
					</c:if>
				</div>
			</div>

			<section class="card" style="margin-bottom: 18px">
				<div class="section-title">
					<h2>검색 조건</h2>
				</div>

				<form action="${pageContext.request.contextPath}/qualityList" method="get">
					<input type="hidden" name="page" value="1">

					<div class="search-inline-wrap">
						<div class="search-inline-item">
							<input class="input" type="text" name="qualityCode"
								placeholder="검사 번호 입력" value="${qualityCode}" />
						</div>

						<div class="search-inline-item">
							<select class="select" name="itemName">
								<option value="">품목명 선택</option>
								<c:forEach var="iname" items="${itemNameList}">
									<option value="${iname}" ${itemName == iname ? 'selected' : ''}>${iname}</option>
								</c:forEach>
							</select>
						</div>

						<div class="search-inline-item">
							<select class="select" name="status">
								<option value="" ${empty status ? 'selected' : ''}>전체</option>
								<option value="합격" ${status == '합격' ? 'selected' : ''}>합격</option>
								<option value="불합격" ${status == '불합격' ? 'selected' : ''}>불합격</option>
								<option value="재검" ${status == '재검' ? 'selected' : ''}>재검</option>
							</select>
						</div>

						<div class="search-inline-item date-area">
							<label class="search-small-label">검사일자</label>
							<input class="input date-input" type="date" name="inspectDate"
								value="${inspectDate}" />
						</div>

						<div class="search-inline-btns">
							<button class="btn primary" type="submit">조회</button>
							<a href="${pageContext.request.contextPath}/qualityList?page=1" class="btn">초기화</a>
						</div>
					</div>
				</form>
			</section>

			<section class="panel-grid">
				<div class="card">
					<form id="listForm" action="${pageContext.request.contextPath}/quality/delete" method="post">
						<input type="hidden" id="listAction" name="action" value="" />

						<div class="section-title">
							<h2>품질 목록</h2>

							<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
								<div class="top-action-wrap">
									<button type="button" class="btn primary" onclick="completeSelected()">완료</button>
									<button type="button" class="btn" onclick="deleteSelected()">삭제</button>
								</div>
							</c:if>
						</div>

						<div class="table-wrap">
							<table>
								<thead>
									<tr>
										<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
											<th style="cursor:pointer;" onclick="toggleAllCheckboxes()">선택</th>
										</c:if>
										<th>검사번호</th>
										<th>품목명</th>
										<th>생산명</th>
										<th>검사일자</th>
										<th>등록일</th>
										<th>검사수량</th>
										<th>양품수량</th>
										<th>불량수량</th>
										<th>불량사유</th>
										<th>상태</th>
										<th>담당자명</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="m" items="${list}">
										<tr class="click-row"
											onclick="openEditModal(
											'${m.quality_key}',
											'${m.quality_code}',
											'${m.inspect_date}',
											'${m.inspect_qty}',
											'${m.good_qty}',
											'${m.defect_qty}',
											'${m.defect_reason}',
											'${m.qc_status}',
											'${m.prod_key}',
											'${m.prod_name}',
											'${m.item_name}',
											'${m.user_key}'
											)">

											<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
												<td onclick="event.stopPropagation();">
													<input type="checkbox" name="quality_key" value="${m.quality_key}">
												</td>
											</c:if>

											<td>${m.quality_code}</td>
											<td>${m.item_name}</td>
											<td>${m.prod_name}</td>
											<td><fmt:formatDate value="${m.inspect_date}" pattern="yyyy-MM-dd"/></td>
											<td><fmt:formatDate value="${m.created_at}" pattern="yyyy-MM-dd"/></td>
											<td>${m.inspect_qty}</td>
											<td>${m.good_qty}</td>
											<td>${m.defect_qty}</td>
											<td>${m.defect_reason}</td>
											<td>
												<c:choose>
													<c:when test="${m.qc_status eq '합격'}">
														<span class="status-pass">합격</span>
													</c:when>
													<c:when test="${m.qc_status eq '불합격'}">
														<span class="status-fail">불합격</span>
													</c:when>
													<c:otherwise>
														<span class="status-re">재검</span>
													</c:otherwise>
												</c:choose>
											</td>
											<td>${m.user_name}</td>
										</tr>
									</c:forEach>

									<c:if test="${empty list}">
										<tr>
											<c:choose>
												<c:when test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
													<td colspan="12" style="text-align:center; padding:26px;">데이터가 없습니다.</td>
												</c:when>
												<c:otherwise>
													<td colspan="11" style="text-align:center; padding:26px;">데이터가 없습니다.</td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:if>
								</tbody>
							</table>

							<div class="pagination">
								<c:forEach var="i" begin="1" end="${totalPage}">
									<c:if test="${currentPage == i}">
										<a href="${pageContext.request.contextPath}/qualityList?page=${i}&qualityCode=${qualityCode}&itemName=${itemName}&status=${status}&inspectDate=${inspectDate}"
										   class="active">${i}</a>
									</c:if>
									<c:if test="${currentPage != i}">
										<a href="${pageContext.request.contextPath}/qualityList?page=${i}&qualityCode=${qualityCode}&itemName=${itemName}&status=${status}&inspectDate=${inspectDate}">
											${i}
										</a>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</form>
				</div>
			</section>
		</main>
	</div>

	<div id="commonModal" class="modal">
		<div class="modal-box">
			<form id="qualityForm" action="${pageContext.request.contextPath}/quality/add" method="post">

				<input type="hidden" id="quality_key" name="quality_key" />
				<input type="hidden" id="quality_code" name="quality_code" />
				<input type="hidden" id="prod_key_hidden" name="prod_key" />

				<div class="modal-header">
					<h3 id="modalTitle">품질 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<div class="modal-body">
					<div class="form-grid">
						<div class="form-group">
							<label>검사일자</label>
							<input type="date" class="input" id="inspect_date" name="inspect_date" />
							<small class="error-text" id="inspect_date_error"></small>
						</div>

						<div class="form-group prod-group" id="prod_select_wrap">
							<label>작업지시 코드</label>
							<select class="select" id="prod_key_select">
								<option value="">선택</option>
								<c:forEach var="w" items="${workOrderList}">
									<option value="${w.prod_key}"
											data-itemname="${w.item_name}"
											data-stockqty="${w.stock_qty}"
											data-prodname="${w.prod_name}">
										${w.prod_name}
									</option>
								</c:forEach>
							</select>
							<small class="error-text" id="prod_key_select_error"></small>
						</div>

						<div class="form-group prod-view-group" id="prod_view_wrap" style="display:none;">
							<label>작업지시 코드</label>
							<input type="text" class="input" id="prod_name_view" readonly />
						</div>

						<div class="form-group">
							<label>검사수량</label>
							<input type="number" class="input" id="inspect_qty" name="inspect_qty" placeholder="검사수량 자동입력" readonly />
							<small class="error-text" id="inspect_qty_error"></small>
						</div>

						<div class="form-group">
							<label>양품수량</label>
							<input type="number" class="input" id="good_qty" name="good_qty" placeholder="양품수량 자동계산" readonly />
							<small class="error-text" id="good_qty_error"></small>
						</div>

						<div class="form-group">
							<label>불량수량</label>
							<input type="number" class="input" id="defect_qty" name="defect_qty" placeholder="불량수량 입력" />
							<small class="error-text" id="defect_qty_error"></small>
						</div>

						<div class="form-group status-group">
							<label>상태</label>
							<select class="select" id="qc_status" name="qc_status">
								<option value="">선택</option>
								<option value="합격">합격</option>
								<option value="불합격">불합격</option>
								<option value="재검">재검</option>
							</select>
							<small class="error-text" id="qc_status_error"></small>
						</div>

						<div class="form-group item-group">
							<label>품목명</label>
							<input type="text" class="input" id="item_name_view" name="item_name" />
							<small class="error-text" id="item_name_view_error"></small>
						</div>

						<div class="form-group user-group">
							<label>담당자명</label>
							<select class="select" id="user_key" name="user_key">
								<option value="">선택</option>
								<c:forEach var="u" items="${userList}">
									<option value="${u.user_key}">${u.user_name}</option>
								</c:forEach>
							</select>
							<small class="error-text" id="user_key_error"></small>
						</div>

						<div class="form-group" style="grid-column: span 2;">
							<label>불량사유</label>
							<textarea class="textarea" id="defect_reason" name="defect_reason" placeholder="불량사유 입력"></textarea>
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
		function getTodayDateString() {
			const today = new Date();
			const year = today.getFullYear();
			const month = String(today.getMonth() + 1).padStart(2, "0");
			const day = String(today.getDate()).padStart(2, "0");
			return year + "-" + month + "-" + day;
		}

		function clearValidation() {
			const errorTexts = document.querySelectorAll(".error-text");
			for (let i = 0; i < errorTexts.length; i++) {
				errorTexts[i].innerText = "";
			}

			const inputs = document.querySelectorAll(".input, .select, .textarea");
			for (let i = 0; i < inputs.length; i++) {
				inputs[i].classList.remove("input-error");
				inputs[i].classList.remove("select-error");
				inputs[i].classList.remove("textarea-error");
			}
		}

		function showError(inputId, errorId, message) {
			const input = document.getElementById(inputId);
			const error = document.getElementById(errorId);

			if (input) {
				if (input.tagName === "SELECT") {
					input.classList.add("select-error");
				} else if (input.tagName === "TEXTAREA") {
					input.classList.add("textarea-error");
				} else {
					input.classList.add("input-error");
				}
			}

			if (error) {
				error.innerText = message;
			}
		}

		function validateQualityForm() {
			clearValidation();

			let isValid = true;

			const modalTitle = document.getElementById("modalTitle").innerText;
			const inspectDate = document.getElementById("inspect_date").value.trim();
			const inspectQty = document.getElementById("inspect_qty").value.trim();
			const goodQty = document.getElementById("good_qty").value.trim();
			const defectQty = document.getElementById("defect_qty").value.trim();
			const qcStatus = document.getElementById("qc_status").value.trim();
			const itemName = document.getElementById("item_name_view").value.trim();
			const userKey = document.getElementById("user_key").value.trim();
			const prodKey = document.getElementById("prod_key_hidden").value.trim();
			const prodKeySelect = document.getElementById("prod_key_select").value.trim();

			if (inspectDate === "") {
				showError("inspect_date", "inspect_date_error", "검사일자를 입력해주세요.");
				isValid = false;
			}

			if (modalTitle === "품질 등록" && prodKeySelect === "") {
				showError("prod_key_select", "prod_key_select_error", "작업지시 코드를 선택해주세요.");
				isValid = false;
			}

			if (prodKey === "" && modalTitle === "품질 등록") {
				showError("prod_key_select", "prod_key_select_error", "작업지시 코드를 선택해주세요.");
				isValid = false;
			}

			if (inspectQty === "") {
				showError("inspect_qty", "inspect_qty_error", "검사수량을 확인해주세요.");
				isValid = false;
			}

			if (goodQty === "") {
				showError("good_qty", "good_qty_error", "양품수량을 확인해주세요.");
				isValid = false;
			}

			if (defectQty === "") {
				showError("defect_qty", "defect_qty_error", "불량수량을 입력해주세요.");
				isValid = false;
			}

			if (qcStatus === "") {
				showError("qc_status", "qc_status_error", "상태를 선택해주세요.");
				isValid = false;
			}

			if (itemName === "") {
				showError("item_name_view", "item_name_view_error", "품목명을 입력해주세요.");
				isValid = false;
			}

			if (userKey === "") {
				showError("user_key", "user_key_error", "담당자를 선택해주세요.");
				isValid = false;
			}

			if (inspectQty !== "" && defectQty !== "") {
				const inspect = parseInt(inspectQty, 10);
				const defect = parseInt(defectQty, 10);

				if (defect > inspect) {
					showError("defect_qty", "defect_qty_error", "불량수량은 검사수량보다 클 수 없습니다.");
					isValid = false;
				}
			}

			return isValid;
		}

		function openInsertModal() {
			if (loginUserRole !== "관리자" && loginUserRole !== "슈퍼바이저") {
				alert("등록 권한이 없습니다.");
				return;
			}

			clearValidation();

			document.getElementById("modalTitle").innerText = "품질 등록";
			document.getElementById("qualityForm").action = contextPath + "/quality/add";

			document.getElementById("prod_select_wrap").style.display = "";
			document.getElementById("prod_view_wrap").style.display = "none";

			document.getElementById("quality_key").value = "";
			document.getElementById("quality_code").value = "";
			document.getElementById("inspect_date").value = getTodayDateString();
			document.getElementById("inspect_qty").value = "";
			document.getElementById("good_qty").value = "";
			document.getElementById("defect_qty").value = "";
			document.getElementById("qc_status").value = "";
			document.getElementById("prod_key_select").value = "";
			document.getElementById("prod_key_hidden").value = "";
			document.getElementById("prod_name_view").value = "";
			document.getElementById("item_name_view").value = "";
			document.getElementById("item_name_view").readOnly = true;
			document.getElementById("defect_reason").value = "";
			document.getElementById("user_key").value = "${dto.user_key}";

			document.getElementById("commonModal").classList.add("show");
		}

		function openEditModal(qualityKey, qualityCode, inspectDate, inspectQty, goodQty, defectQty, defectReason, qcStatus, prodKey, prodName, itemName, userKey) {
			if (loginUserRole !== "관리자" && loginUserRole !== "슈퍼바이저") {
				return;
			}

			clearValidation();

			document.getElementById("modalTitle").innerText = "품질 수정";
			document.getElementById("qualityForm").action = contextPath + "/quality/update";

			document.getElementById("prod_select_wrap").style.display = "none";
			document.getElementById("prod_view_wrap").style.display = "";

			document.getElementById("quality_key").value = qualityKey;
			document.getElementById("quality_code").value = qualityCode;
			document.getElementById("inspect_date").value = inspectDate.substring(0, 10);
			document.getElementById("inspect_qty").value = inspectQty;
			document.getElementById("good_qty").value = goodQty;
			document.getElementById("defect_qty").value = defectQty;
			document.getElementById("qc_status").value = qcStatus;
			document.getElementById("prod_key_hidden").value = prodKey;
			document.getElementById("prod_name_view").value = prodName;
			document.getElementById("item_name_view").value = itemName;
			document.getElementById("item_name_view").readOnly = false;
			document.getElementById("user_key").value = userKey;

			if (defectReason == 'null') {
				document.getElementById("defect_reason").value = '';
			} else {
				document.getElementById("defect_reason").value = defectReason;
			}

			document.getElementById("commonModal").classList.add("show");
		}

		function closeModal() {
			clearValidation();
			document.getElementById("commonModal").classList.remove("show");
		}

		function deleteSelected() {
			const checked = document.querySelectorAll('input[name="quality_key"]:checked');

			if (checked.length === 0) {
				alert("삭제할 항목을 선택하세요.");
				return;
			}

			if (confirm("선택한 품질 데이터를 삭제하시겠습니까?")) {
				const form = document.getElementById("listForm");
				document.getElementById("listAction").value = "";
				form.action = contextPath + "/quality/delete";
				form.submit();
			}
		}

		function completeSelected() {
			const checked = document.querySelectorAll('input[name="quality_key"]:checked');

			if (checked.length === 0) {
				alert("완료할 항목을 선택하세요.");
				return;
			}

			if (confirm("선택한 품질 데이터를 완료 처리하시겠습니까?")) {
				const form = document.getElementById("listForm");
				document.getElementById("listAction").value = "completeSelected";
				form.action = contextPath + "/qualityList";
				form.submit();
			}
		}

		function toggleAllCheckboxes() {
			const checkboxes = document.querySelectorAll('input[name="quality_key"]');
			let allChecked = true;

			checkboxes.forEach(function(chk) {
				if (!chk.checked) {
					allChecked = false;
				}
			});

			checkboxes.forEach(function(chk) {
				chk.checked = !allChecked;
			});
		}

		document.addEventListener("DOMContentLoaded", function() {
			const qualityForm = document.getElementById("qualityForm");
			const inspectQty = document.getElementById("inspect_qty");
			const goodQty = document.getElementById("good_qty");
			const defectQty = document.getElementById("defect_qty");
			const prodKeySelect = document.getElementById("prod_key_select");
			const prodKeyHidden = document.getElementById("prod_key_hidden");
			const itemNameView = document.getElementById("item_name_view");
			const commonModal = document.getElementById("commonModal");

			function calcGoodQty() {
				const inspect = parseInt(inspectQty.value || 0);
				const defect = parseInt(defectQty.value || 0);

				if (defect > inspect) {
					defectQty.value = inspect;
					goodQty.value = 0;
					showError("defect_qty", "defect_qty_error", "불량수량은 검사수량보다 클 수 없습니다.");
					return;
				}

				document.getElementById("defect_qty_error").innerText = "";
				defectQty.classList.remove("input-error");
				goodQty.value = inspect - defect;
			}

			if (defectQty) {
				defectQty.addEventListener("input", function() {
					document.getElementById("defect_qty_error").innerText = "";
					defectQty.classList.remove("input-error");
					calcGoodQty();
				});
			}

			if (prodKeySelect) {
				prodKeySelect.addEventListener("change", function() {
					const selected = this.options[this.selectedIndex];
					const stockQty = selected.getAttribute("data-stockqty");
					const itemName = selected.getAttribute("data-itemname");

					prodKeyHidden.value = this.value;

					if (stockQty != null && this.value !== "") {
						inspectQty.value = stockQty;
						defectQty.value = "";
						goodQty.value = stockQty;
					} else {
						inspectQty.value = "";
						defectQty.value = "";
						goodQty.value = "";
					}

					if (itemNameView) {
						itemNameView.value = itemName != null ? itemName : "";
					}

					document.getElementById("prod_key_select_error").innerText = "";
					prodKeySelect.classList.remove("select-error");
					document.getElementById("inspect_qty_error").innerText = "";
					document.getElementById("good_qty_error").innerText = "";
					document.getElementById("item_name_view_error").innerText = "";
					inspectQty.classList.remove("input-error");
					goodQty.classList.remove("input-error");
					itemNameView.classList.remove("input-error");
				});
			}

			if (document.getElementById("inspect_date") && !document.getElementById("inspect_date").value) {
				document.getElementById("inspect_date").value = getTodayDateString();
			}

			if (qualityForm) {
				qualityForm.addEventListener("submit", function(e) {
					if (!validateQualityForm()) {
						e.preventDefault();
					}
				});
			}

			if (commonModal) {
				commonModal.addEventListener("click", function(e) {
					if (e.target === commonModal) {
						e.stopPropagation();
					}
				});
			}
		});

		function logout() {
			location.href = './logout';
		}
	</script>
</body>
</html>