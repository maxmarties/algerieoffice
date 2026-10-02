<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-linked m-t-10">
	<ul class="navbar-nav nav-flex-icons navbar-linked">
		<c:set var="providers" value="facebook,twitter,google,linkedin,youtube,instagram" scope="page"></c:set>
		<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
			<c:if test="${!empty explorerPage.linked.socialMedias[state.count - 1]}">
				<li><a href="<c:url value="${explorerPage.linked.socialMedias[state.count - 1]}" />" 
						class="btn btn-linked btn-${pageScope.provider}" target="_blank"><i class="cmsms-icon-${pageScope.provider}"></i></a></li>
			</c:if>
		</c:forEach>
	</ul>
</div>