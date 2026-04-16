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
<title>AUTO MES | BOM관리</title>
<script src="./asset/js/common.js" defer></script>
<script src="./asset/js/bom.js" defer></script>


<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
<link rel="stylesheet" href="./asset/css/pagination.css" />
<link rel="stylesheet" href="./asset/css/bom.css" />

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
					<li class="active"><a href="./bom">BOM</a></li>
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
					<h1>BOM관리</h1>
					<p>제품별 자재 구성과 소요량을 관리하는 페이지입니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn primary" type="button"
						onclick="openInsertModal('BOM 신규 등록')">신규 등록</button>
				</div>
			</div>
			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>완제품별 BOM 조회</h2>
					<span>기준 조건을 선택하세요</span>
				</div>
				<form method="get" action="bom">
					<div class="search-row bom_search">
						<select name="parent_item_key" class="modal-select">
							<option value="">완제품 선택</option>
							<option value="1">컴프레셔 완제품 A형</option>
							<option value="2">컴프레셔 완제품 B형</option>
							<option value="3">컴프레셔 완제품 C형</option>
							<option value="4">컴프레셔 완제품 D형</option>
							<option value="5">컴프레셔 완제품 E형</option>
						</select>

						<button class="btn primary" type="submit">조회</button>
					</div>
				</form>
			</section>
			<section class="panel-grid">
				<form method="post" action="bom">
					<div class="card">
						<div class="section-title">
							<h2>BOM관리 목록</h2>
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
										<th>선택</th>
										<th>BOM코드</th>
										<th>완제품명</th>
										<th>자재명</th>
										<th>수량</th>
										<th>비고</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach var="bom" items="${list}">
										<tr>
											<td><input type="checkbox" name="bom_key"
												value="${bom.bom_key}"></td>
											<td><a href="javascript:void(0);"
												onclick="openEditModal('${bom.bom_key}',
											'${bom.bom_code}',
											'${bom.qty}',
											'${bom.remark}',
											'${bom.item_name}',
											'${bom.parent_item_name}'
											)">
													${bom.bom_code} </a></td>
											<td>${bom.parent_item_name}</td>
											<td>${bom.item_name}</td>
											<td>${bom.qty}</td>
											<td>${bom.remark}</td>
										</tr>
									</c:forEach>

								</tbody>
							</table>

							<div class="page" style="text-align: center;">

								<c:set var="total" value="${map.totalCount }" />
								<c:set var="size" value="${map.size }" />
								<c:set var="page" value="${map.page }" />

								<c:set var="totalPage" value="${(total + size - 1) /size}" />
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
										<a href="bom?page=${i}&size=10"> <c:if
												test="${map.page eq i }">
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

			<form id="bomForm" method="post" action="bom">

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
							<label>BOM코드</label> <input type="text" name="bom_code"
								id="bom_code" class="input" placeholder="자동 생성" />
						</div>

						<div class="form-group">
							<label>완제품명</label> <select name="parent_item_key"
								id="parent_item_key" class="modal-select">
								<option value="" selected>-- 선택하세요 --</option>
								<option value="1">컴프레셔 완제품 A형</option>
								<option value="2">컴프레셔 완제품 B형</option>
								<option value="3">컴프레셔 완제품 C형</option>
								<option value="4">컴프레셔 완제품 D형</option>
								<option value="5">컴프레셔 완제품 E형</option>
							</select>
						</div>

						<div class="form-group">
							<label>자재명</label> <select name="item_key" id="item_key"
								class="modal-select">
								<option value="" selected>-- 선택하세요 --</option>
								<option value="6">전방 하우징</option>
								<option value="7">후방 하우징</option>
								<option value="8">실린더 블록</option>
								<option value="9">샤프트</option>
								<option value="10">스와시 플레이트</option>
								<option value="11">피스톤 키트</option>
								<option value="12">밸브 플레이트</option>
								<option value="13">클러치 허브</option>
								<option value="14">풀리</option>
								<option value="15">코일 어셈블리</option>
								<option value="16">베어링 6204</option>
								<option value="17">오링</option>
								<option value="18">샤프트 씰</option>
								<option value="19">볼트MB</option>
								<option value="20">컴프레셔 오일</option>
							</select>
						</div>

						<div class="form-group">
							<label>소요량</label> <input type="number" name="qty" id="qty"
								class="input" placeholder="수량 입력" />
						</div>

						<div class="form-group" style="grid-column: span 2;">
							<label>비고</label>
							<textarea name="remark" class="textarea" id="remark"
								placeholder="추가 설명 입력"></textarea>
						</div>



					</div>
				</div>

				<!-- 푸터 -->
				<div class="modal-footer">
					<button class="btn" onclick="closeModal()" type="button">취소</button>
					<button class="btn primary" type="submit">저장</button>
					<input type="hidden" name="cmd" id="cmd"> <input
						type="hidden" name="bom_key" id="bom_key">

				</div>

			</form>

		</div>
	</div>



</body>
</html>