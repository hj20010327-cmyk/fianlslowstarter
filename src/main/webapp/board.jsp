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
<title>AUTO MES | 게시판</title>
<script src="./asset/js/common.js" defer></script>
<script src="./asset/js/board.js"></script>
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
/* 게시판 메인 영역 */
.content {
	flex: 1;
	padding: 24px;
	background: #f5f7fb;
	min-height: calc(100vh - 70px);
	box-sizing: border-box;
}

/* 상단 헤더 */
.board-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
}

.board-header h1 {
	margin: 0;
	font-size: 28px;
	font-weight: 700;
	color: #222;
}

.board-header p {
	margin-top: 6px;
	color: #666;
	font-size: 14px;
}

/* 글쓰기 버튼 */
.btn-write {
	border: none;
	background: #03c75a;
	color: #fff;
	padding: 11px 18px;
	border-radius: 8px;
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
	transition: 0.2s;
}

.btn-write:hover {
	background: #02b350;
}

/* 게시판 전체 박스 */
.board-wrap {
	background: #fff;
	border-radius: 14px;
	padding: 24px;
	box-shadow: 0 4px 14px rgba(0, 0, 0, 0.06);
	border: 1px solid #e9edf3;
}

/* 상단 검색영역 */
.board-top {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 18px;
	flex-wrap: wrap;
	gap: 12px;
}

.board-info {
	font-size: 14px;
	color: #555;
}

.board-info strong {
	color: #111;
	font-weight: 700;
}

/* 검색 */
.board-search {
	display: flex;
	align-items: center;
	gap: 8px;
}

.board-search select,
.board-search input {
	height: 40px;
	border: 1px solid #dcdfe6;
	border-radius: 8px;
	padding: 0 12px;
	font-size: 14px;
	background: #fff;
	outline: none;
}

.board-search input {
	width: 240px;
}

.board-search button {
	height: 40px;
	padding: 0 16px;
	border: none;
	border-radius: 8px;
	background: #2f80ed;
	color: #fff;
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
}

.board-search button:hover {
	background: #1869d5;
}

/* 테이블 */
.board-table-wrap {
	width: 100%;
	overflow-x: auto;
}

.board-table {
	width: 100%;
	border-collapse: collapse;
	table-layout: fixed;
	border-top: 2px solid #333;
}

.board-table thead th {
	background: #f8fafc;
	color: #333;
	font-size: 14px;
	font-weight: 700;
	padding: 14px 10px;
	border-bottom: 1px solid #e5e7eb;
	text-align: center;
}

.board-table tbody td {
	padding: 15px 10px;
	border-bottom: 1px solid #edf1f5;
	font-size: 14px;
	color: #444;
	text-align: center;
}

.board-table tbody tr:hover {
	background: #f9fbff;
}

.board-table .title {
	text-align: left;
	padding-left: 18px;
}

.board-table .title a {
	color: #222;
	text-decoration: none;
	display: inline-block;
	max-width: 100%;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.board-table .title a:hover {
	color: #03c75a;
	text-decoration: underline;
}

/* 공지 스타일 */
.notice-row {
	background: #fcfcfd;
}

.notice-badge {
	display: inline-block;
	background: #ff6b6b;
	color: white;
	font-size: 12px;
	font-weight: 700;
	padding: 4px 8px;
	border-radius: 20px;
}

/* 상태 텍스트 */
.status-text {
	display: inline-block;
	font-size: 12px;
	font-weight: 700;
	padding: 4px 9px;
	border-radius: 20px;
}

.status-text.fixed {
	background: #fff3cd;
	color: #9a6b00;
}

.status-text.normal {
	background: #e8f1ff;
	color: #246bdb;
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
			<section class="page-header board-header">
				<div>
					<h1>게시판</h1>
					<p>공지사항 및 업무 관련 내용을 확인할 수 있습니다.</p>
				</div>
				<div class="board-actions">
					<button type="button" class="btn-write"
						onclick="location.href='${pageContext.request.contextPath}/board?action=writeForm'">
						글쓰기
					</button>
				</div>
			</section>

			<section class="board-wrap">
				<div class="board-top">
					<div class="board-info">
						<span>전체 <strong>${totalCount}</strong>건</span>
					</div>

					<form action="${pageContext.request.contextPath}/board" method="get" class="board-search">
						<input type="hidden" name="action" value="list" />
						<select name="searchType">
							<option value="title" ${searchType == 'title' ? 'selected' : ''}>제목</option>
							<option value="writer" ${searchType == 'writer' ? 'selected' : ''}>작성자</option>
							<option value="content" ${searchType == 'content' ? 'selected' : ''}>내용</option>
						</select>
						<input type="text" name="keyword" value="${keyword}" placeholder="검색어를 입력하세요" />
						<button type="submit">검색</button>
					</form>
				</div>

				<div class="board-table-wrap">
					<table class="board-table">
						<colgroup>
							<col style="width: 10%">
							<col style="width: 48%">
							<col style="width: 12%">
							<col style="width: 12%">
							<col style="width: 9%">
							<col style="width: 9%">
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>조회</th>
								<th>상태</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty boardList}">
									<c:forEach var="b" items="${boardList}">
										<tr class="${b.board_type == '공지' ? 'notice-row' : ''}">
											<td>
												<c:choose>
													<c:when test="${b.board_type == '공지'}">
														<span class="notice-badge">공지</span>
													</c:when>
													<c:otherwise>
														${b.board_key}
													</c:otherwise>
												</c:choose>
											</td>
											<td class="title">
												<a href="${pageContext.request.contextPath}/board?action=detail&board_key=${b.board_key}">
													${b.title}
												</a>
											</td>
											<td>${b.user_name}</td>
											<td><fmt:formatDate value="${b.created_at}" pattern="yyyy-MM-dd"/></td>
											<td>${b.view_count}</td>
											<td>
												<c:choose>
													<c:when test="${b.board_type == '공지'}">
														<span class="status-text fixed">고정</span>
													</c:when>
													<c:otherwise>
														<span class="status-text normal">일반</span>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="6">등록된 게시글이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>

				<div class="pagination">
					<c:if test="${page > 1}">
						<a href="${pageContext.request.contextPath}/board?action=list&page=${page - 1}&searchType=${searchType}&keyword=${keyword}">&laquo;</a>
					</c:if>

					<c:forEach var="i" begin="1" end="${totalPage}">
						<a href="${pageContext.request.contextPath}/board?action=list&page=${i}&searchType=${searchType}&keyword=${keyword}"
						   class="${page == i ? 'active' : ''}">
							${i}
						</a>
					</c:forEach>

					<c:if test="${page < totalPage}">
						<a href="${pageContext.request.contextPath}/board?action=list&page=${page + 1}&searchType=${searchType}&keyword=${keyword}">&raquo;</a>
					</c:if>
				</div>
			</section>
		</main>
	</div>
</body>
</html>
	

	