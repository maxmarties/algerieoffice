<%@ include file="/WEB-INF/tags/libs/secs_libs.jsp"%>
<div class="head-crumb">
	<ol class="bread-crumb">
		<li class="disabled"><spring:message code="tooltip.here"/></li>
		<li><a href="<c:url value="/company/overview/header"/>" class="lien lien-black">
			<i class="cmsms-icon-website m-r-5"></i><spring:message code="sidebar.company.dashboard4"/></a></li>
		<li class="active"><spring:message code="sidebar.company.dashboard4.6" /></li>
	</ol>
</div>
<div class="page-wrapper">
	<div class="page-title">
		<h1 class="h-header h-header2 i-primary"><spring:message code="sidebar.company.dashboard4.6"/></h1>
		<p class="m-t-5"><spring:message code="txt.company.overview6"/></p>
	</div>
	<sec:authorize access="hasAuthority('COMPANY_EDIT_PRIVILEGE')">
		<div class="row m-t-10">
			<div class="col-md-6 col-lg-4">
				<form:form name="aboutForm" action="/" method="POST" modelAttribute="about" enctype="utf8" novalidate="novalidate">
					<spring:bind path="id"><form:input type="hidden" path="id" /></spring:bind>
					<div id="fileForm" class="form-group m-t-10">
						<spring:bind path="hasAvatar"><form:input type="hidden" path="hasAvatar" /></spring:bind>
						<spring:bind path="hasFileChanged"><form:input type="hidden" path="hasFileChanged" /></spring:bind>
						<label class="col-form-label">
							<spring:message code="lbl.companyabout" />
							<span class="help-text"><spring:message code="txt.help.about1.1" /></span>
						</label>
						<div class="m-t-5">
							<div class="avatar-content avatar-actu">
								<div class="avatar-view">
									<img id="avatarImg" class="img-responsive transition-35"
										src="<c:url value="${about.urlAvatar}"/>" alt="<spring:message code="tooltip.avatar" />">
									<input type="file" id="choseAvatar" accept="image/*" title="<spring:message code="tooltip.image" />">
								</div>
								<a id="clearAvatar" class="btn btn-table btn-red" title="<spring:message code="btn.delete.photo"/>" 
									style="${!about.hasAvatar ? 'display:none;' : ''}"><i class="cmsms-icon-trash-7"></i>
								</a>
							</div>
							<span class="error"></span>
						</div>
					</div>
					<spring:bind path="word">
						<div id="wordForm" class="form-group">
							<label class="col-form-label">
								<spring:message code="tabs.message" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.about1.2" /></span>
							</label>
							<c:set var="faholder" scope="page"><spring:message code="txt.help.about1" /></c:set>
							<form:textarea class="form-control form-area" rows="4" path="word" placeholder="${pageScope.faholder}" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="name">
						<div id="nameForm" class="form-group">
							<label class="col-form-label">
								<spring:message code="tabs.name" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.about1.3" /></span>
							</label>
							<form:input class="form-control" type="text" path="name" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="function">
						<div id="functionForm" class="form-group">
							<label class="col-form-label">
								<spring:message code="tabs.function" /> <small class="min"><spring:message code="lbl.requis" /></small>
							</label>
							<form:input class="form-control" type="text" path="function" />
							<span class="error"></span>
						</div>
					</spring:bind>
					<spring:bind path="size">
						<div id="sizeForm" class="form-group">
							<form:label class="col-form-label" path="size">
								<spring:message code="tabs.size" /> <small class="min"><spring:message code="lbl.requis" /></small>
								<span class="help-text"><spring:message code="txt.help.about1.4" /></span>
							</form:label>
							<div>
								<form:select class="form-select2-simple" path="size">
									<c:forEach var="i" begin="1" end="3" step="1">
										<option value="${i}" ${about.size == i ? 'selected' : ''}><spring:message code="chose.about.size${i}" /></option>
									</c:forEach>
								</form:select>
								<span class="error"></span>
							</div>
						</div>
					</spring:bind>
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
					<div class="bn-box about-overview m-auto">
						<div id="coverOverview" class="mainabout mainabout-${about.size} background-container" 
							style="background-image: url('<c:url value="${about.hasAvatar ? about.urlAvatar : '/static/picts/aobns/about-min.jpg'}" />');">
							<div class="mainabout-overlay flexed flex-colone flex-jusitify">
								<p class="sh-black"><i class="cmsms-icon-quote i-segond1 m-r-10"></i><span id="wordOverview"><c:out value="${about.word}"/></span></p>
								<div class="m-t-20">
									<h2 id="nameOverview" class="h-header h-header5"><c:out value="${about.name}"/></h2>
									<span id="functionOverview" class="font-small i-gray"><c:out value="${about.function}"/></span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</sec:authorize>
</div>