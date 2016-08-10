<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script type="text/javascript">

</script>

	<!-- Fixed navbar -->
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/main"><img width="100" height="33" src="/resources/images/logo.png"></a>
<!-- 				<a class="navbar-brand" href="/main">Game Reunion</a> -->
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
<!-- 					<li class="active"><a href="#">게임</a></li> -->
<!-- 					<li><a href="/game/list">게임</a></li> -->
<!-- 					<li><a href="/mypage/main">마이 페이지</a></li> -->
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">${NICKNAME}<span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="/out">로그아웃</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>