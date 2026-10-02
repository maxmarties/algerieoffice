<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr"><td colspan="10"><spring:message code="tool.empty.table" /></td></tr></c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list.lines}" varStatus="state">
			<tr id="rowtable${line.id}" class="font-small tr-admin">
				<td class="td-check">
					<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
						<input type="checkbox" name="checkRowTable" data-row="${line.id}" />
						<span class="input-span"></span>
					</label>
				</td>
				<td class="i-primary result-td">
					<c:choose>
						<c:when test="${line.enabled()}">
							<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table explorer-result" target="_blank"><c:out value="${line.title}" /></a>
						</c:when>
						<c:otherwise><span class="explorer-result"><c:out value="${line.title}" /></span></c:otherwise>
					</c:choose>
				</td>
				<td><spring:message code="chose.contract${line.contract}" /></td>
				<td class="text-center"><c:out value="${line.modifiedDate}" /></td>
				<td><a href="<c:url value="${line.companyURL}" />" class="lien lien-table"><c:out value="${line.company}" /></a></td>
				<td class="text-center"><c:out value="${line.clickCount}" /></td>
				<td class="text-center"><c:out value="${line.workCount}" /></td>
				<td class="text-center"><i class="cmsms-icon-${line.published ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td class="text-center">
					<c:choose>
						<c:when test="${line.trashed}"><i class="cmsms-icon-trash i-red"></i></c:when>
						<c:otherwise><c:out value="-" /></c:otherwise>
					</c:choose>
				</td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<c:if test="${line.enabled()}">
							<a href="<c:url value="${line.identifyURL}" />" class="btn btn-table btn-green" 
								title="<spring:message code="btn.overiew" />" target="_blank"><i class="cmsms-icon-paper-plane-3"></i></a>
						</c:if>
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