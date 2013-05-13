<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="static/css/style.css" type="text/css" />

</head>
<body>
	<div class="main">
		<div class="header">
			<tiles:insertAttribute name="banner-content" />
		</div>
		<div class="content">
			<tiles:insertAttribute name="primary-content" />
			<tiles:insertAttribute name="paging-content" />
		</div>
		<div class="footer">
			<tiles:insertAttribute name="footer-content" />
		</div>
	</div>
</body>
</html>