import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class VideoFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final Scalar green = new Scalar(0, 255, 0);
	private static final Scalar red = new Scalar(0, 0, 255);
	
	VideoPane videoPane;
	JPanel contentPane;
	JPanel buttonPane;
	
	JButton calibrateButton;
	JButton enableButton;
	
	Rect currentHandPosition;
	Rect calibratedHandPosition = null;
	
	int calTop = 0;
	int calBottom = 0;
	int calLeft = 0;
	int calRight = 0;
	
	Robot robot;
	int lastKey = 0;
	
	private boolean controlEnabled = false;
	
	private VideoCapture camera = new VideoCapture(0);
	CascadeClassifier cascade = new CascadeClassifier("fist.xml");

    public VideoFrame() {
    	super();
    	
    	try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 550);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());

        videoPane = new VideoPane();
        videoPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        videoPane.setLayout(null);
        
        buttonPane = new JPanel();
        buttonPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
        
        calibrateButton = new JButton("Calibrate");
        calibrateButton.setSize(new Dimension(180, 30));
        calibrateButton.addActionListener(this);
        
        enableButton = new JButton("Enable");
        enableButton.setSize(new Dimension(180, 30));
        enableButton.addActionListener(this);
        
        buttonPane.add(calibrateButton);
        buttonPane.add(enableButton);
        
        contentPane.add(videoPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.EAST);
        setContentPane(contentPane);
        
        new MyThread().start();
    }
    
    public void processFrame() {
    	Mat frame = new Mat();
        camera.read(frame);
        
        Mat grey = new Mat();
        Imgproc.cvtColor(frame, grey, Imgproc.COLOR_RGB2GRAY);
        
        MatOfRect hands = new MatOfRect();
        cascade.detectMultiScale(grey, hands);
        
        if(this.calibratedHandPosition != null) {
    		this.drawRectangle(frame, this.calibratedHandPosition, red);
    	}
        
        if(hands.toArray().length > 0) {
        	this.currentHandPosition = hands.toArray()[0];
        	this.drawRectangle(frame, this.currentHandPosition, green);
        	
        	if(this.controlEnabled) {
        		this.mapControl();
        	}
        }
        
        videoPane.setFrame(matToBufferedImage(frame));
        videoPane.repaint();
    }
    

	@Override
	public void actionPerformed(ActionEvent e) {
    	if(e.getSource().equals(this.calibrateButton)) {
    		this.calibrate();
    	} else if(e.getSource().equals(this.enableButton)) {
    		this.setEnabled();
    	}
	}
	
	private void mapControl() {
		int currTop = this.currentHandPosition.y;
    	int currBottom = this.currentHandPosition.y + this.currentHandPosition.height;
    	int currLeft = this.currentHandPosition.x;
    	int currRight = this.currentHandPosition.x + this.currentHandPosition.width;
		
    	if(this.lastKey != 0) {
    		robot.keyRelease(this.lastKey);
    	}
    	
		if(currBottom < this.calTop) { // Fist moved up
			this.lastKey = KeyEvent.VK_Y;
			System.out.println("Press Up");
		} else if(currTop > this.calBottom) { // Fist moved down
			this.lastKey = KeyEvent.VK_B;
			System.out.println("Press Down");
		} else if(currRight < this.calLeft) { // Fist moved right
			this.lastKey = KeyEvent.VK_D;
			System.out.println("Press Left");
		} else if(currLeft > this.calRight) { // Fist moved left
			this.lastKey = KeyEvent.VK_A;
			System.out.println("Press Right");
		} else {
			this.lastKey = 0;
		}
		
		if(this.lastKey != 0) {
			robot.keyPress(this.lastKey);
		}
	}
    
    private void calibrate() {
    	this.calibratedHandPosition = new Rect(this.currentHandPosition.x - 35,
							    			   this.currentHandPosition.y - 35,
							    			   this.currentHandPosition.width + 70,
							    			   this.currentHandPosition.height + 70);
    	
    	this.calTop = this.calibratedHandPosition.y;
    	this.calBottom = this.calibratedHandPosition.y + this.calibratedHandPosition.height;
    	this.calLeft = this.calibratedHandPosition.x;
    	this.calRight = this.calibratedHandPosition.x + this.calibratedHandPosition.width;
    }
    
    private void setEnabled() {
    	if(!this.controlEnabled) {
    		this.controlEnabled = true;    		
    	} else {
    		this.controlEnabled = false;
    	}
    }
    
    private void drawRectangle(Mat frame, Rect rect, Scalar color) {
    	Imgproc.rectangle(frame, 
    			new Point(rect.x, rect.y), 
    			new Point(rect.x + rect.width, rect.y + rect.height),
    			color);
    }
    
    private BufferedImage matToBufferedImage(Mat frame) {
        int type = frame.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        
        DataBufferByte dataBuffer = (DataBufferByte) image.getRaster().getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }
    
    public static void main(String[] args) {
    	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VideoFrame frame = new VideoFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
 
    class MyThread extends Thread{
        @Override
        public void run() {
            for (;;){
            	processFrame();
                repaint();
                try { Thread.sleep(30); } catch (InterruptedException e) { }
            }  
        } 
    }
}