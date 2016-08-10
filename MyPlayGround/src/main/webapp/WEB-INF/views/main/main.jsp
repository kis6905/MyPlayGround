<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

<%@ include file="../include/include.jsp" %>

<link href="/resources/css/bootstrap/main.css" rel="stylesheet">

<script type="text/javascript">
</script>

</head>

<body>
	<%@ include file="../include/header.jsp" %>

	<!-- Begin page content -->
	<div class="container">
		
		<!-- Carousel
		================================================== -->
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<c:forEach items="${myServiceList}" var="myService" varStatus="vs">
					<c:choose>
						<c:when test="${vs.index == 0}">
							<li data-target="#myCarousel" data-slide-to="${vs.index}" class="active"></li>
						</c:when>
						<c:otherwise>
							<li data-target="#myCarousel" data-slide-to="${vs.index}"></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ol>
			<div class="carousel-inner" role="listbox">
				<c:forEach items="${myServiceList}" var="myService" varStatus="vs">
					<c:choose>
						<c:when test="${vs.index == 0}">
							<div class="item active">
								<img class="slide${myService.serviceId}" src="/resources/images/${myService.sliderImage}" alt="slide${myService.serviceId}">
								<div class="container">
									<div class="carousel-caption">
										<h1>${myService.title}</h1>
										<p>${myService.description}</p>
										<p><a class="btn btn-lg btn-primary" href="/service/page/${myService.serviceId}" role="button">바로가기</a></p>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="item">
								<img class="slide${myService.serviceId}" src="/resources/images/${myService.sliderImage}" alt="slide${myService.serviceId}">
									<div class="container">
										<div class="carousel-caption">
											<h1>${myService.title}</h1>
											<p>${myService.description}</p>
											<p><a class="btn btn-lg btn-primary" href="/service/page/${myService.serviceId}" role="button">바로가기</a></p>
										</div>
									</div>
								</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
				<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
				<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div><!-- /.carousel -->
		
		<div id="serviceIconArea">
			<div class="row">
				<c:forEach items="${myServiceList}" var="myService">
					<div id="iconArea" class="col-xs-6 col-sm-3"><span id="iconClickArea" onclick="location.href='/service/page/${myService.serviceId}'"><img src="/resources/images/${myService.iconImage}"><br>${myService.title}</span></div>
				</c:forEach>
			</div>
		</div>
		
	</div>

	<%@ include file="../include/footer.jsp" %>
</body>
</html>
