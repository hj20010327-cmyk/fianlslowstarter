package quality;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.LoginDTO;

@WebServlet("/qualityList")
public class QualityController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // =========================
    // 목록 조회
    // =========================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        String qualityCode = request.getParameter("qualityCode");
        String itemName = request.getParameter("itemName");
        String status = request.getParameter("status");
        String inspectDate = request.getParameter("inspectDate");

        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null && !pageStr.trim().equals("")) {
            page = Integer.parseInt(pageStr);
        }

        int pageSize = 10;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        QualityService service = new QualityService();
        List<QualityDTO> list = null;
        int totalCount = 0;

        if ((qualityCode == null || qualityCode.trim().equals(""))
                && (itemName == null || itemName.trim().equals(""))
                && (status == null || status.trim().equals(""))
                && (inspectDate == null || inspectDate.trim().equals(""))) {

            list = service.selectPage(startRow, endRow);
            totalCount = service.getTotalCount();

        } else {
            list = service.searchPage(qualityCode, itemName, status, inspectDate, startRow, endRow);
            totalCount = service.getSearchCount(qualityCode, itemName, status, inspectDate);
        }

        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        List<QualityDTO> userList = service.getUserList();
        List<QualityDTO> workOrderList = service.getWorkOrderList();
        List<String> qualityCodeList = service.getQualityCodeList();
        List<String> itemNameList = service.getItemNameList();

        request.setAttribute("userList", userList);
        request.setAttribute("workOrderList", workOrderList);
        request.setAttribute("qualityCodeList", qualityCodeList);
        request.setAttribute("itemNameList", itemNameList);
        request.setAttribute("list", list);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);

        request.setAttribute("qualityCode", qualityCode);
        request.setAttribute("itemName", itemName);
        request.setAttribute("status", status);
        request.setAttribute("inspectDate", inspectDate);

        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }

    // =========================
    // 완료 처리
    // - 선택 완료 / 단건 완료
    // - 완료 후 자동 이동 없이
    //   "완료되었습니다."만 띄움
    // =========================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        HttpSession session = request.getSession();
        LoginDTO loginUser = (LoginDTO) session.getAttribute("dto");

        // 관리자 / 슈퍼바이저만 완료 가능
        if (loginUser == null ||
            (!"관리자".equals(loginUser.getUser_role()) && !"슈퍼바이저".equals(loginUser.getUser_role()))) {
            response.sendRedirect(request.getContextPath() + "/qualityList");
            return;
        }

        String action = request.getParameter("action");

        try {
            QualityService service = new QualityService();

            // =========================
            // 체크박스 선택 후 완료
            // =========================
            if ("completeSelected".equals(action)) {
                String[] qualityKeys = request.getParameterValues("quality_key");

                // 선택된 값이 있을 때만 완료 처리 실행
                if (qualityKeys != null && qualityKeys.length > 0) {
                    service.completeQuality(qualityKeys);
                }

                // [수정] 자동 이동 없이 완료 문구만 출력
                response.getWriter().println(
                    "<script>" +
                    "alert('완료되었습니다.');" +
                    "history.back();" +
                    "</script>"
                );
                return;
            }

            // =========================
            // 단건 완료
            // =========================
            if ("completeOne".equals(action)) {
                String qualityKey = request.getParameter("quality_key");

                // 단건 완료 실행
                if (qualityKey != null && !qualityKey.trim().equals("")) {
                    service.completeQuality(new String[] { qualityKey });
                }

                // [수정] 자동 이동 없이 완료 문구만 출력
                response.getWriter().println(
                    "<script>" +
                    "alert('완료되었습니다.');" +
                    "history.back();" +
                    "</script>"
                );
                return;
            }

            response.sendRedirect(request.getContextPath() + "/qualityList");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println(
                "<script>" +
                "alert('오류 발생');" +
                "history.back();" +
                "</script>"
            );
        }
    }
}