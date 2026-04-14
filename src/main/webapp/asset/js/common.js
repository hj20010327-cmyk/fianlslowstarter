
const menuToggle = document.getElementById('menuToggle');
const snb = document.querySelector('.snb');
const snbOverlay = document.getElementById('snbOverlay');

if (menuToggle && snb && snbOverlay) {
    menuToggle.addEventListener('click', function () {
        snb.classList.toggle('open');
        snbOverlay.classList.toggle('show');
    });

    snbOverlay.addEventListener('click', function () {
        snb.classList.remove('open');
        snbOverlay.classList.remove('show');
    });

    window.addEventListener('resize', function () {
        if (window.innerWidth > 1024) {
            snb.classList.remove('open');
            snbOverlay.classList.remove('show');
        }
    });
}

document.addEventListener("DOMContentLoaded", function () {
    const sections = document.querySelectorAll(".snb-section");

    sections.forEach((section) => {
        if (section.querySelector("li.active")) {
            section.classList.add("open");
        }
    });

    const titles = document.querySelectorAll(".snb-title");

    titles.forEach((title) => {
        title.addEventListener("click", () => {
            const section = title.closest(".snb-section");
            section.classList.toggle("open");
        });
    });
    
  });
  window.addEventListener('load', ()=>{
    const date = document.querySelector('.date');
    if (!date) return;

    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');

    date.textContent = `${year}-${month}-${day}`;
  })





function openModal(title = "신규 등록") {
  document.getElementById("modalTitle").innerText = title;
  document.getElementById("commonModal").classList.add("show");
}

function closeModal() {
  document.getElementById("commonModal").classList.remove("show");
}

document.addEventListener("click", function (e) {
    const modal = document.getElementById("commonModal");
    const modalBox = document.querySelector(".modal-box");

    if (!modal || !modalBox) return;

    if (modal.classList.contains("show") && e.target === modal) {
        closeModal();
    }
});

// ESC 키로 모달 닫기
document.addEventListener("keydown", function (e) {
    const modal = document.getElementById("commonModal");

    if (!modal) return;

    if (e.key === "Escape" && modal.classList.contains("show")) {
        closeModal();
    }
});

function logout() {
    if (!confirm("로그아웃 하시겠습니까?")) return;

    window.location.href = contextPath + "/logout";
}

setTimeout(() => {
    alert("세션이 만료되었습니다. 다시 로그인해주세요.");
    logout();
}, 1000 * 60 * 30);