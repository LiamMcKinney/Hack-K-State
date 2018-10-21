import java.io.File;
import java.net.URL;
import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_calib3d.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

public class CameraInput {
    private static FrameGrabber grabber;
    private static OpenCVFrameConverter.ToMat converter;
    private static CanvasFrame frame;
    private static Mat minGreenHSV;
    private static Mat maxGreenHSV;
    private static Hand rightHand = Main.rightHand;
    private static Hand leftHand = Main.leftHand;
    private static int minArea = 800;


    static void initCamera() {
        String classifierName = null;
        try {

        } catch(Throwable e){

        }


        try {
            grabber = FrameGrabber.createDefault(0);
            grabber.start();
            grabber.setImageWidth(32);
            grabber.setImageHeight(24);
            System.out.println("width: "+grabber.getImageWidth());
            System.out.println("height: "+grabber.getImageHeight());

        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }

        //converts between Mats, Images, Frames, etc.
        converter = new OpenCVFrameConverter.ToMat();

        frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma()/grabber.getGamma());

        minGreenHSV = new Mat(1, 1, CV_32SC4, new IntPointer(0, 39, 138, 0));
        maxGreenHSV = new Mat(1, 1, CV_32SC4, new IntPointer(35, 90, 255, 0));
    }

    static boolean updateCamera() {
        Mat grabbedImage = null;
        try {
            grabbedImage = converter.convert(grabber.grab());
            resize(grabbedImage, grabbedImage, new Size(320,240));
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        int height = grabbedImage.rows();
        int width = grabbedImage.cols();

        Mat grayImage = new Mat(height, width, CV_8UC1);
        Mat HSVImage = new Mat(height, width, CV_8UC1);
        Mat armImage = new Mat(height, width, CV_8UC1);

        cvtColor(grabbedImage, HSVImage, CV_BGR2HSV);
        cvtColor(grabbedImage, grayImage, CV_BGR2GRAY);

        inRange(HSVImage, minGreenHSV, maxGreenHSV, armImage);

        MatVector contours = new MatVector();
        findContours(armImage, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);

        long n = contours.size();
        Rect biggestRect = new Rect(0,0,0,0);
        Rect bigRec2 = new Rect(0,0,0,0);
        Rect rect;
        for (long i = 0; i < n; i++) {
            Mat contour = contours.get(i);
            rect = boundingRect(contour);
            if(rect.area() > biggestRect.area() && rect.area() > minArea){
                bigRec2 = biggestRect;
                biggestRect = rect;
            }else if(rect.area() > bigRec2.area() && rect.area() > minArea){
                bigRec2 = rect;
            }
            Mat points = new Mat();
            approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
            drawContours(grabbedImage, new MatVector(points), -1, Scalar.BLACK);
        }
        if(biggestRect.x() < bigRec2.x()){
            rightHand.setX(biggestRect.x() + (1/2 * biggestRect.width()));
            rightHand.setY(biggestRect.y() + (1/2 * biggestRect.height()));
            rectangle(grabbedImage, biggestRect, Scalar.RED);
            leftHand.setX(bigRec2.x() + (1/2 * bigRec2.width()));
            leftHand.setY(bigRec2.y() + (1/2 * bigRec2.height()));
            rectangle(grabbedImage, bigRec2, Scalar.BLUE);
        }else{
            leftHand.setX(biggestRect.x() + (1/2 * biggestRect.width()));
            leftHand.setY(biggestRect.y() + (1/2 * biggestRect.height()));
            rectangle(grabbedImage, biggestRect, Scalar.BLUE);
            rightHand.setX(bigRec2.x() + (1/2 * bigRec2.width()));
            rectangle(grabbedImage, bigRec2, Scalar.RED);
        }
        //strumming rectangle
        rectangle(grabbedImage, new Point(0, GuitarHeroRules.Y_POS_DOWN), new Point(1000, 1000), Scalar.RED);

        //note rectangles
        rectangle(grabbedImage, new Point(0, 0), new Point(GuitarHeroRules.X_POS_2 - 1, 1000), Scalar.BLUE);
        rectangle(grabbedImage, new Point(GuitarHeroRules.X_POS_2, 0), new Point(GuitarHeroRules.X_POS_1 - 1, 1000), Scalar.YELLOW);
        rectangle(grabbedImage, new Point(GuitarHeroRules.X_POS_1, 0), new Point(GuitarHeroRules.X_POS_0 - 1, 1000), Scalar.RED);
        rectangle(grabbedImage, new Point(GuitarHeroRules.X_POS_0, 0), new Point(grabbedImage.arrayWidth() - 1, 1000), Scalar.GREEN);

        if(frame.isVisible() && (grabbedImage) != null) {
            Frame rotatedFrame = converter.convert(grabbedImage);
            frame.showImage(rotatedFrame);
        }
        return (frame.isVisible() && (grabbedImage) != null);
    }


    public void close() {
        frame.dispose();

        try {
            grabber.stop();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
    }
}