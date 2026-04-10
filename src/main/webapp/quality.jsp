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
        /* 가독성을 위한 레이아웃 보정 */
        .snb { overflow-y: auto; max-height: 100vh; }
        
        .pagination { 
            display: flex; justify-content: center; 
            margin-top: 20px; gap: 5px; 
        }
        
        .pagination a { 
            padding: 8px 12px; border: 1px solid #ddd; 
            text-decoration: none; color: #333; border-radius: 4px; 
        }
        
        .pagination a.active { 
            background-color: #0d6efd; color: white; 
        }

        /* 상태별 뱃지 스타일 */
        .badge { 
            padding: 4px 10px; 
            border-radius: 50px; 
            font-size: 12px; 
            font-weight: 600; 
            display: inline-block;
        }
        .badge.ok { background-color: #e6f4ea; color: #1e8e3e; }      /* 합격 */
        .badge.warning { background-color: #fef7e0; color: #f9ab00; } /* 재검 */
        .badge.danger { background-color: #fce8e6; color: #d93025; }  /* 불합격 */

        /* 테이블 행 호버 효과 */
        tbody tr:hover { background-color: #f8fafc; cursor: pointer; }
        
        /* 테이블 텍스트 정렬 보정 */
        th, td { text-align: center !important; padding: 12px 8px; }
        td:nth-child(3) { text-align: left !important; } /* 품목명은 왼쪽 정렬 */
    </style>
</head>

<body>
    <header class="header">
        <div class="header-left">
            <a href="#" class="logo"><span class="logo-mark">AM</span><span>AUTO MES</span></a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip">2026-04-10</div>
            <div class="header-chip" style="color: #1e8e3e;">● 생산 1라인 가동중</div>
            <div class="header-chip">관리자</div>
        </div>
    </header>

    <div class="layout">
        <aside class="snb">
            <div class="snb-section">
                <div class="snb-title">MAIN</div>
                <ul class="snb-menu">
                    <li><a href="#">대시보드</a></li>
                </ul>
            </div>
            
            <div class="snb-section active">
                <div class="snb-title">품질관리</div>
                <ul class="snb-menu">
                    <li class="active"><a href="#">품질 목록</a></li>
                </ul>
            </div>
            
            <div class="snb-section">
                <div class="snb-title">시스템</div>
                <ul class="snb-menu">
                    <li><a href="#">사용자관리</a></li>
                    <li><a href="#">마이페이지</a></li>
                </ul>
            </div>
        </aside>

        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>품질관리</h1>
                    <p>검사 결과와 불량 유형을 관리합니다.</p>
                </div>
                <div class="page-actions">
                    <button class="btn primary">품질 등록</button>
                </div>
            </div>

            <section class="card">
                <div class="search-row">
                    <input type="text" class="input" placeholder="품목코드/검사번호" style="width: 300px;">
                    <select class="select">
                        <option value="">전체 상태</option>
                        <option value="합격">합격</option>
                        <option value="재검">재검</option>
                        <option value="불합격">불합격</option>
                    </select>
                    <button class="btn primary">조회</button>
                </div>
            </section>

            <section class="panel-grid">
                <div class="card">
                    <div class="section-title">
                        <h2>품질 목록</h2>
                        <button class="btn danger" style="padding: 5px 15px; font-size: 13px;">삭제</button>
                    </div>
                    
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th style="width: 50px;"><input type="checkbox"></th>
                                    <th>검사번호</th>
                                    <th>품목명</th>
                                    <th>검사수량</th>
                                    <th>상태</th>
                                    <th>검사일자</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="m" items="${list}">
                                    <tr>
                                        <td><input type="checkbox"></td>
                                        <td style="font-weight: 500;">${m.quality_key}</td>
                                        <td>${m.item_name}</td>
                                        <td><fmt:formatNumber value="${m.inspect_qty}" pattern="#,###"/></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${m.qc_status == '합격'}">
                                                    <span class="badge ok">합격</span>
                                                </c:when>
                                                <c:when test="${m.qc_status == '재검'}">
                                                    <span class="badge warning">재검</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge danger">${m.qc_status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td style="color: #64748b;">
                                            <fmt:formatDate value="${m.inspect_date}" pattern="yyyy-MM-dd"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                                
                                <c:if test="${empty list}">
                                    <tr>
                                        <td colspan="6" style="padding: 50px 0; color: #94a3b8;">조회된 데이터가 없습니다.</td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>

                    <div class="pagination">
                        <a href="#" class="active">1</a>
                        <a href="#">2</a>
                        <a href="#">3</a>
                    </div>
                </div>
            </section>
        </main>
    </div>
</body>
</html>