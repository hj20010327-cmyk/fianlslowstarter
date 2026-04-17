package quality;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;

@WebServlet("/quality/delete")
public class QualityDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        HttpSession session = request.getSession();
        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");

        // 관리자/슈퍼바이저만 삭제 가능
        if (loginUser == null ||
           (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
            response.sendRedirect(request.getContextPath() + "/quality");
            return;
        }

        try {
            // JSP 체크박스 name을 quality_key 로 맞춤
            String[] qualityKeys = request.getParameterValues("quality_key");

            if (qualityKeys != null && qualityKeys.length > 0) {
                QualityService service = new QualityService();
                service.deleteQuality(qualityKeys);
            }

            response.sendRedirect(request.getContextPath() + "/qualityList");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('삭제 중 오류가 발생했습니다.'); history.back();</script>");
        }
    }
}