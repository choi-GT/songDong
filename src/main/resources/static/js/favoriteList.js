// 즐겨찾기 목록 업데이트
async function updateFavoriteList() {
    const favoriteList = document.getElementById("favoriteList");
    favoriteList.innerHTML = "";

    try {
        const response = await fetch(`/api/favorite`);
        if (!response.ok) {
            throw new Error('서버에서 데이터를 가져오는 데 실패했습니다.');
        }
        const favorites = await response.json();

        favorites.forEach((store) => {
            const li = document.createElement("li");
            li.textContent = store.storeName; // storeName 프로퍼티에 맞게 수정

            const removeButton = document.createElement("span");
            removeButton.innerHTML = '<i class="fa fa-times" aria-hidden="true"></i>';
            removeButton.style.cursor = "pointer";
            removeButton.style.marginLeft = "10px";
            removeButton.onclick = () => {
                removeFavorite(store.storeName); // storeName으로 삭제 함수 호출
            };

            li.appendChild(removeButton);
            favoriteList.appendChild(li);
        });
    } catch (error) {
        console.error("즐겨찾기 목록 업데이트 오류:", error);
        alert("즐겨찾기 목록을 가져오는 데 실패했습니다.");
    }
}