<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<jsp:include page="/main_header.jsp" flush="false" />
<script>
   $(function(){
   	
	   	//유효성체크 오류시 에러표시 css적용 
	   	$("span[id$='.errors']").each(function(idx){
	   		if($(this).text().length > 0){
	   			 $(this).prev().removeClass("").addClass("is-invalid");
	   			 $(this).removeClass("valid-feedback").addClass("invalid-feedback");
	   		}
	   	});	
	   	
	   	// 목록으로 이동
	   	$("#btn1").on("click",function(e){
	   		e.preventDefault();
	   		location.href="/board/list";
	   	});
	   	
	   	// 게시글 등록
	   	$("#btn2").on("click",function(e){
	   		e.preventDefault();
	   		// 유효성 체크
	   		if(valChk()){
	   		// form태그의 action="/board/write"
	   			$("form").submit();
	   		}
	   	});
	   	
	   	// 게시글 취소
	   	$("#btn3").on("click",function(e){
	   		e.preventDefault();
	   		$("form").each(function(){
	   			this.reset();
	   		})
	   	});
   });
   
   function valChk(){
   
   	/*제목입력값이 없을 경우*/
      if($("#btitle").val().length == 0){
        alert('제목을 입력하세요.');
        $("#btitle").focus();
        return false;
      }
   
      /*제목입력값길이 체크*/
      if($("#btitle").val().length > 30){
        alert('제목은 30자이내로 입력하세요.');
        $("#btitle").focus();
        return false;
      }
   
      /*내용입력값이 없을 경우*/
      if($("#bcontent").val().length == 0){
        alert('내용을 입력하세요.');
        bcontent.focus();
        return false;
      }
   
      /*내용입력값길이 체크*/
    	if($("#bcontent").val().length > 100){
        alert('내용은 100자이내로 입력하세요.');
        $("#bcontent").focus();
        return false;
      }
      return true;
   };
</script>
<div class="container">
<sec:authorize var="admin" access="hasRole('ROLE_ADMIN')"/>
<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }" />
   <div class="table-responsive">
      <h1 class="text-center p-3 mb-3 bg-white mt-4">게시글 작성</h1>
      <form:form modelAttribute="boardDTO" action="/board/writeOk" method="post" enctype="multipart/form-data">
         <table class="table table-sm" summary="게시글 작성">
            <colgroup>
               <col width="20%">
               <col width="">
            </colgroup>
            <tbody>
               <tr>
                  <th>제목</th>
                  <td>
                     <form:input class="form-control " type="text" placeholder="제목을 입력하세요." path="btitle"/>
                     <form:errors path="btitle" class="valid-feedback"></form:errors>
                  </td>
               </tr>
               <tr>
                  <th>작성자</th>
                  <td>${user.nickname }(${user.id })
                  </td>
               </tr>
               <tr>
                  <th>내용</th>
                  <td>
                     <form:textarea class="form-control " path="bcontent" rows="15" cols="30" placeholder="본문내용을 입력하세요."/>
                     <form:errors path="bcontent" class="valid-feedback"></form:errors>
                  </td>
               </tr>
               <tr>
	               	<td>
				            <input id="fileupload" type="file" name="file" id="file" multiple="">
	               	</td>
                  <td colspan="2" align="right">
                     <button id="btn1" type="button" class="btn btn-outline-secondary btn-sm">목록</button>
                     <button id="btn2" type="button" class="btn btn-outline-secondary btn-sm">등록</button>
                     <button id="btn3" type="button" class="btn btn-outline-secondary btn-sm">취소</button>
                  </td>
               </tr>
               <tr>
               </tr>
            </tbody>
            
         </table>
      </form:form>
   </div>
</div>
<jsp:include page="/main_footer.jsp" flush="false" />