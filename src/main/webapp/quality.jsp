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

        .content > .page-header, 
        .content > .card, 
        .content > .main-grid { 
            margin-left: 24px; 
            margin-right: 24px; 
        }

        .card { 
            background: #fff; 
            border-radius: 12px; 
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05); 
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
            margin-bottom: 40px; 
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

        tbody tr { cursor: pointer; }
        tbody tr:hover { background-color: #f8fafc; }

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

        /* Modal Styles */
        .modal { display: none; }
        .modal.show { 
            display: flex; 
            position: fixed; 
            top: 0; left: 0; 
            width: 100%; height: 100%; 
            justify-content: center; 
            align-items: center; 
            background: rgba(0, 0, 0, 0.4); 
            z-index: 1000; 
        }

        .modal-box { 
            background: #ffffff; 
            width: 550px; 
            border-radius: 16px; 
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1); 
            overflow: hidden; 
        }

        .modal-header { 
            display: flex; 
            justify-content: space-between; 
            align-items: center; 
            padding: 16px 20px; 
            border-bottom: 1px solid #eee; 
        }

        .modal-header h3 { font-size: 18px; font-weight: 700; color: #333; margin: 0; }
        .modal-body { padding: 24px; }
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
        .modal-footer { display: flex; justify-content: center; gap: 12px; padding: 20px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; font-size: 14px; font-weight: 600; color: #555; margin-bottom: 8px; }
    </style>

    <script>
        const QUALITY_PATH = "${pageContext.request.contextPath}/quality";

        // 페이지 로드 시 날짜 설정
        window.addEventListener('load', () => {
            const dateEl = document.querySelector('.header-chip.date');
            if (dateEl) { 
                const now = new Date();
                dateEl.textContent = now.toISOString().split('T')[0]; 
            }
        });

        function stopProp(e) { 
            e.stopPropagation(); 
        }
        
        // 체크박스 전체 토글
        function toggleAllCheckboxes() {
            const checkboxes = document.querySelectorAll('input[name="chk_quality"]');
            if (checkboxes.length === 0) return;
            
            const allChecked = Array.from(checkboxes).every(cb => cb.checked);
            checkboxes.forEach((cb) => { 
                cb.checked = !allChecked; 
            });
        }

        // 검색
        function doSearch() {
            const keyword = document.getElementById('searchKeyword').value.trim();
            const status = document.getElementById('searchStatus').value;
            location.href = QUALITY_PATH + "?p=1&keyword=" + encodeURIComponent(keyword) + "&status=" + encodeURIComponent(status);
        }

        // 선택 삭제
        function deleteSelected() {
            const checked = document.querySelectorAll('input[name="chk_quality"]:checked');
            if (checked.length === 0) { 
                alert("삭제할 항목을 선택해주세요."); 
                return; 
            }
            
            if (confirm("선택한 항목을 삭제하시겠습니까?")) {
                let codes = Array.from(checked).map(cb => cb.value).join(",");
                location.href = QUALITY_PATH + "/delete?codes=" + codes;
            }
        }

        // 등록 모달 열기
        function openAddModal() {
            document.getElementById("modalTitle").innerText = "신규 품질검사 등록";
            document.getElementById("modalSubmitBtn").innerText = "등록하기";
            document.getElementById("qualityForm").reset();
            document.getElementById("origin_code").value = "";
            document.getElementById("modal_code").readOnly = false;
            document.getElementById("modalSubmitBtn").onclick = function() { sendData("add"); };
            document.getElementById("commonModal").classList.add("show");
        }

        // 수정 모달 열기
        function openEditModal(code, itemCode, itemName, type, qty, status, inspector, date, remarks) {
            document.getElementById("modalTitle").innerText = "품질검사 수정";
            document.getElementById("modalSubmitBtn").innerText = "수정하기";
            
            document.getElementById("origin_code").value = code;
            document.getElementById("modal_code").value = code;
            document.getElementById("modal_code").readOnly = true;
            
            document.getElementById("modal_item_code").value = itemCode;
            document.getElementById("modal_item_name").value = itemName;
            document.getElementById("modal_type").value = type;
            document.getElementById("modal_qty").value = qty.toString().replace(/,/g, '');
            document.getElementById("modal_status").value = status;
            document.getElementById("modal_inspector").value = inspector;
            document.getElementById("modal_date").value = date;
            document.getElementById("modal_remarks").value = remarks;
            
            document.getElementById("modalSubmitBtn").onclick = function() { sendData("update"); };
            document.getElementById("commonModal").classList.add("show");
        }

        // 품목명 조회 AJAX
        function fetchItemName() {
            const itemCode = document.getElementById("modal_item_code").value.trim();
            if (!itemCode) {
                document.getElementById("modal_item_name").value = "";
                return;
            }

            fetch(`${pageContext.request.contextPath}/item/getOne?item_code=` + itemCode)
                .then(response => {
                    if(!response.ok) throw new Error("서버 응답 오류");
                    return response.json();
                })
                .then(data => {
                    if(data && data.item_name) {
                        document.getElementById("modal_item_name").value = data.item_name;
                    } else {
                        document.getElementById("modal_item_name").value = "품목 정보 없음";
                    }
                })
                .catch(err => {
                    console.error("조회 실패:", err);
                    document.getElementById("modal_item_name").value = "";
                });
        }

        // 데이터 전송
        function sendData(mode) {
            const code = document.getElementById("modal_code").value.trim();
            const itemCode = document.getElementById("modal_item_code").value.trim();

            if(!code) { alert("검사번호를 입력해주세요."); return; }
            if(!itemCode) { alert("품목코드를 입력해주세요."); return; }
            
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = (mode === "add") ? QUALITY_PATH + "/add" : QUALITY_PATH + "/update";

            const params = {
                origin_code: document.getElementById("origin_code").value,
                quality_code: code,
                item_code: itemCode,
                inspect_type: document.getElementById("modal_type").value.trim(),
                inspect_qty: document.getElementById("modal_qty").value.trim(),
                qc_status: document.getElementById("modal_status").value,
                inspector: document.getElementById("modal_inspector").value.trim(),
                inspect_date: document.getElementById("modal_date").value,
                remarks: document.getElementById("modal_remarks").value.trim()
            };

            for (const key in params) {
                const hiddenField = document.createElement('input');
                hiddenField.type = 'hidden';
                hiddenField.name = key;
                hiddenField.value = params[key];
                form.appendChild(hiddenField);
            }

            document.body.appendChild(form);
            form.submit();
        }

        function closeModal() { 
            document.getElementById("commonModal").classList.remove("show"); 
        }
        
        function goPage(pageNum) {
            const keyword = document.getElementById('searchKeyword').value;
            const status = document.getElementById('searchStatus').value;
            location.href = QUALITY_PATH + "?p=" + pageNum + "&keyword=" + encodeURIComponent(keyword) + "&status=" + encodeURIComponent(status);
        }
    </script>
</head>

<body>
    <header class="header">
        <div class="header-left">
            <a href="./index" class="logo">
                <span class="logo-mark">AM</span>
                <span>AUTO MES</span>
            </a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>

        <div class="header-right">
            <div class="header-chip date"></div>
            <div class="header-chip">${dto.user_name}님</div>
            <button class="btn logout-btn" onclick="location.href='./logout'">로그아웃</button>
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
                    <li><a href="./item">자재</a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">품질관리</div>
                <ul class="snb-menu">
                    <li class="active"><a href="/slowstarter/quality">품질</a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">시스템</div>
                <ul class="snb-menu">
                    <li><a href="./board">게시판</a></li>
                    <li><a href="./user">사용자관리</a></li>
                </ul>
            </div>
        </aside>

        <div class="snb-overlay" id="snbOverlay"></div>
        
        <main class="content">
            <div class="page-header">
                <div class="page-header-title">
                    <h1>품질관리</h1>
                    <p>검사 결과 정보를 확인하고 상태를 관리합니다.</p>
                </div>
                <button type="button" class="btn-blue" onclick="openAddModal()">신규 검사 등록</button>
            </div>

            <div class="card">
                <div class="search-container">
                    <div style="flex: 2">
                        <input type="text" id="searchKeyword" class="input-field" placeholder="검사번호 또는 품목코드 입력" value="${param.keyword}">
                    </div>
                    <div style="flex: 1">
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
                    <div class="section-header" style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
                        <h2>품질 목록</h2>
                        <button type="button" class="btn-delete-custom" onclick="deleteSelected()">삭제</button>
                    </div>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th onclick="toggleAllCheckboxes()" style="cursor:pointer;">선택</th>
                                    <th>검사번호</th>
                                    <th>품목코드</th>
                                    <th>품목명</th>
                                    <th>유형</th>
                                    <th>검사수량</th>
                                    <th>상태</th>
                                    <th>검사자</th>
                                    <th>검사일자</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="m" items="${list}">
                                    <fmt:formatDate var="fmtDate" value="${m.inspect_date}" pattern="yyyy-MM-dd" />
                                    <tr onclick="openEditModal('${m.quality_code}', '${m.item_code}', '${m.item_name}', '${m.inspect_type}', '${m.inspect_qty}', '${m.qc_status}', '${m.inspector}', '${fmtDate}', '${m.remarks}')">
                                        <td onclick="stopProp(event)">
                                            <input type="checkbox" name="chk_quality" value="${m.quality_code}">
                                        </td>
                                        <td style="font-weight: 600;">${m.quality_code}</td>
                                        <td>${m.item_code}</td>
                                        <td>${m.item_name}</td>
                                        <td>${m.inspect_type}</td>
                                        <td><fmt:formatNumber value="${m.inspect_qty}" /></td>
                                        <td>
                                            <span class="status-badge status-${m.qc_status}">${m.qc_status}</span>
                                        </td>
                                        <td>${m.inspector}</td>
                                        <td>${fmtDate}</td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty list}">
                                    <tr>
                                        <td colspan="9" style="text-align:center; padding:30px;">데이터가 없습니다.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                    <div class="pagination">
                        <c:forEach var="i" begin="1" end="5">
                            <a onclick="goPage(${i})" class="${(param.p == i || (empty param.p && i == 1)) ? 'active' : ''}">${i}</a>
                        </c:forEach>
                    </div>
                </div>

                <div class="card">
                    <div class="section-header">
                        <h2>요약 / 상태</h2>
                    </div>
                    <div class="summary-content">
                        <div style="display: flex; justify-content: space-between; padding: 15px 0; border-bottom: 1px solid #f1f5f9;">
                            <span>재검 건수</span>
                            <span style="color: #b7791f; font-weight: 700;">${recheckCount != null ? recheckCount : 0}건</span>
                        </div>
                        <div style="display: flex; justify-content: space-between; padding: 15px 0;">
                            <span>품질 상태</span>
                            <span style="color: #38a169; font-weight: 700;">양호</span>
                        </div>
                    </div>
                </div>
            </div>

            <div id="commonModal" class="modal">
                <div class="modal-box">
                    <form id="qualityForm" onsubmit="return false;">
                        <div class="modal-header">
                            <h3 id="modalTitle">품질검사 정보</h3>
                            <span onclick="closeModal()" style="cursor: pointer; font-size: 20px; color: #999;">&times;</span>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" id="origin_code">
                            <div class="form-grid">
                                <div class="form-group">
                                    <label>검사번호</label> 
                                    <input type="text" id="modal_code" class="input-field">
                                </div>
                                <div class="form-group">
                                    <label>품목코드</label> 
                                    <input type="text" id="modal_item_code" class="input-field" onblur="fetchItemName()" placeholder="코드 입력 시 자동조회">
                                </div>
                                <div class="form-group">
                                    <label>품목명</label> 
                                    <input type="text" id="modal_item_name" class="input-field" readonly style="background-color: #f8fafc;">
                                </div>
                                <div class="form-group">
                                    <label>검사유형</label> 
                                    <input type="text" id="modal_type" class="input-field">
                                </div>
                                <div class="form-group">
                                    <label>검사수량</label> 
                                    <input type="number" id="modal_qty" class="input-field">
                                </div>
                                <div class="form-group">
                                    <label>검사자</label> 
                                    <input type="text" id="modal_inspector" class="input-field">
                                </div>
                                <div class="form-group">
                                    <label>상태</label> 
                                    <select id="modal_status" class="input-field">
                                        <option value="합격">합격</option>
                                        <option value="재검">재검</option>
                                        <option value="불합격">불합격</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>검사일자</label> 
                                    <input type="date" id="modal_date" class="input-field">
                                </div>
                            </div>
                            <div class="form-group" style="margin-top:10px;">
                                <label>비고</label> 
                                <textarea id="modal_remarks" class="input-field" style="height:60px; resize:none;"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn-white" style="width: 80px;" onclick="closeModal()">취소</button>
                            <button type="button" class="btn-blue" style="width: 120px;" id="modalSubmitBtn">저장하기</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</body>
</html>