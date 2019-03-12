<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/main_header.jsp" flush="false" />


<style>
body > .container {
	width:700px !important;
}
.card-body > .row > div {
	max-width: 100%;
	width: 100%;
	flex:none;
}
</style>
<script>
	$(function(){
		// 수정버튼
		$("#modifyBtn").on("click", function(e) {
			e.preventDefault();
			if(confirm("정보를 변경하시겠습니까?")){
				$("form").submit();
			}
		});
	});
</script>
<body class="bg-gradient-primary">
<!--1) SPRING_SECURITY_CONTEXT 내장객체 -->
<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }"/>


  <div class="container">

    <div class="card o-hidden border-0 my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-7">
            <div class="p-5">
              <div class="text-center">
                <h1 class="text-gray-900 mb-4">내 정보</h1>
              </div>
              
              <form:form id="formData" class="user" modelAttribute="mdto" action="${pageContext.request.contextPath }/member/memberModify">
                <div class="form-group">
                  <form:input path="id" type="email" class="form-control form-control-user" readonly="true"/>
                </div>
                <div class="form-group">
                  <form:input path="pw" type="password" class="form-control form-control-user" readonly="true"/>
                </div>

                <div class="form-group">
                  <form:input path="name" type="text" class="form-control form-control-user" ></form:input>
                </div>
                <div class="form-group">
                  <form:input path="phone" type="text" class="form-control form-control-user" ></form:input>
                </div>
                <div class="form-group">
                  <form:input path="nickname" type="text" class="form-control form-control-user"></form:input>
                </div>
							  <div class="form-group">
							    <form:select class="form-control" path="gender">
							      <form:options path="gender" items="${gender }" itemLabel="label" itemValue="code" />
							    </form:select>
							  </div>
										  
							   <div class="form-group">
                  <form:input path="birth" type="date" class="form-control form-control-user"></form:input>
                </div>
							  <div class="form-label-group">
							  	<span class="errmsg">${failed }</span>
							  </div>

                <button type="submit" class="btn btn-primary btn-user btn-block" id="modifyBtn">
                 	수정
                </button>
              </form:form>
            	  <hr>
              
              <div class="text-center">
                <a class="small" href="/member/deleteForm/${user.id }">회원탈퇴</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>

</body>


<jsp:include page="/main_footer.jsp" flush="false" />