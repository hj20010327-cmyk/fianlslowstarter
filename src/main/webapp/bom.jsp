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
    <title>AUTO MES | BOM관리</title>
    <script src="./asset/js/common.js" defer></script>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/page.css" />
</head>

<body>
    <header class="header">
        <div class="header-left"><a href="./index.html" class="logo"><span class="logo-mark">AM</span><span>AUTO
                    MES</span></a>
            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>
        <div class="header-right">
            <div class="header-chip">2026-03-30</div>
            <div class="header-chip">생산 1라인 가동중</div>
            <div class="header-chip">관리자</div>
        </div>
        <button type="button" class="menu-toggle" id="menuToggle">☰</button>
    </header>
    <div class="layout">
        <aside class="snb" id="snb">
            <div class="snb-section">
                <div class="snb-title">MAIN</div>
                <ul class="snb-menu">
                    <li><a href="./index.html">대시보드</a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">기준관리</div>
                <ul class="snb-menu">
                    <li><a href="./master.html">기준관리</a></li>
                    <li class="active"><a href="./bom.html">BOM</a></li>
                    <li><a href="./process.html">공정</a></li>
                </ul>
            </div>
            
            <div class="snb-section">
                <div class="snb-title">생산관리</div>
                <ul class="snb-menu">
                    <li><a href="./workorder.html">작업지시 <span class="menu-badge">4</span></a></li>
                    <li><a href="./plan.html">생산계획 <span class="menu-badge">2</span></a></li>
                </ul>
            </div>
            <div class="snb-section">
                <div class="snb-title">재고관리</div>
                <ul class="snb-menu">
                    <li><a href="./stock.html">재고</a></li>
                    <li><a href="./product.html">완제품</a></li>
                    <li><a href="./item.html">자재</a></li>
                </ul>
            </div>
            
            <div class="snb-section">
                <div class="snb-title">품질관리</div>
                <ul class="snb-menu">
                    <li><a href="./quality.html">품질<span class="menu-badge">2</span></a></li>
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
                    <li><a href="./user.html">사용자관리</a></li>
                    <li><a href="./mypage.html">마이페이지</a></li>
                </ul>
            </div>
        </aside>

        <div class="snb-overlay" id="snbOverlay"></div>
        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>BOM관리</h1>
                    <p>제품별 자재 구성과 소요량을 관리하는 페이지입니다.</p>
                </div>
                <div class="page-actions"><button class="btn primary" type="button" onclick="openModal('BOM 신규 등록')">
  신규 등록
</button></div>
            </div>
            <section class="card" style="margin-bottom:20px">
                <div class="section-title">
                    <h2>검색 조건</h2><span>기준 조건을 선택하세요</span>
                </div>
                <div class="search-row"><input class="input" type="text" placeholder="코드 또는 번호 입력" /><input
                        class="input" type="text" placeholder="명칭 입력" /><select class="select">
                        <option>전체</option>
                        <option>사용</option>
                        <option>미사용</option>
                    </select><button class="btn primary" type="button">조회</button></div>
            </section>
            <section class="panel-grid">
                <div class="card">
                    <div class="section-title">
                        <h2>BOM관리 목록</h2><span>샘플 데이터</span>
                    </div>
                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th>BOM코드</th>
                                    <th>제품명</th>
                                    <th>품목명</th>
                                    <th>수량</th>
                                    <th>비고</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="bom" items="${map.list}">
                                <tr>
                                    <td>${bom.bom_key}</td>
                                    <td>${bom.product_item_key}</td>
                                    <td>${bom.material_item_key}</td>
                                    <td>${bom.QTY}</td>
                                    <td>${bom.remark}</td>
                                </tr>
                               </c:forEach>
                               
                            </tbody>
                        </table>
                        <%--
                           <% Map map = (Map)request.getAttribute("map");
                           
                           if(map != null){
                        	    int total = Integer.parseInt(map.get("totalCount").toString());
                        	    int size = Integer.parseInt(map.get("size").toString());
                        	}
                               		int total = (int)map.get("totalCount");
                               		int size = (int)map.get("size");
                               		
                               		int totalPage = (int)Math.ceil((double)total/size);
                               		
                               		int section = 5; 
                               		int pageNum = (int)map.get("page");
                               		
                               		int end_section = (int)Math.ceil((double)pageNum / section) * section;
                               		int start_section = end_section - section + 1; 
                               		
                               		if(end_section > totalPage){
                               			end_section = totalPage;
                               			}
                               		
                               		System.out.println("end_section:" + end_section);
                               		System.out.println("start_section:" + start_section);
                            --%> 
                            <c:set var="total" value="${map.totalCount }" />
                            <c:set var="size" value="${map.size }" />
                            <c:set var="page" value="${map.page }" />
                            
                            <c:set var="totalPage" value="${total/size}" />
                            <c:set var="section" value="5" />
                            <c:set var="end_section" value="${(page/section)*section}" />
                            <c:set var="start_section" value="${end_section - section + 1}" />
                            
                            <c:if test="${start_section < 1}">
  							  <c:set var="start_section" value="1" />
							</c:if>
                            <c:if test="${end_section > totalPage}"> 
                            	<c:set var="end_section" value="${totalPage}" />
                           	</c:if>
                             
                               
                            <c:forEach var="i" begin="${start_section}" end="${end_section}">
                               	<a href = "BOM?page=${i}&size=5">
                              
                               	<c:if test="${map.page eq i }">
                               	<strong>${i}</strong>
                               	</c:if>
                               	<c:if test="${map.page != i} ">
                               	${i}
                               	</c:if>
                               	</a>
                            </c:forEach>
                    </div>
                </div>
                <div class="card">
                    <div class="section-title">
                        <h2>요약 / 상태</h2><span>오늘 기준</span>
                    </div>
                    <ul class="summary-list">
                        <li>
                            <div><strong>최신 버전</strong>
                                <p>B210 제품이 V2로 운영 중입니다.</p>
                            </div><span class="badge ok">적용</span>
                        </li>
                        <li>
                            <div><strong>변경 요청</strong>
                                <p>설계 변경 요청이 1건 있습니다.</p>
                            </div><span class="badge warn">1건</span>
                        </li>
                        <li>
                            <div><strong>미연결 품목</strong>
                                <p>BOM 미연결 자재가 없습니다.</p>
                            </div><span class="badge ok">정상</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>
    </div>
    
       <!-- ===== 공통 모달 ===== -->
<div id="commonModal" class="modal">
  <div class="modal-box">

    <!-- 헤더 -->
    <div class="modal-header">
      <h3 id="modalTitle">신규 등록</h3>
      <button class="modal-close" onclick="closeModal()">×</button>
    </div>

    <!-- 바디 -->
    <div class="modal-body">
      <div class="form-grid">

        <div class="form-group">
          <label>코드</label>
          <input type="text" class="input" placeholder="코드 입력" />
        </div>

        <div class="form-group">
          <label>제품명</label>
          <input type="text" class="input" placeholder="제품명 입력" />
        </div>

        <div class="form-group">
          <label>품목명</label>
          <input type="text" class="input" placeholder="품목명 입력" />
        </div>

        <div class="form-group">
          <label>소요량</label>
          <input type="number" class="input" placeholder="수량 입력" />
        </div>

        <div class="form-group">
          <label>버전</label>
          <input type="text" class="input" placeholder="예: V1" />
        </div>

        <div class="form-group">
          <label>사용여부</label>
          <select class="select">
            <option>사용</option>
            <option>미사용</option>
          </select>
        </div>

        <div class="form-group" style="grid-column: span 2;">
          <label>비고</label>
          <textarea class="textarea" placeholder="설명 입력"></textarea>
        </div>

      </div>
    </div>

    <!-- 푸터 -->
    <div class="modal-footer">
      <button class="btn" onclick="closeModal()">취소</button>
      <button class="btn primary">저장</button>
    </div>

  </div>
</div>
</body>

</html>