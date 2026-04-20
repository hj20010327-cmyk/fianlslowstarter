  // 👉 수정 모드일 때만 값 세팅
  
  function openInsertModal(){
  	
  	document.getElementById("modalTitle").innerText = "BOM 신규 등록"; 
  	document.getElementById("bomForm").reset(); 
  	
  	// 반드시 id.로 직접 주기 
  	document.getElementById("cmd").value = "insert";
  	
  	
  	document.getElementById("bom_code").value= ""; 
  	document.getElementById("qty").value= ""; 
  	document.getElementById("remark").value= ""; 
  	document.getElementById("item_key").value= ""; 
  	document.getElementById("parent_item_key").value= ""; 
  	
  	console.log(document.getElementById("bomForm"));
console.log(document.getElementById("bom_code"));
console.log(document.getElementById("qty"));
  	
  	document.getElementById("commonModal").classList.add("show");
  }
  
 
	function openEditModal(bom_key, bom_code, qty, remark, item_key, parent_item_key){
		document.getElementById("modalTitle").innerText = "BOM 수정";
		document.getElementById("bomForm").action = "bom";
		
		document.getElementById("cmd").value = "update";
		
		
		
		document.getElementById("bom_key").value = bom_key;
		document.getElementById("bom_code").value = bom_code;
		document.getElementById("qty").value = qty; 
		document.getElementById("remark").value = remark; 
		document.getElementById("item_key").value = item_key; 
		document.getElementById("parent_item_key").value = parent_item_key; 
		
		document.getElementById("commonModal").classList.add("show");
		
		console.log("bom_key:", bom_key);
		console.log(document.getElementById("bom_key"));
		
		console.log("item_key:", item_key);
console.log("select value:", document.getElementById("item_key").value);

		
	}
	
	function closeModal(){
		document.getElementById("commonModal").classList.remove("show");
	}
	
	
	document.getElementById("bomForm").addEventListener("submit", function(){
		console.log("cmd=", document.getElementById("cmd").value);
		console.log("bom_key = ", document.getElementById("bom_key").value);
	});


