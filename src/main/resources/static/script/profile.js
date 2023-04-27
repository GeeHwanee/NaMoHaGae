// 프사 출력
function loadProfile() {
    const file = $("#profile")[0].files[0];
    const maxSize = 1024*1024;
    if(file.size>maxSize) {
        Swal.fire('프로필 크기 오류', '프로필 사진은 1MB를 넘을 수 없습니다','error');
        $("#profile").val("");
        $("#show-profile").removeAttr("src");
        return false;
    }
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function() {
        $("#show-profile").attr("src", reader.result);
    }
    return true;
}