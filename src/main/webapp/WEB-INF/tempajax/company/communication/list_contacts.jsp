<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!list.lines.isEmpty()}">
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small ${!line.approuved ? 'inchecked' : ''}">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td>
					<a href="<c:url value="/company/communication/contacts/detail?id=${line.id}" />" class="lien lien-table explorer-result">
						<spring:message code="chose.contact${line.object}" />
					</a>
				</td>
				<td class="column2 td-parag result-td" style="${currTable.column[1] ? '' : 'display:none;'}">
					<span class="font-bold i-primary explorer-result"><c:out value="${line.username}" /></span>
					<span class="help-text"><spring:message code="lbl.sub.pro${line.pro ? 1 : 2}" /></span>
				</td>
				<td class="column3 text-center" style="${currTable.column[2] ? '' : 'display:none;'}"><c:out value="${line.phone}" /></td>
				<td class="column4" style="${currTable.column[3] ? '' : 'display:none;'}"><a href="mailto:${line.email}" class="lien lien-primary lien-underline"><c:out value="${line.email}" /></a></td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><c:out value="${line.postedDate}" /></td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.approuved ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td class="column7" style="${currTable.column[6] ? '' : 'display:none;'}"><c:out value="${line.approuvedBy}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a href="<c:url value="/company/communication/contacts/detail?id=${line.id}" />" class="btn btn-table btn-green" 
							title="<spring:message code="btn.viewmore" />"><i class="cmsms-icon-link-ext-alt"></i></a>
						<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
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
	<c:otherwise><tr class="empty-tr"><td colspan="9"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>