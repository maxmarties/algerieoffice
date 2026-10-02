<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-container">
	<div class="container">
		<ol class="bread-crumb bread-explorer">
			<li><a href="<c:url value="/"/>" class="lien"><i class="cmsms-icon-home"></i></a></li>
			<li><a href="<c:url value="/entreprises"/>" class="lien"><spring:message code="sidebar.admin.dashboard3"/></a></li>
			<li class="active"><c:out value="${explorerCompany.profile.tradename}"/></li>
		</ol>
		<div class="row row-mini">
			<div class="col-lg-8 col-mini m-t-10">
				<div class="explorer-column h-100">
					<div class="explorer-title">
						<h1 class="h-doc h-explorer h-explorer1">
							<spring:message code="explorer.subheader.presentation${explorerPage.briefcase.stateBriefcase()}"/> 
							<c:out value="${explorerCompany.profile.tradename}"/>
						</h1>
					</div>
					<div class="explorer-body">
						<c:choose>
							<c:when test="${empty explorerPage.presentation}">
								<div class="alert alert-info">
									<i class="cmsms-icon-info-circled-3 i-alert"></i>
									<p class="p-alert">
										<spring:message code="message.explorer.presentation"/><br>
										<a href="<c:url value="/company/overview/presentation"/>" 
											class="lien lien-primary lien-underline"><spring:message code="lien.help.presentation"/></a> 
									</p>
								</div>
							</c:when>
							<c:otherwise>
								<div class="fr-view fr-view-mask fr-view-preview fr-explorer"><c:out value="${explorerPage.presentation}" escapeXml="false" /></div>
								<hr class="my-2">
								<i class="cmsms-icon-explorer-angle m-r-10"></i>
								<a href="<c:url value="${explorerCurrent.companyURL}/presentation"/>" 
									class="lien lien-explorer-more h-header"><spring:message code="btn.explorer.read"/></a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-mini">
				<div class="row row-mini">
					<div class="col-md-6 col-lg-12 col-mini m-t-10">
						<c:choose>
							<c:when test="${explorerCurrent.premium == 4}"><c:import url="/WEB-INF/explorer/widgets/widget_location.jsp" /></c:when>
							<c:otherwise>
								<div class="explorer-column h-100">
									<div class="explorer-title">
										<h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.promote"/> ...</h2>
									</div>
									<div class="explorer-body">
										<div class="inbox-overview inbox-promote text-center" 
											style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
											<p><spring:message code="message.explorer.overview1"/></p>
										</div>
										<div class="m-t-10">
											<i class="cmsms-icon-explorer-angle m-r-10"></i>
											<a href="<c:url value="/company/marketplace/promotes/new"/>" 
												class="lien lien-explorer-primary lien-hover h-header"><spring:message code="txt.help.explorer4.1"/></a>
										</div>
										<hr class="my-1">
										<p class="font-mini">
											<spring:message code="txt.help.explorer1.2"/> 
											<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.more"/></a>
										</p>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-md-6 col-lg-12 col-mini m-t-10">
						<div class="explorer-column h-100">
							<div class="explorer-sponsore m-auto">
								<p class="font-small text-right text-help"><spring:message code="txt.help.explorer4.2"/></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row row-mini">
			<div class="col-lg-4 col-mini m-t-10">
				<div class="explorer-column h-100">
					<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="sidebar.company.dashboard6.2"/></h2></div>
					<div class="widget-body m-b-20">
						<c:import url="/WEB-INF/explorer/widgets/widget_briefcase.jsp" />
						<c:if test="${empty explorerPage.briefcase.briefcase}">
							<div class="alert alert-info m-t-10">
								<i class="cmsms-icon-info-circled-3 i-alert"></i>
								<p class="p-alert">
									<spring:message code="message.explorer.briefcase"/><br>
									<a href="<c:url value="/company/profile/briefcase"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.briefcase"/></a> 
								</p>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-mini m-t-10">
				<div class="explorer-column h-100">
					<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.meta"/></h2></div>
					<div class="explorer-body">
						<h3 class="h-header h-header6 text-input"><spring:message code="tool.explorer.description"/></h3>
						<p class="font-small m-t-5"><c:out value="${explorerPage.meta.description}"/></p>
						<hr class="my-4">
						<h3 class="h-header h-header6 text-input"><spring:message code="tabs.keys"/></h3>
						<c:choose>
							<c:when test="${empty explorerPage.meta.keysword}">
								<div class="alert alert-info m-t-10">
									<i class="cmsms-icon-info-circled-3 i-alert"></i>
									<p class="p-alert">
										<spring:message code="message.explorer.seo"/><br>
										<a href="<c:url value="/company/profile/linked"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.linked"/></a>
									</p>
								</div>
							</c:when>
							<c:otherwise>
								<ul class="list-keysword list-none">
									<c:forEach var="keyword" items="${explorerPage.meta.buildKeysword()}">
										<li>
											<i class="cmsms-icon-hash-1 text-segond i-tags m-r-5"></i>
											<a href="<c:url value="/recherche/entreprises?tag=${explorerPage.meta.parsKey(keyword)}" />" 
												class="lien lien-keyword lien-explorer-black"><c:out value="${keyword}" /></a>
										</li>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose>
						<hr class="my-4">
						<h3 class="h-header h-header6 text-input"><spring:message code="lbl.shedule"/></h3>
						<c:choose>
							<c:when test="${explorerCompany.shedule.isEmptyShedule()}">
								<div class="alert alert-info m-t-10">
									<i class="cmsms-icon-info-circled-3 i-alert"></i>
									<p class="p-alert">
										<spring:message code="message.explorer.shedule"/><br>
										<a href="<c:url value="/company/profile/contact"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.shedule"/></a> 
									</p>
								</div>
							</c:when>
							<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_shedule.jsp" /></c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-mini m-t-10">
				<div class="explorer-column h-100">
					<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.location"/></h2></div>
					<div class="explorer-body">
						<h3 class="h-header h-header6 text-input"><spring:message code="tool.explorer.location"/></h3>
						<c:choose>
							<c:when test="${empty explorerPage.linked.urlEmbded}">
								<div class="alert alert-info m-t-10">
									<i class="cmsms-icon-info-circled-3 i-alert"></i>
									<p class="p-alert">
										<spring:message code="message.explorer.location"/><br>
										<a href="<c:url value="/company/profile/location"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.location"/></a> 
									</p>
								</div>
							</c:when>
							<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_carte.jsp" /></c:otherwise>
						</c:choose>
						<hr class="my-4">
						<h3 class="h-header h-header6 text-input"><spring:message code="lbl.sub.linked2"/></h3>
						<c:choose>
							<c:when test="${explorerPage.linked.websiteNames.isEmpty()}">
								<div class="alert alert-info m-t-10">
									<i class="cmsms-icon-info-circled-3 i-alert"></i>
									<p class="p-alert">
										<spring:message code="message.explorer.linked"/><br>
										<a href="<c:url value="/company/profile/linked"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.linked"/></a> 
									</p>
								</div>
							</c:when>
							<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_linked.jsp" /></c:otherwise>
						</c:choose>
						<hr class="my-4">
						<h3 class="h-header h-header6 text-input"><spring:message code="lbl.sub.linked3"/></h3>
						<c:choose>
							<c:when test="${explorerPage.linked.isEmptySocialMedia()}">
								<div class="alert alert-info m-t-10">
									<i class="cmsms-icon-info-circled-3 i-alert"></i>
									<p class="p-alert">
										<spring:message code="message.explorer.social"/><br>
										<a href="<c:url value="/company/profile/linked"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.linked"/></a> 
									</p>
								</div>
							</c:when>
							<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_social.jsp" /></c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${explorerCompany.menu.hasSlider()}">
			<div class="row row-mini">
				<div class="col-lg-8 col-mini m-t-10">
					<c:choose>
						<c:when test="${explorerPage.inbox.slider.titles.isEmpty()}">
							<div class="explorer-column h-100">
								<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard4.4"/></h1></div>
								<div class="explorer-body">
									<div class="alert alert-info">
										<i class="cmsms-icon-info-circled-3 i-alert"></i>
										<p class="p-alert">
											<spring:message code="message.explorer.slider"/><br>
											<a href="<c:url value="/company/overview/slider"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.slider"/></a> 
										</p>
									</div>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="explorer-column explorer-slider h-100">
								<c:import url="/WEB-INF/explorer/widgets/widget_slider.jsp" />
								<hr class="m-t-10 m-b-10"><i class="cmsms-icon-explorer-angle m-r-10"></i>
								<a class="lien lien-explorer-more h-header iReport"><spring:message code="lien.explorer.image"/></a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="col-lg-4 col-mini m-t-10">
					<div class="explorer-column h-100">
						<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.actus"/></h2></div>
						<div class="explorer-body">
							<c:choose>
								<c:when test="${explorerPage.actualities.isEmpty()}">
									<div class="alert alert-info">
										<i class="cmsms-icon-info-circled-3 i-alert"></i>
										<p class="p-alert">
											<spring:message code="message.explorer.actus"/><br>
											<a href="<c:url value="/company/portfolio/actus/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.actus"/></a>
										</p>
									</div>
								</c:when>
								<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_actuality.jsp" /></c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${explorerCompany.menu.hasPost()}">
			<c:choose>
				<c:when test="${explorerPage.posts.size() < 3}">
					<div class="explorer-column m-t-10">
						<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard1"/></h1></div>
						<div class="explorer-body">
							<div class="alert alert-info">
								<i class="cmsms-icon-info-circled-3 i-alert"></i>
								<p class="p-alert">
									<spring:message code="message.explorer.post"/><br>
									<a href="<c:url value="/company/posts/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.post"/></a> 
								</p>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="explorer-auth-hr text-center m-t-20 m-b-10">
						<span class="line"></span><span class="h-header"><spring:message code="sidebar.company.dashboard1"/></span>
					</div>
					<div class="row row-mini explorer-product explorer-product${explorerCompany.menu.postStyle()}">
						<c:set var="maxPosts" value="${explorerPage.posts.size() < 6 ? 3 : 6}" scope="page"></c:set>
						<c:forEach var="post" items="${explorerPage.posts}" varStatus="state">
							<c:if test="${state.count <= pageScope.maxPosts}">
								<div class="col-md-6 col-lg-4 col-mini m-t-10">
									<c:set var="previewPost" value="${post}" scope="request"></c:set>
									<c:set var="companyURI" value="${explorerCurrent.companyURL}" scope="request"></c:set>
									<c:import url="/WEB-INF/explorer/widgets/widget_post.jsp" />
								</div>
							</c:if>
						</c:forEach>
					</div>
					<c:if test="${explorerPage.posts.size() >= 3}">
						<div class="text-center m-t-20 m-b-10">
							<a href="<c:url value="${explorerCurrent.companyURL}/produits-et-services"/>" 
								class="btn btn-explorer-more btn-simple btn-add btn-right">
								<span><i class="cmsms-icon-explorer-arrow transition-35"></i><spring:message code="btn.explorer.post"/></span>
							</a>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${explorerCompany.menu.hasTimeline()}">
			<div class="row row-mini">
				<div class="col-lg-8 col-mini m-t-10">
					<div class="explorer-column h-100">
						<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard4.5"/></h1></div>
						<div class="explorer-body">
							<c:choose>
								<c:when test="${empty explorerPage.inbox.timeline.history && explorerPage.inbox.timeline.titles.isEmpty()}">
									<div class="alert alert-info">
										<i class="cmsms-icon-info-circled-3 i-alert"></i>
										<p class="p-alert">
											<spring:message code="message.explorer.timeline"/><br>
											<a href="<c:url value="/company/overview/timeline"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.timeline"/></a> 
										</p>
									</div>
								</c:when>
								<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_timeline.jsp" /></c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<div class="col-lg-4 col-mini m-t-10">
					<div class="explorer-column flexed flex-colone flex-jusitify h-100">
						<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard7.3"/></h1></div>
						<div class="widget-flexed flexed flex-colone flex-jusitify">
							<div class="explorer-body">
								<div id="iExplorerAgents" class="explorer-loader">
									<c:import url="/WEB-INF/basics/loading_span.jsp"/>
									<ul class="list-none list-explorer-agent"></ul>
									<div class="table-pagination m-t-10" style="display:none;">
										<ul class="nav nav-pagination">
											<li class="nav-text font-mini ml-auto">
												<span id="paginationResult"></span> <spring:message code="tool.pagination.div"/> <span class="countAgent"></span>
											</li>
											<li class="m-l-10">
												<a id="agentExplorerBack" class="nav-link nav-icon transition-35" 
													title="<spring:message code="tool.pagination.back"/>"><i class="cmsms-icon-explorer-left"></i></a>
											</li>
											<li>
												<a id="agentExplorerNext" class="nav-link nav-icon transition-35" 
													title="<spring:message code="tool.pagination.next"/>"><i class="cmsms-icon-explorer-right"></i></a>
											</li>
										</ul>
									</div>
								</div>
							</div>
							<div>
								<c:if test="${empty currentUser || !currentUser.hasCompany()}">
									<div class="explorer-about text-center">
										<p class="font-small m-b-5"><spring:message code="txt.help.explorer1.4"/></p>
										<a class="btn btn-explorer-segond btn-fixed disabled"><span><spring:message code="btn.explorer.agent"/></span></a>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<c:if test="${explorerCompany.menu.hasThink()}">
			<c:choose>
				<c:when test="${!explorerPage.inbox.think.hasPresent()}">
					<div class="explorer-column m-t-10">
						<div class="explorer-title">
							<h1 class="h-doc h-explorer h-explorer1"><c:out value="${explorerCompany.profile.tradename}"/> <spring:message code="sidebar.company.dashboard4.7"/></h1>
						</div>
						<div class="explorer-body">
							<div class="alert alert-info">
								<i class="cmsms-icon-info-circled-3 i-alert"></i>
								<p class="p-alert">
									<spring:message code="message.explorer.think"/><br>
									<a href="<c:url value="/company/overview/think"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.think"/></a> 
								</p>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="explorer-auth-hr text-center m-t-20 m-b-10">
						<span class="line"></span><span class="h-header"><c:out value="${explorerCompany.profile.tradename}"/> <spring:message code="explorer.subheader.think"/></span>
					</div>
					<c:import url="/WEB-INF/explorer/widgets/widget_think.jsp" />
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${explorerCompany.menu.hasWork()}">
			<c:choose>
				<c:when test="${explorerPage.works.size() != 3}">
					<div class="explorer-column m-t-10">
						<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard3.1"/></h1></div>
						<div class="explorer-body">
							<div class="alert alert-info">
								<i class="cmsms-icon-info-circled-3 i-alert"></i>
								<p class="p-alert">
									<spring:message code="message.explorer.work"/><br>
									<a href="<c:url value="/company/portfolio/works/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.works"/></a> 
								</p>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="explorer-auth-hr text-center m-t-20 m-b-10">
						<span class="line"></span><span class="h-header"><spring:message code="sidebar.company.dashboard3.1"/></span>
					</div>
					<div class="row row-mini">
						<c:forEach var="work" items="${explorerPage.works}" varStatus="state">
							<div class="col-md-6 col-lg-4 col-mini m-t-10 ${state.count == 3 ? 'hidden-md-center' : ''}">
								<c:set var="previewWork" value="${work}" scope="request"></c:set>
								<c:import url="/WEB-INF/explorer/widgets/widget_work.jsp" />
							</div>
						</c:forEach>
					</div>
					<div class="text-center m-t-20 m-b-10">
						<a href="<c:url value="${explorerCurrent.companyURL}/realisations"/>" 
							class="btn btn-explorer-more btn-simple btn-add btn-right">
							<span><i class="cmsms-icon-explorer-arrow transition-35"></i><spring:message code="btn.explorer.work"/></span>
						</a>
					</div>
				</c:otherwise>
			</c:choose>
		</c:if>
		<div class="row row-mini">
			<div class="col-lg-8 col-mini m-t-10">
				<div class="explorer-column h-100">
					<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="explorer.subheader.notice"/></h1></div>
					<div class="explorer-body">
						<div class="inbox-overview inbox-comment text-center" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
							<p class="m-t-40"><spring:message code="message.explorer.overview2"/></p>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-mini m-t-10">
				<div class="explorer-column h-100">
					<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.evaluation"/></h2></div>
					<div class="explorer-body">
						<div class="inbox-overview inbox-comment text-center" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
							<p class="m-t-30"><spring:message code="message.explorer.overview3"/></p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${explorerPage.partners.size() >= 3}">
			<div class="explorer-column m-t-10">
				<div class="explorer-title">
					<h1 class="h-doc h-explorer h-explorer1">
						<spring:message code="explorer.subheader.partner${explorerPage.briefcase.stateBriefcase()}"/> 
						<c:out value="${explorerCompany.profile.tradename}"/>
					</h1>
				</div>
				<div id="partnerExplorer" class="explorer-body explorer-partner">
					<div class="owl-carousel owl-theme">
						<c:forEach var="partner" items="${explorerPage.partners}">
							<div class="item m-auto">
								<a href="<c:url value="${partner.url}"/>" target="_blank" title="<c:out value="${partner.name}" />">
									<img class="img-responsive" src="<c:url value="${partner.urlAvatar}"/>" alt="<c:out value="${partner.name}" />">
								</a>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
		<div class="explorer-column m-t-10">
			<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="explorer.subheader.simult"/></h1></div>
			<div class="explorer-body">
				<div class="inbox-overview inbox-sector text-center" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
					<p><spring:message code="txt.help.explorer1.5"/> <spring:message code="chose.wilaya${explorerCompany.profile.wilaya}"/></p>
				</div>
				<hr class="my-1">
				<p class="font-mini">
					<spring:message code="txt.help.explorer1.6"/> 
					<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.more"/></a>
				</p>
			</div>
		</div>
	</div>
</div>