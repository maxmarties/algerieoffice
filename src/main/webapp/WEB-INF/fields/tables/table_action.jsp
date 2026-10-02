<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="table-action">
	<div class="table-overlay"></div>
	<nav class="navbar m-t-5">
		<ul class="nav nav-action">
			<li class="form-action m-r-5">
				<select class="form-select2-simple" id="actionTable" name="actionTable">
					<c:forEach var="i" begin="1" end="2" step="1"><option value="${i}"><spring:message code="tool.find.action${i}" /></option></c:forEach>
				</select>
			</li>
			<li>
				<div id="deleteSelectedForm" class="form-submit">
					<button type="button" class="btn btn-danger btn-simple btn-add btn-left btn-fixed btn-submit disabled"
						data-toggle="multiple-select" data-attribut="delete-selected">
						<span><i class="cmsms-icon-trash-7"></i><spring:message code="btn.${empty requestScope.actionTrash ? 'delete' : 'trash'}"/></span>
					</button>
				</div>
			</li>
		</ul>
	</nav>
</div>