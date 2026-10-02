<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<nav class="navbar navbar-mainmenu navbar-expand-lg">
	<div class="container">
		<ul class="navbar-nav nav-responsive">
			<li><a class="transition-color" type="button" data-toggle="collapse" data-target=".nav-mainmenu" 
					aria-expanded="false"><i class="cmsms-icon-menu-3"></i></a></li>
		</ul>
		<ul class="navbar-nav menu-navigate hidden-md-down">
			<li><a href="<c:url value="${explorerCurrent.companyURL}"/>" 
					class="transition-color ${requestScope.explorerMainmenu == 1 ? 'active' : ''}"><i class="cmsms-icon-home-2"></i></a></li>
			<c:set var="mainmenu" value="${explorerCompany.menu.mainmenu()}" scope="page"></c:set>
			<c:if test="${pageScope.mainmenu[0]}">
				<c:set var="hasSociety" value="${explorerCompany.menu.hasSociety()}" scope="page"></c:set>
				<li class="${pageScope.hasSociety ? 'dropdown' : ''}">
					<a href="<c:url value="${explorerCurrent.companyURL}/presentation"/>" 
						class="transition-color ${requestScope.explorerMainmenu == 2 ? 'active' : ''}">
						<spring:message code="explorer.mainmenu2"/>
						<c:if test="${pageScope.hasSociety}"><i class="cmsms-icon-down-dir i-8 m-l-5"></i></c:if>
					</a>
					<c:if test="${pageScope.hasSociety}">
						<c:set var="society" value="${explorerCompany.menu.society()}" scope="page"></c:set>
						<ul class="dropdown-menu animated fadeInDown" role="menu">
							<c:set var="liens" value="historique,actualites,evenements,realisations,faqs,partenaires" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<c:if test="${pageScope.society[state.count - 1]}">
									<li>
										<a href="<c:url value="${explorerCurrent.companyURL}/${pageScope.lien}"/>" 
											class="dropdown-item transition-color"><spring:message code="explorer.mainmenu2.${state.count}"/></a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</c:if>
				</li>
			</c:if>
			<c:if test="${pageScope.mainmenu[1]}">
				<li class="${!explorerCompany.menu.categories.isEmpty() ? 'dropdown' : ''}">
					<a href="<c:url value="${explorerCurrent.companyURL}/produits-et-services"/>" 
						class="transition-color ${requestScope.explorerMainmenu == 3 ? 'active' : ''}">
						<spring:message code="explorer.mainmenu3"/>
						<c:if test="${!explorerCompany.menu.categories.isEmpty()}"><i class="cmsms-icon-down-dir i-8 m-l-5"></i></c:if>
					</a>
					<c:if test="${!explorerCompany.menu.categories.isEmpty()}">
						<ul class="dropdown-menu animated fadeInDown" role="menu">
							<c:forEach var="category" items="${explorerCompany.menu.categories}">
								<li><a href="<c:url value="${explorerCurrent.companyURL}${explorerCompany.menu.categoryURL}${category.identify}"/>" 
										class="dropdown-item transition-color"><c:out value="${category.title}"/></a></li>
							</c:forEach>
						</ul>
					</c:if>
				</li>
			</c:if>
			<c:if test="${pageScope.mainmenu[2]}">
				<li><a href="<c:url value="${explorerCurrent.companyURL}/marketplace"/>" 
						class="transition-color ${requestScope.explorerMainmenu == 4 ? 'active' : ''}"><spring:message code="explorer.mainmenu4"/></a></li>
			</c:if>
			<c:if test="${pageScope.mainmenu[3]}">
				<li><a href="<c:url value="${explorerCurrent.companyURL}/contact"/>" 
						class="transition-color ${requestScope.explorerMainmenu == 5 ? 'active' : ''}"><spring:message code="explorer.mainmenu5"/></a></li>
			</c:if>
		</ul>
		<c:import url="/WEB-INF/explorer/sidebars/sidebar_evaluation.jsp"/>
		<div class="collapse navbar-collapse nav-mainmenu">
			<ul class="navbar-nav">
				<li><a href="<c:url value="${explorerCurrent.companyURL}"/>" 
						class="transition-35 ${requestScope.explorerMainmenu == 1 ? 'active' : ''}"><spring:message code="explorer.mainmenu1"/></a></li>
				<c:set var="liens" value="presentation,produits-et-services,marketplace,contact" scope="page"></c:set>
				<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
					<c:if test="${pageScope.mainmenu[state.count - 1]}">
						<li><a href="<c:url value="${explorerCurrent.companyURL}/${pageScope.lien}"/>" 
								class="transition-35 ${requestScope.explorerMainmenu == state.count + 1 ? 'active' : ''}">
								<spring:message code="explorer.mainmenu${state.count + 1}"/></a></li>
					</c:if>
				</c:forEach>
				<li><a class="transition-35" data-toggle="collapse" data-target=".nav-plus" aria-expanded="false">
						<spring:message code="tooltip.navcompany"/><i class="cmsms-icon-plus nav-trigger p-right"></i></a>
					<div class="collapse navbar-collapse nav-plus">
						<ul class="navbar-nav">
							<li><a href="tel:+213${explorerCompany.profile.phone}" class="transition-35 iPhone ${explorerCurrent.hasPreview ? 'disabled' : ''}">
									<i class="cmsms-icon-call-out m-r-10"></i><spring:message code="tool.navigate.company1"/>
								</a></li>
							<li><a class="transition-35 iFavorite ${explorerCurrent.hasPreview ? 'disabled' : !empty currentVisitor.favorite ? 'active' : ''}">
									<i class="cmsms-icon-star-3 m-r-10"></i><spring:message code="tool.navigate.company2"/>
								</a></li>
							<li><a href="mailto:${explorerCompany.profile.email}" class="transition-35 iMail ${explorerCurrent.hasPreview ? 'disabled' : ''}">
									<i class="cmsms-icon-mail-1 m-r-10"></i><spring:message code="tool.navigate.company3"/>
								</a></li>
						</ul>
					</div>
				</li>
			</ul>
		</div>
	</div>
</nav>