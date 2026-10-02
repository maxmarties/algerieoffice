<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-partner">
	<ul class="list-none list-partner">
		<c:forEach var="partner" items="${explorerPage.partners}">
			<li>
				<div class="explorer-item">
					<img src="<c:url value="${partner.photoURL}"/>" class="pull-left img-circle" alt="<c:out value="${partner.name}" />">
					<div class="explorer-brand">
						<p class="h-header text-input text-truncate"><c:out value="${partner.name}"/></p>
						<div class="explorer-externe">
							<a href="<c:url value="${partner.url}" />" class="lien lien-explorer-primary lien-underline" target="_blank">
								<c:out value="${partner.url}" />
							</a>
						</div>
					</div>
					<span class="clearfix"></span>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>