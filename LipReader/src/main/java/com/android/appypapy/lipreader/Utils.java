package com.android.appypapy.lipreader;

import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.xml.XStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This class provides static functionality for the LipReading framework.
 */
public class Utils
{
    public static final int X_INDEX = 0;
    public static final int Y_INDEX = 1;

    /**
     * this function will download the file from the url into the filename specified
     * in the current directory
     *
     * @param urlToDownload
     * @throws IOException
     */
    public static void get(String urlToDownload) throws IOException
    {
	URL url = new URL(urlToDownload);
	InputStream in = url.openStream();
	String filename = getFileNameFromUrl(urlToDownload);
	int size = tryGetFileSize(url);
	OutputStream out = new FileOutputStream(filename);
	byte[] buf = new byte[4096];
	int len;
	DecimalFormat formatter = new DecimalFormat("###,###,###,###");
	System.out.println("downloading " + filename + " (" + formatter.format(size / 1024) + " kB) from " + url +
		":");
	int i = 0;
	int totalLen = 0;
	while ((len = in.read(buf)) >= 0)
	{
	    out.write(buf, 0, len);
	    totalLen += len;
	    i = (100 * totalLen) / size;

	    String print = "\r[                                                  ]" + i + "%";
	    for (int j = 0; j < i; j += 2)
	    {
		print = print.replaceFirst(" ", "*");
	    }
	    System.out.print(print);
	}

	System.out.println();
	in.close();
	out.close();
    }

    public static boolean isCI()
    {
	return System.getProperty("user.name").equals("travis");
    }

    public static void textToSpeech(String text) throws Exception
    {
	// TODO KLN google text to speach
    }

    public static String getFileNameFromUrl(String urlToDownload) throws UnsupportedEncodingException
    {
	String[] split = urlToDownload.split("/");
	String fileName = split[split.length - 1];
	return URLDecoder.decode(fileName, "ISO-8859-1");
    }

    private static int tryGetFileSize(URL url)
    {
	HttpURLConnection conn = null;

	try
	{
	    conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("HEAD");
	    conn.getInputStream();
	    return conn.getContentLength();
	}
	catch (IOException e)
	{
	    return -1;
	}
	finally
	{
	    if (conn != null)
	    {
		conn.disconnect();
	    }
	}
    }

    public static String getFileName(String source)
    {
	String s = System.getProperty("file.separator");

	if (!s.equals("/"))
	{
	    s = "\\\\";
	}

	String[] split = source.split(s);
	return split[split.length - 1];
    }

    public static List<LipVideoSample> getTrainingSetFromZip(String zipUrl) throws Exception
    {
	String name = zipUrl;
	if (!Utils.isSourceUrl(zipUrl))
	{
	    name = zipUrl;
	}
	else
	{
	    if (!new File(Utils.getFileNameFromUrl(name)).exists())
	    {
		Utils.get(zipUrl);
	    }
	    name = Utils.getFileNameFromUrl(name);
	}
	ZipFile samplesZip = new ZipFile(name);
	List<LipVideoSample> trainingSet = new Vector<>();
	Enumeration<? extends ZipEntry> entries = samplesZip.entries();
	while (entries.hasMoreElements())
	{
	    LipVideoSample read = (LipVideoSample) XStream.read(samplesZip.getInputStream(entries.nextElement()));
	    trainingSet.add(read);
	}
	samplesZip.close();
	return trainingSet;
    }

    public static void dataSetToCSV(List<LipVideoSample> trainingSet, String outputFile) throws Exception
    {
//	CSVWriter writer = new CSVWriter(new FileWriter(outputFile));
//	int instanceSize = (Constants.FRAMES_COUNT * Constants.POINT_COUNT * 2) + Constants.SAMPLE_ROW_SHIFT;
//	String[] title = new String[instanceSize];
//	title[0] = "word";
//	title[1] = "original_length";
//	title[2] = "width";
//	title[3] = "height";
//	for (int i = Constants.SAMPLE_ROW_SHIFT; i < instanceSize; i++)
//	{
//	    title[i] = String.valueOf(i - Constants.SAMPLE_ROW_SHIFT);
//	}
//	List<String[]> samplesStrings = new ArrayList<String[]>();
//	samplesStrings.add(title);
//	for (Sample sample : trainingSet)
//	{
//	    sample = LipReading.normalize(sample);
//	    samplesStrings.add(sample.toCSV());
//	}
//	writer.writeAll(samplesStrings);
//	writer.close();
    }

    public static void matrixToCSV(double[][] matrix, String outputFile) throws Exception
    {
//	CSVWriter writer = new CSVWriter(new FileWriter(outputFile));
//	List<String[]> rows = new ArrayList<String[]>();
//	for (double[] ds : matrix)
//	{
//	    String[] row = new String[ds.length];
//	    for (int i = 0; i < ds.length; i++)
//	    {
//		row[i] = String.valueOf(ds[i]);
//	    }
//	    rows.add(row);
//	}
//	writer.writeAll(rows);
//	writer.close();
    }

    public static void dataSetToARFF(List<LipVideoSample> trainingSet, String outputFile) throws Exception
    {
	String csvFileName = outputFile.split("\\.")[0] + ".csv";
	dataSetToCSV(trainingSet, csvFileName);
	CSVLoader csvLoader = new CSVLoader();
	ArffSaver arffSaver = new ArffSaver();
	File csvFile = new File(csvFileName);
	File arffFile = new File(outputFile);
	csvLoader.setFile(csvFile);
	arffSaver.setFile(arffFile);
	arffSaver.setStructure(csvLoader.getStructure());
	arffSaver.setInstances(csvLoader.getDataSet());
	arffSaver.writeBatch();
	csvFile.delete();
    }

    public static List<String> readFile(String resource)
    {
	String string =
		convertStreamToString(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource));
	return Arrays.asList(string.toLowerCase().split("\\r?\\n"));
    }

    public static String convertStreamToString(InputStream is)
    {
	String ans = "";
	Scanner s = new Scanner(is);
	s.useDelimiter("\\A");
	ans = s.hasNext() ? s.next() : "";
	s.close();
	return ans;
    }

    public static boolean isWindows()
    {
	return System.getProperty("os.name").toLowerCase().contains("win");
    }

//    public static CvScalar getCvScalar(String property)
//    {
//	String[] split = property.split(",");
////        return new CvScalar(Double.valueOf(split[0]),
////                Double.valueOf(split[1]),
////                Double.valueOf(split[2]),
////                Double.valueOf(split[3]));
//	return new CvScalar().red(Double.valueOf(split[0])).green(Double.valueOf(split[1]))
//		.blue(Double.valueOf(split[2])).position(Integer.valueOf(split[3]));
//    }

    public static int getMinIndex(double[] ds)
    {
	return getMinIndex(ds, true);
    }

    public static int getMinIndex(double[] ds, boolean includeZero)
    {
	int ans = 0;
	double min = Double.MAX_VALUE;
	for (int i = 0; i < ds.length; i++)
	{
	    if (includeZero || ds[i] != 0)
	    {
		min = Math.min(min, ds[i]);
		if (min == ds[i])
		{
		    ans = i;
		}
	    }
	}
	return ans;
    }

    public static boolean isSourceFile(String source)
    {
	return null != source && !isSourceUrl(source);
    }

    public static boolean isSourceUrl(String source)
    {
	return null != source && source.contains("://");
    }

    public static void writeSampleToXML(String folderPath, String sampleName, LipVideoSample sample) throws Exception
    {
	File samplesDir = new File(folderPath);
	if (!samplesDir.exists())
	{
	    samplesDir.mkdirs();
	}
	File sampleFile = new File(samplesDir.getAbsolutePath() + "/" + sampleName);
	FileOutputStream fos = new FileOutputStream(sampleFile);
	XStream.write(sampleFile, sample);
	fos.close();
    }

    static boolean isAndroid()
    {
	return System.getProperty("java.vm.vendor").toLowerCase().contains("android");
    }

    public static LipVideoSample getSampleFromPacket(SamplePacket sp)
    {
	LipVideoSample sample = new LipVideoSample();
	sample.setHeight(sp.getHeight());
	sample.setWidth(sp.getWidth());
	sample.setOriginalMatrixSize(sp.getOriginalMatrixSize());
	sample.setId(sp.getId());
	sample.setLabel(sp.getLabel());

	for (int i = 0; i < sp.getMatrix().length; i++)
	{
	    Vector<Integer> vec = new Vector<Integer>();
	    for (int j = 0; j < sp.getMatrix()[i].length; j++)
	    {
		vec.add(sp.getMatrix()[i][j]);
	    }
	    sample.getMatrix().add(vec);
	}

	return sample;
    }

    public static SamplePacket getPacketFromSample(LipVideoSample s)
    {
	SamplePacket sample = new SamplePacket();
	sample.setHeight(s.getHeight());
	sample.setWidth(s.getWidth());
	sample.setLabel(s.getLabel());
	sample.setId(s.getId());
	sample.setOriginalMatrixSize(s.getOriginalMatrixSize());

	int[][] matrix = new int[s.getMatrix().size()][];
	for (int i = 0; i < matrix.length; i++)
	{
	    matrix[i] = new int[s.getMatrix().get(i).size()];
	    for (int j = 0; j < matrix[i].length; j++)
	    {
		matrix[i][j] = s.getMatrix().get(i).get(j);
	    }
	}
	sample.setMatrix(matrix);
	return sample;
    }

    public static int[] getCenter(List<Integer> vector)
    {
	int[] center = { 0, 0 };
	for (int i = 0; i < vector.size(); i++)
	{
	    if (i % 2 == 0)
	    {
		center[X_INDEX] += vector.get(i);
	    }
	    else
	    {
		center[Y_INDEX] += vector.get(i);
	    }
	}
	center[X_INDEX] = (int) Math.round(((double) center[X_INDEX]) / (vector.size() / 2));
	center[Y_INDEX] = (int) Math.round(((double) center[Y_INDEX]) / (vector.size() / 2));
	return center;
    }
}
