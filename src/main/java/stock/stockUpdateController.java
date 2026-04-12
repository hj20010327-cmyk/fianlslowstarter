package stock;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/stockUpdate")
public class stockUpdateController extends HttpServlet {




    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {


        request.setCharacterEncoding("utf-8");




        // 수정을 위해 PK인 stockKey를 반드시 받아야 합니다
        int stockKey = Integer.parseInt(request.getParameter("stockKey"));
        
        String lot = request.getParameter("lot");
        int inQty = Integer.parseInt(request.getParameter("inQty"));
        int outQty = Integer.parseInt(request.getParameter("outQty"));
        int currentQty = Integer.parseInt(request.getParameter("currentQty"));




        // DTO 구성
        StockDTO dto = new StockDTO();
        dto.setStockKey(stockKey);
        dto.setLot(lot);
        dto.setInQty(inQty);
        dto.setOutQty(outQty);
        dto.setCurrentQty(currentQty);




        // 서비스 호출
        // StockService service = new StockService();
        // service.updateStock(dto);




        // 수정 후 목록으로 이동
        response.sendRedirect("stockList");


    }


}