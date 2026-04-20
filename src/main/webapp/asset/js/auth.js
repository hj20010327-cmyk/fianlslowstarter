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
        this.bindPasswordToggle();
        this.bindSignupValidation();
        
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
        this.passwordToggles = document.querySelectorAll(".eye-toggle");

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

        const pw = this.userPw.value;
        const pwCheck = this.userPwCheck.value;

        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/;

        if (!pw) {
            this.pwCheckMsg.textContent = "";
            return;
        }

        // 규칙 실패
        if (!regex.test(pw)) {
            this.pwCheckMsg.textContent = "영문 대/소문자, 숫자, 특수문자 포함 8자 이상";
            this.pwCheckMsg.classList.add("pw-msg-error");
            return;
        }

        // 규칙 성공
        if (!pwCheck) {
            this.pwCheckMsg.textContent = "사용 가능한 비밀번호입니다.";
            this.pwCheckMsg.classList.add("pw-msg-success");
            return;
        }

        // 일치 검사
        if (pw === pwCheck) {
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
    },
    bindPasswordToggle() {
        if (!this.passwordToggles.length) return;

        this.passwordToggles.forEach((btn) => {
            btn.addEventListener("click", () => {
                const targetId = btn.dataset.target;
                const input = document.getElementById(targetId);

                if (!input) return;

                if (input.type === "password") {
                    input.type = "text";
                    btn.innerHTML = '<i class="fa-solid fa-eye-slash"></i>';
                } else {
                    input.type = "password";
                    btn.innerHTML = '<i class="fa-solid fa-eye"></i>';
                }
            });
        });
    },
    
bindSignupValidation() {
    const form = document.getElementById("signupForm");
    if (!form) return;

    const requiredInputs = form.querySelectorAll("[data-required]");

    form.addEventListener("submit", (e) => {

        // 1. 필수값 체크 + 포커스 이동
        for (let input of requiredInputs) {
            if (!input.value.trim()) {
                e.preventDefault();

                input.classList.add("input-error");
                input.focus();
                input.scrollIntoView({ behavior: "smooth", block: "center" });

                return;
            }
        }

        // 2. 비밀번호 규칙 검사
        const pw = document.getElementById("user_pw");
const pwCheck = document.getElementById("user_pw_check");
const pwRuleMsg = document.getElementById("pwRuleMsg");

const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/;

// 메시지 초기화
pwRuleMsg.textContent = "";

// ❌ 비밀번호 규칙 실패
if (!regex.test(pw.value)) {
    e.preventDefault();

    pwRuleMsg.textContent = "영문 대/소문자, 숫자, 특수문자 포함 8자 이상 입력해주세요.";
    pwRuleMsg.classList.add("pw-msg-error");

    pw.classList.add("input-error");
    pw.focus();
    return;
}

// ❌ 비밀번호 불일치
if (pw.value !== pwCheck.value) {
    e.preventDefault();

    pwRuleMsg.textContent = "비밀번호가 일치하지 않습니다.";
    pwRuleMsg.classList.add("pw-msg-error");

    pwCheck.classList.add("input-error");
    pwCheck.focus();
    return;
}

// ✅ 통과
pwRuleMsg.textContent = "";
pwRuleMsg.classList.remove("pw-msg-error");
    });

    // ================================
    // 실시간 에러 제거 + 엔터 이동
    // ================================
    requiredInputs.forEach((input, idx) => {

        input.addEventListener("input", () => {
            if (input.value.trim()) {
                input.classList.remove("input-error");
            }
        });

        input.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                if (requiredInputs[idx + 1]) {
                    requiredInputs[idx + 1].focus();
                }
            }
        });
    });
}
};