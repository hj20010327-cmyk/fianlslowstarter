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
        /* 품질관리에서 사용하신 스타일 그대로 적용 */
        body { display: flex; flex-direction: column; min-height: 100vh; margin: 0; }
        .layout { display: flex; flex: 1; }
        .content { 
            padding: 24px 0 0 0; 
            background-color: #f4f7fa; 
            flex: 1; 
            display: flex;
            flex-direction: column;
            width: 100%;
        }
        .content > .page-header, .content > .card, .content > .main-grid {
            margin-left: 24px; margin-right: 24px;
        }
        .card { 
            background: #fff; border-radius: 12px; 
            box-shadow: 0 1px 3px rgba(0,0,0,0.05); padding: 24px; margin-bottom: 20px; 
        }
        .page-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .search-container { display: flex; align-items: center; gap: 16px; }
        .input-field { width: 100%; padding: 10px 14px; border: 1px solid #e2e8f0; border-radius: 8px; font-size: 14px; box-sizing: border-box; }
        .main-grid { display: grid; grid-template-columns: 7.5fr 2.5fr; gap: 20px; margin-bottom: 40px; }
        .btn-delete-custom { padding: 8px 16px; background-color: #ffffff; color: #64748b; border: 1px solid #e2e8f0; border-radius: 6px; font-size: 13px; font-weight: 600; cursor: pointer; }
        table { width: 100%; border-collapse: collapse; }
        th { background-color: #f8fafc; padding: 12px; border-bottom: 1px solid #e2e8f0; font-size: 13px; text-align: center; color: #64748b; }
        td { padding: 14px 12px; border-bottom: 1px solid #f1f5f9; text-align: center; font-size: 14px; color: #334155; }
        tbody tr:hover { background-color: #f8fafc; }
        
        /* 재고관리용 수량 강조 스타일 */
        .qty-alert { color: #e53e3e; font-weight: bold; }
        
        .pagination { display: flex; justify-content: center; align-items: center; margin-top: 25px; gap: 8px; }
        .pagination a { padding: 10px 15px; border: 1px solid #dee2e6; text-decoration: none; color: #495057; border-radius: 5px; cursor: pointer; }
        .pagination a.active { background-color: #0d6efd; color: white; border-color: #0d6efd; font-weight: bold; }
        .btn-blue { background: #3182ce; color: #fff; border: none; padding: 10px 20px; border-radius: 8px; cursor: pointer; }
        
        .footer { width: 100%; background-color: #1a2332; color: #94a3b8; padding: 60px 24px; font-size: 12px; margin-top: auto; box-sizing: border-box; }
        .footer-top { display: flex; justify-content: space-between; align-items: center; margin-bottom: 25px; padding-bottom: 15px; border-bottom: 1px solid rgba(255,255,255,0.1); }
        .footer-logo { font-size: 18px; font-weight: 800; color: #f8fafc; }
        .info-row { display: flex; flex-wrap: wrap; gap: 20px; }
    </style>

    <script>
        // 상단 날짜 자동 표시
        window.addEventListener('load', () => {
            const dateEl = document.querySelector('.header-chip:first-child');
            if (dateEl) { dateEl.textContent = new Date().toISOString().split('T')[0]; }
        });

        // 재고 조회 (slowstarter 컨텍스트 포함)
        function doSearch() {
            const keyword = document.getElementById('searchKeyword').value.trim();
            location.href = "${pageContext.request.contextPath}/stockList?p=1&keyword=" + encodeURIComponent(keyword);
        }

        // 선택 삭제
        function deleteSelected() {
            const checked = document.querySelectorAll('input[name="chk_stock"]:checked');
            if (checked.length === 0) { alert("삭제할 항목을 선택해주세요."); return; }
            if (confirm("선택한 항목을 삭제하시겠습니까?")) {
                let codes = Array.from(checked).map(cb => cb.value).join(",");
                location.href = "${pageContext.request.contextPath}/stockDelete?codes=" + codes;
            }
        }

        // 페이징 이동
        function goPage(pageNum) {
            const keyword = document.getElementById('searchKeyword').value;
            location.href = "${pageContext.request.contextPath}/stockList?p=" + pageNum + "&keyword=" + encodeURIComponent(keyword);
        }

        // 전체 선택
        function toggleAll() {
            const checkboxes = document.querySelectorAll('input[name="chk_stock"]');
            const allChecked = Array.from(checkboxes).every(cb => cb.checked);
            checkboxes.forEach(cb => { cb.checked = !allChecked; });
        }
    </script>
</head>

<body>
    <header class="header">
        <div class="header-left">
            <a href="#" class="logo"><span class="logo-mark">AM</span><span>AUTO MES</span></a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip"></div> 
            <div class="header-chip">생산 1라인 가동중</div>
            <div class="header-chip">관리자</div>
        </div>
    </header>

    <div class="layout">
        <aside class="snb">
            <div class="snb-section">
                <div class="snb-title">MAIN</div>
                <ul class="snb-menu"><li><a href="#">대시보드</a></li></ul>
            </div>
            <div class="snb-section">
                <div class="snb-title">기준관리</div>
                <ul class="snb-menu"><li><a href="#">공정관리</a></li></ul>
            </div>
            <div class="snb-section active"> <div class="snb-title">재고관리</div>
                <ul class="snb-menu">
                    <li class="active"><a href="${pageContext.request.contextPath}/stockList">재고 <span class="menu-badge">HOT</span></a></li>
                    <li><a href="#">완제품</a></li>
                </ul>
            </div>
            <div class="snb-section">
                <div class="snb-title">품질관리</div>
                <ul class="snb-menu">
                    <li><a href="${pageContext.request.contextPath}/qualityList">품질 <span class="menu-badge">2</span></a></li>
                </ul>
            </div>
        </aside>

        <main class="content">
            <div class="page-header">
                <div class="page-header-title">
                    <h1>재고관리</h1>
                    <p>전체 재고 현황 및 LOT 정보를 확인하고 관리합니다.</p>
                </div>
            </div>

            <div class="card">
                <div class="search-container">
                    <div style="flex:3">
                        <input type="text" id="searchKeyword" class="input-field" placeholder="LOT 번호 입력" value="${param.keyword}">
                    </div>
                    <button type="button" class="btn-blue" onclick="doSearch()">조회</button>
                </div>
            </div>

            <div class="main-grid">
                <div class="card">
                    <div class="section-header" style="display:flex; justify-content:space-between; align-items:center; margin-bottom:15px;">
                        <h2>재고 목록</h2>
                        <button type="button" class="btn-delete-custom" onclick="deleteSelected()">삭제</button>
                    </div>
                    
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th onclick="toggleAll()" style="cursor:pointer;">선택</th>
                                    <th>LOT 번호</th>
                                    <th>입고수량</th>
                                    <th>출고수량</th>
                                    <th>현재고</th>
                                    <th>안전재고</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="s" items="${list}">
                                    <tr>
                                        <td><input type="checkbox" name="chk_stock" value="${s.stock_key}"></td>
                                        <td style="font-weight:600;">${s.lot}</td>
                                        <td><fmt:formatNumber value="${s.in_qty}" /></td>
                                        <td><fmt:formatNumber value="${s.out_qty}" /></td>
                                        <td class="${s.current_qty < s.safe_qty ? 'qty-alert' : ''}">
                                            <fmt:formatNumber value="${s.current_qty}" />
                                        </td>
                                        <td><fmt:formatNumber value="${s.safe_qty}" /></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="4">
                            <a onclick="goPage(${i})" class="${(param.p == i || (empty param.p && i == 1)) ? 'active' : ''}">${i}</a>
                        </c:forEach>
                    </div>
                </div>

                <div class="card">
                    <div class="section-header"><h2>요약 / 상태</h2></div>
                    <div class="summary-content">
                        <div style="display:flex; justify-content:space-between; padding:15px 0; border-bottom:1px solid #f1f5f9;">
                            <span>부족 항목</span><span style="color:#e53e3e; font-weight:700;">2건</span>
                        </div>
                    </div>
                </div>
            </div>

            <footer class="footer">
                <div class="footer-container">
                    <div class="footer-top">
                        <div class="footer-logo">AUTO MES</div>
                        <div class="footer-links">
                            <a href="#">개인정보 처리방침</a> <a href="#">이용약관</a> <a href="#">고객센터</a>
                        </div>
                    </div>
                    <div class="footer-info">
                        <div class="info-row">
                            <div class="info-item"><b>상호명</b> (주) AUTO</div>
                            <div class="info-item"><b>대표자</b> 이용상</div>
                            <div class="info-item"><b>사업자등록번호</b> 123-45-67890</div>
                        </div>
                        <div class="info-row">
                            <div class="info-item"><b>주소</b> 충남 천안시 동남구 대흥로 215 7층, 8층</div>
                            <div class="info-item"><b>대표전화</b> 041-561-1122</div>
                        </div>
                    </div>
                    <p class="copyright">Copyright © 2026 AUTO MES. All rights reserved.</p>
                </div>
            </footer>
        </main>
    </div>
</body>
</html>