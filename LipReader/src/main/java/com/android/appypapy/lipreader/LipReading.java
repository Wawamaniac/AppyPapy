package com.android.appypapy.lipreader;

import com.android.appypapy.lipreader.classifier.Classifier;
import com.android.appypapy.lipreader.classifier.MultiLayerPerceptronClassifier;
import com.android.appypapy.lipreader.extractor.AbstractFeatureExtractor;
import com.android.appypapy.lipreader.normalizer.CenterNormalizer;
import com.android.appypapy.lipreader.normalizer.LinearStretchTimeNormalizer;
import com.android.appypapy.lipreader.normalizer.Normalizer;
import com.android.appypapy.lipreader.normalizer.SkippedFramesNormalizer;
import weka.core.xml.XStream;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


public class LipReading
{

    private static void testModel(List<LipVideoSample> trainingSet, Classifier classifier)
    {
	int correct = 0;
	for (LipVideoSample sample : trainingSet)
	{
	    try
	    {
		String output = classifier.test(normalize(sample));
		if (sample.getLabel().equals(output))
		{
		    correct++;
		}
		else
		{
		}
	    }
	    catch (Exception e)
	    {
	    }
	}
    }

    private static void test(AbstractFeatureExtractor fe, String testFile, String trainigSetZipFile) throws Exception
    {
	File modelFile = new File(Constants.CLASSIFIER_MODEL_URL);

	if (Utils.isSourceUrl(Constants.CLASSIFIER_MODEL_URL))
	{
	    modelFile = new File(Utils.getFileNameFromUrl(trainigSetZipFile));
	    if (!modelFile.exists())
	    {
		Utils.get(Constants.CLASSIFIER_MODEL_URL);
	    }
	}

//	Classifier classifier = new MultiLayerPerceptronClassifier(new FileInputStream(modelFile));
//	LipVideoSample sample = fe.extract(testFile);
//	sample = normalize(sample);
//
//	System.out.println("got the word: " + classifier.test(sample));
    }

    private static void dataset(AbstractFeatureExtractor fe, String folderPath) throws Exception
    {
	File samplesDir = new File(folderPath);

	for (String sampleName : samplesDir.list())
	{
	    File sampleFile = new File(samplesDir.getAbsolutePath() + "/" + sampleName);

	    if (sampleFile.isFile() && sampleFile.getName().contains("MOV"))
	    {
		LipVideoSample sample = fe.extract(sampleFile.getAbsolutePath());
		sample = normalize(sample);
		XStream.write(sampleName.split("\\.")[0] + ".xml", sample);
	    }
	}
    }

    private static LipVideoSample normalize(LipVideoSample sample, Normalizer... normalizers)
    {
	if (sample.getOriginalMatrixSize() == 0)
	{
	    sample.setOriginalMatrixSize(sample.getMatrix().size());
	}

	for (Normalizer normalizer : normalizers)
	{
	    sample = normalizer.normalize(sample);
	}

	return sample;
    }

    /**
     * Normalize sample using predefined normalizers
     *
     * @param sample the sample to normalize
     * @return the normalized sample
     */
    public static LipVideoSample normalize(LipVideoSample sample)
    {
	Normalizer sfn = new SkippedFramesNormalizer();
	Normalizer cn = new CenterNormalizer();
	Normalizer tn = new LinearStretchTimeNormalizer();

	return normalize(sample, sfn, cn, tn);
    }

}
