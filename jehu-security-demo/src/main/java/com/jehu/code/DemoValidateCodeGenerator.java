/**
 * 
 */
package com.jehu.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.springframework.web.context.request.ServletWebRequest;

import com.jehu.security.core.validate.code.ValidateCode;
import com.jehu.security.core.validate.code.ValidateCodeGenerator;
import com.jehu.security.core.validate.code.image.ImageCode;

/**
 * @author Administrator
 *
 */
//@Component("imageCodeGenerator")
public class DemoValidateCodeGenerator implements ValidateCodeGenerator {

	@Override
	public ValidateCode generate(ServletWebRequest request) {
		System.out.println("书写自己的图片验证码代码");
		
	    Random random = new Random();
	    BufferedImage image = new BufferedImage(100,25,BufferedImage.TYPE_INT_RGB);
	    Graphics g = image.getGraphics();
	
	    String sRand = "";
		for(int i=0;i<4;i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
			g.drawString(rand, 13*i+6, 16);
		}
		g.dispose();
	
	return new ImageCode(image, sRand, 60);
	}

	/* (non-Javadoc)
	 * @see com.jehu.security.core.validate.code.ValidateCodeGenerator#createImageCode(javax.servlet.http.HttpServletRequest)
	 */
	/*@Override
	public ImageCode createImageCode(HttpServletRequest request){
		System.out.println("书写自己的图片验证码代码");
		
		    Random random = new Random();
		    Color color = new Color(random.nextInt(25), 
		    		random.nextInt(25), random.nextInt(26));
		    
		    BufferedImage image = new BufferedImage(100,25,BufferedImage.TYPE_INT_RGB);
		    Graphics g = image.getGraphics();
		    //g.setColor(color);
		
		    String sRand = "";
			for(int i=0;i<4;i++) {
				String rand = String.valueOf(random.nextInt(10));
				sRand += rand;
				g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
				g.drawString(rand, 13*i+6, 16);
			}
			g.dispose();
		
		return new ImageCode(image, sRand, 60);
	}*/

}
