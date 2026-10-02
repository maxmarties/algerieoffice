<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${!list.lines.isEmpty()}">
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small ${!line.consulted ? 'inchecked' : ''}">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="i-primary result-td">
					<c:choose>
						<c:when test="${line.hasPublished}">
							<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table explorer-result" target="_blank">
								<c:out value="${line.title}" />
							</a>
						</c:when>
						<c:otherwise><span class="explorer-result"><c:out value="${line.title}" /></span></c:otherwise>
					</c:choose>
				</td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}"><c:out value="${line.username}" /></td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}"><a href="mailto:${line.email}" class="lien lien-primary lien-underline"><c:out value="${line.email}" /></a></td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.postedDate}" /></td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${!empty line.fileUrl}">
							<a href="<c:url value="${line.fileUrl}" />" class="lien lien-hover lien-primary lien-small" target="_blank">
								<spring:message code="tool.view.file" />
							</a>
						</c:when>
						<c:otherwise><c:out value="-" /></c:otherwise>
					</c:choose>
				</td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.consulted ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td class="column7" style="${currTable.column[6] ? '' : 'display:none;'}"><c:out value="${line.consultedBy}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a href="<c:url value="/company/prospect/jobs/detail?id=${line.id}" />" class="btn btn-table btn-green" 
							title="<spring:message code="btn.viewmore" />"><i class="cmsms-icon-link-ext-alt"></i></a>
						<c:if test="${!line.consulted}">
							<div id="validate${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-yellow btn-submit" title="<spring:message code="btn.consulted" />" 
									data-attribut="validate" data-validate="${line.id}"><i class="cmsms-icon-minus-4"></i></a>
							</div>
						</c:if>
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