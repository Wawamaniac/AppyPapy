package com.android.appypapy.lipreader.extractor;

import com.android.appypapy.lipreader.LipVideoSample;
import com.android.appypapy.lipreader.Utils;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.VideoInputFrameGrabber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This abstract class purpose is to extract user`s lip coordinates from video.
 * The classes that extend AbstractFeatureExtractor will extract 4 point of user lip:
 * - Coordinate of right connection between upper and lower lips.
 * - Coordinate of left connection between upper and lower lips.
 * - Coordinate of middle upper lip.
 * - Coordinate of middle lower lip.
 */
public abstract class AbstractFeatureExtractor
{
    protected FrameGrabber grabber;
    private LipVideoSample sample;
    private boolean output = false;
    private boolean gui = true;

    /**
     * Gets source name and returns Sample data type contains lip coordinates
     *
     * @param source url of video file or live camera
     * @return Sample contains lip coordinate
     * @throws Exception
     */
    public LipVideoSample extract(String source) throws Exception
    {
	grabber = getGrabber(source);
	grabber.start();

	LipVideoSample sample = getPoints();

	grabber.stop();
	return sample;
    }

    public FrameGrabber getGrabber(String source) throws Exception
    {
	String sampleName;
	if (Utils.isSourceUrl(source))
	{
	    Utils.get(source);
	    sampleName = Utils.getFileNameFromUrl(source);
	    grabber = new FFmpegFrameGrabber(sampleName);
	}
	else if (Utils.isSourceFile(source))
	{
	    sampleName = Utils.getFileName(source);
	    grabber = new FFmpegFrameGrabber(source);
	}
	else
	{
	    //try open the default camera
	    grabber = new VideoInputFrameGrabber(0);
	    sampleName =
		    "web cam " + new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault()).format(new Date());
	}
	setSample(new LipVideoSample(sampleName));
	return grabber;
    }

    protected LipVideoSample getPoints() throws Exception
    {
//	opencv_core.IplImage grabbed;
//	CanvasFrame frame = null;
//	FrameRecorder recorder = null;
//
//	if (isGui())
//	{
//	    frame = new CanvasFrame(getSample().getId(), CanvasFrame.getGamma(0) / grabber.getGamma());
//	    if (isOutput())
//	    {
//		String[] sampleNameSplit = getSample().getId().split("\\.");
//		recorder = new FFmpegFrameRecorder(sampleNameSplit[0] + "-output." + sampleNameSplit[1],
//			grabber.getImageWidth(), grabber.getImageHeight());
//		recorder.setFrameRate(grabber.getFrameRate());
//		recorder.start();
//	    }
//	}
//
//	while ((grabbed = grabber.grab()) != null)
//	{
//	    List<Integer> frameCoordinates = getPoints(grabbed);
//
//	    if (isGui())
//	    {
//		paintCoordinates(grabbed, frameCoordinates);
//		frame.showImage(grabbed);
//		if (isOutput())
//		{
//		    recorder.record(grabbed);
//		}
//	    }
//	    getSample().getMatrix().add(frameCoordinates);
//	}
//	if (isGui())
//	{
//	    frame.dispose();
//	    if (isOutput())
//	    {
//		recorder.stop();
//	    }
//	}
//	getSample().setWidth(grabber.getImageWidth());
//	getSample().setHeight(grabber.getImageHeight());
//	getSample().setOriginalMatrixSize(getSample().getMatrix().size());
//
//	return getSample();
	return null;
    }

    /**
     * Gets an IplImage and List represents coordinates of 4 positions of lips and paint these coordinates on frame
     *
     * @param grabbed          video frame
     * @param frameCoordinates coordinates of lips to paint
     */
    abstract public void paintCoordinates(opencv_core.IplImage grabbed, List<Integer> frameCoordinates);

    /**
     * Gets IplImage (video frame) and extract lip coordinates in this frame
     *
     * @param grabbed
     * @return List of 8 numbers representing X,Y coordinates of each location(Right, Left, Upper, Lower)
     * @throws Exception
     * @precondition grabbed != null
     * @postcondition List<Integer> coordinates of lip. If no lip found, than 0 is return instead of coordinate
     */
    abstract public List<Integer> getPoints(opencv_core.IplImage grabbed) throws Exception;

    public LipVideoSample getSample()
    {
	return sample;
    }

    public void setSample(LipVideoSample sample)
    {
	this.sample = sample;
    }

    public boolean isGui()
    {
	return this.gui && !Utils.isCI();
    }

    public void setGui(boolean gui)
    {
	this.gui = gui;
    }

    public boolean isOutput()
    {
	return output;
    }

    public void setOutput(boolean shouldOutput)
    {
	this.output = shouldOutput;
    }
}
