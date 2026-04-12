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
    <title>AUTO MES | 대시보드</title>
    <script src="./asset/js/common.js" defer></script>
    <link rel="stylesheet" href="./asset/css/common.css" />
    <link rel="stylesheet" href="./asset/css/page.css" />
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
                    <li class="active"><a href="./index.jsp">대시보드</a></li>
                </ul>
            </div>

            <div class="snb-section">
                <div class="snb-title">기준관리</div>
                <ul class="snb-menu">
                    <li><a href="./master.jsp">기준관리</a></li>
                    <li><a href="./bom.jsp">BOM</a></li>
                    <li><a href="./process.jsp">공정</a></li>
                </ul>
            </div>
            
            <div class="snb-section">
                <div class="snb-title">생산관리</div>
                <ul class="snb-menu">
                    <li><a href="./workorder.jsp">작업지시 <span class="menu-badge">4</span></a></li>
                    <li><a href="./plan.jsp">생산계획 <span class="menu-badge">2</span></a></li>
                </ul>
            </div>
            <div class="snb-section">
                <div class="snb-title">재고관리</div>
                <ul class="snb-menu">
                    <li><a href="./stock.jsp">재고</a></li>
                    <li><a href="./product.html">완제품</a></li>
                    <li><a href="./item.html">자재</a></li>
                </ul>
            </div>
            
            <div class="snb-section">
                <div class="snb-title">품질관리</div>
                <ul class="snb-menu">
                    <li><a href="./quality.jsp">품질<span class="menu-badge">2</span></a></li>
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
                    <li><a href="./user">사용자관리</a></li>
                    <li><a href="./mypage">마이페이지</a></li>
                </ul>
            </div>
        </aside>

        <div class="snb-overlay" id="snbOverlay"></div>

        <main class="content">
            <div class="page-head">
                <div class="page-head-left">
                    <h1>대시보드</h1>
                    <p>자동차 콤프레셔 공장의 생산, 품질, 재고 현황을 한눈에 확인할 수 있는 메인 페이지입니다.</p>
                </div>

                <div class="page-actions">
                    <button class="btn" type="button">공지사항</button>
                    <button class="btn primary" type="button">오늘 작업 보기</button>
                </div>
            </div>

            <section class="hero-banner">
                <div>
                    <h2>자동차 콤프레셔 공장 통합 대시보드</h2>
                    <p>작업지시, 생산실적, 품질, 재고 상태를 한 화면에서 빠르게 확인합니다.</p>
                </div>

                <div class="pill-row">
                    <span class="pill">오늘 작업지시 12건</span>
                    <span class="pill">불량 알람 2건</span>
                    <span class="pill">부족 재고 5건</span>
                </div>
            </section>

            <section class="top-cards">
                <div class="card">
                    <div class="card-label">오늘 작업지시</div>
                    <div class="card-value">12건</div>
                    <div class="card-meta">진행중 8건 / 대기 4건</div>
                </div>

                <div class="card">
                    <div class="card-label">오늘 생산량</div>
                    <div class="card-value">1,280EA</div>
                    <div class="card-meta">목표 대비 92%</div>
                </div>

                <div class="card">
                    <div class="card-label">불량 수량</div>
                    <div class="card-value">44EA</div>
                    <div class="card-meta">불량률 3.5%</div>
                </div>

                <div class="card">
                    <div class="card-label">재고 부족 품목</div>
                    <div class="card-value">5건</div>
                    <div class="card-meta">즉시 발주 필요 2건</div>
                </div>
            </section>

            <section class="grid-2">
                <div class="card">
                    <div class="section-title">
                        <h2>시간대별 생산 현황</h2>
                        <span>금일 기준</span>
                    </div>

                    <div class="chart-box">
                        <div class="bar-item">
                            <strong>80EA</strong>
                            <div class="bar" style="height:42%"></div>
                            <p>08시</p>
                        </div>
                        <div class="bar-item">
                            <strong>95EA</strong>
                            <div class="bar" style="height:52%"></div>
                            <p>09시</p>
                        </div>
                        <div class="bar-item">
                            <strong>120EA</strong>
                            <div class="bar" style="height:66%"></div>
                            <p>10시</p>
                        </div>
                        <div class="bar-item">
                            <strong>138EA</strong>
                            <div class="bar" style="height:78%"></div>
                            <p>11시</p>
                        </div>
                        <div class="bar-item">
                            <strong>145EA</strong>
                            <div class="bar" style="height:86%"></div>
                            <p>12시</p>
                        </div>
                        <div class="bar-item">
                            <strong>110EA</strong>
                            <div class="bar" style="height:62%"></div>
                            <p>13시</p>
                        </div>
                        <div class="bar-item">
                            <strong>155EA</strong>
                            <div class="bar" style="height:92%"></div>
                            <p>14시</p>
                        </div>
                    </div>
                </div>

                <div class="card">
                    <div class="section-title">
                        <h2>라인 / 품질 상태</h2>
                        <span>실시간</span>
                    </div>

                    <ul class="status-list">
                        <li><span>조립 1라인</span><span class="badge ok">정상</span></li>
                        <li><span>조립 2라인</span><span class="badge ok">정상</span></li>
                        <li><span>가공 라인</span><span class="badge warn">점검중</span></li>
                        <li><span>검사 설비</span><span class="badge ok">정상</span></li>
                        <li><span>불량 알람</span><span class="badge danger">2건</span></li>
                    </ul>
                </div>
            </section>

            <section class="grid-bottom">
                <div class="card">
                    <div class="section-title">
                        <h2>오늘 작업지시 현황</h2>
                        <span>workorder.html 연동 예정</span>
                    </div>

                    <div class="table-wrap">
                        <table>
                            <thead>
                                <tr>
                                    <th>작업번호</th>
                                    <th>제품명</th>
                                    <th>공정</th>
                                    <th>진행률</th>
                                    <th>상태</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>WO-260330-001</td>
                                    <td>COMP-A100</td>
                                    <td>조립</td>
                                    <td>
                                        <div class="progress-wrap">
                                            <div class="progress-bar" style="width:75%"></div>
                                        </div>
                                        75%
                                    </td>
                                    <td>진행중</td>
                                </tr>
                                <tr>
                                    <td>WO-260330-002</td>
                                    <td>COMP-B210</td>
                                    <td>가공</td>
                                    <td>
                                        <div class="progress-wrap">
                                            <div class="progress-bar" style="width:48%"></div>
                                        </div>
                                        48%
                                    </td>
                                    <td>진행중</td>
                                </tr>
                                <tr>
                                    <td>WO-260330-003</td>
                                    <td>COMP-E300</td>
                                    <td>검사</td>
                                    <td>
                                        <div class="progress-wrap">
                                            <div class="progress-bar" style="width:100%"></div>
                                        </div>
                                        100%
                                    </td>
                                    <td>완료</td>
                                </tr>
                                <tr>
                                    <td>WO-260330-004</td>
                                    <td>COMP-H120</td>
                                    <td>포장</td>
                                    <td>
                                        <div class="progress-wrap">
                                            <div class="progress-bar" style="width:22%"></div>
                                        </div>
                                        22%
                                    </td>
                                    <td>대기</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <div class="card">
                    <div class="section-title">
                        <h2>알림 / 공지</h2>
                        <span>오늘 기준</span>
                    </div>

                    <ul class="notice-list">
                        <li>
                            <div>
                                <strong>가공 라인 정기 점검 예정</strong>
                                <p>설비관리 · 오전 09:00</p>
                            </div>
                            <span class="badge warn">점검</span>
                        </li>
                        <li>
                            <div>
                                <strong>COMP-A100 자재 재고 부족</strong>
                                <p>재고관리 · 오전 08:35</p>
                            </div>
                            <span class="badge danger">긴급</span>
                        </li>
                        <li>
                            <div>
                                <strong>조립 1라인 생산 목표 90% 달성</strong>
                                <p>생산실적 · 오전 08:10</p>
                            </div>
                            <span class="badge ok">정상</span>
                        </li>
                        <li>
                            <div>
                                <strong>금일 품질 검사 계획 등록 완료</strong>
                                <p>품질관리 · 오전 07:50</p>
                            </div>
                            <span class="badge ok">완료</span>
                        </li>
                    </ul>
                </div>
            </section>
        </main>
    </div>
</body>

</html>