/* window.onload 는 페이지가 로딩된 후 실행하고 싶을 때 
버튼 클릭용만은 필요 없음 */

window.onload = function(){
 	console.log(document.getElementById('commonModal'));
}

function openModal(title, el = null) {
  const modal = document.getElementById('commonModal');

  // 제목 변경
  document.getElementById('modalTitle').innerText = title;

  // 👉 수정 모드일 때만 값 세팅
  if (el) {
    const code = el.dataset.code;
    const name = el.dataset.name;
    const qty = el.dataset.qty;
    const remark = el.dataset.remark;
    
    console.log(code, name, qty, remark);
    
	document.querySelector('input[name="bom_key"]').value = key;
    document.querySelector('input[name="bom_code"]').value = code;
    document.querySelector('input[name="bom_item_key"]').value = name;
    document.querySelector('input[name="QTY"]').value = qty;
    document.querySelector('textarea[name="remark"]').value = remark;
    
    

    document.querySelector('input[name="cmd"]').value = 'update';
  } else {
    // 👉 신규 등록일 때 초기화
    document.querySelector('input[name="bom_code"]').value = '';
    document.querySelector('input[name="bom_item_key"]').value = '';
    document.querySelector('input[name="QTY"]').value = '';
    document.querySelector('textarea[name="remark"]').value = '';

    document.querySelector('input[name="cmd"]').value = 'insert';
  }

  // 모달 열기
  modal.classList.add('show');
}

console.log("bom.js loaded");
console.log("1");

console.log(document.getElementById('commonModal'));

console.log("2");


