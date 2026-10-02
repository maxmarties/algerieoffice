<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/user/account/profile"/>" class="lien lien-black">
			<i class="cmsms-icon-user-1 m-r-5"></i><spring:message code="sidebar.admin.dashboard2"/></a></li>
		<li class="active"><spring:message code="sidebar.admin.dashboard2.4" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.admin.dashboard2.4"/></h1>
		<p class="m-t-5"><spring:message code="txt.user.account2.4"/></p>
	</div>
	<div class="form-container">
		<sec:authorize access="hasAuthority('ACCOUNT_PRIVILEGE')">
			<form:form name="loginForm" action="/" method="POST" modelAttribute="loginform" enctype="multipart/form-data" novalidate="novalidate">
				<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
				<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
				<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
				<div id="fileForm" class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3">
						<spring:message code="lbl.avatar" />
						<span class="help-text"><spring:message code="txt.help.companylogo" /></span>
					</label>
					<div class="col-md-8 col-lg-9">
						<div class="avatar-content avatar-account">
							<div class="avatar-view">
								<img id="avatarImg" class="img-responsive transition-35"
									src="<c:url value="${loginform.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
								<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
							</div>
							<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
								style="${!loginform.hasAvatar ? 'display:none;' : ''}">
								<i class="cmsms-icon-trash-7"></i>
							</a>
						</div>
						<span class="error"></span>
					</div>
				</div>
				<spring:bind path="pseudo">
					<div id="pseudoForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="pseudo">
							<spring:message code="lbl.userurl" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text">
								<spring:message code="txt.help.url" />
								<i class="i-trick m-l-5" data-toggle="tooltip" title="<spring:message code="tooltip.userurl" />"></i>
							</span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<div class="input-group">
								<div class="input-group-lien"><c:out value="${staticURL}"/><c:out value="${urlProfiles}"/></div>
								<div class="input-group-icon input-group-check" data-input="">
									<form:input class="form-control" type="text" path="pseudo" />
								</div>
							</div>
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="checkedPseudo"><form:input type="hidden" path="checkedPseudo" /></spring:bind>
				<spring:bind path="firstname">
					<div id="firstnameForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="firstname">
							<spring:message code="lbl.firstname" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="text" path="firstname" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="lastname">
					<div id="lastnameForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="lastname">
							<spring:message code="lbl.lastname" /> <small class="min"><spring:message code="lbl.requis" /></small>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="text" path="lastname" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="email">
					<div id="emailForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="email">
							<spring:message code="tabs.email" /> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.account4.1" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="email" path="email" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="newpassword">
					<div id="newpasswordForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="newpassword">
							<spring:message code="lbl.sub.newpassword"/>
							<span class="help-text"><spring:message code="txt.help.newpassword" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="password" path="newpassword" value="" />
							<span class="error"></span>
							<span class="help-text"><spring:message code="txt.help.password" /></span>
							<hr class="my-1">
							<p class="font-mini"><spring:message code="txt.help.account4.2" /></p>
							<ul class="font-mini m-t-10">
								<c:forEach var="i" begin="1" end="3" step="1"><li><spring:message code="txt.help.account4.2.${i}" /></li></c:forEach>
							</ul>
						</div>
					</div>
				</spring:bind>
				<div id="matchesForm" class="form-group row">
					<label class="col-form-label col-md-4 col-lg-3" for="matches">
						<spring:message code="lbl.sub.newmatches" />
					</label>
					<div class="col-md-8 col-lg-9">
						<input class="form-control" type="password" id="matches" name="matches" value="" />
						<span class="error"></span>
					</div>
				</div>
				<spring:bind path="password">
					<div id="passwordForm" class="form-group row">
						<form:label class="col-form-label col-md-4 col-lg-3" path="password">
							<spring:message code="lbl.sub.oldpassword"/> <small class="min"><spring:message code="lbl.requis" /></small>
							<span class="help-text"><spring:message code="txt.help.oldpassword" /></span>
						</form:label>
						<div class="col-md-8 col-lg-9">
							<form:input class="form-control" type="password" path="password" value="" />
							<span class="error"></span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="hasAccepte">
					<div class="form-group row">
						<div class="col-md-4 col-lg-3 hidden-sm-down"></div>
						<div class="col-md-8 col-lg-9">
							<label class="ui-checkbox ui-checkbox-segond font-small">
					        	<form:checkbox path="hasAccepte" />
								<span class="input-span"></span><spring:message code="comp.news" />
							</label>
						</div>
	            	</div>
				</spring:bind>
				<div class="form-group row m-t-0 m-b-20">
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
</div>