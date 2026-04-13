<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>AUTO MES | 재고관리</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/common.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/page.css" />
    <script src="${pageContext.request.contextPath}/asset/js/common.js" defer></script>

    <style>
        body { 
            display: flex; 
            flex-direction: column; 
            min-height: 100vh; 
            margin: 0; 
        }

        .layout { 
            display: flex; 
            flex: 1; 
        }

        .content { 
            padding: 24px 0 0 0; 
            background-color: #f4f7fa; 
            flex: 1; 
            display: flex; 
            flex-direction: column; 
            width: 100%; 
        }

        /* 본문 요소 좌우 여백 */
        .content > .page-header, 
        .content > .card, 
        .content > .main-grid { 
            margin-left: 24px; 
            margin-right: 24px; 
        }

        .card { 
            background: #fff; 
            border-radius: 12px; 
            box-shadow: 0 1px 3px rgba(0,0,0,0.05); 
            padding: 24px; 
            margin-bottom: 20px; 
        }

        /* 테이블 스타일 */
        table { width: 100%; border-collapse: collapse; }
        
        th { 
            background-color: #f8fafc; 
            padding: 12px; 
            border-bottom: 1px solid #e2e8f0; 
            text-align: center; 
            color: #64748b; 
            font-size: 13px;
        }

        td { 
            padding: 14px 12px; 
            border-bottom: 1px solid #f1f5f9; 
            text-align: center; 
            font-size: 14px;
        }

        .qty-alert { color: #e53e3e; font-weight: bold; }

        /* 모달 스타일 */
        .modal { 
            display: none; 
            position: fixed; 
            z-index: 1000; 
            left: 0; top: 0; 
            width: 100%; height: 100%; 
            background: rgba(0,0,0,0.5); 
            align-items: center; 
            justify-content: center; 
        }

        .modal.show { display: flex; }

        .modal-content { 
            background: #fff; 
            width: 450px; 
            border-radius: 12px; 
            overflow: hidden; 
        }
    </style>

    <script>
        function openAddModal() {
            document.getElementById("stockModal").classList.add("show");
        }

        function closeModal() {
            document.getElementById("stockModal").classList.remove("show");
        }

        function doSearch() {
            const keyword = document.getElementById('searchKeyword').value;
            location.href = "stockList?p=1&keyword=" + encodeURIComponent(keyword);
        }
    </script>
</head>

<body>

    <header class="header">
        <div class="header-left">
            <a href="./index.jsp" class="logo">
                <span class="logo-mark">AM</span>
                <span>AUTO MES</span>
            </a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
<<<<<<< HEAD
            <div class="header-chip">2026-03-30</div>
            <div class="header-chip">생산 1라인 가동중</div>
            <div class="header-chip">관리자</div>
=======
            <div class="header-chip date"></div> 
            <div class="header-chip">${dto.user_name}</div>
            <div class="header-chip">${dto.user_role}</div>
>>>>>>> 05ba314 (모든 사이트 연결)
        </div>
        <button type="button" class="menu-toggle" id="menuToggle">☰</button>
    </header>


    <div class="layout">
<<<<<<< HEAD
        
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
            <div class="snb-section active">
                <div class="snb-title">재고관리</div>
                <ul class="snb-menu">
                    <li class="active"><a href="./stock.jsp">재고</a></li>
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
=======
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
					<li><a href="./BOM">BOM</a></li>
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
					<li class="active"><a href="./stock.jsp">재고</a></li>
					<li><a href="./product.jsp">완제품</a></li>
					<li><a href="./item.jsp">자재</a></li>
				</ul>
			</div>

			<div class="snb-section">
				<div class="snb-title">품질관리</div>
				<ul class="snb-menu">
					<li><a href="qualityList">품질<span class="menu-badge">2</span></a></li>
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
>>>>>>> 05ba314 (모든 사이트 연결)


        <main class="content">
            
            <div class="page-header">
                <div class="page-header-title">
                    <h1>재고관리</h1>
                    <p>전체 재고 현황 및 LOT 정보를 확인하고 관리합니다.</p>
                </div>
            </div>

            <div class="card">
                <div style="margin-bottom: 20px;">
                    <button type="button" class="btn-blue" onclick="openAddModal()">+ 신규 재고 등록</button>
                </div>

                <div style="display:flex; gap:16px;">
                    <div style="flex:3">
                        <input type="text" id="searchKeyword" class="input-field" placeholder="LOT 번호 입력" value="${param.keyword}">
                    </div>
                    <button type="button" class="btn-blue" onclick="doSearch()">조회</button>
                </div>
            </div>

            <div class="main-grid" style="display: grid; grid-template-columns: 7.5fr 2.5fr; gap: 20px; margin-bottom: 40px;">
                
                <div class="card">
                    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:15px;">
                        <h2>재고 목록</h2>
                        <button type="button" class="btn-white">삭제</button>
                    </div>
                    
                    <table>
                        <thead>
                            <tr>
                                <th>선택</th>
                                <th>LOT 번호</th>
                                <th>입고수량</th>
                                <th>출고수량</th>
                                <th>현재고</th>
                                <th>안전재고</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty list}">
                                <tr><td colspan="6" style="padding:40px; color:#94a3b8; text-align:center;">조회된 재고 데이터가 없습니다.</td></tr>
                            </c:if>
                            
                            <c:forEach var="s" items="${list}">
                                <tr>
                                    <td><input type="checkbox"></td>
                                    <td style="font-weight:600;">${s.lot}</td>
                                    <td>${s.in_qty}</td>
                                    <td>${s.out_qty}</td>
                                    <td class="${s.current_qty < s.safe_qty ? 'qty-alert' : ''}">
                                        ${s.current_qty}
                                    </td>
                                    <td>${s.safe_qty}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="card">
                    <h2>요약 / 상태</h2>
                    <div style="display:flex; justify-content:space-between; padding:15px 0; border-bottom:1px solid #f1f5f9;">
                        <span>부족 항목</span>
                        <span style="color:#e53e3e; font-weight:700;">2건</span>
                    </div>
                    <p style="font-size:13px; color:#64748b; margin-top:15px;">안전재고 관리가 필요합니다.</p>
                </div>

            </div>
        </main>
    </div>


    <div id="stockModal" class="modal">
        <div class="modal-content">
            <div class="modal-header" style="padding:20px; border-bottom:1px solid #eee; display:flex; justify-content:space-between;">
                <h3>신규 재고 등록</h3>
                <span onclick="closeModal()" style="cursor:pointer; font-size:24px; color:#999;">&times;</span>
            </div>
            
            <div class="modal-body" style="padding:24px;">
                <div style="margin-bottom:15px;">
                    <label style="display:block; font-weight:600; margin-bottom:5px;">LOT 번호</label>
                    <input type="text" class="input-field" style="width:100%; box-sizing:border-box;">
                </div>
                <div style="margin-bottom:15px;">
                    <label style="display:block; font-weight:600; margin-bottom:5px;">입고수량</label>
                    <input type="number" class="input-field" style="width:100%; box-sizing:border-box;">
                </div>
            </div>

            <div class="modal-footer" style="padding:15px; text-align:right; background:#f8fafc; border-top:1px solid #eee;">
                <button type="button" class="btn-white" onclick="closeModal()">취소</button>
                <button type="button" class="btn-blue" style="width:100px;">등록</button>
            </div>
        </div>
    </div>

</body>
</html>