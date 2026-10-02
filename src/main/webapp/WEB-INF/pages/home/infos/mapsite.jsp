<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="#" class="lien lien-black disaload"><spring:message code="explorer.mainmenu7"/></a></li>
		<li class="active"><spring:message code="explorer.mainmenu7.5"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen header-mapsite"><h1 class="h-header m-auto"><spring:message code="txt.infos.mapsite1"/></h1></div>
		<p class="parag-blog header-detect text-center m-auto"><spring:message code="txt.infos.mapsite1.1"/></p>
		<div class="row m-t-30">
			<div class="col-md-6 col-lg-3 m-t-20">
				<h2 class="h-doc h-header5 i-primary"><spring:message code="txt.infos.mapsite2.1"/></h2>
				<ul class="list-none list-mapsite m-t-40 m-l-20">
					<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/"/>" class="lien lien-table"><spring:message code="explorer.mainmenu1"/></a></li>
					<li>
						<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/entreprises"/>" class="lien lien-table"><spring:message code="sidebar.admin.dashboard3"/></a>
						<ul class="list-none list-mapsite">
							<c:set var="liens" value="secteurs,villes,recherche/entreprises,trouver-vite" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/${pageScope.lien}"/>" 
									class="lien lien-table"><spring:message code="explorer.home.mainmenu2.${state.count}" /></a></li>
							</c:forEach>
						</ul>
					</li>
					<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="#" class="lien lien-table"><spring:message code="sidebar.home.mainfooter1.8"/></a></li>
				</ul>
			</div>
			<div class="col-md-6 col-lg-3 m-t-20">
				<h2 class="h-doc h-header5 i-primary"><spring:message code="txt.infos.mapsite2.2"/></h2>
				<ul class="list-none list-mapsite m-t-40 m-l-20">
					<li>
						<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/marketplace"/>" class="lien lien-table"><spring:message code="sidebar.admin.dashboard4"/></a>
						<ul class="list-none list-mapsite">
							<c:set var="liens" value="produits-et-services,annonces,evenements,offres-emploi,actualites" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/marketplace/${pageScope.lien}"/>" 
									class="lien lien-table"><spring:message code="wizard.screen.navbar${state.count}" /></a></li>
							</c:forEach>
						</ul>
					</li>
				</ul>
			</div>
			<div class="col-md-6 col-lg-3 m-t-20">
				<h2 class="h-doc h-header5 i-primary"><spring:message code="txt.infos.mapsite2.3"/></h2>
				<ul class="list-none list-mapsite m-t-40 m-l-20">
					<li>
						<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/solutions"/>" class="lien lien-table"><spring:message code="sidebar.home.dashboard6"/></a>
						<ul class="list-none list-mapsite">
							<c:set var="liens" value="presentation,visibilite,referencement,detect,easylist,publicite" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/solutions/${pageScope.lien}"/>" 
									class="lien lien-table"><spring:message code="explorer.home.mainmenu4.${state.count}" /></a></li>
							</c:forEach>
						</ul>
					</li>
				</ul>
			</div>
			<div class="col-md-6 col-lg-3 m-t-20">
				<h2 class="h-doc h-header5 i-primary"><spring:message code="txt.infos.mapsite2.4"/></h2>
				<ul class="list-none list-mapsite m-t-40 m-l-20">
					<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
						<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/forums"/>" class="lien lien-table" target="_blank"><spring:message code="sidebar.home.mainfooter1.12"/></a></li>
					</sec:authorize>
					<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/membres"/>" class="lien lien-table"><spring:message code="wizard.screen.navbar6"/></a></li>
					<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/blog"/>" class="lien lien-table"><spring:message code="sidebar.admin.dashboard5"/></a></li>
					<li>
						<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/contacts"/>" class="lien lien-table"><spring:message code="sidebar.admin.dashboard9.6"/></a>
						<ul class="list-none list-mapsite">
							<li><i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/infos/temoignages-clients"/>" class="lien lien-table"><spring:message code="sidebar.home.mainfooter1.10"/></a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
		<hr class="m-t-30 m-b-30">
		<div class="text-center">
			<ul class="list-none list-inline list-footer-more font-small text-left">
				<c:set var="liens" value="faq,cgu,politique-confidentialite,mentions-legales,credits,charte-bonnes-pratiques" scope="page"></c:set>
				<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
					<li><a href="<c:url value="/infos/${pageScope.lien}"/>" class="lien lien-help">
						<spring:message code="explorer.mainmenu7.${state.count >= 5 ? state.count + 1 : state.count}"/></a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>