<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 재고관리</title>
    
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

        /* 클릭 가능한 데이터 셀에만 포인터 표시 */
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
                    <li class="active"><a href="./stock">재고</a></li>
                    <li><a href="./product.jsp">완제품</a></li>
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
                    <h1>재고관리</h1>
                    <p>전체 재고 현황 및 LOT 정보를 관리합니다.</p>
                </div>
                
                <div class="page-actions">
                    <c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
                        <button class="btn primary" type="button" onclick="openInsertModal()">신규 재고 등록</button>
                    </c:if>
                </div>
            </div>

            <section class="card">
                <div class="section-title"><h2>검색 조건</h2></div>
                <form action="stock" method="get">
                    <input type="hidden" name="p" value="1">
                    <div class="search-row">
                        <input class="input" type="text" name="keyword" placeholder="LOT 번호 입력" value="${param.keyword}" />
                        <button class="btn primary" type="submit">조회</button>
                    </div>
                </form>
            </section>

            <section class="panel-grid">
                <div class="card" style="flex: 7;">
                    <form id="deleteForm" action="stock" method="post" onsubmit="return validateDelete();">
                        <input type="hidden" name="cmd" value="delete"> 
                        <div class="section-title">
                            <h2>재고 목록</h2>
                            <span>DB 실시간 데이터</span>
                            <c:if test="${dto.user_role eq '관리자' or dto.user_role eq '슈퍼바이저'}">
                                <button type="submit" class="btn danger-outline">삭제</button>
                            </c:if>
                        </div>
                        
                        <div class="table-wrap">
                            <table>
                                <thead>
                                    <tr>
                                        <th onclick="toggleAllCheckboxes()" style="cursor:pointer;">선택</th>
                                        <th style="cursor:default;">번호</th>
                                        <th style="cursor:default;">LOT 번호</th>
                                        <th style="cursor:default;">입고수량</th>
                                        <th style="cursor:default;">출고수량</th>
                                        <th style="cursor:default;">현재고</th>
                                        <th style="cursor:default;">안전재고</th>
                                        <th style="cursor:default;">최근 업데이트</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="dto" items="${list}">
                                        <c:set var="params" value="'${dto.stock_key}', '${dto.lot}', '${dto.in_qty}', '${dto.out_qty}', '${dto.safe_qty}', '${dto.item_key}'" />
                                        <tr>
                                            <td>
                                                <input type="checkbox" name="codes" value="${dto.stock_key}" class="stock-checkbox">
                                            </td>
                                            
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${dto.stock_key}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${dto.lot}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${dto.in_qty}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${dto.out_qty}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${dto.current_qty}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">${dto.safe_qty}</td>
                                            <td class="clickable-cell" onclick="openUpdateModal(${params})">
                                                <fmt:formatDate value="${dto.updated_at}" pattern="yyyy-MM-dd HH:mm"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    
                                    <c:if test="${empty list}">
                                        <tr><td colspan="8" style="padding:40px; color:#999;">데이터가 없습니다.</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                            
                            <div class="pagination">
                                <c:set var="totalCount" value="${totalCount != null ? totalCount : 0}" /> 
                                <fmt:parseNumber var="totalPage" value="${(totalCount + 9) / 10}" integerOnly="true" />
                                
                                <c:forEach var="i" begin="1" end="${totalPage > 0 ? totalPage : 1}">
                                    <a href="stock?p=${i}&keyword=${param.keyword}" class="${(param.p == i || (empty param.p && i == 1)) ? 'active' : ''}">${i}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="card" style="flex: 3;">
                    <div class="section-title"><h2>요약 / 상태</h2></div>
                    <ul class="summary-list">
                        <li>
                            <div><strong>총 관리 LOT</strong><p>${totalCount}건</p></div> 
                            <span class="badge ok">정상</span>
                        </li>
                        <li>
                            <div><strong>재고 부족</strong><p>안전재고 미달 품목</p></div> 
                            <span class="badge danger">주의</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>

        <div id="commonModal" class="modal">
            <div class="modal-box">
                <form id="stockForm" action="stock" method="post">
                    <input type="hidden" name="cmd" id="modal_cmd" value="insert">
                    <input type="hidden" name="stock_key" id="modal_stock_key" value="0">
                    
                    <div class="modal-header">
                        <h3 id="modalTitle">신규 재고 등록</h3>
                        <button type="button" class="modal-close" onclick="closeModal()">×</button>
                    </div>
                    <div class="modal-body">
                        <div class="form-grid">
                            <div class="form-group" style="grid-column: span 2;">
                                <label>LOT 번호</label> <input type="text" class="input" name="lot" id="modal_lot" required />
                            </div>
                            <div class="form-group">
                                <label>입고수량</label> <input type="number" class="input" name="in_qty" id="modal_in_qty" value="0" />
                            </div>
                            <div class="form-group">
                                <label>출고수량</label> <input type="number" class="input" name="out_qty" id="modal_out_qty" value="0" />
                            </div>
                            <div class="form-group">
                                <label>안전재고</label> <input type="number" class="input" name="safe_qty" id="modal_safe_qty" value="0" />
                            </div>
                            <div class="form-group" style="grid-column: span 2;">
                                <label>품목번호 (item_key) <small style="color:red;">* 필수</small></label> <input type="number" class="input" name="item_key" id="modal_item_key" required />
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
            const checkboxes = document.querySelectorAll('.stock-checkbox');
            const allChecked = Array.from(checkboxes).every(cb => cb.checked);
            checkboxes.forEach(cb => cb.checked = !allChecked);
        }

        function validateDelete() {
            const checkedCount = document.querySelectorAll('.stock-checkbox:checked').length;
            if (checkedCount === 0) {
                alert("삭제할 항목을 선택해주세요.");
                return false;
            }
            return confirm(checkedCount + "개의 항목을 삭제하시겠습니까?");
        }

        function openInsertModal() {
            const form = document.getElementById("stockForm");
            document.getElementById("modal_cmd").value = "insert";
            document.getElementById("modalTitle").innerText = "신규 재고 등록";
            document.getElementById("modal_stock_key").value = "0";
            document.getElementById("modal_lot").value = "";
            document.getElementById("modal_in_qty").value = "0";
            document.getElementById("modal_out_qty").value = "0";
            document.getElementById("modal_safe_qty").value = "0";
            document.getElementById("modal_item_key").value = "";
            document.getElementById("commonModal").classList.add("show");
        }

        function openUpdateModal(key, lot, inQty, outQty, safeQty, itemKey) {
            const form = document.getElementById("stockForm");
            document.getElementById("modal_cmd").value = "update";
            document.getElementById("modalTitle").innerText = "재고 정보 수정";
            document.getElementById("modal_stock_key").value = key;
            document.getElementById("modal_lot").value = lot;
            document.getElementById("modal_in_qty").value = inQty;
            document.getElementById("modal_out_qty").value = outQty;
            document.getElementById("modal_safe_qty").value = safeQty;
            document.getElementById("modal_item_key").value = itemKey;
            document.getElementById("commonModal").classList.add("show");
        }

        function closeModal() { document.getElementById("commonModal").classList.remove("show"); }
    </script>
</body>
</html>