
function openInsertModal(){

	document.getElementById("modalTitle").innerText = "Process 신규 등록";
	
	
	document.getElementById("processForm").reset(); 
	document.querySelector("input[name='cmd']").value = "insert";

	document.getElementById("process_code").value = "";
	document.getElementById("sequence_no").value = "";
	document.getElementById("process_desc").value = "";
	document.getElementById("status").value = "Y";
	document.getElementById("item-key").value = "";

	document.getElementById("commonModal").classList.add("show");
}

function openEditModal(process_key, process_code, process_name, sequence_no, process_desc, status, item_key){

	document.getElementById("modalTitle").innerText = "Process 수정"; 
	
	document.getElementById("processForm").reset(); 

	// 💥 reset 이후에 반드시 다시 세팅
	document.getElementById("cmd").value = "update";
	document.getElementById("process_key").value = process_key;

	document.getElementById("process_code").value = process_code;
	document.getElementById("sequence_no").value = sequence_no;
	document.getElementById("process_desc").value = process_desc;
	document.getElementById("status").value = status;
	document.getElementById("item-key").value = item_key;

	document.getElementById("commonModal").classList.add("show");
}

function closeModal(){
	document.getElementById("commonModal").classList.remove("show");
}
