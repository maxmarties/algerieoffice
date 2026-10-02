<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<aside class="main-sidebar dashboard-sidebar">
	<c:import url="/WEB-INF/basics/brand_user.jsp"/>
	<div class="sidebar flexed flex-colone flex-jusitify">
		<div class="sidebar-body">
			<div class="sidebar-scroll">
				<ul class="sidebar-menu" data-widget="tree">
					<li class="treeview ${!empty requestScope.accountDashboard ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-home-2 i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard1"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="home,history" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/dashboard/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountDashboard == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard1.${state.count == 1 ? 1 : 5}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.accountGlobe ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-globe-4 i-22"></i><span class="treeview-header"><spring:message code="sidebar.user.dashboard1"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="companies,profiles" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/globe/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountGlobe == state.count ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard1.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.accountFavorite ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-star-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.user.dashboard2"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="posts,ads,events,jobs" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/favorite/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountFavorite == state.count ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard2.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.accountAlert ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-bell-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.user.dashboard4"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="posts,ads,events,jobs" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/alerts/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountAlert == state.count ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard4.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.accountEasylist ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-floppy i-22"></i><span class="treeview-header"><spring:message code="sidebar.user.dashboard9"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="companies,posts,ads,events,jobs" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/company-user/easylist/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountEasylist == state.count ? 'active' : ''}">
											<spring:message code="sidebar.user.dashboard9.${state.count}"/>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<li class="treeview ${!empty requestScope.accountCommunication ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-chat i-22"></i><span class="treeview-header"><spring:message code="sidebar.user.dashboard3"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="notices,appointments,evaluations,comments" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/communication/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountCommunication == state.count ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard3.${state.count}"/>
									</a>
								</li>
							</c:forEach>
							<sec:authorize access="hasAuthority('VISITOR_PRIVILEGE')">
								<li class="treeview-empty">
									<a href="<c:url value="/guest/communication/contributors"/>" class="transition-35 ${requestScope.accountCommunication == 5 ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard3.5"/>
									</a>
								</li>
							</sec:authorize>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.accountFeedback ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-megaphone-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.user.dashboard5"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="messages,notifications" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/feedback/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountFeedback == state.count ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard5.${state.count}"/>
									</a>
								</li>
							</c:forEach>
							<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
								<li class="treeview-empty">
									<a href="<c:url value="/company-user/feedback/communications"/>" class="transition-35 ${requestScope.accountFeedback == 3 ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard5.3"/>
									</a>
								</li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
								<li class="treeview-empty">
									<a href="<c:url value="/admin-user/feedback/supports"/>" class="transition-35 ${requestScope.accountFeedback == 4 ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard5.4"/>
									</a>
								</li>
							</sec:authorize>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.accountRepport ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-bug i-22"></i><span class="treeview-header"><spring:message code="sidebar.user.dashboard6"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="testimonial,assist,problem" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/repports/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountRepport == state.count ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard6.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.accountLogin ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-user-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard2"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="profile,coordinates,identities,login" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/account/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountLogin == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard2.${state.count}"/>
									</a>
								</li>
							</c:forEach>
							<sec:authorize access="!hasAuthority('SUPPORT_AUTOR_PRIVILEGE')">
								<li class="treeview-empty">
									<a href="<c:url value="/user/delete"/>" class="transition-35 ${requestScope.accountLogin == 5 ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard2.5"/>
									</a>
								</li>
							</sec:authorize>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.accountParams ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-cog-5 i-22"></i><span class="treeview-header"><spring:message code="sidebar.user.dashboard7"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="general,notifications,security,cookies,blacklist" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/user/settings/${pageScope.lien}"/>" class="transition-35 ${requestScope.accountParams == state.count ? 'active' : ''}">
										<spring:message code="sidebar.user.dashboard7.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview-empty treeview-help ${!empty requestScope.accountHelp ? 'active' : ''}">
						<a href="<c:url value="/user/account/help"/>" class="transition-35">
							<i class="cmsms-icon-help-circled-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.company.dashboard11"/></span>
						</a>
					</li>
				</ul>
				<ul class="sidebar-popup list-none list-block hidden-md-down" role="menu">
					<li class="sidebar-header text-uppercase"><spring:message code="tooltip.navcompany"/></li>
					<c:set var="liens" value="marketplace,marketplace/actualites,blog,contacts" scope="page"></c:set>
					<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
						<li class="sidebar-item">
							<a href="<c:url value="/${pageScope.lien}"/>" class="h-header lien" target="_blank">
								<spring:message code="sidebar.user.dashboard8.${state.count}"/><i class="cmsms-icon-link-ext i-11 m-l-10"></i>
							</a>
							<span class="help-text"><spring:message code="txt.help.sidebar${state.count}"/></span>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/basics/sidebar_footer.jsp"/>
	</div>
</aside>