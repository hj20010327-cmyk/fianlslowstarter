document.addEventListener("DOMContentLoaded", function () {
    ProductionPage.init();
});

const ProductionPage = {
    init() {
        this.cacheDom();
        this.bindEvents();
        this.bindValidation();
        this.bindSearch();
    },

    cacheDom() {
        this.addBtn = document.getElementById("btnAddProduction");
        this.form = document.getElementById("productionForm");

        this.prodDate = document.getElementById("prod_date");
        this.inputQty = document.getElementById("input_qty");
        this.goodQty = document.getElementById("good_qty");
        this.defectQty = document.getElementById("defect_qty");

        this.searchKeyword = document.querySelector('input[name="keyword"]');
        this.editLinks = document.querySelectorAll(".prod-edit-link");
    },

    getToday() {
        const now = new Date();
        return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;
    },

    onlyNumber(value) {
        return value.replace(/[^0-9]/g, "");
    },

    bindEvents() {
        // 숫자 입력 제한
        [this.inputQty, this.goodQty, this.defectQty].forEach(el => {
            if (!el) return;
            el.addEventListener("input", () => {
                el.value = this.onlyNumber(el.value);
            });
        });

        // 신규 등록
        if (this.addBtn) {
            this.addBtn.addEventListener("click", () => {
                this.resetModal();
                openModal("생산실적 신규 등록");
            });
        }

        // 수정
        this.editLinks.forEach(link => {
            link.addEventListener("click", () => {
                this.fillEditModal(link.dataset);
                openModal("생산실적 수정");
            });
        });
    },

    resetModal() {
        document.getElementById("modalTitle").innerText = "생산실적 신규 등록";
        document.getElementById("cmd").value = "insert";

        [
            "prod_key","prod_code","item_name","plan_qty","order_qty",
            "input_qty","good_qty","defect_qty","work_user_key",
            "work_user_name","work_order_key","work_order_code",
            "plan_key","remark"
        ].forEach(id => {
            const el = document.getElementById(id);
            if (el) el.value = "";
        });

        this.prodDate.value = this.getToday();

        this.setInsertModeReadonly();
    },

    fillEditModal(data) {
        document.getElementById("modalTitle").innerText = "생산실적 수정";
        document.getElementById("cmd").value = "update";

        Object.keys(data).forEach(key => {
            const el = document.getElementById(key.replace(/([A-Z])/g, "_$1").toLowerCase());
            if (el) el.value = data[key] || "";
        });

        this.setUpdateModeReadonly();
    },

    validateQty() {
        const input = parseInt(this.inputQty?.value || "0", 10);
        const good = parseInt(this.goodQty?.value || "0", 10);
        const defect = parseInt(this.defectQty?.value || "0", 10);

        if (good + defect > input) {
            alert("양품수량 + 불량수량이 투입수량보다 큽니다.");
            this.defectQty.focus();
            return false;
        }
        return true;
    },

    bindValidation() {
        if (!this.form) return;

        this.form.addEventListener("submit", (e) => {
            const cmd = document.getElementById("cmd").value;

            if (!this.prodDate?.value) {
                alert("생산일을 입력해주세요.");
                this.prodDate.focus();
                e.preventDefault();
                return;
            }

            if (!this.inputQty?.value || !this.goodQty?.value || !this.defectQty?.value) {
                alert("수량을 모두 입력해주세요.");
                e.preventDefault();
                return;
            }

            if (!this.validateQty()) {
                e.preventDefault();
                return;
            }

            if (!confirm(cmd === "update" ? "수정하시겠습니까?" : "등록하시겠습니까?")) {
                e.preventDefault();
            }
        });
    },

    bindSearch() {
        if (!this.searchKeyword) return;

        this.searchKeyword.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                this.searchKeyword.form.submit();
            }
        });
    },

    setInsertModeReadonly() {
        [
            "prod_code","prod_date","item_name","plan_qty","order_qty",
            "input_qty","good_qty","defect_qty","work_user_name","work_order_code"
        ].forEach(id => {
            document.getElementById(id)?.removeAttribute("readonly");
        });
    },

    setUpdateModeReadonly() {
        const readonlyFields = ["prod_code","item_name","plan_qty","order_qty","work_user_name","work_order_code"];

        readonlyFields.forEach(id => {
            document.getElementById(id)?.setAttribute("readonly", true);
        });
    }
};