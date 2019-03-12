<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/main_header.jsp" flush="false" />

<div class="container">
	<div class="table-responsive">
	<h1 class="text-center p-3 mb-3 bg-white  mb-4 mt-4">회원 목록</h1>
		<table class="table table-sm" summary="회원 목록">
			<!-- <caption><b>게시글 목록</b></caption> -->
			<colgroup>
				<col width="15%">
				<col width="15%">
				<col width="25%">
				<col width="25%">
				<col width="10%">
				<col width="10%">
			</colgroup>
			<thead>
				<th scope="col">아이디</th>
				<th scope="col">닉네임</th>
				<th scope="col">생성일</th>
				<th scope="col">수정일</th>
				<th scope="col">회원정지</th>
				<th scope="col">활동</th>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="memberDTO" varStatus="fuck">
				<tr>
					<td>${memberDTO.id }</td>
					<td>${memberDTO.nickname }</td>
					<td>${memberDTO.cdate }</td>
					<td>${memberDTO.udate }</td>
					<td>${memberDTO.denied }</td>
					<td>
						<select name="denied" class="denied">
				      <option <c:if test="${memberDTO.denied == 'N'}"> selected </c:if> value="N">활동</option>
				      <option <c:if test="${memberDTO.denied == 'Y'}"> selected </c:if> value="Y">활동정지</option>
						</select>
					</td>		
				</tr>
				</c:forEach>
				<tr>
					<!-- <td colspan="5" align="right">
						<button id="write" type="button" class="btn btn-outline-primary btn-sm">글쓰기</button>
					</td> -->
				</tr>
			</tbody>
		</table>
	</div>
	
	<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center  pagination-sm">
			<c:if test="${pc.prev }">
			<li class="page-item">
				<a class="page-link" href="/member/memberList?${pc.makeSearchURL(1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>
			<li class="page-item">
				<a class="page-link" href="/member/memberList?${pc.makeSearchURL(pc.startPage-1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
					<span aria-hidden="true">&lt;</span>
				</a>
			</li>
			</c:if>
			
			 <c:forEach begin="${pc.startPage }" end="${pc.endPage }" var="pageNum">
      
       <!-- 요청페이지와 현재페이지가 다르면  -->
      <c:if test="${pc.recordCriteria.reqPage != pageNum }">
      <li class="page-item"><a class="page-link" href="/member/memberList?${pc.makeSearchURL(pageNum) }&searchType=${pc.searchType}&keyword=${pc.keyword}">${pageNum }</a></li>
      </c:if>
      
      <!-- 요청페이지와 현재페이지가 같으면 강조표시  -->
				<c:if test="${pc.recordCriteria.reqPage == pageNum }">
	     	 <li class="page-item active"><a class="page-link" href="/member/memberList?${pc.makeSearchURL(pageNum) }&searchType=${pc.searchType}&keyword=${pc.keyword}">${pageNum }</a></li>
	      </c:if>
	    <!--------------------------------------------------------------------------->
      </c:forEach>
			
			<c:if test="${pc.next }">
			<li class="page-item">
				<a class="page-link" href="/member/memberList?${pc.makeSearchURL(pc.endPage+1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
					<span aria-hidden="true">&gt;</span>
				</a>
			</li>
			<li class="page-item">
				<a class="page-link" href="/member/memberList?${pc.makeSearchURL(pc.finalEndPage) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
			</c:if>
		</ul>
	</nav>
	
	<!-- 검색 조건 -->
   <div class="row justify-content-center mb-3">
     <form action="/member/memberList" method="post">
     	<div class="row">
       <label class="col-sm-2 col-form-label col-form-label-sm px-0 mx-0" for="key1">검색어</label>
       <select class="col-sm-3 custom-select custom-select-sm px-1 mr-1" name="searchType" id="key1">
         <option value="I"
         	<c:out value="${pc.searchType == 'I' ? 'selected' : ''} "/>>아이디</option>
         <option value="N"
         	<c:out value="${pc.searchType == 'N' ? 'selected' : ''} "/>>닉네임</option>
       </select>
       <input class="col-sm-5 form-control form-control-sm px-1 mr-1" type="search" 
       name="keyword" id="keyword" placeholder="검색어를 입력하세요" value="${pc.keyword}">
       <button id="btn1" type="button" class="btn btn-outline-primary btn-sm px-2 mx-0">검색</button>
     </div>
     </form>
   </div>
</div>

<script>
	$(function(){
		$(document).ready(function() {
			// 활동정지 버튼 클릭
			$(".denied").change(function(){
				console.log("활동정지");
				console.log( $("#denied").val());
				
				var denied = $(this).val();
				
				let memberId = $(this).closest("tr").find("td").eq(0)[0].outerText;
				
				$.ajax({
					type : "PUT",
					headers: {
						"X-HTTP-Method-Override": "PUT"
					},
					url : "/member/memberDenied/" + denied + "/" + encodeURIComponent(memberId),
					dataType : "text",
					data: "id=" + memberId,
					success : function(){
						console.log("활동정지성공");
						denied = denied;
						location.reload();
					},
					error : function(){
						//console.log("xhr"+xhr);
						console.log("status"+status);
						console.log("err"+err);
					}
					
				});
				
				
			});
			
			// 검색 버튼 클릭
			$("#btn1").on("click",function(e){
				if($("#keyword").val().trim().length == 0){
					alert("검색어를 입력하세요.");
					return;
				}
				$("form").submit();
				
			})
		});
	});
</script>

<jsp:include page="/main_footer.jsp" flush="false" />
