<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/main_header.jsp" flush="false" />

<script>
    $(function() {

        // 검색 버튼 클릭
        $("#btn1").on("click", function(e) {
            if ($("#keyword").val().trim().length == 0) {
                alert("검색어를 입력하세요.");
                return;
            }
            $("form").submit();

        })
        
        // 전체글 버튼 클릭
        $("#best").on("click", function(e) {
        	e.preventDefault();
        	location.href="/board/list?best=1";
        })
        
        // 전체글 버튼 클릭
        $("#allpage").on("click", function(e) {
        	e.preventDefault();
        	location.href="/board/list";
        })
        
        // 글쓰기 버튼 클릭
        $("#write").on("click", function(e) {
        	e.preventDefault();
        	location.href="/board/writeForm";
        })

    });
</script>

<style>
    td,
    th {
        border: none !important;
    }
</style>

<body>
    <div class="container" style="margin-top: 30px;">
         <h1 class="text-center p-3 mb-3 bg-white mt-4">리뷰 게시판</h1>
        <!-- DataTales Example -->
        <div class="card  mb-4 mt-4 border-0">
            <div class="card-body text-right border-0 bg-white mr-2 mb-0">
                <button id="best" type="button" class="btn btn-outline-secondary btn-sm">베스트</button>
                <button id="allpage" type="button" class="btn btn-outline-secondary btn-sm">전체글</button>
                <!-- <button id="best" type="button" class="btn btn-outline-secondary btn-sm">인기글</button> -->
                <button id="write" type="button" class="btn btn-outline-secondary btn-sm">글쓰기</button>
            </div>
            <div class="card-body ">
                <div class="table-responsive">
                    <table class="table " id="dataTable" width="100%" cellspacing="0">
                        <thead>
                        </thead>
                        <tbody>
                            <c:forEach items="${list }" var="boardDTO">
                                <tr>
                                    <div style="width: 32%;float: left;margin: 7px 7px;">
                                        <div class="thumbnail_wrapper">
                                            <a class="thumbnail" href="http://bbs.ruliweb.com/hobby/board/300117/read/30602649?"></a>
                                        </div>
                                        <div class="card mb-4 -sm">
                                            <svg style="background-image:url('${boardDTO.getImage()}');background-size:100%;" class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img">
                                                <title>Placeholder</title>
                                                <rect fill="none" width="100%" height="100%" />
                                                <text dy=".3em" x="50%" y="50%"></text>
                                            </svg>
                                            <div class="card-body">
                                                <div class="onthumbnail_icons text-right">
                                                    <span class="thumbnail_bhit"><i class="far fa-eye"></i>${boardDTO.bhit}</span>
                                                    <span class="thumbnail_bgood"><i class="fas fa-thumbs-up"></i>${boardDTO.bgood}</span>
                                                </div>
                                                <p class="card-text"><a href="${boardDTO.getLink(pc)}">${boardDTO.btitle}</a>
                                                </p>
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <small class="small_writer"><a href="">${boardDTO.bnickname}</a></small>
                                                    <small class="small_cdate">${boardDTO.bcdate}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </tr>
                            </c:forEach>

                            <tr>
                                <td>${boardDTO.bnum }</td>
                                <td>
                                    <a class="text-decoration-none text-reset" href="/board/view?bnum=${boardDTO.getLink() }
            	&${pc.makeSearchURL(pc.recordCriteria.reqPage)}">${boardDTO.btitle }</a>
                                </td>
                                <td>${boardDTO.bnickname }</td>
                                <td>${boardDTO.bcdate }</td>
                                <td>${boardDTO.bhit }</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
    <nav aria-label="Page navigation example">
		         <ul class="pagination justify-content-center  pagination-sm">
		            <c:if test="${pc.prev }">
		               <li class="page-item">
		                  <a class="page-link" href="/board/list?${pc.makeSearchURL(1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
		                  <span aria-hidden="true">&laquo;</span>
		                  </a>
		               </li>
		               <li class="page-item">
		                  <a class="page-link" href="/board/list?${pc.makeSearchURL(pc.startPage-1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
		                  <span aria-hidden="true">&lt;</span>
		                  </a>
		               </li>
		            </c:if>
		            <c:forEach begin="${pc.startPage }" end="${pc.endPage }" var="pageNum">
		               <!-- 요청페이지와 현재페이지가 다르면  -->
		               <c:if test="${pc.recordCriteria.reqPage != pageNum }">
		                  <li class="page-item"><a class="page-link" href="/board/list?${pc.makeSearchURL(pageNum) }&searchType=${pc.searchType}&keyword=${pc.keyword}">${pageNum }</a></li>
		               </c:if>
		               <!-- 요청페이지와 현재페이지가 같으면 강조표시  -->
		               <c:if test="${pc.recordCriteria.reqPage == pageNum }">
		                  <li class="page-item active"><a class="page-link" href="/board/list?${pc.makeSearchURL(pageNum) }&searchType=${pc.searchType}&keyword=${pc.keyword}">${pageNum }</a></li>
		               </c:if>
		               <!--------------------------------------------------------------------------->
		            </c:forEach>
		            <c:if test="${pc.next }">
		               <li class="page-item">
		                  <a class="page-link" href="/board/list?${pc.makeSearchURL(pc.endPage+1) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
		                  <span aria-hidden="true">&gt;</span>
		                  </a>
		               </li>
		               <li class="page-item">
		                  <a class="page-link" href="/board/list?${pc.makeSearchURL(pc.finalEndPage) }&searchType=${pc.searchType}&keyword=${pc.keyword}">
		                  <span aria-hidden="true">&raquo;</span>
		                  </a>
		               </li>
		            </c:if>
		         </ul>
		      </nav>
		      <!-- 검색 조건 -->
		      <div class="row justify-content-center mb-3">
		         <form action="/board/list" method="post">
		            <div class="row">
		               <label for="key1" class="col-sm-2 col-form-label col-form-label-sm px-0 mx-0">검색어</label>
		               <select name="searchType" id="key1" class="col-sm-3 custom-select custom-select-sm px-1 mr-1">
		                  <option value="TC"
		                  <c:out value="${pc.searchType == 'TC' ? 'selected' : '' }"/>
		                  >제목+내용</option>
		                  <option value="T"
		                  <c:out value="${pc.searchType == 'T' ? 'selected' : '' }"/>
		                  >제목</option>
		                  <option value="C"
		                  <c:out value="${pc.searchType == 'C' ? 'selected' : '' }"/>
		                  >내용</option>
		                  <option value="W"
		                  <c:out value="${pc.searchType == 'N' ? 'selected' : '' }"/>
		                  >작성자</option>
		                  <option value="I"
		                  <c:out value="${pc.searchType == 'I' ? 'selected' : '' }"/>
		                  >아이디</option>
		               </select>
		               <input class="col-sm-5 form-control form-control-sm px-1 mr-1" type="search"
		                  name="keyword" id="keyword" placeholder="검색어를 입력하세요"
		                  value="${pc.keyword }">
		               <button id='btn1' type="button" class="btn btn-sm btn-secondary px-2 mx-0">검색</button>
		            </div>
		         </form>
		      </div>
        
    </div>
</body>

<jsp:include page="/main_footer.jsp" flush="false" />