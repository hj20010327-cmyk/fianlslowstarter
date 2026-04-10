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
<title>AUTO MES | 작업지시</title>
<script src="./asset/js/common.js" defer></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
</head>

<body>
	<header class="header">
		<div class="header-left">

			<a href="./index.jsp" class="logo"> <span class="logo-mark">AM</span>
				<span>AUTO MES</span>
			</a>

			<div class="header-title">자동차 콤프레셔 제조 MES</div>
		</div>

		<div class="header-right">
			<div class="header-chip">2026-03-30</div>
			<div class="header-chip">생산 1라인 가동중</div>
			<div class="header-chip">관리자</div>
		</div>
		<button type="button" class="menu-toggle" id="menuToggle">☰</button>
	</header>

	<div class="layout">
		<aside class="snb" id="snb">
			<div class="snb-section">
				<div class="snb-title">MAIN</div>
				<ul class="snb-menu">
					<li class="active"><a href="./index.jsp">대시보드</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">기준관리</div>
				<ul class="snb-menu">
					<li><a href="./master.jsp">기준관리</a></li>
					<li><a href="./bom.jsp">BOM</a></li>
					<li><a href="./process.jsp">공정</a></li>
					<li><a href="./machine.jsp">설비</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">생산관리</div>
				<ul class="snb-menu">
					<li><a href="./workorder.jsp">작업지시 <span
							class="menu-badge"></span></a></li>
					<li><a href="./plan.jsp">생산계획 <span class="menu-badge"></span></a></li>
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
					<li><a href="./quality.jsp">품질<span class="menu-badge">2</span></a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">리포트</div>
				<ul class="snb-menu">
					<li><a href="./report.jsp">리포트</a></li>
					<li><a href="./production.jsp">생산실적</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">시스템</div>
				<ul class="snb-menu">
					<li><a href="./board.jsp">게시판</a></li>
					<li><a href="./user.jsp">사용자관리</a></li>
					<li><a href="./mypage.jsp">마이페이지</a></li>
				</ul>
			</div>
		</aside>

		<div class="snb-overlay" id="snbOverlay"></div>
		<main class="content">
			<div class="page-head">
				<div class="page-head-left">
					<h1>작업지시</h1>
					<p>생산 작업지시 등록 및 진행상태를 확인합니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn" type="button">조회</button>
					<button class="btn primary" type="button"
						onclick="openModal('BOM 신규 등록')">신규 등록</button>
				</div>
			</div>
			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>기준 조건을 선택하세요</span>
				</div>
				<div class="search-row">
					<input class="input" type="text" placeholder="코드 또는 번호 입력" /><input
						class="input" type="text" placeholder="명칭 입력" /><select
						class="select">
						<option>전체</option>
						<option>사용</option>
						<option>미사용</option>
					</select>
					<button class="btn primary" type="button">조회</button>
				</div>
			</section>
			<section class="panel-grid">
				<div class="card">
					<form action="workorder" method="post">
						<input type="hidden" name="cmd" value="delete">

						<div class="section-title">
							<h2>작업지시 목록</h2>
							<button type="submit" class="btn">삭제</button>
						</div>
						<div class="table-wrap">

							<table>
								<tr>
									<th>선택</th>
									<th>작업코드</th>
									<th>계획Key</th>
									<th>작업일</th>
									<th>수량</th>
									<th>지시자</th>
								</tr>

								<c:forEach var="w" items="${list}">
									<tr>
										<td><input type="checkbox" name="work_order_key"
											value="${w.work_order_key}"></td>
										<td>${w.work_order_code}</td>
										<td>${w.plan_key}</td>
										<td>${w.work_date}</td>
										<td>${w.order_qty}</td>
										<td>${w.order_user_key}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</form>
				</div>
				<div class="card">
					<div class="section-title">
						<h2>요약 / 상태</h2>
						<span>오늘 기준</span>
					</div>
					<ul class="summary-list">
						<li>
							<div>
								<strong>진행중 작업</strong>
								<p>현재 8건의 작업이 진행 중입니다.</p>
							</div> <span class="badge ok">8건</span>
						</li>
						<li>
							<div>
								<strong>금일 마감</strong>
								<p>오늘 납기 작업이 3건 남았습니다.</p>
							</div> <span class="badge warn">3건</span>
						</li>
						<li>
							<div>
								<strong>지연 작업</strong>
								<p>현재 지연 작업은 없습니다.</p>
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

			<!-- 헤더 -->
			<div class="modal-header">
				<h3 id="modalTitle">신규 등록</h3>
				<button class="modal-close" onclick="closeModal()">×</button>
			</div>

			<!-- 바디 -->
			<div class="modal-body">
				<div class="form-grid">

					<div class="form-group">
						<label>코드</label> <input type="text" class="input"
							placeholder="코드 입력" />
					</div>

					<div class="form-group">
						<label>제품명</label> <input type="text" class="input"
							placeholder="제품명 입력" />
					</div>

					<div class="form-group">
						<label>품목명</label> <input type="text" class="input"
							placeholder="품목명 입력" />
					</div>

					<div class="form-group">
						<label>소요량</label> <input type="number" class="input"
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
						<textarea class="textarea" placeholder="설명 입력"></textarea>
					</div>

				</div>
			</div>

			<!-- 푸터 -->
			<div class="modal-footer">
				<button class="btn" onclick="closeModal()">취소</button>
				<button class="btn primary">저장</button>
			</div>

		</div>
	</div>
</body>

</html>