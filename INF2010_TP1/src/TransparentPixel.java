/**
 * Classe de pixel transparent
 * @author : Dominik Courcelles, Dan vatnik
 * @date : 2016-01-16
 */

public class TransparentPixel extends AbstractPixel
{
	public int[] rgba; // donnees de l'image
	
	/**
	 * Constructeur par defaut (pixel blanc)
	 */
	TransparentPixel()
	{
		rgba = new int[4];
		rgba[0] = MAX_COLOR_VALUE;
		rgba[1] = MAX_COLOR_VALUE;
		rgba[2] = MAX_COLOR_VALUE;
		rgba[3] = MAX_COLOR_VALUE;
	}
	
	/**
	 * Assigne une valeur au pixel
	 * @param rgba: valeurs a assigner 
	 */
	TransparentPixel(int[] rgba)
	{
		if(rgba.length != 4)
		{
			throw new IllegalArgumentException("Un tableau a 4 dimensions doit etre passe en parametres.");
		}
		this.rgba = rgba.clone();
	}
	
	/**
	 * Calcule la moyenne des 3 composantes couleurs du pixel
	 */
	private int getAveragePixel()
	{
		return (rgba[0] + rgba[1] + rgba[2]) / 3;
	}
	
	/**
	 * Renvoie un pixel copie de type noir et blanc
	 */
	public BWPixel toBWPixel()
	{
		return new BWPixel(getAveragePixel() >= HALF_VALUE_SEPARATION);
	}
	
	/**
	 * Renvoie un pixel copie de type tons de gris
	 */
	public GrayPixel toGrayPixel()
	{
		return new GrayPixel(getAveragePixel());
	}
	
	/**
	 * Renvoie un pixel copie de type couleurs
	 */
	public ColorPixel toColorPixel()
	{
		return new ColorPixel(new int[]{rgba[0], rgba[1], rgba[2]});
	}
	
	public TransparentPixel toTransparentPixel()
	{
		return new TransparentPixel(rgba);
	}
	
	/**
	 * Renvoie le negatif du pixel (255-r, 255-g, 255-b, a)
	 */
	public AbstractPixel Negative()
	{
		return new TransparentPixel(new int[]{MAX_COLOR_VALUE - rgba[0], MAX_COLOR_VALUE - rgba[1], MAX_COLOR_VALUE - rgba[2], rgba[3]});
	}
	
	public void setAlpha(int alpha)
	{
		rgba[3] = alpha;
	}
	
	/**
	 * Convertit le pixel en String (sert a ecrire dans un fichier 
	 * (avec un espace supplémentaire en fin)s
	 */
	public String toString()
	{
		return  ((Integer)rgba[0]).toString() + " " + 
				((Integer)rgba[1]).toString() + " " +
				((Integer)rgba[2]).toString() + " " +
				((Integer)rgba[3]).toString() + " ";
	}
}
