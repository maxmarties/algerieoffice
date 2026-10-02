<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!list.lines.isEmpty()}">
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" /><span class="input-span"></span>
					</label>
				</td>
				<td class="td-check td-image"><img src="<c:url value="${line.urlAvatar}"/>" class="img-circle" alt="<c:out value="${line.username}"/>"></td>
				<td class="column2 result-td" style="${currTable.column[1] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${line.hasEnabled}">
							<a href="<c:url value="/membres?id=${line.id}" />" class="lien lien-table explorer-result" 
								title="<spring:message code="tool.navigate.company3.3" />" target="_blank"><c:out value="${line.username}" /></a>
						</c:when>
						<c:otherwise><span class="explorer-result"><c:out value="${line.username}" /></span></c:otherwise>
					</c:choose>
				</td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}"><spring:message code="chose.role.${line.role}" /></td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.createDate}" /></td>
				<td class="column5" style="${currTable.column[4] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${line.hasEnabled}">
							<a href="mailto:${line.email}" class="lien lien-primary lien-underline"><c:out value="${line.email}" /></a>
						</c:when>
						<c:otherwise><c:out value="${line.email}" /></c:otherwise>
					</c:choose>
				</td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}"><c:out value="${line.loginDate}" /></td>
				<td class="column7 text-center" style="${currTable.column[6] ? '' : 'display:none;'}"><c:out value="${line.numberOfVisit}" /></td>
				<td class="column8 text-center" style="${currTable.column[7] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.hasEnabled ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<c:if test="${currUserId != line.id}">
							<a class="btn btn-table btn-blue iMessage" title="<spring:message code="tooltip.messenger" />" data-id="${line.id}" 
								data-avatar="${line.urlAvatar}" data-name="${line.username}"><i class="cmsms-icon-mail-alt"></i></a>
						</c:if>
						<a href="<c:url value="/company/team/users/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
							title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
						<div id="delete${line.id}Form" class="form-submit">
							<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
								data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
						</div>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise><tr class="empty-tr"><td colspan="10"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>