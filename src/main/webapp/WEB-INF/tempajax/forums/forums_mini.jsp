<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<compress:html removeIntertagSpaces="false">
	<c:if test="${!list.isEmpty()}">
		<ul class="list-none list-block list-topics-explorer">
			<c:forEach var="line" items="${list}" varStatus="state">
				<li class="flexed flex-row item-${line.language == 'ar' ? 'rtl' : 'ltr'} ${!line.usersAvatar.isEmpty() ? 'item-avatars' : ''}">
					<span class="predview-trigger text-truncate"><c:out value="${line.commentCount}"/></span>
					<div class="brand-colspan text-truncate">
						<a href="<c:url value="/forums/topic/${line.id}"/>" class="lien lien-company sh-black" 
							target="${target ? '_blank' : '_self'}" title="<c:out value="${line.title}"/>"><c:out value="${line.title}"/></a>
					</div>
					<div class="brand-avatars">
						<c:if test="${!line.usersAvatar.isEmpty()}">
							<ul class="navbar-nav nav-flex-icons">
								<c:forEach var="userAvatar" items="${line.usersAvatar}" varStatus="subState">
									<li class="${subState.count == 1 ? 'ml-auto' : ''}"><img src="<c:url value="${userAvatar}"/>" class="img-circle"></li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
					<span class="clearfix"></span>
				</li>
			</c:forEach>
		</ul>
	</c:if>
</compress:html>