<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="/main_header.jsp" flush="false"/>


<style>
body > .container {
	width:700px !important;
}
.card-body > .row > div {
	max-width: 100%;
	width: 100%;
	flex:none;
}

.pwErr {color: #f00;}
</style>

<script>
var idChk = true;

	$(function(){
		
		// 중복체크
		$("#idChkBtn").click(function(){
			
			var userid = $("#id").val();
			
			$.ajax({
				type : 'POST',
				data : userid,
				url : "/member/checkId",
				dataType : "json",
				contentType : "application/json; charset=UTF-8",
				success : function(data){
					if(data.success){
						alert("이미 사용중인 아이디입니다. 다시 입력해 주세요.");
						$('#id').focus();
					}else{
						alert("사용가능한 아이디입니다.");
						$('#pw').focus();
						idChk = false;
					}
				},
				error : function(error){
					alert("아이디를 입력하세요!");
				}
			});
		
		});
		
		
		//비밀번호 확인
		$("#pw,#pwChk").on("keyup",function(){
			var isCheck = ($("#pw").val() != $("#pwChk").val() && !$("#pw").is(":focus"));
			if(isCheck) {
				console.log($(this).val());
				$(".pwErr").text('비밀번호가 틀립니다!');
				$(this).focus();
			}else{
				document.getElementsByClassName("pwErr")[0].innerHTML = "";
			}
		})
		
		$("#joinBtn").on("click",function(e){
			e.preventDefault();
			if(valChk()){
				$("form").submit();
			}
		});
	});
	
	function valChk(){
		if($("#id").val().length == 0){
			alert("아이디를 입력하세요");
			$("#id").focus();
			return false;
		}
		if($("#pw").val().length == 0){
			alert("비밀번호를 입력하세요");
			$("#pw").focus();
			return false;
		}
		if($("#name").val().length == 0){
			alert("이름을 입력하세요");
			$("#name").focus();
			return false;
		}
		if($("#phone").val().length == 0){
			alert("전화번호를 입력하세요");
			$("#phone").focus();
			return false;
		}
		if($("#nickname").val().length == 0){
			alert("닉네임을 입력하세요");
			$("#nickname").focus();
			return false;
		}
		if($("#gender").val().length == 0){
			alert("성별 입력하세요");
			$("#gender").focus();
			return false;
		}
		if($("#birth").val().length == 0){
			alert("생년월일 입력하세요");
			$("#birth").focus();
			return false;
		}
		return true;
	}
</script>

<body class="bg-gradient-primary">

  <div class="container">

    <div class="card o-hidden border-0 my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="text-gray-900 mb-4">회원 가입 양식</h1>
              </div>
              
              <form:form class="user" modelAttribute="join" method="post" action="/member/join">
 								<div class="form-group row">
	                <div class="col mb-3 mb-sm-3" style="width:100%;margin-right: 10px;display: inline-block;">
	                  <form:input type="email" path="id" class="form-control form-control-user" placeholder="아이디(Email)"></form:input>
	                </div>
	                <div class="col-sm-2.5 text-right">
	                  <button  style="margin-right: 16px;" class="btn btn-outline-secondary" type="button" id="idChkBtn">중복체크</button>
	                </div>
	               </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <form:input type="password" path="pw" class="form-control form-control-user" placeholder="비밀번호"></form:input>
                  </div>
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="password" id="pwChk" class="form-control form-control-user" placeholder="비밀번호 확인"></input>
                 		<div class="form-label-group">
									  	<span class="pwErr"></span>
									  </div>
                  </div>
                  
                </div>
                <div class="form-group">
                  <form:input path="name" type="text" class="form-control form-control-user" placeholder="이름"></form:input>
                </div>
                <div class="form-group">
                  <form:input path="phone" type="text" class="form-control form-control-user" placeholder="전화번호"></form:input>
                </div>
                <div class="form-group">
                  <form:input path="nickname" type="text" class="form-control form-control-user" placeholder="닉네임"></form:input>
                </div>
							  <div class="form-group">
							    <form:select path="gender" class="form-control">
							      <option value="W">여자</option>
							      <option value="M">남자</option>
							    </form:select>
							  </div>
							  
							   <div class="form-group">
                  <form:input path="birth" type="date" class="form-control form-control-user" ></form:input>
                </div>
							  
  
                <button type="submit" class="btn btn-primary btn-user btn-block" id="joinBtn">
                 	회원가입
                </button>
              </form:form>
              
              <hr>
              
              <div class="text-center">
                <a class="small" href="/login/loginForm">이미 가입했습니다. 로그인!</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>

</body>

<jsp:include page="/main_footer.jsp" flush="false"/>    