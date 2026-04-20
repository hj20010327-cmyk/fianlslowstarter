package quality;

public class QualityCompleteService {

    // =========================
    // 완료 전용 DAO
    // =========================
    private QualityCompleteDAO dao = new QualityCompleteDAO();

    // =========================
    // 품질 완료 처리
    // =========================
    public QualityCompleteResult completeQuality(String[] qualityKeys) {
        return dao.completeQuality(qualityKeys);
    }
}