<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<li class="dropdown brand-menu brand-fixed brand-table">
	<a id="columnDropdown" class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.table.config"/>">
		<i class="cmsms-icon-cog"></i>
	</a>
	<ul class="dropdown-menu dropdown-menu-right animated slideInY" role="menu">
		<form:form name="columnTableForm" action="/" method="POST" modelAttribute="currTable" enctype="utf8" novalidate="novalidate">
			<spring:bind path="cookieName"><form:input type="hidden" path="cookieName" /></spring:bind>
			<li class="m-t-10">
				<div class="form-group m-b-0">
					<ul class="list-none">
						<c:forEach var="choseColumn" items="${requestScope.choseColumns}" varStatus="state">
							<li class="dropdown-checkbox">
								<spring:bind path="column[${state.count - 1}]">
									<label class="ui-checkbox ui-checkbox-segond font-small ${state.count == 1 || state.count == requestScope.maxColumns ? 'disabled' : ''}">
										<form:checkbox id="column${state.count}" path="column[${state.count - 1}]" />
										<span class="input-span"></span><spring:message code="tabs.${choseColumn}" />
									</label>
								</spring:bind>
							</li>
						</c:forEach>
					</ul>
				</div>
			</li>
			<li class="dropdown-footer dropdown-mini">
				<button type="submit" class="btn btn-primary btn-brand btn-block"><span><spring:message code="btn.apply"/></span></button>
				<input type="hidden" id="maxColumns" value="${requestScope.maxColumns}" />
			</li>
		</form:form>
	</ul>
</li>