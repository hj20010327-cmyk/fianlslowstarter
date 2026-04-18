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
.pagination {
	display: flex;
	justify-content: center;
	align-items: center;
	margin: 25px 0 10px;
	gap: 8px;
}

.pagination a {
	padding: 10px 15px;
	border: 1px solid #dee2e6;
	text-decoration: none;
	color: #495057;
	border-radius: 5px;
	font-size: 14px;
}

.pagination a.active {
	background: #0d6efd;
	color: #fff;
	border-color: #0d6efd;
	font-weight: bold;
}

.table-wrap table td, .table-wrap table th {
	text-align: center;
}

.clickable-cell {
	cursor: pointer;
}

.clickable-cell:hover {
	background: #f8f9fa;
}

.search-inline-wrap {
	display: flex;
	align-items: flex-end;
	gap: 12px;
	flex-wrap: nowrap;
	margin-bottom: 12px;
}

.search-inline-item {
	flex: 1;
	min-width: 0;
}

.search-inline-btns {
	display: flex;
	align-items: flex-end;
	gap: 10px;
	flex: 0 0 auto;
}

.search-inline-item .input, .search-inline-item .select {
	width: 100%;
}

.field-error {
	margin-top: 6px;
	font-size: 13px;
	color: #dc3545;
	min-height: 18px;
	text-align: left;
}

.input-error {
	border: 1px solid #dc3545 !important;
}

/* ===== 품목코드 커스텀 드롭다운 (항상 아래) ===== */
.code-dropdown {
	position: relative;
	width: 100%;
}

.code-dropdown-input {
	width: 100%;
	height: 46px;
	padding: 0 42px 0 14px;
	border: 1px solid #d8dee8;
	border-radius: 12px;
	font-size: 16px;
	background: #fff;
	cursor: pointer;
	box-sizing: border-box;
}

.code-dropdown-arrow {
	position: absolute;
	top: 50%;
	right: 14px;
	transform: translateY(-50%);
	font-size: 12px;
	color: #111;
	pointer-events: none;
}

.code-dropdown-list {
	display: none;
	position: absolute;
	top: calc(100% + 6px);
	left: 0;
	right: 0;
	max-height: 220px;
	overflow-y: auto;
	background: #fff;
	border: 1px solid #d8dee8;
	border-radius: 12px;
	box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
	z-index: 9999;
}

.code-dropdown.open .code-dropdown-list {
	display: block;
}

.code-dropdown-item {
	padding: 12px 14px;
	cursor: pointer;
	font-size: 15px;
	background: #fff;
}

.code-dropdown-item:hover {
	background: #f5f8ff;
}
</style>
</head>

<body>
	<header class="header">
		<div class="header-left">
			<a href="./index" class="logo"> <span class="logo-mark">AM</span><span>AUTO
					MES</span>
			</a>
			<div class="header-title">자동차 콤프레셔 제조 MES</div>
		</div>

		<script>
        const contextPath = '${pageContext.request.contextPath}';
        const userRole = '${dto.user_role}';
    </script>

		<div class="header-right">
			<div class="header-chip date"></div>
			<div class="header-chip">${dto.user_name}님</div>
			<button class="btn logout-btn" onclick="logout()">로그아웃</button>
		</div>
		<button type="button" class="menu-toggle" id="menuToggle">☰</button>
	</header>

	<div class="layout">
		<!-- ===== 사이드바 (기존 그대로) ===== -->
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
					<li><a href="/slowstarter/workorder">작업지시</a></li>
					<li><a href="/slowstarter/plan">생산계획</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">재고관리</div>
				<ul class="snb-menu">
					<li class="active"><a href="./stock">재고</a></li>
					<li><a href="#">완제품</a></li>
					<li><a href="#">자재</a></li>
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
					<h1>재고관리</h1>
					<p>재고 / 완제품 / 자재 통합 재고를 관리합니다.</p>
				</div>
				<div class="page-actions">
					<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
						<button class="btn primary" type="button"
							onclick="openInsertModal()">신규 재고 등록</button>
					</c:if>
				</div>
			</div>

			<!-- ===== 검색조건 (기존 유지) ===== -->
			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>작업자는 조회만 가능, 관리자/슈퍼바이저만 등록/수정/삭제</span>
				</div>

				<form action="${pageContext.request.contextPath}/stock" method="get">
					<input type="hidden" name="p" value="1">
					<div class="search-inline-wrap">
						<div class="search-inline-item">
							<select class="select" name="itemType">
								<option value="all" ${itemType eq 'all' ? 'selected' : ''}>전체</option>
								<option value="product"
									${itemType eq 'product' ? 'selected' : ''}>완제품</option>
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
					</div>

					<div class="search-inline-wrap">
						<div class="search-inline-item">
							<select class="select" name="itemCodeKeyword">
								<option value="">품목코드 선택</option>
								<c:forEach var="code" items="${itemCodeList}">
									<option value="${code}"
										${itemCodeKeyword eq code ? 'selected' : ''}>${code}</option>
								</c:forEach>
							</select>
						</div>

						<div class="search-inline-item">
							<select class="select" name="itemNameKeyword">
								<option value="">품목명 선택</option>
								<c:forEach var="name" items="${itemNameList}">
									<option value="${name}"
										${itemNameKeyword eq name ? 'selected' : ''}>${name}</option>
								</c:forEach>
							</select>
						</div>

						<div class="search-inline-btns">
							<button class="btn primary" type="submit">조회</button>
							<a href="${pageContext.request.contextPath}/stock" class="btn">초기화</a>
						</div>
					</div>
				</form>
			</section>

			<!-- ===== 목록 (기존 유지) ===== -->
			<section class="panel-grid">
				<div class="card">
					<form id="deleteForm"
						action="${pageContext.request.contextPath}/stock" method="post"
						onsubmit="return validateDelete();">
						<input type="hidden" name="cmd" value="delete">

						<div class="section-title">
							<h2>재고 목록</h2>
							<c:if
								test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
								<button type="submit" class="btn">삭제</button>
							</c:if>
						</div>

						<div class="table-wrap">
							<table>
								<thead>
									<tr>
										<th onclick="toggleAllCheckboxes()" style="cursor: pointer;">선택</th>
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
											value="'${s.stock_key}', '${s.lot}', '${s.current_qty}', '${s.safe_qty}', '${s.item_key}', '${s.item_code}'" />
										<tr>
											<td><input type="checkbox" name="codes"
												value="${s.stock_key}" class="stock-checkbox"
												<c:if test="${not (dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저')}">disabled</c:if>>
											</td>

											<c:choose>
												<c:when
													test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})">${s.item_type}</td>
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})">${s.item_code}</td>
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})">${s.item_name}</td>
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})">${s.lot}</td>
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})">${s.wait_qc}</td>
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})">${s.done_qc}</td>
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})">${s.current_qty}</td>
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})">${s.safe_qty}</td>
													<td class="clickable-cell"
														onclick="openUpdateModal(${params})"><fmt:formatDate
															value="${s.updated_at}" pattern="yyyy-MM-dd HH:mm" /></td>
												</c:when>
												<c:otherwise>
													<td>${s.item_type}</td>
													<td>${s.item_code}</td>
													<td>${s.item_name}</td>
													<td>${s.lot}</td>
													<td>${s.wait_qc}</td>
													<td>${s.done_qc}</td>
													<td>${s.current_qty}</td>
													<td>${s.safe_qty}</td>
													<td><fmt:formatDate value="${s.updated_at}"
															pattern="yyyy-MM-dd HH:mm" /></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>

									<c:if test="${empty list}">
										<tr>
											<td colspan="10" style="padding: 40px; color: #999;">데이터가
												없습니다.</td>
										</tr>
									</c:if>
								</tbody>
							</table>

							<div class="pagination">
								<c:forEach var="i" begin="1"
									end="${totalPage > 0 ? totalPage : 1}">
									<a
										href="${pageContext.request.contextPath}/stock?p=${i}&lotKeyword=${lotKeyword}&itemType=${itemType}&itemCodeKeyword=${itemCodeKeyword}&itemNameKeyword=${itemNameKeyword}"
										class="${currentPage == i ? 'active' : ''}">${i}</a>
								</c:forEach>
							</div>
						</div>
					</form>
				</div>
			</section>
		</main>

		<!-- ===== 모달: 품목코드만 커스텀으로 교체 ===== -->
		<div id="commonModal" class="modal">
			<div class="modal-box">
				<form id="stockForm"
					action="${pageContext.request.contextPath}/stock" method="post"
					onsubmit="return validateStockForm();">
					<input type="hidden" name="cmd" id="modal_cmd" value="insert">
					<input type="hidden" name="stock_key" id="modal_stock_key"
						value="0">

					<div class="modal-header">
						<h3 id="modalTitle">신규 재고 등록</h3>
						<button type="button" class="modal-close" onclick="closeModal()">×</button>
					</div>

					<div class="modal-body">
						<div class="form-grid">
							<div class="form-group" style="grid-column: span 2;">
								<label>품목 선택</label> <select class="select" name="item_key"
									id="modal_item_key">
									<option value="">선택</option>
									<c:forEach var="item" items="${itemList}">
										<option value="${item.item_key}">[${item.item_type}]
											${item.item_code} / ${item.item_name}</option>
									</c:forEach>
								</select>
								<div class="field-error" id="item_key_error"></div>
							</div>

							<!-- 🔥 품목코드 커스텀 드롭다운 -->
							<div class="form-group" style="grid-column: span 2;">
								<label>품목코드</label>
								<div class="code-dropdown" id="codeDropdown">
									<input type="text" id="modal_item_code_select"
										class="code-dropdown-input" placeholder="품목코드 선택" readonly />
									<span class="code-dropdown-arrow">▼</span>
									<div class="code-dropdown-list">
										<c:forEach var="code" items="${itemCodeList}">
											<div class="code-dropdown-item" data-code="${code}">${code}</div>
										</c:forEach>
									</div>
								</div>
								<div class="field-error" id="item_code_select_error"></div>
							</div>

							<!-- 수정 모달에서만 직접입력 -->
							<div class="form-group" style="grid-column: span 2;"
								id="itemCodeInputGroup">
								<label>품목코드 직접입력</label> <input type="text" class="input"
									id="modal_item_code_input" />
								<div class="field-error" id="item_code_input_error"></div>
							</div>

							<div class="form-group" style="grid-column: span 2;">
								<label>LOT 번호</label> <input type="text" class="input"
									name="lot" id="modal_lot" />
								<div class="field-error" id="lot_error"></div>
							</div>

							<div class="form-group" id="currentQtyGroup">
								<label>현재고</label> <input type="number" class="input"
									name="current_qty" id="modal_current_qty" value="0" />
								<div class="field-error" id="current_qty_error"></div>
							</div>

							<div class="form-group" id="safeQtyGroup">
								<label>안전재고</label> <input type="number" class="input"
									name="safe_qty" id="modal_safe_qty" value="0" />
								<div class="field-error" id="safe_qty_error"></div>
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
    function toggleAllCheckboxes(){
        if(!(userRole==='관리자'||userRole==='슈퍼바이저'))return;
        const cbs=document.querySelectorAll('.stock-checkbox');
        const en=Array.from(cbs).filter(cb=>!cb.disabled);
        const all=en.every(cb=>cb.checked);
        en.forEach(cb=>cb.checked=!all);
    }

    function validateDelete(){
        if(!(userRole==='관리자'||userRole==='슈퍼바이저')){ alert("작업자는 삭제 불가"); return false; }
        const cnt=document.querySelectorAll('.stock-checkbox:checked').length;
        if(cnt===0){ alert("선택 필요"); return false; }
        return confirm(cnt+"개 삭제?");
    }

    function clearFieldErrors(){
        ["item_key_error","item_code_select_error","item_code_input_error","lot_error","current_qty_error","safe_qty_error"]
        .forEach(id=>{const el=document.getElementById(id); if(el) el.innerText="";});
    }

    function openInsertModal(){
        if(!(userRole==='관리자'||userRole==='슈퍼바이저'))return;
        document.getElementById("modal_cmd").value="insert";
        document.getElementById("modalTitle").innerText="신규 재고 등록";
        document.getElementById("modal_item_key").value="";
        document.getElementById("modal_item_code_select").value="";
        document.getElementById("modal_item_code_input").value="";
        document.getElementById("modal_lot").value="";
        document.getElementById("currentQtyGroup").style.display="none";
        document.getElementById("safeQtyGroup").style.display="none";
        document.getElementById("itemCodeInputGroup").style.display="none";
        closeCodeDropdown();
        document.getElementById("commonModal").classList.add("show");
    }

    function openUpdateModal(key,lot,currentQty,safeQty,itemKey,itemCode){
        if(!(userRole==='관리자'||userRole==='슈퍼바이저'))return;
        document.getElementById("modal_cmd").value="update";
        document.getElementById("modalTitle").innerText="재고 정보 수정";
        document.getElementById("modal_stock_key").value=key;
        document.getElementById("modal_lot").value=lot;
        document.getElementById("modal_current_qty").value=currentQty;
        document.getElementById("modal_safe_qty").value=safeQty;
        document.getElementById("modal_item_key").value=itemKey;
        document.getElementById("modal_item_code_select").value=itemCode;
        document.getElementById("modal_item_code_input").value=itemCode;
        document.getElementById("currentQtyGroup").style.display="";
        document.getElementById("safeQtyGroup").style.display="";
        document.getElementById("itemCodeInputGroup").style.display="";
        closeCodeDropdown();
        document.getElementById("commonModal").classList.add("show");
    }

    function closeModal(){
        document.getElementById("commonModal").classList.remove("show");
        closeCodeDropdown();
    }

    function validateStockForm(){ return true; }

    // ===== 커스텀 드롭다운 =====
    function closeCodeDropdown(){
        const d=document.getElementById("codeDropdown");
        if(d) d.classList.remove("open");
    }

    document.addEventListener("DOMContentLoaded",function(){
        const dropdown=document.getElementById("codeDropdown");
        const input=document.getElementById("modal_item_code_select");
        const items=dropdown?dropdown.querySelectorAll(".code-dropdown-item"):[];
        if(input){
            input.addEventListener("click",function(e){
                e.stopPropagation();
                dropdown.classList.toggle("open");
            });
        }
        items.forEach(it=>{
            it.addEventListener("click",function(){
                const code=this.dataset.code;
                input.value=code;
                const inp=document.getElementById("modal_item_code_input");
                if(inp) inp.value=code;
                closeCodeDropdown();
            });
        });
        document.addEventListener("click",function(e){
            if(dropdown && !dropdown.contains(e.target)) closeCodeDropdown();
        });
    });

    function logout(){ location.href='./logout'; }
</script>
</body>
</html>