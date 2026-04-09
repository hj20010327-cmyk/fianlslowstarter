
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


function openModal(title = "신규 등록") {
  document.getElementById("modalTitle").innerText = title;
  document.getElementById("commonModal").classList.add("show");
}

function closeModal() {
  document.getElementById("commonModal").classList.remove("show");
}

