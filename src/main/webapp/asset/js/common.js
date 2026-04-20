document.addEventListener("DOMContentLoaded", function () {
    CommonUI.init();
});

const CommonUI = {
    warningTimer: null,
    logoutTimer: null,

    init() {
        this.cacheDom();
        this.bindMenuToggle();
        this.bindSnbAccordion();
        this.renderTodayDate();
        this.bindModalOutsideClick();
        this.bindModalEscapeKey();
        this.initSessionAutoLogout();
    },

    cacheDom() {
        this.menuToggle = document.getElementById("menuToggle");
        this.snb = document.querySelector(".snb");
        this.snbOverlay = document.getElementById("snbOverlay");
        this.sections = document.querySelectorAll(".snb-section");
        this.titles = document.querySelectorAll(".snb-title");
        this.date = document.querySelector(".date");
        this.modal = document.getElementById("commonModal");
        this.modalTitle = document.getElementById("modalTitle");
        this.modalBox = document.querySelector(".modal-box");
    },

    bindMenuToggle() {
        if (!this.menuToggle || !this.snb || !this.snbOverlay) return;

        this.menuToggle.addEventListener("click", () => {
            this.snb.classList.toggle("open");
            this.snbOverlay.classList.toggle("show");
        });

        this.snbOverlay.addEventListener("click", () => {
            this.closeSnb();
        });

        window.addEventListener("resize", () => {
            if (window.innerWidth > 1024) {
                this.closeSnb();
            }
        });
    },

    closeSnb() {
        if (this.snb) this.snb.classList.remove("open");
        if (this.snbOverlay) this.snbOverlay.classList.remove("show");
    },

    bindSnbAccordion() {
        if (!this.sections.length || !this.titles.length) return;

        this.sections.forEach((section) => {
            if (section.querySelector("li.active")) {
                section.classList.add("open");
            }
        });

        this.titles.forEach((title) => {
            title.addEventListener("click", () => {
                const section = title.closest(".snb-section");
                if (section) {
                    section.classList.toggle("open");
                }
            });
        });
    },

    renderTodayDate() {
        if (!this.date) return;

        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, "0");
        const day = String(now.getDate()).padStart(2, "0");

        this.date.textContent = `${year}-${month}-${day}`;
    },

    openModal(title = "신규 등록") {
        if (!this.modal || !this.modalTitle) return;

        this.modalTitle.innerText = title;
        this.modal.classList.add("show");
    },

    closeModal() {
        if (!this.modal) return;
        this.modal.classList.remove("show");
    },

    bindModalOutsideClick() {
    document.addEventListener("mousedown", (e) => {
        if (!this.modal || !this.modalBox) return;

        if (!this.modal.classList.contains("show")) return;

        // 모달 박스 안 클릭이면 무시
        if (this.modalBox.contains(e.target)) return;

        // 바깥 클릭이면 닫기
        this.closeModal();
    });
},

    bindModalEscapeKey() {
        document.addEventListener("keydown", (e) => {
            if (!this.modal) return;

            if (e.key === "Escape" && this.modal.classList.contains("show")) {
                this.closeModal();
            }
        });
    },

    logout() {
        if (!confirm("로그아웃 하시겠습니까?")) return;

        if (typeof contextPath === "undefined") {
            location.href = "/logout";
            return;
        }

        location.href = contextPath + "/logout";
    },

    forceLogoutBySessionExpired() {
        alert("세션이 만료되었습니다. 다시 로그인해주세요.");

        if (typeof contextPath === "undefined") {
            location.href = "/logout";
            return;
        }

        location.href = contextPath + "/logout";
    },

    initSessionAutoLogout() {
        const enableAutoLogout = document.body?.dataset?.autoLogout === "true";
        if (!enableAutoLogout) return;

        if (typeof sessionTimeoutSeconds === "undefined" || !sessionTimeoutSeconds) return;

        this.bindUserActivityReset();
        this.resetSessionTimers();
    },

    bindUserActivityReset() {
        const events = ["click", "keydown", "mousemove", "scroll", "touchstart"];

        let throttle = false;

        const resetHandler = () => {
            if (throttle) return;

            throttle = true;
            this.resetSessionTimers();

            setTimeout(() => {
                throttle = false;
            }, 1000);
        };

        events.forEach((eventName) => {
            window.addEventListener(eventName, resetHandler, { passive: true });
        });
    },

    resetSessionTimers() {
        this.clearSessionTimers();

        const totalMs = sessionTimeoutSeconds * 1000;
        const warningMs = Math.max(totalMs - 60000, 0);

        this.warningTimer = setTimeout(() => {
            alert("1분 후 세션이 만료됩니다.");
        }, warningMs);

        this.logoutTimer = setTimeout(() => {
            this.forceLogoutBySessionExpired();
        }, totalMs);
    },

    clearSessionTimers() {
        if (this.warningTimer) {
            clearTimeout(this.warningTimer);
            this.warningTimer = null;
        }

        if (this.logoutTimer) {
            clearTimeout(this.logoutTimer);
            this.logoutTimer = null;
        }
    }
};

// 전역 함수 유지
function openModal(title = "신규 등록") {
    CommonUI.openModal(title);
}

function closeModal() {
    CommonUI.closeModal();
}

function logout() {
    CommonUI.logout();
}