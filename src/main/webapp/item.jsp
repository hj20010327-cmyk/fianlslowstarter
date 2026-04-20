<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>AUTO MES | 품목관리</title>

<script src="${pageContext.request.contextPath}/asset/js/common.js"
	defer></script>
<script src="${pageContext.request.contextPath}/asset/js/item.js" defer></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/page.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/item.css">
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
					<li class="active"><a href="./item">품목관리</a></li>
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
				<h1>품목관리</h1>
				<button class="btn primary" onclick="openItemModal('insert')">신규
					등록</button>
			</div>


			<!-- 검색 -->
			<form method="get" action="${pageContext.request.contextPath}/item">
				<div class="search-row">
					<input type="text" name="keyword" placeholder="코드/품목명 검색"
						class="input" value="${keyword}"> <select name="item_type"
						class="select">
						<option value="">전체품목</option>
						<option value="완제품" ${itemType=='완제품'?'selected':''}>완제품</option>
						<option value="반제품" ${itemType=='반제품'?'selected':''}>반제품</option>
						<option value="자재" ${itemType=='자재'?'selected':''}>자재</option>
					</select> <select name="status" class="select">
						<option value="">전체</option>
						<option value="Y" ${status=='Y'?'selected':''}>사용</option>
						<option value="N" ${status=='N'?'selected':''}>미사용</option>
					</select>

					<button class="btn primary">조회</button>
				</div>
			</form>

			<!-- 테이블 -->
			<div class="table-wrap">
				<table>
					<thead>
						<tr>
							<th>코드</th>
							<th>품목명</th>
							<th>구분</th>
							<th>규격</th>
							<th>단위</th>
							<th>가격(₩)</th>
							<th>안전재고</th>
							<th>상태</th>
							<th>관리</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="i" items="${itemList}">
							<tr>
								<td>${i.item_code}</td>
								<td>${i.item_name}</td>
								<td>${i.item_type}</td>
								<td>${i.spec}</td>
								<td>${i.unit}</td>
								<td>${i.price}</td>
								<td>${i.safe_qty}</td>

								<td><c:choose>
										<c:when test="${i.status=='Y'}">
											<span class="badge green">사용</span>
										</c:when>
										<c:otherwise>
											<span class="badge red">미사용</span>
										</c:otherwise>
									</c:choose></td>

								<td>
									<button class="btn small"
										onclick="openItemModal('update',
            '${i.item_key}',
            '${i.item_code}',
            '${i.item_name}',
            '${i.item_type}',
            '${i.spec}',
            '${i.unit}',
            '${i.price}',
            '${i.safe_qty}',
            '${i.status}')">
										수정</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="pagination"
					style="margin-top: 20px; display: flex; gap: 8px; justify-content: center; align-items: center;">
					<c:if test="${currentPage > 1}">
						<a class="btn"
							href="${pageContext.request.contextPath}/item?page=${currentPage - 1}&keyword=${keyword}&status=${status}&item_type=${itemType}">
							이전 </a>
					</c:if>

					<c:forEach var="p" begin="1" end="${totalPage}">
						<c:choose>
							<c:when test="${p == currentPage}">
								<span class="btn primary">${p}</span>
							</c:when>
							<c:otherwise>
								<a class="btn"
									href="${pageContext.request.contextPath}/item?page=${p}&keyword=${keyword}&status=${status}&item_type=${itemType}">
									${p} </a>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<c:if test="${currentPage < totalPage}">
						<a class="btn"
							href="${pageContext.request.contextPath}/item?page=${currentPage + 1}&keyword=${keyword}&status=${status}&item_type=${itemType}">
							다음 </a>
					</c:if>
				</div>
			</div>

		</main>
	</div>

	<!-- 모달 -->
	<div id="commonModal" class="modal">
		<div class="modal-box">

			<form method="post"
				action="${pageContext.request.contextPath}/itemsave" id="itemForm">

				<!-- 헤더 -->
				<div class="modal-header">
					<h3 id="modalTitle">품목 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<!-- 바디 -->
				<div class="modal-body">
					<div class="form-grid">


						<!-- 품목코드 -->
						<div class="form-group" style="display:none;">
							<input type="hidden" id="item_code_view"
								class="input" value="자동 생성" readonly /> <input type="hidden"
								name="item_code" id="item_code" />
						</div>

						<!-- 품목명 -->
						<div class="form-group">
							<label>품목명</label> <input type="text" name="item_name"
								id="item_name" class="input" required />
						</div>

						<!-- 구분 -->
						<div class="form-group">
							<label>구분</label> <select name="item_type" id="item_type"
								class="select" required>
								<option value="완제품">완제품</option>
								<option value="반제품">반제품</option>
								<option value="자재">자재</option>
							</select>
						</div>

						<!-- 규격 -->
						<div class="form-group">
							<label>규격</label> <input type="text" name="spec" id="spec"
								class="input" />
						</div>

						<!-- 단위 -->
						<div class="form-group">
							<label>단위</label> <input type="text" name="unit" id="unit"
								class="input" />
						</div>

						<!-- 가격 -->
						<div class="form-group">
							<label>가격</label> <input type="number" name="price" id="price"
								class="input" />
						</div>

						<!-- 안전재고 -->
						<div class="form-group">
							<label>안전재고</label> <input type="number" name="safe_qty"
								id="safe_qty" class="input" />
						</div>

						<!-- 상태 -->
						<div class="form-group">
							<label>상태</label> <select name="status" id="status"
								class="select">
								<option value="Y">사용</option>
								<option value="N">미사용</option>
							</select>
						</div>

					</div>
				</div>

				<!-- 푸터 -->
				<div class="modal-footer">
					<button class="btn" onclick="closeModal()" type="button">취소</button>
					<button class="btn primary" type="submit">저장</button>

					<input type="hidden" name="cmd" id="cmd"> <input
						type="hidden" name="item_key" id="item_key">
				</div>

			</form>

		</div>
	</div>

</body>
</html>