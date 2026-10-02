<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<ul class="navbar-nav nav-flex-icons">
	<li class="m-r-10"><spring:message code="tool.follow"/> :</li>
	<li><a href="<c:url value="https://www.facebook.com/algerieoffice"/>" class="lien" target="_blank"
		title="<spring:message code="tooltip.sociaux" arguments="Facebook"/>"><i class="cmsms-icon-facebook"></i></a></li>
	<li><a href="<c:url value="https://twitter.com/AlgerieOffice"/>" class="lien" target="_blank" 
		title="<spring:message code="tooltip.sociaux" arguments="Twitter"/>"><i class="cmsms-icon-twitter"></i></a></li>
	<li><a href="<c:url value="https://www.linkedin.com/company/algerieoffice"/>" class="lien" target="_blank" 
		title="<spring:message code="tooltip.sociaux" arguments="Linkedin"/>"><i class="cmsms-icon-linkedin"></i></a></li>
	<li><a href="<c:url value="https://www.youtube.com/channel/UCVtW1WLbMT63ny6X1lhehAw"/>" class="lien" target="_blank"
		title="<spring:message code="tooltip.sociaux" arguments="Youtube"/>"><i class="cmsms-icon-youtube"></i></a></li>
</ul>