document.addEventListener("DOMContentLoaded", function () {

    const editBtn = document.getElementById("btnEdit");
    const cancelBtn = document.getElementById("btnCancel");
    const inputs = document.querySelectorAll(".mypage-input");

    if (editBtn) {
        editBtn.addEventListener("click", function () {
            inputs.forEach(input => input.removeAttribute("readonly"));
        });
    }

    if (cancelBtn) {
        cancelBtn.addEventListener("click", function () {
            location.reload();
        });
    }

});