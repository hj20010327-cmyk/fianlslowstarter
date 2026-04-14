/* window.onload 는 페이지가 로딩된 후 실행하고 싶을 때 
버튼 클릭용만은 필요 없음 */

function openModal(title, el = null) {
  const modal = document.getElementById('commonModal');

  document.getElementById('modalTitle').innerText = title;

  if (el) {
    const key = el.dataset.key;
    const code = el.dataset.code;
    const name = el.dataset.name;
    const desc = el.dataset.desc;
    const status = el.dataset.status;
    const itemkey = el.dataset.itemkey;
    const itemName = el.dataset.itemname;
   

    document.querySelector('input[name="process_key"]').value = key;
    document.querySelector('input[name="process_code"]').value = code;
    document.querySelector('input[name="process_name"]').value = name;
    document.querySelector('input[name="process_desc"]').value = desc;
    document.querySelector('select[name="status"]').value = status;
    document.querySelector('select[name="item_name"]').value = itemName;

    document.querySelector('input[name="cmd"]').value = 'update';
  } else {
    document.querySelector('input[name="process_code"]').value = '';
    document.querySelector('input[name="item_key"]').value = '';
    document.querySelector('input[name="process_desc"]').value = '';
    document.querySelector('input[name="sequence_no"]').value = '';

    document.querySelector('input[name="cmd"]').value = 'insert';
  }

  modal.classList.add('show');
}
