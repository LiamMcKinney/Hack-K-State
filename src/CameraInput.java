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
    private static Mat minRedHSV;
    private static Mat maxRedHSV;
    private static Mat minGreenHSV;
    private static Mat maxGreenHSV;
    private static Hand rightHand = Main.rightHand;
    private static Hand leftHand = Main.leftHand;
    private static int minArea = 3000;


    static void initCamera() {
        String classifierName = null;
        try {

        } catch(Throwable e){

        }


        try {
            grabber = FrameGrabber.createDefault(0);
            grabber.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }

        //converts between Mats, Images, Frames, etc.
        converter = new OpenCVFrameConverter.ToMat();

        frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma()/grabber.getGamma());

        minRedHSV = new Mat(1, 1, CV_32SC4, new IntPointer(163, 186, 60, 0));
        maxRedHSV = new Mat(1, 1, CV_32SC4, new IntPointer(180, 255, 242, 0));

        minGreenHSV = new Mat(1, 1, CV_32SC4, new IntPointer(0, 50, 172, 0));
        maxGreenHSV = new Mat(1, 1, CV_32SC4, new IntPointer(18, 90, 255, 0));
    }

    static boolean updateCamera() {
        Mat grabbedImage = null;
        try {
            grabbedImage = converter.convert(grabber.grab());
            grabbedImage.
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }
        int height = grabbedImage.rows();
        int width = grabbedImage.cols();

        Mat grayImage = new Mat(height, width, CV_8UC1);
        Mat HSVImage = new Mat(height, width, CV_8UC1);
        Mat greenImage = new Mat(height, width, CV_8UC1);
        Mat redImage = new Mat(height, width, CV_8UC1);

        cvtColor(grabbedImage, HSVImage, CV_BGR2HSV);
        cvtColor(grabbedImage, grayImage, CV_BGR2GRAY);



        inRange(HSVImage, minRedHSV, maxRedHSV, redImage);
        inRange(HSVImage, minGreenHSV, maxGreenHSV, greenImage);

        MatVector contours = new MatVector();
        findContours(redImage, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);

        long n = contours.size();
        Rect biggestRect = new Rect(0,0,0,0);
        Rect rect;
        for (long i = 0; i < n; i++) {
            Mat contour = contours.get(i);
            rect = boundingRect(contour);
            if(rect.area() > biggestRect.area() && rect.area() > minArea){
                biggestRect = rect;
            }
            Mat points = new Mat();
            approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
            drawContours(grabbedImage, new MatVector(points), -1, Scalar.BLACK);
        }
        leftHand.setX(biggestRect.x());
        leftHand.setY(biggestRect.y());
        rectangle(grabbedImage, biggestRect, Scalar.RED);

        findContours(greenImage, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);

        n = contours.size();
        biggestRect = new Rect(0,0,0,0);
        for (long i = 0; i < n; i++) {
            Mat contour = contours.get(i);
            rect = boundingRect(contour);
            if(rect.area() > biggestRect.area() && rect.area() > minArea){
                biggestRect = rect;
            }
            Mat points = new Mat();
            approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
            drawContours(grabbedImage, new MatVector(points), -1, Scalar.RED);
        }
        rightHand.setX(biggestRect.x());
        rightHand.setY(biggestRect.y());
        rectangle(grabbedImage, biggestRect, Scalar.RED);


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