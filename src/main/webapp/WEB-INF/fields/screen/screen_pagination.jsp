<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-pagination m-t-10" style="display:none;">
	<nav class="navbar">
		<ul class="nav nav-pagination ml-auto">
			<c:set var="providers" value="10,20,50,100" scope="page"></c:set>
			<li class="nav-text font-mini m-r-5"><spring:message code="tool.explorer.line" /> :</li>
			<li class="form-select">
				<select class="form-select2-simple" id="pagescreen" name="pagescreen">
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<option value="${state.count - 1}" ${state.count - 1 == currentConfig.defaultResult ? 'selected' : ''}><c:out value="${pageScope.provider}"/></option>
					</c:forEach>
				</select>
			</li>
		</ul>
		<ul class="nav nav-pagination m-l-10">
			<li class="nav-text font-mini m-r-10">
				<span id="paginationResultScreen"></span> <spring:message code="tool.pagination.div"/> <span class="countLine"></span>
			</li>
			<li>
				<a id="screenBack" class="btn btn-icon btn-simple" title="<spring:message code="tool.pagination.back"/>">
					<i class="cmsms-icon-${langage.lang == 'ar' ? 'right' : 'left'}-open"></i>
				</a>
			</li>
			<li>
				<a id="screenNext" class="btn btn-icon btn-simple" title="<spring:message code="tool.pagination.next"/>">
					<i class="cmsms-icon-${langage.lang == 'ar' ? 'left' : 'right'}-open"></i>
				</a>
			</li>
		</ul>
	</nav>
</div>
<input type="hidden" id="defaultResult" value="${pageScope.providers.split(',')[currentConfig.defaultResult]}" />