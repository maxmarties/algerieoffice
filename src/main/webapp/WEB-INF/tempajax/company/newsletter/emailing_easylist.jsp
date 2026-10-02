<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<c:if test="${choseCompanies != null}">
	<div id="targetEasylistForm" class="form-group">
		<div class="input-group-icon">
			<input class="form-control form-error" type="search" id="findeasy" name="findeasy" placeholder="<spring:message code="tool.find.company"/>" />
			<span class="input-group-addon input-group-simple"><i class="cmsms-icon-search-1"></i></span>
		</div>
		<div class="table-scroll m-t-5" style="min-height:420px;">
			<table class="table table-target">
				<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 40px);"></th></tr></thead>
				<tbody class="font-small">
					<tr class="header">
						<td class="td-check">
							<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
								<input type="checkbox" name="checkAllRowEasylist"/>
								<span class="input-span"></span>
							</label>
						</td>
						<td><spring:message code="sidebar.admin.dashboard3.1"/></td>
					</tr>
					<c:forEach var="choseCompany" items="${choseCompanies}">
						<tr>
							<td class="td-check">
								<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
									<input type="checkbox" name="checkRowEasylist" data-row="${choseCompany.userId}"/>
									<span class="input-span"></span>
								</label>
							</td>
							<td class="td-result"><c:out value="${choseCompany.email}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input type="hidden" id="countEasylist" value="0" />
			<input type="hidden" id="countResultEasylist" value="${choseCompanies.size()}" />
		</div>
		<span class="error"></span>
		<span class="help-text m-t-10"><span class="font-bold countTarget">0</span> <spring:message code="tool.find.select" /></span>
	</div>
</c:if>