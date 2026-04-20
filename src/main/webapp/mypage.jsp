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
<title>AUTO MES | 마이페이지</title>
<script src="./asset/js/common.js" defer></script>
<script src="./asset/js/mypage.js"></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
<link rel="stylesheet" href="./asset/css/pagination.css" />
<style>
.msg-box {
	margin-bottom: 16px;
	padding: 12px 14px;
	border-radius: 8px;
	font-size: 14px;
	font-weight: 500;
}

.msg-success {
	background: #e8f7ee;
	color: #1d7a46;
	border: 1px solid #b9e3c9;
}

.msg-error {
	background: #fff1f0;
	color: #c62828;
	border: 1px solid #f0b8b8;
}

.content {
	flex: 1;
	padding: 24px;
	background: #f5f7fb;
	min-height: calc(100vh - 70px);
	box-sizing: border-box;
}

.page-head {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
}

.page-head h1 {
	margin: 0;
	font-size: 28px;
}

.page-head p {
	margin-top: 6px;
	color: #666;
	font-size: 14px;
}

.card {
	background: #fff;
	border-radius: 14px;
	padding: 24px;
	box-shadow: 0 4px 14px rgba(0, 0, 0, .06);
	border: 1px solid #e9edf3;
	margin-bottom: 20px;
}

.section-title {
	margin-bottom: 18px;
}

.section-title h2 {
	margin: 0;
	font-size: 20px;
}

.form-grid {
	display: grid;
	grid-template-columns: repeat(2, 1fr);
	gap: 18px 20px;
}

.form-row.full {
	grid-column: 1/-1;
}

.form-row label {
	display: block;
	font-size: 14px;
	font-weight: 700;
	margin-bottom: 8px;
	color: #333;
}

.form-row input {
	width: 100%;
	height: 44px;
	border: 1px solid #dcdfe6;
	border-radius: 8px;
	padding: 0 14px;
	font-size: 14px;
	box-sizing: border-box;
}

.form-desc {
	margin-top: 6px;
	font-size: 12px;
	color: #777;
}

.form-actions {
	display: flex;
	justify-content: flex-end;
	margin-top: 20px;
}

.btn-save {
	border: none;
	background: #2f80ed;
	color: #fff;
	padding: 12px 18px;
	border-radius: 8px;
	font-size: 14px;
	font-weight: 700;
	cursor: pointer;
}

.msg-box {
	margin-bottom: 16px;
	padding: 12px 14px;
	border-radius: 8px;
	font-size: 14px;
}

.msg-success {
	background: #e8f7ec;
	color: #1e8e3e;
}

.msg-error {
	background: #fdecec;
	color: #d93025;
}

.work-table {
	width: 100%;
	border-collapse: collapse;
}

.work-table th, .work-table td {
	padding: 12px 10px;
	border-bottom: 1px solid #edf1f5;
	text-align: center;
	font-size: 14px;
}

.work-table th {
	background: #f8fafc;
	font-weight: 700;
}

.work-table td.left {
	text-align: left;
}

.empty-row {
	text-align: center;
	color: #777;
	padding: 18px 0;
}

@media ( max-width : 900px) {
	.form-grid {
		grid-template-columns: 1fr;
	}
}
</style>
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
					<li class="active"><a href="./mypage">마이페이지</a></li>
				</ul>
			</div>
		</aside>

		<div class="snb-overlay" id="snbOverlay"></div>

		<main class="content">
			<div class="page-head">
				<div>
					<h1>마이페이지</h1>
					<p>내 정보 수정과 비밀번호 변경, 내 작업지시를 확인할 수 있습니다.</p>
				</div>
			</div>

			<c:if test="${param.msg == 'success'}">
				<div class="msg-box msg-success">정보가 정상적으로 수정되었습니다.</div>
			</c:if>
			<c:if test="${param.msg == 'wrongPw'}">
				<div class="msg-box msg-error">현재 비밀번호가 올바르지 않습니다.</div>
			</c:if>
			<c:if test="${param.msg == 'pwMismatch'}">
				<div class="msg-box msg-error">새 비밀번호와 비밀번호 확인이 일치하지 않습니다.</div>
			</c:if>
			<c:if test="${param.msg == 'pwShort'}">
				<div class="msg-box msg-error">새 비밀번호는 8자 이상이어야 합니다.</div>
			</c:if>
			<c:if test="${param.msg == 'emptyPw'}">
				<div class="msg-box msg-error">현재 비밀번호를 입력해주세요.</div>
			</c:if>
			<c:if test="${param.msg == 'fail'}">
				<div class="msg-box msg-error">수정 중 오류가 발생했습니다.</div>
			</c:if>

			<div class="card">
				<div class="section-title">
					<h2>내 정보 / 비밀번호 변경</h2>
				</div>

				<form method="post"
					action="${pageContext.request.contextPath}/mypage">
					<div class="form-grid">
						<div class="form-row">
							<label>사번</label> <input type="text" value="${dto.emp_no}"
								readonly />
						</div>

						<div class="form-row">
							<label>권한</label> <input type="text" value="${dto.user_role}"
								readonly />
						</div>

						<div class="form-row">
							<label>아이디</label> <input type="text" value="${dto.user_id}"
								readonly />
						</div>

						<div class="form-row">
							<label>이름</label> <input type="text" name="name"
								value="${dto.user_name}" required />
						</div>

						<div class="form-row">
							<label>이메일</label> <input type="email" name="email"
								value="${dto.user_email}" />
						</div>

						<div class="form-row">
							<label>연락처</label> <input type="text" name="phone"
								value="${dto.user_phone}" />
						</div>

						<div class="form-row full">
							<label>현재 비밀번호</label> <input type="password" name="pw" required />
							<div class="form-desc">정보 수정 또는 비밀번호 변경 시 현재 비밀번호 확인이
								필요합니다.</div>
						</div>

						<div class="form-row">
							<label>새 비밀번호</label> <input type="password" name="npw" />
							<div class="form-desc">비밀번호 변경이 필요 없으면 비워두세요.</div>
						</div>

						<div class="form-row">
							<label>새 비밀번호 확인</label> <input type="password" name="cnpw" />
						</div>
					</div>

					<div class="form-actions">
						<button type="submit" class="btn-save">저장</button>
					</div>
				</form>
			</div>

			<div class="card">
				<div class="section-title">
					<h2>내 작업지시</h2>
				</div>

				<table class="work-table">
					<thead>
						<tr>
							<th>작업지시코드</th>
							<th>계획코드</th>
							<th>품목코드</th>
							<th>품목명</th>
							<th>수량</th>
							<th>작업일</th>
							<th>지시자</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty dto.myWorkOrders}">
								<c:forEach var="w" items="${dto.myWorkOrders}">
									<tr>
										<td>${w.work_order_code}</td>
										<td>${w.plan_code}</td>
										<td>${w.item_code}</td>
										<td class="left">${w.item_name}</td>
										<td>${w.order_qty}</td>
										<td>${w.work_date}</td>
										<td>${w.order_user_name}</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="7" class="empty-row">배정된 작업지시가 없습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
				<div class="pagination">
					<c:if test="${dto.workOrderPage > 1}">
						<a
							href="${pageContext.request.contextPath}/mypage?workPage=${dto.workOrderPage - 1}">
							&laquo; </a>
					</c:if>

					<c:forEach var="i" begin="1" end="${dto.workOrderTotalPage}">
						<a href="${pageContext.request.contextPath}/mypage?workPage=${i}"
							class="${dto.workOrderPage == i ? 'active' : ''}"> ${i} </a>
					</c:forEach>

					<c:if test="${dto.workOrderPage < dto.workOrderTotalPage}">
						<a
							href="${pageContext.request.contextPath}/mypage?workPage=${dto.workOrderPage + 1}">
							&raquo; </a>
					</c:if>
				</div>
			</div>
		</main>
	</div>
</body>
</html>