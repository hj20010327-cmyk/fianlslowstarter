const productItems = document.querySelectorAll(".product-list li");
const bomBlocks = document.querySelectorAll(".bom-block");

window.onload = function () {

  const urlParams = new URLSearchParams(window.location.search);
  const productId = urlParams.get("item_key");

  if (!productId) return;

  productItems.forEach(li => {
    const a = li.querySelector("a");

    if (!a) return;

    //  href 파싱해서 비교
    const params = new URL(a.href).searchParams;
    const id = params.get("item_key");

    if (id === productId) {
      loadBom(productId, li);
    }
  });

};


function loadBom(productId, el) {





	  	
	 // active 처리
	  document.querySelectorAll(".product-list li")
	    .forEach(li => li.classList.remove("active"));

	  el.classList.add("active");

	  // 제목 변경
	  const title = document.getElementById("selectedName");
	  if (title) {
	    title.innerText = el.innerText;
	  }
	  
	  
	 
	  // 모든 BOM 숨기기
	  document.querySelectorAll(".bom-block")
	  .forEach(div => div.style.display = "none");
	  
	  // 선택한 BOM만 보여주기
	  const target = document.getElementById("bom-" + productId);
	  if(target){
		  target.style.display = "block";
	  }
	  
	  console.log("productId:", productId);
console.log("target:", document.getElementById("bom-" + productId));


	
	
  

	}
	
	
	// 모달용 
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

	
	

