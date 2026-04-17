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
}