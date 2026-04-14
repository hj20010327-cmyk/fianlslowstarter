document.addEventListener("DOMContentLoaded", function () {
    AuthPage.init();
});

const AuthPage = {
    init() {
        this.cacheDom();
        this.bindPhoneEvents();
        this.bindLoginValidation();
        this.bindPasswordMatchCheck();
        this.bindRequiredFieldHighlight();
    },

    cacheDom() {
        this.phone2 = document.getElementById("phone2");
        this.phone3 = document.getElementById("phone3");

        this.loginForm = document.getElementById("loginForm");
        this.userId = document.getElementById("user_id");
        this.userPw = document.getElementById("user_pw");
        this.userPwCheck = document.getElementById("user_pw_check");
        this.pwCheckMsg = document.getElementById("pwCheckMsg");

        this.requiredInputs = document.querySelectorAll("[data-required]");
    },

    onlyNumber(value) {
        return value.replace(/[^0-9]/g, "");
    },

    bindPhoneEvents() {
        if (!this.phone2 || !this.phone3) return;

        this.phone2.addEventListener("input", () => {
            this.phone2.value = this.onlyNumber(this.phone2.value);

            if (this.phone2.value.length === 4) {
                this.phone3.focus();
            }
        });

        this.phone3.addEventListener("input", () => {
            this.phone3.value = this.onlyNumber(this.phone3.value);
        });

        this.phone3.addEventListener("keydown", (e) => {
            if (e.key === "Backspace" && this.phone3.value.length === 0) {
                this.phone2.focus();
            }
        });
    },

    bindLoginValidation() {
        if (!this.loginForm || !this.userId || !this.userPw) return;

        this.loginForm.addEventListener("submit", (e) => {
            if (!this.userId.value.trim()) {
                alert("아이디를 입력해주세요.");
                this.userId.focus();
                e.preventDefault();
                return;
            }

            if (!this.userPw.value.trim()) {
                alert("비밀번호를 입력해주세요.");
                this.userPw.focus();
                e.preventDefault();
            }
        });
    },

    bindPasswordMatchCheck() {
        if (!this.userPw || !this.userPwCheck || !this.pwCheckMsg) return;

        const checkPw = () => {
            this.pwCheckMsg.classList.remove("pw-msg-success", "pw-msg-error");

            if (!this.userPwCheck.value) {
                this.pwCheckMsg.textContent = "";
                return;
            }

            if (this.userPw.value === this.userPwCheck.value) {
                this.pwCheckMsg.textContent = "비밀번호가 일치합니다.";
                this.pwCheckMsg.classList.add("pw-msg-success");
            } else {
                this.pwCheckMsg.textContent = "비밀번호가 일치하지 않습니다.";
                this.pwCheckMsg.classList.add("pw-msg-error");
            }
        };

        this.userPw.addEventListener("input", checkPw);
        this.userPwCheck.addEventListener("input", checkPw);
    },

    bindRequiredFieldHighlight() {
        if (!this.requiredInputs.length) return;

        this.requiredInputs.forEach((input) => {
            const wrapper = input.closest(".auth-row, .form-group");
            const label = wrapper ? wrapper.querySelector("label") : null;

            if (label && !label.querySelector(".required-star")) {
                const star = document.createElement("span");
                star.classList.add("required-star");
                star.textContent = " *";
                label.appendChild(star);
            }

            const toggleError = () => {
                if (!input.value.trim()) {
                    input.classList.add("input-error");
                } else {
                    input.classList.remove("input-error");
                }
            };

            input.addEventListener("blur", toggleError);
            input.addEventListener("input", () => {
                if (input.value.trim()) {
                    input.classList.remove("input-error");
                }
            });
        });
    }
};