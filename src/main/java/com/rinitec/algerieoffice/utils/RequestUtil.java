package com.rinitec.algerieoffice.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.util.WebUtils;

import com.rinitec.algerieoffice.web.form.ConstraintesURL;
import com.rinitec.algerieoffice.web.modal.CurrentConfig;

public class RequestUtil {
	public static final String COOKIE_CONFIG = "ao_user_config";
	public static final String COOKIE_SETTING = "ao_office_config";
	public static final String COOKIE_GEOLOCATE = "ao_geo_config";
	
	public static final String COOKIE_TABLE_ADMIN = "ao_tp_adm";
	
	public static final String COOKIE_TABLE_POST1 = "ao_tp_pos";
	public static final String COOKIE_TABLE_POST2 = "ao_tp_cat";
	public static final String COOKIE_TABLE_POST3 = "ao_tp_sts";
	
	public static final String COOKIE_TABLE_DASHBOARD1 = "ao_tp_jou";
	public static final String COOKIE_TABLE_DASHBOARD2 = "ao_tp_det";
	
	public static final String COOKIE_TABLE_MARKETPLACE1 = "ao_tm_pro";
	public static final String COOKIE_TABLE_MARKETPLACE2 = "ao_tm_ads";
	public static final String COOKIE_TABLE_MARKETPLACE3 = "ao_tm_job";
	public static final String COOKIE_TABLE_MARKETPLACE4 = "ao_tm_cam";
	
	public static final String COOKIE_TABLE_PORTFOLIO1 = "ao_tf_wor";
	public static final String COOKIE_TABLE_PORTFOLIO2 = "ao_tf_act";
	public static final String COOKIE_TABLE_PORTFOLIO3 = "ao_tf_eve";
	public static final String COOKIE_TABLE_PORTFOLIO4 = "ao_tf_faq";
	public static final String COOKIE_TABLE_PORTFOLIO5 = "ao_tf_par";
	
	public static final String COOKIE_TABLE_TEAM1 = "ao_tt_use";
	public static final String COOKIE_TABLE_TEAM2 = "ao_tt_age";
	public static final String COOKIE_TABLE_TEAM3 = "ao_tt_gue";
	
	public static final String COOKIE_TABLE_COMMUNICATION1 = "ao_tc_not";
	public static final String COOKIE_TABLE_COMMUNICATION2 = "ao_tc_app";
	public static final String COOKIE_TABLE_COMMUNICATION3 = "ao_tc_eva";
	public static final String COOKIE_TABLE_COMMUNICATION4 = "ao_tc_col";
	public static final String COOKIE_TABLE_COMMUNICATION5 = "ao_tc_con";
	public static final String COOKIE_TABLE_COMMUNICATION6 = "ao_tc_par";
	public static final String COOKIE_TABLE_COMMUNICATION7 = "ao_tc_cht";
	
	public static final String COOKIE_TABLE_PROSPECT1 = "ao_ts_quo";
	public static final String COOKIE_TABLE_PROSPECT2 = "ao_ts_ads";
	public static final String COOKIE_TABLE_PROSPECT3 = "ao_ts_inf";
	public static final String COOKIE_TABLE_PROSPECT4 = "ao_ts_job";
	
	public static final String COOKIE_TABLE_TOOLS1 = "ao_tl_bck";
	public static final String COOKIE_TABLE_TOOLS2_1 = "ao_tl_pos";
	public static final String COOKIE_TABLE_TOOLS2_2 = "ao_tl_pro";
	public static final String COOKIE_TABLE_TOOLS2_3 = "ao_tl_ann";
	public static final String COOKIE_TABLE_TOOLS2_4 = "ao_tl_emp";
	public static final String COOKIE_TABLE_TOOLS3 = "ao_tl_sub";
	
	public static final String COOKIE_TABLE_COMMUNICATION01 = "ao_tc_not0";
	public static final String COOKIE_TABLE_COMMUNICATION02 = "ao_tc_app0";
	public static final String COOKIE_TABLE_COMMUNICATION03 = "ao_tc_eva0";
	public static final String COOKIE_TABLE_COMMUNICATION04 = "ao_tc_gue0";
	public static final String COOKIE_TABLE_COMMUNICATION05 = "ao_tc_com0";
	
	public static final String COOKIE_TABLE_DASHBOARD01 = "ao_td_his0";
	public static final String COOKIE_TABLE_GLOBE01 = "ao_tv_com0";
	public static final String COOKIE_TABLE_GLOBE02 = "ao_tv_pro0";
	
	public static final String COOKIE_TABLE_FAVORITE01 = "ao_tv_pos0";
	public static final String COOKIE_TABLE_FAVORITE02 = "ao_tv_ads0";
	public static final String COOKIE_TABLE_FAVORITE03 = "ao_tv_eve0";
	public static final String COOKIE_TABLE_FAVORITE04 = "ao_tv_job0";
	
	public static final String COOKIE_TABLE_ALERT01 = "ao_ta_pos0";
	public static final String COOKIE_TABLE_ALERT02 = "ao_ta_ads0";
	public static final String COOKIE_TABLE_ALERT03 = "ao_ta_eve0";
	public static final String COOKIE_TABLE_ALERT04 = "ao_ta_job0";
	
	public static final String COOKIE_TABLE_EASY01 = "ao_te_com0";
	public static final String COOKIE_TABLE_EASY11 = "ao_te_com01";
	public static final String COOKIE_TABLE_EASY02 = "ao_te_pos0";
	public static final String COOKIE_TABLE_EASY12 = "ao_te_pos01";
	public static final String COOKIE_TABLE_EASY03 = "ao_te_ads0";
	public static final String COOKIE_TABLE_EASY13 = "ao_te_ads01";
	public static final String COOKIE_TABLE_EASY04 = "ao_te_eve0";
	public static final String COOKIE_TABLE_EASY14 = "ao_te_eve01";
	public static final String COOKIE_TABLE_EASY05 = "ao_te_job0";
	public static final String COOKIE_TABLE_EASY15 = "ao_te_job01";
	
	public static final String COOKIE_TABLE_FEEDBACK01 = "ao_tf_mes0";
	public static final String COOKIE_TABLE_FEEDBACK02 = "ao_tf_not0";
	public static final String COOKIE_TABLE_FEEDBACK03 = "ao_tf_com0";
	public static final String COOKIE_TABLE_SETTING01 = "ao_ts_blc0";

	public static final String getAppurl(final HttpServletRequest request) {
        return ConstraintesURL.URL_APPLICATION;
    }
	
	public static final String getClientIP(HttpServletRequest request) {
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
	
	public static final void rememberRegistred(final HttpServletRequest request, final String email) {
		final HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(60 * 60); //1H
		session.setAttribute("remember", email);
	}
	
	public static final String getRemembredRegistred(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session != null) {
			try {
				return (String) session.getAttribute("remember");
			} catch(Exception e) {}
		}
		return null;
	}
	
	public static final void rememberIdentity(final HttpServletRequest request, final Long userId) {
		final HttpSession session = request.getSession(true);
		session.setMaxInactiveInterval(60 * 60); //1H
		session.setAttribute("remember", userId);
	}
	
	public static final Long getRemembredIdentity(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session != null) {
			try {
				return (Long) session.getAttribute("remember");
			} catch(Exception e) {}
		}
		return null;
	}
	
	public static final void destroyedRemember(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session != null) {
			session.setAttribute("remember", null);
		}
	}
	
	public static final void addCookie(final HttpServletResponse response, final Cookie cookie, final Integer ageByDay) {
		cookie.setMaxAge(ageByDay * 24 * 60 * 60); // ageByDay = 30 (30 days)
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static final void deleteCookie(final HttpServletResponse response, final Cookie cookie) {
		cookie.setMaxAge(0);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static final void postOrUpdateCookieConfig(final HttpServletRequest request, final HttpServletResponse response) {
		final Cookie cookie = WebUtils.getCookie(request, COOKIE_CONFIG);
		if(cookie == null) {
			final CurrentConfig currentConfig = new CurrentConfig();
			addCookie(response, new Cookie(COOKIE_CONFIG, currentConfig.toString()), 365);
		} else {
			addCookie(response, cookie, 365);
		}
	}
	
}
