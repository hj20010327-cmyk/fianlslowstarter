
 function openInsertModal(){

    document.getElementById("modalTitle").innerText = "Process 신규 등록";

    document.getElementById("processForm").reset();

    // 🔥 반드시 id로 직접
    document.getElementById("cmd").value = "insert";

    document.getElementById("process_key").value = "";
    document.getElementById("process_name").value = "";
    document.getElementById("process_code").value = "";
    document.getElementById("sequence_no").value = "";
    document.getElementById("process_desc").value = "";
    document.getElementById("status").value = "Y";
    document.getElementById("item_key").value = "";

    document.getElementById("commonModal").classList.add("show");
}



function openEditModal(process_key, process_code, process_name, sequence_no, process_desc, status, item_key){

	document.getElementById("modalTitle").innerText = "Process 수정"; 
	
	document.getElementById("processForm").action = "process";

	// 💥 reset 이후에 반드시 다시 세팅
	document.getElementById("cmd").value = "update";
	document.getElementById("process_key").value = process_key;
	document.getElementById("process_name").value = process_name;
	document.getElementById("process_code").value = process_code;
	document.getElementById("sequence_no").value = sequence_no;
	document.getElementById("process_desc").value = process_desc;
	document.getElementById("status").value = status;
	document.getElementById("item_key").value = item_key;
	
	

	document.getElementById("commonModal").classList.add("show");
}

function closeModal(){
	document.getElementById("commonModal").classList.remove("show");
}

document.getElementById("processForm").addEventListener("submit", function () {
    console.log("cmd =", document.getElementById("cmd").value);
    console.log("process_key =", document.getElementById("process_key").value);
});



document.addEventListener("change", function (e) {
    if (e.target && e.target.id === "process_name") {
        const selected = e.target.options[e.target.selectedIndex];
        document.getElementById("process_desc").value =
            selected.dataset.desc || "";
    }
});
console.log("js 실행행");

document.getElementById("process_name").addEventListener("change", function () {
    console.log("change 됨?");
});

document.addEventListener("change", function (e) {
    console.log("change 발생:", e.target);
});

window.addEventListener("load", function () {
    console.log("JS 실행됨 (load)");
});

