// 로컬 저장소에서 즐겨찾기 목록 가져오기
const storeIds = JSON.parse(localStorage.getItem("favoriteStores")) || [];

// 즐겨찾기 목록을 페이지에 표시하는 함수
function displayFavoriteList() {
    const favoriteList = document.getElementById("favoriteList");
    favoriteList.innerHTML = ""; // 기존 목록 초기화

    storeIds.forEach((id) => {
        const li = document.createElement("li");
        li.textContent = id;

        const removeButton = document.createElement("span");
        removeButton.innerHTML = '<i class="fa fa-times" aria-hidden="true"></i>';
        removeButton.style.cursor = "pointer";
        removeButton.style.marginLeft = "10px";
        removeButton.onclick = () => {
            removeFavorite(id);
        };

        li.appendChild(removeButton);
        favoriteList.appendChild(li);
    });
}

// 즐겨찾기 제거 함수
function removeFavorite(storeId) {
    const index = storeIds.indexOf(storeId);
    if (index > -1) {
        storeIds.splice(index, 1); // 로컬 저장소에서 제거
        localStorage.setItem("favoriteStores", JSON.stringify(storeIds));
        displayFavoriteList(); // 리스트 업데이트
        alert(`${storeId} 는 즐겨찾기 목록에서 삭제되었습니다.`);
    } else {
        alert(`${storeId} 는 즐겨찾기 목록에 없습니다.`);
    }
}

// 페이지 로드 시 즐겨찾기 목록 표시
window.onload = displayFavoriteList;