<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>AUTO MES | 품질관리</title>
<script src="./asset/js/common.js" defer></script>
<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/page.css" />
<style>
    /* 페이지네이션 스타일 유지 */
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
</style>
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
    </style>
<body>
<<<<<<< HEAD
    <header class="header">
        <div class="header-left">
=======
	 <header class="header">
        <div class="header-left">
            
>>>>>>> aaaaa1c577f545a094cfdfabe4c605dea7dc8d47
            <a href="./index.jsp" class="logo">
                <span class="logo-mark">AM</span>
                <span>AUTO MES</span>
            </a>
<<<<<<< HEAD
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip">2026-04-10</div>
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
                    <li><a href="./index.jsp">대시보드</a></li>
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
                    <li><a href="./workorder.jsp">작업지시</a></li>
                    <li><a href="./plan.jsp">생산계획</a></li>
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
                    <li class="active"><a href="./quality/main">품질 <span class="menu-badge">2</span></a></li>
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

        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>품질관리</h1>
                    <p>검사 결과 및 상태 정보를 관리합니다.</p>
                </div>
                <div class="page-actions">
                    <button class="btn primary" type="button" onclick="openModal('품질 신규 등록')">품질 등록</button>
                </div>
            </div>

            <section class="card">
                <div class="section-title">
                    <h2>검색 조건</h2>
                    <span>품질 조회 조건</span>
                </div>
                <form action="quality/main" method="get">
                    <div class="search-row">
                        <input class="input" type="text" name="qualityCode" placeholder="검사번호 입력" /> 
                        <select class="select" name="qcStatus">
                            <option value="">전체 상태</option>
                            <option value="합격">합격</option>
                            <option value="불합격">불합격</option>
                            <option value="재검">재검</option>
                        </select>
                        <button class="btn primary" type="submit">조회</button>
                    </div>
                </form>
            </section>

            <section class="panel-grid">
                <div class="card">
                    <form action="quality/delete" method="post">
                        <div class="section-title">
                            <h2>품질 목록</h2>
                            <button type="submit" class="btn">삭제</button>
                        </div>
                        <div class="table-wrap">
                            <table>
                                <thead>
                                    <tr>
                                        <th>선택</th>
                                        <th>검사 번호</th>
                                        <th>품목 명</th>
                                        <th>검사 수량</th>
                                        <th>품질 상태</th>
                                        <th>검사 일자</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="q" items="${list}">
                                        <tr>
                                            <td><input type="checkbox" name="quality_key" value="${q.quality_key}"></td>
                                            <td>${q.quality_code}</td>
                                            <td>${q.item_name}</td>
                                            <td>${q.inspect_qty}</td>
                                            <td>
                                                <span class="badge ${q.qc_status == '합격' ? 'ok' : (q.qc_status == '재검' ? 'warn' : 'danger')}">
                                                    ${q.qc_status}
                                                </span>
                                            </td>
                                            <td>${q.inspect_date}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <div class="pagination">
                                <a href="quality/main?page=1">1</a>
                                <a href="quality/main?page=2">2</a>
                                <a href="quality/main?page=3">3</a>
                                <a href="quality/main?page=4">4</a>
                            </div>
                        </div>
                    </form>
                </div>
=======
            
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
                    <li><a href="./workorder.jsp">작업지시 <span class="menu-badge"></span></a></li>
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
		<main class="content">
			<div class="page-head">
				<div class="page-head-left">
					<h1>설비정보</h1>
					<p>설비 상태 및 운영 정보를 관리합니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn primary" type="button"
						onclick="openModal('BOM 신규 등록')">설비 등록</button>
				</div>
			</div>

			<section class="card">
				<div class="section-title">
					<h2>검색 조건</h2>
					<span>설비 조회 조건</span>
				</div>
				<form action="machine" method="get">
					<div class="search-row">
						<input class="input" type="text" name="machineName"
							placeholder="설비명 입력" /> <select class="select"
							name="machineStatus">
							<option value="">전체</option>
							<option value="가동중">가동중</option>
							<option value="점검중">점검중</option>
						</select>

						<button class="btn primary" type="submit">조회</button>
					</div>
				</form>
			</section>
>>>>>>> aaaaa1c577f545a094cfdfabe4c605dea7dc8d47

                <div class="card">
                    <div class="section-title">
                        <h2>품질 상태 요약</h2>
                        <span>실시간 검사 기준</span>
                    </div>
                    <ul class="summary-list">
                        <li>
                            <div>
                                <strong>합격 건수</strong>
                                <p>금일 검사 대비 85% 합격</p>
                            </div> <span class="badge ok">정상</span>
                        </li>
                        <li>
                            <div>
                                <strong>재검 필요</strong>
                                <p>2건의 데이터 재검토 필요</p>
                            </div> <span class="badge warn">주의</span>
                        </li>
                        <li>
                            <div>
                                <strong>불합격 발생</strong>
                                <p>1건의 부적합 품목 발생</p>
                            </div> <span class="badge danger">긴급</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>

<<<<<<< HEAD
        <div id="commonModal" class="modal">
            <div class="modal-box">
                <div class="modal-header">
                    <h3 id="modalTitle">품질 등록</h3>
                    <button class="modal-close" onclick="closeModal()">×</button>
                </div>
                <form action="quality/add" method="post">
                    <div class="modal-body">
                        <div class="form-grid">
                            <div class="form-group">
                                <label>검사 번호</label> 
                                <input type="text" name="quality_code" class="input" placeholder="Q-XXX 형식" />
                            </div>
                            <div class="form-group">
                                <label>품목 키(Prod Key)</label> 
                                <input type="number" name="prod_key" class="input" placeholder="숫자 입력" />
                            </div>
                            <div class="form-group">
                                <label>검사 수량</label> 
                                <input type="number" name="inspect_qty" class="input" placeholder="수량 입력" />
                            </div>
                            <div class="form-group">
                                <label>합격 수량</label> 
                                <input type="number" name="good_qty" class="input" placeholder="수량 입력" />
                            </div>
                            <div class="form-group">
                                <label>품질 상태</label> 
                                <select name="qc_status" class="select">
                                    <option value="합격">합격</option>
                                    <option value="불합격">불합격</option>
                                    <option value="재검">재검</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>검사 일자</label> 
                                <input type="date" name="inspect_date" class="input" />
                            </div>
                            <div class="form-group" style="grid-column: span 2;">
                                <label>불합격 사유 (선택)</label>
                                <textarea name="defect_reason" class="textarea" placeholder="사유 입력"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn" onclick="closeModal()">취소</button>
                        <button type="submit" class="btn primary">저장</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
=======
							<table>
								<tr>
									<th>선택</th>
									<th>설비 번호</th>
									<th>설비 명</th>
									<th>설비 코드</th>
									<th>설비 상태</th>
									<th>공정 번호</th>
								</tr>

								<c:forEach var="m" items="${list}">
									<tr>
										<td><input type="checkbox" name="machineKey"
											value="${m.machineKey}"></td>
										<td>${m.machineKey}</td>
										<td>${m.machineName}</td>
										<td>${m.machineCode}</td>
										<td>${m.machineStatus}</td>
										<td>${m.processKey}</td>
									</tr>
								</c:forEach>
							</table>
							<div class="pagination">
    						<a href="machine?page=1">1</a>
   							<a href="machine?page=2">2</a>
    						<a href="machine?page=3">3</a>
    						<a href="machine?page=4">4</a>
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
	</div>
>>>>>>> aaaaa1c577f545a094cfdfabe4c605dea7dc8d47
</body>
</html>