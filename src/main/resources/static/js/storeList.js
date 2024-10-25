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
	
	// 초기값 설정  -->  메인페이지에서 검색창에서 즐겨찾기 눌렀을때 성동구빵집리스트에서 가게검색 필터링 함수 기능 정상적으로 수행되도록 설정
	const initialQuery = new URLSearchParams(window.location.search).get('shopText') || '';
	document.getElementById("storeQuery").value = initialQuery;
	filterStores(); // 페이지 로드 시 필터링 함수 호출
	
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





// 페이지 로드 시 초기화
window.onload = function() {
    // 추가 함수 (필요시 여기 추가)
};




