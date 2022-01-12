

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

$(function(){

    $("#delete").onclick(function(){
        alert("asd");
    })

})























