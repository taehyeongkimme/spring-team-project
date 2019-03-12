<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   

<style>
.card-img-top {
	border-radius:3px !important;
}
</style>

<!-- Begin page content -->
<main role="main" style="" class="container">
<div class="album py-5 ">
	<div class="container">
		<div class="row">
			<c:forEach items="${list}" var="productDTO">
				<div class="col-md-4">
					<div class="card mb-4 shadow-sm">
						<svg style="background:url('${productDTO.getImage()}');background-size: 100%;"
							class="bd-placeholder-img card-img-top" width="100%" height="225"
							xmlns="http://www.w3.org/2000/svg"
							preserveAspectRatio="xMidYMid slice" focusable="false" role="img"
							aria-label="Placeholder: Thumbnail">
							<title>Placeholder</title>
							<rect fill="none" width="100%" height="100%" />
							<text fill="#eceeef" dy=".3em" x="50%" y="50%"></text></svg>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
</main>
