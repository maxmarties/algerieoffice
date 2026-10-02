<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-sidebar h-header">
	<a id="cleardesktop" class="sidebar-header bn-explorer-primary" data-name="<spring:message code="explorer.desktop.element0.1"/>"><spring:message code="explorer.mainmenu3"/></a>
	<ul class="sidebar-mainmenu list-none" data-widget="bread">
		<c:forEach var="category" items="${explorerPage.categories}">
			<c:choose>
				<c:when test="${category.childs.isEmpty()}">
					<li class="breadview-item ${!empty categoryMini && categoryMini.uuid() == category.item.uuid() ? 'active' : ''}" 
						data-uuid="${category.item.uuid()}" data-name="${category.item.name}">
						<a class="transition-color"><c:out value="${category.item.name}"/><small class="mini m-l-5">(<c:out value="${category.item.count}"/>)</small></a>
					</li>
				</c:when>
				<c:otherwise>
					<li class="breadview ${!empty categoryMini ? categoryMini.uuid() == category.item.uuid() ? 'active' : category.inChilds(categoryMini.id) ? 'menu-open' : '' : ''}">
						<a class="transition-color">
							<c:out value="${category.item.name}"/><small class="mini m-l-5">(<c:out value="${category.count()}"/>)</small>
							<i class="breadview-plus cmsms-icon-angle-down"></i>
						</a>
						<ul class="breadview-menu list-none">
							<c:forEach var="child" items="${category.childs}">
								<li class="breadview-item ${!empty categoryMini && categoryMini.uuid() == child.uuid() ? 'active' : ''}" 
									data-uuid="${child.uuid()}" data-name="${child.name}">
									<a class="transition-color"><c:out value="${child.name}"/><small class="mini m-l-5">(<c:out value="${child.count}"/>)</small></a>
								</li>
							</c:forEach>
						</ul>
					</li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</div>
<c:if test="${!empty categoryMini}">
	<input type="hidden" id="categoryMiniUUID" value="${categoryMini.uuid()}" />
	<input type="hidden" id="categoryMiniName" value="${categoryMini.name}" />
</c:if>