<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/manage/display"/>" class="lien lien-black">
			<i class="cmsms-icon-sliders m-r-5"></i><spring:message code="sidebar.company.dashboard5"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard5.3" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title m-b-20">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard5.3"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.manage3"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_MANAGER_PRIVILEGE')">
		<div class="row">
			<div class="col-md-8 m-b-20">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="subheader.widget"/></h2>
				<form:form name="widgetForm" action="/" method="POST" modelAttribute="widget" enctype="multipart/form-data" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<spring:bind path="enabled">
						<div class="form-group row">
							<label class="col-form-label col-md-4 col-lg-3">
								<spring:message code="tabs.pin" />
								<span class="help-text"><spring:message code="txt.help.sticky2.4" /></span>
							</label>
							<div class="col-md-8 col-lg-9">
								<ul class="nav">
									<li class="m-r-10"><spring:message code="lbl.sub.display1.1.3" /></li>
									<li><label class="ui-switch ui-switch-action"><form:checkbox path="enabled" /><span class="input-span"></span><span class="layer-span"></span></label></li>
									<li class="m-l-10"><spring:message code="lbl.sub.display1.1.4" /></li>
								</ul>
							</div>
						</div>
					</spring:bind>
					<div id="fileForm" class="form-group row">
						<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
						<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="lbl.companyabout" />
							<span class="help-text"><spring:message code="txt.help.sticky2.1" /></span>
						</label>
						<div class="col-md-8 col-lg-9">
							<div class="avatar-content avatar-extrawid">
								<div class="avatar-view">
									<img id="avatarImg" class="img-responsive transition-35"
										src="<c:url value="${widget.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
									<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
								</div>
								<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
									style="${!widget.hasAvatar ? 'display:none;' : ''}"><i class="cmsms-icon-trash-7"></i>
								</a>
							</div>
							<span class="error"></span>
							<hr class="my-4">
						</div>
					</div>
					<spring:bind path="category">
						<div id="categoryForm" class="form-group row" data-toggle="buttons">
							<form:label class="col-form-label col-md-4 col-lg-3" path="category">
								<spring:message code="tabs.category" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.sticky2.2" /></span>
							</form:label>
							<div class="col-md-8 col-lg-9">
								<ul class="nav nav-campaign m-b-10">
									<c:set var="providers" value="food,home-1,medkit,basket-1,bus,flight,extinguisher,hammer,ellipsis" scope="page"></c:set>
									<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
										<li class="${state.count > 3 ? 'm-t-20' : ''}">
											<label class="btn btn-campaign btn-simple m-auto ${widget.category == state.count ? 'active' : ''}" style="min-height:120px;">
												<i class="cmsms-icon-${pageScope.provider} i-24"></i>
												<span class="help-text m-t-10"><spring:message code="chose.family.btoc${state.count}"/></span>
												<form:radiobutton class="hidden" value="${state.count}" path="category" />
											</label>
										</li>
									</c:forEach>
								</ul>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
					<div id="createForm" class="animated onne fadeIn m-b-0" style="${empty widget.category ? 'display:none;' : ''}">
						<spring:bind path="activity">
							<div id="activityForm" class="form-group row">
								<form:label class="col-form-label col-md-4 col-lg-3" path="activity">
									<spring:message code="tabs.activity" /> <small class="min"><spring:message code="lbl.requis" /></small>
									<span class="help-text"><spring:message code="txt.help.sticky2.3" /></span>
								</form:label>
								<div class="col-md-6 col-lg-4">
									<form:select class="form-select2" path="activity">
										<c:set var="providers" value="7,7,14,18,10,9,8,8,6" scope="page"></c:set>
										<option></option>
										<c:forEach var="i" begin="1" end="${empty widget.category ? 7 : pageScope.providers.split(',')[widget.category - 1]}" step="1">
											<option value="i" ${widget.activity == i ? 'selected' : ''}><spring:message code="chose.family.btoc${empty widget.category ? 1 : widget.category}.${i}" /></option>
										</c:forEach>
									</form:select>
									<span class="error"></span>
								</div>
							</div>
						</spring:bind>
						<div class="form-group row">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<spring:bind path="filtred">
									<label class="ui-checkbox ui-checkbox-segond font-small">
										<form:checkbox path="filtred" />
										<span class="input-span"></span><spring:message code="comp.widget" />
									</label>
								</spring:bind>
							</div>
						</div>
						<div class="form-group row m-t-0 m-b-20">
							<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
							<div class="col-md-8 col-lg-9">
								<hr class="my-4">
								<div id="submitForm" class="form-submit m-t-10">
									<button type="submit" class="btn btn-primary btn-sm btn-submit btn-add btn-left">
										<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</div>
			<div class="col-md-4 m-b-20">
				<h2 class="h-header h-header4 i-primary m-t-10"><spring:message code="lbl.sub.account1"/></h2>
				<div class="bn-overview bn-body m-t-20" style="background: url('<c:url value="/static/vectors/maskdark-min.png" />') repeat;">
					<div class="widgetb2c m-auto" data-dir="${widgetView.language == 'ar' ? 'rtl' : 'ltr'}">
						<div id="coverOverview" class="inner-thumbnail background-container" 
							style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="${widget.urlCover}" />');">
							<span class="ind-language text-center text-uppercase" data-ind="${widgetView.language}"><c:out value="${widgetView.language}"/></span>
							<c:if test="${currentCompany.premium != 0}">
								<div class="ind-premium text-center btn-warning">
									<span class="m-r-5"><c:out value="PRO"/></span>
									<ul class="list-none list-premium list-inline">
										<c:forEach var="i" begin="1" end="${currentCompany.premium}" step="1"><li><i class="cmsms-icon-plus-circled"></i></li></c:forEach>
									</ul>
								</div>
							</c:if>
						</div>
						<div class="widgetb2c-centent flexed flex-colone flex-jusitify h-100">
							<div>
								<div class="company-avatar"><img class="img-responsive" src="<c:url value="${widgetView.urlLogo}"/>" alt="<c:out value="${currentCompany.tradename}" />"></div>
								<div class="company-content">
									<div style="min-height:64px;padding-top:3px;">
										<a class="lien lien-company font-bold sh-black"><c:out value="${currentCompany.tradename}" /></a>
										<p class="font-mini i-gray sh-black" style="line-height:14px;"><i class="cmsms-icon-location-1 i-red m-r-10"></i><c:out value="${widgetView.address}" /></p>
									</div>
									<p class="font-bold i-segond" style="line-height:14px;"><spring:message code="chose.activity.${widgetView.activity}"/></p>
								</div>
								<div class="clearfix"></div>
								<hr class="m-t-10 m-b-5">
								<ul class="nav list-evaluation">
									<c:forEach var="i" begin="1" end="4" step="1"><li class="i-yellow"><i class="cmsms-icon-star-1"></i></li></c:forEach>
									<li class="font-small font-bold i-yellow m-l-5"><c:out value="(2)"/></li>
									<li class="font-small font-bold i-blue ml-auto"><i class="cmsms-icon-thumbs-up m-r-5"></i><c:out value="100%"/></li>
								</ul>
							</div>
							<ul class="navbar-nav nav-flex-icons nav-company">
								<li><a class="lien" title="<spring:message code="tool.navigate.company1"/>"><i class="cmsms-icon-call-out"></i></a></li>
								<li><a class="lien" title="<spring:message code="tool.navigate.company3"/>"><i class="cmsms-icon-mail-1"></i></a></li>
								<li><a class="lien"title="<spring:message code="tool.navigate.company2"/>"><i class="cmsms-icon-star-3"></i></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</sec:authorize>
</div>