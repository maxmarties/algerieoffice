<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<aside class="main-sidebar dashboard-sidebar">
	<c:import url="/WEB-INF/basics/brand_admin.jsp"/>
	<div class="sidebar flexed flex-colone flex-jusitify">
		<div class="sidebar-body">
			<div class="sidebar-scroll">
				<ul class="sidebar-menu" data-widget="tree">
					<li class="treeview ${!empty requestScope.treeviewDashboard ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-home-2 i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard1"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="home,analytic,journal,detect" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/company/dashboard/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewDashboard == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard1.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewPosts ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-bag i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard1"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="all,#,categories,statistic" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<c:choose>
									<c:when test="${state.count == 2}">
										<sec:authorize access="hasAuthority('COMPANY_AUTOR_PRIVILEGE')">
											<li class="treeview-empty">
												<a href="<c:url value="/company/posts/new"/>" class="transition-35 ${requestScope.treeviewPosts == 2 ? 'active' : ''}">
													<spring:message code="sidebar.company.dashboard1.2"/>
												</a>
											</li>	
										</sec:authorize>
									</c:when>
									<c:otherwise>
										<li class="treeview-empty">
											<a href="<c:url value="/company/posts/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewPosts == state.count ? 'active' : ''}">
												<spring:message code="sidebar.company.dashboard1.${state.count}"/>
											</a>
										</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewMarketplace ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-pin-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard2"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="promotes,ads,jobs,campaigns" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/company/marketplace/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewMarketplace == state.count ? 'active' : ''}">
										<spring:message code="sidebar.company.dashboard2.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewPortfolio ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-book i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard3"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="works,actus,events,faqs,partners" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/company/portfolio/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewPortfolio == state.count ? 'active' : ''}">
										<spring:message code="sidebar.company.dashboard3.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.treeviewOverview ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-website i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard4"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="header,presentation,catalog,slider,timeline,about,think" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/company/overview/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewOverview == state.count ? 'active' : ''}">
											<spring:message code="sidebar.company.dashboard4.${state.count}"/>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.treeviewManage ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-sliders i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard5"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="display,sticky,widget,appearance,preferences" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/company/manage/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewManage == state.count ? 'active' : ''}">
											<spring:message code="sidebar.company.dashboard5.${state.count}"/>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.treeviewProfil ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-building-filled i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard6"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="identity,briefcase,contact,location,linked,credit" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/company/profile/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewProfil == state.count ? 'active' : ''}">
											<spring:message code="sidebar.company.dashboard6.${state.count}"/>
											<c:if test="${state.count == 1 && !currentCompany.enabled}">
												<i class="empty-trigger cmsms-icon-attention-alt i-yellow"></i>
											</c:if>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.treeviewTeam ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-user-2 i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard7"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="users,users/new,agents,guests" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/company/team/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewTeam == state.count ? 'active' : ''}">
											<spring:message code="sidebar.company.dashboard7.${state.count}"/>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<li class="treeview ${!empty requestScope.treeviewCommunication ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-cloud i-22 ${currentCommunication.hasPresent() ? 'i-yellow' : ''}"></i>
							<span class="treeview-header"><spring:message code="sidebar.company.dashboard8"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="notices,contacts,evaluations,appointments,collaborators,partners,chatbots" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<c:set var="provider" value="${currentCommunication.getCommunication(state.count)}" scope="page"></c:set>
								<li class="treeview-empty">
									<a href="<c:url value="/company/communication/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewCommunication == state.count ? 'active' : ''}">
										<spring:message code="sidebar.company.dashboard8.${state.count}"/>
										<c:if test="${!empty pageScope.provider}"><span class="treeview-indicator"><c:out value="${pageScope.provider}"/></span></c:if>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewProspect ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-quote-right i-22 ${currentProspect.hasPresent() ? 'i-yellow' : ''}"></i>
							<span class="treeview-header"><spring:message code="sidebar.company.dashboard9"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="quotes,ads,infos,jobs" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<c:set var="provider" value="${currentProspect.getProspect(state.count)}" scope="page"></c:set>
								<li class="treeview-empty">
									<a href="<c:url value="/company/prospect/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewProspect == state.count ? 'active' : ''}">
										<spring:message code="sidebar.company.dashboard9.${state.count}"/>
										<c:if test="${!empty pageScope.provider}"><span class="treeview-indicator"><c:out value="${provider}"/></span></c:if>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.treeviewNewsletter ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-email i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard12"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="template,budget,emailing" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/company/newsletter/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewNewsletter == state.count ? 'active' : ''}">
											<spring:message code="sidebar.company.dashboard12.${state.count}"/>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.treeviewTools ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-wrench i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard10"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="setting,recycle/posts,black-list,subscribes" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/company/tools/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewTools == state.count ? 'active' : ''}">
											<spring:message code="sidebar.company.dashboard10.${state.count}"/>
											<c:if test="${state.count == 4}">
												<span class="empty-trigger font-mini ${currentCompany.hasPremium() ? 'i-yellow' : ''}">
													<spring:message code="chose.subscribe.order${currentCompany.premium + 1}"/></span>
											</c:if>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<li class="treeview-empty treeview-help ${!empty requestScope.treeviewHelp ? 'active' : ''}">
						<a href="<c:url value="/company/help"/>" class="transition-35">
							<i class="cmsms-icon-help-circled-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard11"/></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/basics/sidebar_footer.jsp"/>
	</div>
</aside>