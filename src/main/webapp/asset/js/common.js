
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
