package stock;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stockList")
public class StockController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // [조회 로직] TB_ITEM 테이블 기반 검색 및 페이징
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/stockList (재고 목록조회) 실행");
        
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");
        
        // 검색 파라미터 변경 (품목코드, 품목명)
        String searchCode = request.getParameter("searchCode");
        String searchName = request.getParameter("searchName");
        
        String pStr = request.getParameter("page");
        int curPage = (pStr == null || pStr.isEmpty()) ? 1 : Integer.parseInt(pStr);
        int size = 10; // 재고 목록은 조금 더 많이 보이게 10개로 조정 가능

        int startRow = (curPage - 1) * size + 1;
        int endRow = curPage * size;

        // StockService 객체 생성 (품질 서비스 복사해서 새로 만드셔야 함)
        StockService stockService = new StockService();
        
        // TB_ITEM 데이터를 가져오는 메서드 호출
        List<StockDTO> list = stockService.getSearchList(searchCode, searchName, startRow, endRow);
        Map<String, Object> pageInfo = stockService.getPageInfo(curPage, size);
        
        System.out.println("조회된 재고 품목 개수: " + list.size());
        
        request.setAttribute("list", list);
        request.setAttribute("p", pageInfo); 
        request.setAttribute("searchCode", searchCode);
        request.setAttribute("searchName", searchName);
        
        // 재고관리 페이지(JSP)로 이동
        request.getRequestDispatcher("/stock.jsp").forward(request, response);
    }

    // [저장/수정 로직] 안전재고 수량 수정 등 처리
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        // PK인 ITEM_CODE 또는 ITEM_KEY를 확인
        String itemCode = request.getParameter("item_code");
        
        if (itemCode != null && !itemCode.isEmpty()) {
            System.out.println("재고 데이터(안전재고 등) 수정 프로세스 시작");
            
            try {
                // 수정할 정보들 파라미터 수집 (이미지 컬럼 기준)
                String itemName = request.getParameter("item_name");
                String spec = request.getParameter("spec");
                String safeQtyStr = request.getParameter("safe_qty");

                StockDTO dto = new StockDTO();
                dto.setItem_code(itemCode);
                dto.setItem_name(itemName);
                dto.setSpec(spec);
                
                // 안전재고 숫자 변환
                if(safeQtyStr != null && !safeQtyStr.isEmpty()) {
                    dto.setSafe_qty(Integer.parseInt(safeQtyStr));
                }

                StockService stockService = new StockService();
                
                // 서비스에서 업데이트(또는 인서트) 처리
                int result = stockService.updateStockInfo(dto); 

                if (result > 0) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            
        } else {
            // 데이터 없으면 다시 목록으로
            doGet(request, response);
        }
    }
}