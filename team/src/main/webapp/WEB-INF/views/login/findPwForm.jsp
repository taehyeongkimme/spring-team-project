<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/main_header.jsp" flush="false" />


<style>
	.errmsg {
		color: #f00;
	}
	
	.bd-placeholder-img {
		font-size: 1.125rem;
		text-anchor: middle;
	}
	
	@media ( min-width : 768px) {
		.bd-placeholder-img-lg {
			font-size: 3.5rem;
			}
		.find {
			font-size: 13px;
		}
	}
</style>

<script>
	$(function() {
		//유효성체크 오류시 에러표시 css적용 
		$(".errmsg").each(function(idx) {
			if ($(this).text().length > 0) {
				$(this).prev().removeClass("is-valid").addClass("is-invalid");
			}
		});
		
		$("#findPw").on("click",function(e){
			e.preventDefault();
			$("form").submit();
		})
		
		$("#cancel").on("click",function(e){
			e.preventDefault();
			location.href="/";
		})

	});
</script>

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

<body class="bg-gradient-primary">
	<div class="container">
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
                    <h1 class="text-gray-400 mb-4">비밀번호 조회</h1>
                  </div>
									<form:form class="form-login user" action="/login/findPw" modelAttribute="findPW" onsubmit="return postData(this, event)">
										<div class="form-group">
											<form:input path="id" type="email" class="form-control form-control-user" placeholder="아이디"></form:input>
										</div>
										<div class="form-group">
											<form:input path="phone" type="text" class="form-control form-control-user" placeholder="전화번호"></form:input>
										</div>
										<div class="form-group">
											<form:input path="birth" type="date" class="form-control form-control-user"></form:input>
										</div>
										
										<button type="submit" class="btn btn-primary btn-user btn-block">
											비밀번호 조회
										</button>
									</form:form>
									<hr>
									<div class="text-center">
										<a class="small" href="/member/joinForm">회원가입!</a>
									</div>
									<div class="text-center">
										<a class="small" href="/login/loginForm">로그인!</a>
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


<script>
function postData(data, e) {
	e.preventDefault();
	
	var str = $("form.form-login").serialize();

	$.ajax({
			type:"post",
			data:str,
			url:"/login/findPw",
			async: false,
			dataType: "json",
			success: function(args){
			if (args['success'] === 'true') {
				alert(args['msg']);
				} else if (args['success'] === 'false') {
				alert(args['msg']);
				}
			}
	});
	
	return false;
}
</script>

<jsp:include page="/main_footer.jsp" flush="false" />

