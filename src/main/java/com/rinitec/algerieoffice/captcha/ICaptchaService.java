package com.rinitec.algerieoffice.captcha;

import com.rinitec.algerieoffice.web.error.captcha.ReCaptchaInvalidException;

public interface ICaptchaService {

	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
	String getReCaptchaSite();
	
	/**
	 * VERSION BEGIN 03/2021
	 * @return
	 */
    String getReCaptchaSecret();
    
    /**
     * VERSION BEGIN 03/2021
     * @param response
     * @throws ReCaptchaInvalidException
     */
    void processResponse(final String response) throws ReCaptchaInvalidException;
    
}
