<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/main_header.jsp" flush="false"/>


<style>
body > .container {
	width:500px !important;
}
.card-body > .row > div {
	max-width: 100%;
	width: 100%;
	flex:none;
}
</style>
<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }" />
<script>
var userPw = '${user.pw}';
	$(function(){
		$("#delBtn").on("click",function(e){
			console.log(userPw);
			e.preventDefault();
			if(pwChk()){
				if(userPw == $("#pw").val()){
					if(confirm("정말 탈퇴하시겠습니까?")){
						$("form").submit();
					}
				}else{
					alert("비밀번호가 일치하지않습니다!");
				}
			}
		});
		
	});
	
	function pwChk(){
		// 비밀번호 입력 체크
		if($("#pw").val().length == 0){
			alert("비밀빈호를 입력하세요");
			$("#pw").focus();
			return false;
		}
		return true;
	}
</script>
  <div class="container bg-gradient-primary">

    <!-- Outer Row -->
    <div class="row justify-content-center">

      <div class="col-xl-10 col-lg-12 col-md-9">

        <div class="card o-hidden border-0 my-5">
        
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="text-gray-900 mb-4">회원탈퇴</h1>
                  </div>
                  <form:form modelAttribute="mdto" action="/member/memberDelete" class="user">
                    
                    <div class="form-group">
                      <form:input path="id" type="text" class="form-control form-control-user" readonly="true" ></form:input>
                    </div>
                    <div class="form-group">
                      <form:input path="pw" type="password" class="form-control form-control-user" placeholder="비밀번호"></form:input>
                    </div>
                    
                    
										<button type="submit" class="btn btn-primary btn-user btn-block" id="delBtn">
											확인
										</button>
                  </form:form>
                </div>
              </div>
            </div>
          </div>
        </div>

      </div>

    </div>

  </div>



<jsp:include page="/main_footer.jsp" flush="false"/>