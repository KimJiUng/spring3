// 작성버튼 눌렀을때 실행되는 메소드
function send(){
    $.ajax({    // ajax 비동기 사용시 jquery cdn 필수
        url : "save",   // java 통신할 URL 작성
        data : {"content" : $("#content").val() },  // 보낼 데이터
        success : function(result){ // 응답 데이터
            $("#content").val("");
            호출();
        }
    })
}

$(function(){
    호출();
});

let list = [];
// 해당 페이지가 열렸을때 실행되는 메소드
function 호출(){
    $.ajax({
        url : "getlist",
        success : function(result){
            list = result;
            let html = "";
            html += '<tr><th>번호</th><th>내용</th><th>비고</th></tr>';
            for(let i=0; i<list.length; i++){
                html += '<tr>'+
                            '<td>'+list[i]["no"]+'</td>'+
                             '<td>'+list[i]["content"]+'</td>'+
                             '<td> <button onclick="update('+list[i]["no"]+')">수정</button> </td>'+
                             '<td> <button onclick="delete1('+list[i]["no"]+')">삭제</button> </td>'+
                         '/tr>';
            }
            $("#viewtable").html(html);
        }
    });
}

function update(no){
    $.ajax({
        url : "update",
        data : {"no" : no, "content" : $("#content").val()},
        success : function(result){
            if(result==1){
                alert("수정완료");
                호출();
            }
        }

    });
}

function delete1(no){
    $.ajax({
        url : "delete1",
        data : {"no" : no},
        success : function(result){
            if(result==1){
                alert("삭제완료");
                호출();
            }
        }
    });
}