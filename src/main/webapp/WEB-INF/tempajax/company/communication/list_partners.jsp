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
				<td class="td-brand result-td">
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.tradename}"/>">
					<div class="brand-colspan">
						<a href="<c:url value="${line.companyURL}" />" class="lien lien-table explorer-result" target="_blank">
							<c:out value="${line.tradename}" />
						</a>
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
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.guestDate}" /></td>
				<td class="column5 text-center" style="${currTable.column[4] ? '' : 'display:none;'}"><i class="cmsms-icon-${line.approuved ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td class="column6" style="${currTable.column[5] ? '' : 'display:none;'}"><c:out value="${line.approuvedBy}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
							<c:if test="${!line.approuved}">
								<div id="validate${line.id}Form" class="form-submit">
									<a class="btn btn-table btn-green btn-submit" title="<spring:message code="btn.approuved" />" 
										data-attribut="validate" data-validate="${line.id}"><i class="cmsms-icon-ok-5"></i></a>
								</div>
							</c:if>
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
	<c:otherwise><tr class="empty-tr"><td colspan="8"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>