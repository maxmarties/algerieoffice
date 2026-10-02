<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column background-container bnn-anonym h-100" 
	style="background-image: radial-gradient(circle at center, rgba(32,42,56,.4) 10%, #242A38 100%), url('<c:url value="/static/picts/aobns/subscribe-min.jpg" />');">
	<h2 class="h-header h-header2"><spring:message code="explorer.subheader.browser1"/></h2>
	<div class="widget-body">
		<p class="h-header i-gray m-auto"><spring:message code="txt.help.explorer2.1"/></p>
		<ul class="navbar-nav nav-flex-icons">
			<c:set var="providers" value="picture-4,basket-2,headphones-3,hash-1,laptop-2,facebook-1,clock-5,credit-card" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<li>
					<i class="cmsms-icon-${pageScope.provider} i-28"></i>
					<span class="font-mini hidden-sm-down"><spring:message code="tool.anonym.company1.${state.count}"/></span>
				</li>
			</c:forEach>
		</ul>
		<div class="text-center">
			<a href="<c:url value="/register/company"/>" class="btn btn-warning btn-add btn-left"><span><i class="cmsms-icon-plus"></i><spring:message code="btn.partner3"/></span></a>
		</div>
	</div>
</div>