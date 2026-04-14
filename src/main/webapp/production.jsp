<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>AUTO MES | 생산실적</title>
<script src="./asset/js/common.js" defer></script>

<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
</head>
<body>
	<header class="header">
		<div class="header-left">
			<a href="./index.html" class="logo"><span class="logo-mark">AM</span><span>AUTO
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
					<li><a href="./index.html">대시보드</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">기준관리</div>
				<ul class="snb-menu">
					<li><a href="./master.html">기준관리</a></li>
					<li><a href="./bom.html">BOM</a></li>
					<li><a href="./process.html">공정</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">생산관리</div>
				<ul class="snb-menu">
					<li><a href="./workorder.html">작업지시 <span
							class="menu-badge">4</span></a></li>
					<li><a href="./plan.html">생산계획 <span class="menu-badge">2</span></a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">재고관리</div>
				<ul class="snb-menu">
					<li><a href="./stock.html">재고</a></li>
					<li><a href="./product.html">완제품</a></li>
					<li><a href="./item.html">자재</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">품질관리</div>
				<ul class="snb-menu">
					<li><a href="./quality.html">품질<span class="menu-badge">2</span></a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">리포트</div>
				<ul class="snb-menu">
					<li><a href="./report.html">리포트</a></li>
					<li class="active"><a href="./production.html">생산실적</a></li>
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
					<h1>생산실적</h1>
					<p>생산 결과와 계획 대비 실적을 조회합니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn" type="button"
						onclick="location.href='${pageContext.request.contextPath}/production'">초기화</button>
					<button class="btn primary" type="button" id="btnAddProduction">신규 등록</button>
				</div>
			</div>

			<c:if test="${not empty errorMsg}">
				<div class="card"
					style="margin-bottom: 20px; color: #d64545; font-weight: 700;">
					${errorMsg}</div>
			</c:if>

			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>기준 조건을 선택하세요</span>
				</div>

				<form action="${pageContext.request.contextPath}/production"
					method="get" class="search-row">
					<input class="input" type="date" name="startDate"
						value="${startDate}" /> <input class="input" type="date"
						name="endDate" value="${endDate}" /> <input class="input"
						type="text" name="keyword" value="${keyword}"
						placeholder="품목코드 / 품목명 / 생산코드" /> 
						
					<button class="btn primary" type="submit">조회</button>
				</form>
			</section>

			<section class="panel-grid">
				<div class="card">
					<div class="section-title">
						<h2>생산실적 목록</h2>
						<span>${startDate} ~ ${endDate}</span>
					</div>
					<div class="table-wrap">
						<table>
							<thead>
								<tr>
									<th>생산일</th>
									<th>생산코드</th>
									<th>품목명</th>
									<th>계획수량</th>
									<th>지시수량</th>
									<th>투입수량</th>
									<th>양품수량</th>
									<th>불량수량</th>
									<th>달성률</th>
									<th>작업자</th>
								</tr>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty list}">
										<c:forEach var="p" items="${list}">
											<tr>
												<td>${p.prod_date}</td>
												<td>${p.prod_code}</td>
												<td><a href="javascript:void(0);"
													class="prod-edit-link" 
													data-prod-key="${p.prod_key}"
													data-prod-code="${p.prod_code}"
													data-prod-date="${p.prod_date}"
													data-item-name="${p.item_name}"
													data-plan-qty="${p.plan_qty}"
													data-order-qty="${p.order_qty}"
													data-input-qty="${p.input_qty}"
													data-good-qty="${p.good_qty}"
													data-defect-qty="${p.defect_qty}"
													data-work-user-key="${p.work_user_key}"
													data-work-user-name="${p.work_user_name}"
													data-work-order-key="${p.work_order_key}"
													data-work-order-code="${p.work_order_code}"
													data-plan-key="${p.plan_key}"
													data-plan-code="${p.plan_code}">
													 ${p.item_name} </a></td>
												<td><fmt:formatNumber value="${p.plan_qty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${p.order_qty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${p.input_qty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${p.good_qty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${p.defect_qty}"
														pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${p.achievement_rate}"
														pattern="0.00" />%</td>
												<td>${p.work_user_name}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="10">조회 결과가 없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>

				<div class="card">
					<div class="section-title">
						<h2>요약 / 상태</h2>
						<span>조회 기간 기준</span>
					</div>
					<ul class="summary-list">
						<li>
							<div>
								<strong>전체 달성률</strong>
								<p>조회 기간 평균 생산 달성률입니다.</p>
							</div> <span class="badge ok"> <fmt:formatNumber
									value="${summary.avg_achievement_rate}" pattern="0.00" />%
						</span>
						</li>
						<li>
							<div>
								<strong>최고 실적 품목</strong>
								<p>
									<c:choose>
										<c:when test="${not empty summary.best_item_name}">
                                            ${summary.best_item_name}
                                        </c:when>
										<c:otherwise>데이터 없음</c:otherwise>
									</c:choose>
								</p>
							</div> <span class="badge ok"> <fmt:formatNumber
									value="${summary.best_rate}" pattern="0.00" />%
						</span>
						</li>
						<li>
							<div>
								<strong>저조 품목</strong>
								<p>
									<c:choose>
										<c:when test="${not empty summary.low_item_name}">
                                            ${summary.low_item_name}
                                        </c:when>
										<c:otherwise>데이터 없음</c:otherwise>
									</c:choose>
								</p>
							</div> <span class="badge warn"> <fmt:formatNumber
									value="${summary.low_rate}" pattern="0.00" />%
						</span>
						</li>
						<li>
							<div>
								<strong>총 불량수량</strong>
								<p>조회 기간 누적 불량입니다.</p>
							</div> <span class="badge danger"> <fmt:formatNumber
									value="${summary.total_defect_qty}" pattern="#,##0" />
						</span>
						</li>
					</ul>
				</div>
			</section>
		</main>
	</div>

	<div id="commonModal" class="modal">
		<div class="modal-box">

			<form method="post" action="productionSave" id="productionForm">

				<div class="modal-header">
					<h3 id="modalTitle">생산실적 신규 등록</h3>
					<button type="button" class="modal-close" onclick="closeModal()">×</button>
				</div>

				<div class="modal-body">
					<div class="form-grid">

						<div class="form-group">
							<label>등록일</label> <input type="text" class="input"
								value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"
								readonly />
						</div>

						<div class="form-group">
							<label>생산코드</label> <input type="text" name="prod_code"
								id="prod_code" class="input" placeholder="생산코드 입력" />
						</div>

						<div class="form-group">
							<label>생산일</label> <input type="date" name="prod_date"
								id="prod_date" class="input" />
						</div>

						<div class="form-group">
							<label>품목명</label> <input type="text" name="item_name"
								id="item_name" class="input" placeholder="품목명" readonly />
						</div>

						<div class="form-group">
							<label>계획수량</label> <input type="number" name="plan_qty"
								id="plan_qty" class="input" readonly />
						</div>

						<div class="form-group">
							<label>지시수량</label> <input type="number" name="order_qty"
								id="order_qty" class="input" readonly />
						</div>

						<div class="form-group">
							<label>투입수량</label> <input type="number" name="input_qty"
								id="input_qty" class="input" placeholder="투입수량 입력" />
						</div>

						<div class="form-group">
							<label>양품수량</label> <input type="number" name="good_qty"
								id="good_qty" class="input" placeholder="양품수량 입력" />
						</div>

						<div class="form-group">
							<label>불량수량</label> <input type="number" name="defect_qty"
								id="defect_qty" class="input" placeholder="불량수량 입력" />
						</div>

						<div class="form-group">
							<label>작업자</label> <input type="text" name="work_user_name"
								id="work_user_name" class="input" readonly />
						</div>

						<div class="form-group">
							<label>작업지시코드</label> <input type="text" name="work_order_code"
								id="work_order_code" class="input" readonly />
						</div>

						<div class="form-group" style="grid-column: span 2;">
							<label>비고</label>
							<textarea name="remark" id="remark" class="textarea"
								placeholder="추가 설명 입력"></textarea>
						</div>

					</div>
				</div>

				<div class="modal-footer">
					<button class="btn" onclick="closeModal()" type="button">취소</button>
					<button class="btn primary" type="submit">저장</button>

					<input type="hidden" name="cmd" id="cmd" value="insert"> 
					<input type="hidden" name="prod_key" id="prod_key" value=""> 
					<input type="hidden" name="work_order_key" id="work_order_key" value="">
					<input type="hidden" name="work_user_key" id="work_user_key" value=""> 
					<input type="hidden" name="plan_key" id="plan_key" value="">
				</div>

			</form>

		</div>
	</div>
<script src="./asset/js/Production.js"></script>
</body>
</html>