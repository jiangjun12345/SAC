/**
 * 
 */
package net.easipay.pepp.peps.web;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.easipay.pepp.peps.util.AuthCodeImageUtil;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author Administrator
 * 获取验证码
 */
public class AuthCodeCheckController extends MultiActionController {
	
	public static final Logger logger = Logger.getLogger(AuthCodeCheckController.class);

	
	/**
	 * 获得验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void achieveAuthCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("===开始获取验证随机码====");
		String authCode = AuthCodeImageUtil.getAuthCode(AuthCodeImageUtil.DEFAULT_AUTH_CODE_SIZE);
		HttpSession session = request.getSession();
		session.setAttribute(AuthCodeImageUtil.AUTH_CODE_SESSION_KEY, authCode);
		BufferedImage imageStream = AuthCodeImageUtil.createAuthCodeImage(AuthCodeImageUtil.DEFAULT_AUTH_CODE_IMG_WITH, AuthCodeImageUtil.DEFAULT_AUTH_CODE_IMG_HEIGHT, authCode);
		response.setHeader( "Pragma", "no-cache" );
        response.addHeader( "Cache-Control", "no-cache" );
        response.setDateHeader("Expires", 0); 
        response.setContentType("image/jpeg");
		ServletOutputStream outputSteam = response.getOutputStream();
		ImageIO.write(imageStream, AuthCodeImageUtil.DEFAULT_AUTH_CODE_FORMAT, outputSteam);
		outputSteam.close();
		logger.debug("====验证码已经生成====");
	}
	
	/**
	 * 验证验证码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void verifyAuthCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String authCode = request.getParameter("authCode");
		String authCodeInSession = (String)request.getSession().getAttribute(AuthCodeImageUtil.AUTH_CODE_SESSION_KEY);
		StringBuilder sb = new StringBuilder();
		if(StringUtils.hasText(authCode)
				&& authCode.equalsIgnoreCase(authCodeInSession)){
			sb.append("{'success':true,'msg':\"验证成功\"}");
		} else {
			sb.append("{'success':false,'msg':\"验证错误\"}");
		}
		OutputStream outputStream = response.getOutputStream();
		IOUtils.write(sb, outputStream);
		outputStream.close();
	}
}
