<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="login.LoginDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>AUTO MES | 사용자 관리</title>
<script src="${pageContext.request.contextPath}/asset/js/common.js"
	defer></script>
	<script src="./asset/js/user.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/page.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/asset/css/user.css"/>
</head>

<body>
	<%
	LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");
	if (loginUser == null) {
		response.sendRedirect(request.getContextPath() + "/login.jsp");
		return;
	}
	%>
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
					<li><a href="./board">게시판</a></li>
					<c:if test='${dto.user_role eq "슈퍼바이저"}'>
						<li class="active"><a href="./user">사용자관리</a></li>
					</c:if>
					<li><a href="./mypage">마이페이지</a></li>
				</ul>
			</div>
		</aside>
		<div class="snb-overlay" id="snbOverlay"></div>
		<main class="content">
			<div class="page-head">
				<div class="page-head-left">
					<h1>사용자 관리</h1>
					<p>사용자 목록 조회, 상세 수정, 상태 변경을 관리합니다.</p>
				</div>
			</div>

			<c:if test="${param.msg == 'updated'}">
				<div class="msg-box msg-success">사용자 정보가 수정되었습니다.</div>
			</c:if>
			<c:if test="${param.msg == 'deleted'}">
				<div class="msg-box msg-success">사용자 상태가 퇴사로 변경되었습니다.</div>
			</c:if>
			<c:if test="${param.msg == 'fail'}">
				<div class="msg-box msg-error">작업에 실패했습니다. 다시 시도해주세요.</div>
			</c:if>

			<div class="user-page-wrap">
				<section class="card-box">
					<div class="card-title">
						<div>
							<h2>사용자 목록</h2>
							<p>전체 사용자 현황을 확인할 수 있습니다.</p>
						</div>
						<div class="count-chip">
							총
							<c:out value="${fn:length(userList)}" />
							명
						</div>
					</div>
					<div class="table-wrap">
						<table class="user-table">
							<thead>
								<tr>
									<th>번호</th>
									<th>사번</th>
									<th>아이디</th>
									<th>이름</th>
									<th>권한</th>
									<th>연락처</th>
									<th>이메일</th>
									<th>상태</th>
									<th>관리</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty userList}">
										<c:forEach var="u" items="${userList}">
											<tr>
												<td>${u.user_key}</td>
												<td>${u.emp_no}</td>
												<td>${u.user_id}</td>
												<td>${u.user_name}</td>
												<td><c:choose>
														<c:when test="${u.user_role == '작업자'}">
															<span class="badge badge-role-worker">${u.user_role}</span>
														</c:when>
														<c:when test="${u.user_role == '관리자'}">
															<span class="badge badge-role-manager">${u.user_role}</span>
														</c:when>
														<c:otherwise>
															<span class="badge badge-role-supervisor">${u.user_role}</span>
														</c:otherwise>
													</c:choose></td>
												<td>${u.user_phone}</td>
												<td>${u.user_email}</td>
												<td><c:choose>
														<c:when test="${u.status == '재직'}">
															<span class="badge badge-status-active">${u.status}</span>
														</c:when>
														<c:when test="${u.status == '휴직'}">
															<span class="badge badge-status-rest">${u.status}</span>
														</c:when>
														<c:otherwise>
															<span class="badge badge-status-leave">${u.status}</span>
														</c:otherwise>
													</c:choose></td>
												<td>
													<div class="btn-row">
														<a class="btn btn-primary"
															href="${pageContext.request.contextPath}/user?action=detail&user_key=${u.user_key}">상세</a>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="9">등록된 사용자가 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</section>

				<section class="card-box">
					<div class="card-title">
						<div>
							<h2>사용자 상세</h2>
							<p>선택한 사용자의 정보를 수정하거나 상태를 변경할 수 있습니다.</p>
						</div>
					</div>
					<c:choose>
						<c:when test="${not empty userOne}">
							<form method="post"
								action="${pageContext.request.contextPath}/user">
								<input type="hidden" name="action" value="update"> <input
									type="hidden" name="user_key" value="${userOne.user_key}">
								<div class="form-grid">
									<div class="form-group">
										<label>사번</label><input type="text" class="input"
											value="${userOne.emp_no}" readonly>
									</div>
									<div class="form-group">
										<label>아이디</label><input type="text" class="input"
											value="${userOne.user_id}" readonly>
									</div>
									<div class="form-group">
										<label>이름</label><input type="text" name="user_name"
											class="input" value="${userOne.user_name}" required readonly>
									</div>
									<div class="form-group">
										<label>권한</label> <select name="user_role" class="select"
											required>
											<option value="작업자"
												<c:if
                                                                    test="${userOne.user_role == '작업자'}">selected</c:if>>작업자</option>
											<option value="관리자"
												<c:if
                                                                    test="${userOne.user_role == '관리자'}">selected</c:if>>관리자</option>
											<option value="슈퍼바이저"
												<c:if
                                                                    test="${userOne.user_role == '슈퍼바이저'}">selected
                                                                    </c:if>>슈퍼바이저</option>
										</select>
									</div>
									<div class="form-group">
										<label>연락처</label><input type="text" name="user_phone"
											class="input" value="${userOne.user_phone}" readonly>
									</div>
									<div class="form-group">
										<label>이메일</label><input type="email" name="user_email"
											class="input" value="${userOne.user_email}" readonly>
									</div>
									<div class="form-group">
										<label>상태</label> <select name="status" class="select"
											required>
											<option value="재직"
												<c:if
                                                                    test="${userOne.status == '재직'}">selected</c:if>>재직
											</option>
											<option value="휴직"
												<c:if
                                                                    test="${userOne.status == '휴직'}">selected</c:if>>휴직
											</option>
											<option value="퇴사"
												<c:if
                                                                    test="${userOne.status == '퇴사'}">selected</c:if>>퇴사
											</option>
										</select>
									</div>
								</div>
								<div class="detail-actions" style="margin-top: 18px;">
									<button type="submit" class="btn btn-primary">수정 저장</button>
									<a href="${pageContext.request.contextPath}/user"
										class="btn btn-gray">선택 해제</a>
								</div>
							</form>
							<hr
								style="margin: 18px 0; border: 0; border-top: 1px solid #e5e7eb;">
						</c:when>
						<c:otherwise>
							<div class="detail-empty">위의 목록에서 사용자를 선택하면 상세 정보가 표시됩니다.</div>
						</c:otherwise>
					</c:choose>
				</section>
			</div>
		</main>
	</div>
</body>

</html>