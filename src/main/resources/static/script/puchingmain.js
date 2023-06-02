
    let pageNum = 1; // 현재 페이지
    const pageSize = 10; // 한 페이지당 출력할 유저의 수
    let isLoading = false;

    function roundLatlong(num,n){
    if(isNaN(num)){
    return ;
}
    return +(Math.round(num + "e+" + n)  + "e-" + n);
}
    async function locationLoadSuccess(pos){
    // geo로 받은 내위치
    const currentPos = new kakao.maps.LatLng(pos.coords.latitude, pos.coords.longitude);

    await map.panTo(currentPos);

    const marker = new kakao.maps.Marker({
    position: currentPos
});
    await marker.setMap(null);
    await marker.setMap(map)
    await $('#itemList').empty();
    pageNum=1;
    await loadItems(pageNum,pageSize,pos.coords.latitude,pos.coords.longitude)
    await $('#loading').hide();

    // map.setCenter(currentPos);
}
    async function locationLoadError(pos){
    const defalut_LatLng = new kakao.maps.LatLng(37.4398411, 126.6640894);

    await map.panTo(defalut_LatLng)
    Swal.fire({
    title:'위치 정보를 찾을 수 없습니다.',
    icon:'warning'});
    await $('#loading').hide();
}
    async function getCurrentPosBtn(){
    await $('#loading').show();
    await navigator.geolocation.getCurrentPosition(locationLoadSuccess,locationLoadError);

}


    const $itemList = $('#itemList');

    // 스크롤 이벤트 핸들러 등록
    $itemList.scroll(function() {

    if (($itemList.scrollTop()+20) + $itemList.height() >= $itemList[0].scrollHeight) {
    // 리스트의 끝까지 도달함

    console.log(pageNum)
    const center = map.getCenter();
    const now_Centerlag = roundLatlong(center.Ma, 7);
    const now_Centerlng = roundLatlong(center.La, 7);

    pageNum++;
    loadItems(pageNum,pageSize,now_Centerlag,now_Centerlng); //

}
});

    // 중심좌표에 따른 유저리스트 부르는 함수
    async function loadItems(pageNum,pageSize,latitude,longitude) {
    if (!isLoading) {
    isLoading = true;
    $('#loading').show(); // 로딩 div 노출
    $.ajax({
    url: "/api/v1/puching/userlist",
    type: "GET",
    data: {
    pageNum: pageNum,
    pageSize: pageSize,
    latitude: latitude,
    longitude: longitude
},
    success: await function (data) {
    const userdata = data;
    console.log(userdata);

    // 데이터 가져오기 성공
    const itemList = $("#itemList");
    $.each(userdata, function (i, item) {
    itemList.append("<div class='item'><img class='itemImage' src='"+item.memberImage+"' alt='profile image'><" +
    "span class='itemNickname'>" + item.memberNickName + "</span>" +
    "<a href=/member/profile?memberNo="+item.memberNo+"><i class=\"fa-solid fa-circle-user fa-2xl\" style=\"color: #ea986b;\"></i></a>" + item.distance + "KM</div>");

});
    isLoading = false;
    $('#loading').hide();
},
    error: await function (err) {
    console.log(err)
    // 데이터 가져오기 실패
    Swal.fire({
    title:'유저정보를 가져올 수 없습니다.',
    icon:'warning'});
    isLoading = false;
    $('#loading').hide();
}

});



}

};

    const mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
    center: new kakao.maps.LatLng(37.4398411, 126.6640894), // 지도의 중심좌표 geo로 값을 변경해주자
    level: 3 // 지도의 확대 레벨
};
    // 지도를 생성한다
    const map = new kakao.maps.Map(mapContainer, mapOption);
    map.setMinLevel(3);
    map.setMaxLevel(6);

    // 다각형에 마우스오버 이벤트가 발생했을 때 변경할 채우기 옵션입니다
    const town_mouseoverOption = {
    fillColor: '#ea986b', // 채우기 색깔입니다
    fillOpacity: 0.4 // 채우기 불투명도 입니다
};
    // 다각형에 마우스아웃 이벤트가 발생했을 때 변경할 채우기 옵션입니다
    const town_mouseoutOption = {
    fillColor: '#ea986b', // 채우기 색깔입니다
    fillOpacity: 0.7 // 채우기 불투명도 입니다
};

    function updatetown() {
    $.get("/api/v1/puching/townlist",async function (data) {
        const circles = await data.map(function (i, position) {
            const circle = new kakao.maps.Circle({
                map: map, // 원을 표시할 지도 객체
                center: new kakao.maps.LatLng(i.townLatitude, i.townLongitude), // 지도의 중심 좌표
                radius: 100, // 원의 반지름 (단위 : m)
                fillColor: '#ea986b', // 채움 색
                fillOpacity: 0.7, // 채움 불투명도
                strokeWeight: 3, // 선의 두께
                strokeColor: '#de743a', // 선 색
                strokeOpacity: 0.9, // 선 투명도
                strokeStyle: 'solid' // 선 스타일
            });
            // var infowindow = new kakao.maps.InfoWindow({
            //     map: map,
            //     position: new kakao.maps.LatLng(i.townLatitude, i.townLongitude),
            //     content: i.townDong + '^_^' + i.townCnt + '명'
            // });
            const customOverlay = new kakao.maps.CustomOverlay({
                map: map,
                position: new kakao.maps.LatLng(i.townLatitude, i.townLongitude),
                content: '<div id="townCnt" ">' + i.townCnt + '</div>'
            });

            // 원클릭시 리스트 초기화 및 조건에 맞는 리스트 출력
            kakao.maps.event.addListener(circle, 'click', function () {
                const circlecenter = circle.getPosition();
                const Ma = roundLatlong(circlecenter.Ma, 7);
                const La = roundLatlong(circlecenter.La, 7);
                map.panTo(new kakao.maps.LatLng(Ma, La));
                $('#itemList').empty();
                pageNum = 1;
                loadItems(pageNum, pageSize, Ma, La);
            });

            // 마커에 mouseover 이벤트를 등록한다
            kakao.maps.event.addListener(circle, 'mouseover', function () {
                circle.setOptions(town_mouseoverOption);
            });

            // 마커에 mouseout 이벤트 등록
            kakao.maps.event.addListener(circle, 'mouseout', function () {
                circle.setOptions(town_mouseoutOption);
            });
            return circle;
        });


    })
}
    // 지도 확대 레벨 변화 이벤트를 등록한다
    kakao.maps.event.addListener(map, 'zoom_changed', function () {
    console.log('지도의 현재 확대레벨은 ' + map.getLevel() + '레벨 입니다.');
});
    // 드러그 앤드 이벤트 발생시 리스트 초기화하고 중앙 좌표에서 가까운 사람을 리스트로 출력
    kakao.maps.event.addListener(map, 'dragend', function() {
    const center = map.getCenter();
    const now_Centerlag = roundLatlong(center.Ma, 7);
    const now_Centerlng = roundLatlong(center.La, 7);
    $('#itemList').empty();
    pageNum=1;
    loadItems(pageNum,pageSize,now_Centerlag,now_Centerlng);
});


    const geocoder = new kakao.maps.services.Geocoder();

    kakao.maps.event.addListener(map, 'idle', function() {
    console.log("2123123")
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);
});

    function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
}

    function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
    const infoDiv = document.getElementById('centerAddr');

    for(let i = 0; i < result.length; i++) {
    // 행정동의 region_type 값은 'H' 이므로
    if (result[i].region_type === 'H') {
    infoDiv.innerHTML = result[i].address_name;
    break;
}
}
}
}

    $(document).ready(async function(){
    await updatetown();
    const center = map.getCenter();
    const now_Centerlag = roundLatlong(center.Ma, 7);
    const now_Centerlng = roundLatlong(center.La, 7);

    await loadItems(pageNum,pageSize,now_Centerlag,now_Centerlng); //
    await  searchAddrFromCoords(center, displayCenterInfo);

});
