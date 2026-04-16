document.addEventListener("DOMContentLoaded", function () {
    ProductionPage.init();
});

const ProductionPage = {
    init() {
        this.cacheDom();
        this.bindEvents();
        this.bindValidation();
        this.bindSearch();
        this.bindWorkOrderSelect();
    },

    cacheDom() {
        this.addBtn = document.getElementById("btnAddProduction");
        this.form = document.getElementById("productionForm");

        this.prodDate = document.getElementById("prod_date");
        this.goodQty = document.getElementById("good_qty");
        this.defectQty = document.getElementById("defect_qty");

        this.workOrderSelect = document.getElementById("workOrderSelect");
        this.itemNameSelect = document.getElementById("itemNameSelect");
        this.planQtySelect = document.getElementById("planQtySelect");
        this.workUserSelect = document.getElementById("workUserSelect");
        this.goodQtySelect = document.getElementById("goodQtySelect");
        this.defectQtySelect = document.getElementById("defectQtySelect");
        this.planKeyHidden = document.getElementById("planKeyHidden");

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
        [this.goodQty, this.defectQty].forEach(el => {
            if (!el) return;
            el.addEventListener("input", () => {
                el.value = this.onlyNumber(el.value);
            });
        });

        if (this.addBtn) {
            this.addBtn.addEventListener("click", () => {
                this.resetModal();
                openModal("생산실적 신규 등록");
            });
        }

        this.editLinks.forEach(link => {
            link.addEventListener("click", () => {
                this.fillEditModal(link.dataset);
                openModal("생산실적 수정");
            });
        });
    },

    bindWorkOrderSelect() {
        if (!this.workOrderSelect) return;

        this.workOrderSelect.addEventListener("change", () => {
            const selected = this.workOrderSelect.options[this.workOrderSelect.selectedIndex];
            if (!selected || !selected.value) {
                this.resetLinkedSelects();
                return;
            }

            const itemName = selected.dataset.itemName || "";
            const planQty = selected.dataset.planQty || "";
            const workUserKey = selected.dataset.workUserKey || "";
            const workUserName = selected.dataset.workUserName || "";
            const goodQty = selected.dataset.goodQty || "";
            const defectQty = selected.dataset.defectQty || "";
            const planKey = selected.dataset.planKey || "";

            this.setSingleOption(this.itemNameSelect, itemName, itemName);
            this.setSingleOption(this.planQtySelect, planQty, planQty);
            this.setSingleOption(this.workUserSelect, workUserKey, workUserName);
            this.setSingleOption(this.goodQtySelect, goodQty, goodQty);
            this.setSingleOption(this.defectQtySelect, defectQty, defectQty);

            if (this.goodQty) this.goodQty.value = goodQty;
            if (this.defectQty) this.defectQty.value = defectQty;
            if (this.planKeyHidden) this.planKeyHidden.value = planKey;
        });
    },

    setSingleOption(selectEl, value, text) {
        if (!selectEl) return;
        selectEl.innerHTML = "";

        const option = document.createElement("option");
        option.value = value;
        option.textContent = text || "선택하세요";
        option.selected = true;
        selectEl.appendChild(option);
    },

    resetLinkedSelects() {
        [this.itemNameSelect, this.planQtySelect, this.workUserSelect, this.goodQtySelect, this.defectQtySelect].forEach(selectEl => {
            if (!selectEl) return;
            selectEl.innerHTML = '<option value="">선택하세요</option>';
        });

        if (this.goodQty) this.goodQty.value = "";
        if (this.defectQty) this.defectQty.value = "";
        if (this.planKeyHidden) this.planKeyHidden.value = "";
    },

    resetModal() {
        const titleEl = document.getElementById("modalTitle");
        const cmdEl = document.getElementById("cmd");

        if (titleEl) titleEl.innerText = "생산실적 신규 등록";
        if (cmdEl) cmdEl.value = "insert";

        [
            "prod_key", "prod_code", "good_qty", "defect_qty",
            "work_order_key", "work_user_key", "plan_key", "remark"
        ].forEach(id => {
            const el = document.getElementById(id);
            if (el) el.value = "";
        });

        if (this.prodDate) {
            this.prodDate.value = this.getToday();
        }

        if (this.workOrderSelect) {
            this.workOrderSelect.value = "";
        }

        this.resetLinkedSelects();
        this.setInsertModeReadonly();
    },

    fillEditModal(data) {
        const titleEl = document.getElementById("modalTitle");
        const cmdEl = document.getElementById("cmd");

        if (titleEl) titleEl.innerText = "생산실적 수정";
        if (cmdEl) cmdEl.value = "update";

        const fieldMap = {
            prodKey: "prod_key",
            prodCode: "prod_code",
            prodDate: "prod_date",
            goodQty: "good_qty",
            defectQty: "defect_qty",
            workOrderKey: "work_order_key",
            workOrderCode: "work_order_code",
            planKey: "plan_key",
            planCode: "plan_code",
            planQty: "plan_qty",
            itemName: "item_name",
            workUserKey: "work_user_key",
            workUserName: "work_user_name"
        };

        Object.keys(fieldMap).forEach(key => {
            const el = document.getElementById(fieldMap[key]);
            if (el) el.value = data[key] || "";
        });

        if (this.planKeyHidden && data.planKey) {
            this.planKeyHidden.value = data.planKey;
        }

        this.setUpdateModeReadonly();
    },

    bindValidation() {
        if (!this.form) return;

        this.form.addEventListener("submit", (e) => {
            const cmdEl = document.getElementById("cmd");
            const cmd = cmdEl ? cmdEl.value : "insert";

            if (!this.prodDate || !this.prodDate.value) {
                alert("생산일을 입력해주세요.");
                if (this.prodDate) this.prodDate.focus();
                e.preventDefault();
                return;
            }

            if (!this.goodQty || !this.goodQty.value || !this.defectQty || !this.defectQty.value) {
                alert("양품수량과 불량수량을 확인해주세요.");
                e.preventDefault();
                return;
            }

            const good = parseInt(this.goodQty.value || "0", 10);
            const defect = parseInt(this.defectQty.value || "0", 10);

            if (good < 0 || defect < 0) {
                alert("수량은 0 이상이어야 합니다.");
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
        const readonlyFields = ["prod_code"];
        readonlyFields.forEach(id => {
            document.getElementById(id)?.removeAttribute("readonly");
        });
    },

    setUpdateModeReadonly() {
        const readonlyFields = [
            "prod_code", "work_order_code", "plan_code",
            "plan_qty", "item_name", "work_user_name"
        ];

        readonlyFields.forEach(id => {
            document.getElementById(id)?.setAttribute("readonly", true);
        });
    }
};