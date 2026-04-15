package quality;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/qualityList")
public class QualityController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String p_ = request.getParameter("p");
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        int page = (p_ != null && !p_.isEmpty()) ? Integer.parseInt(p_) : 1;
        int pageSize = 10;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        QualityService service = new QualityService();
        List<QualityDTO> list = service.selectPage(startRow, endRow, keyword);
        int totalCount = service.getTotalCount(keyword);
        int totalPage = (int)Math.ceil((double)totalCount / pageSize);

        request.setAttribute("list", list);
        request.setAttribute("totalPage", totalPage);
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}