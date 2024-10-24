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
            li.textContent = `${store.STORENAME} (즐겨찾기된 수: ${store.STORECOUNT})`; // 사용자 ID도 함께 표시
            globalFavoriteList.appendChild(li);
        });
    } catch (error) {
        console.error("즐겨찾기 목록 업데이트 오류:", error);
        alert("즐겨찾기 목록을 가져오는 데 실패했습니다.");
    }
}


