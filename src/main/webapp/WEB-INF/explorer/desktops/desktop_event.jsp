<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-column widget-document">
	<p class="ind-screen"><spring:message code="explorer.desktop.grid3" /></p>
	<div id="submitDocumentForm" class="document-favorite form-submit p-right">
		<a class="transition-35 btn-submit iDocument ${explorerCurrent.hasPreview ? 'disabled' : favoriteDocument ? 'active' : ''}" 
			title="<spring:message code="tool.navigate.company2"/>"><i class="cmsms-icon-star-empty-2"></i></a>
	</div>
	<h1 class="h-doc h-doc1 text-title"><c:out value="${explorerPage.inbox.title}"/></h1>
	<div class="document-content">
		<div class="row">
			<div class="col-6"><p class="font-small"><spring:message code="tool.navigate.company" /> : <c:out value="${explorerPage.inbox.modifiedDate}" /></p></div>
			<div class="col-6">
				<p class="font-small text-right no-print">
					<a id="iPrint" class="lien lien-explorer-black lien-underline"><spring:message code="btn.print"/></a><i class="cmsms-icon-print m-l-5"></i>
				</p>
			</div>
		</div>
		<div class="document-background background-container" style="background-image: url('${explorerPage.inbox.photoURL}');"></div>
		<h2 class="h-header h-explorer3"><spring:message code="lbl.sub.explorer3.3"/></h2>
		<div class="table-responsive m-t-10">
			<table class="table table-explorer table-last">
				<thead><tr><th style="width:60%;"></th><th style="width:40%;"></th></tr></thead>
				<tbody class="font-small">
					<c:forEach var="eventDate" items="${explorerPage.inbox.eventsDate}" varStatus="state">
						<tr>
							<td><i class="cmsms-icon-calendar m-r-10"></i><c:out value="${eventDate}"/><span class="m-l-10"><c:out value="${explorerPage.inbox.eventsClock.get(state.count - 1)}"/></span></td>
							<td class="text-uppercase text-right"><i class="cmsms-icon-location m-r-10"></i><spring:message code="chose.wilaya${explorerPage.inbox.wilayas.get(state.count - 1)}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<h2 class="h-header h-explorer3 m-t-20"><spring:message code="lbl.sub.explorer3.4"/></h2>
		<div class="fr-view fr-explorer m-t-20"><c:out value="${explorerPage.inbox.detail}" escapeXml="false" /></div>
		<c:if test="${!empty explorerPage.meta.keysword}">
			<ul class="list-keysword list-none m-t-10">
				<c:forEach var="keyword" items="${explorerPage.meta.buildKeysword()}">
					<li>
						<i class="cmsms-icon-hash-1 text-segond i-tags m-r-5"></i>
						<a href="<c:url value="/marketplace/evenements?tag=${explorerPage.meta.parsKey(keyword)}" />" 
							class="lien lien-keyword lien-explorer-black"><c:out value="${keyword}" /></a>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${!empty explorerPage.inbox.urlExtern}">
			<hr class="my-2">
			<h2 class="h-header h-explorer3 m-b-10"><spring:message code="tabs.url"/></h2>
			<a href="<c:url value="${explorerPage.inbox.urlExtern}" />" class="lien lien-explorer-primary lien-underline lien-small" target="_blank">
				<c:out value="${explorerPage.inbox.urlExtern}" />
			</a>
		</c:if>
	</div>
	<div class="document-footer">
		<div>
			<c:set var="documentBackword" value="3" scope="request"></c:set>
			<c:import url="/WEB-INF/explorer/widgets/widget_prospect.jsp"/>
		</div>
	</div>
</div>