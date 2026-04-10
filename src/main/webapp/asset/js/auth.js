document.addEventListener("DOMContentLoaded", function () {
    const phone2 = document.getElementById("phone2");
    const phone3 = document.getElementById("phone3");

    function onlyNumber(value) {
        return value.replace(/[^0-9]/g, "");
    }

    phone2.addEventListener("input", function () {
        this.value = onlyNumber(this.value);

        if (this.value.length === 4) {
            phone3.focus();
        }
    });

    phone3.addEventListener("input", function () {
        this.value = onlyNumber(this.value);
    });

    phone3.addEventListener("keydown", function (e) {
        if (e.key === "Backspace" && this.value.length === 0) {
            phone2.focus();
        }
    });
});