<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/main_header.jsp" flush="false" />
<script>
	$(function(){
		/* $("#write").on("click",function(e){
			
			var user ="${user.id == null ? null : user.id}";
			if(user == null || user == ""){
				location.href="/login/loginForm";
				return;
			}
			
			e.preventDefault();
			location.href="${pageContext.request.contextPath }/board/write";
		}) */
		
		// 검색 버튼 클릭
		/* $("#btn1").on("click",function(e){
			if($("#keyword").val().trim().length == 0){
				alert("검색어를 입력하세요.");
				return;
			}
			$("form").submit();
			
		}) */
		
	});
</script>

<div class="container">
	<div class="table-responsive">
		<h1 class="text-center p-3 mb-3 bg-white mb-4 mt-4">내가 작성한 게시글</h1>
		<table class="table table-sm" summary="회원 목록">
			<!-- <caption><b>게시글 목록</b></caption> -->
			<colgroup>
				<col width="15%">
				<col width="25%">
				<col width="25%">
				<col width="25%">
				<col width="10%">
			</colgroup>
			<thead>
				<th scope="col">글번호</th>
				<th scope="col">제목</th>
				<th scope="col">작성자</th>
				<th scope="col">생성일</th>
				<th scope="col">조회수</th>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="boardDTO">
					<tr>
						<td>${boardDTO.bnum }</td>
						<td><a class="text-decoration-none text-reset"
							href="/board/view?bnum=${boardDTO.bnum }
												&${pc.makeSearchURL(pc.recordCriteria.reqPage)}">${boardDTO.btitle }</a>
						</td>
						<td>${boardDTO.bnickname }</td>
						<td>${boardDTO.bcdate }</td>
						<td>${boardDTO.bhit }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

<nav aria-label="Page navigation example">
	<ul class="pagination justify-content-center  pagination-sm">
		<c:if test="${pc.prev }">
			<li class="page-item"><a class="page-link"
				href="/board/list?${pc.makeSearchURL(1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
					<span aria-hidden="true">&laquo;</span>
			</a></li>
			<li class="page-item"><a class="page-link"
				href="/board/list?${pc.makeSearchURL(pc.startPage-1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
					<span aria-hidden="true">&lt;</span>
			</a></li>
		</c:if>

		<c:forEach begin="${pc.startPage }" end="${pc.endPage }" var="pageNum">

			<!-- 요청페이지와 현재페이지가 다르면  -->
			<c:if test="${pc.recordCriteria.reqPage != pageNum }">
				<li class="page-item"><a class="page-link"
					href="/board/list?${pc.makeSearchURL(pageNum) }&searchType=${pc.searchType}&keyword=${pc.keyword}">${pageNum }</a></li>
			</c:if>

			<!-- 요청페이지와 현재페이지가 같으면 강조표시  -->
			<c:if test="${pc.recordCriteria.reqPage == pageNum }">
				<li class="page-item active"><a class="page-link"
					href="/board/list?${pc.makeSearchURL(pageNum) }&searchType=${pc.searchType}&keyword=${pc.keyword}">${pageNum }</a></li>
			</c:if>
			<!--------------------------------------------------------------------------->
		</c:forEach>

		<c:if test="${pc.next }">
			<li class="page-item"><a class="page-link"
				href="/board/list?${pc.makeSearchURL(pc.endPage+1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
					<span aria-hidden="true">&gt;</span>
			</a></li>
			<li class="page-item"><a class="page-link"
				href="/board/list?${pc.makeSearchURL(pc.finalEndPage) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
					<span aria-hidden="true">&raquo;</span>
			</a></li>
		</c:if>
	</ul>
</nav>

</div>


<jsp:include page="/main_footer.jsp" flush="false" />
