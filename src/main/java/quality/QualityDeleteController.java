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

        // 관리자 / 슈퍼바이저만 삭제 가능
        if (loginUser == null ||
           (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
            response.sendRedirect(request.getContextPath() + "/qualityList");
            return;
        }

        try {
            String[] qualityKeys = request.getParameterValues("quality_key");

            if (qualityKeys == null || qualityKeys.length == 0) {
                response.getWriter().println(
                    "<script>" +
                    "alert('삭제할 항목을 선택하세요.');" +
                    "history.back();" +
                    "</script>"
                );
                return;
            }

            QualityService service = new QualityService();
            int result = service.deleteQuality(qualityKeys);

            if (result > 0) {
                response.getWriter().println(
                    "<script>" +
                    "alert('삭제되었습니다.');" +
                    "location.href='" + request.getContextPath() + "/qualityList';" +
                    "</script>"
                );
            } else {
                response.getWriter().println(
                    "<script>" +
                    "alert('삭제할 수 없는 항목이 있습니다.');" +
                    "history.back();" +
                    "</script>"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                "<script>" +
                "alert('삭제 중 오류가 발생했습니다.');" +
                "history.back();" +
                "</script>"
            );
        }
    }
}