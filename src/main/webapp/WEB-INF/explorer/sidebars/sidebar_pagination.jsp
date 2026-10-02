<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-pagination" style="display:none;">
	<nav class="navbar">
		<ul class="nav nav-pagination ml-auto">
			<c:set var="providers" value="10,20,50,100" scope="page"></c:set>
			<li class="nav-text font-mini m-r-5"><spring:message code="tool.explorer.line" /> :</li>
			<li class="form-select">
				<select class="form-select2-simple" id="pagedesktop" name="pagedesktop">
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<option value="${state.count}" ${state.count == 2 ? 'selected' : ''}><c:out value="${pageScope.provider}"/></option>
					</c:forEach>
				</select>
			</li>
		</ul>
		<ul class="nav nav-pagination m-l-10">
			<li class="nav-text font-mini m-r-5">
				<span id="paginationResultDesktop"></span> <spring:message code="tool.pagination.div"/> <span class="countLine"></span>
			</li>
			<li>
				<a id="desktopBack" class="nav-link nav-icon transition-35" title="<spring:message code="tool.pagination.back"/>">
					<i class="cmsms-icon-${explorerCompany.profile.language == 'ar' ? 'right' : 'left'}-open"></i>
				</a>
			</li>
			<li>
				<a id="desktopNext" class="nav-link nav-icon transition-35" title="<spring:message code="tool.pagination.next"/>">
					<i class="cmsms-icon-${explorerCompany.profile.language == 'ar' ? 'left' : 'right'}-open"></i>
				</a>
			</li>
		</ul>
	</nav>
</div>