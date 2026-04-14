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
    
    const loginForm = document.getElementById("loginForm");

    if (loginForm) {
        loginForm.addEventListener("submit", function (e) {
            const id = document.getElementById("user_id");
            const pw = document.getElementById("user_pw");

            if (!id.value.trim()) {
                alert("아이디를 입력해주세요.");
                id.focus();
                e.preventDefault();
                return;
            }

            if (!pw.value.trim()) {
                alert("비밀번호를 입력해주세요.");
                pw.focus();
                e.preventDefault();
            }
        });
    }

    // 비밀번호 일치 체크
    const pw = document.getElementById("user_pw");
    const pw2 = document.getElementById("user_pw_check");
    const msg = document.getElementById("pwCheckMsg");

    function checkPw() {
        if (!pw || !pw2 || !msg) return;

        if (!pw2.value) {
            msg.textContent = "";
            return;
        }

        if (pw.value === pw2.value) {
            msg.textContent = "비밀번호가 일치합니다.";
            msg.style.color = "green";
        } else {
            msg.textContent = "비밀번호가 일치하지 않습니다.";
            msg.style.color = "red";
        }
    }

    if (pw) pw.addEventListener("input", checkPw);
    if (pw2) pw2.addEventListener("input", checkPw);
});