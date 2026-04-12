<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>AUTO MES | 품질관리</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/common.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/page.css" />
    <script src="${pageContext.request.contextPath}/asset/js/common.js" defer></script>

    <style>
        /* 기본 레이아웃 설정 */
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

        /* 메인 콘텐츠 영역 수정 */
        .content { 
            padding: 24px 0 0 0; /* 좌우 패딩을 0으로 설정하여 푸터가 끝까지 가도록 함 */
            background-color: #f4f7fa; 
            flex: 1; 
            display: flex;
            flex-direction: column;
            width: 100%;
        }

        /* 상단 요소들의 간격을 유지하기 위해 카드와 헤더에 마진 추가 */
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

        .page-header { 
            display: flex; 
            justify-content: space-between; 
            align-items: center; 
            margin-bottom: 20px; 
        }

        .search-container { 
            display: flex; 
            align-items: center; 
            gap: 16px; 
        }

        .input-field { 
            width: 100%; 
            padding: 10px 14px; 
            border: 1px solid #e2e8f0; 
            border-radius: 8px; 
            font-size: 14px; 
            box-sizing: border-box; 
        }

        .main-grid { 
            display: grid; 
            grid-template-columns: 7.5fr 2.5fr; 
            gap: 20px; 
            margin-bottom: 40px; /* 푸터와의 간격 확보 */
        }

        .btn-delete-custom { 
            padding: 8px 16px; 
            background-color: #ffffff; 
            color: #64748b; 
            border: 1px solid #e2e8f0; 
            border-radius: 6px; 
            font-size: 13px; 
            font-weight: 600; 
            cursor: pointer; 
        }

        table { 
            width: 100%; 
            border-collapse: collapse; 
        }

        th { 
            background-color: #f8fafc; 
            padding: 12px; 
            border-bottom: 1px solid #e2e8f0; 
            font-size: 13px; 
            text-align: center; 
            color: #64748b; 
        }

        td { 
            padding: 14px 12px; 
            border-bottom: 1px solid #f1f5f9; 
            text-align: center; 
            font-size: 14px; 
            color: #334155; 
        }

        tbody tr { 
            cursor: pointer; 
        }
        
        tbody tr:hover { 
            background-color: #f8fafc; 
        }

        .status-badge { 
            padding: 4px 10px; 
            border-radius: 20px; 
            font-size: 12px; 
            font-weight: 600; 
        }
        
        .status-재검 { background: #fefcbf; color: #b7791f; }
        .status-합격 { background: #f0fff4; color: #38a169; }
        .status-불합격 { background: #fff5f5; color: #e53e3e; }

        .pagination { 
            display: flex; 
            justify-content: center; 
            align-items: center; 
            margin-top: 25px; 
            gap: 8px; 
        }
        
        .pagination a { 
            padding: 10px 15px; 
            border: 1px solid #dee2e6; 
            text-decoration: none; 
            color: #495057; 
            border-radius: 5px; 
            cursor: pointer; 
        }
        
        .pagination a.active { 
            background-color: #0d6efd; 
            color: white; 
            border-color: #0d6efd; 
            font-weight: bold; 
        }

        .btn-blue { 
            background: #3182ce; 
            color: #fff; 
            border: none; 
            padding: 10px 20px; 
            border-radius: 8px; 
            cursor: pointer; 
        }
        
        .btn-white { 
            background: #fff; 
            border: 1px solid #e2e8f0; 
            padding: 10px 20px; 
            border-radius: 8px; 
            cursor: pointer; 
        }

        /* 모달 스타일 */
        .modal { display: none; }
        
        .modal.show { 
            display: flex; 
            position: fixed; 
            top: 0; left: 0; width: 100%; height: 100%; 
            justify-content: center; align-items: center; 
            background: rgba(0, 0, 0, 0.4); z-index: 1000; 
        }
        
        .modal-box { 
            background: #ffffff; 
            width: 480px; 
            border-radius: 16px; 
            box-shadow: 0 10px 25px rgba(0,0,0,0.1); 
            overflow: hidden; 
        }
        
        .modal-header { 
            display: flex; 
            justify-content: space-between; 
            align-items: center; 
            padding: 16px 20px; 
            border-bottom: 1px solid #eee; 
        }
        
        .modal-header h3 { 
            font-size: 18px; 
            font-weight: 700; 
            color: #333; 
            margin: 0; 
        }
        
        .modal-body { padding: 24px; }
        
        .modal-footer { 
            display: flex; 
            justify-content: center; 
            gap: 12px; 
            padding: 20px; 
        }
        
        .form-group { margin-bottom: 20px; }
        
        .form-group label { 
            display: block; 
            font-size: 14px; 
            font-weight: 600; 
            color: #555; 
            margin-bottom: 8px; 
        }

        /* 푸터 스타일 수정 */
        .footer { 
            width: 100%; 
            background-color: #1a2332; 
            color: #94a3b8; 
            padding: 60px 24px; /* 좌우 패딩을 카드 마진과 맞춰 24px로 설정 */
            font-size: 12px; 
            margin-top: auto; 
            border-radius: 0; /* 라운드 제거하여 창 끝에 붙임 */
            box-sizing: border-box;
        }

        .footer-container {
            max-width: 100%;
            margin: 0;
        }

        .footer-top { 
            display: flex; 
            justify-content: space-between; 
            align-items: center; 
            margin-bottom: 25px; 
            padding-bottom: 15px;
            border-bottom: 1px solid rgba(255,255,255,0.1);
        }

        .footer-logo { 
            font-size: 18px; 
            font-weight: 800; 
            color: #f8fafc; 
        }

        .footer-links { 
            display: flex; 
            gap: 15px; 
        }

        .footer-links a { 
            color: #cbd5e1; 
            text-decoration: none; 
        }
        
        .footer-info { 
            display: flex; 
            flex-direction: column; 
            gap: 10px; 
            margin-bottom: 15px; 
            line-height: 1.6; 
        }

        .info-row { 
            display: flex; 
            flex-wrap: wrap; 
            gap: 20px; 
        }

        .info-item b { 
            color: #cbd5e1; 
            margin-right: 4px; 
        }

        .copyright { 
            margin-top: 20px; 
            color: #475569; 
            font-size: 11px; 
        }
    </style>

    <script>
        window.addEventListener('load', () => {
            const dateEl = document.querySelector('.header-chip:first-child');
            const now = new Date();
            const arr = now.toISOString().split('T');
            if (dateEl) { dateEl.textContent = arr[0]; }
        });

        function stopProp(e) { e.stopPropagation(); }
        function toggleAllByText() {
            const checkboxes = document.querySelectorAll('input[name="chk_quality"]');
            const allChecked = Array.from(checkboxes).every(cb => cb.checked);
            checkboxes.forEach((cb) => { cb.checked = !allChecked; });
        }

        function doSearch() {
            const keyword = document.getElementById('searchKeyword').value.trim();
            const status = document.getElementById('searchStatus').value;
            const path = "${pageContext.request.contextPath}/qualityList";
            location.href = path + "?p=1&keyword=" + encodeURIComponent(keyword) + "&status=" + encodeURIComponent(status);
        }

        function deleteSelected() {
            const checked = document.querySelectorAll('input[name="chk_quality"]:checked');
            if (checked.length === 0) { alert("삭제할 항목을 선택해주세요."); return; }
            if (confirm("선택한 항목을 삭제하시겠습니까?")) {
                let codes = Array.from(checked).map(cb => cb.value).join(",");
                location.href = "${pageContext.request.contextPath}/qualityDelete?codes=" + codes;
            }
        }

        function openEditModal(code, qty, status, date) {
            document.getElementById("modalTitle").innerText = "품질검사 수정";
            document.getElementById("modalSubmitBtn").innerText = "수정하기";
            document.getElementById("origin_code").value = code;
            document.getElementById("modal_code").value = code;
            document.getElementById("modal_qty").value = qty.toString().replace(/,/g, '');
            document.getElementById("modal_status").value = status;
            document.getElementById("modal_date").value = date;
            document.getElementById("modalSubmitBtn").onclick = function() { sendUpdateData(); };
            document.getElementById("commonModal").classList.add("show");
        }

        function sendUpdateData() {
            const originCode = document.getElementById("origin_code").value;
            const newCode = document.getElementById("modal_code").value.trim();
            const qty = document.getElementById("modal_qty").value.trim();
            const status = document.getElementById("modal_status").value;
            const date = document.getElementById("modal_date").value;
            if(!newCode) { alert("검사번호를 입력해주세요."); return; }
            location.href = "${pageContext.request.contextPath}/qualityUpdate" + 
                            "?origin_code=" + encodeURIComponent(originCode) + 
                            "&quality_code=" + encodeURIComponent(newCode) + 
                            "&inspect_qty=" + qty + 
                            "&qc_status=" + encodeURIComponent(status) + 
                            "&inspect_date=" + date;
        }

        function closeModal() { document.getElementById("commonModal").classList.remove("show"); }
        function goPage(pageNum) {
            const keyword = document.getElementById('searchKeyword').value;
            const status = document.getElementById('searchStatus').value;
            location.href = "${pageContext.request.contextPath}/qualityList?p=" + pageNum + 
                            "&keyword=" + encodeURIComponent(keyword) + 
                            "&status=" + encodeURIComponent(status);
        }
    </script>
</head>

<body>
    <header class="header">
        <div class="header-left">
            <a href="./index.html" class="logo">
                <span class="logo-mark">AM</span><span>AUTO MES</span>
            </a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip"></div> 
            <div class="header-chip">생산 1라인 가동중</div>
            <div class="header-chip">관리자</div>
        </div>
    </header>

    <div class="layout">
        <aside class="snb" id="snb">
            <div class="snb-section">
                <div class="snb-title">MAIN</div>
                <ul class="snb-menu"><li><a href="./index.html">대시보드</a></li></ul>
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
                    <li><a href="./workorder.html">작업지시 <span class="menu-badge">4</span></a></li>
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
            <div class="snb-section active">
                <div class="snb-title">품질관리</div>
                <ul class="snb-menu">
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/qualityList">품질 <span class="menu-badge">2</span></a>
                    </li>
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
                    <li><a href="./user.html">사용자관리</a></li>
                    <li><a href="./mypage.html">마이페이지</a></li>
                </ul>
            </div>
        </aside>

        <main class="content">
            <div class="page-header">
                <div class="page-header-title">
                    <h1>품질관리</h1>
                    <p>검사 결과 정보를 확인하고 상태를 관리합니다.</p>
                </div>
            </div>

            <div class="card">
                <div class="search-container">
                    <div style="flex:2">
                        <input type="text" id="searchKeyword" class="input-field" placeholder="검사번호 입력" value="${param.keyword}">
                    </div>
                    <div style="flex:1">
                        <select id="searchStatus" class="input-field">
                            <option value="" ${empty param.status ? 'selected' : ''}>전체 상태</option>
                            <option value="합격" ${param.status == '합격' ? 'selected' : ''}>합격</option>
                            <option value="재검" ${param.status == '재검' ? 'selected' : ''}>재검</option>
                            <option value="불합격" ${param.status == '불합격' ? 'selected' : ''}>불합격</option>
                        </select>
                    </div>
                    <button type="button" class="btn-blue" onclick="doSearch()">조회</button>
                </div>
            </div>

            <div class="main-grid">
                <div class="card">
                    <div class="section-header" style="display:flex; justify-content:space-between; align-items:center; margin-bottom:15px;">
                        <h2>품질 목록</h2>
                        <button type="button" class="btn-delete-custom" onclick="deleteSelected()">삭제</button>
                    </div>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th onclick="toggleAllByText()"><span class="select-label">선택</span></th>
                                    <th>검사번호</th>
                                    <th>검사수량</th>
                                    <th>상태</th>
                                    <th>검사일자</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="m" items="${list}">
                                    <fmt:formatDate var="fmtDate" value="${m.inspect_date}" pattern="yyyy-MM-dd"/>
                                    <tr onclick="openEditModal('${m.quality_code}', '${m.inspect_qty}', '${m.qc_status}', '${fmtDate}')">
                                        <td onclick="stopProp(event)">
                                            <input type="checkbox" name="chk_quality" value="${m.quality_code}">
                                        </td>
                                        <td style="font-weight:600;">${m.quality_code}</td>
                                        <td><fmt:formatNumber value="${m.inspect_qty}" /></td>
                                        <td><span class="status-badge status-${m.qc_status}">${m.qc_status}</span></td>
                                        <td>${fmtDate}</td>
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
                            <span>재검 건수</span><span style="color:#b7791f; font-weight:700;">1건</span>
                        </div>
                        <div style="display:flex; justify-content:space-between; padding:15px 0;">
                            <span>합격률</span><span style="color:#38a169; font-weight:700;">양호</span>
                        </div>
                    </div>
                </div>
            </div>
            
            <footer class="footer">
                <div class="footer-container">
                    <div class="footer-top">
                        <div class="footer-logo">AUTO MES</div>
                        <div class="footer-links">
                            <a href="#">개인정보 처리방침</a>
                            <a href="#">이용약관</a>
                            <a href="#">사이트맵</a>
                            <a href="#">고객센터</a>
                        </div>
                    </div>
                    
                    <div class="footer-info">
                        <div class="info-row">
                            <div class="info-item"><b>상호명</b> (주) AUTO</div>
                            <div class="info-item"><b>대표자</b> 이용상</div>
                            <div class="info-item"><b>사업자등록번호</b> 123-45-67890</div>
                        </div>
                        <div class="info-row">
                            <div class="info-item"><b>주소</b>충남 천안시 동남구 대흥로 215 7층, 8층 </div>
                            <div class="info-item"><b>대표전화</b> 041-561-1122</div>
                            <div class="info-item"><b>이메일</b> cungho2086@gmail.com</div>
                        </div>
                    </div>
                    
                    <p class="copyright">Copyright © 2026 AUTO MES. All rights reserved.</p>
                </div>
            </footer>
        </main>
    </div> 

    <div id="commonModal" class="modal">
        <div class="modal-box">
            <div class="modal-header">
                <h3 id="modalTitle">품질검사 수정</h3> 
                <span onclick="closeModal()" style="cursor:pointer; font-size:20px; color:#999;">&times;</span>
            </div>
            <div class="modal-body">
                <input type="hidden" id="origin_code">
                <div class="form-group">
                    <label>검사번호</label>
                    <input type="text" id="modal_code" name="quality_code" class="input-field">
                </div>
                <div class="form-group">
                    <label>검사수량</label>
                    <input type="number" id="modal_qty" name="inspect_qty" class="input-field">
                </div>
                <div class="form-group">
                    <label>상태</label>
                    <select id="modal_status" name="qc_status" class="input-field">
                        <option value="합격">합격</option>
                        <option value="재검">재검</option>
                        <option value="불합격">불합격</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>검사일자</label>
                    <input type="date" id="modal_date" name="inspect_date" class="input-field">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn-white" style="width:80px;" onclick="closeModal()">취소</button>
                <button type="button" class="btn-blue" style="width:120px;" id="modalSubmitBtn">수정하기</button>
            </div>
        </div>
    </div>
</body>
</html>