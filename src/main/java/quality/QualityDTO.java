package quality;

public class QualityDTO {
    // 필드 선언 (DB 컬럼과 가급적 이름을 맞춥니다)
    private String inspect_code;
    private String item_name;
    private String inspect_date;
    private String inspector;
    private String result;

    // Getter/Setter (이게 있어야 외부에서 값을 넣고 뺄 수 있습니다)
    public String getInspect_code() { return inspect_code; }
    public void setInspect_code(String inspect_code) { this.inspect_code = inspect_code; }

    public String getItem_name() { return item_name; }
    public void setItem_name(String item_name) { this.item_name = item_name; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    
    // 나머지 필드(date, inspector)의 Getter/Setter도 동일하게 작성
}