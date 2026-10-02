<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-sidebar h-header">
	<a id="cleardesktop" class="sidebar-header bn-explorer-primary" data-name="<spring:message code="explorer.desktop.element7.1"/>"><spring:message code="explorer.mainmenu4"/></a>
	<ul class="sidebar-mainmenu list-none" data-widget="bread">
		<c:set var="countAnnonces" value="${explorerPage.countAnnonces()}" scope="page"></c:set>
		<c:choose>
			<c:when test="${countAnnonces == 0}">
				<li class="breadview-item active" data-uuid="" data-name="<spring:message code="explorer.desktop.element7.1"/>">
					<a class="transition-color"><spring:message code="sidebar.company.dashboard2.2"/><small class="mini m-l-5">(<c:out value="0"/>)</small></a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="breadview ${!empty typeMarket ? 'menu-open' : ''}">
					<a class="transition-color">
						<spring:message code="sidebar.company.dashboard2.2"/><small class="mini m-l-5">(<c:out value="${countAnnonces}"/>)</small>
						<i class="breadview-plus cmsms-icon-angle-down"></i>
					</a>
					<ul class="breadview-menu list-none">
						<c:forEach var="i" begin="1" end="5" step="1">
							<li class="breadview-item ${typeMarket == i ? 'active' : ''}" data-uuid="${i}" data-name="<spring:message code="chose.annonce${i}"/>">
								<a class="transition-color"><spring:message code="chose.annonce${i}"/><small class="mini m-l-5">(<c:out value="${explorerPage.countsMarket[i - 1]}"/>)</small></a>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:otherwise>
		</c:choose>
		<li>
			<a href="<c:url value="${explorerCurrent.companyURL}/offres-emploi"/>" class="transition-color">
				<spring:message code="sidebar.company.dashboard2.3"/><small class="mini m-l-5">(<c:out value="${explorerPage.countsMarket[5]}"/>)</small>
			</a>
		</li>
	</ul>
</div>
<c:if test="${!empty typeMarket}">
	<input type="hidden" id="typeMarketExplorer" value="${typeMarket}" />
	<input type="hidden" id="nameMarketExplorer" value="<spring:message code="chose.annonce${typeMarket}"/>" />
</c:if>