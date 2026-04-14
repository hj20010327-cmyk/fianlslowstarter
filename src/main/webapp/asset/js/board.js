document.addEventListener("DOMContentLoaded", function () {

    // 글자 수 카운트
    const content = document.getElementById("content");
    const counter = document.getElementById("contentCount");

    if (content && counter) {
        content.addEventListener("input", function () {
            counter.textContent = this.value.length + "자";
        });
    }

    // 삭제 확인
    const deleteBtns = document.querySelectorAll(".btn-delete");

    deleteBtns.forEach(btn => {
        btn.addEventListener("click", function () {
            if (!confirm("삭제하시겠습니까?")) {
                return false;
            }
        });
    });

    // 검색 엔터
    const keyword = document.querySelector('input[name="keyword"]');

    if (keyword) {
        keyword.addEventListener("keydown", function (e) {
            if (e.key === "Enter") {
                e.preventDefault();
                this.form.submit();
            }
        });
    }

});