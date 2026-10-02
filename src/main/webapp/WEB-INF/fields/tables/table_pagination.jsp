<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="table-pagination m-t-10" style="display:none;">
	<div class="table-overlay"></div>
	<nav class="navbar">
		<ul class="nav nav-pagination ml-auto">
			<c:set var="providers" value="10,25,50,100,200" scope="page"></c:set>
			<li class="nav-text font-mini m-r-5"><spring:message code="tool.pagination.line"/> :</li>
			<li class="form-select">
				<select class="form-select2-simple" id="selectRows" name="selectRows">
					<option></option>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<option value="${state.count - 1}" ${state.count - 1 == currentConfig.defaultRow ? 'selected' : ''}><c:out value="${pageScope.provider}"/></option>
					</c:forEach>
				</select>
			</li>
		</ul>
		<ul class="nav nav-pagination m-l-10">
			<li class="nav-text font-mini m-r-5">
				<span id="paginationResult"></span> <spring:message code="tool.pagination.div"/> <span class="countLine"></span>
			</li>
			<li>
				<a id="paginationBack" class="nav-link nav-icon transition-35" title="<spring:message code="tool.pagination.back"/>">
					<i class="cmsms-icon-${langage.clazz == 'fr' ? 'left' : 'right'}-open"></i>
				</a>
			</li>
			<li>
				<a id="paginationNext" class="nav-link nav-icon transition-35" title="<spring:message code="tool.pagination.next"/>">
					<i class="cmsms-icon-${langage.clazz == 'fr' ? 'right' : 'left'}-open"></i>
				</a>
			</li>
		</ul>
	</nav>
</div>
<input type="hidden" id="defaultLine" value="${pageScope.providers.split(',')[currentConfig.defaultRow]}" />