document.addEventListener('DOMContentLoaded', () => {
    updateGlobalFavoriteList();
});

async function updateGlobalFavoriteList() {
    const globalFavoriteList = document.getElementById("globalFavoriteList");
    globalFavoriteList.innerHTML = "";

    try {
        const response = await fetch(`/api/allFavorites`);
        if (!response.ok) {
            throw new Error('서버에서 데이터를 가져오는 데 실패했습니다.');
        }
        const favorites = await response.json();

        favorites.forEach((store) => {
            const li = document.createElement("li");
            li.textContent = `${store.STORENAME} (즐겨찾기한 회원: ${store.STORECOUNT}명)`;
            li.onclick = () => {
                const storeName = encodeURIComponent(store.STORENAME);
                // URL로 이동하면서 쿼리 스트링에 storeName 전달
                window.location.href = `/member/findOften?storeName=${storeName}`;
            };
            globalFavoriteList.appendChild(li);
        });
    } catch (error) {
        console.error("즐겨찾기 목록 업데이트 오류:", error);
        alert("즐겨찾기 목록을 가져오는 데 실패했습니다.");
    }
}
