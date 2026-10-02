<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<c:choose>
	<c:when test="${list.isEmpty()}">
		<c:if test="${currPage == 1}">
			<li class="dropdown-empty">
				<spring:message code="tool.empty.followed1"/>
				<c:if test="${empty currSearch}">
					<span class="help-text m-t-5"><i class="cmsms-icon-info-circled-1 font-big i-blue m-r-5"></i><spring:message code="txt.help.followed1"/></span>
				</c:if>
			</li>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:forEach var="line" items="${list}">
			<li>
				<a href="<c:url value="${line.companyURL}"/>" class="dropdown-inbox transition-35" target="_blank" title="<spring:message code="tooltip.followed"/>">
					<span class="img-circle img-container pull-left">
						<img src="<c:url value="${line.urlAvatar}"/>" class="img-circle img-responsive" alt="<c:out value="${line.tradename}" />">
					</span>
					<span class="inbox-header font-small">
						<span class="block text-truncate">
							<span class="font-bold followed-result"><c:out value="${line.tradename}"/></span>
							<c:if test="${line.alert}"><span class="font-mini i-segond pull-right"><i class="cmsms-icon-bell-5"></i></span></c:if>
						</span>
						<span class="inbox-text i-help"><spring:message code="overview.favorite${line.type}"/></span>
					</span>
					<span class="clearfix"></span>
				</a>
			</li>
		</c:forEach>
	</c:otherwise>
</c:choose>
</compress:html>