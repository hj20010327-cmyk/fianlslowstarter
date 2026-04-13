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
        /* 페이지네이션 */
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

        /* 현재고 부족 알림 */
        .qty-alert { 
            color: #e53e3e; 
            font-weight: bold; 
        }

        /* 테이블 텍스트 중앙 정렬 */
        .table-wrap table td, 
        .table-wrap table th {
            text-align: center;
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
            <div class="header-chip date"></div>
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
                    <button class="btn primary" type="button" onclick="openInsertModal()">신규 재고 등록</button>
                </div>
            </div>

            <section class="card">
                <div class="section-title">
                    <h2>검색 조건</h2>
                </div>
                <form action="stock" method="get">
                    <input type="hidden" name="page" value="1">
                    <div class="search-row">
                        <input class="input" type="text" name="keyword" placeholder="LOT 번호 입력" value="${param.keyword}" />
                        <button class="btn primary" type="submit">조회</button>
                    </div>
                </form>
            </section>

            <section class="panel-grid">
                <div class="card" style="flex: 7;">
                    <form action="stockDelete" method="post">
                        <div class="section-title">
                            <h2>재고 목록</h2>
                            <span>DB 실시간 데이터</span>
                            <button type="submit" class="btn">삭제</button>
                        </div>
                        <div class="table-wrap">
                            <table>
                                <tr>
                                    <th>선택</th>
                                    <th>LOT 번호</th>
                                    <th>입고수량</th>
                                    <th>출고수량</th>
                                    <th>현재고</th>
                                    <th>최근 업데이트</th>
                                </tr>
                                <c:forEach var="s" items="${list}">
                                    <tr>
                                        <td><input type="checkbox" name="codes" value="${s.stock_key}"></td>
                                        <td style="font-weight:600;">${s.lot}</td>
                                        <td><fmt:formatNumber value="${s.in_qty}" /></td>
                                        <td><fmt:formatNumber value="${s.out_qty}" /></td>
                                        <td>
                                            <span class="${s.current_qty < s.safe_qty ? 'qty-alert' : ''}">
                                                <fmt:formatNumber value="${s.current_qty}" />
                                            </span>
                                        </td>
                                        <td><fmt:formatDate value="${s.updated_at}" pattern="yyyy-MM-dd HH:mm" /></td>
                                    </tr>
                                </c:forEach>
                                <c:if test="${empty list}">
                                    <tr><td colspan="6" style="padding:40px; color:#999;">데이터가 없습니다.</td></tr>
                                </c:if>
                            </table>
                            <div class="pagination">
                                <c:forEach var="i" begin="1" end="5">
                                    <a href="stock?p=${i}&keyword=${param.keyword}" class="${(param.p == i || (empty param.p && i == 1)) ? 'active' : ''}">${i}</a>
                                </c:forEach>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="card" style="flex: 3;">
                    <div class="section-title">
                        <h2>요약 / 상태</h2>
                        <span>전체 현황</span>
                    </div>
                    <ul class="summary-list">
                        <li>
                            <div>
                                <strong>총 관리 LOT</strong>
                                <p>${fn:length(list)}건</p>
                            </div> 
                            <span class="badge ok">정상</span>
                        </li>
                        <li>
                            <div>
                                <strong>재고 부족</strong>
                                <p>안전재고 미달 품목</p>
                            </div> 
                            <span class="badge danger">주의</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>

        <div id="commonModal" class="modal">
            <div class="modal-box">
                <form id="stockForm" action="stockAdd" method="post">
                    <div class="modal-header">
                        <h3 id="modalTitle">신규 재고 등록</h3>
                        <button type="button" class="modal-close" onclick="closeModal()">×</button>
                    </div>
                    <div class="modal-body">
                        <div class="form-grid">
                            <div class="form-group" style="grid-column: span 2;">
                                <label>LOT 번호</label> 
                                <input type="text" class="input" name="lot" required />
                            </div>
                            <div class="form-group">
                                <label>입고수량</label> 
                                <input type="number" class="input" name="in_qty" value="0" />
                            </div>
                            <div class="form-group">
                                <label>출고수량</label> 
                                <input type="number" class="input" name="out_qty" value="0" />
                            </div>
                            <div class="form-group">
                                <label>안전재고</label> 
                                <input type="number" class="input" name="safe_qty" value="0" />
                            </div>
                            <div class="form-group">
                                <label>품목번호(FK)</label> 
                                <input type="number" class="input" name="item_key" value="1" />
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
        function openInsertModal() {
            document.getElementById("commonModal").classList.add("show");
        }
        function closeModal() {
            document.getElementById("commonModal").classList.remove("show");
        }
    </script>
</body>
</html>