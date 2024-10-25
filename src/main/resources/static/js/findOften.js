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

    // URL에서 가게 이름 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const storeName = urlParams.get('storeName');
    if (storeName) {
        document.getElementById("storeQuery").value = decodeURIComponent(storeName); // 검색창에 설정
        searchStores(); // 자동으로 검색 수행
    }
}

async function searchStores() {
    const query = document.getElementById("storeQuery").value.trim();
    const searchResults = document.getElementById("searchResults");
    searchResults.innerHTML = ""; // 이전 검색 결과 초기화

    if (!query) {
        alert("검색어를 입력하세요.");
        return;
    }

    try {
        const response = await fetch(`https://dapi.kakao.com/v2/local/search/keyword.json?query=${encodeURIComponent(query)}`, {
            headers: {
                Authorization: `KakaoAK ${API_KEY}`,
            },
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();

        if (!data.documents || data.documents.length === 0) {
            alert("검색 결과가 없습니다.");
            return;
        }

        const favorites = await fetchFavorites(); // 현재 사용자의 즐겨찾기 가져오기
        const favoriteStoreIds = favorites.map(store => store.storeName);
        
        data.documents.forEach((store) => {
            if (store.address_name.includes("성동구")) {
                const li = document.createElement("li");
                li.textContent = `${store.place_name} - ${store.address_name}`;
                
                // 클릭 시 해당 위치 표시
                li.onclick = () => {
                    showLocation(store.address_name, store.place_url);
                    document.getElementById("storeId").value = store.place_name;
                    selectedStore = store.place_name; // 선택된 가게 업데이트
                };

                searchResults.appendChild(li);

                // 이미 즐겨찾기에 추가된 가게라면 비활성화
                if (favoriteStoreIds.includes(store.place_name)) {
                    li.style.textDecoration = "line-through"; // 이미 추가된 가게 표시
                }
            }
        });
    } catch (error) {
        console.error("Error:", error);
        alert("가게 검색에 실패했습니다: " + error.message);
    }
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
async function addFavorite() {
    if (selectedStore) {
        const favorites = await fetchFavorites(); // 현재 사용자의 즐겨찾기 가져오기
        const favoriteStoreIds = favorites.map(store => store.storeName);

        if (!favoriteStoreIds.includes(selectedStore)) {
            await saveFavoriteToDB(selectedStore); // DB에 저장
            alert(`${selectedStore} 가 즐겨찾기 목록에 추가되었습니다.`);
            updateFavoriteList();
        } else {
            alert(`${selectedStore} 는 이미 즐겨찾기 목록에 있습니다.`);
        }
    } else {
        alert("가게를 선택하세요.");
    }
}

// 현재 사용자의 즐겨찾기 가져오기
async function fetchFavorites() {
    try {
        const response = await fetch(`/api/favorite`);
        if (!response.ok) {
            throw new Error('서버에서 데이터를 가져오는 데 실패했습니다.');
        }
        return await response.json();
    } catch (error) {
        console.error("즐겨찾기 가져오기 오류:", error);
        alert("즐겨찾기 목록을 가져오는 데 실패했습니다.");
        return [];
    }
}

// DB에 즐겨찾기 저장
async function saveFavoriteToDB(storeName) {
    try {
        await fetch('/api/favorites', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ storeName }), // storeName만 JSON으로 전송
        });
    } catch (error) {
        console.error("DB 저장 실패:", error);
        alert("즐겨찾기 저장에 실패했습니다.");
    }
}

// 즐겨찾기 목록 업데이트
async function updateFavoriteList() {
    const favoriteList = document.getElementById("favoriteList");
    favoriteList.innerHTML = "";

    try {
        const favorites = await fetchFavorites();
        favorites.forEach((store) => {
            const li = document.createElement("li");
            li.textContent = store.storeName; // storeName 프로퍼티에 맞게 수정
            
            li.onclick = () => {
                document.getElementById("storeQuery").value = store.storeName; // 검색창에 가게 이름 설정
                searchStores(); // 검색 수행

                // 검색 결과 중 해당 가게를 자동으로 클릭하는 효과
                setTimeout(() => {
                    const searchResults = document.getElementById("searchResults").children;
                    for (let i = 0; i < searchResults.length; i++) {
                        if (searchResults[i].textContent.includes(store.storeName)) {
                            searchResults[i].click(); // 자동으로 클릭한 것처럼 실행
                            break; // 첫 번째 일치하는 가게만 클릭
                        }
                    }
                }, 100); 
            };

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

// 즐겨찾기 제거
async function removeFavorite(storeName) {
    try {
        const response = await fetch(`/api/favorite/delete`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ storeName }), // JSON 형식으로 storeName 전달
        });

        if (response.ok) {
            alert(`${storeName} 가 즐겨찾기 목록에서 삭제되었습니다.`);
            updateFavoriteList(); // 목록 업데이트
        } else {
            const errorText = await response.text();
            alert("삭제 오류: " + errorText);
        }
    } catch (error) {
        console.error("즐겨찾기 삭제 오류:", error);
        alert("즐겨찾기 삭제에 실패했습니다.");
    }
}

