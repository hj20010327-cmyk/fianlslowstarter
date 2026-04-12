/* window.onload 는 페이지가 로딩된 후 실행하고 싶을 때 
버튼 클릭용만은 필요 없음 */

function openEditModalFromElement(el) {
  const code = el.dataset.code;
  const name = el.dataset.name;
  const qty = el.dataset.qty;
  const remark = el.dataset.remark;

  openEditModal(code, name, qty, remark);
}

function openEditModal(code,name,qty,remark){
	
	document.getElementById('modalTitle').innerText = '수정';
	
	document.querySelector('input[name="bom_code"]').value = code; 
	document.querySelector('input[name="bom_item_key"]').value = name; 
	document.querySelector('input[name="QTY"]').value = qty; 
	document.querySelector('textarea[name="remark"]').value = remark;
	
	document.querySelector('#commonModal input[name="cmd"]').value = 'update';
	document.getElementById('commonModal').style.display='block';	
	

}

console.log("bom.js loaded");

