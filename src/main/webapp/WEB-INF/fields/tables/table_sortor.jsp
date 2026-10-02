<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<li class="form-print"><a id="iPrint" class="nav-link nav-icon transition-35" title="<spring:message code="btn.print"/>">
	<i class="cmsms-icon-print"></i><span class="font-small hidden-sm-down m-l-10"><spring:message code="btn.print"/></span></a></li>
<li class="divider"></li>
<li class="dropdown brand-menu brand-fixed">
	<a class="nav-link nav-icon transition-35" data-toggle="dropdown" title="<spring:message code="tooltip.table.sort"/>">
		<i class="cmsms-icon-sort-name-up"></i>
	</a>
	<ul class="dropdown-menu dropdown-menu-right animated slideInY" role="menu">
		<c:forEach var="choseSorter" items="${requestScope.choseSorters}" varStatus="state">
			<li>
				<a class="dropdown-item dropdown-sorter" data-sorter="${state.count}">
					<i class="cmsms-icon-ok-4 i-12 ${state.count == 1 ? '' : 'invisible'}"></i><spring:message code="chose.sort.${choseSorter}"/>
				</a>
			</li>
		</c:forEach>
		<li class="dropdown-divider"></li>
		<li>
			<a class="dropdown-item dropdown-desc">
				<i class="cmsms-icon-ok-4 i-12 ${empty desc ? 'invisible' : ''}"></i><spring:message code="chose.sort.desc"/>
			</a>
		</li>
	</ul>
</li>