<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>AUTO MES | 완제품 관리</title>


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/page.css" />
<script src="${pageContext.request.contextPath}/asset/js/common.js"
	defer></script>


<style>
.content {
	padding: 24px;
	background-color: #f4f7fa;
	min-height: calc(100vh - 60px);
}

.card {
	background: #fff;
	border-radius: 12px;
	box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
	padding: 24px;
	margin-bottom: 20px;
}

.page-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
}

.header-buttons {
	display: flex;
	gap: 8px;
}

.search-container {
	display: flex;
	align-items: center;
	gap: 16px;
}

.input-field {
	width: 100%;
	padding: 10px 14px;
	border: 1px solid #e2e8f0;
	border-radius: 8px;
	font-size: 14px;
	box-sizing: border-box;
}

.main-grid {
	display: grid;
	grid-template-columns: 7.5fr 2.5fr;
	gap: 20px;
}

.btn-blue {
	background: #3182ce;
	color: #fff;
	border: none;
	padding: 10px 20px;
	border-radius: 8px;
	cursor: pointer;
	font-weight: 600;
}

.btn-white {
	background: #fff;
	border: 1px solid #e2e8f0;
	padding: 10px 20px;
	border-radius: 8px;
	cursor: pointer;
}

.btn-delete-custom {
	padding: 8px 16px;
	background-color: #ffffff;
	color: #64748b;
	border: 1px solid #e2e8f0;
	border-radius: 6px;
	font-size: 13px;
	font-weight: 600;
	cursor: pointer;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th {
	background-color: #f8fafc;
	padding: 12px;
	border-bottom: 1px solid #e2e8f0;
	font-size: 13px;
	text-align: center;
	color: #64748b;
}

td {
	padding: 14px 12px;
	border-bottom: 1px solid #f1f5f9;
	text-align: center;
	font-size: 14px;
	color: #334155;
}

tbody tr:hover {
	background-color: #f8fafc;
	cursor: pointer;
}

.status-badge {
	padding: 4px 10px;
	border-radius: 20px;
	font-size: 12px;
	font-weight: 600;
}

.status-Y {
	background: #f0fff4;
	color: #38a169;
}

.status-N {
	background: #fff5f5;
	color: #e53e3e;
}

.pagination {
	display: flex;
	justify-content: center;
	margin-top: 25px;
	gap: 8px;
}

.pagination a {
	padding: 8px 14px;
	border: 1px solid #dee2e6;
	text-decoration: none;
	color: #495057;
	border-radius: 5px;
}

.pagination a.active {
	background-color: #3182ce;
	color: white;
	border-color: #3182ce;
}

.modal {
	display: none;
}

.modal.show {
	display: flex;
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	justify-content: center;
	align-items: center;
	background: rgba(0, 0, 0, 0.4);
	z-index: 1000;
}

.modal-box {
	background: #ffffff;
	width: 500px;
	border-radius: 16px;
	box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
	overflow: hidden;
}

.modal-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 16px 20px;
	border-bottom: 1px solid #eee;
}

.modal-body {
	padding: 24px;
}

.modal-footer {
	padding: 20px;
	display: flex;
	justify-content: center;
	gap: 12px;
}

.form-group {
	margin-bottom: 15px;
}

.form-group label {
	display: block;
	font-size: 13px;
	font-weight: 600;
	color: #555;
	margin-bottom: 5px;
}

.footer {
	background-color: #0f1a30;
	color: #8fa0bc;
	padding: 30px 0;
	font-size: 13px;
}
</style>


<script>
        function openModal(mode, key='', code='', name='', spec='', unit='', price='', safeQty='', status='Y') {
            const modal = document.getElementById("commonModal");
            const title = document.getElementById("modalTitle");
            const submitBtn = document.getElementById("modalSubmitBtn");


            if(mode === 'edit') {
                title.innerText = "완제품 수정";
                submitBtn.innerText = "수정하기";
                document.getElementById("modal_key").value = key;
                document.getElementById("modal_code").value = code;
                document.getElementById("modal_code").readOnly = true;
                document.getElementById("modal_name").value = name;
                document.getElementById("modal_spec").value = spec;
                document.getElementById("modal_unit").value = unit;
                document.getElementById("modal_price").value = price;
                document.getElementById("modal_safe").value = safeQty;
                document.getElementById("modal_status").value = status;
                submitBtn.onclick = function() { sendData('update'); };
            } else {
                title.innerText = "완제품 등록";
                submitBtn.innerText = "저장하기";
                document.getElementById("modalForm").reset();
                document.getElementById("modal_code").readOnly = false;
                submitBtn.onclick = function() { sendData('insert'); };
            }
            modal.classList.add("show");
        }


        function closeModal() {
            document.getElementById("commonModal").classList.remove("show");
        }


        function sendData(type) {
            const form = document.getElementById("modalForm");
            const action = type === 'insert' ? "/productInsert" : "/productUpdate";
            form.action = "${pageContext.request.contextPath}" + action;
            form.submit();
        }


        function doSearch() {
            const keyword = document.getElementById('searchKeyword').value;
            location.href = "${pageContext.request.contextPath}/productList?keyword=" + encodeURIComponent(keyword);
        }
    </script>
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
		<aside class="snb">
			<div class="snb-section active">
				<div class="snb-title">재고관리</div>
				<ul class="snb-menu">
					<li class="active"><a
						href="${pageContext.request.contextPath}/productList">완제품 관리</a></li>
					<li><a href="${pageContext.request.contextPath}/itemList">자재
							관리</a></li>
				</ul>
			</div>
		</aside>


		<main class="content">
			<div class="page-header">
				<div class="page-header-title">
					<h1>완제품 관리</h1>
					<p>생산 완료된 완제품 목록을 조회하고 관리합니다.</p>
				</div>
				<div class="header-buttons">
					<button type="button" class="btn-blue" onclick="openModal('add')">신규
						등록</button>
				</div>
			</div>


			<div class="card">
				<div class="search-container">
					<input type="text" id="searchKeyword" class="input-field"
						placeholder="품목명 또는 코드 입력" style="flex: 3">
					<button type="button" class="btn-blue" onclick="doSearch()">조회</button>
				</div>
			</div>


			<div class="main-grid">
				<div class="card">
					<table>
						<thead>
							<tr>
								<th>품목코드</th>
								<th>품목명</th>
								<th>규격</th>
								<th>단가</th>
								<th>안전재고</th>
								<th>상태</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${list}">
								<tr
									onclick="openModal('edit', '${p.itemKey}', '${p.itemCode}', '${p.itemName}', '${p.spec}', '${p.unit}', '${p.price}', '${p.safeQty}', '${p.status}')">
									<td style="font-weight: 600;">${p.itemCode}</td>
									<td>${p.itemName}</td>
									<td>${p.spec}</td>
									<td><fmt:formatNumber value="${p.price}" /></td>
									<td>${p.safeQty}</td>
									<td><span class="status-badge status-${p.status}">${p.status == 'Y' ? '사용중' : '중단'}</span></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>


				<div class="card">
					<h3>요약 현황</h3>
					<div style="margin-top: 15px; font-size: 14px; line-height: 2;">
						<div style="display: flex; justify-content: space-between;">
							<span>총 품목 수</span><strong>${list.size()}건</strong>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>


	<div id="commonModal" class="modal">
		<div class="modal-box">
			<div class="modal-header">
				<h3 id="modalTitle">등록/수정</h3>
				<span onclick="closeModal()"
					style="cursor: pointer; font-size: 20px;">&times;</span>
			</div>
			<form id="modalForm" method="post">
				<input type="hidden" name="itemKey" id="modal_key">
				<div class="modal-body">
					<div class="form-group">
						<label>품목코드</label><input type="text" name="itemCode"
							id="modal_code" class="input-field">
					</div>
					<div class="form-group">
						<label>품목명</label><input type="text" name="itemName"
							id="modal_name" class="input-field">
					</div>
					<div class="form-group">
						<label>규격</label><input type="text" name="spec" id="modal_spec"
							class="input-field">
					</div>
					<div class="form-group">
						<label>단위</label><input type="text" name="unit" id="modal_unit"
							class="input-field">
					</div>
					<div class="form-group">
						<label>단가</label><input type="number" name="price"
							id="modal_price" class="input-field">
					</div>
					<div class="form-group">
						<label>안전재고</label><input type="number" name="safeQty"
							id="modal_safe" class="input-field">
					</div>
					<div class="form-group">
						<label>상태</label> <select name="status" id="modal_status"
							class="input-field">
							<option value="Y">사용(Y)</option>
							<option value="N">중단(N)</option>
						</select>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn-white" onclick="closeModal()">취소</button>
					<button type="button" class="btn-blue" id="modalSubmitBtn">저장</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>