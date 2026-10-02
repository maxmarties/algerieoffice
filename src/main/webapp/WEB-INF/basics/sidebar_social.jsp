<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<ul class="navbar-nav nav-flex-icons navbar-social">
	<li class="nav-item">
		<a href="<c:url value="https://www.facebook.com/algerieoffice"/>" class="nav-link icon-facebook transition-35" target="_blank" 
			title="<spring:message code="tooltip.sociaux" arguments="Facebook"/>">
			<i class="cmsms-icon-facebook"></i><c:if test="${currentSocial.facebook != 0}"><span class="footer-indicator"><c:out value="${currentSocial.facebook}" /></span></c:if>
		</a>
	</li>
	<li class="nav-item">
		<a href="<c:url value="https://twitter.com/AlgerieOffice"/>" class="nav-link icon-twitter transition-35" target="_blank"
			title="<spring:message code="tooltip.sociaux" arguments="Twitter"/>">
			<i class="cmsms-icon-twitter"></i><c:if test="${currentSocial.twitter != 0}"><span class="footer-indicator"><c:out value="${currentSocial.twitter}" /></span></c:if>
		</a>
	</li>
	<li class="nav-item">
		<a href="<c:url value="https://www.linkedin.com/company/algerieoffice"/>" class="nav-link icon-linkedin transition-35" target="_blank"
			title="<spring:message code="tooltip.sociaux" arguments="Linkedin"/>">
			<i class="cmsms-icon-linkedin"></i><c:if test="${currentSocial.linkedin != 0}"><span class="footer-indicator"><c:out value="${currentSocial.linkedin}" /></span></c:if>
		</a>
	</li>
	<li class="nav-item">
		<a href="<c:url value="https://www.youtube.com/channel/UCVtW1WLbMT63ny6X1lhehAw"/>" class="nav-link icon-youtube transition-35" target="_blank"
			title="<spring:message code="tooltip.sociaux" arguments="Youtube"/>">
			<i class="cmsms-icon-youtube"></i><c:if test="${currentSocial.youtube != 0}"><span class="footer-indicator"><c:out value="${currentSocial.youtube}" /></span></c:if>
		</a>
	</li>
</ul>