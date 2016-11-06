package com.android.appypapy.lipreader;

import android.content.Context;
import org.apache.commons.io.IOUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by kln on 05/11/2016.
 */

public class Constants
{

    public static final Properties LIP_READING_PROPS = new Properties();

    static
    {
	InputStream is;
	String propertiesFileName = "lr.properties";
	try
	{
	    is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName);
	    LIP_READING_PROPS.load(is);
	    is.close();
	}
	catch (IOException e)
	{
	    throw new RuntimeException(e);
	}

    }

    public static final String CLASSIFIER_MODEL_URL = LIP_READING_PROPS.getProperty("CLASSIFIER_MODEL_URL");
    public static final String DEFAULT_TRAINING_SET_ZIP = LIP_READING_PROPS.getProperty("DEFAULT_TRAINING_SET_ZIP");
    public static final String DEFAULT_ARFF_FILE = LIP_READING_PROPS.getProperty("DEFAULT_ARFF_FILE");
    public static final String HAAR_CASCADE_MOUTH_FILE = LIP_READING_PROPS.getProperty("HAAR_CASCADE_MOUTH_FILE");
    public static final String HAAR_CASCADE_EYES_FILE = LIP_READING_PROPS.getProperty("HAAR_CASCADE_EYES_FILE");

    public static final int FRAMES_COUNT = Integer.valueOf(LIP_READING_PROPS.getProperty("FRAMES_COUNT"));
    public static final int SAMPLE_ROW_SHIFT = Integer.valueOf(LIP_READING_PROPS.getProperty("SAMPLE_ROW_SHIFT"));
    public static final int POINT_COUNT = Integer.valueOf(LIP_READING_PROPS.getProperty("POINT_COUNT"));

    public static final short RIGHT_VECTOR_INDEX = 0;
    public static final short LEFT_VECTOR_INDEX = 1;
    public static final short UPPER_VECTOR_INDEX = 2;
    public static final short LOWER_VECTOR_INDEX = 3;

    public static final List<String> VOCABULARY;

    public static void init(Context context)
    {
	try
	{
	    VOCABULARY = IOUtils.readLines(new FileReader(Constants.class.getClassLoader()
		    .getResource(LIP_READING_PROPS.getProperty("DEFAULT_VOCABULARY_FILE")).getFile()));
	}
	catch (IOException e)
	{
	    throw new RuntimeException(e);
	}
    }

    public static class MouthDetector
    {
	public static final String TEMP_DIRECTORY = "mouthDetector";
	public static final String TEMP_FILENAME = "haarcascade_mcs_mouth.xml";
    }

}
