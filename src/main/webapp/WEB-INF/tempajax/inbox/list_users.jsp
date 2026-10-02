<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.isEmpty()}">
		<c:if test="${currPage == 1}">
			<li class="dropdown-empty">
				<spring:message code="tool.empty.followed3"/>
				<c:if test="${empty currSearch}">
					<span class="help-text m-t-5"><i class="cmsms-icon-info-circled-1 font-big i-blue m-r-5"></i><spring:message code="txt.help.followed3"/></span>
				</c:if>
			</li>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list}">
			<li>
				<a class="dropdown-inbox transition-35" title="<spring:message code="tooltip.messenger"/>" data-avatar="${line.urlAvatar}" 
					data-name="${line.username}" data-login="${line.online}" data-user="${line.id}">
					<span class="img-circle img-container pull-left ${line.online ? 'ind-login' : ''}">
						<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${line.username}" />">
					</span>
					<span class="inbox-header font-small">
						<span class="block text-truncate"><span class="font-bold followed-result"><c:out value="${line.username}"/></span></span>
						<span class="inbox-text text-truncate i-help"><spring:message code="chose.role.${line.role}" /></span>
					</span>
					<span class="clearfix"></span>
				</a>
			</li>
		</c:forEach>
	</c:otherwise>
</c:choose>
</compress:html>