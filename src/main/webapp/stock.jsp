<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>AUTO MES | 재고 현황</title>


    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/common.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/asset/css/page.css" />


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


        .btn-blue { 
            background: #3182ce; 
            color: #fff; 
            border: none; 
            padding: 10px 20px; 
            border-radius: 8px; 
            cursor: pointer; 
            font-weight: 600;
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


        /* 재고 부족 경고 스타일 */
        .stock-warning { 
            color: #e53e3e; 
            font-weight: 700; 
            background-color: #fff5f5; 
            padding: 4px 8px; 
            border-radius: 4px; 
        }


        .stock-safe { 
            color: #38a169; 
            font-weight: 600; 
        }


        .badge-category { 
            padding: 2px 8px; 
            border-radius: 4px; 
            font-size: 11px; 
            background: #edf2f7; 
            color: #4a5568; 
        }
    </style>


    <script>
        function doSearch() {
            const keyword = document.getElementById('searchKeyword').value;
            location.href = "${pageContext.request.contextPath}/stockList?keyword=" + encodeURIComponent(keyword);
        }
    </script>
</head>


<body>
    <header class="header">
        <div class="header-left">
            <a href="#" class="logo"><span class="logo-mark">AM</span><span>AUTO MES</span></a>
        </div>
    </header>


    <div class="layout">
        <aside class="snb">
            <div class="snb-section active">
                <div class="snb-title">재고관리</div>
                <ul class="snb-menu">
                    <li><a href="${pageContext.request.contextPath}/productList">완제품 관리</a></li>
                    <li><a href="${pageContext.request.contextPath}/itemList">자재 관리</a></li>
                    <li class="active"><a href="${pageContext.request.contextPath}/stockList">재고 현황</a></li>
                </ul>
            </div>
        </aside>


        <main class="content">
            <div class="page-header">
                <h1>재고 현황</h1>
            </div>


            <div class="card">
                <div class="search-container">
                    <input type="text" id="searchKeyword" class="input-field" placeholder="품목명 또는 코드로 검색" style="flex:4">
                    <button type="button" class="btn-blue" onclick="doSearch()">조회</button>
                </div>
            </div>


            <div class="card">
                <table>
                    <thead>
                        <tr>
                            <th>구분</th>
                            <th>품목코드</th>
                            <th>품목명</th>
                            <th>규격</th>
                            <th>현재고</th>
                            <th>안전재고</th>
                            <th>상태</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${stockList}">
                            <tr>
                                <td><span class="badge-category">${s.itemType}</span></td>
                                <td style="font-weight:600;">${s.itemCode}</td>
                                <td>${s.itemName}</td>
                                <td>${s.spec}</td>
                                <td class="${s.currQty < s.safeQty ? 'stock-warning' : 'stock-safe'}">
                                    <fmt:formatNumber value="${s.currQty}" />
                                </td>
                                <td><fmt:formatNumber value="${s.safeQty}" /></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${s.currQty < s.safeQty}">
                                            <span style="color:#e53e3e;">▼ 재고부족</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color:#38a169;">● 정상</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</body>
</html>