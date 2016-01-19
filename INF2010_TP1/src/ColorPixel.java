/**
 * Classe de pixel en couleurs 
 * @author : Dominik Courcelles, Dan Vatnik
 * @date : 2016-01-16
 */

public class ColorPixel extends AbstractPixel
{
	private int[] rgb; // donnees de l'image
	
	/**
	 * Constructeur par defaut (pixel blanc)
	 */
	ColorPixel()
	{
		rgb = new int[3];
		rgb[0] = MAX_COLOR_VALUE;
		rgb[1] = MAX_COLOR_VALUE;
		rgb[2] = MAX_COLOR_VALUE;
	}
	
	/**
	 * Assigne une valeur au pixel
	 * @param rgb: valeurs a assigner 
	 */
	ColorPixel(int[] rgb)
	{
		if(rgb.length != 3)
		{
			throw new IllegalArgumentException("Un tableau a 3 dimensions doit etre passe en parametres.");
		}
		this.rgb = rgb.clone();
	}
	
	/**
	 * Calcule la moyenne des 3 composantes du pixel
	 */
	private int getAveragePixel()
	{
		return (rgb[0] + rgb[1] + rgb[2]) / 3;
	}
	
	/**
	 * Renvoie un pixel copie de type noir et blanc
	 */
	public BWPixel toBWPixel()
	{
		return new BWPixel(getAveragePixel() > HALF_VALUE_SEPARATION);
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
		return new ColorPixel(rgb);
	}
	
	public TransparentPixel toTransparentPixel()
	{
		return new TransparentPixel(new int[]{rgb[0], rgb[1], rgb[2], MAX_COLOR_VALUE});
	}
	
	/**
	 * Renvoie le negatif du pixel (255-r, 255-g, 255-b)
	 */
	public AbstractPixel Negative()
	{
		return new ColorPixel(new int[]{MAX_COLOR_VALUE - rgb[0], MAX_COLOR_VALUE - rgb[1], MAX_COLOR_VALUE - rgb[2]});
	}
	
	public void setAlpha(int alpha)
	{
		//ne fait rien
	}
	
	/**
	 * Convertit le pixel en String (sert a ecrire dans un fichier 
	 * (avec un espace supplémentaire en fin)s
	 */
	public String toString()
	{
		return  ((Integer)rgb[0]).toString() + " " + 
				((Integer)rgb[1]).toString() + " " +
				((Integer)rgb[2]).toString() + " ";
	}
}