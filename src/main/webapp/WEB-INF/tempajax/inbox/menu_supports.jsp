<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<li class="dropdown-header">
	<span class="font-bold i-primary"><spring:message code="tooltip.popup.supports"/></span>
	<a id="readallSupports" class="lien lien-hover lien-primary lien-small pull-right" style="display:${hasAllConsulted ? 'none' : 'block'};">
		<spring:message code="tool.inobx.readall"/>
	</a>
	<div class="clearfix"></div>
</li>
<li class="dropdown-body">
	<ul class="menu-scroll list-none">
		<li style="display:none;"></li>
		<c:choose>
			<c:when test="${supports.size() > 0}"><c:import url="/WEB-INF/tempajax/inbox/sub_supports.jsp"/></c:when>
			<c:otherwise><li class="dropdown-empty"><spring:message code="tool.empty.supports"/></li></c:otherwise>
		</c:choose>
	</ul>
</li>
<li class="dropdown-footer text-center">
	<a href="<c:url value="/admin-user/feedback/supports"/>" class="lien lien-hover lien-primary lien-small"><spring:message code="tool.inobx.viewall"/></a>
</li>
</compress:html>