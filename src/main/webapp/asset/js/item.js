function openItemModal(mode, ...data) {
    document.getElementById("cmd").value = mode;

    if (mode === "insert") {
        document.getElementById("modalTitle").innerText = "품목 등록";

        document.getElementById("item_key").value = "";
        document.getElementById("item_code").value = "";
        document.getElementById("item_code_view").value = "자동 생성";

        document.getElementById("item_name").value = "";
        document.getElementById("item_type").value = "완제품";
        document.getElementById("spec").value = "";
        document.getElementById("unit").value = "";
        document.getElementById("price").value = "";
        document.getElementById("safe_qty").value = "";
        document.getElementById("status").value = "Y";

    } else {
        document.getElementById("modalTitle").innerText = "품목 수정";

        document.getElementById("item_key").value = data[0];
        document.getElementById("item_code").value = data[1];
        document.getElementById("item_code_view").value = data[1];

        document.getElementById("item_name").value = data[2];
        document.getElementById("item_type").value = data[3];
        document.getElementById("spec").value = data[4];
        document.getElementById("unit").value = data[5];
        document.getElementById("price").value = data[6];
        document.getElementById("safe_qty").value = data[7];
        document.getElementById("status").value = data[8];
    }

    openModal("품목");
}