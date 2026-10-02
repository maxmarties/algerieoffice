<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="table-responsive">
	<table class="table table-explorer table-first">
		<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
		<tbody class="font-mini">
			<tr><td><spring:message code="lbl.denomination" /></td><td><c:out value="${explorerCompany.profile.denomination}"/></td></tr>
			<tr><td><spring:message code="tabs.tradename" /></td><td><c:out value="${explorerCompany.profile.tradename}"/></td></tr>
			<tr><td><spring:message code="tabs.buildate" /></td><td><c:out value="${explorerCompany.profile.buildDate}"/></td></tr>
			<c:if test="${!empty explorerPage.briefcase.briefcase}">
				<tr><td><spring:message code="lbl.sub.briefcase1" /></td><td><spring:message code="overview.briefcase1.${explorerPage.briefcase.briefcase}" /></td></tr>
			</c:if>
			<tr><td><spring:message code="lbl.sub.briefcase2" /></td><td><spring:message code="chose.briefcase2.${explorerPage.briefcase.warehouse}" /></td></tr>
			<c:if test="${explorerPage.briefcase.capital != '-'}">
				<tr><td><spring:message code="lbl.sub.briefcase3" /></td><td><c:out value="${explorerPage.briefcase.capital}"/> <spring:message code="tool.ind.capital" /></td></tr>
			</c:if>
			<c:if test="${!empty explorerPage.briefcase.type}">
				<tr><td><spring:message code="lbl.sub.briefcase4.2" /></td><td><spring:message code="overview.briefcase3.${explorerPage.briefcase.type}" /></td></tr>
			</c:if>
			<c:if test="${explorerPage.briefcase.nrc != '-'}">
				<tr><td><spring:message code="lbl.sub.briefcase5" /></td><td><c:out value="${explorerPage.briefcase.nrc}"/></td></tr>
			</c:if>
			<c:if test="${explorerPage.briefcase.nif != '-'}">
				<tr><td><spring:message code="lbl.sub.briefcase6" /></td><td><c:out value="${explorerPage.briefcase.nif}"/></td></tr>
			</c:if>
			<c:if test="${explorerPage.briefcase.nis != '-'}">
				<tr><td><spring:message code="lbl.sub.briefcase7" /></td><td><c:out value="${explorerPage.briefcase.nis}"/></td></tr>
			</c:if>
			<tr>
				<td><spring:message code="lbl.sub.briefcase9" /></td>
				<td>
					<ul class="list-none">
						<c:forEach var="code" items="${explorerCompany.profile.activities}">
							<li class="m-b-5"><c:out value="${code}"/> <spring:message code="chose.activity.${code}" /></li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			<tr>
				<td><spring:message code="tool.navigate.company1" /></td>
				<td id="phoneExplorerOverview">
					<a id="phoneExplorerPreview" class="lien lien-explorer-primary lien-hover font-bold"><spring:message code="lien.explorer.phone"/></a>
				</td>
			</tr>
			<c:if test="${!explorerPage.briefcase.labels.isEmpty()}">
				<c:forEach var="label" items="${explorerPage.briefcase.labels}" varStatus="state">
					<tr class="tr-briefcase animated onne fadeIn" style="display:none;">
						<td><c:out value="${label}" /></td><td><c:out value="${explorerPage.briefcase.infos.get(state.count - 1)}" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>
<c:if test="${!explorerPage.briefcase.labels.isEmpty()}">
	<div class="text-center m-t-20">
		<a id="briefcaseExplorerPreview" class="btn btn-explorer-segond btn-simple btn-add btn-left">
			<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.briefcase"/></span>
		</a>
	</div>
</c:if>