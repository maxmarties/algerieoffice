<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<p class="font-small m-b-10"><span class="font-bold ${choseUsers.size() == 0 ? 'i-red' : 'i-green'}"><c:out value="${choseUsers.size()}"/></span> <spring:message code="txt.help.newsletter3.3.3.1" /></p>
<div id="targetUsersForm" class="form-group">
	<div class="input-group-icon">
		<input class="form-control form-error" type="search" id="finduser" name="finduser" placeholder="<spring:message code="tool.find.user"/>" />
		<span class="input-group-addon input-group-simple"><i class="cmsms-icon-search-1"></i></span>
	</div>
	<div class="table-scroll m-t-5" style="min-height:420px;">
		<table class="table table-target">
			<thead><tr><th style="width:40px;"></th><th style="width:calc(100% - 40px);"></th></tr></thead>
			<tbody class="font-small">
				<tr class="header">
					<td class="td-check">
						<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
							<input type="checkbox" name="checkAllRowUser"/>
							<span class="input-span"></span>
						</label>
					</td>
					<td><spring:message code="sidebar.admin.dashboard10.1"/></td>
				</tr>
				<c:forEach var="choseUser" items="${choseUsers}">
					<tr>
						<td class="td-check">
							<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
								<input type="checkbox" name="checkRowUser" data-row="${choseUser.userId}"/>
								<span class="input-span"></span>
							</label>
						</td>
						<td class="td-result"><c:out value="${choseUser.email}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="countUsers" value="0" />
	</div>
	<span class="help-text m-t-10"><span class="font-bold countTarget">0</span> <spring:message code="tool.find.select" /></span>
</div>