import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Test {
   public static void main( String[] args ) throws InterruptedException {
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

      VideoCapture camera = new VideoCapture(0);
      camera.open(0);
      
      Thread.sleep(1000);
      
      Mat frame = new Mat();
      
      
      camera.read(frame);
      
      System.out.println(frame.width() + " " + frame.height());
      
      saveImage(matToBufferedImage(frame));
      camera.release();
      
      return;
   }
   
   public static void saveImage(BufferedImage img) {        
       try {
           File outputfile = new File("test.png");
           ImageIO.write(img, "png", outputfile);
       } catch (Exception e) {
           System.out.println("error");
       }
   }

   public static BufferedImage matToBufferedImage(Mat frame) {
       int type = frame.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
       BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
       
       DataBufferByte dataBuffer = (DataBufferByte) image.getRaster().getDataBuffer();
       byte[] data = dataBuffer.getData();
       frame.get(0, 0, data);

       return image;
   }
}