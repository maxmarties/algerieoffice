<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<footer class="main-footer">
	<c:if test="${empty homeContact}">
		<c:if test="${empty homeInfos}"><a class="btn btn-segond btn-totop btn-simple" href="#page-top"><span><i class="cmsms-icon-up-open"></i></span></a></c:if>
		<div class="footer-up">
			<div class="container">
				<div class="row h-header">
					<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
						<a class="brand-logo m-auto" href="<c:url value="/"/>" style="margin-top:-4px;">
							<img height="40" src="<c:url value="/static/icons/branded-white.svg"/>" alt="<spring:message code="app.brand"/>" /></a>
						<p class="font-small m-t-20"><spring:message code="app.description"/></p>
						<p class="i-gray m-t-20 m-b-20"><spring:message code="app.addr1"/><br><spring:message code="app.addr2"/></p>
					</div>
					<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
						<h3 class="h-doc h-footer"><spring:message code="header.explorer.mainfooter1"/></h3>
						<div class="widget-footer">
							<ul class="list-none list-block list-mainfooter font-small">
								<c:set var="liens" value="marketplace,marketplace/produits-et-services,marketplace/annonces,marketplace/evenements,marketplace/offres-emploi,marketplace/actualites,blog,user/repports/testimonial,membres,infos/temoignages-clients,solutions/publicite" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li>
										<i class="cmsms-icon-explorer-angle m-r-10"></i>
										<a href="<c:url value="/${pageScope.lien}"/>" class="lien"><spring:message code="sidebar.home.mainfooter1.${state.count}"/></a>
									</li>
								</c:forEach>
								<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
									<li>
										<i class="cmsms-icon-explorer-angle m-r-10"></i><a href="<c:url value="/forums"/>" class="lien" 
											target="_blank"><spring:message code="sidebar.home.mainfooter1.12"/></a><i class="cmsms-icon-paper-plane-3 font-mini m-l-5 i-segond1"></i>
									</li>
								</sec:authorize>
							</ul>
						</div>
					</div>
					<div class="col-sm-12 col-lg-3">
						<div class="row">
							<div class="col-sm-6 col-lg-12 m-t-10 m-b-10">
								<h3 class="h-doc h-footer"><spring:message code="header.explorer.mainfooter2"/></h3>
								<div class="widget-footer">
									<ul class="list-none list-block list-mainfooter font-small">
										<c:set var="liens" value="visibilite,referencement,detect,easylist,presentation" scope="page"></c:set>
										<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
											<li>
												<i class="cmsms-icon-explorer-angle m-r-10"></i>
												<a href="<c:url value="/solutions/${pageScope.lien}"/>" class="lien"><spring:message code="sidebar.home.mainfooter2.${state.count}"/></a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
							<div class="col-sm-6 col-lg-12 m-t-10 m-b-10">
								<h3 class="h-doc h-footer"><spring:message code="header.explorer.mainfooter3"/></h3>
								<div class="widget-footer">
									<ul class="list-none list-block list-mainfooter font-small">
										<c:set var="liens" value="secteurs,villes,recherche/entreprises,trouver-vite" scope="page"></c:set>
										<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
											<li>
												<i class="cmsms-icon-explorer-angle m-r-10"></i>
												<a href="<c:url value="/${pageScope.lien}"/>" class="lien"><spring:message code="sidebar.home.mainfooter3.${state.count}"/></a>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-12 col-lg-3">
						<div class="row">
							<div class="col-sm-6 col-lg-12 m-t-10 m-b-10">
								<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
									<c:set var="lienExtranet" value="user/dashboard" scope="page"></c:set>
									<c:set var="lienPartner" value="admin/dashboard" scope="page"></c:set>
								</sec:authorize>
								<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
									<c:set var="lienExtranet" value="company/dashboard" scope="page"></c:set>
									<c:set var="lienPartner" value="company/tools/subscribes/new" scope="page"></c:set>
								</sec:authorize>
								<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')">
									<c:set var="lienExtranet" value="user/dashboard" scope="page"></c:set>
									<c:set var="lienPartner" value="guest/add-company" scope="page"></c:set>
								</sec:authorize>
								<sec:authorize access="isAnonymous()">
									<c:set var="lienExtranet" value="users/login" scope="page"></c:set>
									<c:set var="lienPartner" value="register/company" scope="page"></c:set>
								</sec:authorize>
								<a href="<c:url value="/${pageScope.lienExtranet}" />" class="btn btn-footer-segond btn-block btn-flat">
									<span><i class="cmsms-icon-lock-5 m-r-10"></i><spring:message code="btn.partner6.1"/></span></a>
								<a href="<c:url value="/${pageScope.lienPartner}" />" class="btn btn-footer-primary btn-block btn-flat">
									<span><spring:message code="btn.partner6.2"/></span></a>
							</div>
							<div class="col-sm-6 col-lg-12 m-t-10 m-b-10">
								<h3 class="h-doc h-footer"><spring:message code="header.explorer.mainfooter4"/></h3>
								<div class="widget-footer">
									<p class="font-small m-b-10"><spring:message code="txt.help.explorer5.3"/></p>
									<a href="mailto:<spring:message code="app.contact" />" class="lien lien-segond lien-hover"><spring:message code="app.contact" /></a>
									<p class="font-large i-white sh-black m-t-10"><spring:message code="app.mobile" /></p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${!empty requestScope.parseBlog}"><c:import url="/WEB-INF/tempajax/blog/blog_explorer.jsp"/></c:if>
				<hr class="my-2">
				<h3 class="h-doc h-footer m-t-30"><spring:message code="header.explorer.mainfooter5"/></h3>
				<div class="widget-footer">
					<ul class="list-none list-inline list-footer-more font-small text-left">
						<c:forEach var="i" begin="1" end="48" step="1">
							<li><a href="<c:url value="/villes?wilaya=${i}"/>" class="lien"><spring:message code="chose.wilaya${i}"/></a></li>
						</c:forEach>
					</ul>
				</div>
				<c:import url="/WEB-INF/basics/footer_brand.jsp"/>
			</div>
		</div>
	</c:if>
	<c:import url="/WEB-INF/basics/footer_copyright.jsp"/>
</footer>