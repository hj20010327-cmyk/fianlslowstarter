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
	gap: 12px;
	flex-wrap: nowrap;
}

.search-inline-item {
	flex: 1;
	min-width: 0;
}

.search-inline-item.date-area {
	flex: 0 0 180px;
	display: flex;
	flex-direction: column;
}

.search-small-label {
	font-size: 13px;
	color: #666;
	margin-bottom: 6px;
}

.search-inline-btns {
	display: flex;
	align-items: flex-end;
	gap: 10px;
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
					<li><a href="/slowstarter/workorder">작업지시</a></li>
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
					<li><a href="./user">사용자관리</a></li>
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

			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>기준 조건을 선택하세요</span>
				</div>

				<form action="${pageContext.request.contextPath}/qualityList" method="get">
					<input type="hidden" name="page" value="1">

					<div class="search-inline-wrap">
						<div class="search-inline-item">
							<input class="input" type="text" name="qualityCode"
								placeholder="검사 번호 입력"
								value="${qualityCode}" />
						</div>

						<div class="search-inline-item">
							<select class="select" name="status">
								<option value="" ${empty status ? 'selected' : ''}>전체</option>
								<option value="합격" ${status == '합격' ? 'selected' : ''}>합격</option>
								<option value="불합격" ${status == '불합격' ? 'selected' : ''}>불합격</option>
								<option value="재검" ${status == '재검' ? 'selected' : ''}>재검</option>
							</select>
						</div>

						<div class="search-inline-item.date-area">
							<label class="search-small-label">등록일</label>
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
					<form id="deleteForm" action="${pageContext.request.contextPath}/quality/delete" method="post">
						<div class="section-title">
							<h2>품질 목록</h2>
							<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
								<span>목록 클릭 시 수정할 수 있습니다.</span>
								<button type="button" class="btn" onclick="deleteSelected()">삭제</button>
							</c:if>
						</div>

						<div class="table-wrap">
							<table>
								<thead>
									<tr>
										<th
											<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
												style="cursor:pointer;" onclick="toggleAllCheckboxes()"
											</c:if>
										>선택</th>

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

										<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
											<tr class="click-row"
												onclick="openEditModal(
												'${m.quality_key}',
												'${m.quality_code}',
												'${m.inspect_date}',
												'${m.inspect_qty}',
												'${m.defect_reason}',
												'${m.prod_key}',
												'${m.item_key}',
												'${m.user_key}'
												)">
												<td onclick="event.stopPropagation();">
													<input type="checkbox" name="quality_key" value="${m.quality_key}">
												</td>

												<td>${m.quality_code}</td>
												<td>${m.item_name}</td>
												<td>${m.prod_name}</td>
												<td><fmt:formatDate value="${m.inspect_date}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${m.due_date}" pattern="yyyy-MM-dd"/></td>
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
										</c:if>

										<c:if test="${dto.user_role ne '관리자' and dto.user_role ne '슈퍼바이저'}">
											<tr>
												<td></td>
												<td>${m.quality_code}</td>
												<td>${m.item_name}</td>
												<td>${m.prod_name}</td>
												<td><fmt:formatDate value="${m.inspect_date}" pattern="yyyy-MM-dd"/></td>
												<td><fmt:formatDate value="${m.due_date}" pattern="yyyy-MM-dd"/></td>
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
										</c:if>

									</c:forEach>

									<c:if test="${empty list}">
										<tr>
											<td colspan="12" style="text-align: center; padding: 30px;">
												데이터가 없습니다.
											</td>
										</tr>
									</c:if>
								</tbody>
							</table>

							<div class="pagination">
								<c:forEach var="i" begin="1" end="${totalPage}">
									<c:if test="${currentPage == i}">
										<a href="${pageContext.request.contextPath}/qualityList?page=${i}&qualityCode=${qualityCode}&status=${status}&inspectDate=${inspectDate}"
										   class="active">${i}</a>
									</c:if>
									<c:if test="${currentPage != i}">
										<a href="${pageContext.request.contextPath}/qualityList?page=${i}&qualityCode=${qualityCode}&status=${status}&inspectDate=${inspectDate}">
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

				<div class="modal-header">
					<h3 id="modalTitle">품질 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<div class="modal-body">
					<input type="hidden" id="quality_key" name="quality_key" />

					<div class="form-grid">
						<div class="form-group">
							<label>검사번호</label>
							<input type="text" class="input" id="quality_code" name="quality_code" />
						</div>

						<div class="form-group">
							<label>검사일자</label>
							<input type="date" class="input" id="inspect_date" name="inspect_date" />
						</div>

						<div class="form-group">
							<label>검사수량</label>
							<input type="number" class="input" id="inspect_qty" name="inspect_qty" />
						</div>

						<div class="form-group">
							<label>양품수량</label>
							<input type="number" class="input" id="good_qty" name="good_qty" />
						</div>

						<div class="form-group">
							<label>불량수량</label>
							<input type="number" class="input" id="defect_qty" name="defect_qty" />
						</div>

						<div class="form-group">
							<label>검사상태</label>
							<select class="select" id="qc_status" name="qc_status">
								<option value="">선택</option>
								<option value="합격">합격</option>
								<option value="불합격">불합격</option>
								<option value="재검">재검</option>
							</select>
						</div>

						<div class="form-group">
							<label>생산KEY</label>
							<input type="number" class="input" id="prod_key" name="prod_key" />
						</div>

						<div class="form-group">
							<label>담당자명</label>
							<select class="select" id="user_key" name="user_key" required>
								<option value="">선택</option>
								<c:forEach var="u" items="${userList}">
									<option value="${u.user_key}">${u.user_name}</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group" style="grid-column: span 2;">
							<label>불량사유</label>
							<textarea class="textarea" id="defect_reason" name="defect_reason"></textarea>
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
			document.getElementById("modalTitle").innerText = "품질 등록";
			document.getElementById("qualityForm").action = contextPath + "/quality/add";

			document.getElementById("quality_code").readOnly = false;

			document.getElementById("quality_key").value = "";
			document.getElementById("quality_code").value = "";
			document.getElementById("inspect_date").value = "";
			document.getElementById("inspect_qty").value = "";
			document.getElementById("good_qty").value = "";
			document.getElementById("defect_qty").value = "";
			document.getElementById("qc_status").value = "";
			document.getElementById("prod_key").value = "";
			document.getElementById("user_key").value = "";
			document.getElementById("defect_reason").value = "";

			document.getElementById("commonModal").classList.add("show");
		}

		function openEditModal(qualityKey, qualityCode, inspectDate, inspectQty, defectReason, prodKey, itemKey, userKey) {
			document.getElementById("modalTitle").innerText = "품질 수정";
			document.getElementById("qualityForm").action = contextPath + "/quality/update";

			document.getElementById("quality_code").readOnly = true;

			document.getElementById("quality_key").value = qualityKey;
			document.getElementById("quality_code").value = qualityCode;
			document.getElementById("inspect_date").value = inspectDate.substring(0, 10);
			document.getElementById("inspect_qty").value = inspectQty;
			document.getElementById("prod_key").value = prodKey;
			document.getElementById("user_key").value = userKey;

			if (defectReason == 'null') {
				document.getElementById("defect_reason").value = '';
			} else {
				document.getElementById("defect_reason").value = defectReason;
			}

			document.getElementById("commonModal").classList.add("show");
		}

		function closeModal() {
			document.getElementById("commonModal").classList.remove("show");
		}

		function deleteSelected() {
			const checked = document.querySelectorAll('input[name="quality_key"]:checked');

			if (checked.length === 0) {
				alert("삭제할 항목을 선택하세요.");
				return;
			}

			if (confirm("선택한 품질 데이터를 삭제하시겠습니까?")) {
				document.getElementById("deleteForm").submit();
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

		function logout() {
			location.href = './logout';
		}
	</script>
</body>
</html>