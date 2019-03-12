<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<jsp:include page="/main_header.jsp" flush="false" />

<link rel="stylesheet" href="/resources/bootstrap-4.2.1/dist/css/bootstrap.css" />

<script>
$(function(){
	
	
	// 목록으로 이동
	$("#btn1").on("click",function(e){
		e.preventDefault();
		location.href="/product/list";
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
   if($("#ptitle").val().length == 0){
     alert('제목을 입력하세요.');
     $("#ptitle").focus();
     return false;
   }

   /*제목입력값길이 체크*/
   if($("#ptitle").val().length > 30){
     alert('제목은 30자이내로 입력하세요.');
     $("#ptitle").focus();
     return false;
   }

   /*내용입력값이 없을 경우*/
   if($("#pcontent").val().length == 0){
     alert('내용을 입력하세요.');
     pcontent.focus();
     return false;
   }

   /*내용입력값길이 체크*/
 	if($("#pcontent").val().length > 100){
     alert('내용은 100자이내로 입력하세요.');
     $("#pcontent").focus();
     return false;
   }
   return true;
};
</script>

<div class="container">
<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }" />
	<div class="table-responsive">
		<h1 class="text-center p-3 mb-3 bg-white mt-4">제품 정보 작성</h1>
		<form:form id="productForm" modelAttribute="ProductDTO" action="/product/writeOk" method="post" enctype="multipart/form-data">
			<table class="table table-sm" summary="게시글 작성">
				<colgroup>
					<col width="20%">
					<col width="">
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<form:input class="form-control " type="text" placeholder="제목을 입력하세요." path="ptitle"></form:input>
							<form:errors path="ptitle" class="valid-feedback"></form:errors>
						</td>
					</tr>
					
					<tr>
						<th>가격</th>
						<td>
							<form:input class="form-control " type="number" min="0" max="100000" path="price"></form:input>
							<form:errors path="price" class="valid-feedback"></form:errors>
						</td>
					</tr>
					
					<tr>
						<th>제품군</th>
						<td>
							<form:select path="pgroup" class="form-control " name="pgroup">
								<c:forEach items="${product}" var="productDTO">
							  <option value="${productDTO}">${productDTO}</option>
							  </c:forEach>
							</form:select>
							<form:errors path="pgroup" class="valid-feedback"></form:errors>
						</td>
					</tr>
					
					<tr>
						<th>판매처</th>
						<td>
							<form:select path="pstore" class="form-control " name="pstore">
								<c:forEach items="${store}" var="productDTO">
							  <option value="${productDTO}">${productDTO}</option>
							  </c:forEach>
							</form:select>
							<form:errors path="pstore" class="valid-feedback"></form:errors>
						</td>
					</tr>
					
					<tr>
						<th>알러지</th>
						<td>
							<form:input class="form-control " type="text" placeholder="알러지 정보" path="allergy"></form:input>
							<form:errors path="allergy" class="valid-feedback"></form:errors>
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
							<form:textarea class="form-control " path="pcontent" rows="15" cols="30" placeholder="본문내용을 입력하세요."></form:textarea>
							
							<form:errors path="pcontent" class="valid-feedback"></form:errors>
						</td>
					</tr>
					
					<tr>
						<th>썸네일 <input onchange="readURL(this);" id="fileupload" type="file" name="file" id="file" ></th>
						<td>
							<img src="" id="blah" src="#" alt="your image" />
						</td>
					</tr>
					
					<tr>
						<td colspan="2" align="right">
						
							<button id="btn1" type="button" class="btn btn-outline-secondary btn-sm">목록</button>
							<button id="btn2" type="button" class="btn btn-outline-secondary btn-sm">등록</button>
							<button id="btn3" type="button" class="btn btn-outline-secondary btn-sm">취소</button>
						</td>
					</tr>
					
					<div class="editOpt"   style="display:none">
						<input class="removeAllFiles" type="button" name="upload" value="전체 삭제">	
						<input class="insertAllImageToContent" type="button" name="upload" value="전체 삽입">	
						<input class="unsetSelectedItem" type="button" name="upload" value="전체 선택 해제">	
						<input class="insertImageToContent" type="button" name="upload" value="본문 삽입">
					</div>		
				</tbody>
			</table>
		</form:form>
	</div>
</div>

<script>

function readURL(input) {
	  if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    reader.onload = function (e) {
	      $('#blah')
	        .attr('src', e.target.result)
	        .width(150)
	        .height(200);
	    };
	    reader.readAsDataURL(input.files[0]);
	  }
	}

</script>

<jsp:include page="/main_footer.jsp" flush="false" />