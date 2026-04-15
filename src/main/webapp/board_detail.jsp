<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AUTO MES | 게시글 상세</title>
<script src="./asset/js/common.js" defer></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
<link rel="stylesheet" href="./asset/css/comment.css"/>
<style>
.content {
	flex: 1;
	padding: 24px;
	background: #f5f7fb;
	min-height: calc(100vh - 70px);
	box-sizing: border-box;
}

.detail-wrap {
	background: #fff;
	border-radius: 14px;
	padding: 26px;
	box-shadow: 0 4px 14px rgba(0, 0, 0, 0.06);
	border: 1px solid #e9edf3;
}

.detail-top {
	margin-bottom: 18px;
	border-bottom: 1px solid #e9edf3;
	padding-bottom: 16px;
}

.detail-title {
	font-size: 26px;
	font-weight: 700;
	color: #222;
	margin-bottom: 14px;
}

.detail-meta {
	display: flex;
	flex-wrap: wrap;
	gap: 16px;
	font-size: 14px;
	color: #666;
}

.detail-content {
	min-height: 260px;
	padding: 24px 6px;
	line-height: 1.8;
	font-size: 15px;
	color: #333;
	white-space: pre-line;
}

.detail-actions {
	display: flex;
	gap: 10px;
	justify-content: flex-end;
	flex-wrap: wrap;
	margin-top: 18px;
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
	background: #2f80ed;
	color: #fff;
	padding: 10px 16px;
	border-radius: 8px;
	cursor: pointer;
	font-size: 14px;
	font-weight: 600;
}

.btn-danger2 {
	border: none;
	background: #e74c3c;
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
			<section class="detail-wrap">
				<div class="detail-top">
					<div class="detail-title">${board.title}</div>
					<div class="detail-meta">
						<span>구분 : ${board.board_type}</span> <span>작성자 :
							${board.user_name}</span> <span>작성일 : <fmt:formatDate
								value="${board.created_at}" pattern="yyyy-MM-dd" /></span> <span>조회수
							: ${board.view_count}</span>
					</div>
				</div>

				<div class="detail-content">${board.content}</div>

				<div class="detail-actions">
					<a href="${pageContext.request.contextPath}/board?action=list"
						class="btn-line">목록</a> <a
						href="${pageContext.request.contextPath}/board?action=editForm&board_key=${board.board_key}"
						class="btn-primary2">수정</a>

					<form method="post"
						action="${pageContext.request.contextPath}/board"
						style="display: inline;">
						<input type="hidden" name="action" value="delete"> <input
							type="hidden" name="board_key" value="${board.board_key}">
						<button type="submit" class="btn-danger2"
							onclick="return confirm('삭제하시겠습니까?');">삭제</button>
					</form>
				</div>
			</section>
			<section class="comment-wrap">
				<div class="comment-title">댓글</div>

				<form method="post"
					action="${pageContext.request.contextPath}/comment"
					class="comment-form">
					<input type="hidden" name="action" value="write"> <input
						type="hidden" name="board_key" value="${board.board_key}">
					<textarea name="content" placeholder="댓글을 입력하세요." required></textarea>
					<button type="submit" class="comment-submit">댓글 등록</button>
				</form>

				<div class="comment-list">
					<c:choose>
						<c:when test="${not empty commentList}">
							<c:forEach var="c" items="${commentList}">
								<div class="comment-item">
									<div class="comment-meta">
										<div class="comment-user">${c.user_name}</div>
										<div class="comment-date">
											<fmt:formatDate value="${c.created_at}" pattern="yyyy-MM-dd" />
										</div>
									</div>

									<div class="comment-content">${c.content}</div>

									<c:if
										test="${dto.user_key == c.user_key || dto.user_role == '관리자' || dto.user_role == '슈퍼바이저'}">
										<div class="comment-delete">
											<form method="post"
												action="${pageContext.request.contextPath}/comment">
												<input type="hidden" name="action" value="delete"> <input
													type="hidden" name="comment_key" value="${c.comment_key}">
												<input type="hidden" name="board_key"
													value="${board.board_key}">
												<button type="submit"
													onclick="return confirm('댓글을 삭제하시겠습니까?');">삭제</button>
											</form>
										</div>
									</c:if>
								</div>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<div class="comment-empty">등록된 댓글이 없습니다.</div>
						</c:otherwise>
					</c:choose>
				</div>
			</section>
		</main>
	</div>

</body>
</html>