<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/marketplace/promotes"/>" class="lien lien-black">
			<i class="cmsms-icon-pin-1 m-r-5"></i><spring:message code="sidebar.admin.dashboard4"/></a></li>
		<li><a href="<c:url value="/admin/marketplace/promotes"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard4.1"/></a></li>
		<li class="active"><spring:message code="btn.edit" /></li>
	</ol>
</div>
<div class="page-wrapper page-panel">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.marketplace.promote.edit"/></h1>
		<p class="m-t-5"><spring:message code="txt.admin.promote"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/marketplace/promotes" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<sec:authorize access="hasAuthority('SUPPORT_MANAGER_PRIVILEGE')">
		<form:form name="promoteForm" action="/" method="POST" modelAttribute="promote" enctype="multipart/form-data" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
			<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
			<div id="fileForm" class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="lbl.image" />
					<span class="help-text"><spring:message code="txt.help.marketplace1.1" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<div class="avatar-content avatar-promote">
						<div class="avatar-view">
							<img id="avatarImg" class="img-responsive transition-35"
								src="<c:url value="${promote.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
							<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
						</div>
						<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
							style="${promote.hasAvatar ? '' : 'display:none;'}">
							<i class="cmsms-icon-trash-7"></i>
						</a>
					</div>
					<span class="error"></span>
				</div>
			</div>
			<spring:bind path="title">
				<div id="titleForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="title">
						<spring:message code="tabs.title" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.marketplace1.2" /></span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:input class="form-control" type="text" path="title" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="description">
				<div id="descriptionForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="description">
						<spring:message code="tabs.descrptif" /> <small class="min"><spring:message code="lbl.requis" /></small>
						<span class="help-text"><spring:message code="txt.help.marketplace1.3" /></span>
					</form:label>
					<div class="col-md-8 col-lg-9">
						<form:textarea class="form-control form-area" rows="2" path="description" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<div id="urlForm" class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="tabs.url" /> <small class="min"><spring:message code="lbl.requis" /></small>
					<span class="help-text"><spring:message code="txt.help.marketplace1.5" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<div>
						<label class="ui-radio ui-radio-segond font-small">
							<form:radiobutton value="${true}" path="hasURL" />
							<span class="input-span"></span><spring:message code="lbl.sub.sticky1.2.2" />
						</label>
						<c:set var="faholder" scope="page"><spring:message code="tool.ind.linked" /></c:set>
						<spring:bind path="url">
							<form:input class="form-control" type="url" path="url" placeholder="${pageScope.faholder}" 
							disabled="${!promote.hasURL}" />
							<span class="error"></span>
						</spring:bind>
					</div>
					<div class="m-t-10">
						<label class="ui-radio ui-radio-segond font-small">
							<form:radiobutton value="${false}" path="hasURL" />
							<span class="input-span"></span><spring:message code="lbl.sub.sticky1.2.3" />
						</label>
					</div>
				</div>
			</div>
			<spring:bind path="viewCount">
				<div id="viewCountForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="viewCount">
						<spring:message code="tabs.eye" /> <small class="min"><spring:message code="lbl.requis" /></small>
					</form:label>
					<div class="col-md-4 col-lg-3">
						<form:input class="form-control" type="number" path="viewCount" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="creditCount">
				<div id="creditCountForm" class="form-group row">
					<form:label class="col-form-label col-md-4 col-lg-3" path="creditCount">
						<spring:message code="tabs.credit" /> <small class="min"><spring:message code="lbl.requis" /></small>
					</form:label>
					<div class="col-md-4 col-lg-3">
						<form:input class="form-control" type="number" path="creditCount" />
						<span class="error"></span>
					</div>
				</div>
			</spring:bind>
			<div class="form-group row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<spring:bind path="enabled">
						<label class="ui-checkbox ui-checkbox-segond font-small">
							<form:checkbox path="enabled" />
							<span class="input-span"></span><spring:message code="comp.admin.promote" /> 
						</label>
					</spring:bind>
					<hr class="my-2">
					<p class="font-mini"><spring:message code="txt.help.admin.promote1"/></p>
				</div>
			</div>
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
	<div class="panel-header"><h2 class="h-header h-header4 i-primary"><spring:message code="wizard.marketplace.promote3"/></h2></div>
	<div class="panel-body">
		<div class="panel-scroll">
			<h3 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.marketplace3.1"/></h3>
			<div class="form-group m-t-20">
				<label class="col-form-label"><spring:message code="lbl.sub.marketplace1.2" /> :</label>
				<ul class="font-small m-t-10">
					<c:forEach var="i" begin="1" end="31" step="1">
						<li style="${promote.inSectors(i) ? '' : 'display:none;'}"><spring:message code="chose.sector${i}"/></li>
					</c:forEach>
				</ul>
			</div>
			<div class="form-group m-t-20">
				<label class="col-form-label"><spring:message code="lbl.sub.marketplace1.3" /> :</label>
				<ul class="font-small m-t-10">
					<li style="${promote.wilayas.isEmpty() ? '' : 'display:none;'}"><spring:message code="comp.target"/></li>
					<c:forEach var="i" begin="1" end="48" step="1">
						<li style="${promote.inWilayas(i) ? '' : 'display:none;'}"><spring:message code="chose.wilaya${i}"/></li>
					</c:forEach>
				</ul>
			</div>
			<hr class="my-4">
			<h3 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.marketplace3.2"/></h3>
			<div class="banner-overview banner-body m-t-20" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
				<div class="widget-promote banner-box m-auto">
					<img id="avatarOverview" class="img-responsive m-b-10" src="<c:url value="${promote.urlAvatar}"/>" 
						alt="<c:out value="${promote.title}" />" style="${promote.hasAvatar ? '' : 'display:none;'}">
					<h4 id="titleOverview" class="h-header h-headerAds i-primary"><c:out value="${promote.title}" /></h4>
					<p id="descriptionOverview" class="font-small i-help m-t-5"><c:out value="${promote.description}" /></p>
				</div>
			</div>
		</div>
	</div>
	<div class="panel-footer"></div>
</div>