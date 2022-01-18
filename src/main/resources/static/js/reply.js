
function replywrite(){

    var replyinput =$("#replyinput").val();
    var replyPw = $("#replyPw").val();
    var b_num = $("#b_num").val();

    if(replyPw==""){
        alert("댓글 비밀번호를 입력해주세요");
        return;
    }
    if(replyinput==""){
        alert("댓글 입력해주세요");
        return;
    }

    $.ajax({
        url:"/board/replywrite",
        data: {
            "b_num" : b_num ,
            "replyinput" : replyinput,
            "replyPw" : replyPw
        },
        success :function(data){
          if(data==1){
               $("#replytable").load(location.href+" #replytable");
            }
        }
    })

}

function replypwconfirm(rnum){

    document.getElementById('replypw'+rnum).style.display='';
}

function replydelete(rnum){
        var replypw = $("#replypwq"+rnum).val();
        alert(rnum);
        alert(replypw);
        $.ajax({
            url:"/board/replypwconfirm",
            data: {
                "rnum" : rnum ,
                "replypw" : replypw
            },
            success :function(data){
                if(data==1){
                   $("#replytable").load(location.href+" #replytable");
                }else{
                    alert("비밀번호가 틀립니다")
                }

            }
        })
}