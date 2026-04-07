package stock;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/stockList")
public class StockController extends HttpServlet {
    private StockDAO dao = new StockDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        //  파라미터 
        String searchCode = request.getParameter("searchCode");
        String searchName = request.getParameter("searchName");
        String searchType = request.getParameter("searchType");

       
        List<StockDTO> list = dao.selectSearch(searchCode, searchName, searchType);

        request.setAttribute("list", list);
        request.getRequestDispatcher("/stock.jsp").forward(request, response);
    }
}