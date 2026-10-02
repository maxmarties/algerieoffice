<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/profile/identity"/>" class="lien lien-black">
			<i class="cmsms-icon-building-filled m-r-5"></i><spring:message code="sidebar.company.dashboard6"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard6.5" /></li>
	</ol>
	<a id="collapsePanel" title="<spring:message code="tooltip.panel.${currentConfig.panelCollapse ? 'open' : 'close'}" />"><i class="cmsms-icon-menu-3"></i></a>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard6.5"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.profile5"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_ADMIN_PRIVILEGE')">
		<form:form name="linkedForm" action="/" method="POST" modelAttribute="linked" enctype="multipart/form-data" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<c:if test="${currentCompany.enabled}">
				<spring:bind path="url">
					<div id="urlForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="url">
							<spring:message code="lbl.companyurl" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.url" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.companyurl" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<div class="input-group">
								<div class="input-group-lien"><c:out value="${staticURL}"/><c:out value="${urlCompanies}"/></div>
								<div class="input-group-icon input-group-check" data-input="">
									<form:input class="form-control" type="text" path="url" />
								</div>
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="checkedURL"><form:input type="hidden" path="checkedURL" /></spring:bind>
			</c:if>
			<spring:bind path="tageline">
				<div id="tagelineForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="tageline">
						<spring:message code="lbl.tageline" />
						<span class="help-text"><spring:message code="txt.help.tageline" /></span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:input class="form-control" type="text" path="tageline" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="keysword">
				<div id="keyswordForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="keysword">
						<spring:message code="tabs.keys" />
						<span class="help-text">
							<spring:message code="txt.help.keys" arguments="${maxkeysword}" />
							<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.keysword" />"></i>
						</span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:input class="form-control" type="text" path="keysword" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<input type="hidden" id="maxkeysword" value="${maxkeysword}" />
			<spring:bind path="description">
				<div id="descriptionForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="description">
						<spring:message code="lbl.description" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text">
							<spring:message code="txt.help.description" />
							<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.description" />"></i>
						</span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:textarea class="form-control form-area" rows="3" path="description" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<div class="row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
			</div>
			<spring:bind path="idents"><form:input type="hidden" path="idents" /></spring:bind>
			<spring:bind path="websitesName"><form:input type="hidden" path="websitesName" /></spring:bind>
			<spring:bind path="websitesUrl"><form:input type="hidden" path="websitesUrl" /></spring:bind>
			<spring:bind path="websitesType"><form:input type="hidden" path="websitesType" /></spring:bind>
			<spring:bind path="photosUUID"><form:input type="hidden" path="photosUUID" /></spring:bind>
			<spring:bind path="updated"><form:input type="hidden" path="updated" /></spring:bind>
			<spring:bind path="trashed"><form:input type="hidden" path="trashed" /></spring:bind>
			<spring:bind path="updateWebsite"><form:input type="hidden" path="updateWebsite" /></spring:bind>
			<div id="itemsForm" class="form-group row m-t-10">
				<div class="col-md-4 col-lg-3">
					<h2 class="h-header h-header4 i-primary"><spring:message code="lbl.sub.linked2"/></h2>
					<span class="help-text m-t-5"><spring:message code="txt.help.linked1" /></span>
				</div>
				<div class="col-md-8 col-lg-9">
					<table id="tableItems" class="table table-page ${!linked.websitesName.isEmpty() ? 'm-b-20' : ''}">
						<thead>
							<tr>
								<th style="width:26px;"></th><th style="width:calc(60% - 43px);"></th>
								<th style="width:calc(40% - 51px);"></th><th style="width:34px;"></th><th style="width:34px;"></th>
							</tr>
						</thead>
						<tbody class="font-small">
							<c:forEach var="websiteName" items="${linked.websitesName}" varStatus="state">
								<tr id="lineItem${state.count}" data-ident="${linked.idents.get(state.count - 1)}" 
									data-uuid="${linked.photosUUID.get(state.count - 1)}">
									<td><i class="cmsms-icon-globe i-help"></i></td>
									<td class="i-input">
										<a href="<c:url value="${linked.websitesUrl.get(state.count - 1)}" />" 
											class="lien lien-table" target="_blank">
											<c:out value="${websiteName}" />
										</a>
									</td>
									<td><spring:message code="chose.linked${linked.websitesType.get(state.count - 1)}"/></td>
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
					<input type="hidden" id="countItems" value="${linked.websitesName.size()}" />
					<a id="insertItem" class="btn btn-success btn-simple btn-add btn-left ${linked.websitesName.size() >= 6 ? 'disabled' : ''}">
						<span><i class="cmsms-icon-plus"></i><spring:message code="btn.add.website"/></span>
					</a>
					<div id="insertForm" class="form-group b-gray animated onne fadeIn m-b-0" style="display:none;"></div>
					<c:set var="chosesType" scope="page">
						<c:forEach var="i" begin="1" end="5" step="1"><spring:message code="chose.linked${i}" /><c:out value="${i < 5 ? ',' : ''}"/></c:forEach>
					</c:set>
					<input type="hidden" id="chosesType" value="${pageScope.chosesType}" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9"><hr class="my-4"></div>
			</div>
			<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="lbl.sub.linked3"/></h2>
			<div class="row m-t-5">
				<div class="col-md-4 col-lg-3"><span class="help-text"><spring:message code="txt.help.linked3" /></span></div>
			</div>
			<c:set var="providers" value="facebook,twitter,google,linkedin,youtube,instagram" scope="page"></c:set>
			<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
				<spring:bind path="${pageScope.provider}">
					<div id="${pageScope.provider}Form" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="${pageScope.provider}">
							<span class="text-capitalize"><c:out value="${pageScope.provider}"/></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<c:set var="faholder" scope="page"><spring:message code="tool.ind.linked${state.count}" /></c:set>
							<div class="input-group-contact">
								<i class="cmsms-icon-${pageScope.provider} icon-contact"></i>
								<form:input class="form-control" type="url" path="${pageScope.provider}" 
									placeholder="${pageScope.faholder}" data-provider="${pageScope.provider}" />
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
			</c:forEach>
			<input type="hidden" id="companyEnabled" value="${currentCompany.enabled}" />
			<div class="form-group row m-b-20">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-4">
					<div id="submitForm" class="form-submit m-t-20">
						<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>
<div class="panel-sidebar hidden-md-down">
	<div class="panel-header"><h2 class="h-header h-header4 i-primary text-truncate"><spring:message code="subheader.profile5"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<div class="form-group">
				<table class="table table-page table-panel">
					<thead><tr><th style="width:40%;"></th><th style="width:60%;"></th></tr></thead>
					<tbody class="font-small">
						<c:if test="${currentCompany.enabled}">
							<tr>
								<td><spring:message code="lbl.companyurl" /></td>
								<td>
									<a id="urlOverview" href="<c:url value="${urlCompanies}${linked.url}"/>" 
										class="lien lien-primary lien-underline" target="_blank">
										<c:out value="${staticURL}" /><c:out value="${urlCompanies}"/><span class="resultOver"><c:out value="${linked.url}"/></span>
									</a>
								</td>
							</tr>
						</c:if>
						<tr>
							<td><spring:message code="lbl.tageline" /></td>
							<td id="tagelineOverview"><c:out value="${!empty linked.tageline ? linked.tageline : '-'}" /></td>
						</tr>
						<tr>
							<td><spring:message code="tabs.keys" /></td>
							<td id="keyswordOverview">
								<c:choose>
									<c:when test="${!empty linked.keysword}">
										<c:forEach var="keyword" items="${linked.buildKeysword()}" varStatus="state">
											<span class="tag-keyword m-r-5 m-b-5"><c:out value="${keyword}" /></span>
										</c:forEach>
									</c:when>
									<c:otherwise><c:out value="-" /></c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td><spring:message code="lbl.sub.linked1" /></td>
							<td id="descriptionOverview"><c:out value="${linked.description}" /></td>
						</tr>
					</tbody>
				</table>
			</div>
			<h3 class="h-header h-header6 i-primary m-t-20"><spring:message code="lbl.sub.linked2"/></h3>
			<div id="websiteOverview" class="widget-website widget-overview m-t-20" style="${linked.websitesName.isEmpty() ? 'display:none;' : ''}">
				<div class="owl-carousel owl-theme">
					<c:forEach var="websiteName" items="${linked.websitesName}" varStatus="state">
						<div class="item" data-item="${state.count}">
							<span class="tags-type"><spring:message code="chose.linked${linked.websitesType.get(state.count - 1)}" /></span>
							<img class="img-responsive" src="<c:url value="/media/photo?photoId=${linked.photosUUID.get(state.count - 1)}"/>" 
								alt="<c:out value="${websiteName}" />">
							<a href="<c:url value="${linked.websitesUrl.get(state.count - 1)}" />" target="_blank"  
								class="btn btn-segond btn-block m-t-10 m-b-10"><span><spring:message code="btn.website"/></span></a>
						</div>
					</c:forEach>
				</div>
				<hr class="my-4">
			</div>
			<div id="emptyWebsiteOverview" class="form-group form-tr m-t-10" style="${!linked.websitesName.isEmpty() ? 'display:none;' : ''}">
				<spring:message code="tool.empty.website"/>
			</div>
			<h3 class="h-header h-header6 i-primary m-t-20"><spring:message code="lbl.sub.linked3"/></h3>
			<div class="form-group m-t-10 m-b-20">
				<ul class="navbar-nav nav-flex-icons navbar-linked">
					<c:set var="providers" value="facebook,twitter,google,linkedin,youtube,instagram" scope="page"></c:set>
					<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
						<li>
							<c:set var="builder" value="${linked.buildSocial(pageScope.provider)}" scope="page"></c:set>
							<a id="${pageScope.provider}Overview" href="<c:url value="${pageScope.builder}" />" 
								class="btn btn-linked btn-${pageScope.provider}" target="_blank" 
								style="${empty pageScope.builder ? 'display:none;' : ''}">
								<i class="cmsms-icon-${pageScope.provider}"></i></a>
						</li>
					</c:forEach>
				</ul>
				<div id="emptyLinkedOverview" class="form-group form-tr m-t-10" style="${linked.hasSocial() ? 'display:none;' : ''}">
					<spring:message code="tool.empty.social"/>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>