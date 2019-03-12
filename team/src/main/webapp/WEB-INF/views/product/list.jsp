<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<jsp:include page="/main_header.jsp" flush="false" />

<style>
.card-body>.row {
	max-width: 100%;
	width: 100%;
	flex: none;
}

#product_list {
	list-style: none;
	padding: 0px;
	margin; 0px;
}

#product_list > li > #item {
    height: 150px;
    margin: 15px 0px;
}
#product_list > li > #item > #thumbnail {
	float:left;
	margin-right: 20px;
}
#product_list > li > #item > #thumbnail > img {
    width: 150px;
    height: 150px;
}
#product_list > li > #item > #descriptions {
	padding-top: 14px;
}
#product_list > li > #item > #descriptions > #brand {
    font-size: 14px;
    color: #dc1f1f;
	margin-bottom: 5px;
}
#product_list > li > #item > #descriptions > #title {
	font-size: 19px;
	font-weight: bold;
    margin-bottom: 10px;
}
#product_list > li > #item > #descriptions > #allergy {
	font-size: 15px;
	font-weight: bold;
	margin-bottom: 5px;
    color: #2a70ca;
}
#product_list > li > #item > #descriptions > #description {
	font-size: 15px;
	font-weight: bold;
	margin-bottom: 5px;
    color: #903030;
}
.focus {
    border-color: #b10909;
    color: #8e2626;
    background-color: #e2e2e2;
}
#product_list > li > #item , #product_list > li > #item > #thumbnail{
	border-bottom:1px solid #eee
}
.py-2{
    margin: 8px 1px;
    padding: 11px;
    border: none;
    border-radius: 5px;
    background-color: #d8d8d8;
}
</style>
<sec:authorize var="admin" access="hasRole('ROLE_ADMIN')"/>
<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }" />
<body>
	<div class="container" style="margin-top: 30px;">
		<!-- Content Row -->
		<div class="row">

			<!-- Content Column -->
			<div class="col-lg-4 mb-4">
				<c:if test="${admin eq true }">
					<button style="margin-bottom:10px;width: 100%;" id="write" type="button" class="btn btn-secondary btn-sm">글쓰기</button>
				</c:if>
				<!-- Project Card Example -->
				<div class="root card mb-4" style="border:none">

					<div class="card-header py-2">
						<h6 class="m-0 text-dark">판매처</h6>
					</div>

					<div style="display: block;width: 100%;">
						<c:forEach items="${store}" var="productDTO">
						<div style="width: 50%;padding: 3px;float: left;" class="col-sm-7 ">
							<div class="card bg-light" style="border: none;margin: 1px !important;background-color: #f1f1f1  !important;">
								<div data-type="sold" data-id="${productDTO}" class="fnd-option card-body p-2">${productDTO}</div>
							</div>
						</div>
						</c:forEach>
					</div>
				</div>
				
				<!-- Project Card Example -->
				<div class="root card mb-4"  style="border:none">
					<div class="card-header py-2">
						<h6 class="m-0 text-dark">식품종류</h6>
					</div>

					<div style="display: block;width: 100%;">
						<c:forEach items="${product}" var="productDTO">
						<div style="width: 50%;padding: 3px;float: left;" class="col-sm-7 ">
							<div class="card bg-light" style="border: none;margin: 1px !important;background-color: #f1f1f1  !important;">
								<div data-type="type" data-id="${productDTO}" class="fnd-option card-body p-2">${productDTO}</div>
							</div>
						</div>
						</c:forEach>
					</div>
				</div>
				
				<form id="option-find">
					<input type="hidden" name="sold"></input>
					<input type="hidden" name="type"></input>
					<input style="width: 100%;background-color: #ff7575;border: none;padding: 3px 5px;border-radius: 5px;color: #fff;" type="submit" value="검색"></input>
				</form>
			</div>

			<div class="col-lg-8 mb-4">
				<div class="list">
					<ul id="product_list">
						<li>
							<c:forEach items="${list}" var="productDTO">
								<div id="item">
									<div id="thumbnail">
										<img src="${productDTO.getImage()}"/>
									</div>
									<div id="descriptions">
										<div id="brand">
											${productDTO.pstore}
										</div>
										<div id="title">
											<a href="${productDTO.getLink()}">
												${productDTO.getTitle()}
											</a>
										</div>
										<div id="allergy">
											${productDTO.getAllergy()}
										</div>
										<div id="description">
											${productDTO.price}원
										</div>
									</div>
								</div>
							</c:forEach>
						</li>
					</ul>
				</div>
			
		      <nav aria-label="Page navigation example">
		         <ul class="pagination justify-content-center  pagination-sm">
		            <c:if test="${pc.prev }">
		               <li class="page-item">
		                  <a class="page-link" href="/product/list?${pc.makeSearchURL(1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
		                  <span aria-hidden="true">&laquo;</span>
		                  </a>
		               </li>
		               <li class="page-item">
		                  <a class="page-link" href="/product/list?${pc.makeSearchURL(pc.startPage-1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
		                  <span aria-hidden="true">&lt;</span>
		                  </a>
		               </li>
		            </c:if>
		            <c:forEach begin="${pc.startPage }" end="${pc.endPage }" var="pageNum">
		               <!-- 요청페이지와 현재페이지가 다르면  -->
		               <c:if test="${pc.recordCriteria.reqPage != pageNum }">
		                  <li class="page-item"><a class="page-link" href="/product/list?${pc.makeSearchURL(pageNum) }&searchType=${pc.searchType}&keyword=${pc.keyword}">${pageNum }</a></li>
		               </c:if>
		               <!-- 요청페이지와 현재페이지가 같으면 강조표시  -->
		               <c:if test="${pc.recordCriteria.reqPage == pageNum }">
		                  <li class="page-item active"><a class="page-link" href="/product/list?${pc.makeSearchURL(pageNum) }&searchType=${pc.searchType}&keyword=${pc.keyword}">${pageNum }</a></li>
		               </c:if>
		               <!--------------------------------------------------------------------------->
		            </c:forEach>
		            <c:if test="${pc.next }">
		               <li class="page-item">
		                  <a class="page-link" href="/product/list?${pc.makeSearchURL(pc.endPage+1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
		                  <span aria-hidden="true">&gt;</span>
		                  </a>
		               </li>
		               <li class="page-item">
		                  <a class="page-link" href="/product/list?${pc.makeSearchURL(pc.finalEndPage) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
		                  <span aria-hidden="true">&raquo;</span>
		                  </a>
		               </li>
		            </c:if>
		         </ul>
		      </nav>
		       <!-- 검색 조건 -->
		      <div class="row justify-content-center mb-3">
		         <form action="/product/list" method="GET">
		            <div class="row">
		               <select id="key1" name="searchType" class="col-sm-3 custom-select custom-select-sm px-1 mr-1">
		                  <option value="ptitle"
		                  <c:out value="${pc.searchType == 'TC' ? 'selected' : '' }"/>
		                  >제품명</option>
		                  <option value="pcontent"
		                  <c:out value="${pc.searchType == 'T' ? 'selected' : '' }"/>
		                  >내용</option>
		               </select>
		               <input class="col-sm-5 form-control keyset form-control-sm px-1 mr-1" type="search"
		                  name="keyword" id="keyword" placeholder="검색어를 입력하세요"
		                  value="${pc.keyword }">
		               <button id='btn1' type="button" class="btn btn-sm btn-outline-secondary px-2 mx-0">검색</button>
		            </div>
		         </form>
		      </div>
			</div>
		</div>
	</div>
	
	<script>
	$(function(){
		$(document).ready(function(){
			var url_string = window.location.href
			var url = new URL(url_string);
			var c = url.searchParams.get("sold");
			var d = url.searchParams.get("type");
			$(".row").find("div[data-type=\"sold\"][data-id=\"" + c + "\"]").addClass("focus");
			$("#option-find").find("input[name=\"sold\"]").val(c);
			$(".row").find("div[data-type=\"type\"][data-id=\"" + d + "\"]").addClass("focus");
			$("#option-find").find("input[name=\"type\"]").val(d);
		});
		
		$(".fnd-option").click(function(self) {
			let datatype = $(this).attr("data-type");
			let dataid = $(this).attr("data-id");

			let isFocused = $(this).hasClass("focus");
			
			$(this).closest(".root").find(".fnd-option").each(function(idx, dat){
				$(dat).removeClass("focus");
			});
			
			if (isFocused) {
				$(this).removeClass("focus");
				$("#option-find").find("input[name=\"" + datatype + "\"]").val('');
			} else {
				$(this).addClass("focus");
				$("#option-find").find("input[name=\"" + datatype + "\"]").val(dataid);
			}
		});
		
		$("#write").on("click",function(e){
			e.preventDefault();
			location.href="${pageContext.request.contextPath }/product/writeForm";
		})
		
		// 검색 버튼 클릭
		$("#btn1").on("click",function(e){
			if($("#keyword").val().trim().length == 0){
				alert("검색어를 입력하세요.");
				return;
			}
			$("form").submit();
			
		})
		
	});
	
</script>
	
</body>

<jsp:include page="/main_footer.jsp" flush="false" />