<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/tags/libs/form_libs.jsp"%>
<c:import url="/WEB-INF/explorer/banners/banner_begginer.jsp" />
<div class="container">
	<ol class="bread-crumb bread-screen">
		<li><a href="<c:url value="/"/>" class="lien lien-black"><i class="cmsms-icon-home"></i></a></li>
		<li><a href="<c:url value="/entreprises"/>" class="lien lien-black"><spring:message code="sidebar.admin.dashboard3"/></a></li>
		<li class="active"><spring:message code="sidebar.home.dashboard1"/></li>
	</ol>
</div>
<div class="screen-container">
	<div class="container">
		<div class="header-screen"><h1 class="h-header m-auto"><spring:message code="subheader.screen.search2"/></h1></div>
		<div class="screen-search">
			<form name="searchScreenForm" action="/" novalidate="novalidate">
				<div class="row row-mini">
					<div class="form-group col-md-6 col-lg-6 col-mini m-b-5">
						<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.token"/></c:set>
						<div class="input-group-token">
							<span class="indSearch"><spring:message code="tool.view.token" /></span>
							<input class="form-control" type="search" id="findtoken" name="findtoken" placeholder="${pageScope.placeholderFind}" value="${token}"/>
						</div>
					</div>
					<div class="form-group col-md-6 col-lg-4 col-mini m-b-5">
						<c:set var="placeholderFind" scope="page"><spring:message code="tooltip.search.location"/></c:set>
						<div class="input-group-location">
							<span class="indSearch"><spring:message code="tool.view.location" /></span><i class="cmsms-icon-location i-red trigger-location"></i>
							<select class="form-select2" id="findlocation" name="findlocation" data-placeholder="${pageScope.placeholderFind}" >
								<option></option>
								<option value="0"><spring:message code="comp.target" /></option>
								<c:forEach var="i" begin="1" end="48" step="1">
									<option value="${i}" ${i == wilaya ? 'selected' : ''}><c:out value="${i < 10 ? '0' : ''}${i}"/> - <spring:message code="chose.wilaya${i}" /></option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group col-md-12 col-lg-2 col-mini m-b-5">
						<button type="submit" class="btn btn-primary btn-simple btn-block">
							<span><i class="cmsms-icon-search-1 m-r-10"></i><spring:message code="btn.find" /></span>
						</button>
					</div>
				</div>
			</form>
		</div>		
	</div>
</div>
<div id="screenElements" class="screen-container screen-segond">
	<div class="container">
		<div class="row row-mini">
			<div class="col-lg-9 col-mini m-t-10 m-b-10">
				<h2 class="h-header h-header3 i-primary">
					<spring:message code="subheader.screen.search2.1"/> "<span class="font-bold i-segond tokenResult"><c:out value="${token}"/></span>"
				</h2>
				<div id="elementsWithToken" class="screen-loader m-t-30">
					<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
					<div class="screenLoad"></div>
				</div>
				<div class="explorer-auth-hr m-t-20 m-b-20"><span class="line"></span></div>
				<h2 class="h-header h-header3 i-primary">
					<spring:message code="subheader.screen.search2.2"/> "<span class="font-bold i-segond tokenResult"><c:out value="${token}"/></span>"
				</h2>
				<div id="elementsWithAgent" class="screen-loader m-t-30">
					<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
					<div class="screenLoad"></div>
				</div>
				<div class="explorer-auth-hr m-t-20 m-b-20"><span class="line"></span></div>
				<h2 class="h-header h-header3 i-primary">
					<spring:message code="subheader.screen.search2.3"/> "<span class="font-bold i-segond tokenResult"><c:out value="${token}"/></span>"
				</h2>
				<div id="elementsWithKeyword" class="screen-loader m-t-30">
					<c:import url="/WEB-INF/fields/tables/table_loading.jsp"/>
					<div class="screenLoad"></div>
				</div>
			</div>
			<div class="col-lg-3 col-mini m-t-10 m-b-10">
				<div class="screen-sticky hidden-md-down">
					<div class="screen-column m-t-10">
						<div id="iExplorerSpn" class="explorer-spn"><p class="font-small text-right i-help"><spring:message code="txt.help.explorer4.2"/></p></div>
					</div>
					<c:import url="/WEB-INF/fields/screen/screen_topics.jsp"/>
				</div>
			</div>
		</div>
		<div class="explorer-auth-hr m-t-20 m-b-20"><span class="line"></span></div>
		<div class="text-center m-b-20">
			<a href="<c:url value="/recherche/entreprises" />" class="btn btn-primary btn-flat btn-big">
				<span><spring:message code="subheader.screen.search2.4"/><i class="cmsms-icon-explorer-arrow m-l-10"></i></span></a>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/fields/blog/blog_carrousel.jsp"/>