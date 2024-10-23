let storeIds = [];
const API_KEY = "78a4b456e0a19ac893591fb5dc67d523"; // JavaScript key
let selectedStore;
let map; // 전역 변수로 지도 저장

// 카카오 맵 로드 및 초기화
kakao.maps.load(() => { 
    initMap();	
    updateFavoriteList();
});

// 지도 초기화 함수
function initMap() {
    const mapContainer = document.getElementById("map");
    const mapOptions = {
        center: new kakao.maps.LatLng(37.5636, 127.0427), // 성동구 중심 좌표
        level: 3, // 확대 레벨
    };
    map = new kakao.maps.Map(mapContainer, mapOptions); // 전역 변수로 지도 인스턴스 저장
}

// 가게 검색
function searchStores() {
    const query = document.getElementById("storeQuery").value.trim();
    const searchResults = document.getElementById("searchResults");
    searchResults.innerHTML = ""; // 이전 검색 결과 초기화

    if (!query) {
        alert("검색어를 입력하세요.");
        return;
    }

    fetch(`https://dapi.kakao.com/v2/local/search/keyword.json?query=${encodeURIComponent(query)}`, {
        headers: {
            Authorization: `KakaoAK ${API_KEY}`,
        },
    })
    .then((response) => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then((data) => {
        if (!data.documents || data.documents.length === 0) {
            alert("검색 결과가 없습니다.");
            return;
        }

        data.documents.forEach((store) => {
            const li = document.createElement("li");
            li.textContent = `${store.place_name} - ${store.address_name}`;
            li.onclick = () => {
                showLocation(store.address_name, store.place_url); // URL도 넘겨줌
                selectedStore = store.place_name;
                document.getElementById("storeId").value = selectedStore;
                document.getElementById("addFavoriteBtn").scrollIntoView({ behavior: "smooth" });
            };
            searchResults.appendChild(li);
        });
    })
    .catch((error) => {
        console.error("Error:", error);
        alert("가게 검색에 실패했습니다: " + error.message);
    });
}

// 주소로 위치 표시
function showLocation(address, placeUrl) {
    const geocoder = new kakao.maps.services.Geocoder();

    geocoder.addressSearch(address, function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            map.setCenter(coords); // 지도 중심을 결과 좌표로 설정

            // 작은 마커 이미지 URL
            const imageSrc = 'https://cdn-icons-png.flaticon.com/512/148/148836.png',
                imageSize = new kakao.maps.Size(30, 30),  // 마커 크기 조정
                imageOption = { offset: new kakao.maps.Point(15, 30) };

            const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);
            const marker = new kakao.maps.Marker({
                map: map,
                position: coords,
                image: markerImage,
            });

            // 마커 클릭 시 링크로 이동
            kakao.maps.event.addListener(marker, 'click', function() {
                window.open(placeUrl, '_blank'); // 새 창에서 링크 열기
            });

            const infowindow = new kakao.maps.InfoWindow({
                content: `<div style="width:150px;text-align:center;padding:6px 0;">${address}</div>`,
            });
            infowindow.open(map, marker);
        }
    });
}

// 즐겨찾기 추가
async function addFavorite(userId) {
    if (selectedStore && !storeIds.includes(selectedStore)) {
        storeIds.push(selectedStore);
        //localStorage.setItem("favoriteStores", JSON.stringify(storeIds));
        await saveFavoriteToDB(selectedStore, userId); // DB에 저장
        updateFavoriteList();
        alert(`${selectedStore} 가 즐겨찾기 목록에 추가되었습니다.`);
    } else if (storeIds.includes(selectedStore)) {
        alert(`${selectedStore} 는 이미 즐겨찾기 목록에 있습니다.`);
    } else {
        alert("가게를 선택하세요.");
    }
}

// DB에 즐겨찾기 저장
async function saveFavoriteToDB(storeName) {
    try {
        await fetch('/api/favorites', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json', // 텍스트로 전송
            },
            body: storeName, // storeName만 전송
        });
    } catch (error) {
        console.error("DB 저장 실패:", error);
        alert("즐겨찾기 저장에 실패했습니다.");
    }
}

// 즐겨찾기 목록 업데이트
function updateFavoriteList() {
    const favoriteList = document.getElementById("favoriteList");
    favoriteList.innerHTML = "";

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

// 즐겨찾기 제거
async function removeFavorite(storeId) {
    const index = storeIds.indexOf(storeId);
    if (index > -1) {
        // 로컬 저장소에서 제거
        storeIds.splice(index, 1);
        //localStorage.setItem("favoriteStores", JSON.stringify(storeIds));

        // DB에서 제거 요청
        await deleteFavoriteFromDB(storeId);

        updateFavoriteList();
        alert(`${storeId} 는 즐겨찾기 목록에서 삭제되었습니다.`);
    } else {
        alert(`${storeId} 는 즐겨찾기 목록에 없습니다.`);
    }
}

// DB에서 즐겨찾기 삭제
async function deleteFavoriteFromDB(storeName) {
    try {
        await fetch('http://192.168.30.10:8080/api/favorites', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json', // JSON 형식으로 설정
            },
            body: JSON.stringify({ storeName }), // storeName을 JSON 객체로 감싸서 전송
        });
    } catch (error) {
        console.error("DB 삭제 실패:", error);
        alert("즐겨찾기 삭제에 실패했습니다.");
    }
}


