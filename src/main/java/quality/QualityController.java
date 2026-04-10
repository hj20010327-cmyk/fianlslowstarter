package quality;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quality/main")
public class QualityController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("utf-8");
        
        QualityService service = new QualityService();
        List<QualityDTO> list = service.getList();
        
        request.setAttribute("list", list);
        
        // webapp 폴더 바로 아래에 있다면 아래 경로가 맞습니다.
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}