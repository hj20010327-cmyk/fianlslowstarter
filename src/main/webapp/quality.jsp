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
        /* [1] 페이지네이션 스타일 */
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

        /* [2] 모달창 레이아웃 스타일 */
        .modal { display: none; }

        .modal.show {
            display: flex;
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            justify-content: center;
            align-items: center;
            background: rgba(15, 23, 42, 0.45); 
            backdrop-filter: blur(6px);
            z-index: 10000;
        }

        .modal-box {
            background: #ffffff;
            width: 520px;
            max-width: 92%;
            border-radius: 20px; 
            box-shadow: 0 20px 60px rgba(0,0,0,0.15); 
            overflow: hidden;
            border: none;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 16px 20px;
            border-bottom: 1px solid #e2e8f0;
            background: #f8fafc;
        }

        .modal-body { 
            padding: 20px; 
            max-height: 70vh; 
            overflow-y: auto; 
        }

        .modal-footer {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            padding: 16px 20px;
            border-top: 1px solid #e2e8f0;
            background: #f9fafb;
        }

        /* [3] 폼 그리드 스타일 */
        .form-grid { 
            display: grid; 
            grid-template-columns: 1fr 1fr; 
            gap: 16px; 
        }

        .form-group { 
            display: flex; 
            flex-direction: column; 
            gap: 8px; 
        }

        .form-group label { 
            font-size: 14px; 
            font-weight: 600; 
            color: #475569; 
        }

        .form-group input, .form-group select {
            padding: 10px 14px;
            border: 1px solid #e2e8f0;
            border-radius: 10px;
            font-size: 14px;
            outline: none;
        }

        .btn-custom {
            background-color: #ffffff;
            color: #495057;
            border: 1px solid #dee2e6;
            padding: 6px 12px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: all 0.2s;
        }
    </style>

    <script>
        /* [A] 모달 제어 함수 */
        function openModal(title = "신규 등록") {
            document.getElementById("qualityForm").reset(); // 이전 입력값 초기화
            document.getElementById("modalTitle").innerText = title;
            document.getElementById("commonModal").classList.add("show");
        }

        function closeModal() {
            document.getElementById("commonModal").classList.remove("show");
        }

        /* [B] 비동기 데이터 저장 함수 (AJAX) */
        function submitForm() {
            const form = document.getElementById("qualityForm");
            
            // 유효성 체크 (빈값 확인)
            if(!form.quality_key.value || !form.item_name.value || !form.inspect_qty.value) {
                alert("모든 정보를 입력해주세요.");
                return;
            }

            // 1. 폼 데이터 가공
            const formData = new FormData(form);
            const params = new URLSearchParams(formData);

            // 2. 서버로 전송 (품질관리 등록 액션)
            fetch("qualityInsert", {
                method: "POST",
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: params
            })
            .then(response => {
                if (response.ok) {
                    alert("성공적으로 저장되었습니다.");
                    closeModal(); // 모달 닫기
                    location.reload(); // 리스트 갱신을 위해 새로고침
                } else {
                    alert("저장에 실패했습니다. (Error: " + response.status + ")");
                }
            })
            .catch(error => {
                console.error("Fetch Error:", error);
                alert("서버 통신 중 오류가 발생했습니다.");
            });
        }

        /* [C] 조회 및 검색 함수 */
        function searchQuality(page) {
            const searchCode = document.getElementById('searchCode').value;
            const searchName = document.getElementById('searchName').value;
            const status = document.getElementById('searchStatus').value;
            const p = page || 1; 

            location.href = "qualityList?page=" + p 
                          + "&searchCode=" + encodeURIComponent(searchCode) 
                          + "&searchName=" + encodeURIComponent(searchName) 
                          + "&status=" + status;
        }

        /* [D] 체크박스 및 삭제 제어 */
        let isAllChecked = false;
        function toggleAll() {
            isAllChecked = !isAllChecked;
            const chks = document.getElementsByName("chk");
            for (let i = 0; i < chks.length; i++) { 
                chks[i].checked = isAllChecked; 
            }
        }

        function deleteSelected() {
            const chks = document.getElementsByName("chk");
            let keys = [];
            for (let i = 0; i < chks.length; i++) { 
                if (chks[i].checked) { keys.push(chks[i].value); } 
            }
            if (keys.length === 0) { 
                alert("삭제할 항목을 선택해주세요."); 
                return; 
            }
            if (confirm("선택한 항목을 삭제하시겠습니까?")) { 
                location.href = "qualityDelete?quality_key=" + keys.join(","); 
            }
        }
    </script>
</head>

<body>
    <header class="header">
        <div class="header-left">
            <a href="./index.html" class="logo"><span class="logo-mark">AM</span><span>AUTO MES</span></a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip">2026-04-08</div>
            <div class="header-chip">생산 1라인 가동중</div>
            <div class="header-chip">관리자</div>
        </div>
    </header>

    <div class="layout">
        <aside class="snb" id="snb">
            <div class="snb-section">
                <div class="snb-title">MAIN</div>
                <ul class="snb-menu">
                    <li><a href="./index.html">대시보드</a></li>
                    <li><a href="./mypage.html">마이페이지</a></li>
                </ul>
            </div>
            <div class="snb-section">
                <div class="snb-title">MASTER</div>
                <ul class="snb-menu">
                    <li><a href="./product.html">제품관리</a></li>
                    <li><a href="./item.html">품목관리</a></li>
                    <li><a href="./bom.html">BOM관리</a></li>
                    <li><a href="./process.html">공정관리</a></li>
                </ul>
            </div>
            <div class="snb-section">
                <div class="snb-title">OPERATION</div>
                <ul class="snb-menu">
                    <li><a href="./workorder.html">작업지시 <span class="menu-badge">4</span></a></li>
                    <li><a href="./production.html">생산실적</a></li>
                    <li class="active"><a href="qualityList">품질관리 <span class="menu-badge">${p.total}</span></a></li>
                    <li><a href="./stock.html">재고관리</a></li>
                    <li><a href="./machine.html">설비</a></li>
                    <li><a href="./plan.html">생산계획</a></li>
                </ul>
            </div>
        </aside>

        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>품질관리</h1>
                    <p>검사 결과와 불량 유형, 조치 현황을 관리합니다.</p>
                </div>
                <div class="page-actions">
                    <button class="btn primary" type="button" onclick="openModal('품질관리 신규 등록')">신규 등록</button>
                </div>
            </div>

            <section class="card">
                <div class="section-title"><h2>검색 조건</h2></div>
                <div class="search-row">
                    <input class="input" type="text" id="searchCode" value="${searchCode}" placeholder="품목코드 및 검사번호 입력" />
                    <input class="input" type="text" id="searchName" value="${searchName}" placeholder="명칭 입력" />
                    <select class="select" id="searchStatus">
                        <option value="">전체 상태</option>
                        <option value="PASS" ${param.status == 'PASS' ? 'selected' : ''}>PASS (정상)</option>
                        <option value="FAIL" ${param.status == 'FAIL' ? 'selected' : ''}>FAIL (불량)</option>
                    </select>
                    <button class="btn primary" onclick="searchQuality(1)">조회</button>
                </div>
            </section>

            <section class="panel-grid">
                <div class="card">
                    <div class="section-title">
                        <h2>품질관리 목록</h2>
                        <div style="display: flex; gap: 8px;">
                            <button class="btn-custom" type="button">수정</button>
                            <button class="btn-custom" type="button" onclick="deleteSelected()">삭제</button>
                        </div>
                    </div>

                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th style="width: 60px; text-align: center; cursor: pointer;" onclick="toggleAll()">선택</th>
                                    <th>검사번호</th>
                                    <th>품목명</th>
                                    <th>검사수량</th>
                                    <th>검사일자</th>
                                    <th>상태</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="m" items="${list}">
                                    <tr>
                                        <td style="text-align:center;">
                                            <input type="checkbox" name="chk" value="${m.quality_key}">
                                        </td>
                                        <td>${m.quality_key}</td>
                                        <td>${m.item_name}</td>
                                        <td>${m.inspect_qty}</td>
                                        <td><fmt:formatDate value="${m.inspect_date}" pattern="yyyy-MM-dd"/></td>
                                        <td>
                                            <span class="badge ${m.qc_status == 'PASS' ? 'ok' : 'danger'}">
                                                ${m.qc_status}
                                            </span>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <c:if test="${not empty p}">
                        <div class="pagination">
                            <c:if test="${p.prev}">
                                <a href="javascript:searchQuality(${p.startPage - 1})">이전</a>
                            </c:if>
                            <c:forEach var="i" begin="${p.startPage}" end="${p.endPage}">
                                <a href="javascript:searchQuality(${i})" class="${p.curPage == i ? 'active' : ''}">${i}</a>
                            </c:forEach>
                            <c:if test="${p.next}">
                                <a href="javascript:searchQuality(${p.endPage + 1})">다음</a>
                            </c:if>
                        </div>
                    </c:if>
                </div>

                <div class="card">
                    <div class="section-title">
                        <h2>품질 상태 요약</h2>
                        <span style="font-size: 12px; color: #94a3b8;">실시간 요약 현황</span>
                    </div>
                    <ul class="summary-list">
                        <li style="margin-bottom: 20px;">
                            <div>
                                <strong>총 검사 건수</strong>
                                <p>현재 ${p.total}건 완료</p>
                            </div>
                            <span class="badge ok">정상 가동</span>
                        </li>
                        <li>
                            <div>
                                <strong>불량 발생(FAIL)</strong>
                                <p>조치 필요한 항목 존재</p>
                            </div>
                            <span class="badge danger">긴급 점검</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>

        <div id="commonModal" class="modal">
            <div class="modal-box">
                <div class="modal-header">
                    <h3 id="modalTitle">품질관리 신규 등록</h3>
                    <button class="modal-close" onclick="closeModal()">×</button>
                </div>

                <div class="modal-body">
                    <form id="qualityForm">
                        <div class="form-grid">
                            <div class="form-group">
                                <label>검사번호</label>
                                <input type="text" name="quality_key" placeholder="검사번호 입력">
                            </div>
                            <div class="form-group">
                                <label>품목명</label>
                                <input type="text" name="item_name" placeholder="품목명 입력">
                            </div>
                            <div class="form-group">
                                <label>검사수량</label>
                                <input type="number" name="inspect_qty" placeholder="0">
                            </div>
                            <div class="form-group">
                                <label>검사상태</label>
                                <select name="qc_status">
                                    <option value="PASS">PASS (정상)</option>
                                    <option value="FAIL">FAIL (불량)</option>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="modal-footer">
                    <button class="btn-custom" onclick="closeModal()">취소</button>
                    <button class="btn primary" type="button" onclick="submitForm()" 
                            style="background:#0d6efd; color:#fff; border:none;">
                        저장하기
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>