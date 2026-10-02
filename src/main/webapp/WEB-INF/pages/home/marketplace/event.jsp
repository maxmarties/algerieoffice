<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/marketplace"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li><a href="<c:url value="/marketplace/evenements"/>" class="lien lien-black"><spring:message code="wizard.screen.navbar3"/></a></li>
		<li class="active"><c:out value="${inbox.title}"/></li>
	</ol>
</div>
<div class="screen-container screen-noprint ${!empty sponsoreScreen ? 'screen-aobubs' : ''}">
	<div class="container">
		<c:if test="${!empty sponsoreScreen}"><c:import url="/WEB-INF/fields/screen/screen_sponsore.jsp"/></c:if>
		<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.event"/></c:set>
		<c:set var="placeholderResult" scope="request"><spring:message code="tool.result.screen3"/></c:set>
		<c:import url="/WEB-INF/fields/screen/screen_find.jsp"/>
	</div>
</div>
<c:set var="treeviewNav" value="3" scope="request"></c:set>
<c:import url="/WEB-INF/fields/screen/screen_marketplace.jsp"/>
<c:set var="hrefBackword" value="/marketplace/evenements" scope="request"></c:set>
<c:set var="screenBackword" value="3" scope="request"></c:set>
<c:import url="/WEB-INF/fields/screen/screen_backword.jsp"/>
<div class="screen-container screen-segond">
	<div class="container">
		<div class="row">
			<c:set var="screenAbout" value="3" scope="request"></c:set>
			<c:import url="/WEB-INF/fields/screen/screen_about.jsp"/>
			<div class="col-lg-9 m-t-10 m-b-10">
				<div id="screenDocument" class="screen-column widget-document">
					<p class="ind-screen"><spring:message code="explorer.desktop.grid3" /></p>
					<div id="submitFavoriteForm" class="document-favorite form-submit p-right no-print">
						<a class="transition-35 btn-submit iFavorite ${hasFavorite ? 'active' : ''}" 
							title="<spring:message code="tool.navigate.company2"/>"><i class="cmsms-icon-star-empty-2"></i></a>
					</div>
					<h1 class="h-doc h-doc1"><c:out value="${inbox.title}"/></h1>
					<div class="document-content">
						<div class="row">
							<div class="col-6"><p class="font-small"><spring:message code="tool.navigate.company" /> : <c:out value="${inbox.modifiedDate}" /></p></div>
							<div class="col-6">
								<p class="font-small text-right no-print">
									<a id="iPrint" class="lien lien-black lien-underline"><spring:message code="btn.print"/></a><i class="cmsms-icon-print m-l-5"></i>
								</p>
							</div>
						</div>
						<div class="document-background background-container" style="background-image: url('${inbox.photoURL}');"></div>
						<h2 class="h-header h-header4 i-header"><spring:message code="lbl.sub.explorer3.3"/></h2>
						<div class="table-responsive m-t-10">
							<table class="table table-widget">
								<thead><tr><th style="width:60%;"></th><th style="width:40%;"></th></tr></thead>
								<tbody class="font-small">
									<c:forEach var="eventDate" items="${inbox.eventsDate}" varStatus="state">
										<tr>
											<td>
												<i class="cmsms-icon-calendar m-r-10"></i><c:out value="${eventDate}"/>
												<span class="m-l-10"><c:out value="${inbox.eventsClock.get(state.count - 1)}"/></span>
											</td>
											<td class="text-uppercase text-right">
												<i class="cmsms-icon-location m-r-10"></i><spring:message code="chose.wilaya${inbox.wilayas.get(state.count - 1)}"/>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<div class="m-t-20">
							<h2 class="h-header h-header4 i-header m-b-20"><spring:message code="lbl.sub.explorer3.4"/></h2>
							<div class="fr-view fr-explorer"><c:out value="${inbox.detail}" escapeXml="false" /></div>
							<c:if test="${!empty inbox.keysword}">
								<ul class="list-keysword list-none m-t-10">
									<c:forEach var="keyword" items="${inbox.buildKeysword()}">
										<li>
											<i class="cmsms-icon-hash-1 i-segond i-tags m-r-5"></i>
											<a href="<c:url value="/marketplace/evenements?tag=${inbox.parsKey(keyword)}" />" 
												class="lien lien-keyword"><c:out value="${keyword}" /></a>
										</li>
									</c:forEach>
								</ul>
							</c:if>
							<c:if test="${!empty inbox.urlExtern}">
								<hr class="my-2">
								<h2 class="h-header h-header4 i-header m-b-20"><spring:message code="tabs.url"/></h2>
								<a href="<c:url value="${inbox.urlExtern}" />" class="lien lien-primary lien-underline lien-small" 
									target="_blank"><c:out value="${inbox.urlExtern}" /></a>
							</c:if>
						</div>
					</div>
					<div class="document-footer screen-noprint">
						<div>
							<c:set var="documentBackword" value="3" scope="request"></c:set>
							<c:import url="/WEB-INF/explorer/widgets/widget_prospect.jsp"/>
						</div>
					</div>
				</div>
				<div id="screenSimultudes"></div>
			</div>
		</div>
	</div>
</div>