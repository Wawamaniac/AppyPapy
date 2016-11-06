package com.android.appypapy.lipreader.normalizer;

import android.graphics.Point;
import com.android.appypapy.lipreader.LipVideoSample;
import com.android.appypapy.lipreader.Utils;

import java.util.List;

/**
 * This normalizer class intends to normalize the given Sample coordinates to be around (0,0).
 * The center point of Sample`s lip coordinates will be (0,0) after normalization.
 */
public class CenterNormalizer implements Normalizer
{


    @Override
    public LipVideoSample normalize(LipVideoSample sample)
    {

	for (int i = 0; i < sample.getMatrix().size(); i++)
	{
	    List<Integer> vector = sample.getMatrix().get(i);
	    int[] center = Utils.getCenter(vector);

	    if (i == 0 && sample.getLeftEye() != null && sample.getRightEye() != null)
	    {
		//If first frame - normalize also eyes
		sample.setLeftEye(new Point(sample.getLeftEye().x - center[Utils.X_INDEX],
			sample.getLeftEye().y - center[Utils.Y_INDEX]));
		sample.setRightEye(new Point(sample.getRightEye().x - center[Utils.X_INDEX],
			sample.getRightEye().y - center[Utils.Y_INDEX]));
	    }

	    for (int j = 0; j < vector.size(); j++)
	    {
		if (j % 2 == 0)
		{
		    vector.set(j, vector.get(j) - center[Utils.X_INDEX]);
		}
		else
		{
		    vector.set(j, vector.get(j) - center[Utils.Y_INDEX]);
		}
	    }
	}

	return sample;
    }


}
