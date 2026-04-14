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

</head>

<body>
	<header class="header">
		<div class="header-left">
			<a href="./index.jsp" class="logo"><span class="logo-mark">AM</span><span>AUTO
					MES</span></a>
			<div class="header-title">자동차 콤프레셔 제조 MES</div>
		</div>
		<div class="header-right">
			<div class="header-chip date"></div>
			<div class="header-chip">${dto.user_name}</div>
			<div class="header-chip">${dto.user_role}</div>
		</div>
		<button type="button" class="menu-toggle" id="menuToggle">☰</button>
	</header>
	<div class="layout">
		<aside class="snb" id="snb">
			<div class="snb-section">
				<div class="snb-title">MAIN</div>
				<ul class="snb-menu">
					<li><a href="./index.jsp">대시보드</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">기준관리</div>
				<ul class="snb-menu">
					<li><a href="./master.jsp">기준관리</a></li>
					<li class="active"><a href="./BOM">BOM</a></li>
					<li><a href="./process.jsp">공정</a></li>
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
					<li><a href="./stock.jsp">재고</a></li>
					<li><a href="./product.jsp">완제품</a></li>
					<li><a href="./item.jsp">자재</a></li>
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
					<li><a href="./report.html">리포트</a></li>
					<li><a href="./production.html">생산실적</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">시스템</div>
				<ul class="snb-menu">
					<li><a href="./board.jsp">게시판</a></li>
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
						onclick="openModal('BOM 신규 등록')">신규 등록</button>
				</div>
			</div>
			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>기준 조건을 선택하세요</span>
				</div>
				<form method="get" action="BOM">
					<div class="search-row">
						<input class="input" type="text" name="keycode"
							placeholder="코드 또는 번호 입력" /> <input class="input" type="text"
							name="keyword" placeholder="명칭 입력" /> <select class="select">
							<option>전체</option>
							<option>사용</option>
							<option>미사용</option>
						</select>
						<button class="btn primary" type="submit">조회</button>
					</div>
				</form>
			</section>
			<section class="panel-grid">
				<form method="post" action="BOM">
					<div class="card">
						<div class="section-title">
							<h2>BOM관리 목록</h2>
							<span>
								<button class="btn modify" type="button"
									onclick="openModal('BOM 수정')">수정</button>
								<button type="submit" class="btn" value="삭제"
									style="background: #4a90e2; color: white;">삭제</button> 
								<input type="hidden" name="cmd" value="delete">
							</span>
						</div>
						
						<div class="table-wrap">
							<table>
								<thead>
									<tr>
										<th>선택</th>
										<th>BOM코드</th>
										<th>수량</th>
										<th>비고</th>
										<th>제품명</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach var="bom" items="${map.list}">
										<tr>
											<td><input type="checkbox" name="bom_key"
												value="${bom.bom_key}"></td>
											<td class="clickable" onclick="openEditModalFromElement(this)"
												data-code="${bom.bom_code}" data-name="${bom.bom_item_key}"
												data-qty="${bom.QTY}" data-remark="${bom.remark}">


												${bom.bom_code}</td>
											<td>${bom.QTY}</td>
											<td>${bom.remark}</td>
											<td>${bom.bom_item_key}</td>
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



							<div class = "pagination">
								<c:forEach var="i" begin="${start_section}" end="${end_section}">
									<a href="BOM?page=${i}&size=5"> <c:if
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
							</div>
							<span class="badge ok">적용</span>
						</li>
						<li>
							<div>
								<strong>변경 요청</strong>
								<p>설계 변경 요청이 1건 있습니다.</p>
							</div>
							<span class="badge warn">1건</span>
						</li>
						<li>
							<div>
								<strong>미연결 품목</strong>
								<p>BOM 미연결 자재가 없습니다.</p>
							</div>
							<span class="badge ok">정상</span>
						</li>
					</ul>
				</div>
			</section>
		</main>
	</div>

	<span class="date" style="display: none;"></span>

	<!-- ===== 공통 모달 ===== -->
	<div id="commonModal" class="modal">
		<div class="modal-box">

			<form method="post" action="BOM">

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
							<label>코드</label> <input type="text" name="bom_code"
								class="input" placeholder="코드 입력" />
						</div>



						<div class="form-group">
							<label>제품명</label> <input type="text" class="input"
								name="bom_item_key" placeholder="제품명 입력" />
						</div>


						<div class="form-group">
							<label>소요량</label> <input type="number" class="input" name="QTY"
								placeholder="수량 입력" />
						</div>

						<div class="form-group">
							<label>버전</label> <input type="text" class="input"
								placeholder="예: V1" />
						</div>

						<div class="form-group">
							<label>사용여부</label> <select class="select">
								<option>사용</option>
								<option>미사용</option>
							</select>
						</div>





						<div class="form-group" style="grid-column: span 2;">
							<label>비고</label>
							<textarea name="remark" class="textarea" placeholder="추가 설명 입력"></textarea>
						</div>

						<%-- 
        <div class="form-group" style="grid-column: span 2;">
          <label>비고</label>
          <textarea class="textarea" placeholder="설명 입력"></textarea>
        </div>
        
        --%>

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