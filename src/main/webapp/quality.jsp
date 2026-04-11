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
        .content { 
            padding: 24px; 
            background-color: #f4f7fa; 
            min-height: calc(100vh - 60px); 
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

        .header-buttons { display: flex; gap: 8px; }

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

        table { width: 100%; border-collapse: collapse; }

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

        tbody tr { cursor: pointer; }

        tbody tr:hover { background-color: #f8fafc; }

        .select-label { cursor: pointer; user-select: none; text-decoration: underline; }

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

        .btn-blue { background: #3182ce; color: #fff; border: none; padding: 10px 20px; border-radius: 8px; cursor: pointer; }
        .btn-white { background: #fff; border: 1px solid #e2e8f0; padding: 10px 20px; border-radius: 8px; cursor: pointer; }

        /* [수정] 모달 스타일 보정 (이미지 참고) */
        .modal { display: none; }
        .modal.show { 
            display: flex; 
            position: fixed; 
            top: 0; left: 0; width: 100%; height: 100%; 
            justify-content: center; align-items: center; 
            background: rgba(0, 0, 0, 0.4); 
            z-index: 1000; 
        }
        .modal-box { 
            background: #ffffff; 
            width: 480px; 
            border-radius: 16px; 
            box-shadow: 0 10px 25px rgba(0,0,0,0.1); 
            overflow: hidden; 
        }
        .modal-header { 
            display: flex; justify-content: space-between; align-items: center; 
            padding: 16px 20px; border-bottom: 1px solid #eee; 
        }
        .modal-header h3 { font-size: 18px; font-weight: 700; color: #333; margin: 0; }
        .modal-body { padding: 24px; }
        .modal-footer { 
            display: flex; justify-content: center; gap: 12px; 
            padding: 20px; border-top: none; 
        }
        .form-group { margin-bottom: 20px; }
        .form-group label { display: block; font-size: 14px; font-weight: 600; color: #555; margin-bottom: 8px; }

        /* [수정] 푸터 스타일 (이미지 60f75b 참고) */
        .footer {
            clear: both;
            width: 100%;
            background-color: #0f1a30; /* 어두운 네이비색 */
            color: #8fa0bc;
            padding: 30px 0;
            font-size: 13px;
        }
        .footer-container { width: 100%; max-width: 1400px; margin: 0 auto; padding: 0 40px; box-sizing: border-box; }
        .footer-top { display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px; }
        .footer-logo { font-size: 18px; font-weight: 800; color: #f8fafc; }
        .footer-links { display: flex; gap: 20px; align-items: center; }
        .footer-links a { color: #f8fafc; text-decoration: none; font-weight: 600; }
        .family-site-btn { border: 1px solid #334155; padding: 4px 12px; border-radius: 6px; font-size: 12px; background: transparent; color: #f8fafc; cursor: pointer; }
        .footer-body { font-size: 12px; color: #8fa0bc; }
        .info-row { display: flex; gap: 12px; align-items: center; margin-bottom: 5px; flex-wrap: wrap; }
        .info-row span b { color: #ccd6e6; font-weight: normal; }
        .bar { width: 1px; height: 10px; background-color: #334155; }
        .copyright { margin-top: 15px; color: #475569; text-align: center; }
    </style>

    <script>
        // [수정] 이미지 610da9 에러 해결 로직
        window.addEventListener('load', () => {
            const dateEl = document.querySelector('.header-chip:first-child'); // .date 대신 실제 날짜가 표시될 요소를 선택
            const now = new Date();
            const iso = now.toISOString();
            const arr = iso.split('T');
            
            console.log(arr[0]);
            if (dateEl) {
                dateEl.textContent = arr[0];
            }
        });

        function stopProp(e) { e.stopPropagation(); }

        function toggleAllByText() {
            const checkboxes = document.querySelectorAll('input[name="chk_quality"]');
            const allChecked = Array.from(checkboxes).every(cb => cb.checked);
            checkboxes.forEach((cb) => cb.checked = !allChecked);
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

        function openModal(mode, code = '', name = '', qty = '', status = '합격', date = '') {
            const modal = document.getElementById("commonModal");
            const title = document.getElementById("modalTitle");
            const submitBtn = document.getElementById("modalSubmitBtn");

            const pureQty = (qty && qty.toString()) ? qty.toString().replace(/,/g, '') : '';

            if(mode === 'edit') {
                title.innerText = "품질관리 수정"; 
                submitBtn.innerText = "수정하기";
                document.getElementById("modal_code").value = code;
                document.getElementById("modal_code").readOnly = true; 
                document.getElementById("modal_name").value = name;
                document.getElementById("modal_qty").value = pureQty;
                document.getElementById("modal_status").value = status;
                document.getElementById("modal_date").value = date;
                submitBtn.onclick = function() { sendData('update'); };
            } else {
                title.innerText = "reg"; // 이미지 5fb488 기준
                submitBtn.innerText = "저장하기";
                document.getElementById("modal_code").value = "";
                document.getElementById("modal_code").readOnly = false;
                document.getElementById("modal_name").value = "";
                document.getElementById("modal_qty").value = "";
                document.getElementById("modal_status").value = "합격";
                document.getElementById("modal_date").value = "";
                submitBtn.onclick = function() { sendData('insert'); };
            }
            modal.classList.add("show");
        }

        function sendData(type) {
            const code = document.getElementById("modal_code").value;
            const name = document.getElementById("modal_name").value;
            const qty = document.getElementById("modal_qty").value;
            const status = document.getElementById("modal_status").value;
            const date = document.getElementById("modal_date").value;
            if(!code || !name) { alert("번호와 품목명은 필수입니다."); return; }
            const targetUrl = type === 'insert' ? "/qualityInsert" : "/qualityUpdate";
            location.href = "${pageContext.request.contextPath}" + targetUrl + 
                            "?code=" + code + "&name=" + encodeURIComponent(name) + 
                            "&qty=" + qty + "&status=" + encodeURIComponent(status) + "&date=" + date;
        }

        function closeModal() {
            document.getElementById("commonModal").classList.remove("show");
        }

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
            <a href="./index.do" class="logo">
                <span class="logo-mark">AM</span><span>AUTO MES</span>
            </a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>

        <div class="header-right">
            <div class="header-chip"></div> <div class="header-chip">생산 1라인 가동중</div>
            <div class="header-chip">관리자</div>
        </div>
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
            <div class="snb-section">
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
                    <p>검사 결과와 불량 유형, 조치 현황을 관리합니다.</p>
                </div>
                <div class="header-buttons">
                    <button type="button" class="btn-blue" onclick="openModal('add')">신규 등록</button>
                </div>
            </div>

            <div class="card">
                <div class="search-container">
                    <div style="flex:2">
                        <input type="text" id="searchKeyword" class="input-field" placeholder="품목코드 또는 검사번호 입력" value="${param.keyword}">
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
                                    <th>품목명</th>
                                    <th>검사수량</th>
                                    <th>상태</th>
                                    <th>검사일자</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="m" items="${list}">
                                    <fmt:formatDate var="fmtDate" value="${m.inspect_date}" pattern="yyyy-MM-dd"/>
                                    <tr onclick="openModal('edit', '${m.quality_code}', '${m.item_name}', '${m.inspect_qty}', '${m.qc_status}', '${fmtDate}')">
                                        <td onclick="stopProp(event)">
                                            <input type="checkbox" name="chk_quality" value="${m.quality_code}">
                                        </td>
                                        <td style="font-weight:600;">${m.quality_code}</td>
                                        <td>${m.item_name}</td>
                                        <td><fmt:formatNumber value="${m.inspect_qty}" /></td>
                                        <td><span class="status-badge status-${m.qc_status}">${m.qc_status}</span></td>
                                        <td>${fmtDate}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="pagination">
                        <a onclick="goPage(1)">&laquo;</a>
                        <c:forEach var="i" begin="1" end="4">
                            <a onclick="goPage(${i})" class="${(param.p == i || (empty param.p && i == 1)) ? 'active' : ''}">${i}</a>
                        </c:forEach>
                        <a onclick="goPage(2)">&raquo;</a>
                    </div>
                </div>

                <div class="card">
                    <div class="section-header"><h2>요약 / 상태</h2></div>
                    <div class="summary-content">
                        <div style="display:flex; justify-content:space-between; padding:15px 0; border-bottom:1px solid #f1f5f9;">
                            <span>재검 건수</span><span style="color:#b7791f; font-weight:700;">1건</span>
                        </div>
                        <div style="display:flex; justify-content:space-between; padding:15px 0; border-bottom:1px solid #f1f5f9;">
                            <span>주요 불량</span><span style="color:#e53e3e; font-weight:700;">확인</span>
                        </div>
                        <div style="display:flex; justify-content:space-between; padding:15px 0;">
                            <span>합격률</span><span style="color:#38a169; font-weight:700;">양호</span>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <footer class="footer">
        <div class="footer-container">
            <div class="footer-top">
                <div class="footer-logo">HUMANJOBS</div>
                <nav class="footer-links">
                    <a href="#">개인정보 처리방침</a>
                    <a href="#">이용약관</a>
                    <a href="#">사이트맵</a>
                    <a href="#">고객센터</a>
                    <button class="family-site-btn">패밀리 사이트 ▾</button>
                </nav>
            </div>
            
            <div class="footer-body">
                <div class="info-row">
                    <span>(주)휴먼교육센터 채용지원 플랫폼</span>
                    <span class="bar"></span>
                    <span>관리자 <b>(주)휴먼교육센터</b></span>
                    <span class="bar"></span>
                    <span>대표전화 <b>1566-9564</b></span>
                    <span class="bar"></span>
                    <span>이메일 <b>humanec@naver.com</b></span>
                    <span class="bar"></span>
                    <span>사업자등록번호 <b>667-81-02135</b></span>
                </div>
                <p class="copyright">© 2025 HUMANJOBS. All rights reserved.</p>
            </div>
        </div>
    </footer>

    <div id="commonModal" class="modal">
        <div class="modal-box">
            <div class="modal-header">
                <h3 id="modalTitle">reg</h3>
                <span onclick="closeModal()" style="cursor:pointer; font-size:20px; color:#999;">&times;</span>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label>검사번호</label>
                    <input type="text" id="modal_code" name="quality_code" class="input-field" placeholder="번호를 입력하세요">
                </div>
                <div class="form-group">
                    <label>품목명</label>
                    <input type="text" id="modal_name" name="item_name" class="input-field" placeholder="품목명을 입력하세요">
                </div>
                <div class="form-group">
                    <label>검사수량</label>
                    <input type="number" id="modal_qty" name="inspect_qty" class="input-field" placeholder="수량을 입력하세요">
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
                <button type="button" class="btn-blue" style="width:120px;" id="modalSubmitBtn">저장하기</button>
            </div>
        </div>
    </div>
</body>
</html>