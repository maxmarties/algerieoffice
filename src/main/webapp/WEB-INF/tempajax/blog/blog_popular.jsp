<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
<div class="widget-body">
	<ul class="list-none list-blog-proxis">
		<c:forEach var="line" items="${list}" varStatus="state">
			<li>
				<span class="item-provider p-left"><c:out value="00${state.count}"/></span>
				<a href="<c:url value="${line.identifyURL}" />" class="lien lien-table"><c:out value="${line.title}" /></a>
			</li>
		</c:forEach>
	</ul>
</div>
</compress:html>