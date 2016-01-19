/**
 * Classe abstraite de pixel
 * @author Tarek Ould Bachir, Wail Khemir
 * @date : 2015-09-06
 */

public abstract class AbstractPixel 
{
	protected final int MAX_COLOR_VALUE = 255;
	protected final int HALF_VALUE_SEPARATION = 128;
	
	public abstract BWPixel toBWPixel();
	public abstract GrayPixel toGrayPixel();
	public abstract ColorPixel toColorPixel();
	public abstract TransparentPixel toTransparentPixel();
	
	public abstract AbstractPixel Negative();
	public abstract void setAlpha(int alpha);
	
	public abstract String toString();
}
