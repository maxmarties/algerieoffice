<%@ include file="/WEB-INF/tags/libs/tags_libs.jsp"%>
<ul class="list-none list-block banner-follow p-left animated fadeFromLeft" style="display:none;">
	<li>
		<a class="icon-facebook transition-35 iFollow" title="<spring:message code="tool.navigate.company5.1" arguments="Facebook" />"
			href="<c:url value="https://www.facebook.com/sharer/sharer.php?url=${mapsiteURL}" />"><i class="cmsms-icon-facebook"></i></a>
	</li>
	<li>
		<a class="icon-twitter transition-35 iFollow" title="<spring:message code="tool.navigate.company5.1" arguments="Twitter" />"
			href="<c:url value="https://twitter.com/intent/tweet?url=${mapsiteURL}" />"><i class="cmsms-icon-twitter"></i></a>
	</li>
	<li>
		<a class="icon-google transition-35 iFollow" title="<spring:message code="tool.navigate.company5.1" arguments="Google" />"
			href="<c:url value="https://plus.google.com/share?url=${mapsiteURL}" />"><i class="cmsms-icon-google"></i></a>
	</li>
	<li>
		<a class="icon-linkedin transition-35 iFollow" title="<spring:message code="tool.navigate.company5.1" arguments="Linkedin" />"
			href="<c:url value="https://www.linkedin.com/shareArticle?url=${mapsiteURL}" />"><i class="cmsms-icon-linkedin"></i></a>
	</li>
	<li>
		<a class="icon-viadeo transition-35 iFollow" title="<spring:message code="tool.navigate.company5.1" arguments="Viadeo" />"
			href="<c:url value="https://www.viadeo.com/shareit/share/?url=${mapsiteURL}" />"><i class="cmsms-icon-viadeo"></i></a>
	</li>
</ul>