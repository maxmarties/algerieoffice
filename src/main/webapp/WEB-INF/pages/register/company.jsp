<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<div class="container">
	<div class="row m-t-20">
		<div class="col-lg-8 m-b-20">
			<div class="register-form">
				<h1 class="h-header h-header2 i-primary m-t-10"><spring:message code="header.register1"/></h1>
				<p class="m-t-10"><spring:message code="txt.register2"/></p>
				<hr class="my-4">
				<div class="wizard wizard-register">
					<form:form name="companyForm" action="/" method="POST" modelAttribute="company" enctype="multipart/form-data" novalidate="novalidate">
						<div class="wizard-card">
							<div class="wizard-nav">
								<ul class="nav nav-pills">
									<c:set var="providers" value="user-male,building-filled,location-5,shield" scope="page"></c:set>
									<c:forEach var="provider" items="${pageScope.providers}" varStatus="state">
										<c:set var="fatitle" scope="page"><spring:message code="wizard.regsiter1.${state.count}"/></c:set>
										<li style="width: 25%;">
											<a class="lien" data-toggle="tab" title="${pageScope.fatitle}">
												<i class="cmsms-icon-${pageScope.provider} m-r-5"></i>
												<span class="hidden-sm-down"><spring:message code="wizard.regsiter1.${state.count}"/></span>
											</a>
										</li>
									</c:forEach>
								</ul>
							</div>
							<div class="wizard-content m-t-20">
								<div class="wizard-body">
									<c:forEach var="i" begin="1" end="4" step="1">
										<div id="tab-pill${i}" class="animated onne fadeIn" style="${i != 1 ? 'display:none;' : ''}">
											<c:import url="/WEB-INF/pages/register/forms/form_register${i}.jsp"/>
										</div>
									</c:forEach>
								</div>
								<div class="wizard-footer">
									<div class="pull-left">
										<button type="button" class="btn btn-segond btn-previous btn-add btn-left" style="display:none;">
											<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'left' : 'right'}-4"></i><spring:message code="btn.previous"/></span>
										</button>
									</div>
									<div id="submitForm" class="form-submit pull-right">
										<button type="button" class="btn btn-primary btn-next btn-add btn-right">
											<span><i class="cmsms-icon-${langage.clazz == 'fr' ? 'right' : 'left'}-4"></i><spring:message code="btn.next"/></span>
										</button>
					            		<button type="submit" class="btn btn-primary btn-finish btn-submit btn-add btn-left" style="display:none;">
					            			<span><i class="cmsms-icon-login-1"></i><spring:message code="btn.signin"/></span>
					            		</button>
									</div>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</form:form>
				</div>
				<hr class="my-4">
				<p class="font-small m-t-20"><spring:message code="txt.help.register1"/>
					<a href="<c:url value="/infos/cgu" />" class="lien lien-hover lien-primary" target="_blank"><spring:message code="lien.terme"/></a>.</p>
				<p class="font-small m-b-10"><spring:message code="txt.help.login"/>
					<a href="<c:url value="/users/login" />" class="lien lien-hover lien-primary"><spring:message code="btn.partner2"/></a> !</p>
			</div>
		</div>
		<div class="col-lg-4 m-b-20">
			<div class="register-screen background-container"
				style="background-image: radial-gradient(circle at center, rgba(24,31,42,.6) 50%, rgba(24,31,42,.8) 100%), url('<c:url value="/static/picts/images/register2-min.jpg" />');">
				<div class="register-company">
					<div class="row">
						<div class="col-md-6 col-lg-12 m-t-20">
							<h3 class="h-header h-header3 i-white sh-black"><spring:message code="txt.register2.1"/></h3>
							<ul class="list-none list-block list-begin">
								<c:forEach var="i" begin="1" end="4" step="1">
									<li>
										<span class="h-header font-bold breadview-trigger pull-left"><c:out value="${i}"/></span>
										<p class="sh-black"><spring:message code="txt.register2.1.${i}"/></p>
										<span class="clearfix"></span>
									</li>
								</c:forEach>
							</ul>
						</div>
						<div class="col-md-6 col-lg-12 m-t-20">
							<h3 class="h-header h-header4 i-white sh-black"><spring:message code="txt.register2.2"/></h3>
							<ul class="list-none list-block list-last">
								<c:forEach var="i" begin="1" end="5" step="1">
									<li>
										<i class="cmsms-icon-ok-2 breadview-trigger pull-left"></i>
										<p class="sh-black"><spring:message code="txt.register2.2.${i}"/></p>
										<span class="clearfix"></span>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>