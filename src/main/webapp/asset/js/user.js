document.addEventListener("DOMContentLoaded", function () {

    // 전체 선택
    const checkAll = document.getElementById("checkAll");
    const checks = document.querySelectorAll(".row-check");

    if (checkAll) {
        checkAll.addEventListener("change", function () {
            checks.forEach(ch => ch.checked = this.checked);
        });
    }

    // 선택 삭제
    const deleteBtn = document.getElementById("btnDelete");

    if (deleteBtn) {
        deleteBtn.addEventListener("click", function () {
            const selected = document.querySelectorAll(".row-check:checked");

            if (selected.length === 0) {
                alert("삭제할 항목을 선택하세요.");
                return;
            }

            if (!confirm("선택한 사용자를 삭제하시겠습니까?")) {
                return;
            }

            document.getElementById("userForm").submit();
        });
    }

});