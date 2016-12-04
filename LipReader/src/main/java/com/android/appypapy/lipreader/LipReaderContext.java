package com.android.appypapy.lipreader;

import android.content.Context;
import com.android.applypapy.lipreader.R;
import com.android.appypapy.lipreader.classifier.Classifier;
import com.android.appypapy.lipreader.classifier.MultiLayerPerceptronClassifier;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LipReaderContext
{

    private static final ConcurrentMap<Integer, LipVideoSample> instances = new ConcurrentHashMap<>();
    private static final AtomicInteger counter = new AtomicInteger(0);

    private static final int TRAIN_EACH = 100;
    private static Classifier classifier;

    public static void init(Context context)
    {
	try
	{
	    InputStream modelFileInputStream = context.getResources().openRawResource(R.raw.yesnohello);
	    //classifier = new MultiLayerPerceptronClassifier(modelFileInputStream);
	    modelFileInputStream.close();
	}
	catch (Exception e)
	{
	}
    }


    public static LipVideoSample normalize(LipVideoSample sample)
    {
	return LipReading.normalize(sample);
    }

    public static String classify(LipVideoSample sample)
    {
	return classifier.test(sample);
    }

    public static int put(LipVideoSample sample)
    {
	int count = count();
	instances.put(count, sample);
	if ((count % TRAIN_EACH == 0) && (count != 0))
	{
	    startTraining();
	}
	return count;
    }

    public static void startTraining()
    {
	new Thread(new Runnable()
	{
	    @Override
	    public void run()
	    {
		classifier.train(new Vector<>(instances.values()));
	    }
	}).start();
    }

    public static LipVideoSample get(int id)
    {
	return instances.get(id);
    }

    private static int count()
    {
	return counter.getAndIncrement();
    }

    public static LipVideoSample remove(int id)
    {
	return instances.remove(id);
    }

    public static Map<Integer, LipVideoSample> list()
    {
	return new HashMap<>(instances);
    }
}
