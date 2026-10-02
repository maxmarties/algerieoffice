<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
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
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.tradename}"/>">
					<div class="brand-colspan">
						<c:choose>
							<c:when test="${line.published}">
								<a href="<c:url value="${line.companyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.tradename}" /></a>
							</c:when>
							<c:otherwise><span class="i-primary explorer-result"><c:out value="${line.tradename}" /></span></c:otherwise>
						</c:choose>
						<span class="help-text"><c:out value="${line.address}"/> <span class="text-uppercase"><spring:message code="chose.wilaya${line.wilaya}"/></span></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}"><spring:message code="chose.activity.${line.activity}"/></td>
				<td class="column3 text-center" style="${currTable.column[2] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${!empty line.briefcase}"><spring:message code="overview.briefcase3.${line.briefcase}" /></c:when>
						<c:otherwise><c:out value="-"/></c:otherwise>
					</c:choose>
				</td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}">
					<a href="mailto:${line.email}" class="lien lien-primary lien-underline"><c:out value="${line.email}" /></a>
				</td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}">
					<a href="tel:+213${line.phone}" class="lien lien-primary lien-underline"><c:out value="${line.getFormattedPhone()}"/></a>
				</td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.published ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<c:if test="${line.published}">
							<a href="<c:url value="${line.companyURL}" />" class="btn btn-table btn-green" title="<spring:message code="btn.view" />" 
								target="_blank"><i class="cmsms-icon-paper-plane-3"></i></a>
						</c:if>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise><tr class="empty-tr"><td colspan="8"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>