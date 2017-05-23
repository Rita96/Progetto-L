package machines;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;

/**
 *
 * @author Manuele
 */
public class QRCodeHandler {
    private final String filePath = "images/qrbuffer.png";
    private final int size = 125;
    
    /**
     * Costruisce il QR Code a partire dalla stringa indicata. Di default il QR Code
     * viene salvato come file .png nella cartella images, sotto il nome di qrbuffer.png
     * @param qrCodeText
     * @return
     * @throws WriterException
     * @throws IOException 
     */
    public String buildQRCodeFromString(String qrCodeText) throws WriterException, IOException{
        //Creo l'Hashtable. Serve per costruire la BitMatrix e comunicare qual è il
        //comportamento da tenere in caso di errore. In questo caso se si verifica un
        //errore la parola viene modificata
        Hashtable hintMap = new Hashtable();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        
        //Istanzio il QRCodeWriter. E' quello che effettua la codifica da stringa
        //a QR Code. Il QR Code viene memorizzato in una BitMatrix
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        
        //Crea una nuova immagine con la sua grafica. La grafica serve a colorare 
        //l'immagine di bianco/nero
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        
        //Coloro l'immagine
        setGraphicsBackground(graphics, Color.WHITE);
        addColoredDots(graphics, byteMatrix, Color.BLACK);
        
        ImageIO.write(image, "png", new File(filePath));
        return filePath;
    }
    
    /**
     * Colora tutta la graphics del colore indicato. L'istruzione fillRect prende 
       come parametri x, y, width e height, e colora del colore selezionato i pixel
       da x a x + width -1 e da y a y + height -1. Passando 0, 0, size e size colora
       del colore selezionato tutta l'immagine
     * @param graphics
     * @param color 
     */
    private void setGraphicsBackground(Graphics2D graphics, Color color) {
        graphics.setColor(color);
        graphics.fillRect(0, 0, size, size);
    }
    
    /**
     * Aggiunge i quadrati del colore indicato. Per farlo vengono controllate 
     * tutte le righe e tutte le colonne di matrix, e 
     * @param graphics
     * @param matrix
     * @param color 
     */
    private void addColoredDots(Graphics2D graphics, BitMatrix matrix, Color color) {
        graphics.setColor(color);
        
        //Ciclo tutta la matrice e nei posti dove la matrice è piena coloro del
        //colore indicato
        for (int i = 0; i < matrix.getWidth(); i++) {
            for (int j = 0; j < matrix.getHeight(); j++) {
                //Per capire se colorare o no, uso il metodo get. Get restituisce
                //un booleano pari a true se il pixel nella posizione i,j è nero
                //N.b: get(i, j) prende l'elemento della riga j e colonna i (contrario dello standard)
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
    }
}