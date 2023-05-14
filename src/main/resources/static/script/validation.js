function check(value, pattern, message, element) {
    if(value=="") {
        element.text("필수입력입니다").attr("class","fail");
        return false;
    }
    if(pattern.test(value)==false) {
        element.text(message).attr("class","fail");
        return false;
    }
    return true;
}
function emailCheck() {
    const pattern = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    return check($('#memberEmail').val(), pattern, "이메일을 정확하게 입력하세요", $('#memberEmail_msg'));
}

function passwordCheck() {
    $('#memberPassword_msg').text("");
    const pattern = /^[0-9a-zA-Z]{8,16}$/;
    return check($('#memberPassword').val(), pattern, "비밀번호는 영숫자 8~16자입니다", $("#memberPassword_msg"));
}

function password2Check() {
    $('#memberPassword2_msg').text("");
    const value =$('#memberPassword2').val();
    if(value=="")	{
        $('#memberPassword2_msg').text("필수입력입니다").attr("class","fail");
        return false;
    }
    if(value!=$('#memberPassword2').val()) {
        $('#memberPassword2_msg').text("비밀번호가 일치하지 않습니다.").attr("class","fail");
        return false;
    }
}


function irumCheck() {
    $('#memberNickname_msg').text("");
    const pattern = /^[가-힣a-z]{2,8}$/;
    return check($('#memberNickname').val(), pattern, "별명은 한,영자 2~8자입니다", $("#memberNickname_msg"));
}



$(document).ready(function() {
    $('#memberProfileImage').change(loadProfile);
    $('#memberPassword').blur(passwordCheck);
    $('#memberPassword2').blur(password2Check);

    $('#sendEmail').click(async function (){
        try {
            const url = "/member/sendAudenticationCode?email=" + $("#memberEmail").val();
            // await를 빼먹으면 나중에 결과가 들어갈 것이라는 약속(Promise)로 리턴
            // Promise에는 done()을 이용해 성공 핸들러를 지정할 수 있다
            const result = await $.ajax({url:url, method:"patch"})
            alert(result);
            $("#memberEmail_msg").text(result).attr("class","fail");
            $('#checkCodeArea').removeClass('hidden');
        } catch(err) {
            $("#memberEmail_msg").text("이메일을 찾지 못했습니다").attr("class","fail");
        }
    });
    $('#checkCode').click(async function(){
        try {
            const url = "/member/checkAudenticationCode?code=" + $("#code").val();
            // await를 빼먹으면 나중에 결과가 들어갈 것이라는 약속(Promise)로 리턴
            // Promise에는 done()을 이용해 성공 핸들러를 지정할 수 있다
            const result = await $.ajax({url:url, method:"get"});
            $("#memberEmail_msg").text(result).attr("class","fail");
            $('#join').prop('disabled', false);
        }catch (err){
            $("#memberEmail_msg").text("인증에 실패했습니다").attr("class","fail");
        }
    });

    $('#memberEmail').blur(async function() {
        if(emailCheck()==false)
            return false;
        try {
            const result = await $.ajax('/member/checkEmail?email=' + $('#memberEmail').val());
            if (result==false){
            $("#memberEmail_msg").text("사용 가능한 이메일 입니다").attr("class","success");
            }
            else {
            $("#memberEmail_msg").text("사용중입니다").attr("class","fail");
            }
        } catch(err) {
            console.log(err);
        }
    });

    // email 검증하시오
    $('#memberNickname').blur(async function() {
        if(irumCheck()==false)
            return false;
        try {
          const result= await $.ajax('/member/checkNickname?nickname=' + $('#memberNickname').val());
          if (result==false){
          $("#memberNickname_msg").text("사용 가능한 별명입니다").attr("class","success");
          }  else {
            $("#memberNickname_msg").text("사용중입니다").attr("class","fail");
          }
        } catch(err) {
        console.log(err);
        }
    });



    $('#join').click(async function() {

        //  필수입력에 대한 널 체크, 패턴 체크 수행
        const result = emailCheck() && passwordCheck() && password2Check() && irumCheck() && emailCheck() && a;
        if(result==false)
            return false;

        try {
            await $.ajax('/member/checkEmail?email=' + $('#memberEmail').val());
            await $.ajax('/member/checkNickname?nickname=' + $('#memberNickname').val());
            $('#join_form').submit();
        } catch(err) {
            console.log(err);
            alert("아이디나 이메일이 사용 중입니다");
        }
    })
});
