<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>AUTO MES | 공정관리</title>
<script src="./asset/js/common.js" defer></script>
<script src="./asset/js/process.js" defer></script>

<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
<link rel="stylesheet" href="./asset/css/pagination.css" />

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
					<li class="active"><a href="./process">공정</a></li>
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
					<h1>공정관리</h1>
					<p>제품 제조 공정 흐름과 표준 작업정보를 관리합니다</p>
				</div>
				<div class="page-actions">
					<button class="btn primary" type="button"
						onclick="openInsertModal()">신규 등록</button>
				</div>
			</div>
			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>기준 조건을 선택하세요</span>
				</div>
				<form method="get" action="process">
					<div class="search-row">
						<input class="input" type="text" name="keycode"
							placeholder="코드 또는 번호 입력" /><input class="input" name="keyword"
							type="text" placeholder="명칭 입력" /><select class="select">
							<option>전체</option>
							<option>사용</option>
							<option>미사용</option>
						</select>
						<button class="btn primary" type="submit">조회</button>
					</div>
				</form>
			</section>
			<section class="panel-grid">
				<form method="post" action="process">
					<div class="card">
						<div class="section-title">
							<h2>공정관리 목록</h2>
							<span>
								<button type="submit" class="btn" value="삭제"
									style="background: #4a90e2; color: white;">삭제</button> <input
								type="hidden" name="cmd" value="delete">
							</span>
						</div>

						<div class="table-wrap">
							<table>
								<thead>
									<tr>
										<th>선택</th>
										<th>공정코드</th>
										<th>공정명</th>
										<th>일련번호</th>
										<th>공정설명</th>
										<th>상태</th>
										<th>제품명</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="process" items="${map.list}">
										<tr>
										
										
											<td><input type="checkbox" name="process_key"
												value="${process.process_key}"></td>
											<td> <a href="javascript:void(0);" 
												onclick="openEditModal(
												'${process.process_key}',		
												'${process.process_code}',		
												'${process.process_name}',		
												'${process.sequence_no}',		
												'${process.process_desc}',		
												'${process.status}',		
												'${process.item_key}'		
												)"> 
												${ process.process_code }</a></td>
											<td>${ process.process_name }</td>
											<td>${ process.sequence_no }</td>
											<td>${ process.process_desc }</td>
											<td>${ process.status }</td>
											<td>${ process.item_key }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							<div class="page" style="text-align: center;">

								<c:set var="total" value="${map.totalCount}" />
								<c:set var="size" value="${map.size}" />
								<c:set var="page" value="${map.page}" />

								<c:set var="totalPage" value="${(total + size - 1)/size}" />
								<c:set var="section" value="5" />
								<c:set var="end_section" value="${totalPage}" />
								<c:set var="start_section" value="${end_section - section + 1}" />

								<c:if test="${start_section < 1}">
									<c:set var="start_section" value="1" />
								</c:if>
								<c:if test="${end_section > totalPage}">
									<c:set var="end_section" value="${totalPage - 1}" />
								</c:if>


								<div class="pagination">
									<c:forEach var="i" begin="${start_section}"
										end="${end_section}">
										<a href="process?page=${i}&size=5"> <c:if
												test="${map.page eq i}">
												<strong>${i}</strong>
											</c:if> <c:if test="${map.page != i}">
										${i}
										</c:if>
										</a>

									</c:forEach>
								</div>
							</div>

						</div>
					</div>
				</form>

				<div class="card">
					<div class="section-title">
						<h2>요약 / 상태</h2>
						<span>오늘 기준</span>
					</div>
					<ul class="summary-list">
						<li>
							<div>
								<strong>병목 공정</strong>
								<p>조립 공정이 평균 3분 지연됩니다.</p>
							</div> <span class="badge warn">주의</span>
						</li>
						<li>
							<div>
								<strong>표준시간 갱신</strong>
								<p>검사 공정 표준시간이 수정되었습니다.</p>
							</div> <span class="badge ok">반영</span>
						</li>
						<li>
							<div>
								<strong>비사용 공정</strong>
								<p>비사용 공정 1건이 있습니다.</p>
							</div> <span class="badge danger">1건</span>
						</li>
					</ul>
				</div>
			</section>
		</main>
	</div>
	
		<!-- ===== 공통 모달 ===== -->
	<div id="commonModal" class="modal">
		<div class="modal-box">

			<form id="processForm" method="post" action="process">
			

				<!-- 헤더 -->
				<div class="modal-header">
					<h3 id="modalTitle">신규 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<!-- 바디 -->
				<div class="modal-body">
					<div class="form-grid">

						<div class="form-group">
							<label>등록일</label> 
							<input type="text" class="input"
								value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"
								readonly />
						</div>


						<div class="form-group">
							<label>코드</label> 
							<input type="text" name="process_code" id="process_code"
								class="input" placeholder="코드 입력" />
						</div>


						<div class="form-group">
							<label>제품명</label> 
							<input type="number" name="item_key" id="item-key"
								class="input" placeholder="제품명 입력" />
								
						</div>


						<div class="form-group">
							<label>공정 설명</label> 
							<input type="text" name="process_desc" id="process_desc"
								class="input" placeholder="공정 설명 입력" />
						</div>

						<div class="form-group">
							<label>공정 번호</label> 
							<input type="number" class="input" name="sequence_no" id="sequence_no"
								placeholder="예: 1" />
						</div>

						<div class="form-group">
							<label>사용여부</label> <select class="select" name="status" id="status">
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
					<input type="hidden" name="cmd" value="insert">
				</div>

			</form>

		</div>
	</div>
	
</body>

</html>