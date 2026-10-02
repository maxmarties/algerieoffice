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
					<img src="<c:url value="${line.userMini.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.userMini.username}"/>">
					<div class="brand-colspan">
						<a href="<c:url value="${line.userMini.pseudoURL}" />" class="lien lien-table explorer-result" 
							title="<spring:message code="tool.navigate.company3.3" />" target="_blank">
							<c:out value="${line.userMini.username}" />
						</a><span class="i-certificated i-certificated${line.userMini.verified}"></span>
						<span class="help-text"><spring:message code="lbl.sub.pro${line.userMini.hasPro ? 1 : 2}" /></span>
					</div>
					<span class="clearfix"></span>
				</td>
				<td class="column2" style="${currTable.column[1] ? '' : 'display:none;'}"><c:out value="${line.reason}" /></td>
				<td class="column3 text-center" style="${currTable.column[2] ? '' : 'display:none;'}"><c:out value="${line.lockedDate}" /></td>
				<td class="column4" style="${currTable.column[3] ? '' : 'display:none;'}"><c:out value="${line.lockedBy}" /></td>
				<td>
					<div class="form-groupe text-right m-b-0 m-r-10">
						<div id="delete${line.id}Form" class="form-submit">
							<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.delete" />" 
								data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
						</div>
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