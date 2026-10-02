<%@ include file="/WEB-INF/tags/libs/tiles_libs.jsp"%>
<compress:html removeIntertagSpaces="false" removeComments="true">
<!DOCTYPE html>
<html dir="${langage.dir}" lang="${langage.lang}">
<head>
	<tiles:insertAttribute name="meta"/>
	<title><c:out value="${maquetteTitle}"/></title>
	<c:import url="/WEB-INF/tags/comps/init_css.jsp"/>
	<link rel="stylesheet" href='<c:url value="/static/webjars/css/window/screen.min.css"/>'>
</head>
<body class="${langage.clazz}">
	<div class="maquette-navbar bn-beginner" style="padding:5px 0;">
		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<p style="padding:10px 0;">
						<span class="font-bold i-primary"><c:out value="${maquetteTitle}"/></span>: <span class="font-small i-help"><spring:message code="subheader.office.slider4.7"/></span>
					</p>
				</div>
				<div class="col-md-7">
					<div class="text-right">
						<a href="<c:url value="${maquetteLink}" />" class="btn btn-segond btn-fixed m-r-5" target="_blank">
							<span><spring:message code="subheader.office.slider4.7.1"/><i class="cmsms-icon-paper-plane-3 m-l-20"></i></span>
						</a>
						<a href="<c:url value="/contacts" />" class="btn btn-primary btn-fixed">
							<span><spring:message code="subheader.office.slider4.7.2"/><i class="cmsms-icon-explorer-arrow m-l-20"></i></span>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="inner-thumbnail" style="width:100;padding:0!important;">
		<img class="img-responsive" src="<c:url value="/static/picts/aodemos/demotheme${maquetteIndex}-min.jpg"/>" alt="<c:out value="${maquetteTitle}"/>">
	</div>
	<script src="<c:url value="/static/webjars/js/lib/ui.min.js" />"></script>
	<script src="<c:url value="/static/webjars/js/algerieoffice.min.js" />"></script>
	<script type="text/javascript">$(function(){Algerieoffice.init('<c:url value="/" />','<c:out value="${langage.lang}" />')});</script>
</body>
</html>
</compress:html>