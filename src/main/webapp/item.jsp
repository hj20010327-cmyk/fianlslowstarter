<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
					<li class="active"><a href="./item">자재</a></li>
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
                    <h1>품목관리</h1>
                    <p>원자재와 부품 마스터를 관리하는 페이지입니다.</p>
                </div>
                <div class="page-actions"><button class="btn" type="button">조회</button><button class="btn primary"
                        type="button">신규 등록</button></div>
            </div>
            <section class="card" style="margin-bottom:20px">
                <div class="section-title">
                    <h2>검색 조건</h2><span>기준 조건을 선택하세요</span>
                </div>
                <div class="search-row"><input class="input" type="text" placeholder="코드 또는 번호 입력" /><input
                        class="input" type="text" placeholder="명칭 입력" /><select class="select">
                        <option>전체</option>
                        <option>사용</option>
                        <option>미사용</option>
                    </select><button class="btn primary" type="button">조회</button></div>
            </section>
            <section class="panel-grid">
                <div class="card">
                    <div class="section-title">
                        <h2>품목관리 목록</h2><span>샘플 데이터</span>
                    </div>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th>품목코드</th>
                                    <th>품목명</th>
                                    <th>구분</th>
                                    <th>단위</th>
                                    <th>현재고</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>ITM-1001</td>
                                    <td>하우징</td>
                                    <td>원자재</td>
                                    <td>EA</td>
                                    <td>1,250</td>
                                </tr>
                                <tr>
                                    <td>ITM-1002</td>
                                    <td>샤프트</td>
                                    <td>부품</td>
                                    <td>EA</td>
                                    <td>840</td>
                                </tr>
                                <tr>
                                    <td>ITM-1003</td>
                                    <td>베어링</td>
                                    <td>부품</td>
                                    <td>EA</td>
                                    <td>2,120</td>
                                </tr>
                                <tr>
                                    <td>ITM-1004</td>
                                    <td>오일씰</td>
                                    <td>소모품</td>
                                    <td>EA</td>
                                    <td>320</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="card">
                    <div class="section-title">
                        <h2>요약 / 상태</h2><span>오늘 기준</span>
                    </div>
                    <ul class="summary-list">
                        <li>
                            <div><strong>부족 품목</strong>
                                <p>안전재고 이하 품목이 5건 있습니다.</p>
                            </div><span class="badge danger">긴급</span>
                        </li>
                        <li>
                            <div><strong>입고 예정</strong>
                                <p>오늘 오후 2시 자재 입고 예정입니다.</p>
                            </div><span class="badge warn">예정</span>
                        </li>
                        <li>
                            <div><strong>품목 사용률</strong>
                                <p>핵심 품목 사용률 97%입니다.</p>
                            </div><span class="badge ok">양호</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>
    </div>
</body>
</html>