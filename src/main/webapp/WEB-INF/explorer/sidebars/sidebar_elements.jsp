<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="explorer-toolbar">
	<div class="toolbar-header h-header">
		<span id="desktopFilter"><spring:message code="explorer.desktop.element${requestScope.explorerMainsibdebar}.1"/></span>
		<span class="pull-right">
			<span class="countLine font-bold"></span> <span class="font-mini"><spring:message code="explorer.desktop.element${requestScope.explorerMainsibdebar}.2"/></span>
		</span>
		<span class="clearfix"></span>
	</div>
	<div class="row">
		<div class="col-md-6 m-t-10">
			<nav class="navbar">
				<ul class="nav nav-pagination" data-toggle="buttons">
					<li>
						<label class="btn btn-explorer-icon btn-simple ${!empty view ? 'active' : ''} ${!empty viewWidget ? 'disabled' : ''}" 
							title="<spring:message code="tooltip.explorer.view1" />">
							<i class="cmsms-icon-th-list"></i><input type="radio" name="desktopView" class="hidden" value="true" ${!empty view ? 'checked' : ''} />
						</label>
					</li>
					<li>
						<label class="btn btn-explorer-icon btn-simple ${empty view ? 'active' : ''} ${!empty viewList ? 'disabled' : ''}" 
							title="<spring:message code="tooltip.explorer.view2" />">
							<i class="cmsms-icon-th-large"></i><input type="radio" name="desktopView" class="hidden" value="false" ${empty view ? 'checked' : ''}/>
						</label>
					</li>
				</ul>
				<ul class="nav nav-pagination nav-pagination-find">
					<li>
						<form name="findDesktopForm" action="/" novalidate="novalidate">
							<div class="input-group-contact">
								<button type="submit" class="btn btn-simple icon-contact" title="<spring:message code="btn.find" />"><i class="cmsms-icon-search-1"></i></button>
								<input class="form-control" type="search" id="finddesktop" name="finddesktop" placeholder="<spring:message code="tool.find.preview" />" />
							</div>
						</form>
					</li>
				</ul>
			</nav>
		</div>
		<div class="col-md-6 m-t-10">
			<nav class="navbar">
				<ul class="nav nav-pagination nav-pagination-sort ml-auto">
					<li class="nav-text font-small m-r-10"><spring:message code="tool.explorer.sort"/> :</li>
					<li class="form-explorer-sort">
						<select class="form-select2-simple" id="desktopSort" name="desktopSort">
							<c:forEach var="choseSorter" items="${requestScope.choseSortersDesktop}" varStatus="state">
								<option value="${state.count}"><spring:message code="tabs.explorer.${choseSorter}"/></option>
							</c:forEach>
						</select>
					</li>
				</ul>
				<ul class="nav nav-pagination" data-toggle="buttons">
					<li>
						<label class="btn btn-explorer-icon btn-simple ${empty desc ? 'active' : ''}" title="<spring:message code="tooltip.explorer.sort1" />">
							<i class="cmsms-icon-sort-name-up"></i><input type="radio" name="desktopDesc" class="hidden" value="false" ${empty desc ? 'checked' : ''} />
						</label>
					</li>
					<li>
						<label class="btn btn-explorer-icon btn-simple ${!empty desc ? 'active' : ''}" title="<spring:message code="tooltip.explorer.sort2" />">
							<i class="cmsms-icon-sort-name-down"></i><input type="radio" name="desktopDesc" class="hidden" value="true" ${!empty desc ? 'checked' : ''}/>
						</label>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</div>