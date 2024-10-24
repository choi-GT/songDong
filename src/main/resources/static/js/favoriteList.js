document.addEventListener('DOMContentLoaded', () => {
    updateGlobalFavoriteList();
});

// 전체 즐겨찾기 목록 업데이트
async function updateGlobalFavoriteList() {
    const globalFavoriteList = document.getElementById("globalFavoriteList");
    globalFavoriteList.innerHTML = "";

    try {
        const response = await fetch(`/api/allFavorites`); // 모든 즐겨찾기 가져오는 API 호출
        if (!response.ok) {
            throw new Error('서버에서 데이터를 가져오는 데 실패했습니다.');
        }
        const favorites = await response.json();
		console.log(favorites)

        favorites.forEach((store) => {
			console.log(store)
            const li = document.createElement("li");
            li.textContent = `${store.STORENAME} (즐겨찾기한 회원: ${store.STORECOUNT}명)`; // 즐겨찾기된 수도 같이 표시
			
			// 항목 클릭 시 가게 이름을 URL 매개변수로 추가하고 이동
			li.onclick = () => {
			    const storeName = encodeURIComponent(store.STORENAME);
			    window.location.href = `/member/findOften?storeName=${storeName}`; // findOften.html로 이동
			};
			
            globalFavoriteList.appendChild(li);
        });
    } catch (error) {
        console.error("즐겨찾기 목록 업데이트 오류:", error);
        alert("즐겨찾기 목록을 가져오는 데 실패했습니다.");
    }
}


