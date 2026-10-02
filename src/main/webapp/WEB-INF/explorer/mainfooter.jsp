<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<footer class="explorer-footer">
	<a class="btn btn-explorer-segond btn-totop btn-simple" href="#page-top"><span><i class="cmsms-icon-up-open"></i></span></a>
	<div class="explorer-up">
		<div class="container">
			<div class="row h-header">
				<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
					<h3 class="h-doc h-footer"><spring:message code="header.explorer.footer1"/></h3>
					<div class="widget-footer">
						<p class="font-big"><c:out value="${explorerCompany.profile.denomination}"/></p>
						<p class="font-small m-t-10" style="line-height:14px;"><c:out value="${explorerCompany.footer.tageline}"/></p>
					</div>
				</div>
				<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
					<h3 class="h-doc h-footer"><spring:message code="header.explorer.footer2"/></h3>
					<div class="widget-footer">
						<ul class="list-none list-explorer-footer list-block font-small">
							<c:set var="mainfooter" value="${explorerCompany.menu.mainfooter()}" scope="page"></c:set>
							<c:set var="liens" value="produits-et-services,presentation,actualites,evenements,faqs,offres-emploi,contact" scope="page"></c:set>
							<c:forEach var="lien" items="${pageScope.liens}" varStatus="state">
								<c:if test="${pageScope.mainfooter[state.count - 1]}">
									<li>
										<i class="cmsms-icon-explorer-angle m-r-10"></i>
										<a href="<c:url value="${explorerCurrent.companyURL}/${pageScope.lien}"/>" class="lien">
											<spring:message code="explorer.mainmenu6.${state.count}"/>
										</a>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
					<h3 class="h-doc h-footer"><spring:message code="lbl.shedule"/></h3>
					<div class="widget-footer">
						<c:choose>
							<c:when test="${explorerCompany.shedule.hasPresentShedule()}">
								<ul class="list-none list-explorer-footer list-block font-small">
									<c:forEach var="i" begin="1" end="7" step="1">
										<c:if test="${explorerCompany.shedule.stateday[i - 1] != 0}">
											<li>
												<span class="day-footer"><spring:message code="chose.day${i}" /></span>
												<c:out value="${explorerCompany.shedule.getFormattedDay(i - 1)}" />
											</li>
										</c:if>
									</c:forEach>
								</ul>		
							</c:when>
							<c:otherwise><p class="font-small"><spring:message code="txt.help.explorer4.4"/></p></c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="col-sm-6 col-lg-3 m-t-10 m-b-10">
					<h3 class="h-doc h-footer"><spring:message code="header.explorer.footer${explorerCompany.menu.hasActusFooter() ? '3' : '4'}"/></h3>
					<div class="widget-footer">
						<ul class="list-none list-explorer-footer list-block font-small">
							<c:choose>
								<c:when test="${explorerCompany.menu.hasActusFooter()}">
									<c:choose>
										<c:when test="${!explorerCompany.footer.actus.isEmpty()}">
											<c:forEach var="actu" items="${explorerCompany.footer.actus}">
												<li>
													<div class="actu-footer background-container pull-left" style="background-image: url('${actu.photoURL}');"></div>
													<div class="actu-brand">
														<p><c:out value="${actu.title}"/></p>
														<span class="font-mini"><joda:format value="${actu.actuDate}" pattern="dd MMMM yyyy"></joda:format></span>
													</div>
													<span class="clearfix"></span>
												</li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<li>
												<div class="actu-footer background-container pull-left" style="background-image: url('${explorerCompany.header.urlLogo}');"></div>
												<div class="actu-brand">
													<p>
														<span class="text-white"><c:out value="${explorerCompany.profile.tradename}"/></span> 
														<spring:message code="txt.help.explorer4.3.2"/>
													</p>
													<span class="font-mini"><c:out value="${explorerCompany.header.updateDate}" /></span>
												</div>
												<span class="clearfix"></span>
											</li>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${explorerCompany.profile.addrs.isEmpty()}">
											<li>
												<i class="cmsms-icon-commerical-building pull-left"></i>
												<div class="explorer-brand">
													<c:out value="${explorerCompany.profile.address}" />, <c:out value="${explorerCompany.profile.postal}" /><br>
													<span class="text-uppercase"><spring:message code="chose.wilaya${explorerCompany.profile.wilaya}"/></span>
												</div> 
												<span class="clearfix"></span>
											</li>
										</c:when>
										<c:otherwise>
											<c:forEach var="addr" items="${explorerCompany.profile.addrs}" varStatus="state">
												<c:if test="${state.count <= 3}">
													<li>
														<i class="cmsms-icon-building pull-left"></i>
														<div class="explorer-brand">
															<c:out value="${addr}" />, <c:out value="${explorerCompany.profile.postals.get(state.count - 1)}" /><br>
															<span class="text-uppercase">
																<spring:message code="chose.wilaya${explorerCompany.profile.wilayas.get(state.count - 1)}"/>
															</span>
														</div> 
														<span class="clearfix"></span>
													</li>
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</div>
			<div id="iExplorerBlogs"></div>
			<c:import url="/WEB-INF/basics/footer_brand.jsp"/>
		</div>
	</div>
	<c:import url="/WEB-INF/basics/footer_copyright.jsp"/>
</footer>
<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
	<c:if test="${!empty explorerCompany.messenger && explorerCompany.messenger.userId != currentUser.getUserId()}">
		<a class="btn btn-explorer-segond to-messenger" title="<spring:message code="tooltip.messenger"/>" data-id="${explorerCompany.messenger.userId}" 
			data-avatar="${explorerCompany.messenger.avatarURL}" data-name="${explorerCompany.messenger.username}"><span><i class="cmsms-icon-comment-5"></i></span></a>
	</c:if>
</sec:authorize>