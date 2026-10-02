<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="widget-empty-company background-container flexed flex-colone flex-jusitify h-100"
	style="background-image: radial-gradient(circle at center, rgba(32,42,56,.4) 10%, #242A38 100%), url('<c:url value="/static/picts/aobns/subscribe-min.jpg" />');">
	<div>
		<h3 class="h-header h-header4 sh-black"><spring:message code="explorer.subheader.browser1"/></h3>
		<p class="font-small m-t-10"><spring:message code="txt.help.explorer2.2"/></p>
		<c:if test="${!empty requestScope.widgetEmptyB2C}">
			<hr class="my-1 i-light">
			<ul class="navbar-nav nav-flex-icons" style="padding-bottom:10px;">
				<c:set var="providers" value="picture-4,basket-2,headphones-3,hash-1,laptop-2,facebook-1,clock-5,credit-card" scope="page"></c:set>
				<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
					<li class="text-center" style="width:12.5%;" title="<spring:message code="tool.anonym.company1.${state.count}"/>">
						<i class="cmsms-icon-${pageScope.provider} font-large"></i></li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
	<a href="<c:url value="/register/company"/>" class="btn btn-segond btn-flat btn-block"><span><spring:message code="btn.partner6.3"/></span></a>
</div>
</compress:html>