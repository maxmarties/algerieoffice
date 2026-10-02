<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/overview/header"/>" class="lien lien-black">
			<i class="cmsms-icon-website m-r-5"></i><spring:message code="sidebar.company.dashboard4"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard4.5" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard4.5"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.overview5"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<div class="row m-t-10">
			<div class="col-md-6 col-lg-4">
				<form:form name="timelineForm" action="/" method="POST" modelAttribute="timeline" enctype="utf8" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="idents"><form:input type="hidden" path="idents" /></spring:bind>
					<spring:bind path="linesDate"><form:input type="hidden" path="linesDate" /></spring:bind>
					<spring:bind path="titles"><form:input type="hidden" path="titles" /></spring:bind>
					<spring:bind path="descriptions"><form:input type="hidden" path="descriptions" /></spring:bind>
					<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
					<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
					<spring:bind path="updateTimeline"><form:input type="hidden" path="updateTimeline" /></spring:bind>
					<spring:bind path="history">
						<div id="historyForm" class="form-group m-t-10">
							<label class="col-form-label">
								<spring:message code="lbl.history" />
								<span class="help-text"><spring:message code="txt.help.timeline1.1" /></span>
							</label>
							<form:textarea class="form-control form-area" rows="4" path="history" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<hr class="my-4">
					<div id="itemsForm" class="form-group">
						<label class="col-form-label">
							<spring:message code="lbl.keysdate" /> 
							<span class="help-text"><spring:message code="txt.help.timeline1.2" /></span>
						</label>
						<table id="tableItems" class="table table-page m-t-10 ${!timeline.titles.isEmpty() ? 'm-b-20' : ''}">
							<thead>
								<tr>
									<th style="width:26px;"></th><th style="width:calc(40% - 34px);"></th>
									<th style="width:calc(60% - 60px);"></th><th style="width:34px;"></th><th style="width:34px;"></th>
								</tr>
							</thead>
							<tbody class="font-small">
								<c:forEach var="itemTitle" items="${timeline.titles}" varStatus="state">
									<tr id="lineItem${state.count}" data-ident="${timeline.idents.get(state.count - 1)}">
										<td><i class="cmsms-icon-clock i-help"></i></td>
										<td class="i-input"><c:out value="${timeline.linesDate.get(state.count - 1)}" /></td>
										<td><c:out value="${itemTitle}" /></td>
										<td class="btn-td">
											<a class="btn btn-table btn-yellow" title="<spring:message code="btn.edit" />" 
												onclick="editLineItem('${state.count}');"><i class="cmsms-icon-pencil-5"></i></a>
										</td>
										<td class="btn-td">
											<a class="btn btn-table btn-red" title="<spring:message code="btn.delete" />" 
												onclick="deleteLineItem('${state.count}');"><i class="cmsms-icon-trash-7"></i></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<input type="hidden" id="countItems" value="${timeline.titles.size()}" />
						<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left ${timeline.titles.size() == 50 ? 'disabled' : ''}">
							<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.date"/></span>
						</a>
						<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
					</div>
					<div class="form-group m-t-20 m-b-20">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
							</button>
						</div>
					</div>
				</form:form>
			</div>
			<div class="col-md-6 col-lg-8">
				<div class="bn-overview bn-body" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
					<div class="bn-box timeline-overview m-auto">
						<hr class="my-4">
						<ul id="timelineOverview" class="list-none m-t-20">
							<c:forEach var="itemTitle" items="${timeline.titles}" varStatus="state">
								<li id="timelineItem${state.count}">
									<div class="timeline-item flexed">
										<div class="timeline-header"><c:out value="${timeline.getYearlinesDate(state.count - 1)}"/></div>
										<div class="timeline-body">
											<h2 class="h-header h-header4 i-primary"><c:out value="${itemTitle}" /></h2>
											<p class="font-small i-help m-t-10"><c:out value="${timeline.descriptions.get(state.count - 1)}" /></p>
										</div>
									</div>
								</li>
							</c:forEach>
							<li class="empty-tr" style="${!timeline.titles.isEmpty() ? 'display:none;' : ''}"><spring:message code="tool.empty.timeline" /></li>
						</ul>
					</div>
				</div>
				<hr class="my-4">
				<p class="font-mini"><spring:message code="txt.company.overview5.1"/></p>
			</div>
		</div>
	</sec:authorize>
</div>