<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
	<c:choose>
		<c:when test="${list.lines.isEmpty()}"><tr class="empty-tr font-small"><td colspan="7"><spring:message code="tool.empty.table" /></td></tr></c:when>
		<c:otherwise>
			<c:forEach var="line" items="${list.lines}" varStatus="state">
				<tr id="rowtable${line.id}">
					<td><a href="<c:url value="/forums/topic/${line.id}" />" class="ind-language ind-${line.language}"><c:out value="${line.language}" /></a></td>
					<td class="td-brand result-td">
						<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle pull-left" alt="<c:out value="${line.username}"/>">
						<div class="brand-colspan">
							<a href="<c:url value="/forums/topic/${line.id}" />" class="lien lien-primary sh-black explorer-result">
								<c:out value="${line.title}" />
							</a>
							<c:if test="${line.quiz}"><i class="cmsms-icon-mic-2 i-green m-l-10"></i></c:if>
							<span class="help-text">
								<a href="<c:url value="/forums?category=${line.category}" />" class="tag-topic tag-topic${line.category} transition-color">
									<spring:message code="chose.topic.category${line.category}"/>
								</a>
							</span>
						</div>
						<span class="clearfix"></span>
					</td>
					<td class="td-avatars">
						<c:if test="${!line.usersAvatar.isEmpty()}">
							<ul class="navbar-nav nav-flex-icons">
								<c:forEach var="userAvatar" items="${line.usersAvatar}" varStatus="subState">
									<li class="${subState.count == 1 ? 'ml-auto' : ''}"><img src="<c:url value="${userAvatar}"/>" class="img-circle"></li>
								</c:forEach>
							</ul>
						</c:if>
					</td>
					<td class="h-header font-small text-center ${line.commentCount == 0 ? 'i-help' : line.commentCount > 100 ? 'i-red' : ''}"><c:out value="${line.parseCommentCount()}"/></td>
					<td class="h-header font-small text-center ${line.viewCount == 0 ? 'i-help' : line.viewCount > 100 ? 'i-blue' : ''}"><c:out value="${line.parseViewCount()}"/></td>
					<td class="font-mini font-bold text-center i-help"><joda:format value="${line.createdDate}" pattern="dd MMMM, HH:mm"></joda:format></td>
					<td>
						<c:choose>
							<c:when test="${line.userId == currUserId}">
								<div class="form-groupe text-right m-b-0">
									<a href="<c:url value="/forums/edit/${line.id}" />" class="btn btn-table btn-yellow" 
										title="<spring:message code="btn.edit" />"><i class="cmsms-icon-pencil-5"></i></a>
									<div id="delete${line.id}Form" class="form-submit">
										<a class="btn btn-table btn-red btn-submit" title="<spring:message code="btn.trash" />" 
											data-attribut="delete" data-delete="${line.id}"><i class="cmsms-icon-trash-7"></i></a>
									</div>
								</div>
							</c:when>
							<c:otherwise><c:if test="${line.marked}"><i class="cmsms-icon-flag i-yellow sh-black"></i></c:if></c:otherwise>
						</c:choose>
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