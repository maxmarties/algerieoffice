<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!list.lines.isEmpty()}">
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="td-brand result-td">
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.username}"/>">
					<div class="brand-colspan">
						<a href="<c:url value="/membres?id=${line.userId}" />" class="lien lien-table explorer-result" 
							title="<spring:message code="tool.navigate.company3.3" />" target="_blank">
							<c:out value="${line.username}" />
						</a>
						<span class="help-text"><c:out value="${line.companyname}"/></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td class="column2 text-center" style="${currTable.column[1] ? '' : 'display:none;'}"><c:out value="${line.accessDate}" /></td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}"><spring:message code="chose.detect.${line.type}" /></td>
				<td class="column4" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.device}" /></td>
				<td class="column5" style="${currTable.column[4] ? '' : 'display:none;'}">
					<i class="cmsms-icon-${line.formWeb ? 'monitor i-green' : 'mobile-1 i-red'} m-r-10"></i><spring:message code="chose.detect.device${line.formWeb ? '1' : '2'}" />
				</td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a class="btn btn-table btn-blue iMessage" title="<spring:message code="tooltip.messenger" />" data-id="${line.userId}" 
							data-avatar="${line.urlAvatar}" data-name="${line.username}"><i class="cmsms-icon-mail-alt"></i></a>
						<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
							<div id="delete${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
									data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
							</div>
						</sec:authorize>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise><tr class="empty-tr"><td colspan="7"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>