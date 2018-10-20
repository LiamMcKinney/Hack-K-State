import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class CameraInput {
    FrameGrabber grabber;
    OpenCVFrameConverter.ToMat converter;
    CanvasFrame frame;
    Mat minRedHSV;
    Mat maxRedHSV;
    Mat minGreenHSV;
    Mat maxGreenHSV;
    Hand rightHand = new Hand(0,0);
    Hand leftHand = new Hand(0,0);

    public CameraInput() throws Exception{
        grabber = FrameGrabber.createDefault(0);
        grabber.start();

        //converts between Mats, Images, Frames, etc.
        converter = new OpenCVFrameConverter.ToMat();

        frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma()/grabber.getGamma());

        minRedHSV = new Mat(1, 1, CV_32SC4, new IntPointer(163, 186, 60, 0));
        maxRedHSV = new Mat(1, 1, CV_32SC4, new IntPointer(180, 255, 242, 0));

        minGreenHSV = new Mat(1, 1, CV_32SC4, new IntPointer(76, 78, 119, 0));
        maxGreenHSV = new Mat(1, 1, CV_32SC4, new IntPointer(89, 255, 255, 0));
    }

    public boolean updateCamera() throws Exception {
        Mat grabbedImage = converter.convert(grabber.grab());
        int height = grabbedImage.rows();
        int width = grabbedImage.cols();

        Mat grayImage = new Mat(height, width, CV_8UC1);
        Mat greenImage = new Mat(height, width, CV_8UC1);
        Mat redImage = new Mat(height, width, CV_8UC1);

        cvtColor(grabbedImage, grayImage, CV_BGR2HSV);

        inRange(grayImage, minRedHSV, maxRedHSV, redImage);
        inRange(grayImage, minGreenHSV, maxGreenHSV, greenImage);

        MatVector contours = new MatVector();
        findContours(redImage, contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);

        long n = contours.size();
        Rect biggestRect = new Rect(0,0,0,0);
        Rect rect;
        for (long i = 0; i < n; i++) {
            Mat contour = contours.get(i);
            rect = boundingRect(contour);
            if(rect.area() > biggestRect.area()){
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
            if(rect.area() > biggestRect.area()){
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

    public Hand getLeftHand() throws Exception{
        return leftHand;
    }
    public Hand getRightHand() throws Exception{
        return rightHand;
    }

    public void close() throws Exception {
        frame.dispose();
        grabber.stop();
    }
}