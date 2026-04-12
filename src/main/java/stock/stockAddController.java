package stock;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/stockAdd")
public class stockAddController extends HttpServlet {




    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {


        request.setCharacterEncoding("utf-8");




        // JSP에서 보낸 파라미터 받기 (DB 컬럼명 참고)
        String lot = request.getParameter("lot");
        int inQty = Integer.parseInt(request.getParameter("inQty"));
        int currentQty = Integer.parseInt(request.getParameter("currentQty"));
        int safeQty = Integer.parseInt(request.getParameter("safeQty"));
        int itemKey = Integer.parseInt(request.getParameter("itemKey"));




        // DTO에 담기
        StockDTO dto = new StockDTO();
        dto.setLot(lot);
        dto.setInQty(inQty);
        dto.setCurrentQty(currentQty);
        dto.setSafeQty(safeQty);
        dto.setItemKey(itemKey);




        // 서비스 호출 (서비스에 add 기능을 만들어야 합니다)
        // StockService service = new StockService();
        // service.addStock(dto);




        // 등록 후 목록 페이지로 이동
        response.sendRedirect("stockList");


    }


}