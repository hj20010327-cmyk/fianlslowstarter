document.addEventListener("DOMContentLoaded", function () {
    ProductionPage.init();
});

const ProductionPage = {
    init() {
        this.cacheDom();
        this.bindEvents();
        this.bindSearch();
    },

    cacheDom() {
        this.addBtn = document.getElementById("btnAddProduction");
        this.form = document.getElementById("productionForm");

        this.prodDate = document.getElementById("prod_date");
        this.prodCode = document.getElementById("prod_code");

        this.workOrderSelect = document.getElementById("workOrderSelect");
        this.qualitySelect = document.getElementById("qualitySelect");

        this.itemName = document.getElementById("item_name");
        this.planQty = document.getElementById("plan_qty");
        this.workUserName = document.getElementById("work_user_name");
        this.inspectQty = document.getElementById("inspect_qty");
        this.goodQty = document.getElementById("good_qty");
        this.defectQty = document.getElementById("defect_qty");
        this.qcStatus = document.getElementById("qc_status");
        this.defectReason = document.getElementById("defect_reason");

        this.planKey = document.getElementById("plan_key");
        this.cmd = document.getElementById("cmd");
        this.prodKey = document.getElementById("prod_key");

        this.searchKeyword = document.querySelector('input[name="keyword"]');
        this.editLinks = document.querySelectorAll(".prod-edit-link");
    },

    getToday() {
        const now = new Date();
        return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, "0")}-${String(now.getDate()).padStart(2, "0")}`;
    },

    bindEvents() {
        if (this.addBtn) {
            this.addBtn.addEventListener("click", () => {
                this.resetModal();
                openModal("생산실적 신규 등록");
            });
        }

        if (this.workOrderSelect) {
            this.workOrderSelect.addEventListener("change", () => {
                const selected = this.workOrderSelect.options[this.workOrderSelect.selectedIndex];
                if (!selected || !selected.value) {
                    this.resetDetailFields();
                    return;
                }

                this.itemName.value = selected.dataset.itemName || "";
                this.planQty.value = selected.dataset.planQty || "";
                this.workUserName.value = selected.dataset.workUserName || "";
                this.inspectQty.value = selected.dataset.inspectQty || "";
                this.goodQty.value = selected.dataset.goodQty || "";
                this.defectQty.value = selected.dataset.defectQty || "";
                this.qcStatus.value = selected.dataset.qcStatus || "";
                this.defectReason.value = selected.dataset.defectReason || "";
                this.planKey.value = selected.dataset.planKey || "";

                if (this.qualitySelect) {
                    this.qualitySelect.innerHTML = "";
                    const option = document.createElement("option");
                    option.value = selected.dataset.qualityKey || "";
                    option.textContent = `Q-${selected.dataset.qualityKey || ""} / 양품:${selected.dataset.goodQty || 0} / 불량:${selected.dataset.defectQty || 0}`;
                    option.selected = true;
                    this.qualitySelect.appendChild(option);
                }
            });
        }

        this.editLinks.forEach(link => {
            link.addEventListener("click", () => {
                this.fillEditModal(link.dataset);
                openModal("생산실적 수정");
            });
        });

        if (this.form) {
            this.form.addEventListener("submit", (e) => {
                if (!this.prodDate.value) {
                    alert("생산일을 입력해주세요.");
                    this.prodDate.focus();
                    e.preventDefault();
                    return;
                }

                if (!this.prodCode.value) {
                    alert("생산코드를 입력해주세요.");
                    this.prodCode.focus();
                    e.preventDefault();
                    return;
                }

                if (!this.workOrderSelect.value) {
                    alert("작업지시를 선택해주세요.");
                    this.workOrderSelect.focus();
                    e.preventDefault();
                    return;
                }

                if (!this.qualitySelect.value) {
                    alert("품질결과를 선택해주세요.");
                    this.qualitySelect.focus();
                    e.preventDefault();
                    return;
                }

                if (!confirm(this.cmd.value === "update" ? "수정하시겠습니까?" : "등록하시겠습니까?")) {
                    e.preventDefault();
                }
            });
        }
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

    resetDetailFields() {
        if (this.itemName) this.itemName.value = "";
        if (this.planQty) this.planQty.value = "";
        if (this.workUserName) this.workUserName.value = "";
        if (this.inspectQty) this.inspectQty.value = "";
        if (this.goodQty) this.goodQty.value = "";
        if (this.defectQty) this.defectQty.value = "";
        if (this.qcStatus) this.qcStatus.value = "";
        if (this.defectReason) this.defectReason.value = "";
        if (this.planKey) this.planKey.value = "";

        if (this.qualitySelect) {
            this.qualitySelect.innerHTML = '<option value="">선택하세요</option>';
        }
    },

    resetModal() {
        if (this.cmd) this.cmd.value = "insert";
        if (this.prodKey) this.prodKey.value = "";
        if (this.prodDate) this.prodDate.value = this.getToday();
        if (this.prodCode) this.prodCode.value = "";

        if (this.workOrderSelect) this.workOrderSelect.value = "";
        this.resetDetailFields();
    },

    fillEditModal(data) {
        if (this.cmd) this.cmd.value = "update";
    if (this.prodKey) this.prodKey.value = data.prodKey || "";
    if (this.prodDate) this.prodDate.value = data.prodDate || "";
    if (this.prodCode) this.prodCode.value = data.prodCode || "";
    if (this.planKey) this.planKey.value = data.planKey || "";

    if (this.workOrderSelect) {
        this.workOrderSelect.innerHTML = "";

        const option = document.createElement("option");
        option.value = data.workOrderKey || "";
        option.textContent = data.workOrderCode || "";
        option.selected = true;

        option.dataset.planKey = data.planKey || "";
        option.dataset.itemName = data.itemName || "";
        option.dataset.workUserName = data.workUserName || "";
        option.dataset.planQty = data.planQty || "";
        option.dataset.qualityKey = data.qualityKey || "";
        option.dataset.goodQty = data.goodQty || "";
        option.dataset.qcStatus = data.qcStatus || "";

        this.workOrderSelect.appendChild(option);
    }

    if (this.qualitySelect) {
        this.qualitySelect.innerHTML = "";

        const qOption = document.createElement("option");
        qOption.value = data.qualityKey || "";
        qOption.textContent = `Q-${data.qualityKey || ""} / 양품:${data.goodQty || 0}`;
        qOption.selected = true;

        this.qualitySelect.appendChild(qOption);
    }

    if (this.itemName) this.itemName.value = data.itemName || "";
    if (this.planQty) this.planQty.value = data.planQty || "";
    if (this.workUserName) this.workUserName.value = data.workUserName || "";
    if (this.goodQty) this.goodQty.value = data.goodQty || "";
    if (this.qcStatus) this.qcStatus.value = data.qcStatus || "";
    }
};