<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
.errMsg {color:red;}
</style>
<script>
	$(function(){
		$("#loginBtn").on("click",function(e){
		  if($("#id").val().length == 0){
		    alert('아이디를 입력하세요.');
		    $("#id").focus();
		    return false;
		  }	
		  if($("#pw").val().length == 0){
		    alert('비밀번호를 입력하세요.');
		    $("#pw").focus();
		    return false;
		  }
		  
		
		});
		
	
	});	
</script>

<body class="bg-gradient-primary">
	<div class="container">
		<!-- Outer Row -->
		<div class="row justify-content-center">
			<div class="col-xl-10 col-lg-12 col-md-9">
				<div class="card o-hidden border-0  my-5">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="col-lg-6">
								<div class="p-5">
									<div class="text-center">
										<h1 class="text-gray-900 mb-4">어서오세요!</h1>
									</div>
									
									<form:form id="formData" class="user form-login" modelAttribute="login" action="/login">
										<div class="form-group">
											<form:input path="id" type="email" class="form-control form-control-user" placeholder="아이디(Email)"></form:input>
										</div>
										<div class="form-group">
											<form:input path="pw" type="password" class="form-control form-control-user" placeholder="비밀번호"></form:input>
										</div>
										<div class="form-group">
											<div class="custom-control custom-checkbox small">
												<input type="checkbox" class="custom-control-input" id="customCheck" name="remember-me">
												<label class="custom-control-label" for="customCheck">Remember Me</label>
											</div>
										</div>
										<span class="errMsg">${errMsg }</span>
										<button type="submit" class="btn btn-primary btn-user btn-block" id="loginBtn">
											로그인
										</button>
									</form:form>
									<hr>
									
									<div class="text-center">
										<a class="small" href="/login/findIdForm">아이디를 잊어버렸습니까?</a>
									</div>
									<div class="text-center">
										<a class="small" href="/login/findPwForm">비밀번호를 잊어버렸습니까?</a>
									</div>
									<div class="text-center">
										<a class="small" href="/member/joinForm">회원가입!</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>




<jsp:include page="/main_footer.jsp" flush="false"/>