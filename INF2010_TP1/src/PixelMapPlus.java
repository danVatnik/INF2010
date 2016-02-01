import java.awt.PageAttributes.ColorType;
 
/**
 * Classe PixelMapPlus
 * Image de type noir et blanc, tons de gris ou couleurs
 * Peut lire et ecrire des fichiers PNM
 * Implemente les methodes de ImageOperations
 * @author : Dominik Courcelles, Dan Vatnik
 * @date   : 28 janvier 2016
 */
 
public class PixelMapPlus extends PixelMap implements ImageOperations 
{

    /**
     * Constructeur creant l'image a partir d'un fichier
     * @param fileName : Nom du fichier image
     */
    PixelMapPlus(String fileName)
    {
        super( fileName );
    }
     
    /**
     * Constructeur copie
     * @param type : type de l'image a creer (BW/Gray/Color)
     * @param image : source
     */
    PixelMapPlus(PixelMap image)
    {
        super(image); 
    }
     
    /**
     * Constructeur copie (sert a changer de format)
     * @param type : type de l'image a creer (BW/Gray/Color)
     * @param image : source
     */
    PixelMapPlus(ImageType type, PixelMap image)
    {
        super(type, image); 
    }
     
    /**
     * Constructeur servant a allouer la memoire de l'image
     * @param type : type d'image (BW/Gray/Color)
     * @param h : hauteur (height) de l'image 
     * @param w : largeur (width) de l'image
     */
    PixelMapPlus(ImageType type, int h, int w)
    {
        super(type, h, w);
    }
     
    /**
     * Genere le negatif d'une image
     */
    public void negate()
    {
 
        for(int row=0; row < height; row++)
        {
            for(int col=0; col < width; col++)
            {
                imageData[row][col] = imageData[row][col].Negative();
                 
            }
        }
         
    }
     
    /**
     * Convertit l'image vers une image en noir et blanc
     */
    public void convertToBWImage()
    {
 
        for(int row=0; row < height; row++)
        {
            for(int col=0; col < width; col++)
            {
                imageData[row][col] = imageData[row][col].toBWPixel();
                 
            }
        }
        imageType = ImageType.BW;
    }
     
    /**
     * Convertit l'image vers un format de tons de gris
     */
    public void convertToGrayImage()
    {
 
        for(int row=0; row < height; row++)
        {
            for(int col=0; col < width; col++)
            {
                imageData[row][col] = imageData[row][col].toGrayPixel();
                 
            }
        }
        imageType = ImageType.Gray;
    }
     
    /**
     * Convertit l'image vers une image en couleurs
     */
    public void convertToColorImage()
    {
 
        for(int row=0; row < height; row++)
        {
            for(int col=0; col < width; col++)
            {
                imageData[row][col] = imageData[row][col].toColorPixel();
                 
            }
        }
        imageType = ImageType.Color;
    }
     
    public void convertToTransparentImage()
    {
 
        for(int row=0; row < height; row++)
        {
            for(int col=0; col < width; col++)
            {
                imageData[row][col] = imageData[row][col].toTransparentPixel();
                 
            }
        }
        imageType = ImageType.Transparent;
    }
     
    /**
     * Fait pivoter l'image de 10 degres autour du pixel (row,col)=(0, 0)
     * dans le sens des aiguilles d'une montre (clockWise == true)
     * ou dans le sens inverse des aiguilles d'une montre (clockWise == false).
     * Les pixels vides sont blancs.
     * @param clockWise : Direction de la rotation 
     */
    public void rotate(int x, int y, double angleRadian)
    {
        AbstractPixel [][] rotatedImage = new AbstractPixel[height][width];
         
        for(int row=0; row < height; row++)
        {
            for(int col=0; col < width; col++)
            {
                int rotX = (int) (Math.cos(angleRadian) * col + Math.sin(angleRadian) * row - Math.cos(angleRadian) * x - Math.sin(angleRadian) * y + x);
                int rotY = (int) (-Math.sin(angleRadian) * col + Math.cos(angleRadian) * row + Math.sin(angleRadian) * x - Math.cos(angleRadian) * y + y);
                if(rotY < height && rotY >= 0  && rotX < width && rotX >= 0){
                    rotatedImage[row][col] = imageData[rotY][rotX];
                }
                else{
                    switch (imageType){
                     
                    case BW:
                                 
                        rotatedImage[row][col] = new BWPixel();
                         
                        break;
                    case Gray:
                         
                        rotatedImage[row][col] = new GrayPixel();
                         
                        break;
                    case Color:
                         
                        rotatedImage[row][col] = new ColorPixel();
                         
                        break;
                    case Transparent:
                         
                        rotatedImage[row][col] = new TransparentPixel();
 
                        break;
                    }
                }   
            }
        }
         
        imageData = rotatedImage;
    }
     
     
    /**
    * Modifie la longueur et la largeur de l'image 
    * @param w : nouvelle largeur
    * @param h : nouvelle hauteur
    */
    public void resize(int w, int h) throws IllegalArgumentException
    {
            if(w < 0 || h < 0)
                    throw new IllegalArgumentException();
            
            // compléter
            int oldWidth = getWidth();
            int oldHeight = getHeight();
            float ratioWidth = (float)oldWidth / w;
            float ratioHeight = (float)oldHeight / h;
            
            //On conserve une copie de l'ancienne image avant de commencer les modifications.
            PixelMap oldImage  = new PixelMap(this);
            clearData();
            AllocateMemory(getType(), h, w);
            
            for(int i = 0; i < h; ++i)
            {
                    for(int j = 0; j < w; ++j)
                    {
                            int nearestWidth = (int)(j * ratioWidth);
                            int nearestHeight = (int)(i * ratioHeight);
                            setPixel(oldImage.getPixel(nearestHeight, nearestWidth), i, j);
                    }
            }
    }
     
    /**
     * Insert pm dans l'image a la position row0 col0
     */
    public void inset(PixelMap pm, int row0, int col0)
    {
        // compléter
        if(imageType == ImageType.Transparent || pm.imageType == ImageType.Transparent){
            this.convertToTransparentImage();
            pm.toTransparentImage();
        }else if(imageType == ImageType.Color || pm.imageType == ImageType.Color){
            this.convertToColorImage();
            pm.toColorImage();
        }else if(imageType == ImageType.Gray || pm.imageType == ImageType.Gray){
            this.convertToGrayImage();
            pm.toGrayImage();
        }else{
            this.convertToBWImage();
            pm.toBWImage();
        }
         
        for(int row=0; row < pm.height; row++)
        {
            for(int col=0; col < pm.width; col++)
            {
                int newRow = row + row0;
                int newCol = col + col0;
                 
                if( newRow < height && newRow >= 0  && newCol < width && newCol >= 0)
                    imageData[newRow][newCol] = pm.getPixel(row, col);
            }
        }
    }
     
    /**
     * Decoupe l'image 
     */
    public void crop(int h, int w)
    {
        // compléter        
        AbstractPixel [][] croppedImage = new AbstractPixel[h][w];
         
        for(int row=0; row < h; row++)
        {
            for(int col=0; col < w; col++)
            {
                if(row < height && col < width){
                    croppedImage[row][col] = imageData[row][col];
                }
                else{
                    switch (imageType){
                     
                    case BW:
                                 
                        croppedImage[row][col] = new BWPixel();
                         
                        break;
                    case Gray:
                         
                        croppedImage[row][col] = new GrayPixel();
                         
                        break;
                    case Color:
                         
                        croppedImage[row][col] = new ColorPixel();
                         
                        break;
                    case Transparent:
                         
                        croppedImage[row][col] = new TransparentPixel();
 
                        break;
                    }
                }   
            }
        }
         
        imageData = croppedImage;
        height = h;
        width = w;
    }
     
    /**
     * Effectue une translation de l'image 
     */
    public void translate(int rowOffset, int colOffset)
    {
        // compléter        
                AbstractPixel [][] translatedImage = new AbstractPixel[height][width];
                 
                for(int row=0; row < height; row++)
                {
                    for(int col=0; col < width; col++)
                    {
                        int oriRow = row - rowOffset;
                        int oriCol = col - colOffset;
                         
                        if(oriRow < height && oriRow >= 0  && oriCol < width && oriCol >= 0){
                            translatedImage[row][col] = imageData[oriRow][oriCol];
                        }
                        else{
                            switch (imageType){
                             
                            case BW:
                                         
                                translatedImage[row][col] = new BWPixel();
                                 
                                break;
                            case Gray:
                                 
                                translatedImage[row][col] = new GrayPixel();
                                 
                                break;
                            case Color:
                                 
                                translatedImage[row][col] = new ColorPixel();
                                 
                                break;
                            case Transparent:
                                 
                                translatedImage[row][col] = new TransparentPixel();
 
                                break;
                            }
                        }   
                    }
                }
                 
                imageData = translatedImage;
    }
     
    /**
    * Effectue un zoom autour du pixel (x,y) d'un facteur zoomFactor 
    * @param x : colonne autour de laquelle le zoom sera effectue
    * @param y : rangee autour de laquelle le zoom sera effectue  
    * @param zoomFactor : facteur du zoom a effectuer. Doit etre superieur a 1
    */
    public void zoomIn(int x, int y, double zoomFactor) throws IllegalArgumentException
    {
            
            int width = getWidth();
            int height = getHeight();
            if(zoomFactor < 1.0 || x < 0 || x > width || y < 0 || y > height)
                    throw new IllegalArgumentException();
            
            //On conserve une copie de l'ancienne image avant de commencer les modifications.
            PixelMap oldImage  = new PixelMap(this);
            
            for(int i = 0; i < height; ++i)
            {
                    for(int j = 0; j < width; ++j)
                    {
                            double newDistanceX = (j - x) / zoomFactor;
                            double newDistanceY = (i - y) / zoomFactor;
                            setPixel(oldImage.getPixel((int)(y + newDistanceY), (int)(x + newDistanceX)), i, j);
                    }
            }
    }
}

