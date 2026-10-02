<%@ include file="/WEB-INF/tags/script/script_browser.jsp"%>
<script src="https://www.google.com/recaptcha/api.js"></script>
<script type="text/javascript">
$(function(){$(".page-explorer").scrippage({typeDocument:"employe"})});var onReCaptchaSuccess=function(e){$("#g-recaptchaForm").removeClass("has-error")},onReCaptchaExpired=function(e){grecaptcha.reset(),Algerieoffice.showError("g-recaptcha",'<spring:message code="auth.recaptcha.message.expired"/>',!1)};
</script>