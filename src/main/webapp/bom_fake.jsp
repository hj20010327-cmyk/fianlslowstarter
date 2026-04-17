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
          
            <th>완제품명</th>
        </tr>

        <c:forEach var="item" items="${itemList}">
            <tr>
                <td>${item.item_name}</td>
               
            </tr>
        </c:forEach>


    </table>
    
    <table border="1"> 
    	<tr>
    	<th>자재코드</th>
    	<th>자재명</th>
    	<th>검사</th>
    	<th>단위</th>
    	<th>단가</th>
    	<th>수량</th>
    	<th>상태</th>
    	</tr>
    	 <c:forEach var="item" items="${material}">
    	<tr>
    	<th>${item.item_code}</th>
    	<th>${item.item_name}</th>
    	<th>${item.spec}</th>
    	<th>${item.unit}</th>
    	<th>${item.price}</th>
    	<th>${item.safe_qty}</th>
    	<th>${item.status}</th>
    	</tr>
    	</c:forEach>
    </table>
</c:if>



<ul>
<c:forEach var="parent" items="${itemList}">
    <li>${parent.item_name}
        <ul>
            <c:forEach var="child" items="${list}">
                <c:if test="${parent.item_key == child.parent_item_key}">
                    <li>${child.item_name}</li>
                </c:if>
            </c:forEach>
        </ul>
    </li>
</c:forEach>
</ul>

list size = ${list.size()}


<!-- 2. 데이터 없을 때 -->
<c:if test="${empty list}">
    <h3>데이터 없음</h3>
</c:if>

</body>
</html>



</body>
</html>