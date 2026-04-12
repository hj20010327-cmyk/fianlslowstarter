package stock;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




@WebServlet("/stockDelete")
public class stockDeleteController extends HttpServlet {




    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {




        // 삭제할 재고의 키값 받기
        int stockKey = Integer.parseInt(request.getParameter("stockKey"));




        // 서비스 호출
        // StockService service = new StockService();
        // service.deleteStock(stockKey);




        // 삭제 후 목록으로 리다이렉트
        response.sendRedirect("stockList");


    }


}