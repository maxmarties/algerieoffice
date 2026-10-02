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
							<c:set var="liens" value="home,analytic,journal" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/dashboard/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewDashboard == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard1.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewCompany ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-building-filled i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard3"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="all,profiles,features,customers" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/companies/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewCompany == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard3.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewMarketplace ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-pin-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard4"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="promotes,orders,sponsores,campaigns,audiances,ads,jobs" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/marketplace/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewMarketplace == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard4.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewBlog ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-quote-right i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard5"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="all,new,stats,autors" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/blog/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewBlog == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard5.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewData ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-folder i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard6"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<li class="treeview-empty">
								<a href="<c:url value="/admin/data/activities"/>" class="lien ${requestScope.treeviewData == 1 ? 'active' : ''}">
									<spring:message code="sidebar.admin.dashboard6.1"/>
								</a>
							</li>
							<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
								<c:set var="liens" value="identities,postal,social" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/admin/data/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewData == state.count + 1 ? 'active' : ''}">
											<spring:message code="sidebar.admin.dashboard6.${state.count + 1}"/>
										</a>
									</li>
								</c:forEach>
							</sec:authorize>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewFeedback ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-umbrella i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard7"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="reports,rates,locks,blocks" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/feedback/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewFeedback == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard7.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewPremium ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-bookmark i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard8"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="subscribes,orders" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/premium/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewPremium == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard8.${state.count}"/>
									</a>
								</li>
							</c:forEach>
							<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
								<c:set var="liens" value="formule,emailings" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/admin/premium/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewPremium == state.count + 2 ? 'active' : ''}">
											<spring:message code="sidebar.admin.dashboard8.${state.count + 2}"/>
										</a>
									</li>
								</c:forEach>
							</sec:authorize>
							<li class="treeview-empty">
								<a href="<c:url value="/admin/premium/budgets"/>" class="transition-35 ${requestScope.treeviewPremium == 5 ? 'active' : ''}">
									<spring:message code="sidebar.admin.dashboard8.5"/>
								</a>
							</li>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewRealtime ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-flag i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard9"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="companies,users,testimonials,assists,problems,contacts,chatbots" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/realtime/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewRealtime == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard9.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
					<li class="treeview ${!empty requestScope.treeviewTeam ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-user-2 i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard10"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="users,moderators" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/team/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewTeam == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard10.${state.count}"/>
									</a>
								</li>
							</c:forEach>
							<sec:authorize access="hasAuthority('SUPPORT_ADMIN_PRIVILEGE')">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/team/managers"/>" class="transition-35 ${requestScope.treeviewTeam == 3 ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard10.3"/>
									</a>
								</li>
							</sec:authorize>
						</ul>
					</li>
					<sec:authorize access="hasAuthority('SUPPORT_ADMIN_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.treeviewCommunication ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-comment-alt-1 i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard11"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="chater,message,contact,appoint,notice,guest,newsletter,chatbot" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/admin/communication/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewCommunication == state.count ? 'active' : ''}">
											<spring:message code="sidebar.admin.dashboard11.${state.count}"/>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
						<li class="treeview ${!empty requestScope.treeviewMailing ? 'active' : ''}">
							<a class="lien">
								<i class="cmsms-icon-email i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard12"/></span>
								<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
							</a>
							<ul class="treeview-menu">
								<c:set var="liens" value="newsletter,marketplace,actualities" scope="page"></c:set>
								<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
									<li class="treeview-empty">
										<a href="<c:url value="/admin/mailing/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewMailing == state.count ? 'active' : ''}">
											<spring:message code="sidebar.admin.dashboard12.${state.count}"/>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</sec:authorize>
					<li class="treeview ${!empty requestScope.treeviewEasylist ? 'active' : ''}">
						<a class="lien">
							<i class="cmsms-icon-floppy i-22"></i><span class="treeview-header"><spring:message code="sidebar.admin.dashboard13"/></span>
							<i class="treeview-trigger cmsms-icon-explorer-aside"></i>
						</a>
						<ul class="treeview-menu">
							<c:set var="liens" value="companies,documents" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<li class="treeview-empty">
									<a href="<c:url value="/admin/easylist/${pageScope.lien}"/>" class="transition-35 ${requestScope.treeviewEasylist == state.count ? 'active' : ''}">
										<spring:message code="sidebar.admin.dashboard13.${state.count}"/>
									</a>
								</li>
							</c:forEach>
						</ul>
					</li>
				</ul>
			</div>
		</div>
		<c:import url="/WEB-INF/basics/sidebar_footer.jsp"/>
	</div>
</aside>