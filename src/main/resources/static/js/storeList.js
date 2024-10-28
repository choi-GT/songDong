// 가게 검색 필터링 함수
function filterStores() {
    const query = document.getElementById('storeQuery').value.toLowerCase();
    const rows = document.querySelectorAll('#storeTableBody tr');

    rows.forEach(row => {
        const storeName = row.cells[0].textContent.toLowerCase(); // 사업장명
        const address = row.cells[2].textContent.toLowerCase(); // 주소

        // 가게 이름 또는 주소에 쿼리가 포함된 경우에만 보여주기
        if (storeName.includes(query) || address.includes(query)) {
            row.style.display = ''; // 보여주기
        } else {
            row.style.display = 'none'; // 숨기기
        }
    });
}



// 주소 타입 변경 버튼 클릭 시 동작
document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('#changeAddressTypeBtn').addEventListener('click', () => {
        const rdnwhladdrList = document.querySelectorAll('.rdnwhladdr');
        const sitewhladdrList = document.querySelectorAll('.sitewhladdr');
        rdnwhladdrList.forEach(item => {
            item.classList.toggle('none');
        });
        sitewhladdrList.forEach(item => {
            item.classList.toggle('none');
        });
    });
});


// 상태 버튼 클릭 시 폐업 가게 숨기기/보이기
/*let showClosedStores = true;

document.getElementById('toggleClosedStoresBtn').addEventListener('click', () => {
    const rows = document.querySelectorAll('#storeTableBody tr');
    showClosedStores = !showClosedStores; // 상태 토글

    rows.forEach(row => {
        const status = row.cells[3].textContent; // 상태 열의 텍스트 가져오기
        if (status.includes('폐업')) {
            row.style.display = showClosedStores ? '' : 'none'; // 버튼 상태에 따라 보이기/숨기기
        }
    });
});
*/


// 페이지 로드 시 초기화
window.onload = function() {
	    // 인풋에 URL에서 받은 값 설정
	    const initialQuery = document.getElementById("storeQuery").value;
	    if (initialQuery) {
	        filterStores(); // 초기 값으로 필터링 수행
	    }
	};

