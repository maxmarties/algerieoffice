<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/admin/realtime/companies"/>" class="lien lien-black">
			<i class="cmsms-icon-flag m-r-5"></i><spring:message code="sidebar.admin.dashboard9"/></a></li>
		<li><a href="<c:url value="/admin/realtime/testimonials"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard9.3"/></a></li>
		<li class="active"><spring:message code="btn.${empty testimonial.id ? 'add' : 'edit'}" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="header.testimonial.${empty testimonial.id ? 'new' : 'edit'}"/></h1>
		<p><spring:message code="txt.admin.realtime3.1"/></p>
	</div>
	<c:set var="backwordURL" value="/admin/realtime/testimonials" scope="request"></c:set>
	<c:set var="backwordPage" value="0" scope="request"></c:set>
	<c:import url="/WEB-INF/fields/tables/table_backword.jsp"/>
	<div class="row">
		<div class="col-lg-9">
			<form:form name="testimonialForm" action="/" method="POST" modelAttribute="testimonial" enctype="utf8" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<c:if test="${!empty testimonial.id && !empty testimonial.userId}">
					<div class="form-group row">
						<label class="col-form-label col-md-4 col-lg-3"><spring:message code="tabs.user" /></label>
						<div class="col-md-8 col-lg-9">
							<table class="table table-page table-panel">
								<thead><tr><th style="width:100%;"></th></tr></thead>
								<tbody class="font-small">
									<tr>
										<td>
											<div class="form-identity">
												<img src="<c:url value="${testimonial.urlAvatar}"/>" class="pull-left img-circle" alt="<c:out value="${testimonial.displayname}" />">
												<div class="identity-brand">
													<a href="<c:url value="/membres?id=${testimonial.userId}" />" class="lien lien-table" target="_blank">
														<c:out value="${testimonial.displayname}" />
													</a>
													<span class="help-text"><c:out value="${testimonial.companyname}"/></span>
												</div>
												<span class="clearfix"></span>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>	
				</c:if>
				<spring:bind path="note">
					<div id="noteForm" class="form-group row m-t-30">
						<label class="col-form-label col-md-4 col-lg-3">
							<spring:message code="tabs.note" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.tsetimonial1.2" /></span>
						</label>
						<div class="col-md-8 col-lg-9">
							<ul class="navbar-nav nav-flex-icons nav-testimonial">
								<li class="i-help"><spring:message code="lbl.sub.testimonial1" /></li>
								<c:forEach var="i" begin="1" end="10" step="1">
									<li>
										<span class="font-small text-center block m-b-5"><c:out value="${i}"/></span>
										<label class="ui-radio ui-radio-segond font-small"><form:radiobutton value="${i}" path="note" /><span class="input-span"></span></label>
									</li>
								</c:forEach>
								<li class="i-help"><spring:message code="lbl.sub.testimonial2" /></li>
							</ul>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="username">
					<div id="usernameForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="username">
							<spring:message code="tabs.username" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.tsetimonial1.1" /></span>
						</form:label>
						<div class="col-md-8 col-lg-6">
							<form:input class="form-control" type="text" path="username" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="function">
					<div id="functionForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="function">
							<spring:message code="tabs.function" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-6">
							<form:input class="form-control" type="text" path="function" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="tradename">
					<div id="tradenameForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="tradename">
							<spring:message code="tabs.company" />
						</form:label>
						<div class="col-md-8 col-lg-6">
							<form:input class="form-control" type="text" path="tradename" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="message">
					<div id="messageForm" class="form-group row m-b-20">
						<form:label class="col-form-label col-md-4 col-lg-3" path="message">
							<spring:message code="tabs.message" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.tsetimonial1.3" /></span>
						</form:label>
						<div class="col-md-8 col-lg-6">
							<form:textarea class="form-control form-area" rows="4" path="message" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="approuved">
					<div class="form-group row">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<label class="ui-checkbox ui-checkbox-segond font-small">
				            	<form:checkbox path="approuved" />
								<span class="input-span"></span><spring:message code="comp.admin.testimonial" />
							</label>
						</div>
					</div>
				</spring:bind>
				<div class="form-group row m-t-0 m-b-20">
					<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
					<div class="col-md-8 col-lg-6">
						<hr class="my-4">
						<div id="submitForm" class="form-submit m-t-20">
							<button type="submit" class="btn btn-primary btn-submit btn-add btn-left">
								<span><i class="cmsms-icon-floppy"></i><spring:message code="btn.${empty testimonial.id ? 'save' : 'update'}"/></span>
							</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>