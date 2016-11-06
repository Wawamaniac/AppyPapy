package com.android.appypapy.lipreader.classifier;

import com.android.appypapy.lipreader.Constants;
import com.android.appypapy.lipreader.LipVideoSample;
import weka.classifiers.AbstractClassifier;
import weka.core.Attribute;
import weka.core.Debug;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An abstract super-type wrapper for Weka classifiers. Provides all the basic Classifier functionality for Weka
 * classifiers.
 * Created with IntelliJ IDEA.
 * User: Dagan
 * Date: 24/05/13
 * Time: 20:17
 */
public abstract class WekaClassifier implements Classifier
{
    private static final int INSTANCE_SIZE =
	    (Constants.FRAMES_COUNT * Constants.POINT_COUNT * 2) + Constants.SAMPLE_ROW_SHIFT;
    protected List<String> vocabulary = Constants.VOCABULARY;
    protected AbstractClassifier classifier;
    private List<LipVideoSample> samples;

    public WekaClassifier() throws Exception
    {
    }

    public WekaClassifier(InputStream modelFileInputStream) throws Exception
    {
	Object read = SerializationHelper.read(modelFileInputStream);
	this.classifier = (AbstractClassifier) read;
    }

    public WekaClassifier(String modelFile) throws Exception
    {
	Object read = SerializationHelper.read(modelFile);
	this.classifier = (AbstractClassifier) read;
    }

    @Override
    public String test(LipVideoSample test)
    {
	double ans = -1;
	try
	{
	    Instance sampleToInstance = sampleToInstance(test);

	    ans = classifier.classifyInstance(sampleToInstance);
	}
	catch (Exception e)
	{
	    throw new RuntimeException(e);
	}
	return vocabulary.get((int) ans);
    }

    protected Instance sampleToInstance(LipVideoSample sample)
    {
	Instance instance = new DenseInstance(INSTANCE_SIZE);
	instance.setMissing(0);
	instance.setValue(1, sample.getOriginalMatrixSize());
	instance.setValue(2, sample.getWidth());
	instance.setValue(3, sample.getHeight());
	for (int i = 0; i < INSTANCE_SIZE - Constants.SAMPLE_ROW_SHIFT; i++)
	{
	    instance.setValue(i + Constants.SAMPLE_ROW_SHIFT, sample.getMatrix().get(i / 8).get(i % 8));
	}
	return instance;
    }

    public void trainFromFile(String arffFilePath) throws Exception
    {
	ArffLoader loader = new ArffLoader();
	loader.setSource(new File(arffFilePath).toURI().toURL());
	Instances dataSet = loader.getDataSet();
	dataSet.setClassIndex(0);
	AbstractClassifier c = getNewClassifierInstance();
	c.buildClassifier(dataSet);
	classifier = c;
	SerializationHelper.write("mp-classifier.model", classifier);
    }

    public List<String> getVocabulary()
    {
	return vocabulary;
    }

    public void setVocabulary(List<String> vocabulary)
    {
	this.vocabulary = vocabulary;
    }

    @Override
    public void update(LipVideoSample train)
    {
	this.samples.add(train);
	this.train(samples);
    }

    @Override
    public void train(List<LipVideoSample> trainingSet)
    {
	this.samples = trainingSet;
	ArrayList<Attribute> list = Collections.list(sampleToInstance(trainingSet.get(0)).enumerateAttributes());
	Instances instances = new Instances("dataset", list, trainingSet.size());
	for (LipVideoSample sample : trainingSet)
	{
	    instances.add(sampleToInstance(sample));
	}
	try
	{
	    AbstractClassifier c = getNewClassifierInstance();
	    c.buildClassifier(instances);
	    classifier = c;
	}
	catch (Exception e)
	{
	    throw new RuntimeException(e);
	}
    }

    public void saveToFile(String file)
    {
	Debug.saveToFile(file, classifier);
    }

    protected abstract AbstractClassifier getNewClassifierInstance();
}
