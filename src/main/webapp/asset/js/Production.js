document.addEventListener("DOMContentLoaded", function () {
    const addBtn = document.getElementById("btnAddProduction");
    const form = document.getElementById("productionForm");
    const searchKeyword = document.querySelector('input[name="keyword"]');

    const prodDate = document.getElementById("prod_date");
    const inputQty = document.getElementById("input_qty");
    const goodQty = document.getElementById("good_qty");
    const defectQty = document.getElementById("defect_qty");

    function getToday() {
        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, "0");
        const day = String(now.getDate()).padStart(2, "0");
        return `${year}-${month}-${day}`;
    }

    function onlyNumber(value) {
        return value.replace(/[^0-9]/g, "");
    }

    function resetProductionModal() {
        document.getElementById("modalTitle").innerText = "생산실적 신규 등록";
        document.getElementById("cmd").value = "insert";

        document.getElementById("prod_key").value = "";
        document.getElementById("prod_code").value = "";
        document.getElementById("prod_date").value = getToday();
        document.getElementById("item_name").value = "";
        document.getElementById("plan_qty").value = "";
        document.getElementById("order_qty").value = "";
        document.getElementById("input_qty").value = "";
        document.getElementById("good_qty").value = "";
        document.getElementById("defect_qty").value = "";
        document.getElementById("work_user_key").value = "";
        document.getElementById("work_user_name").value = "";
        document.getElementById("work_order_key").value = "";
        document.getElementById("work_order_code").value = "";
        document.getElementById("plan_key").value = "";
        document.getElementById("remark").value = "";
        
        setInsertModeReadonly();
    }

    function validateProductionQty() {
        if (!inputQty || !goodQty || !defectQty) return true;

        const input = parseInt(inputQty.value || "0", 10);
        const good = parseInt(goodQty.value || "0", 10);
        const defect = parseInt(defectQty.value || "0", 10);

        if (good + defect > input) {
            alert("양품수량과 불량수량의 합은 투입수량보다 클 수 없습니다.");
            defectQty.focus();
            return false;
        }

        return true;
    }

    function bindNumericInput(el) {
        if (!el) return;

        el.addEventListener("input", function () {
            this.value = onlyNumber(this.value);
        });
    }

    bindNumericInput(inputQty);
    bindNumericInput(goodQty);
    bindNumericInput(defectQty);

    if (inputQty) inputQty.addEventListener("input", validateProductionQty);
    if (goodQty) goodQty.addEventListener("input", validateProductionQty);
    if (defectQty) defectQty.addEventListener("input", validateProductionQty);

    if (addBtn) {
        addBtn.addEventListener("click", function () {
            resetProductionModal();
            openModal("생산실적 신규 등록");
        });
    }

    const editLinks = document.querySelectorAll(".prod-edit-link");

    editLinks.forEach(function (link) {
        link.addEventListener("click", function () {
            document.getElementById("modalTitle").innerText = "생산실적 수정";
            document.getElementById("cmd").value = "update";

            document.getElementById("prod_key").value = this.dataset.prodKey || "";
            document.getElementById("prod_code").value = this.dataset.prodCode || "";
            document.getElementById("prod_date").value = this.dataset.prodDate || "";
            document.getElementById("item_name").value = this.dataset.itemName || "";
            document.getElementById("plan_qty").value = this.dataset.planQty || "";
            document.getElementById("order_qty").value = this.dataset.orderQty || "";
            document.getElementById("input_qty").value = this.dataset.inputQty || "";
            document.getElementById("good_qty").value = this.dataset.goodQty || "";
            document.getElementById("defect_qty").value = this.dataset.defectQty || "";
            document.getElementById("work_user_key").value = this.dataset.workUserKey || "";
            document.getElementById("work_user_name").value = this.dataset.workUserName || "";
            document.getElementById("work_order_key").value = this.dataset.workOrderKey || "";
            document.getElementById("work_order_code").value = this.dataset.workOrderCode || "";
            document.getElementById("plan_key").value = this.dataset.planKey || "";
            document.getElementById("remark").value = this.dataset.remark || "";

			setUpdateModeReadonly();
            openModal("생산실적 수정");
        });
    });

    if (form) {
        form.addEventListener("submit", function (e) {
            const cmd = document.getElementById("cmd").value;

            if (!prodDate || !prodDate.value) {
                alert("생산일을 입력해주세요.");
                if (prodDate) prodDate.focus();
                e.preventDefault();
                return;
            }

            if (!inputQty.value) {
                alert("투입수량을 입력해주세요.");
                inputQty.focus();
                e.preventDefault();
                return;
            }

            if (!goodQty.value) {
                alert("양품수량을 입력해주세요.");
                goodQty.focus();
                e.preventDefault();
                return;
            }

            if (!defectQty.value) {
                alert("불량수량을 입력해주세요.");
                defectQty.focus();
                e.preventDefault();
                return;
            }

            if (!validateProductionQty()) {
                e.preventDefault();
                return;
            }

            if (cmd === "update") {
                if (!confirm("생산실적 정보를 수정하시겠습니까?")) {
                    e.preventDefault();
                    return;
                }
            } else {
                if (!confirm("생산실적을 등록하시겠습니까?")) {
                    e.preventDefault();
                    return;
                }
            }
        });
    }

    if (searchKeyword) {
        searchKeyword.addEventListener("keydown", function (e) {
            if (e.key === "Enter") {
                e.preventDefault();
                this.form.submit();
            }
        });
    }
});

function setInsertModeReadonly() {
    document.getElementById("prod_code").removeAttribute("readonly");
    document.getElementById("prod_date").removeAttribute("readonly");
    document.getElementById("item_name").removeAttribute("readonly");
    document.getElementById("plan_qty").removeAttribute("readonly");
    document.getElementById("order_qty").removeAttribute("readonly");
    document.getElementById("input_qty").removeAttribute("readonly");
    document.getElementById("good_qty").removeAttribute("readonly");
    document.getElementById("defect_qty").removeAttribute("readonly");
    document.getElementById("work_user_name").removeAttribute("readonly");
    document.getElementById("work_order_code").removeAttribute("readonly");
}

function setUpdateModeReadonly() {
    document.getElementById("prod_code").setAttribute("readonly", true);
    document.getElementById("prod_date").removeAttribute("readonly");   // 수정 가능
    document.getElementById("item_name").setAttribute("readonly", true);
    document.getElementById("plan_qty").setAttribute("readonly", true);
    document.getElementById("order_qty").setAttribute("readonly", true);
    document.getElementById("input_qty").removeAttribute("readonly");   // 수정 가능
    document.getElementById("good_qty").removeAttribute("readonly");    // 수정 가능
    document.getElementById("defect_qty").removeAttribute("readonly");  // 수정 가능
    document.getElementById("work_user_name").setAttribute("readonly", true);
    document.getElementById("work_order_code").setAttribute("readonly", true);
}