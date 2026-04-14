document.addEventListener("DOMContentLoaded", function () {

    const start = document.querySelector('input[name="startDate"]');
    const end = document.querySelector('input[name="endDate"]');

    function format(d) {
        return `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,"0")}-${String(d.getDate()).padStart(2,"0")}`;
    }

    // 오늘
    const btnToday = document.getElementById("btnToday");
    if (btnToday) {
        btnToday.addEventListener("click", function () {
            const now = new Date();
            start.value = format(now);
            end.value = format(now);
        });
    }

    // 이번 주
    const btnWeek = document.getElementById("btnThisWeek");
    if (btnWeek) {
        btnWeek.addEventListener("click", function () {
            const now = new Date();
            const day = now.getDay() || 7;

            const monday = new Date(now);
            monday.setDate(now.getDate() - day + 1);

            const sunday = new Date(monday);
            sunday.setDate(monday.getDate() + 6);

            start.value = format(monday);
            end.value = format(sunday);
        });
    }

});