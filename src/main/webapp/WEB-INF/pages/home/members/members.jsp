<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda" %>
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li class="active"><spring:message code="wizard.screen.navbar6"/></li>
	</ol>
</div>
<div class="screen-container screen-mini">
	<div class="container">
		<div class="header-screen header-actu"><h1 class="h-header m-auto"><spring:message code="subheader.screen.account1"/></h1></div>
		<p class="parag-blog header-actu text-center font-big m-auto">
			<spring:message code="txt.search.account1"/> <span class="h-header font-bold i-segond text-lowercase">
				<c:out value="${countMembers}"/> <spring:message code="wizard.screen.navbar6"/></span>
		</p>
	</div>
</div>
<div class="screen-navnews">
	<div class="container">
		<form:form name="searchMembersForm" action="/" method="POST" modelAttribute="searchMember" enctype="utf8" novalidate="novalidate">
			<spring:bind path="userId"><form:input type="hidden" path="userId" /></spring:bind>
			<spring:bind path="row"><form:input type="hidden" path="row" /></spring:bind>
			<spring:bind path="page"><form:input type="hidden" path="page" /></spring:bind>
			<spring:bind path="letter">
				<div class="form-group text-center m-b-0" data-toggle="buttons">
					<ul class="list-none list-inline list-screenletter">
						<c:forEach var="urlLetter" items="${urlLetters}" varStatus="state">
							<li>
								<label class="btn btn-letter btn-simple ${searchMember.letter == state.count ? 'active' : ''}">
									<spring:message code="chose.letter${state.count}"/>
									<form:radiobutton class="hidden" value="${state.count}" path="letter" />
								</label>
							</li>
						</c:forEach>
					</ul>
				</div>
			</spring:bind>
			<div class="row row-mini">
				<div class="col-md-6 col-lg-3 col-mini">
					<ul class="navbar-nav nav-screenfilter nav-members">
						<li class="dropdown">
							<a class="transition-35 text-truncate" data-toggle="dropdown" title="<spring:message code="tool.filter.statu"/>">
								<i class="breadview-trigger cmsms-icon-down-open"></i>
								<i class="cmsms-icon-filter i-20 m-r-20"></i><span id="resultFilterStatu"><spring:message code="tool.filter.statu"/></span>
							</a>
							<ul class="dropdown-menu" role="menu">
								<li class="dropdown-content" data-toggle="buttons">
									<spring:bind path="statu">
										<ul class="list-none list-block">
											<li>
												<label class="btn btn-simple item-dropdown text-truncate active" title="<spring:message code="chose.members.all"/>">
													<i class="cmsms-icon-cancel-2 m-r-20"></i><spring:message code="chose.members.all"/>
													<form:radiobutton class="hidden" value="0" path="statu" checked="true" />
												</label>
											</li>
											<c:forEach var="i" begin="1" end="2" step="1">
												<c:set var="statuName" scope="page"><spring:message code="chose.members${i}" /></c:set>
												<li>
													<label class="btn btn-simple item-dropdown text-truncate" title="${pageScope.statuName}">
														<c:out value="${pageScope.statuName}"/>
														<form:radiobutton class="hidden" value="${i}" path="statu" data-name="${pageScope.statuName}" />
													</label>
												</li>
											</c:forEach>
										</ul>
									</spring:bind>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="col-md-6 col-lg-3 col-mini">
					<spring:bind path="wilaya">
						<div class="form-group form-filter-ville m-b-0">
							<c:set var="placeholderFilter" scope="page"><spring:message code="tool.filter.wilaya"/></c:set>
							<div class="input-group-ville">
								<i class="cmsms-icon-location i-red trigger-location"></i>
								<form:select class="form-select2" path="wilaya" data-placeholder="${pageScope.placeholderFilter}" >
									<option></option>
									<option value="${0}"><spring:message code="comp.target" /></option>
									<c:forEach var="i" begin="1" end="48" step="1">
										<option value="${i}"><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</spring:bind>
				</div>
				<div class="col-lg-6 col-mini">
					<spring:bind path="token">
						<div class="form-group form-find-news m-b-0">
							<c:set var="placeholderFind" scope="page"><spring:message code="tool.find.user"/></c:set>
							<div class="input-group-screen">
								<i class="cmsms-icon-search-1 icon-screen"></i>
								<form:input class="form-control" type="search" path="token" placeholder="${pageScope.placeholderFind}" />
								<button type="submit" class="btn btn-primary btn-simple" title="<spring:message code="btn.find" />"><span><spring:message code="btn.ok"/></span></button>
							</div>
						</div>
					</spring:bind>
				</div>
			</div>
		</form:form>
	</div>
</div>
<div class="screen-container screen-segond">
	<div class="container">
		<h2 class="h-header h-header3 i-primary">
			<spring:message code="subheader.screen.account2.1"/> <joda:format value="${toDay}" pattern="dd MMMM yyyy"></joda:format>, <spring:message code="subheader.screen.account2.2"/>
		</h2>
		<p class="m-t-5"><spring:message code="txt.search.account2"/></p>
		<hr class="my-4">
		<div id="screenElements" class="m-t-20">
			<div class="screen-loader screen-load-news">
				<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
				<div class="screenLoad"></div>
			</div>
			<c:set var="placeholderViewload" scope="request"><spring:message code="tool.navigate.membres" arguments="${searchMember.row}"/></c:set>
			<c:import url="/WEB-INF/fields/screen/screen_viewload.jsp"/>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/fields/blog/blog_carrousel.jsp"/>