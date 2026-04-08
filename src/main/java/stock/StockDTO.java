package stock;

/**
 * 재고관리 데이터 전송 객체 (DTO)
 */
public class StockDTO {

	// [멤버 변수] 
	private String lot_key;   // 품목 코드 (DB: ITEM_CODE)
	private String name;      // 품목명 (DB: ITEM_NAME)
	private String type;      // 구분 (DB: ITEM_TYPE)
	private String spec;      // 규격 (DB: SPEC)
	private int remain;       // 현재고 (DB: REMAIN)
	private String vender;    // 공급업체 (DB: VENDER)

	
	public StockDTO() {
	}

	
	// [Getter & Setter] 
	public String getLot_key() {
		return lot_key;
	}

	public void setLot_key(String lot_key) {
		this.lot_key = lot_key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public int getRemain() {
		return remain;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

}