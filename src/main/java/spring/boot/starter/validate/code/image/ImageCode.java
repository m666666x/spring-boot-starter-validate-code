package spring.boot.starter.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import spring.boot.starter.validate.code.ValidateCode;


/**
 * @author zhailiang
 *
 */
public class ImageCode extends ValidateCode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6020470039852318468L;
	
	private BufferedImage image; 
	
	public ImageCode(BufferedImage image, String code, int expireIn){
		super(code, expireIn);
		this.image = image;
	}
	
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
		super(code, expireTime);
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
