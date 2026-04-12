package stock;


import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




// 주소를 /stockList로 잡아두었습니다.
@WebServlet("/stockList")
public class StockController extends HttpServlet {




    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {


        request.setCharacterEncoding("utf-8");




        // 페이지 번호 가져오기
        String p_ = request.getParameter("p");
        int page = 1;


        if (p_ != null && !p_.equals("")) {
            page = Integer.parseInt(p_);
        }




        // 서비스 객체 생성 및 목록 가져오기
        StockService service = new StockService();
        List<StockDTO> list = service.getList(page);




        // JSP로 데이터 실어 보내기
        request.setAttribute("list", list);




        // stock.jsp 파일로 이동 (파일명이 다르면 수정하세요!)
        request.getRequestDispatcher("/stock.jsp").forward(request, response);


    }


}