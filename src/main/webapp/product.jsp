<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 완제품관리</title>
    
    <script src="./asset/js/common.js" defer></script>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/page.css" />

    <style>
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

        .table-wrap table td, 
        .table-wrap table th { 
            text-align: center; 
        }

        .clickable-cell {
            cursor: pointer;
        }
        .clickable-cell:hover {
            background-color: #f8f9fa;
        }
    </style>
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
            <div class="header-chip date">2026-04-14</div>
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
                    <li><a href="./index.jsp">대시보드</a></li>
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
            
            <div class="snb-section active">
                <div class="snb-title">재고관리</div>
                <ul class="snb-menu">
                    <li><a href="./stock">재고</a></li>
                    <li class="active"><a href="./product">완제품</a></li>
                    <li><a href="./item.jsp">자재</a></li>
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

        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>완제품 관리</h1>
                    <p>생산 완료된 완제품의 품목 정보 및 규격을 관리합니다.</p>
                </div>
                
                <div class="page-actions">
                    <c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
                        <button class="btn primary" type="button" onclick="openInsertModal()">신규 완제품 등록</button>
                    </c:if>
                </div>
            </div>

            <section class="card">
                <div class="section-title"><h2>검색 조건</h2></div>
                <form action="product" method="get">
                    <input type="hidden" name="p" value="1">
                    <div class="search-row">
                        <input class="input" type="text" name="keyword" placeholder="완제품명 또는 규격 입력" value="${param.keyword}" />
                        <button class="btn primary" type="submit">조회</button>
                    </div>
                </form>
            </section>

            <section class="panel-grid">
                <div class="card" style="flex: 7;">
                    <form id="deleteForm" action="product" method="post" onsubmit="return validateDelete();">
                        <input type="hidden" name="cmd" value="delete"> 
                        <div class="section-title">
                            <h2>완제품 목록</h2>
                            <span>등록된 완제품 리스트</span>
                            <c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
                                <button type="submit" class="btn danger-outline">삭제</button>
                            </c:if>
                        </div>
                        
                        <div class="table-wrap">
                            <table>
                                <thead>
                                    <tr>
                                        <th onclick="toggleAllCheckboxes()" style="cursor:pointer;">선택</th>
                                        <th>번호</th>
                                        <th>완제품명</th>
                                        <th>규격(Spec)</th>
                                        <th>단위</th>
                                        <th>비고</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%-- [수정] 컨트롤러의 request.setAttribute("list", list)와 변수명 일치 확인 --%>
                                    <c:forEach var="pDto" items="${list}">
                                        <c:set var="params" value="'${pDto.product_key}', '${pDto.product_name}', '${pDto.spec}', '${pDto.unit}', '${pDto.remarks}'" />
                                        <tr>
                                            <td>
                                                <input type="checkbox" name="codes" value="${pDto.product_key}" class="product-checkbox">
                                            </td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${pDto.product_key}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${pDto.product_name}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${pDto.spec}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${pDto.unit}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${pDto.remarks}</td>
                                        </tr>
                                    </c:forEach>
                                    
                                    <c:if test="${empty list}">
                                        <tr><td colspan="6" style="padding:40px; color:#999;">데이터가 없습니다.</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                            
                            <div class="pagination">
                                <%-- [수정] totalCount 기반 페이징 --%>
                                <c:set var="tCount" value="${totalCount != null ? totalCount : 0}" /> 
                                <fmt:parseNumber var="totalPage" value="${(tCount + 9) / 10}" integerOnly="true" />
                                
                                <c:forEach var="i" begin="1" end="${totalPage > 0 ? totalPage : 1}">
                                    <a href="product?p=${i}&keyword=${param.keyword}" class="${(param.p == i || (empty param.p && i == 1)) ? 'active' : ''}">${i}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="card" style="flex: 3;">
                    <div class="section-title"><h2>요약 / 상태</h2></div>
                    <ul class="summary-list">
                        <li>
                            <div><strong>총 완제품 품목</strong><p>${totalCount}건</p></div> 
                            <span class="badge ok">정상</span>
                        </li>
                        <li>
                            <div><strong>규격 미지정</strong><p>검토 필요 품목</p></div> 
                            <span class="badge danger">확인</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>

        <div id="commonModal" class="modal">
            <div class="modal-box">
                <form id="productForm" action="product" method="post">
                    <input type="hidden" name="cmd" id="modal_cmd" value="insert">
                    <input type="hidden" name="product_key" id="modal_product_key" value="0">
                    
                    <div class="modal-header">
                        <h3 id="modalTitle">신규 완제품 등록</h3>
                        <button type="button" class="modal-close" onclick="closeModal()">×</button>
                    </div>
                    <div class="modal-body">
                        <div class="form-grid">
                            <div class="form-group" style="grid-column: span 2;">
                                <label>완제품명</label> 
                                <input type="text" class="input" name="product_name" id="modal_product_name" required />
                            </div>
                            <div class="form-group">
                                <label>규격 (Spec)</label> 
                                <input type="text" class="input" name="spec" id="modal_spec" />
                            </div>
                            <div class="form-group">
                                <label>단위</label> 
                                <input type="text" class="input" name="unit" id="modal_unit" placeholder="EA, BOX 등" />
                            </div>
                            <div class="form-group" style="grid-column: span 2;">
                                <label>비고 (Remarks)</label> 
                                <textarea class="input" name="remarks" id="modal_remarks" rows="3" style="resize:none;"></textarea>
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

    <script>
        function toggleAllCheckboxes() {
            const checkboxes = document.querySelectorAll('.product-checkbox');
            const allChecked = Array.from(checkboxes).every(cb => cb.checked);
            checkboxes.forEach(cb => cb.checked = !allChecked);
        }

        function validateDelete() {
            const checkedCount = document.querySelectorAll('.product-checkbox:checked').length;
            if (checkedCount === 0) {
                alert("삭제할 항목을 선택해주세요.");
                return false;
            }
            return confirm(checkedCount + "개의 항목을 삭제하시겠습니까?");
        }

        function openInsertModal() {
            document.getElementById("modal_cmd").value = "insert";
            document.getElementById("modalTitle").innerText = "신규 완제품 등록";
            document.getElementById("modal_product_key").value = "0";
            document.getElementById("modal_product_name").value = "";
            document.getElementById("modal_spec").value = "";
            document.getElementById("modal_unit").value = "";
            document.getElementById("modal_remarks").value = "";
            document.getElementById("commonModal").classList.add("show");
        }

        function openUpdateModal(key, name, spec, unit, remarks) {
            document.getElementById("modal_cmd").value = "update";
            document.getElementById("modalTitle").innerText = "완제품 정보 수정";
            document.getElementById("modal_product_key").value = key;
            document.getElementById("modal_product_name").value = name;
            document.getElementById("modal_spec").value = spec;
            document.getElementById("modal_unit").value = unit;
            document.getElementById("modal_remarks").value = (remarks === 'null' || remarks === undefined) ? "" : remarks;
            document.getElementById("commonModal").classList.add("show");
        }

        function closeModal() { 
            document.getElementById("commonModal").classList.remove("show"); 
        }
    </script>
</body>
</html>