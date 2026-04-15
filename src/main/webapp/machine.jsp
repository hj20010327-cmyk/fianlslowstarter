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
<title>AUTO MES | 설비정보</title>
<script src="./asset/js/common.js" defer></script>
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

.running {
	color: green;
}

.stop {
	color: red;
}
.search-row {
    display: flex;
    align-items: flex-end;
    gap: 12px;
}

.search-item {
    display: flex;
    flex-direction: column;
}

.search-label {
    font-size: 12px;
    color: #666;
    margin-bottom: 4px;
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
					<li><a href="./master">기준관리</a></li>
					<li><a href="./bom">BOM</a></li>
					<li><a href="./process">공정</a></li>
					<li class="active"><a href="/slowstarter/machine">설비</a></li>
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
					<h1>설비정보</h1>
					<p>설비 상태 및 운영 정보를 관리합니다.</p>
				</div>
				<div class="page-actions">
					<c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
						<button class="btn primary" type="button"
							onclick="openInsertModal()">설비 등록</button>
					</c:if>
				</div>
			</div>

			<section class="card" style="margin-bottom: 20px">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>설비 조회 조건</span>
				</div>
				<form action="/slowstarter/machine" method="get">
					<!-- 페이지 초기화 항상 1페이지부터 시작 -->
					<input type="hidden" name="page" value="1">
					<div class="search-row">
						<!--  설비명 검색 -->
						<input class="input" type="text" name="machineName"
							placeholder="설비명 입력" />
						<!--  상태 검색 -->
						<select class="select" name="machineStatus">
							<option value=""
								<c:if test="${empty machineStatus}">selected</c:if>>전체</option>
							<option value="가동중"
								<c:if test="${machineStatus == '가동중'}">selected</c:if>>가동중</option>
							<option value="점검중"
								<c:if test="${machineStatus == '점검중'}">selected</c:if>>점검중</option>
						</select>
						<div class="search-item">
							<span class="search-label">마지막 점검일</span> <input
								class="input date-input" type="date" name="lastCheckDate"
								value="${lastCheckDate}" />
						</div>
						<!-- 조회 버튼 → MachineListController로 이동 -->
						<button class="btn primary" type="submit">조회</button>
						<a href="/slowstarter/machine?page=1" class="btn">초기화</a>
					</div>
				</form>
			</section>

			<section class="panel-grid">
				<div class="card">
					<form action="/slowstarter/machine/delete" method="post">
						<div class="section-title">
							<h2>설비 목록</h2>
							<!--  안내 메시지용  -->
							<c:if
								test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
								<span>설비 정보를 클릭하면 수정할 수 있습니다.</span>
								<button type="submit" class="btn">삭제</button>
							</c:if>
						</div>
						<div class="table-wrap">

							<table>
								<tr>
									<th>선택</th>
									<th>설비 코드</th>
									<th>설비 명</th>
									<th>설비 상태</th>
									<th>마지막 점검일</th>
									<th>비고</th>
								</tr>


								<c:forEach var="m" items="${list}">
									<tr>
										<td><input type="checkbox" name="machineKey"
											value="${m.machineKey}"></td>

										<td>
											<!-- 관리자, 슈퍼바이저 일때 설비명 누르면 모달창 열리게 --> <c:if
												test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
												<!-- javascript:void(0) 이거는 아무동작하지말라고 넣음-->
												<a href="javascript:void(0);"
													onclick="openEditModal(
		   									'${m.machineKey}',
		   									'${m.machineCode}',
		   									'${m.machineName}',
		   									'${m.processKey}',
		   									'${m.machineStatus}',
		   									'${m.buyDate}',
		   									'${m.lastCheckDate}',
		   									'${m.remark}'
	   										)">
													${m.machineCode} </a>
											</c:if> <!--  작업자 일때 설비명 눌러도 반응 x --> <c:if
												test="${not (dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저') }">
												${m.machineName}
											</c:if>
										</td>
										<td>${m.machineName}</td>
										<td><c:if test="${m.machineStatus eq '가동중'}">
												<span class="running">가동중</span>
											</c:if> <c:if test="${m.machineStatus eq '점검중'}">
												<span class="stop">점검중</span>
											</c:if></td>
										<td>${m.lastCheckDate}</td>
										<td>${m.remark}</td>
									</tr>
								</c:forEach>
							</table>
							<div class="pagination">
								<c:forEach var="i" begin="1" end="${totalPage}">
									<c:if test="${page == i}">
										<a href="machine?page=${i}&machineName=${machineName}&machineStatus=${machineStatus}&lastCheckDate=${lastCheckDate}"
											class="active">${i}</a>
									</c:if>

									<c:if test="${page != i}">
										<a href="machine?page=${i}&machineName=${machineName}&machineStatus=${machineStatus}&lastCheckDate=${lastCheckDate}">${i}</a>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</form>
				</div>

				<div class="card">
					<div class="section-title">
						<h2>설비 상태 요약</h2>
						<span>실시간 기준</span>
					</div>
					<ul class="summary-list">
						<li>
							<div>
								<strong>가동 설비</strong>
								<p>총 12대 중 9대 가동중</p>
							</div> <span class="badge ok">정상</span>
						</li>
						<li>
							<div>
								<strong>점검 필요</strong>
								<p>3대 점검 필요</p>
							</div> <span class="badge warn">주의</span>
						</li>
						<li>
							<div>
								<strong>고장 설비</strong>
								<p>1대 고장 발생</p>
							</div> <span class="badge danger">긴급</span>
						</li>
					</ul>
				</div>
			</section>
		</main>
		<!-- ===== 공통 모달 ===== -->
		<div id="commonModal" class="modal">
			<div class="modal-box">
				<form id="machineForm" action="/slowstarter/machine/add"
					method="post">

					<!-- 헤더 -->
					<div class="modal-header">
						<h3 id="modalTitle">설비 등록</h3>
						<button type="button" class="modal-close" onclick="closeModal()">×</button>
					</div>

					<!-- 바디 -->
					<div class="modal-body">
						<input type="hidden" id="machineKey" name="machineKey" />

						<div class="form-grid">

							<div class="form-group">
								<label>설비 코드</label> <input type="text" class="input"
									id="machineCode" name="machineCode" placeholder="자동 생성 예정" />
							</div>

							<div class="form-group">
								<label>설비명</label> <input type="text" class="input"
									id="machineName" name="machineName" placeholder="설비명 입력" />
							</div>

							<div class="form-group">
								<label>공정 번호</label> <input type="number" class="input"
									id="processKey" name="processKey" placeholder="공정 번호 입력" />
							</div>

							<div class="form-group">
								<label>설비 상태</label> <select class="select" id="machineStatus"
									name="machineStatus">
									<option value="">선택</option>
									<option value="가동중">가동중</option>
									<option value="점검중">점검중</option>
								</select>
							</div>

							<div class="form-group">
								<label>구매일</label> <input type="date" class="input" id="buyDate"
									name="buyDate" />
							</div>

							<div class="form-group">
								<label>최근 점검일</label> <input type="date" class="input"
									id="lastCheckDate" name="lastCheckDate" />
							</div>

							<div class="form-group" style="grid-column: span 2;">
								<label>비고</label>
								<textarea class="textarea" id="remark" name="remark"
									placeholder="비고 입력"></textarea>
							</div>

						</div>
					</div>

					<!-- 푸터 -->
					<div class="modal-footer">
						<button type="button" class="btn" onclick="closeModal()">취소</button>
						<button type="submit" class="btn primary">저장</button>
					</div>

				</form>
			</div>
		</div>
	</div>
	<script>
		function openInsertModal() {
			document.getElementById("modalTitle").innerText = "설비 등록";
			document.getElementById("machineForm").action = "/slowstarter/machine/add";

			document.getElementById("machineKey").value = "";
			document.getElementById("machineCode").value = "";
			document.getElementById("machineName").value = "";
			document.getElementById("processKey").value = "";
			document.getElementById("machineStatus").value = "";
			document.getElementById("buyDate").value = "";
			document.getElementById("lastCheckDate").value = "";
			document.getElementById("remark").value = "";

			document.getElementById("commonModal").classList.add("show");
		}

		function openEditModal(machineKey, machineCode, machineName,
				processKey, machineStatus, buyDate, lastCheckDate, remark) {

			console.log("buyDate:", buyDate);
			console.log("lastCheckDate:", lastCheckDate);

			document.getElementById("modalTitle").innerText = "설비 수정";
			document.getElementById("machineForm").action = "/slowstarter/machine/update";

			document.getElementById("machineKey").value = machineKey;
			document.getElementById("machineCode").value = machineCode;
			document.getElementById("machineName").value = machineName;
			document.getElementById("processKey").value = processKey;
			document.getElementById("machineStatus").value = machineStatus;
			document.getElementById("buyDate").value = buyDate.substring(0, 10);
			document.getElementById("lastCheckDate").value = lastCheckDate
					.substring(0, 10);
			if (remark == 'null') {
				document.getElementById("remark").value = '';
			} else {
				document.getElementById("remark").value = remark;
			}

			document.getElementById("commonModal").classList.add("show");
		}

		function closeModal() {
			document.getElementById("commonModal").classList.remove("show");
		}
	</script>
</body>
</html>