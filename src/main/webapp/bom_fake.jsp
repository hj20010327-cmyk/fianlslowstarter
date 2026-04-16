<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>BOM TEST</title>
</head>

<body>

<h2>BOM LIST 테스트</h2>

<!-- 1. list 데이터 확인 -->
<c:if test="${not empty list}">
    <table border="1">

        <tr>
            <th>BOM CODE</th>
            <th>NAME</th>
        </tr>

        <c:forEach var="bom" items="${list}">
            <tr>
                <td>${bom.bom_code}</td>
                <td>${bom.parent_item_name}</td>
            </tr>
        </c:forEach>
        
        <c:forEach var="bom" items="${list}">
										<tr>
											<td><input type="checkbox" name="bom_key"
												value="${bom.bom_key}"></td>
											<td><a href="javascript:void(0);"
												onclick="openEditModal('${bom.bom_key}',
											'${bom.bom_code}',
											'${bom.qty}',
											'${bom.remark}',
											'${bom.item_name}',
											'${bom.parent_item_name}'
											)">
													${bom.bom_code} </a></td>
											<td>${bom.parent_item_name}</td>
											<td>${bom.item_name}</td>
											<td>${bom.qty}</td>
											<td>${bom.remark}</td>
										</tr>
									</c:forEach>

    </table>
</c:if>

list size = ${list.size()}


<!-- 2. 데이터 없을 때 -->
<c:if test="${empty list}">
    <h3>데이터 없음</h3>
</c:if>

</body>
</html>



</body>
</html>