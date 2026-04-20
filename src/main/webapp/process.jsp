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
				<div class="page-head-left">
					<h1>공정관리</h1>
					<p>제품 제조 공정 흐름과 표준 작업정보를 관리합니다</p>
				</div>
				<div class="page-actions">
					<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
						<button class="btn primary" type="button"
							onclick="openInsertModal()">신규 등록</button>
					</c:if>
				</div>
			</div>
			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>기준 조건을 선택하세요</span>
				</div>
				<form method="get" action="process">
					<div class="search-row">
					
					
					<select class="select" name="keyword">
    <option value="">완제품 선택</option>

    <option value="A형"
        ${param.keyword == 'A형' ? 'selected' : ''}>컴프레셔 완제품 A형</option>
    <option value="B형"
        ${param.keyword == 'B형' ? 'selected' : ''}>컴프레셔 완제품 B형</option>
    <option value="C형"
        ${param.keyword == 'C형' ? 'selected' : ''}>컴프레셔 완제품 C형</option>
    <option value="D형"
        ${param.keyword == 'D형' ? 'selected' : ''}>컴프레셔 완제품 D형</option>
    <option value="E형"
        ${param.keyword == 'E형' ? 'selected' : ''}>컴프레셔 완제품 E형</option>

    

</select>
					
						 <select
							class="select">
							<option>선택</option>
							<option>사용</option>
							<option>미사용</option>
						</select>

						<%--	<input class="input" name="keyword"
							type="text" placeholder="공정구분" />   --%>

						<select class="select" name="process_name">
							<option value="">공정구분</option>
							<option value="가공"
								${param.process_name == '가공' ? 'selected' : ''}>가공</option>
							<option value="세척"
								${param.process_name == '세척' ? 'selected' : '' }>세척</option>
							<option value="조립"
								${param.process_name == '조립' ? 'selected' : '' }>조립</option>
							<option value="성능검사"
								${param.process_name == '성능검사' ? 'selected' : '' }>성능검사</option>

						</select> <span>
							<button class="btn primary" type="submit">조회</button> <a
							href="/slowstarter/process" class="btn">초기화</a>
						</span>
					</div>
				</form>
			</section>
			<section class="panel-grid">
				<form method="post" action="process">
					<div class="card">
						<div class="section-title">
							<h2>공정관리 목록</h2>
							<!-- 관리자용 -->
							<c:if
								test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
								<span>
									<button type="submit" class="btn" value="삭제"
										style="background: #4a90e2; color: white;">삭제</button> <input
									type="hidden" name="cmd" value="delete">
								</span>
							</c:if>
						</div>

						<div class="table-wrap">
							<table>
								<thead>
									<tr>
										<th><c:if
												test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
										선택
										</c:if></th>
										<th>공정코드</th>
										<th>제품명</th>
										<th>공정순서</th>
										<th>공정명</th>
										<th>사용여부</th>
										<th>공정설명</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="process" items="${map.list}">
										<tr>


											<td><c:if
													test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
													<input type="checkbox" name="process_key"
														value="${process.process_key}">
												</c:if></td>
											<td><c:if
													test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
													<a href="javascript:void(0);"
														onclick="openEditModal(
												'${process.process_key}',		
												'${process.process_code}',		
												'${process.process_name}',		
												'${process.sequence_no}',		
												'${process.process_desc}',		
												'${process.status}',		
												'${process.item_key}'		
												)">
														${ process.process_code }</a>
												</c:if> <c:if
													test="${not(dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저')}">
												${ process.process_code}</c:if></td>
											<td>${ process.item_name }</td>
											<td>${ process.sequence_no }</td>
											<td>${ process.process_name }</td>
											<td>${ process.status }</td>
											<td>${ process.process_desc }</td>
											<!-- 관리자용 -->
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
										<a href="process?page=${i}&size=10"> <c:if
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


				<script>
console.log("JS 실행됨");

document.addEventListener("change", function (e) {
    if (e.target && e.target.id === "process_name") {
        const selected = e.target.options[e.target.selectedIndex];
        document.getElementById("process_desc").value =
            selected.dataset.desc || "";
        document.getElementById("sequence_no").value = 
        	selected.dataset.seq || "";
    }
});

document.addEventListener("DOMContentLoaded", () => {

	  const processSelect = document.getElementById("process_name");
	  const desc = document.getElementById("process_desc");
	  const seq = document.getElementById("sequence_no");

	  if (!processSelect) return;

	  // 복원
	  const saved = sessionStorage.getItem("process_name");
	  if (saved) {
	    processSelect.value = saved;
	  }

	  // change 이벤트
	  processSelect.addEventListener("change", (e) => {

	    const selected = e.target.options[e.target.selectedIndex];

	    sessionStorage.setItem("process_name", e.target.value);

	    if (desc) desc.value = selected.dataset.desc || "";
	    if (seq) seq.value = selected.dataset.seq || "";
	  });

	});
	
form.addEventListener("submit", () => {
	  const select = document.getElementById("process_name");
	  sessionStorage.setItem("process_name", select.value);
	});
	
window.addEventListener("load", () => {
	  const v = sessionStorage.getItem("process_name");
	  if (v) document.getElementById("process_name").value = v;
	});
	


</script>
				<div class="card">
					<div class="section-title">
						<h2>요약 / 상태</h2>
						<span>오늘 기준</span>
					</div>
					<ul class="summary-list">
						<li>
							<div>
								<strong>최신 버전</strong>
								<p>B210 제품이 V2로 운영 중입니다.</p>
							</div> <span class="badge ok">적용</span>
						</li>
						<li>
							<div>
								<strong>변경 요청</strong>
								<p>설계 변경 요청이 1건 있습니다.</p>
							</div> <span class="badge warn">1건</span>
						</li>
						<li>
							<div>
								<strong>미연결 품목</strong>
								<p>BOM 미연결 자재가 없습니다.</p>
							</div> <span class="badge ok">정상</span>
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
							<label>등록일</label> <input type="text" class="input"
								value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"
								readonly />
						</div>

						<div class="form-group">
							<input type="hidden" name="process_code" id="process_code"
								class="input" placeholder="자동입력" readonly />
						</div>

						<div class="form-group">
							<label>제품명</label> <select id="item_key" name="item_key"
								class="select">
								<option value="">선택하세요</option>
								<c:forEach var="item" items="${itemList}">
									<option value="${item.item_key}">${item.item_name}</option>
								</c:forEach>

							</select>
						</div>


						<div class="form-group">
							<label>공정명</label> <select name="process_name" id="process_name"
								class="select">
								<option value="" selected>-- 선택하세요 --</option>
								<option value="가공" data-desc="전방 하우징 가공" data-seq="1">가공</option>
								<option value="세척" data-desc="가공품 세척" data-seq="2">세척</option>
								<option value="조립" data-desc="핵심 부품 조립" data-seq="3">조립</option>
								<option value="성능검사" data-desc="성능 및 누설 검사" data-seq="4">성능검사</option>
							</select>
						</div>

						<div class="form-group">
							<label>공정 순서</label> <input type="number" class="input"
								name="sequence_no" id="sequence_no" placeholder="예: 1" />
						</div>

						<div class="form-group">
							<label>공정 설명</label> <input type="text" name="process_desc"
								id="process_desc" class="input" placeholder="공정 설명 입력" />
						</div>


						<div class="form-group">
							<label>사용여부</label> <select class="select" name="status"
								id="status">
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
						type="hidden" name="process_key" id="process_key">
				</div>

			</form>

		</div>
	</div>

</body>

</html>