<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:if test="${currentSocial.begginer && !currentConfig.begginerCollapse}">
	<div id="bnBeginner" class="bn-beginner">
		<div class="container">
			<ul class="navbar-nav nav-flex-icons">
				<li class="font-small m-r-20"><spring:message code="message.explorer.begginer1" /><br><spring:message code="message.explorer.begginer2" /></li>
				<li class="ml-auto"><a class="btn btn-primary btn-fixed" data-dismiss="begginer"><span><spring:message code="message.explorer.begginer3" /></span></a></li>
			</ul>
		</div>
	</div>
</c:if>