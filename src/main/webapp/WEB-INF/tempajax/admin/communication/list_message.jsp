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
					<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.sender}"/>">
					<div class="brand-colspan">
						<span class="i-primary explorer-result"><c:out value="${line.sender}" /></span>
						<span class="help-text"><c:out value="${line.tradename}"/></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td><c:out value="${line.recepient}" /></td>
				<td class="${line.emojis ? 'td-emojis' : ''}">
					<c:choose>
						<c:when test="${line.emojis}"><img src="<c:url value="/static/vectors/emoticons/${line.message}.png"/>"></c:when>
						<c:otherwise><c:out value="${line.message}" /></c:otherwise>
					</c:choose>
				</td>
				<td class="text-center"><c:out value="${line.postedDate}" /></td>
				<td class="text-center"><i class="cmsms-icon-${line.consulted ? 'ok-2 i-green' : 'cancel-3 i-red'}"></i></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<sec:authorize access="hasAuthority('SUPPORT_ADMIN_PRIVILEGE')">
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
	<c:otherwise><tr class="empty-tr"><td colspan="7"><spring:message code="tool.empty.table" /></td></tr></c:otherwise>
</c:choose>
<tr style="display:none;">
	<td><input type="hidden" id="countResult" value="${list.countResult}" /></td>
	<td><input type="hidden" id="countSize" value="${list.lines.size()}" /></td>
</tr>
</compress:html>