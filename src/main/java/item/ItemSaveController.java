package item;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/itemsave")
public class ItemSaveController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ItemService itemService = new ItemService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String cmd = nvl(request.getParameter("cmd"));
        String itemKeyStr = nvl(request.getParameter("item_key"));
        String itemCode = nvl(request.getParameter("item_code"));
        String itemName = nvl(request.getParameter("item_name"));
        String itemType = nvl(request.getParameter("item_type"));
        String spec = nvl(request.getParameter("spec"));
        String unit = nvl(request.getParameter("unit"));
        String priceStr = nvl(request.getParameter("price"));
        String safeQtyStr = nvl(request.getParameter("safe_qty"));
        String status = nvl(request.getParameter("status"));

        try {
            if (itemName.isEmpty() || itemType.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/item?msg=fail");
                return;
            }

            ItemDTO dto = new ItemDTO();
            dto.setItem_key("update".equals(cmd) ? parseInt(itemKeyStr) : 0);
            dto.setItem_code(itemCode);
            dto.setItem_name(itemName);
            dto.setItem_type(itemType);
            dto.setSpec(spec);
            dto.setUnit(unit);
            dto.setPrice(parseIntZero(priceStr));
            dto.setSafe_qty(parseIntZero(safeQtyStr));
            dto.setStatus(status.isEmpty() ? "Y" : status);

            int result;
            if ("update".equals(cmd)) {
                result = itemService.updateItem(dto);
            } else {
                result = itemService.insertItem(dto);
            }

            if (result > 0) {
                response.sendRedirect(request.getContextPath() + "/item?msg=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/item?msg=fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/item?msg=fail");
        }
    }

    private int parseInt(String str) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("필수 key 값이 비어 있습니다.");
        }
        return Integer.parseInt(str.trim());
    }

    private int parseIntZero(String str) {
        if (str == null || str.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(str.trim());
    }

    private String nvl(String str) {
        return str == null ? "" : str.trim();
    }

}
