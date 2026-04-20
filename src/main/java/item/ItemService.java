package item;

import java.util.List;

public class ItemService {

	private ItemDAO itemDAO = new ItemDAO();

	public List<ItemDTO> getItemList(String keyword, String status, String itemType, int startRow, int endRow) {
		return itemDAO.selectItemList(keyword, status, itemType, startRow, endRow);
	}

	public int getItemCount(String keyword, String status, String itemType) {
		return itemDAO.selectItemCount(keyword, status, itemType);
	}

	public int getItemCountByCondition(String keyword, String status, String itemType, String targetType,
			String targetStatus) {
		return itemDAO.selectItemCountByCondition(keyword, status, itemType, targetType, targetStatus);
	}

	public ItemDTO getItemOne(int itemKey) {
		return itemDAO.selectItemOne(itemKey);
	}

	public int insertItem(ItemDTO dto) {
		return itemDAO.insertItem(dto);
	}

	public int updateItem(ItemDTO dto) {
		return itemDAO.updateItem(dto);
	}

}
