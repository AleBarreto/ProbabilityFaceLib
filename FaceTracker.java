package barreto.alessandro.mobilevision;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

/**
 * Created by Alessandro on 26/02/2017.
 */

public class FaceTracker extends Tracker<Face> {

    private OnFaceProbability mOnFaceProbability;

    public FaceTracker(OnFaceProbability mOnFaceProbability) {
        this.mOnFaceProbability = mOnFaceProbability;
    }

    public interface OnFaceProbability {
        void getLeftEyeOpenProbability (float probability);
        void getRightEyeOpenProbability (float probability);
        void getSmilingProbability (float probability);
    }

    @Override
    public void onUpdate(Detector.Detections<Face> detections, Face face) {
        if (mOnFaceProbability != null){
            mOnFaceProbability.getLeftEyeOpenProbability( face.getIsLeftEyeOpenProbability() );
            mOnFaceProbability.getRightEyeOpenProbability( face.getIsRightEyeOpenProbability() );
            mOnFaceProbability.getSmilingProbability( face.getIsSmilingProbability() );
        }else{
            throw new NullPointerException("Interface FaceProbability is Null");
        }
    }
}
