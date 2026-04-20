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
<title>AUTO MES | 재고관리</title>

<script src="./asset/js/common.js" defer></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />

<style>
.content { font-size: 14px; }
.page-head h1 { font-size: 22px; }
.page-head p { font-size: 13px; }
.section-title h2 { font-size: 18px; }

.btn { padding: 8px 14px; font-size: 13px; }
.input, .select {
	font-size: 13px;
	padding: 0 10px;
	height: 38px !important;
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
.search-inline-btns {
	display: flex;
	align-items: flex-end;
	gap: 8px;
	flex: 0 0 auto;
}
.search-inline-item .input,
.search-inline-item .select {
	width: 100%;
}

.table-wrap {
	overflow-x: hidden;
}
.table-wrap table {
	width: 100%;
	table-layout: fixed;
}
.table-wrap table td,
.table-wrap table th {
	text-align: center;
	font-size: 13px;
	padding: 12px 8px;
	vertical-align: middle;
	word-break: keep-all;
}
.table-wrap table th:nth-child(1),
.table-wrap table td:nth-child(1) { width: 44px; }
.table-wrap table th:nth-child(2),
.table-wrap table td:nth-child(2) { width: 70px; }
.table-wrap table th:nth-child(3),
.table-wrap table td:nth-child(3) { width: 90px; }
.table-wrap table th:nth-child(4),
.table-wrap table td:nth-child(4) { width: 135px; }
.table-wrap table th:nth-child(5),
.table-wrap table td:nth-child(5) { width: 120px; }
.table-wrap table th:nth-child(6),
.table-wrap table td:nth-child(6),
.table-wrap table th:nth-child(7),
.table-wrap table td:nth-child(7),
.table-wrap table th:nth-child(8),
.table-wrap table td:nth-child(8),
.table-wrap table th:nth-child(9),
.table-wrap table td:nth-child(9) { width: 78px; }
.table-wrap table th:last-child,
.table-wrap table td:last-child { width: 120px; }

.ellipsis-cell {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.clickable-cell { cursor: pointer; }
.clickable-cell:hover { background: #f8f9fa; }

.pagination {
	display: flex;
	justify-content: center;
	align-items: center;
	margin: 20px 0 8px;
	gap: 6px;
}
.pagination a {
	padding: 8px 12px;
	border: 1px solid #dee2e6;
	text-decoration: none;
	color: #495057;
	border-radius: 5px;
	font-size: 13px;
}
.pagination a.active {
	background: #0d6efd;
	color: #fff;
	border-color: #0d6efd;
	font-weight: bold;
}

.field-error {
	margin-top: 6px;
	font-size: 12px;
	color: #dc3545;
	min-height: 18px;
	text-align: left;
}
.input-error,
.select-error {
	border: 1px solid #dc3545 !important;
}

/* 모달 스크롤 최소화 */
#commonModal .modal-box {
	width: 720px;
	max-height: none !important;
	overflow: visible !important;
}
#commonModal .modal-body {
	max-height: none !important;
	overflow: visible !important;
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
			const userRole = '${dto.user_role}';
			const selectedItemType = '${itemType}';
			const selectedItemCodeKeyword = '${itemCodeKeyword}';
			const selectedItemNameKeyword = '${itemNameKeyword}';
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
					<li class="active"><a href="./stock">재고</a></li>
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
					<h1>재고관리</h1>
					<p>재고 / 완제품 / 자재 통합 재고를 관리합니다.</p>
				</div>
				<div class="page-actions">
					<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
						<button class="btn primary" type="button" onclick="openInsertModal()">신규 재고 등록</button>
					</c:if>
				</div>
			</div>

			<section class="card" style="margin-bottom: 18px">
				<div class="section-title">
					<h2>검색 조건</h2>
				</div>

				<form action="${pageContext.request.contextPath}/stock" method="get">
					<input type="hidden" name="p" value="1">

					<div class="search-inline-wrap">
						<div class="search-inline-item">
							<select class="select" name="itemType" id="searchItemType">
								<option value="all" ${itemType eq 'all' ? 'selected' : ''}>전체</option>
								<option value="product" ${itemType eq 'product' ? 'selected' : ''}>완제품</option>
								<option value="item" ${itemType eq 'item' ? 'selected' : ''}>자재</option>
							</select>
						</div>

						<div class="search-inline-item">
							<select class="select" name="lotKeyword">
								<option value="">LOT 번호 선택</option>
								<c:forEach var="lot" items="${lotList}">
									<option value="${lot}" ${lotKeyword eq lot ? 'selected' : ''}>${lot}</option>
								</c:forEach>
							</select>
						</div>

						<div class="search-inline-item">
							<select class="select" name="itemCodeKeyword" id="searchItemCode">
								<option value="">품목코드 선택</option>
							</select>
						</div>

						<div class="search-inline-item">
							<select class="select" name="itemNameKeyword" id="searchItemName">
								<option value="">품목명 선택</option>
							</select>
						</div>

						<div class="search-inline-btns">
							<button class="btn primary" type="submit">조회</button>
							<a href="${pageContext.request.contextPath}/stock" class="btn">초기화</a>
						</div>
					</div>
				</form>
			</section>

			<section class="panel-grid">
				<div class="card">
					<form id="deleteForm" action="${pageContext.request.contextPath}/stock" method="post" onsubmit="return validateDelete();">
						<input type="hidden" name="cmd" value="delete">

						<div class="section-title">
							<h2>재고 목록</h2>
							<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
								<button type="submit" class="btn">삭제</button>
							</c:if>
						</div>

						<div class="table-wrap">
							<table>
								<thead>
									<tr>
										<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
											<th onclick="toggleAllCheckboxes()" style="cursor:pointer;">선택</th>
										</c:if>
										<th>구분</th>
										<th>품목코드</th>
										<th>품목명</th>
										<th>LOT 번호</th>
										<th>검사전 수량</th>
										<th>검사후 수량</th>
										<th>현재고</th>
										<th>안전재고</th>
										<th>최근 업데이트</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="s" items="${list}">
										<c:set var="params"
											value="'${s.stock_key}', '${s.lot}', '${s.current_qty}', '${s.safe_qty}', '${s.item_key}', '${s.item_code}', '${s.updated_at}'" />
										<tr>
											<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
												<td>
													<input type="checkbox" name="codes" value="${s.stock_key}" class="stock-checkbox">
												</td>
											</c:if>

											<c:choose>
												<c:when test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
													<td class="clickable-cell" onclick="openUpdateModal(${params})">${s.item_type}</td>
													<td class="clickable-cell ellipsis-cell" onclick="openUpdateModal(${params})">${s.item_code}</td>
													<td class="clickable-cell ellipsis-cell" onclick="openUpdateModal(${params})">${s.item_name}</td>
													<td class="clickable-cell ellipsis-cell" onclick="openUpdateModal(${params})">${s.lot}</td>
													<td class="clickable-cell" onclick="openUpdateModal(${params})">${s.wait_qc}</td>
													<td class="clickable-cell" onclick="openUpdateModal(${params})">${s.done_qc}</td>
													<td class="clickable-cell" onclick="openUpdateModal(${params})">${s.current_qty}</td>
													<td class="clickable-cell" onclick="openUpdateModal(${params})">${s.safe_qty}</td>
													<td class="clickable-cell ellipsis-cell" onclick="openUpdateModal(${params})">
														<fmt:formatDate value="${s.updated_at}" pattern="yyyy-MM-dd HH:mm" />
													</td>
												</c:when>
												<c:otherwise>
													<td>${s.item_type}</td>
													<td class="ellipsis-cell">${s.item_code}</td>
													<td class="ellipsis-cell">${s.item_name}</td>
													<td class="ellipsis-cell">${s.lot}</td>
													<td>${s.wait_qc}</td>
													<td>${s.done_qc}</td>
													<td>${s.current_qty}</td>
													<td>${s.safe_qty}</td>
													<td class="ellipsis-cell">
														<fmt:formatDate value="${s.updated_at}" pattern="yyyy-MM-dd HH:mm" />
													</td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>

									<c:if test="${empty list}">
										<tr>
											<c:choose>
												<c:when test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
													<td colspan="10" style="padding:30px;color:#999;">데이터가 없습니다.</td>
												</c:when>
												<c:otherwise>
													<td colspan="9" style="padding:30px;color:#999;">데이터가 없습니다.</td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:if>
								</tbody>
							</table>

							<div class="pagination">
								<c:forEach var="i" begin="1" end="${totalPage > 0 ? totalPage : 1}">
									<a href="${pageContext.request.contextPath}/stock?p=${i}&lotKeyword=${lotKeyword}&itemType=${itemType}&itemCodeKeyword=${itemCodeKeyword}&itemNameKeyword=${itemNameKeyword}"
									   class="${currentPage == i ? 'active' : ''}">${i}</a>
								</c:forEach>
							</div>
						</div>
					</form>
				</div>
			</section>
		</main>

		<div id="commonModal" class="modal">
			<div class="modal-box">
				<form id="stockForm" action="${pageContext.request.contextPath}/stock" method="post" onsubmit="return validateStockForm();">
					<input type="hidden" name="cmd" id="modal_cmd" value="insert">
					<input type="hidden" name="stock_key" id="modal_stock_key" value="0">

					<!-- 실제 submit용 hidden -->
					<input type="hidden" name="item_key" id="modal_item_key_hidden">
					<input type="hidden" name="current_qty" id="modal_current_qty_hidden">
					<input type="hidden" name="safe_qty" id="modal_safe_qty_hidden">

					<div class="modal-header">
						<h3 id="modalTitle">신규 재고 등록</h3>
						<button type="button" class="modal-close" onclick="closeModal()">×</button>
					</div>

					<div class="modal-body">
						<!-- 등록 모달 -->
						<div id="insertFormArea">
							<div class="form-grid">
								<div class="form-group" style="grid-column: span 2;">
									<label>품목 선택</label>
									<select class="select" id="modal_item_key_insert">
										<option value="">선택</option>
										<c:forEach var="item" items="${itemList}">
											<option value="${item.item_key}">[${item.item_type}] ${item.item_code} / ${item.item_name}</option>
										</c:forEach>
									</select>
									<div class="field-error" id="item_key_insert_error"></div>
								</div>

								<div class="form-group">
									<label>재고수량</label>
									<input type="number" class="input" id="modal_current_qty_insert" value="0" />
									<div class="field-error" id="current_qty_insert_error"></div>
								</div>

								<div class="form-group">
									<label>안전재고</label>
									<input type="number" class="input" id="modal_safe_qty_insert" value="0" />
									<div class="field-error" id="safe_qty_insert_error"></div>
								</div>

								<div class="form-group" style="grid-column: span 2;">
									<label>최근 업데이트</label>
									<input type="text" class="input" id="modal_updated_at_insert" readonly placeholder="저장 시 자동 반영" />
								</div>
							</div>
						</div>

						<!-- 수정 모달 -->
						<div id="updateFormArea" style="display:none;">
							<div class="form-grid">
								<div class="form-group" style="grid-column: span 2;">
									<label>품목 선택</label>
									<select class="select" id="modal_item_key_update">
										<option value="">선택</option>
										<c:forEach var="item" items="${itemList}">
											<option value="${item.item_key}">[${item.item_type}] ${item.item_code} / ${item.item_name}</option>
										</c:forEach>
									</select>
									<div class="field-error" id="item_key_update_error"></div>
								</div>

								<div class="form-group" style="grid-column: span 2;">
									<label>품목코드</label>
									<input type="text" class="input" id="modal_item_code_update" readonly />
								</div>

								<div class="form-group" style="grid-column: span 2;">
									<label>LOT 번호</label>
									<input type="text" class="input" id="modal_lot_update" readonly />
								</div>

								<div class="form-group">
									<label>현재고</label>
									<input type="number" class="input" id="modal_current_qty_update" value="0" />
									<div class="field-error" id="current_qty_update_error"></div>
								</div>

								<div class="form-group">
									<label>안전재고</label>
									<input type="number" class="input" id="modal_safe_qty_update" value="0" />
									<div class="field-error" id="safe_qty_update_error"></div>
								</div>

								<div class="form-group" style="grid-column: span 2;">
									<label>최근 업데이트</label>
									<input type="text" class="input" id="modal_updated_at_update" readonly />
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
	</div>

	<script>
		const allSearchItems = [
			<c:forEach var="item" items="${itemList}" varStatus="st">
			{
				itemKey: "${item.item_key}",
				itemCode: "${item.item_code}",
				itemName: "${item.item_name}",
				itemType: "${item.item_type}"
			}<c:if test="${!st.last}">,</c:if>
			</c:forEach>
		];

		function filterItemsByType(typeValue) {
			if (typeValue === "product") {
				return allSearchItems.filter(item => item.itemType === "완제품");
			} else if (typeValue === "item") {
				return allSearchItems.filter(item => item.itemType === "자재");
			}
			return allSearchItems;
		}

		function fillSearchCodeAndNameOptions() {
			const typeValue = document.getElementById("searchItemType").value;
			const codeSelect = document.getElementById("searchItemCode");
			const nameSelect = document.getElementById("searchItemName");

			const filtered = filterItemsByType(typeValue);

			const codeMap = new Map();
			const nameMap = new Map();

			filtered.forEach(item => {
				if (!codeMap.has(item.itemCode)) codeMap.set(item.itemCode, item.itemCode);
				if (!nameMap.has(item.itemName)) nameMap.set(item.itemName, item.itemName);
			});

			codeSelect.innerHTML = '<option value="">품목코드 선택</option>';
			nameSelect.innerHTML = '<option value="">품목명 선택</option>';

			codeMap.forEach((value) => {
				const option = document.createElement("option");
				option.value = value;
				option.textContent = value;
				if (value === selectedItemCodeKeyword) option.selected = true;
				codeSelect.appendChild(option);
			});

			nameMap.forEach((value) => {
				const option = document.createElement("option");
				option.value = value;
				option.textContent = value;
				if (value === selectedItemNameKeyword) option.selected = true;
				nameSelect.appendChild(option);
			});
		}

		function openInsertModal() {
			document.getElementById("modalTitle").innerText = "신규 재고 등록";
			document.getElementById("modal_cmd").value = "insert";
			document.getElementById("modal_stock_key").value = "0";

			document.getElementById("insertFormArea").style.display = "";
			document.getElementById("updateFormArea").style.display = "none";

			document.getElementById("modal_item_key_insert").value = "";
			document.getElementById("modal_current_qty_insert").value = "0";
			document.getElementById("modal_safe_qty_insert").value = "0";
			document.getElementById("modal_updated_at_insert").value = "저장 시 자동 반영";

			clearErrors();
			document.getElementById("commonModal").classList.add("show");
		}

		function openUpdateModal(stockKey, lot, currentQty, safeQty, itemKey, itemCode, updatedAt) {
			document.getElementById("modalTitle").innerText = "재고 정보 수정";
			document.getElementById("modal_cmd").value = "update";
			document.getElementById("modal_stock_key").value = stockKey;

			document.getElementById("insertFormArea").style.display = "none";
			document.getElementById("updateFormArea").style.display = "";

			document.getElementById("modal_item_key_update").value = itemKey;
			document.getElementById("modal_item_code_update").value = itemCode;
			document.getElementById("modal_lot_update").value = lot;
			document.getElementById("modal_current_qty_update").value = currentQty;
			document.getElementById("modal_safe_qty_update").value = safeQty;

			if (updatedAt && updatedAt !== "null") {
				document.getElementById("modal_updated_at_update").value = updatedAt;
			} else {
				document.getElementById("modal_updated_at_update").value = "저장 시 자동 갱신";
			}

			clearErrors();
			document.getElementById("commonModal").classList.add("show");
		}

		function closeModal() {
			document.getElementById("commonModal").classList.remove("show");
		}

		function clearErrors() {
			document.getElementById("item_key_insert_error").innerText = "";
			document.getElementById("current_qty_insert_error").innerText = "";
			document.getElementById("safe_qty_insert_error").innerText = "";

			document.getElementById("modal_item_key_insert").classList.remove("select-error");
			document.getElementById("modal_current_qty_insert").classList.remove("input-error");
			document.getElementById("modal_safe_qty_insert").classList.remove("input-error");

			document.getElementById("item_key_update_error").innerText = "";
			document.getElementById("current_qty_update_error").innerText = "";
			document.getElementById("safe_qty_update_error").innerText = "";

			document.getElementById("modal_item_key_update").classList.remove("select-error");
			document.getElementById("modal_current_qty_update").classList.remove("input-error");
			document.getElementById("modal_safe_qty_update").classList.remove("input-error");
		}

		function validateStockForm() {
			clearErrors();

			let valid = true;
			const cmd = document.getElementById("modal_cmd").value;

			if (cmd === "insert") {
				const itemKey = document.getElementById("modal_item_key_insert").value;
				const currentQty = document.getElementById("modal_current_qty_insert").value;
				const safeQty = document.getElementById("modal_safe_qty_insert").value;

				document.getElementById("modal_item_key_hidden").value = itemKey;
				document.getElementById("modal_current_qty_hidden").value = currentQty;
				document.getElementById("modal_safe_qty_hidden").value = safeQty;

				if (!itemKey) {
					document.getElementById("item_key_insert_error").innerText = "품목을 선택해주세요.";
					document.getElementById("modal_item_key_insert").classList.add("select-error");
					valid = false;
				}

				if (currentQty === "" || parseInt(currentQty) < 0) {
					document.getElementById("current_qty_insert_error").innerText = "재고수량은 0 이상 입력해주세요.";
					document.getElementById("modal_current_qty_insert").classList.add("input-error");
					valid = false;
				}

				if (safeQty === "" || parseInt(safeQty) < 0) {
					document.getElementById("safe_qty_insert_error").innerText = "안전재고는 0 이상 입력해주세요.";
					document.getElementById("modal_safe_qty_insert").classList.add("input-error");
					valid = false;
				}
			} else {
				const itemKey = document.getElementById("modal_item_key_update").value;
				const currentQty = document.getElementById("modal_current_qty_update").value;
				const safeQty = document.getElementById("modal_safe_qty_update").value;

				document.getElementById("modal_item_key_hidden").value = itemKey;
				document.getElementById("modal_current_qty_hidden").value = currentQty;
				document.getElementById("modal_safe_qty_hidden").value = safeQty;

				if (!itemKey) {
					document.getElementById("item_key_update_error").innerText = "품목을 선택해주세요.";
					document.getElementById("modal_item_key_update").classList.add("select-error");
					valid = false;
				}

				if (currentQty === "" || parseInt(currentQty) < 0) {
					document.getElementById("current_qty_update_error").innerText = "현재고는 0 이상 입력해주세요.";
					document.getElementById("modal_current_qty_update").classList.add("input-error");
					valid = false;
				}

				if (safeQty === "" || parseInt(safeQty) < 0) {
					document.getElementById("safe_qty_update_error").innerText = "안전재고는 0 이상 입력해주세요.";
					document.getElementById("modal_safe_qty_update").classList.add("input-error");
					valid = false;
				}
			}

			return valid;
		}

		function validateDelete() {
			const checked = document.querySelectorAll(".stock-checkbox:checked");
			if (checked.length === 0) {
				alert("삭제할 항목을 선택하세요.");
				return false;
			}
			return confirm("선택한 재고를 삭제하시겠습니까?");
		}

		function toggleAllCheckboxes() {
			const checkboxes = document.querySelectorAll(".stock-checkbox");
			let allChecked = true;

			checkboxes.forEach(function(chk) {
				if (!chk.checked) allChecked = false;
			});

			checkboxes.forEach(function(chk) {
				chk.checked = !allChecked;
			});
		}

		function logout() {
			location.href = './logout';
		}

		document.addEventListener("DOMContentLoaded", function() {
			fillSearchCodeAndNameOptions();

			document.getElementById("searchItemType").addEventListener("change", function() {
				fillSearchCodeAndNameOptions();
			});
		});
	</script>
</body>
</html>