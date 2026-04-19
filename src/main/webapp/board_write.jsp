<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AUTO MES | 글쓰기</title>
<script src="./asset/js/common.js" defer></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
<style>
.content {
	flex: 1;
	padding: 24px;
	background: #f5f7fb;
	min-height: calc(100vh - 70px);
	box-sizing: border-box;
}

.write-wrap {
	background: #fff;
	border-radius: 14px;
	padding: 26px;
	box-shadow: 0 4px 14px rgba(0, 0, 0, 0.06);
	border: 1px solid #e9edf3;
}

.write-title {
	font-size: 26px;
	font-weight: 700;
	margin-bottom: 20px;
}

.form-row {
	margin-bottom: 18px;
}

.form-row label {
	display: block;
	font-size: 14px;
	font-weight: 700;
	margin-bottom: 8px;
	color: #333;
}

.form-row input, .form-row select, .form-row textarea {
	width: 100%;
	border: 1px solid #dcdfe6;
	border-radius: 8px;
	padding: 12px 14px;
	font-size: 14px;
	box-sizing: border-box;
}

.form-row textarea {
	min-height: 260px;
	resize: vertical;
}

.form-actions {
	display: flex;
	justify-content: flex-end;
	gap: 10px;
}

.btn-line {
	border: 1px solid #dcdfe6;
	background: #fff;
	color: #333;
	padding: 10px 16px;
	border-radius: 8px;
	cursor: pointer;
	font-size: 14px;
	font-weight: 600;
	text-decoration: none;
}

.btn-primary2 {
	border: none;
	background: #03c75a;
	color: #fff;
	padding: 10px 16px;
	border-radius: 8px;
	cursor: pointer;
	font-size: 14px;
	font-weight: 600;
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
					<li class="active"><a href="./board">게시판</a></li>
					<li><a href="./user">사용자관리</a></li>
					<li><a href="./mypage">마이페이지</a></li>
				</ul>
			</div>
		</aside>

		<div class="snb-overlay" id="snbOverlay"></div>
		<main class="content">
			<section class="write-wrap">
				<div class="write-title">글쓰기</div>

				<form method="post"
					action="${pageContext.request.contextPath}/board">
					<input type="hidden" name="action" value="write">

					<div class="form-row">
						<label>구분</label> <select name="board_type">
							<option value="공지">공지</option>
							<option value="건의">건의사항</option>
						</select>
					</div>

					<div class="form-row">
						<label>제목</label> <input type="text" name="title" required>
					</div>

					<div class="form-row">
						<label>내용</label>
						<textarea name="content" required></textarea>
					</div>

					<div class="form-actions">
						<a href="${pageContext.request.contextPath}/board?action=list"
							class="btn-line">취소</a>
						<button type="submit" class="btn-primary2">등록</button>
					</div>
				</form>
			</section>
		</main>
	</div>
</body>
</html>