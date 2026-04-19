window.onload = function () {

  const urlParams = new URLSearchParams(window.location.search);
  const productId = urlParams.get("item_key");

  if (!productId) return;

  const items = document.querySelectorAll(".product-list li");

  items.forEach(li => {
    const a = li.querySelector("a");

    if (a && a.href.includes("item_key=" + productId)) {
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
	

