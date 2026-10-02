<%@ include file="/WEB-INF/tags/libs/joda_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company-user/easylist/companies"/>" class="lien lien-black">
			<i class="cmsms-icon-floppy m-r-5"></i><spring:message code="sidebar.user.dashboard9"/></a></li>
		<li><a href="<c:url value="/company-user/easylist/posts"/>" class="lien lien-black"><spring:message code="sidebar.user.dashboard9.2" /></a></li>
		<li class="active"><c:out value="${easylist.easyname}"/></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><c:out value="${easylist.easyname}"/></h1>
		<p class="m-t-5">
			<joda:format value="${easylist.easyDate}" pattern="dd/MM/yyyy HH:mm"></joda:format>
			<span class="pull-right"><span class="font-bold countLine"></span> <span class="font-mini"><spring:message code="tool.element"/></span></span>
		</p>
		<span class="clearfix"></span>
	</div>
	<c:choose>
		<c:when test="${hasPremium}">
			<div class="row">
				<div class="col-md-6">
					<c:set var="backwordURL" value="/company-user/easylist/posts" scope="request"></c:set>
					<c:set var="backwordPage" value="0" scope="request"></c:set>
					<c:set var="backwordBorder" value="1" scope="request"></c:set>
					<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
				</div>
				<div class="col-md-6 m-t-5">
					<c:set var="placeholderFind" scope="request"><spring:message code="tool.find.post"/></c:set>
					<c:import url="/WEB-INF/fields/tables/table_find.jsp"/>
				</div>
			</div>
			<c:import url="/WEB-INF/fields/tables/table_result.jsp"/>
			<div class="table-container">
				<div class="table-overlay"></div>
				<nav class="navbar">
					<ul class="nav nav-table">
						<c:import url="/WEB-INF/fields/tables/table_filter.jsp"/>
						<li class="form-filter">
							<c:set var="faholder" scope="page"><spring:message code="tool.filter.type" /></c:set>
							<select class="form-select2" id="filterTable" name="filterTable" data-placeholder="${pageScope.faholder}">
								<option></option>
								<option value="false"><spring:message code="chose.post.type1" /></option>
								<option value="true"><spring:message code="chose.post.type2" /></option>
							</select>
						</li>
					</ul>
					<ul class="nav nav-table">
						<c:set var="choseSorters" value="title,price,modifiedate" scope="request"></c:set>
						<c:import url="/WEB-INF/fields/tables/table_sortor.jsp"/>
						<li class="divider"></li>
						<c:set var="maxColumns" value="7" scope="request"></c:set>
						<c:set var="choseColumns" value="title,company,type,price,updated,published,posts" scope="request"></c:set>
						<c:import url="/WEB-INF/fields/tables/table_config.jsp"/>
					</ul>
				</nav>
				<div class="table-content">
					<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
					<div class="table-responsive">
						<table id="tableList" class="table table-form">
							<thead>
								<tr>
									<th class="td-check sorter-false" style="width:4%;">
										<label class="ui-checkbox ui-checkbox-segond font-small m-l-10">
											<input type="checkbox" name="checkAllRowTable" />
											<span class="input-span"></span>
										</label>
									</th>
									<c:set var="cols" value="28,20,10,10,14,6,8" scope="page"></c:set>
									<c:forEach var="col" items="${pageScope.cols}" varStatus="state">
										<th class="column${state.count} ${state.count >= 6 ? 'sorter-false' : ''}" 
											style="width:${pageScope.col}%;${currTable.column[state.count - 1] ? '' : 'display:none;'}">
											<spring:message code="tabs.${requestScope.choseColumns.split(',')[state.count - 1]}"/>
										</th>
									</c:forEach>
								</tr>
							</thead>
							<tbody></tbody>
						</table>
					</div>
				</div>
			</div>
			<c:import url="/WEB-INF/fields/tables/table_pagination.jsp"/>		
		</c:when>
		<c:otherwise>
			<div class="alert alert-warning m-t-10">
				<i class="cmsms-icon-dollar i-alert"></i>
				<p class="p-alert">
					<spring:message code="message.premium.easylist"/> 
					<a href="<c:url value="/company/tools/subscribes/new"/>" class="lien lien-primary lien-underline"><spring:message code="lien.help.plan"/></a>
				</p>
			</div>
		</c:otherwise>
	</c:choose>
</div>