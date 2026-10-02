<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="9"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin">
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
								<a href="<c:url value="${line.companyURL}" />" class="lien lien-table explorer-result" target="_blank">
									<c:out value="${line.tradename}" />
								</a>
							</c:when>
							<c:otherwise><span class="i-primary explorer-result"><c:out value="${line.tradename}" /></span></c:otherwise>
						</c:choose>
						<span class="help-text"><spring:message code="chose.activity.${line.activity}"/></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td><spring:message code="chose.wilaya${line.wilaya}" /></td>
				<td class="text-center"><c:out value="${line.users}" /></td>
				<td class="text-center"><c:out value="${line.completed}%" /></td>
				<td class="text-center i-state${line.premium}"><spring:message code="chose.subscribe.order${line.premium + 1}" /></td>
				<td class="text-center">
					<c:choose>
						<c:when test="${empty line.note}"><c:out value="--"/></c:when>
						<c:otherwise>
							<c:set var="countStar" value="${line.note / 2}" scope="page"></c:set>
							<ul class="list-none list-evaluation">
								<c:forEach var="i" begin="1" end="${pageScope.countStar}" step="1"><li class="i-yellow"><i class="cmsms-icon-star-1"></i></li></c:forEach>
								<c:if test="${line.note % 2 != 0}"><li class="i-yellow"><i class="cmsms-icon-star-half"></i></li></c:if>
							</ul>
						</c:otherwise>
					</c:choose>
				</td>
				<td class="text-center"><i class="cmsms-icon-${line.published ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<sec:authorize access="hasAuthority('SUPPORT_ADMIN_PRIVILEGE')">
							<a href="<c:url value="/admin/companies/features/budget?id=${line.id}" />" class="btn btn-table btn-green" 
								title="<spring:message code="btn.credit" />"><i class="cmsms-icon-paypal-1"></i></a>
							<a href="<c:url value="/admin/companies/features/edit?id=${line.id}" />" class="btn btn-table btn-yellow" 
								title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
							<div id="delete${line.id}Form" class="form-submit">
								<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
									data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
							</div>
						</sec:authorize>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>