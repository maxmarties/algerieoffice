<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/overview/header"/>" class="lien lien-black">
			<i class="cmsms-icon-website m-r-5"></i><spring:message code="sidebar.company.dashboard4"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard4.7" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard4.7"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.overview7"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<div class="row">
			<div class="col-md-6 col-lg-4">
				<form:form name="mainthinkForm" action="/" method="POST" modelAttribute="mainthink" enctype="multipart/form-data" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="idents"><form:input type="hidden" path="idents" /></spring:bind>
					<spring:bind path="thinks"><form:input type="hidden" path="thinks" /></spring:bind>
					<spring:bind path="titles"><form:input type="hidden" path="titles" /></spring:bind>
					<spring:bind path="descriptions"><form:input type="hidden" path="descriptions" /></spring:bind>
					<spring:bind path="photosUUID"><form:input type="hidden" path="photosUUID" /></spring:bind>
					<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
					<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
					<spring:bind path="updateThink"><form:input type="hidden" path="updateThink" /></spring:bind>
					<div id="itemsForm" class="form-group m-t-10">
						<label class="col-form-label">
							<spring:message code="lbl.thinks" /> 
							<small class="min">(<span id="itemSize"><c:out value="${mainthink.thinks.size()}"/></span>/08)</small>
							<span class="help-text"><spring:message code="txt.help.think1" /></span>
						</label>
						<table id="tableItems" class="table table-page m-t-10 ${!mainthink.thinks.isEmpty() ? 'm-b-20' : ''}">
							<thead>
								<tr>
									<th style="width:26px;"></th><th style="width:calc(40% - 60px);"></th><th style="width:calc(60% - 34px);"></th>
									<th style="width:34px;"></th><th style="width:34px;"></th>
								</tr>
							</thead>
							<tbody class="font-small">
								<c:forEach var="itemThink" items="${mainthink.thinks}" varStatus="state">
									<tr id="lineItem${state.count}" data-ident="${mainthink.idents.get(state.count - 1)}" 
										data-uuid="${mainthink.photosUUID.get(state.count - 1)}">
										<td><i class="cmsms-icon-bookmark i-help"></i></td>
										<td class="i-input"><c:out value="${itemThink}" /></td>
										<td><c:out value="${mainthink.titles.get(state.count - 1)}" /></td>
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
						<span class="block"><span class="error"></span></span>
						<input type="hidden" id="countItems" value="${mainthink.thinks.size()}" />
						<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left ${mainthink.thinks.size() == 8 ? 'disabled' : ''}">
							<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.think"/></span>
						</a>
						<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
					</div>
					<hr class="my-4">
					<p class="font-mini">
						<spring:message code="txt.company.overview1.1"/> 
						<a href="<c:url value="https://imagecompressor.com/fr/" />" class="lien lien-hover lien-primary" target="_blank">
						<spring:message code="lien.optimizilla"/></a> <spring:message code="txt.company.overview1.2"/>
					</p>
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
			<div class="col-md-6 col-lg-8 m-t-20">
				<div class="bn-overview bn-body" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
					<div class="bn-box think-overview m-auto">
						<div id="thinkOverview" class="row row-mini">
							<c:forEach var="photoUUID" items="${mainthink.photosUUID}" varStatus="state">
								<div id="itemOverview${state.count}" class="col-sm-6 col-lg-3 m-t-10 m-b-10">
									<div class="widget-think background-container h-100" 
										style="background-image: radial-gradient(circle at center, rgba(32,42,56,.54) 20%, #242A38 100%), url('<c:url value="/media/photo?photoId=${photoUUID}"/>');">
										<h2 class="h-header h-header1 text-truncate i-yellow sh-black"><c:out value="${mainthink.thinks.get(state.count - 1)}"/></h2>
										<h3 class="h-header h-header3 text-truncate i-white m-t-20"><c:out value="${mainthink.titles.get(state.count - 1)}"/></h3>
										<p class="font-small i-gray m-t-10"><c:out value="${mainthink.descriptions.get(state.count - 1)}"/></p>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</sec:authorize>
</div>