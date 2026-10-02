<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-find">
	<form name="findScreenForm" action="/" novalidate="novalidate">
		<div class="form-group m-b-0">
			<div class="input-group-screen">
				<i class="cmsms-icon-search-1 icon-screen"></i>
				<input class="form-control" type="search" id="findscreen" name="findscreen" placeholder="${requestScope.placeholderFind}" value="${token}"/>
				<button type="submit" class="btn btn-primary btn-simple" title="<spring:message code="btn.find" />"><span><spring:message code="btn.ok"/></span></button>
			</div>
			<c:if test="${!empty requestScope.placeholderResult}">
				<div id="resultScreenFind" class="m-t-10" style="display:none;">
					<p class="i-help">
						<span class="h-header font-strong countLine"></span> <c:out value="${requestScope.placeholderResult}"/> "<span class="font-bold i-segond tokenResult"></span>".
						<a id="clearScreenFind" class="lien lien-hover lien-primary font-small pull-right"><spring:message code="btn.clear.find"/></a>
						<span class="clearfix"></span>
					</p>
				</div>
			</c:if>
		</div>
	</form>
</div>