<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
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
								<p class="explorer-alert"><i class="cmsms-icon-info-circled-alt"></i><spring:message code="message.browser.presentation"/></p>
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
									<div id="iExplorerPrm" class="explorer-loader"><c:import url="/WEB-INF/basics/loading_span.jsp"/></div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-md-6 col-lg-12 col-mini m-t-10">
						<div class="explorer-column h-100">
							<div id="iExplorerSpn" class="explorer-sponsore">
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
					<div class="widget-body m-b-20"><c:import url="/WEB-INF/explorer/widgets/widget_briefcase.jsp" /></div>
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
							<c:when test="${empty explorerPage.meta.keysword}"><c:out value="--"/></c:when>
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
								<p class="explorer-alert m-t-10"><i class="cmsms-icon-info-circled-alt"></i><spring:message code="message.browser.shedule"/></p>
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
								<p class="font-small m-t-5">
									<c:out value="${explorerCompany.profile.address}" /><br><c:out value="${explorerCompany.profile.postal}" /> 
									<span class="text-uppercase"><spring:message code="chose.wilaya${explorerCompany.profile.wilaya}"/></span>
								</p>
							</c:when>
							<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_carte.jsp" /></c:otherwise>
						</c:choose>
						<hr class="my-4">
						<h3 class="h-header h-header6 text-input"><spring:message code="lbl.sub.linked2"/></h3>
						<c:choose>
							<c:when test="${explorerPage.linked.websiteNames.isEmpty()}">
								<p class="explorer-alert m-t-10"><i class="cmsms-icon-info-circled-alt"></i><spring:message code="message.browser.linked"/></p>
							</c:when>
							<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_linked.jsp" /></c:otherwise>
						</c:choose>
						<c:if test="${!explorerPage.linked.isEmptySocialMedia()}">
							<hr class="my-4">
							<h3 class="h-header h-header6 text-input"><spring:message code="lbl.sub.linked3"/></h3>
							<c:import url="/WEB-INF/explorer/widgets/widget_social.jsp" />
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${explorerCompany.menu.hasSlider() && !explorerPage.inbox.slider.titles.isEmpty()}">
			<div class="row row-mini">
				<div class="col-lg-8 col-mini m-t-10">
					<div class="explorer-column explorer-slider h-100">
						<c:import url="/WEB-INF/explorer/widgets/widget_slider.jsp" />
						<hr class="m-t-10 m-b-10"><i class="cmsms-icon-explorer-angle m-r-10"></i>
						<a class="lien lien-explorer-more h-header iReport"><spring:message code="lien.explorer.image"/></a>
					</div>
				</div>
				<div class="col-lg-4 col-mini m-t-10">
					<div class="explorer-column h-100">
						<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.actus"/></h2></div>
						<div class="explorer-body">
							<c:choose>
								<c:when test="${explorerPage.actualities.isEmpty()}"><p class="m-t-10"><spring:message code="message.browser.actus"/></p></c:when>
								<c:otherwise><c:import url="/WEB-INF/explorer/widgets/widget_actuality.jsp" /></c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
			</div>	
		</c:if>
		<c:if test="${explorerCompany.menu.hasPost() && explorerPage.posts.size() >= 3}">
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
		</c:if>
		<c:if test="${explorerCompany.menu.hasTimeline()}">
			<div class="row row-mini">
				<div class="col-lg-8 col-mini m-t-10">
					<div class="explorer-column h-100">
						<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="sidebar.company.dashboard4.5"/></h1></div>
						<div class="explorer-body">
							<c:choose>
								<c:when test="${empty explorerPage.inbox.timeline.history && explorerPage.inbox.timeline.titles.isEmpty()}">
									<p class="explorer-alert"><i class="cmsms-icon-info-circled-alt"></i><spring:message code="message.browser.timeline"/></p>
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
										<a class="btn btn-explorer-segond btn-fixed iAgent"><span><spring:message code="btn.explorer.agent"/></span></a>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:if>
		<sec:authorize access="isAnonymous()">
			<div class="row row-mini">
				<div class="col-lg-8 col-mini m-t-10"><c:import url="/WEB-INF/explorer/banners/banner_anonym.jsp" /></div>
				<div class="col-lg-4 col-mini hidden-md-down m-t-10">
					<div class="explorer-column h-100">
						<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.anonym"/></h2></div>
						<div class="explorer-anonym">
							<c:choose>
								<c:when test="${!empty explorerPage.meta.urlOverview}">
									<div class="background-container" style="background-image: url('<c:url value="${explorerPage.meta.urlOverview}" />');"></div>
								</c:when>
								<c:otherwise>
									<div class="background-container" style="background-image: url('<c:url value="${explorerCompany.header.urlLogo}" />');"></div>
								</c:otherwise>
							</c:choose>
							<div class="widget-body">
								<p class="m-t-20"><spring:message code="txt.help.explorer4.4.1"/></p>
								<hr class="my-1">
								<div class="text-center m-t-20 m-b-10">
									<a class="btn btn-explorer-segond btn-add btn-left iFavorite">
										<span><i class="cmsms-icon-star-1"></i><spring:message code="tool.navigate.company2"/></span>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</sec:authorize>
		<c:if test="${explorerCompany.menu.hasThink() && explorerPage.inbox.think.hasPresent()}">
			<div class="explorer-auth-hr text-center m-t-20 m-b-10">
				<span class="line"></span><span class="h-header"><c:out value="${explorerCompany.profile.tradename}"/> <spring:message code="explorer.subheader.think"/></span>
			</div>
			<c:import url="/WEB-INF/explorer/widgets/widget_think.jsp" />
		</c:if>
		<c:if test="${explorerCompany.menu.hasWork() && explorerPage.works.size() == 3}">
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
		</c:if>
		<div class="row row-mini">
			<div class="col-lg-8 col-mini m-t-10">
				<div class="explorer-column flexed flex-colone flex-jusitify h-100">
					<div class="explorer-title"><h1 class="h-doc h-explorer h-explorer1"><spring:message code="explorer.subheader.notice"/></h1></div>
					<div class="widget-flexed flexed flex-colone flex-jusitify">
						<div class="explorer-body">
							<h2 class="h-header h-header6 text-input">
								<spring:message code="tool.explorer.notice"/> <c:out value="${explorerCompany.profile.tradename}"/>
							</h2>
							<div id="iExplorerNotices" class="explorer-loader m-t-10">
								<c:import url="/WEB-INF/basics/loading_span.jsp"/>
								<ul class="list-none list-explorer-notice"></ul>
								<div class="table-pagination m-t-10" style="display:none;">
									<ul class="nav nav-pagination">
										<li class="nav-text font-mini ml-auto">
											<span id="paginationNoticeResult"></span> <spring:message code="tool.pagination.div"/> <span class="countNotices"></span>
										</li>
										<li class="m-l-10">
											<a id="noticeExplorerBack" class="nav-link nav-icon transition-35" 
												title="<spring:message code="tool.pagination.back"/>"><i class="cmsms-icon-explorer-left"></i></a>
										</li>
										<li>
											<a id="noticeExplorerNext" class="nav-link nav-icon transition-35" 
												title="<spring:message code="tool.pagination.next"/>"><i class="cmsms-icon-explorer-right"></i></a>
										</li>
									</ul>
								</div>
							</div>
						</div>
						<div class="explorer-about text-center">
							<a class="btn btn-explorer-segond btn-add btn-left iNotice">
								<span><i class="cmsms-icon-comment"></i><spring:message code="btn.explorer.notice"/> <c:out value="${explorerCompany.profile.tradename}"/></span>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="col-lg-4 col-mini m-t-10">
				<div class="explorer-column flexed flex-colone flex-jusitify h-100">
					<div class="explorer-title"><h2 class="h-doc h-explorer h-explorer2"><spring:message code="explorer.subheader.evaluation"/></h2></div>
					<div class="widget-flexed flexed flex-colone flex-jusitify">
						<div class="explorer-body">
							<h3 class="h-header h-header6 text-input"><spring:message code="tool.explorer.evaluation"/></h3>
							<c:choose>
								<c:when test="${empty explorerCompany.header.evaluation}">
									<p class="text-empty text-center text-help"><spring:message code="message.browser.evaluation"/></p>
								</c:when>
								<c:otherwise>
									<div class="circle-explorer m-t-20">
										<div id="circleEvaluation" class="circle-3d m-auto" data-counter="${explorerCompany.header.averageLiked()}">
											<div class="circle-content text-center">
												<i class="cmsms-icon-thumbs-up i-28"></i>
												<span class="block font-small"><spring:message code="txt.help.explorer2.2.1"/></span>
												<span class="h-header"><c:out value="${explorerCompany.header.averageLiked()}%"/></span>
											</div>
											<div id="circleCanvas" class="circle-canvas"></div>
										</div>
										<div class="explorer-brand text-center">
											<p class="font-small">
												<spring:message code="txt.help.explorer2.2.2" 
													arguments="${explorerCompany.header.countLiked},${explorerCompany.header.countEvaluation}" /> 
												<strong><c:out value="${explorerCompany.profile.tradename}"/></strong> <spring:message code="txt.help.explorer2.2.3"/>
											</p>
											<hr class="my-2">
											<ul class="list-none list-evaluation">
												<c:set var="countStar" value="${explorerCompany.header.evaluation / 2}" scope="page"></c:set>
												<c:forEach var="i" begin="1" end="${pageScope.countStar}" step="1">
													<li class="text-select"><i class="cmsms-icon-star-1"></i></li>
												</c:forEach>
												<c:if test="${explorerCompany.header.evaluation % 2 != 0}">
													<li class="text-select"><i class="cmsms-icon-star-half"></i></li>
												</c:if>
											</ul>
											<p class="font-small m-t-10">
												<spring:message code="txt.help.explorer2.2.4" arguments="${explorerCompany.header.countEvaluation}" /> 
												<span class="font-mini text-help">
													<spring:message code="txt.help.explorer2.2.5" arguments="${explorerCompany.header.evaluation}" />
												</span>
											</p>
										</div>
									</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="explorer-about text-center">
							<p class="font-small m-b-10"><spring:message code="txt.help.explorer2.3.1"/></p>
							<a class="btn btn-explorer-segond btn-add btn-left iRate">
								<span><i class="cmsms-icon-thumbs-up-alt"></i><spring:message code="btn.explorer.evaluation"/></span>
							</a>
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
		<c:if test="${explorerCurrent.premium < 3}"><div id="iExplorerSimultudes"></div></c:if>
	</div>
</div>