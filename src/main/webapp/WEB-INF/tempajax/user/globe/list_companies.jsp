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
						<a href="<c:url value="${line.companyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.tradename}" /></a>
						<span class="help-text"><c:out value="${line.address}"/> <span class="text-uppercase"><spring:message code="chose.wilaya${line.wilaya}"/></span></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}">
					<spring:message code="chose.activity.${line.activity}"/>
					<c:if test="${line.activitySize > 0}">
						<span class="help-text">(<c:out value="${line.activitySize}"/>) <spring:message code="lbl.sub.activity3" /></span>
					</c:if>
				</td>
				<td class="column3" style="${currTable.column[2] ? '' : 'display:none;'}"><spring:message code="overview.favorite${line.type}"/></td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}">
					<c:choose>
						<c:when test="${!empty line.briefcase}"><spring:message code="overview.briefcase3.${line.briefcase}" /></c:when>
						<c:otherwise><c:out value="-"/></c:otherwise>
					</c:choose>
				</td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><c:out value="${line.postedDate}" /></td>
				<td class="column6 text-center" style="${currTable.column[5] ? '' : 'display:none;'}">
					<a href="tel:+213${line.phone}" class="lien lien-primary lien-underline">
						<c:out value="${line.getFormattedPhone()}"/>
					</a>
				</td>
				<td class="column7 text-center" style="${currTable.column[6] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.alert ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a href="<c:url value="${line.companyURL}" />" class="btn btn-table btn-green"
							title="<spring:message code="btn.view" />" target="_blank"><i class="cmsms-icon-paper-plane-3"></i></a>
						<div id="publish${line.id}Form" class="form-submit">
							<a class="btn btn-table btn-yellow btn-submit" title="<spring:message code="btn.alert${line.alert ? '2' : '1'}" />" 
								data-attribut="publish" data-publish="${line.id}"><i class="cmsms-icon-${line.alert ? 'bell-off' : 'bell-alt'}"></i></a>
						</div>
						<div id="delete${line.id}Form" class="form-submit">
							<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
								data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
						</div>
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