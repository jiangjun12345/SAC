/**
 * 
 */
package net.easipay.pepp.peps.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author Administrator
 * 验证码工具类
 */
public class AuthCodeImageUtil {
	
	/**
	 * 验证码字符长度
	 */
	public static final int DEFAULT_AUTH_CODE_SIZE = 4;
	
	/**
	 * 验证码图片宽度
	 */
	public static final int DEFAULT_AUTH_CODE_IMG_WITH = 80;
	
	/**
	 * 验证码图片高度
	 */
	public static final int DEFAULT_AUTH_CODE_IMG_HEIGHT = 35;
	
	/**
	 * 验证码图片格式
	 */
	public static final String DEFAULT_AUTH_CODE_FORMAT = "JPEG";
	
	/**
	 * 验证码在session中的存储关键字
	 */
	public static final String AUTH_CODE_SESSION_KEY = "_SESSION_AUTH_CODE_";
	
	/**
     * 创建随机图片
     * 
     * @param width
     *            图片宽度
     * @param height
     *            图片高度
     * @param checkcode
     *            字符串
     * @return 字符串的图片表示
     */
    public static BufferedImage createAuthCodeImage(final int width, final int height, final String checkcode) {
        BufferedImage bufferedImage = null;
        Graphics2D graphics2D = null;
        try {
            // 创建一个"画板"
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 一只"画笔"
            graphics2D = bufferedImage.createGraphics();
            graphics2D.setColor(Color.LIGHT_GRAY);
            graphics2D.fillRect(0, 0, width, height);
            // 根据图片的高度创建字体
            Font font = new Font("Courier New", Font.BOLD | Font.ITALIC, height);
            graphics2D.setFont(font);
            // 画边框。
            graphics2D.setColor(Color.LIGHT_GRAY);
            graphics2D.drawRect(0, 0, width - 1, height - 1);
            // 随机产生噪点
            Random random = new Random();
            graphics2D.setColor(getColor(random));
            int lineNum = width / 2;
            if (width > 100)
                lineNum = width;
            
            int x1, y1, x2, y2;
            for (int i = 0; i < lineNum; i++) {
                x1 = random.nextInt(width - 2);
                y1 = random.nextInt(height - 2);
                x2 = random.nextInt(12);
                y2 = random.nextInt(12);
                graphics2D.drawLine(x1, y1, x1 + x2, y1 + y2);
            }
            
            int fontNum = checkcode.length();
            final int m = 2;
            int fontWidth = (width - m * (fontNum + 1)) / fontNum;
            int n = height - 5;
            for (int i = 0; i < fontNum; i++) {
                // 用随机产生的颜色将验证码绘制到图像中。
                graphics2D.setColor(getColor(random));
                graphics2D.drawString(checkcode.charAt(i) + "", m * (i + 1) + fontWidth * i, n);
            }
        } finally {
            if (graphics2D != null)
                graphics2D.dispose();
        }
        return bufferedImage;
    }
    
    /**
     * 获取随机颜色
     * 
     * @param random
     *            随机数发生器
     * @return 颜色
     */
    private static Color getColor(Random random) {
        final int n = 255;
        int r = random.nextInt(n);
        if (r > 150)
            r -= 100;
        int g = random.nextInt(n);
        if (g > 150)
            g -= 100;
        int b = random.nextInt(n);
        if (b > 150)
            b -= 100;
        return new Color(r, g, b);
    }
    
    /**
     * 获取随机数字,并拼接成字符串
     * 
     * @param size
     *            生成随机数字的个数
     * @return 随机生成的字符串
     */
    public static String getAuthCode(final int size) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}

