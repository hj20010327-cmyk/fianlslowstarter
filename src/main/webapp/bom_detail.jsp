
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>BOM 상세</title>

<link rel="stylesheet" href="./asset/css/common.css" />
<link rel="stylesheet" href="./asset/css/bom-detail.css" />


<script src="./asset/js/common.js" defer></script>
<script src="./asset/js/bom-detail.js" defer></script>


<style>

/* ===== 2분할 컨테이너 ===== */
.bom-container {
	display: grid;
	grid-template-columns: 320px 1fr;
	gap: 20px;
	height: calc(100vh - 160px);
}

/* LEFT */
.bom-left {
	overflow-y: auto;
}

/* RIGHT */
.bom-right {
	overflow-y: auto;
}

/* ===== 제품 리스트 ===== */
.product-list {
	margin-top: 10px;
	display: flex;
	flex-direction: column;
	gap: 8px;
}

.product-list li {
	padding: 12px 14px;
	border: 1px solid #e2e8f0;
	border-radius: 10px;
	cursor: pointer;
	background: #fff;
	transition: 0.2s;
	font-size: 14px;
}

.product-list li:hover {
	background: #f1f5f9;
	border-color: #4a90e2;
}

.product-list li.active {
	background: #eaf2ff;
	border-color: #4a90e2;
	font-weight: 700;
}

.tree {
	padding: 10px;
}

/* 노드 기본 */
.tree ul {
	list-style: none;
	margin-left: 20px;
	padding-left: 10px;
	border-left: 1px dashed #cbd5e1;
}

.tree li {
	margin: 8px 0;
	position: relative;
	font-size: 14px;
}

/* 노드 버튼 */
.node {
	display: flex;
	align-items: center;
	gap: 8px;
	cursor: pointer;
	padding: 6px 10px;
	border-radius: 8px;
	transition: 0.2s;
}

.node:hover {
	background: #f1f5f9;
}

/* expand icon */
.toggle {
	width: 18px;
	height: 18px;
	display: inline-flex;
	align-items: center;
	justify-content: center;
	border: 1px solid #cbd5e1;
	border-radius: 4px;
	font-size: 12px;
	background: #fff;
}

.node.leaf .toggle {
	visibility: hidden;
}

.node strong {
	color: #1f2937;
}

.qty {
	margin-left: auto;
	font-size: 12px;
	color: #64748b;
}
</style>
<script>
const sampleData = {
		  1: {
		    name: "컴프레셔 A형",
		    children: [
		      {
		        name: "전방 하우징",
		        qty: 1,
		        children: [
		          { name: "알루미늄 블록", qty: 2 },
		          { name: "볼트 M6", qty: 6 }
		        ]
		      },
		      {
		        name: "샤프트",
		        qty: 1,
		        children: [
		          { name: "베어링 6204", qty: 2 },
		          { name: "오링", qty: 3 }
		        ]
		      }
		    ]
		  },

		  2: {
		    name: "컴프레셔 B형",
		    children: [
		      { name: "하우징 B", qty: 1 },
		      { name: "클러치", qty: 1 }
		    ]
		  },

		  3: {
		    name: "컴프레셔 C형",
		    children: [
		      { name: "모터", qty: 1 },
		      { name: "프레임", qty: 1 }
		    ]
		  }
		};

		// ===== 선택 상태 =====
		let selectedId = null;

		// ===== 제품 선택 =====
		function selectProduct(id) {
		  selectedId = id;

		  // active UI
		  document.querySelectorAll(".product-list li")
		    .forEach(li => li.classList.remove("active"));

		  event.target.classList.add("active");

		  // title 변경
		  document.getElementById("selectedName").innerText =
		    sampleData[id].name;

		  renderTree(sampleData[id]);
		}

		// ===== TREE RENDER =====
		function renderTree(data) {
		  const container = document.getElementById("bomTree");
		  container.innerHTML = "";

		  const ul = document.createElement("ul");
		  ul.appendChild(createNode(data));
		  container.appendChild(ul);
		}

		function createNode(node) {
		  const li = document.createElement("li");

		  const hasChild = node.children && node.children.length > 0;

		  const div = document.createElement("div");
		  div.className = "node" + (hasChild ? "" : " leaf");

		  div.innerHTML = `
		    <span class="toggle">${hasChild ? "+" : ""}</span>
		    <strong>${node.name}</strong>
		    <span class="qty">${node.qty ? "x" + node.qty : ""}</span>
		  `;

		  li.appendChild(div);

		  if (hasChild) {
		    const ul = document.createElement("ul");
		    ul.style.display = "none";

		    node.children.forEach(child => {
		      ul.appendChild(createNode(child));
		    });

		    li.appendChild(ul);

		    div.addEventListener("click", () => {
		      const open = ul.style.display === "block";
		      ul.style.display = open ? "none" : "block";
		      div.querySelector(".toggle").textContent = open ? "+" : "−";
		    });
		  }

		  return li;
		}
		
		function toggle(code) {
			const row = document.getElementById("item-" + id);
			
			if (row.style.display === "none"){
				row.style.display = "table_row";
			} else {
				row.style.display = "none";
			}
			
		}
</script>
</head>

<body>
	<header class="header">
		<div class="header-left">

			<a href="./index" class="logo"> <span class="logo-mark">AM</span>
				<span>AUTO MES</span>
			</a>

			<div class="header-title">자동차 콤프레셔 제조 MES</div>
		</div>

		<script>
			const contextPath = '${pageContext.request.contextPath}';
		</script>

		<div class="header-right">
			<div class="header-chip date"></div>
			<div class="header-chip">${dto.user_name}님</div>
			<button class="btn logout-btn" onclick="logout()">로그아웃</button>
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
					<li class="active"><a href="./bom">BOM</a></li>
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
					<li><a href="qualityList">품질</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">리포트</div>
				<ul class="snb-menu">
					<li><a href="./report">리포트</a></li>
					<li><a href="./production">생산실적</a></li>
				</ul>
			</div>
			<div class="snb-section">
				<div class="snb-title">시스템</div>
				<ul class="snb-menu">
					<li><a href="./board">게시판</a></li>
					<li><a href="./user">사용자관리</a></li>
					<li><a href="./mypage">마이페이지</a></li>
				</ul>
			</div>
		</aside>


		<!-- ===== PAGE HEADER ===== -->
		<div class="snb-overlay" id="snbOverlay"></div>
		<main class="content">
			<div class="page-head">
				<div class="page-head-left">
					<h1>BOM관리</h1>
					<p>제품별 자재 구성과 소요량을 관리하는 페이지입니다.</p>
				</div>
				<div class="page-actions">
					<button class="btn primary" type="button"
						onclick="openInsertModal('BOM 신규 등록')">신규 등록</button>
				</div>
			</div>
			
			
			<!-- ===== 2분할 시작 ===== -->
<div class="bom-container">

  <!-- LEFT -->
  <section class="card bom-left">
    <div class="section-title">
      <h2>완제품 목록</h2>
      <span>클릭 시 우측 표시</span>
    </div>

    <ul id="productList" class="product-list">
      <li onclick="selectProduct(1)">컴프레셔 A형</li>
      <li onclick="selectProduct(2)">컴프레셔 B형</li>
      <li onclick="selectProduct(3)">컴프레셔 C형</li>
    </ul>
  </section>

  <!-- RIGHT -->
  <section class="card bom-right">
    <div class="section-title">
      <h2>BOM 구조</h2>
      <span id="selectedName">완제품을 선택하세요</span>
    </div>

    <div id="bomTree" class="tree"></div>
    
    <table >
    	<c:forEach var="bom" items="${map.list}">
    	<tr onclick="toggle('${bom.bom_code}')" style="cursor:pointer">
    		<td>▶ ${bom.bom_code}</td>
    		<td>▶ ${bom.parent_item_name}</td>
    	</tr>
    	
    	<tr id="item-${bom.bom_code}" style="display:none;">
    		<td colspan="2">
    		
    			<table border="1" width="100%">
    				<c:forEach var="item" items="${itemMap[bom.bom_code]}">
    					<tr>
    						<td> - ${item.item_code}</td>
    						<td> - ${item.item_name}</td>
    						<td> - ${item.spec}</td>
    						<td> - ${item.price}</td>
    						<td> - ${item.safe_qty}</td>
    						<td> - ${item.unit}</td>
   						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</c:forEach>
	</table>
	
	
	
	
    
  </section>

</div>
<!-- ===== 2분할 끝 ===== -->






	

</body>
</html>