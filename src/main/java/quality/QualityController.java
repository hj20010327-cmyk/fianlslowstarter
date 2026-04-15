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
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 한글 처리
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8;");

        // 검색값 받기
        String qualityCode = request.getParameter("qualityCode");
        String status = request.getParameter("status");
        String inspectDate = request.getParameter("inspectDate");

        // 현재 페이지
        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null && !pageStr.trim().equals("")) {
            page = Integer.parseInt(pageStr);
        }

        // 페이지당 개수
        int pageSize = 10;
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        QualityService service = new QualityService();
        List<QualityDTO> list = null;
        int totalCount = 0;

        // 검색 조건 없으면 전체조회
        if ((qualityCode == null || qualityCode.trim().equals(""))
                && (status == null || status.trim().equals(""))
                && (inspectDate == null || inspectDate.trim().equals(""))) {

            list = service.selectPage(startRow, endRow);
            totalCount = service.getTotalCount();

        } else {
            list = service.searchPage(qualityCode, status, inspectDate, startRow, endRow);
            totalCount = service.getSearchCount(qualityCode, status, inspectDate);
        }

        // 전체 페이지 수 계산
        int totalPage = (int) Math.ceil((double) totalCount / pageSize);

        // 담당자 목록 조회
        // 등록/수정 모달의 select 박스에서 사용
        List<QualityDTO> userList = service.getUserList();

        // JSP로 데이터 넘기기
        request.setAttribute("userList", userList);
        request.setAttribute("list", list);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);

        // 검색값 유지
        request.setAttribute("qualityCode", qualityCode);
        request.setAttribute("status", status);
        request.setAttribute("inspectDate", inspectDate);

        // JSP 이동
        request.getRequestDispatcher("/quality.jsp").forward(request, response);
    }
}