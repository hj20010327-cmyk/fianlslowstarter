package quality;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/qualityList")
public class QualityController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        request.setCharacterEncoding("utf-8");
        
        String searchCode = request.getParameter("searchCode");
        String pStr = request.getParameter("page");
        
        int curPage = (pStr == null || pStr.isEmpty()) ? 1 : Integer.parseInt(pStr);
        int size = 10;

        int startRow = (curPage - 1) * size + 1;
        int endRow = curPage * size;

        QualityService service = new QualityService();
        
        List<QualityDTO> list = service.getSearchList(searchCode, startRow, endRow);
        Map<String, Object> pageInfo = service.getPageInfo(curPage, size, searchCode);
        
        request.setAttribute("list", list);
        request.setAttribute("p", pageInfo);
        request.setAttribute("searchCode", searchCode);
        
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}