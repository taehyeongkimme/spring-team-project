<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!doctype html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<title>얼리어먹터</title>

<!-- Bootstrap 시작 -->
<link rel="stylesheet"
	href="/resources/bootstrap-4.2.1/dist/css/bootstrap.css" />
<script src="/webjars/jquery/3.3.1/dist/jquery.min.js"></script>
<script src="/webjars/popper.js/1.14.6/dist/umd/popper.min.js"></script>
<script src="/resources/bootstrap-4.2.1/dist/js/bootstrap.js"></script>
<!-- Bootstrap 끝     -->

<!-- font-awesome -->
<link rel="stylesheet" href="/webjars/font-awesome/5.6.3/css/all.css" />

<link rel="stylesheet" href="/resources/css/font.css" />


<!-- Bootstrap core CSS -->
<link href="/resources/bootstrap-4.2.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous" />

<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}
</style>



<!-- Custom styles for this template -->
<link href="/resources/css/sticky-footer-navbar.css" rel="stylesheet">
<link href="/resources/css/carousel.css" rel="stylesheet">

</head>
<body class="d-flex flex-column h-100">
	<!--1) SPRING_SECURITY_CONTEXT 내장객체 -->
	<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal }" />
	
	<sec:authorize var="admin" access="hasRole('ROLE_ADMIN')"/>
	


	<header>
		<!-- Fixed navbar -->
		<nav
			class="navbar navbar-expand-md navbar-dark fixed-top bg-danger text-white">
			<a class="navbar-brand" href="/">얼리어먹터</a>

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarCollapse" aria-controls="navbarCollapse"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarCollapse">
				<ul class="navbar-nav mr-auto">

					<li class="bLink nav-item active"><a class="nav-link"
						href="/board/list">게시판 <span class="sr-only">(current)</span></a>
					</li>

					<li class="pLink nav-item active"><a class="nav-link"
						href="/product/list">제품게시판</a></li>

				</ul>
				<form class="form-inline mt-2 mt-md-0">


					<c:choose>
						<c:when test="${user eq null }">

							<a class="nav-link dropdown-toggle text-light" href="#"
								id="userDropdown" role="button" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false"> <i
								class="far fa-user-circle"></i>
							</a>

							<!-- Dropdown - User Information -->
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<a class="dropdown-item" href="/login/loginForm"> <i
									class="fas fa-sign-in-alt fa-sm fa-fw mr-2 text-gray-400"></i>
									로그인
								</a> <a class="dropdown-item" href="/member/joinForm"> <i
									class="fas fa-user-plus fa-sm fa-fw mr-2 text-gray-400"></i>
									회원가입
								</a>

							</div>
						</c:when>
						<c:when test="${admin eq true }">
							
							<a class="nav-link dropdown-toggle text-light" href="#"
						id="userDropdown" role="button" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="false"> <span
						class="mr-2 d-none d-lg-inline text-gray-600 small">${user.nickname }</span>
						<i class="fas fa-user-circle"></i>
					</a>
					<!-- Dropdown - User Information -->
					<div
						class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
						aria-labelledby="userDropdown">
						<a class="dropdown-item"
							href="/member/memberModifyForm/${user.id }"> <i
							class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> 내정보
						</a> <a class="dropdown-item" href="/member/myList"> <i
							class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i> 내가 쓴 게시물
						</a> <a class="dropdown-item" href="/member/memberList"> <i
							class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i> 회원 관리
						</a>

						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="/login/logout"> <i
							class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
							로그아웃
						</a>

					</div>
							
						</c:when>
						<c:otherwise>

							<a class="nav-link dropdown-toggle text-light" href="#"
								id="userDropdown" role="button" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small">${user.nickname }</span>
								<i class="fas fa-user-circle"></i>
							</a>
							<!-- Dropdown - User Information -->
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<a class="dropdown-item"
									href="/member/memberModifyForm/${user.id }"> <i
									class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> 내정보
								</a> <a class="dropdown-item" href="/member/myList"> <i
									class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i> 내가 쓴
									게시물
								</a>

								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="/login/logout"> <i
									class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
									로그아웃
								</a>

							</div>

						</c:otherwise>
					</c:choose>



					

		

				</form>
			</div>
		</nav>
	</header>