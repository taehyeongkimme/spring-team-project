<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
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

<style>
	 .card {
	 border:none;
	 }
   .card-body>.row {
   max-width: 100%;
   width: 100%;
   flex: none;
   }
   /* body {
   background-color: #eee
   } */
   #contents {
   width: 1080px;
   margin: 0 auto;
   display: grid;
   background-color: #fff;
   }
   #productInfo>.side {
   float: left;
   width: 30%;
   }
   #productInfo>.side>img {
   max-width: 100%;
   }
   #productInfo>.contents {
   float: left;
   width: 68%;
   margin-left: 2%;
   }
   #productInfo>.contents>#category {
   padding: 0px;
   margin: 0px;
   color: #1664c7;
   font-size: 13px;
   }
   #productInfo>.contents>#title {
   padding: 0px;
   margin: 0px;
   margin-top: 10px;
   color: #771212;
   font-size: 33px;
   }
   #productInfo>.contents>#price {
   padding: 0px;
   margin: 0px;
   margin-top: 15px;
   color: #000000;
   font-weight: bold;
   font-size: 12px;
   }
   #seller {
   margin: 10px 0px;
   margin-top: 30px;
   }
   #seller>#part {
   width: 100%;
   display: inline-table;
   margin-bottom: 10px;
   }
   #seller>#part>.header {
   float: left;
   width: 10%;
   font-weight: bold;
   font-size: 13px;
   color: #777777;
   }
   #seller>#part>.contents {
   float: left;
   font-size: 13px;
   color: #444;
   }
   #contents>#productInfo>.contents {
   margin-top: 20px;
   }
</style>
<body>
<sec:authorize var="admin" access="hasRole('ROLE_ADMIN')"/>
   <div style="margin-top: 32px;" class="container">
      <div class="row">
         <div class="col-lg-12 mb-4">
            <!-- Project Card Example -->
            <div class="card mb-4 ">
               <div class="card-header py-3  border-0 bg-white mt-2">
                  <h1 class="m-0 text-dark">제품정보</h1>
               </div>
               <div style="padding: 6px 20px;" class="card-body">
                  <div class="row">
                     <div >
                        <div style="height: 400px;" class="card mb-4">
                           <svg style="width:400px;height: 400px;background:url('${productList.getImage()}');background-size: cover;" class="bd-placeholder-img card-img-top border" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice" focusable="false" role="img" aria-label="Placeholder: Thumbnail">
                           </svg>
                        </div>
                     </div>
                     <div class ="col-lg-7">
                        <div style="padding:1px" class="card-body mt-0">
                           <div class="table-responsive">
                             
                                 <form:form id="modifyForm" style="display:none" modelAttribute="ProductDTO" action="/product/modifyOk" method="post">
                                    <form:hidden path="pnum" value="${productList.pnum}"></form:hidden>
                                    <table class="table table-sm" summary="게시글 작성">
                                       <colgroup>
                                          <col width="20%">
                                          <col width="">
                                       </colgroup>
                                       <tbody>
                                          <tr>
                                             <th>제목</th>
                                             <td>
                                                <form:input class="form-control " type="text" placeholder="제목을 입력하세요." path="ptitle" value="${productList.ptitle}"></form:input>
                                                <form:errors path="ptitle" class="valid-feedback"></form:errors>
                                             </td>
                                          </tr>
                                          <tr>
                                             <th>가격</th>
                                             <td>
                                                <form:input class="form-control " type="number" min="0" max="100000" placeholder="제목을 입력하세요." path="price" value="${productList.price}"></form:input>
                                                <form:errors path="price" class="valid-feedback"></form:errors>
                                             </td>
                                          </tr>
                                          <tr>
                                             <th>제품군</th>
                                             <td>
                                                <form:select path="pgroup" class="form-control " name="pgroup">
                                                   <c:forEach items="${product}" var="productDTO">
                                                      <option 
                                                      <c:if test="${productDTO == productList.pgroup}"> selected </c:if>
                                                      value="${productDTO}">${productDTO}</option>
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
                                                      <option 
                                                      <c:if test="${productDTO == productList.pstore}"> selected </c:if>
                                                      value="${productDTO}">${productDTO}</option>
                                                   </c:forEach>
                                                </form:select>
                                                <form:errors path="pstore" class="valid-feedback"></form:errors>
                                             </td>
                                          </tr>
                                          <tr>
                                             <th>알러지</th>
                                             <td>
                                                <form:input class="form-control " type="text" path="allergy" value="${productList.allergy}"></form:input>
                                                <form:errors path="allergy" class="valid-feedback"></form:errors>
                                             </td>
                                          </tr>
                                          <tr>
                                             <th>내용</th>
                                             <td>
                                                <form:textarea class="form-control " path="pcontent" rows="15" cols="30" value="${productList.pcontent}"/>
                                                <form:errors path="pcontent" class="valid-feedback"></form:errors>
                                             </td>
                                          </tr>
                                          <tr>
                                             <td colspan="2" align="right">
                                                <button type="button" class="btn btn-outline-secondary btn-sm listBtn">목록</button>
                                                <button id="submit" type="submit" class="btn btn-outline-secondary btn-sm">등록</button>
                                                <button id="cancelBtn" type="button" class="btn btn-outline-secondary btn-sm">취소</button>
                                             </td>
                                          </tr>
                                          
                                       </tbody>
                                    </table>
                                 </form:form>
                             
                             
                              <table class="table" id="dataTable">
                                 <colgroup>
                                    <col width="25%">
                                    <col width="75%">
                                 </colgroup>
                                 <tbody>
                                    <tr>
                                       <td>제품명</td>
                                       <td>${productList.ptitle}</td>
                                    </tr>
                                    <tr>
                                       <td>판매처</td>
                                       <td>${productList.pstore}</td>
                                    </tr>
                                    <tr>
                                       <td>식품종류</td>
                                       <td>${productList.pgroup}</td>
                                    </tr>
                                    <tr>
                                       <td>가격</td>
                                       <td>${productList.price}원</td>
                                    </tr>
                                    <tr>
                                       <td>내용</td>
                                       <td>${productList.pcontent}</td>
                                    </tr>
                                    
                              			<tr>
                              				<td></td>
                              				<td class="text-right">
                              				<c:if test="${admin eq true }">
                              					<button id="delete" class="btn btn-outline-secondary btn-sm">삭제</button>	
                              					<button id="modify" class="btn btn-outline-secondary btn-sm">수정</button>
                              				</c:if>
                              					<button class="btn btn-outline-secondary btn-sm listBtn">목록</button>
                              				</td>
                              			</tr>
                              		
                                 </tbody>
                              </table>
                             
                             
                             
				<div class="list">
					<ul id="product_list">
						<h3>관련 음식</h3>
						
						<li>
							<c:forEach items="${relatedList}" var="productDTO">
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
											${productDTO.getContent()}
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
	$("#submit").on("click",function(e){
		$("#modifyForm").submit();
	});
	
	$("#modify").on("click",function(e){
		$("#modifyForm").css("display", "block");
		$("#dataTable").css("display", "none");
	});
   
	$(".listBtn").on("click",function(e){
		e.preventDefault();
	   location.href="${pageContext.request.contextPath }/product/list";
	});
   
	$("#cancelBtn").on("click",function(e){
		e.preventDefault();
		$("form").each(function(){
			this.reset();
			$("#pcontent").focus();
		});
	});
   
	$("#delete").on("click",function(e){
	   	e.preventDefault();
	   	location.href="${pageContext.request.contextPath }/product/delete?pnum=${productList.pnum}";
   });
	
	$(document).ready(function() {
		let txt = $("#pcontent")[0].attributes[3].textContent;
		$("#pcontent").html(txt);
	});
</script>

<jsp:include page="/main_footer.jsp" flush="false" />