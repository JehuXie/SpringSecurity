/**
 * 
 */
package com.jehu.security.core.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.jehu.security.core.validate.code.ValidateCode;

/**
 * @author Administrator
 *
 */
public class ImageCode extends ValidateCode{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	
	public ImageCode(BufferedImage image,String code,int expireTime) {
		super(code,expireTime);
		this.image = image;
	}

	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super(code,expireTime);
		this.image = image;
	}

	/*public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireTime);
	}*/
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
