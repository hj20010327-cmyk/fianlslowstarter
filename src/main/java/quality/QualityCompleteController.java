package quality;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;

@WebServlet("/qualityComplete")
public class QualityCompleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // =========================
    // 완료 처리 전용 컨트롤러
    // - 선택 완료
    // - 단건 완료
    // =========================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        HttpSession session = request.getSession();
        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");

        // =========================
        // 관리자 / 슈퍼바이저만 완료 가능
        // =========================
        if (loginUser == null ||
            (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
            response.getWriter().println(
                "<script>" +
                "alert('완료 권한이 없습니다.');" +
                "location.href='" + request.getContextPath() + "/qualityList';" +
                "</script>"
            );
            return;
        }

        String action = request.getParameter("action");

        try {
            QualityCompleteService service = new QualityCompleteService();
            QualityCompleteResult result = null;

            // =========================
            // 체크박스 선택 완료
            // =========================
            if ("completeSelected".equals(action)) {
                String[] qualityKeys = request.getParameterValues("quality_key");

                if (qualityKeys == null || qualityKeys.length == 0) {
                    response.getWriter().println(
                        "<script>" +
                        "alert('완료할 항목을 선택하세요.');" +
                        "history.back();" +
                        "</script>"
                    );
                    return;
                }

                result = service.completeQuality(qualityKeys);
            }

            // =========================
            // 단건 완료
            // =========================
            else if ("completeOne".equals(action)) {
                String qualityKey = request.getParameter("quality_key");

                if (qualityKey == null || qualityKey.trim().equals("")) {
                    response.getWriter().println(
                        "<script>" +
                        "alert('완료할 항목이 없습니다.');" +
                        "history.back();" +
                        "</script>"
                    );
                    return;
                }

                result = service.completeQuality(new String[] { qualityKey });
            }

            // =========================
            // 잘못된 요청 방지
            // =========================
            else {
                response.getWriter().println(
                    "<script>" +
                    "alert('잘못된 요청입니다.');" +
                    "location.href='" + request.getContextPath() + "/qualityList';" +
                    "</script>"
                );
                return;
            }

            // =========================
            // 완료 결과 메시지 처리
            // =========================
            if (result != null) {
                if (result.getSuccessCount() > 0) {
                    response.getWriter().println(
                        "<script>" +
                        "alert('완료되었습니다.');" +
                        "location.href='" + request.getContextPath() + "/qualityList';" +
                        "</script>"
                    );
                } else if (result.getAlreadyCompletedCount() > 0) {
                    response.getWriter().println(
                        "<script>" +
                        "alert('이미 완료된 항목입니다.');" +
                        "location.href='" + request.getContextPath() + "/qualityList';" +
                        "</script>"
                    );
                } else {
                    response.getWriter().println(
                        "<script>" +
                        "alert('완료 처리 실패');" +
                        "history.back();" +
                        "</script>"
                    );
                }
                return;
            }

            response.sendRedirect(request.getContextPath() + "/qualityList");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                "<script>" +
                "alert('완료 처리 중 오류가 발생했습니다.');" +
                "history.back();" +
                "</script>"
            );
        }
    }
}