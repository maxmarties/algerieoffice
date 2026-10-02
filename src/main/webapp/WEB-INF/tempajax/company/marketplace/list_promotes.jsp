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
				<td class="i-primary result-td explorer-result"><c:out value="${line.title}" /></td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${empty line.url}"><spring:message code="lbl.sub.sticky1.2.3" /></c:when>
						<c:otherwise>
							<a href="<c:url value="${line.url}" />" class="lien lien-primary lien-underline" target="_blank"><spring:message code="lien.extern" /></a>
						</c:otherwise>
					</c:choose>
				</td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}"><c:out value="${line.autor}" /></td>
				<td class="column4" style="${currTable.column[3] ? '' : 'display:none;'}">
					<i class="cmsms-icon-state-${line.state} i-state${line.state} m-r-10"></i><spring:message code="chose.state.promote${line.state}" />
				</td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><c:out value="${line.creditCount}" /></td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}"><c:out value="${line.viewCount}" /></td>
				<td class="column7 text-center" style="${currTable.column[6] ? '' : 'display:none;'}"><c:out value="${line.clickCount}" /></td>
				<td class="column8 text-center font-bold ${line.potentiel > 0 ? 'i-green' : 'i-red'}" style="${currTable.column[7] ? '' : 'display:none;'}">
					<c:out value="${line.parsePotentiel()}" />
				</td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a href="<c:url value="/company/marketplace/promotes/order?id=${line.id}" />" class="btn btn-table btn-green" 
								title="<spring:message code="btn.credit" />"><i class="cmsms-icon-paypal-1"></i></a>
						<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
							<a href="<c:url value="/company/marketplace/promotes/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
								title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
							<div id="delete${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.trash" />" 
									data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
							</div>
						</sec:authorize>
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