<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="static/css/style.css" type="text/css" />
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript" src="static/js/jquery-2.0.0.js"></script>

</head>
<body>
	<div class="main">
		<div class="header">
			<tiles:insertAttribute name="banner-content" />
		</div>
		<div id="page">
			<div id="content">
				<tiles:insertAttribute name="primary-content" />
			</div>
			<tiles:insertAttribute name="sidebar-content" />
		</div>
		<div class="footer">
			<tiles:insertAttribute name="footer-content" />
		</div>
	</div>
</body>
</html>