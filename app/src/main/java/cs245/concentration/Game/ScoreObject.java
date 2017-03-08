package cs245.concentration.Game;

import cs245.concentration.R;

public enum ScoreObject {

    FOUR(R.string.four, R.layout.score_4),
    SIX(R.string.six, R.layout.score_6),
    EIGHT(R.string.eight, R.layout.score_8),
    TEN(R.string.ten, R.layout.score_10),
    TWELVE(R.string.twelve, R.layout.score_12),
    FOURTEEN(R.string.fourteen, R.layout.score_14),
    SIXTEEN(R.string.sixteen, R.layout.score_16),
    EIGHTEEN(R.string.eighteen, R.layout.score_18),
    TWENTY(R.string.twenty, R.layout.score_20);

    private int mTitleResId;
    private int mLayoutResId;

    ScoreObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
