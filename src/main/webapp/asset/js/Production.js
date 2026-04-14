document.addEventListener("DOMContentLoaded", function () {
    const addBtn = document.getElementById("btnAddProduction");

    function resetProductionModal() {
        document.getElementById("modalTitle").innerText = "생산실적 신규 등록";
        document.getElementById("cmd").value = "insert";

        document.getElementById("prod_key").value = "";
        document.getElementById("prod_code").value = "";
        document.getElementById("prod_date").value = "";
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
    }

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

            openModal("생산실적 수정");
        });
    });
});