

$(function(){

    $("#password").keyup(function(){
        var password = $("#password").val();
        var b_num =$("#b_num").val();
        $.ajax({
        method: "POST",
        url: "/board/pwcheck",
        data:{
        'm_password': password,
        'b_num':b_num
        },
        success: function(result){
            if(result==1){
                document.getElementById('update').style.display='';
            }else{
                document.getElementById('pw_result').innerHTML="비밀번호가 다릅니다";
                document.getElementById('update').style.display='none';
            }
        }
        })

    })

})

    function delete0(){
        var b_num = document.getElementById("b_num").value;
        $.ajax({
        method: "post",
        url:"/board/delete",
        data:{
            'b_num' : b_num
        },
        success: function(result){
            alert(result);
            if(result==1){
                alert("삭제되었습니다");
                location.href = "/";
            }
        }
        })

    }

function boardwrite() {
    //폼태그 가져오기
    var formdata = new FormData(form);
    $.ajax({
        type: "post",
        url: "/board/boardwritecontroller",
        data : formdata,
        processData : false,
        contentType : false,
        success : function(result){
            alert(result);
            if(result==1){
                location.href = "/";
            }
        }

    })

}
    function boardwrite0(){

        //폼태그 가져오기
        var formData = new FormData(form);
        //폼을 컨트롤러에게 전송
        $.ajax({
            type:"post",
            url : "/board/boardwritecontroller",
            data : formData,
            processData : false,
            contentType : false,    //첨부파일 보낼때,
            success :function(data){
                alert(data);
               if(data==1){
                location.href = "/board/boardlist";
               }
            }

        })

    }

    function filedelete(){
        var b_num = document.getElementById("b_num0").value;
        var b_file = document.getElementById("b_file0").value;
        alert(b_num);
               $.ajax({
                    url : "/board/filedeletecontroller",
                    data : {
                    'b_num' : b_num,
                    'b_file' : b_file
                    },
                    success :function(data){
                       if(data==1){
                        alert("파일이 삭제되었습니다");
                            location.href = "/board/boardupdate/"+b_num
                       }
                    }

                })
    }























