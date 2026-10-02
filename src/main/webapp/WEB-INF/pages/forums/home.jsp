<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black" title="<spring:message code="tooltip.forums.menu" />"><i class="cmsms-icon-home"></i></a></li>
		<li class="active"><spring:message code="sidebar.home.mainfooter1.12"/></li>
	</ol>
</div>
<sec:authorize access="hasAuthority('COMPANY_VISIT_PRIVILEGE')">
	<div class="tabbed-forums m-t-5">
		<div class="container">
			<div class="table-overlay"></div>
			<form:form name="searchForumForm" action="/" method="POST" modelAttribute="searchForum" enctype="utf8" novalidate="novalidate">
				<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
				<spring:bind path="token"><form:input type="hidden" path="token" /></spring:bind>
				<spring:bind path="row"><form:input type="hidden" path="row" /></spring:bind>
				<spring:bind path="sort"><form:input type="hidden" path="sort" /></spring:bind>
				<spring:bind path="page"><form:input type="hidden" path="page" /></spring:bind>
				<spring:bind path="desc"><form:input type="hidden" path="desc" /></spring:bind>
				<div class="row row-mini">
					<div class="col-md-6 col-lg-3 col-mini">
						<spring:bind path="filter">
							<div class="form-group m-b-0">
								<c:set var="faholder" scope="page"><spring:message code="tool.filter.data" /></c:set>
								<form:select class="form-select2-simple" path="filter" data-placeholder="${pageScope.faholder}" >
									<option value="0"><spring:message code="chose.date7"/></option>
									<c:forEach var="i" begin="1" end="6" step="1">
										<option value="${i}"><spring:message code="chose.date${i}"/></option>
									</c:forEach>
								</form:select>
							</div>
						</spring:bind>
					</div>
					<div class="col-md-6 col-lg-3 col-mini">
						<spring:bind path="category">
							<div class="form-group m-b-0">
								<c:set var="faholder" scope="page"><spring:message code="tool.filter.post" /></c:set>
								<form:select class="form-select2-simple" path="category" data-placeholder="${pageScope.faholder}" >
									<option></option>
									<c:forEach var="i" begin="0" end="10" step="1">
										<option value="${i}" ${searchForum.category == i ? 'selected' : ''}><spring:message code="chose.topic.category${i}"/></option>
									</c:forEach>
								</form:select>
							</div>
						</spring:bind>
					</div>
					<div class="col-md-12 col-lg-6 col-mini">
						<nav class="navbar" style="width:100%;">
							<spring:bind path="tabulation">
								<ul class="navbar-nav nav-flex-icons nav-tab-topics" data-toggle="buttons">
									<c:set var="providers" value="pin-1,wrench-1,flag-filled,mic-2" scope="page"></c:set>
									<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
										<li>
											<label class="btn btn-tabbed btn-simple" title="<spring:message code="wizard.forums.tabbed${state.count - 1}"/>">
												<i class="cmsms-icon-${pageScope.provider}"></i>
												<span class="hidden-sm-down m-l-5"><spring:message code="wizard.forums.tabbed${state.count - 1}"/></span>
												<form:radiobutton class="hidden" value="${state.count - 1}" path="tabulation"/>
											</label>
										</li>
									</c:forEach>
								</ul>
							</spring:bind>
						</nav>
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<div class="container">
		<div class="forums-toolbar m-t-10">
			<div class="table-overlay"></div>
			<div class="row">
				<div class="col-md-4">
					<a href="<c:url value="/forums/new"/>" class="btn btn-success btn-simple btn-add btn-left" style="min-width:180px;">
						<span><i class="cmsms-icon-plus"></i><spring:message code="header.forums.new"/></span>
					</a>
				</div>
				<div class="col-md-8">
					<nav class="navbar">
						<ul class="nav nav-pagination nav-pagination-sort ml-auto">
							<li class="nav-text font-small m-r-10"><spring:message code="tool.explorer.sort"/> :</li>
							<li class="form-explorer-sort">
								<c:set var="choseSorters" value="actu,view,date,title" scope="page"></c:set>
								<select class="form-select2-simple" id="screenSort" name="screenSort">
									<c:forEach var="choseSorter" items="${pageScope.choseSorters}" varStatus="state">
										<option value="${state.count}"><spring:message code="tabs.explorer.${choseSorter}"/></option>
									</c:forEach>
								</select>
							</li>
						</ul>
						<ul class="nav nav-pagination" data-toggle="buttons">
							<li>
								<label class="btn btn-icon btn-simple" title="<spring:message code="tooltip.explorer.sort1" />">
									<i class="cmsms-icon-sort-name-up"></i><input type="radio" name="screenDesc" class="hidden" value="false" />
								</label>
							</li>
							<li>
								<label class="btn btn-icon btn-simple active" title="<spring:message code="tooltip.explorer.sort2" />">
									<i class="cmsms-icon-sort-name-down"></i><input type="radio" name="screenDesc" class="hidden" value="true" checked/>
								</label>
							</li>
						</ul>
					</nav>
				</div>
			</div>
		</div>
		<div class="forums-container m-t-10">
			<c:import url="/WEB-INF/basics/loading_topic.jsp"/>
			<div class="table-responsive">
				<table id="tableList" class="table table-forums">
					<thead>
						<tr>
							<th class="sorter-false" style="width:50px;"><spring:message code="tabs.illustr" /></th>
							<th class="th-title" style="width:calc(100% - 670px);"><spring:message code="tabs.topic" /></th>
							<th class="sorter-false" style="width:210px;"></th>
							<th class="th-icon" style="width:80px;"><i class="cmsms-icon-comment-3"></i></th>
							<th class="th-icon" style="width:80px;"><i class="cmsms-icon-eye-3"></i></th>
							<th class="th-icon" style="width:160px;"><i class="cmsms-icon-time"></i></th>
							<th class="sorter-false" style="width:90px;"></th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
		<div class="forums-toolbar screen-pagination m-t-10" style="display:none;">
			<nav class="navbar">
				<ul class="nav nav-pagination ml-auto">
					<c:set var="providers" value="10,20,50,100" scope="page"></c:set>
					<li class="nav-text font-mini m-r-5"><spring:message code="tool.explorer.line" /> :</li>
					<li class="form-select">
						<select class="form-select2-simple" id="pagescreen" name="pagescreen">
							<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
								<option value="${state.count - 1}" ${state.count - 1 == currentConfig.defaultResult ? 'selected' : ''}><c:out value="${pageScope.provider}"/></option>
							</c:forEach>
						</select>
					</li>
				</ul>
				<ul class="nav nav-pagination m-l-10">
					<li class="nav-text font-mini m-r-10">
						<span id="paginationResultScreen"></span> <spring:message code="tool.pagination.div"/> <span class="countLine"></span>
					</li>
					<li>
						<a id="screenBack" class="btn btn-icon btn-simple" title="<spring:message code="tool.pagination.back"/>">
							<i class="cmsms-icon-${langage.lang == 'ar' ? 'right' : 'left'}-open"></i>
						</a>
					</li>
					<li>
						<a id="screenNext" class="btn btn-icon btn-simple" title="<spring:message code="tool.pagination.next"/>">
							<i class="cmsms-icon-${langage.lang == 'ar' ? 'left' : 'right'}-open"></i>
						</a>
					</li>
				</ul>
			</nav>
			<input type="hidden" id="defaultResult" value="${pageScope.providers.split(',')[currentConfig.defaultResult]}" />
		</div>
	</div>
</sec:authorize>