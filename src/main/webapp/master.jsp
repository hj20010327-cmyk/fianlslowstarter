<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>AUTO MES | 기준관리</title>
    <script src="./asset/js/common.js" defer></script>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/page.css" />
</head>

<body>
    <header class="header">
        <div class="header-left">
            <a href="./index.html" class="logo">
                <span class="logo-mark">AM</span>
                <span>AUTO MES</span>
            </a>

            <div class="header-title">자동차 콤프레셔 제조 MES</div>
        </div>

        <div class="header-right">
            <div class="header-chip">2026-03-30</div>
            <div class="header-chip">기준정보 관리</div>
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
                    <li class="active"><a href="./master.html">기준관리</a></li>
                    <li><a href="./bom.html">BOM</a></li>
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
                    <li><a href="./quality.html">품질 <span class="menu-badge">2</span></a></li>
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
                    <h1>기준관리</h1>
                    <p>자동차 콤프레셔 공장의 BOM, 공정, 품목, 완제품 등 생산 기준정보를 통합 관리하는 페이지입니다.</p>
                </div>

                <div class="page-actions">
                    <button class="btn" type="button">변경이력</button>
                    <button class="btn primary" type="button">신규 기준 등록</button>
                </div>
            </div>

            <section class="hero-banner">
                <div>
                    <h2>생산 기준정보 통합 관리</h2>
                    <p>기준정보를 정확하게 유지하면 생산계획, 작업지시, 재고, 품질 데이터가 안정적으로 연결됩니다.</p>
                </div>

                <div class="pill-row">
                    <span class="pill">BOM 24건</span>
                    <span class="pill">공정 12건</span>
                    <span class="pill">품목 38건</span>
                </div>
            </section>

            <section class="top-cards">
                <div class="card">
                    <div class="card-label">등록된 BOM</div>
                    <div class="card-value">24건</div>
                    <div class="card-meta">최근 수정 2건</div>
                </div>

                <div class="card">
                    <div class="card-label">등록된 공정</div>
                    <div class="card-value">12건</div>
                    <div class="card-meta">가공 ~ 포장 공정</div>
                </div>

                <div class="card">
                    <div class="card-label">등록 품목 수</div>
                    <div class="card-value">38건</div>
                    <div class="card-meta">원자재 / 부품 / 완제품</div>
                </div>

                <div class="card">
                    <div class="card-label">변경 요청</div>
                    <div class="card-value">3건</div>
                    <div class="card-meta">승인 대기중</div>
                </div>
            </section>

            <section class="grid-2">
                <div class="card">
                    <div class="section-title">
                        <h2>기준관리 바로가기</h2>
                        <span>메뉴 선택</span>
                    </div>

                    <div class="master-grid">
                        <a href="./bom.html" class="master-card">
                            <strong>BOM 관리</strong>
                            <p>제품별 구성 자재와 소요량을 관리합니다.</p>
                            <span>총 24건</span>
                        </a>

                        <a href="./process.html" class="master-card">
                            <strong>공정 관리</strong>
                            <p>가공, 조립, 검사, 포장 공정을 관리합니다.</p>
                            <span>총 12건</span>
                        </a>

                        <a href="./item.html" class="master-card">
                            <strong>품목 관리</strong>
                            <p>원자재, 부품, 반제품 품목 기준정보를 관리합니다.</p>
                            <span>총 38건</span>
                        </a>

                        <a href="./product.html" class="master-card">
                            <strong>완제품 관리</strong>
                            <p>완제품 모델 및 출하 대상 품목을 관리합니다.</p>
                            <span>총 16건</span>
                        </a>
                    </div>
                </div>

                <div class="card">
                    <div class="section-title">
                        <h2>운영 안내</h2>
                        <span>관리 기준</span>
                    </div>

                    <ul class="notice-list">
                        <li>
                            <div>
                                <strong>BOM 수정 시 작업지시 영향 확인</strong>
                                <p>생산 중인 제품의 자재 구성과 연동됩니다.</p>
                            </div>
                            <span class="badge warn">주의</span>
                        </li>
                        <li>
                            <div>
                                <strong>공정 순서 변경 시 승인 필요</strong>
                                <p>생산 흐름 및 실적 집계에 영향을 줄 수 있습니다.</p>
                            </div>
                            <span class="badge danger">중요</span>
                        </li>
                        <li>
                            <div>
                                <strong>품목코드는 중복 없이 등록</strong>
                                <p>재고 및 구매 데이터와 연결되는 기준값입니다.</p>
                            </div>
                            <span class="badge ok">기본</span>
                        </li>
                        <li>
                            <div>
                                <strong>완제품 정보는 출하 전 최종 확인</strong>
                                <p>모델명, BOM, 공정 연결 상태를 점검합니다.</p>
                            </div>
                            <span class="badge ok">권장</span>
                        </li>
                    </ul>
                </div>
            </section>

            <section class="grid-bottom">
                <div class="card">
                    <div class="section-title">
                        <h2>최근 변경 기준정보</h2>
                        <span>최근 5건</span>
                    </div>

                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th>구분</th>
                                    <th>코드/번호</th>
                                    <th>항목명</th>
                                    <th>수정자</th>
                                    <th>수정일</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="common" items="${list}">
                                <tr>
                                    <td>${ common. }</td>
                                    <td>${ common. }</td>
                                    <td>${ common. }</td>
                                    <td>${ common. }</td>
                                    <td>${ common. }</td>
                                </tr>
                               </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="card">
                    <div class="section-title">
                        <h2>기준정보 점검 현황</h2>
                        <span>이번 주</span>
                    </div>

                    <ul class="status-list">
                        <li><span>BOM 검토</span><span class="badge ok">완료</span></li>
                        <li><span>공정 순서 검토</span><span class="badge ok">완료</span></li>
                        <li><span>품목코드 정리</span><span class="badge warn">진행중</span></li>
                        <li><span>완제품 모델 점검</span><span class="badge warn">진행중</span></li>
                        <li><span>중복 코드 확인</span><span class="badge danger">3건</span></li>
                    </ul>
                </div>
            </section>
        </main>
    </div>
</body>

</html>