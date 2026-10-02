<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!list.lines.isEmpty()}">
		<c:forEach var="line" items="${list.lines}">
			<tr id="rowtable${line.id}" class="font-small">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="i-primary result-td">
					<c:choose>
						<c:when test="${line.hasPublished}">
							<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.title}" /></a>
						</c:when>
						<c:otherwise><span class="explorer-result"><c:out value="${line.title}" /></span></c:otherwise>
					</c:choose>
				</td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}"><spring:message code="chose.annonce${line.type}" /></td>
				<td class="column3 text-center" style="${currTable.column[2] ? '' : 'display:none;'}"><c:out value="${line.period}" /></td>
				<td class="column4" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.autor}" /></td>
				<td class="column5" style="${currTable.column[4] ? '' : 'display:none;'}">
					<i class="cmsms-icon-state-${line.state} i-state${line.state} i-22"></i><spring:message code="chose.state.annonce${line.state}" />
				</td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}"><c:out value="${line.clickCount}" /></td>
				<td class="column7 text-center" style="${currTable.column[6] ? '' : 'display:none;'}"><c:out value="${line.workCount}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
							<c:if test="${line.state < 3}">
								<div id="publish${line.id}Form" class="form-submit">
									<a class="btn btn-table btn-green btn-submit" title="<spring:message code="btn.${line.state == 1 ? 'pause' : 'start'}" />" 
										data-attribut="publish" data-publish="${line.id}"><i class="cmsms-icon-${line.state == 1 ? 'pause' : 'play'}"></i></a>
								</div>
							</c:if>
							<a href="<c:url value="/company/marketplace/ads/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
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
	<c:otherwise><tr class="empty-tr"><td colspan="9"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>