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
					<img src="<c:url value="${line.userMini.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.userMini.username}"/>">
					<div class="brand-colspan">
						<a href="<c:url value="${line.userMini.pseudoURL}" />" class="lien lien-table explorer-result" 
							title="<spring:message code="tool.navigate.company3.3" />" target="_blank">
							<c:out value="${line.userMini.username}" />
						</a><span class="i-certificated i-certificated${line.userMini.verified}"></span>
						<span class="help-text">
							<c:if test="${line.userMini.hasLocked}"><i class="cmsms-icon-lock i-red m-r-5"></i></c:if>
							<spring:message code="lbl.sub.pro${line.userMini.hasPro ? 1 : 2}" />
						</span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td class="column2 text-center" style="${currTable.column[1] ? '' : 'display:none;'}"><c:out value="${line.postedDate}" /></td>
				<td class="column3 text-center" style="${currTable.column[2] ? '' : 'display:none;'}">
					<c:set var="countStar" value="${line.note / 2}" scope="page"></c:set>
					<ul class="list-none list-evaluation">
						<c:forEach var="i" begin="1" end="${pageScope.countStar}" step="1"><li class="i-yellow"><i class="cmsms-icon-star-1"></i></li></c:forEach>
						<c:if test="${line.note % 2 != 0}"><li class="i-yellow"><i class="cmsms-icon-star-half"></i></li></c:if>
					</ul>
				</td>
				<td class="column4 text-center" style="${currTable.column[3] ? '' : 'display:none;'}"><i class="cmsms-icon-thumbs-${line.liked ? 'up-alt i-green' : 'down-alt i-red'}"></i></td>
				<td>	
					<div class="form-groupe text-right m-b-0 m-r-10">
						<a class="btn btn-table btn-blue iMessage" title="<spring:message code="tooltip.messenger" />" data-id="${line.userMini.id}" 
							 data-avatar="${line.userMini.urlAvatar}" data-name="${line.userMini.username}"><i class="cmsms-icon-mail-alt"></i></a>
						<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
							<c:if test="${!line.userMini.hasLocked}">
								<a href="<c:url value="/company/tools/black-list/new?id=${line.userMini.id}" />" class="btn btn-table btn-yellow" 
									title="<spring:message code="btn.lock" />"><i class="cmsms-icon-lock-6"></i></a>
							</c:if>
						</sec:authorize>
					</div>
				</td>
			</tr>
		</c:forEach>
	</c:when>
	<c:otherwise><tr class="empty-tr"><td colspan="6"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>