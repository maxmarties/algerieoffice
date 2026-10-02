<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<li class="dropdown-header">
	<span class="font-bold i-primary"><spring:message code="tooltip.popup.clouds"/></span>
	<a id="readallTalk" class="lien lien-hover lien-primary lien-small pull-right" style="display:${hasAllConsulted ? 'none' : 'block'};">
		<spring:message code="tool.inobx.readall"/>
	</a>
	<div class="clearfix"></div>
</li>
<li class="dropdown-body">
	<ul class="menu-scroll list-none">
		<c:choose>
			<c:when test="${talks.size() > 0}"><c:import url="/WEB-INF/tempajax/inbox/sub_talk.jsp"/></c:when>
			<c:otherwise><li class="dropdown-empty"><spring:message code="tool.empty.cloud"/></li></c:otherwise>
		</c:choose>
	</ul>
</li>
<li class="dropdown-footer text-center">
	<a href="<c:url value="/company-user/feedback/communications"/>" class="lien lien-hover lien-primary lien-small"><spring:message code="tool.inobx.viewall"/></a>
</li>
</compress:html>