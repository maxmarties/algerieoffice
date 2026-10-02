<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<div class="screen-column screen-newsletter">
	<div class="widget-body text-center">
		<h3 class="h-header h-header3 i-segond"><spring:message code="txt.blog.newsletter${!empty requestScope.homeBlog ? '3' : '2'}"/></h3>
		<hr class="my-2">
		<p class="font-big m-auto"><spring:message code="txt.blog.newsletter${!empty requestScope.homeBlog ? '3' : '2'}.1"/></p>
	</div>
	<div class="widget-news background-container" 
		style="background-image: linear-gradient(to bottom, ${currentConfig.aocolor(2)} 10%, rgba(0,0,0,0) 80%), url('<c:url value="/static/picts/images/newsletter-min.jpg" />');">
		<form name="formNewsblog" action="/" method="POST" novalidate="novalidate">
			<div id="newsblogForm" class="form-group">
				<c:set var="faholder" scope="page"><spring:message code="lbl.login.email" /></c:set>
				<input class="form-control" type="email" id="newsblog" name="newsblog" placeholder="${pageScope.faholder}" />
				<span class="error"></span>
			</div>
			<div class="form-group m-b-0">
				<div id="submitNewsblogForm" class="form-submit form-block">
					<button type="submit" class="btn btn-segond btn-submit btn-block"><span><spring:message code="btn.subscribe"/></span></button>
				</div>
			</div>
		</form>
		<hr class="my-4">
		<ul class="list-none list-block font-small font-bold">
			<c:forEach var="i" begin="1" end="3" step="1">
				<li class="m-b-10"><i class="cmsms-icon-ok-5 i-segond i-22"></i><span class="i-white"><spring:message code="txt.blog.newsletter2.1.${i}"/></span></li>
			</c:forEach>
		</ul>
	</div>
</div>