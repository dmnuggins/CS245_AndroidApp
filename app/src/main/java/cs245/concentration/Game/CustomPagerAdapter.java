package cs245.concentration.Game;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cs245.concentration.R;

public class CustomPagerAdapter extends PagerAdapter {

        private Context mContext;
        private MySQLiteHelper db;

        public CustomPagerAdapter(Context context, MySQLiteHelper db) {
            mContext = context;
            this.db = db;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {

            ScoreObject scoreObject = ScoreObject.values()[position];
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewGroup layout = (ViewGroup) inflater.inflate(scoreObject.getLayoutResId(), collection, false);

            String[] array = {"4", "6", "8", "10", "12", "14", "16", "18", "20"};
            TextView score1 = (TextView) layout.findViewById(R.id.score1);
            TextView score2 = (TextView) layout.findViewById(R.id.score2);
            TextView score3 = (TextView) layout.findViewById(R.id.score3);
            List<Score> scoreList = db.getDifficultyScores(array[position]);
            if (scoreList.size() > 0) {
                Score score = scoreList.get(0);
                score1.setText("Score: " + score.getScore() + " \t\t\t\tby " + score.getUsername());
            }
            if (scoreList.size() > 1) {
                Score score = scoreList.get(1);
                score2.setText("Score: " + score.getScore() + " \t\t\t\tby " + score.getUsername());
            }
            if (scoreList.size() > 2) {
                Score score = scoreList.get(2);
                score3.setText("Score: " + score.getScore() + " \t\t\t\tby " + score.getUsername());
            }
            collection.addView(layout);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getCount() {
            return ScoreObject.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            ScoreObject customPagerEnum = ScoreObject.values()[position];
            return mContext.getString(customPagerEnum.getTitleResId());
        }

    }
