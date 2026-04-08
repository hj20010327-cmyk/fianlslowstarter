package stock;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/stock.html")
public class StockController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // 서비스 객체 생성
    private StockService stockService = new StockService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/stock doGet 실행");
        
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");
        
       
        String cmd = request.getParameter("cmd");
        
      
        if(cmd == null || cmd.equals("list")) {
            list(request, response);
        } else if(cmd.equals("detail")) {
            detail(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/stock doPost 실행");
        
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");
        
        String cmd = request.getParameter("cmd");
        
        if(cmd.equals("insert")) {
            insert(request, response);
        } else if(cmd.equals("update")) {
            update(request, response);
        } else if(cmd.equals("delete")) {
            delete(request, response);
        }
    }

    // 1. 재고 목록 조회 메서드
    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/stock list 실행");
        
        //  파라미터 확보 
        String searchCode = request.getParameter("searchCode"); // 품목 코드 검색
        String searchName = request.getParameter("searchName"); // 품목명 검색
        
        
        List<StockDTO> list = stockService.getStockList(searchCode, searchName); 
        
        // JSP로 데이터를 보내기 위해 request에 담기
        request.setAttribute("list", list);
        
        System.out.println("조회된 데이터 개수: " + list.size());
        
        
        request.getRequestDispatcher("/stock.jsp").forward(request, response);
    }

    // 2. 재고 신규 등록 메서드
    protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/stock insert 실행");
        
        // [파라미터 확보] 
        String lot_key = request.getParameter("lot_key");
        String name = request.getParameter("name");
        String type = request.getParameter("type"); 
        int remain = Integer.parseInt(request.getParameter("remain"));
        String vender = request.getParameter("vender");
        
        // DTO에 넣기
        StockDTO stockDTO = new StockDTO();
        stockDTO.setLot_key(lot_key);
        stockDTO.setName(name);
        stockDTO.setType(type);
        stockDTO.setRemain(remain);
        stockDTO.setVender(vender);
        
        // DTO 보내기
        int result = stockService.insert(stockDTO);
        
        System.out.println("insert 결과 : " + result);
        
       
        response.sendRedirect("stock.html?cmd=list");
    }

    //  재고 정보 수정 
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/stock update 실행");
        
        // 파라미터 확보
        String lot_key = request.getParameter("lot_key");
        int remain = Integer.parseInt(request.getParameter("remain"));
        
        // DTO에 넣기
        StockDTO stockDTO = new StockDTO();
        stockDTO.setLot_key(lot_key);
        stockDTO.setRemain(remain);
        
        // [서비스로 DTO 보내기]
        int result = stockService.update(stockDTO);
        
        System.out.println("update 결과 : " + result);
        
        response.sendRedirect("stock.html?cmd=list");
    }

    //  재고 삭제 메서드
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/stock delete 실행");
        
        // 파라미터 확보 
        String lot_key = request.getParameter("lot_key");
        
        // [DTO에 넣기]
        StockDTO stockDTO = new StockDTO();
        stockDTO.setLot_key(lot_key);
        
        // [서비스로 DTO 보내기]
        int result = stockService.delete(stockDTO);
        
        System.out.println("delete 결과 : " + result);
        
        response.sendRedirect("stock.html?cmd=list");
    }

    // 5. 상세 조회 메서드
    protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("/stock detail 실행");
        
        String lot_key = request.getParameter("lot_key");
        
        // 서비스에서 상세 정보 한 건 가져오기
        StockDTO dto = stockService.getDetail(lot_key);
        
        request.setAttribute("dto", dto);
        request.getRequestDispatcher("/stockDetail.jsp").forward(request, response);
    }
}