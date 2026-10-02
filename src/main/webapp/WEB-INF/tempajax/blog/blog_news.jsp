<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="widget-body">
	<c:choose>
		<c:when test="${list.isEmpty()}"><p><spring:message code="tool.empty.desktop" /></p></c:when>
		<c:otherwise>
			<ul class="list-none list-block list-screen-blog">
				<c:forEach var="line" items="${list}">
					<li>
						<div class="img-blog background-container pull-left" style="background-image: url('${line.photoURL}');"></div>
						<div class="screen-brand">
							<a href="<c:url value="${line.identifyURL}" />" class="lien lien-title h-header"><c:out value="${line.title}" /></a>
							<span class="help-text m-t-5">
								<spring:message code="tool.view.autor" /> <a href="<c:url value="${line.autorURL}" />" 
									class="lien lien-help lien-underline"><c:out value="${line.autorname}" /></a>, 
									<spring:message code="tool.view.in" /> <c:out value="${line.modifiedDate}" />
							</span>
						</div>
						<span class="clearfix"></span>
					</li>
				</c:forEach>
				<li>
					<i class="cmsms-icon-th-list-3 m-r-10"></i>
					<a href="<c:url value="/blog"/>" class="lien lien-primary lien-hover h-header"><spring:message code="sidebar.admin.dashboard5.1"/></a>
				</li>
			</ul>
		</c:otherwise>
	</c:choose>
</div>
</compress:html>