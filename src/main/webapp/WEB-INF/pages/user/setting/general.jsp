<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/settings/general"/>" class="lien lien-black">
			<i class="cmsms-icon-cog-5 m-r-5"></i><spring:message code="sidebar.user.dashboard7"/></a></li>
		<li class="active"><spring:message code="sidebar.user.dashboard7.1" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.setting.general"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.setting1.1"/></p>
	</div>
	<div class="page-container">
		<form:form name="generalForm" action="/" method="POST" modelAttribute="general" enctype="utf8" novalidate="novalidate">
			<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
			<spring:bind path="welcome">
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.sub.setting3.1" />
						<span class="help-text"><spring:message code="txt.help.setting6.1" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<label class="ui-checkbox ui-checkbox-segond font-small">
							<form:checkbox path="welcome" /><span class="input-span"></span><spring:message code="comp.setting5.1" />
						</label>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="style">
				<div id="styleForm" class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.sub.setting3.2" />
						<span class="help-text"><spring:message code="txt.help.setting6.2" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="row">
							<c:forEach var="i" begin="1" end="3" step="1">
								<div class="col-sm-4 m-b-10">
									<div class="form-manage m-auto h-100 ${general.style == i ? 'selected' : ''}">
										<label class="ui-radio ui-radio-segond font-small">
											<form:radiobutton value="${i}" path="style" />
											<span class="input-span"></span><spring:message code="lbl.sub.setting3.2.${i}" />
										</label>
										<div class="m-t-5">
											<img class="img-responsive" src="<c:url value="/static/vectors/display/m_style${i}-min.jpg"/>" alt="<spring:message code="lbl.sub.setting3.2.${i}" />">
										</div>
										<hr class="my-4">
										<span class="help-text ${i == 3 ? 'i-segond' : ''}"><spring:message code="txt.help.setting6.2.${i}" /></span>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</spring:bind>
			<div class="form-group row">
				<label class="col-form-label col-md-4 col-lg-3">
					<spring:message code="subheader.setting2" />
					<span class="help-text"><spring:message code="txt.help.setting6.3" /></span>
				</label>
				<div class="col-md-8 col-lg-9">
					<c:set var="mins" value="10,20,50,100" scope="page"></c:set>
					<c:set var="maxs" value="10,25,50,100,200" scope="page"></c:set>
					<spring:bind path="token">
						<div class="table-pagination">
							<nav class="navbar">
								<ul class="nav nav-pagination">
									<li class="nav-text font-small m-r-10"><spring:message code="lbl.sub.setting4.1.1"/></li>
									<li class="form-select">
										<form:select class="form-select2-simple" path="token">
											<option></option>
											<c:forEach var="provider" items="${pageScope.mins}" varStatus="state">
												<option value="${state.count}" ${state.count == general.token ? 'selected' : ''}><c:out value="${pageScope.provider}"/></option>
											</c:forEach>
										</form:select>
									</li>
									<li class="nav-text font-small m-l-10"><spring:message code="lbl.sub.setting4.1.2"/></li>
								</ul>
							</nav>
						</div>
					</spring:bind>
					<spring:bind path="row">
						<div class="table-pagination m-t-5">
							<nav class="navbar">
								<ul class="nav nav-pagination">
									<li class="nav-text font-small m-r-10"><spring:message code="lbl.sub.setting4.2.1"/></li>
									<li class="form-select">
										<form:select class="form-select2-simple" path="row">
											<option></option>
											<c:forEach var="provider" items="${pageScope.maxs}" varStatus="state">
												<option value="${state.count}" ${state.count == general.row ? 'selected' : ''}><c:out value="${pageScope.provider}"/></option>
											</c:forEach>
										</form:select>
									</li>
									<li class="nav-text font-small m-l-10"><spring:message code="lbl.sub.setting4.2.2"/></li>
								</ul>
							</nav>
						</div>
					</spring:bind>
					<spring:bind path="result">
						<div class="table-pagination m-t-5">
							<nav class="navbar">
								<ul class="nav nav-pagination">
									<li class="nav-text font-small m-r-10"><spring:message code="lbl.sub.setting4.3.1"/></li>
									<li class="form-select">
										<form:select class="form-select2-simple" path="result">
											<option></option>
											<c:forEach var="provider" items="${pageScope.mins}" varStatus="state">
												<option value="${state.count}" ${state.count == general.result ? 'selected' : ''}><c:out value="${pageScope.provider}"/></option>
											</c:forEach>
										</form:select>
									</li>
									<li class="nav-text font-small m-l-10"><spring:message code="lbl.sub.setting4.3.2"/></li>
								</ul>
							</nav>
						</div>
					</spring:bind>
					<spring:bind path="blog">
						<div class="table-pagination m-t-5">
							<nav class="navbar">
								<ul class="nav nav-pagination">
									<li class="nav-text font-small m-r-10"><spring:message code="lbl.sub.setting4.4.1"/></li>
									<li class="form-select">
										<form:select class="form-select2-simple" path="blog">
											<option></option>
											<c:forEach var="provider" items="${pageScope.mins}" varStatus="state">
												<option value="${state.count}" ${state.count == general.blog ? 'selected' : ''}><c:out value="${pageScope.provider}"/></option>
											</c:forEach>
										</form:select>
									</li>
									<li class="nav-text font-small m-l-10"><spring:message code="lbl.sub.setting4.4.2"/></li>
								</ul>
							</nav>
						</div>
					</spring:bind>
				</div>
			</div>
			<spring:bind path="simultude">
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.sub.setting5" />
						<span class="help-text"><spring:message code="txt.help.setting6.4" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<c:forEach var="i" begin="1" end="2" step="1">
							<div>
								<label class="ui-radio ui-radio-segond font-small">
									<form:radiobutton value="${i == 2}" path="simultude" />
									<span class="input-span"></span><spring:message code="lbl.sub.setting5.${i}" />
								</label>	
							</div>
						</c:forEach>
					</div>
				</div>
			</spring:bind>
			<spring:bind path="scroll">
				<div class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.sub.setting6" />
						<span class="help-text"><spring:message code="txt.help.setting6.5" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<c:forEach var="i" begin="1" end="2" step="1">
							<div>
								<label class="ui-radio ui-radio-segond font-small">
									<form:radiobutton value="${i == 1}" path="scroll" />
									<span class="input-span"></span><spring:message code="lbl.sub.setting6.${i}" />
								</label>	
							</div>
						</c:forEach>
					</div>
				</div>
			</spring:bind>
			<div class="form-group row">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-2">
					<a href="<c:url value="/user/delete"/>" class="lien lien-red lien-hover"><spring:message code="lien.account.delete"/></a>
					<span class="help-text m-t-5"><spring:message code="txt.help.setting7" /></span>
				</div>
			</div>
			<div class="form-group row m-t-10 m-b-30">
				<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
				<div class="col-md-8 col-lg-9">
					<hr class="my-1">
					<div id="submitForm" class="form-submit m-t-10">
						<button type="submit" class="btn btn-primary btn-sm btn-submit btn-add btn-left">
							<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.update"/></span>
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>