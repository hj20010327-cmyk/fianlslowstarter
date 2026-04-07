package stock;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 재고관리 DB CRUD 연결
 * 사용자의 요청 
 * DB와 JSP 연결역할
 */
@WebServlet("/stockList") 
public class StockController extends HttpServlet {

    // DB에 접근하기 위한 DAO 객체 생성
    private StockDAO dao = new StockDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        //  한글 깨짐 방지를 위한 인코딩 설정
        request.setCharacterEncoding("UTF-8");

        //  검색 파라미터 받기
        String searchCode = request.getParameter("searchCode"); // 품목코드
        String searchName = request.getParameter("searchName"); // 품목명 
        String searchType = request.getParameter("searchType"); // 구분(자재/제품) 

        
        List<StockDTO> list = dao.selectSearch(searchCode, searchName, searchType);

        
        request.setAttribute("list", list);

       
        request.getRequestDispatcher("/stock.jsp").forward(request, response);
        
    }
}