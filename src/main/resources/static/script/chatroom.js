
    const openPopupButton = document.getElementById("openPopupButton");
    const closePopupButton = document.getElementById("closePopupButton");
    const popupBackground = document.querySelector(".popup-background");

    openPopupButton.addEventListener("click", async function() {

    if(!nowChatUser){
    Swal.fire({
    title:'채팅상대를 지정해주새요',
    icon:'warning'});
    return;
}
    try {
    await $.ajax({
    url:'/api/v1/puching/checkpuching',
    type:'GET',
    data:{"receiverNo":nowChatUser}
})

}catch (err){
    Swal.fire({
    title:'이미 퍼칭이 진행 중 입니다.',
    icon:'warning'});
    return;
}

    popupBackground.style.display = "flex";

    const mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
    center: new kakao.maps.LatLng(37.4398411, 126.6640894), // 지도의 중심좌표 geo로 값을 변경해주자
    level: 3 // 지도의 확대 레벨
};
    // 지도를 생성한다
    const map = new kakao.maps.Map(mapContainer, mapOption);
    map.setMinLevel(3);
    map.setMaxLevel(6);
    const getCurrentPosBtn = document.getElementById("getCurrentPosBtn");
    getCurrentPosBtn.addEventListener("click", function() {
    navigator.geolocation.getCurrentPosition(
    locationLoadSuccess,
    locationLoadError
    );
});
    let marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(37.4398411, 126.6640894),
});

    function locationLoadSuccess(pos) {
    // geo로 받은 내위치
    const currentPos = new kakao.maps.LatLng(
    pos.coords.latitude,
    pos.coords.longitude
    );

    map.panTo(currentPos);
    marker.setMap(null);
    marker = new kakao.maps.Marker({
    position: currentPos,
});
    marker.setMap(map);
    pulat=pos.coords.latitude;
    pulng=pos.coords.longitude;

}
    function locationLoadError(pos) {
    const defalut_LatLng = new kakao.maps.LatLng(37.4398411, 126.6640894);

    map.panTO(defalut_LatLng);

    Swal.fire({
    title:'위치 정보를 못 가져 왔습니다.',
    icon:'error'});
}

    kakao.maps.event.addListener(map, 'dragend', function() {
    const center = map.getCenter();
    const now_Centerlat = roundLatlong(center.Ma, 7);
    const now_Centerlng = roundLatlong(center.La, 7);
    const nowPos = new kakao.maps.LatLng(now_Centerlat, now_Centerlng);

    marker.setMap(null);
    marker = new kakao.maps.Marker({
    position:nowPos,
});
    marker.setMap(map);
    pulat=now_Centerlat;
    pulng=now_Centerlng;

});

    const geocoder = new kakao.maps.services.Geocoder();

    kakao.maps.event.addListener(map, 'idle', function() {
    console.log("2123123")
    searchAddrFromCoords(map.getCenter(), displayCenterInfo);


});

    function searchAddrFromCoords(coords, callback) {
    // 좌표로 행정동 주소 정보를 요청합니다
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
    console.log(coords.getLng(),coords.getLat())
}

    function displayCenterInfo(result, status) {
    if (status === kakao.maps.services.Status.OK) {
    const infoDiv = document.getElementById('centerAddr');
    console.log(infoDiv)
    for(let i = 0; i < result.length; i++) {
    // 행정동의 region_type 값은 'H' 이므로
    if (result[i].region_type === 'H') {
    infoDiv.innerHTML = result[i].address_name;
    address=result[i].address_name;
    break;
}
}
}
}

})

    // Close the popup when the close button is clicked
    closePopupButton.addEventListener("click", function() {
    popupBackground.style.display = "none";
});

    // Close the popup when the user clicks outside of the popup content
    popupBackground.addEventListener("click", function(event) {
    if (event.target === popupBackground) {
    popupBackground.style.display = "none";
}
});

    const url = location.href.slice(7);
    let socket;
    if (url.includes("namohagae.kro.kr")){
    socket = new WebSocket('wss://namohagae.kro.kr/chatroom');
}else if(url.includes("localhost:8081")){
    socket = new WebSocket('ws://localhost:8081/chatroom');
}

    socket.onopen = function() {
    console.log("Chat Service On");

};

    socket.onmessage = function(event) {
    const message = event.data;
    const json = JSON.parse(message);
    const receiverUsername = json.receivername;
    console.log(receiverUsername)
    const messageContent = json.message;
    const senderUsername = json.sendername;

    const firstMessage = json.haveChatRoom;
    if(firstMessage==2){
    $.ajax({
    url: "/api/v1/findchatroom",
    type: "GET",
    data: {
    receiverEmail: senderUsername,
    _csrf:$_csrf
}
})
    .then(function(data) {
    // 성공적으로 응답을 받았을 때 실행할 코드
    console.log("응답 데이터:", data);
    const chatroom = ` <div class="chat_list">
        <a id="userclick" data-userno="` + data.chatRoomReceiverNo + `" data-useremail="` + data.memberEmail + `" data-usernick="` + data.memberNickName + `" data-userimage="` + data.memberImage + `">
            <div class="chat_people">
                <div class="chat_img"> <img src="` + data.memberImage + `" alt="profile"> </div>
                <div class="chat_ib">
                    <h5>` + data.memberNickName + `<span class="chat_date"></span></h5>
                </div>
            </div>
        </a>
    </div>`;

    $('.inbox_chat').append(chatroom)
})

    .catch(function(err) {
    // 오류 발생 시 실행할 코드
    console.error("오류 발생:", err);
});

}

    if(senderUsername!=nowChatEmail){   //여기다가 채팅방을 바꿔주는 css효과 넣고 누를시 없애주는 작업해야함
    return;
}

    // 수신된 메시지를 화면에 표시하는 코드를 작성합니다.
    const $content = `<div class="incoming_msg">
                                <div class="incoming_msg_img"> <img src="` + nowChatImage + `" alt="profile"> </div>
                                <p>` + nowChatNick + `</p>
                                <div class="received_msg">
                                    <div class="received_withd_msg">
                                        <p>` + messageContent + `</p>
                                        <span class="time_date">` + getCurrentTime() + `</span></div>
                                </div>
                            </div>`;
    $('.msg_history').append($content)
    $('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);
    // 채팅 로그에 메시지를 추가합니다.
};

    socket.onerror = function(error) {
    console.log("Chat Service Error: " + error);
};

    socket.onclose = function(event) {
    console.log("Chat Service Off with code " + event.code + " and reason " + event.reason);
};



    $('.msg_send_btn').on('click',function(){
    sendMessage()
})
    $('#message').keypress(function(event) {
    if ($(this).is(':focus') && event.key === 'Enter') {
    event.preventDefault();
    $('.msg_send_btn').click();
}
});



    async function sendMessage() {
    const messageInput = document.getElementById("message");
    const checkmessage = messageInput.value.trim();
    if(checkmessage==0){
    return;
}
    const message = messageInput.value;

    const messageToSend = {
    "message": message,
    "receiverUsername": nowChatEmail,//상대이메일
    "messageType": 'text'
};
    const jsonData = JSON.stringify(messageToSend);


    try {
    socket.send(jsonData);
    const content = ` <div class="outgoing_msg">
                                <div class="sent_msg">
                                    <p>` + message + `</p>
                                    <span class="time_date"> ` + getCurrentTime() + `</span> </div>
                            </div>`;


    $('.msg_history').append(content)
    $('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);
} catch (err) {
    Swal.fire({
    icon: 'error',
    title: '메세지 전송을 실패했습니다.',
    text: '잠시후에 다시 시도하거나 채팅방에 재접속 해주세요.'
})
}finally {
    messageInput.value = "";
}


}



    //이미지 전송 -> 전송중일 때 추가로 요청보내는걸 막자 loading 중 인걸 나타내든가
    $('#sendImageButton').on('click',async function(){
    const imageInput = $('#imageInput')[0];
    if(nowChatUser==undefined){
    await  Swal.fire({
    title:'채팅상대를 지정해주세요!',
    icon:'warning'
});
    return;
}
    if (!imageInput || !imageInput.files || imageInput.files.length === 0) {
    await Swal.fire({
    title:'이미지를 선택하신 후 전송 버튼을 눌러주세요!',
    icon:'warning'});
    return;
}

    const SwalWithImage = Swal.mixin({
    customClass: {
    image: 'custom-swal-image',
    confirmButton: 'custom-swal-confirm-button',
    cancelButton: 'custom-swal-cancel-button',
},
    buttonsStyling: true,
});

    const result = await SwalWithImage.fire({
    title: '이 이미지를 전송하시겠습니까?',
    text: '이미지는 한 번에 한 개씩 전송 가능합니다.',
    imageUrl: URL.createObjectURL(imageInput.files[0]),
    imageWidth: 400,
    imageHeight: 400,
    imageAlt: 'chat image',
    showCancelButton: true,
    confirmButtonText: '전송',
    cancelButtonText: '취소',
    reverseButtons: true,
});
    if(result.isConfirmed){

} else if(result.dismiss === Swal.DismissReason.cancel){
    return;
}

    const formData = new FormData();
    formData.append('file', imageInput.files[0]);
    formData.append('receiverNo', nowChatUser);
    formData.append('_csrf',$_csrf);

    try {
    const response = await $.ajax({
    url: '/api/v1/savechatimage',
    method: 'POST',
    data: formData,
    contentType: false,
    processData: false
});

    const imageMessage = response.messageContent;
    console.log(imageMessage)
    const imageMessageToSend = {
    "message": imageMessage,
    "receiverUsername": nowChatEmail,//상대이메일
    "messageType": 'image'
};
    const jsonData = JSON.stringify(imageMessageToSend);
    socket.send(jsonData);
    //보낸걸 자기거에 띄우는거
    const content = ` <div class="outgoing_msg">
                                <div class="sent_msg">
                                    <p>` + imageMessage + `</p>
                                    <span class="time_date"> ` + getCurrentTime() + `</span> </div>
                            </div>`;


    $('.msg_history').append(content)
    $('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);

} catch (error) {
    console.log(error);
    console.log("이미지 전송실패")
    Swal.fire({
    title:'이미지 전송을 실패했습니다.',
    icon:'error'});
}finally {
    imageInput.val(null);
}

});

    let nowChatUser = undefined;
    let nowChatEmail = undefined;
    let nowChatNick = undefined;
    let nowChatImage = undefined;

    $(document).on('click','#userclick',async function (){

    const userclickvalue = $(this).data("userno");
    if(nowChatUser==userclickvalue){
    return;
}
    nowChatUser = userclickvalue;
    const userclickemailvalue = $(this).data("useremail");
    nowChatEmail =userclickemailvalue;
    const userclicknickvalue = $(this).data("usernick");
    nowChatNick=userclicknickvalue;
    const userclickimagevalue = $(this).data("userimage");
    nowChatImage=userclickimagevalue;
    const $nowchatusernick = $('#nowchatusernick');
    $nowchatusernick.text('')
    $nowchatusernick.append(nowChatNick);




    $('.msg_history').empty();
    $('.msg_send_btn').prop('disabled', false);
    $('.chat_list').removeClass('active_chat');

    $(this).parent().addClass('active_chat')



    //nowChatUser와의 채팅 기록 불러오가
    let chatLog = [];
    const $sender = $('#myMemberNo').val();

    $.ajax({
    type: "GET",
    url: "/api/v1/findchatlog",
    data:{receiverNo: nowChatUser},
    success: function(data) {
    chatLog = data;
    // 채팅창에 이전 채팅 내용을 출력합니다.
    for (let i = 0; i < chatLog.length; i++) {
    const message = chatLog[i];
    if(message.messageSenderNo==$sender){
    const sendContent = `<div class="outgoing_msg">
                                <div class="sent_msg">
                                    <p>` + message.messageContent + `</p>
                                    <span class="time_date"> ` + message.messageWriteDate + `</span> </div>
                            </div>`;

    $('.msg_history').append(sendContent)

}
    if(message.messageSenderNo==nowChatUser) {
    const receiveContent = `<div class="incoming_msg">
                                <div class="incoming_msg_img"> <img src="` + nowChatImage + `" alt="profile"> </div>
                                <p>` + nowChatNick + `</p>
                                <div class="received_msg">
                                    <div class="received_withd_msg">
                                        <p>` + message.messageContent + `</p>
                                        <span class="time_date">` + message.messageWriteDate + `</span></div>
                                </div>
                            </div>`;

    $('.msg_history').append(receiveContent)
    // 메시지를 화면에 표시하는 코드를 작성합니다.
}

}
    $('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);
},
    error: function(xhr, status, error) {
    console.log("Error while getting chat log: " + error);
    Swal.fire({
    title:'채팅 기록을 못 불러 왔습니다.',
    icon:'error'});
}
});

});

    let puchingSocket;
    if (url.includes("namohagae.kro.kr")){
    puchingSocket = new WebSocket('wss://namohagae.kro.kr/puching');
}else if(url.includes("localhost:8081")){
    puchingSocket = new WebSocket('ws://localhost:8081/puching');
}

    let pulat = undefined;
    let pulng = undefined;
    let address = undefined;

    puchingSocket.onopen=function (){
    console.log("Puching Service On");
};
    puchingSocket.onmessage=function (event){
    let content;
    console.log(event)
    //퍼칭 취소 메세지를 받는경우
    const message = event.data;
    const json = JSON.parse(message);
    const receiverUsername = json.receivername;
    const messageContent = json.message;
    const senderUsername = json.sendername;

    if(senderUsername!=nowChatEmail){
    return;
}
    //퍼칭취소요청일 경우
    if(messageContent=='cancel'){
    content = `<span class="puching-title">퍼칭이 취소 되었습니다</span>`;
    $('.msg_history .puching-info').last().empty().append(content);
}
    //퍼칭수락요청일 경우
    if(messageContent=='accept'){
    content = `<span class="puching-description">퍼칭성공!</span>`;
    $('.puching-info .accept-button').remove()
    $('.puching-info .disaccept-button').remove()
    $('.msg_history .puching-info').last().append(content);
}

}
    puchingSocket.onerror=function (error){
    console.log(error)
}
    puchingSocket.onclose=function(event){
    console.log(event+'퍼칭웹소켓 닫힘')

}
    async function sendPuching() {
    //현재 퍼칭상태라면 못보내게 막자  상대와 자신과의 퍼칭 기록중 퍼칭상태가 취소나 리뷰작성완료인 사람인것만 제외하고
    //현재 신청걸려는 사람과 내가 퍼칭중 인지 체크

    const $day = $('#day').val();
    const $time = $('#time').val();
    const date = $day + " " + $time;
    if(compareDate(date)){
    Swal.fire({
    title:'날짜를 정확하게 입력해주세요',
    icon:'warning'});
    return;
}
    if($day==null||$day==""||$day==undefined){
    Swal.fire({
    title:'날짜를 입력해주세요',
    icon:'warning'});
    return
}
    if($time==null||$time==""||$time==undefined){
    Swal.fire({
    title:'퍼칭 시간을 입력해주세요',
    icon:'warning'});

    return
}
    if((pulng || pulat)==null||""||undefined){
    Swal.fire({
    title:'퍼칭장소를 지정해주세요!',
    icon:'warning'});
    return;
}
    if(nowChatEmail==undefined||""||null){
    Swal.fire({
    title:'퍼칭상대를 지정해주세요!',
    icon:'warning'});
    return;
}
    const param = {
    "receiverUsername": nowChatEmail,
    "lat": pulat,
    "lng": pulng,
    "day": $day,
    "time": $time,
    "address": address,
    "_csrf": $_csrf
};
    try {
    await $.ajax({
    url:'/api/v1/sendpuching',
    type:'POST',
    data:param
}).done(function(data){
    Swal.fire({
    title:nowChatNick+'님에게 퍼칭신청을 보냈습니다!',
    icon:'success'});
    popupBackground.style.display = "none";
    console.log(data)
    //content링 messageNo를 이용해서 append시키고 전송
    const puchingMessage = data.messageContent;
    console.log(puchingMessage)
    const puchingMessageToSend = {
    "message": puchingMessage,
    "receiverUsername": nowChatEmail,  //상대이메일
    "messageType": 'puching',
    "_csrf": $_csrf
};
    const jsonData = JSON.stringify(puchingMessageToSend);
    socket.send(jsonData);

    //자기거에 띄우는 코드
    const content = ` <div class="outgoing_msg">
                                <div class="sent_msg">
                                    <p>` + puchingMessage + `</p>
                                    <span class="time_date"> ` + getCurrentTime() + `</span> </div>
                            </div>`;


    $('.msg_history').append(content)
    $('.msg_history').scrollTop($('.msg_history')[0].scrollHeight);
})
}catch (err){
    console.log(err)
    Swal.fire({
    title:'퍼칭신청을 보내지 못했습니다',
    icon:'error'});
}
}
    function getCurrentTime() {
    let now = new Date();
    let year = now.getFullYear();
    let month = now.getMonth() + 1;
    let day = now.getDate();
    let hours = now.getHours();
    let minutes = now.getMinutes();
    let seconds = now.getSeconds();

    if (month < 10) {
    month = "0" + month;
}
    if (day < 10) {
    day = "0" + day;
}
    if (hours < 10) {
    hours = "0" + hours;
}
    if (minutes < 10) {
    minutes = "0" + minutes;
}
    if (seconds < 10) {
    seconds = "0" + seconds;
}
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}
    function compareDate(dateStr) {
    const currentDate = new Date(); // 현재 시간을 구합니다.
    const inputDate = new Date(dateStr); // 입력받은 날짜 값을 Date 객체로 변환합니다.

    return inputDate < currentDate; //  현재시간보다 작으면 트루를 반환
}

    function roundLatlong(num,n){
    if(isNaN(num)){
    console.log(num+'은 숫자가 아니다!!!')
    return ;
}
    return +(Math.round(num + "e+" + n)  + "e-" + n);
}

    $(document).on('click','.location-button',function(){
    const lat = $(this).data('lat');
    const lng = $(this).data('lng');
    window.open('/puching/locationview?lat=' + lat + '&lng=' + lng,'장소보기','width=600,height=600')
});
    $(document).on('click','.disaccept-button',async function(){
    try {
    const cancelParam = {
    message: "cancel",
    receiverUsername: nowChatEmail
};
    const jsondata = JSON.stringify(cancelParam);
    puchingSocket.send(jsondata)
    const content = `<span class="puching-title">퍼칭이 취소 되었습니다</span>`;

    $(this).parent().empty().append(content);

}catch (err){
    Swal.fire({
    title:'퍼칭신청을 취소하지 못했습니다',
    icon:'error'});
}

});
    $(document).on('click','.accept-button',async function(){
    try{
    const acceptParam = {
    message: "accept",
    receiverUsername: nowChatEmail
};
    const jsondata = JSON.stringify(acceptParam);
    puchingSocket.send(jsondata)
    //파칭수락한거 바로 어펜드 시키는 로직
    const content = `<span class="puching-description">퍼칭성공!</span>`;
    $('.puching-info .accept-button').remove()
    $('.puching-info .disaccept-button').remove()
    $('.msg_history .puching-info').last().append(content);
}catch (err){
    Swal.fire({
    title:'퍼칭신청을 수락하지 못했습니다',
    icon:'warning'});
}

});
    $(document).on('click','.review-button',async function(){
    if (!nowChatEmail) {
    Swal.fire({
    title:'리뷰를 작성할 상대의 채팅방을 클릭해주세요',
    icon:'warning'});
    return;
}

    try {
    const data = await $.ajax({
    url: '/api/v1/puching/checkreviewpuching',
    type: 'GET',
    data: {receiverNo: nowChatUser}
});
    const puchingNo = data.puchingNo;
    location.href = '/puching/reviewwrite?receiverNo=' + nowChatUser + '&puchingNo=' + puchingNo;
} catch (err) {
    console.log(err);
    Swal.fire({
    title:'해당 유저에게 작성할 퍼칭 리뷰가 없습니다!',
    icon:'warning'});
}


});

    $(document).ready(function(){

    $('.btn-area i').hover(
        function() {
            $(this).addClass('fa-bounce');
        },
        function() {
            $(this).removeClass('fa-bounce');
        }
    );

    function validateImage(imageInput) {
    const maxSizeInBytes = 5 * 1024 * 1024; // 5MB
    const allowedExtensions = ['jpg', 'jpeg', 'png', 'gif'];

    const file = imageInput.files[0];

    // 파일 유무 확인

    // 파일 크기 확인
    if (file.size > maxSizeInBytes) {
    Swal.fire({
    title:'이미지 크기는 5MB 이하여야 합니다.',
    icon:'warning'});
    imageInput.value = null;
    return;
}

    // 확장자 확인
    const fileName = file.name.toLowerCase();
    const fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);

    if (!allowedExtensions.includes(fileExtension)) {
    Swal.fire({
    title:'지원되는 이미지 형식은 jpg, jpeg, png, gif 입니다.',
    icon:'warning'});
    imageInput.value = null;
    return;
}
};

    $('#imageInput').on('change', function() {
    validateImage(this);
});

});
