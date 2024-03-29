package android.bignerdranch.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    public static final String TAG = "QuizActivity";
    public static final String KEY_INDEX = "index";
    private Button mTrueButton, mFalseButton;
    private Button mNextButton, mResetButton;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true ),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };
    private int mCurrentIndex = 0;
    private int mCorrect = 0;
    private int mIncorrect = 0;
    private Boolean mAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);

        Log.d(TAG, "onCreate(Bundle) called");

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getmTextResId();

        mTrueButton = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                if (mAnswer == true)
                    mCorrect += 1;
                else
                    mIncorrect += 1;
                updateQuestion();

            }
        });
        mResetButton = (Button)findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = 0;
                mCorrect = 0;
                mIncorrect = 0;
                updateQuestion();
            }
        });


        // True Button Listener
        mTrueButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Code to execute on button click goes here.
            if (mCurrentIndex == 0){
                mCorrect = 0;
                mIncorrect = 0;
            }
            checkAnswer(true);
            }
        });

        //False Button Listener
        mFalseButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Code to execute on button click goes here.
            if (mCurrentIndex == 0){
                mCorrect = 0;
                mIncorrect = 0;
            }
            checkAnswer(false);
            }
        });
        updateQuestion();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "OnSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        String final_toast;

        if(userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            mAnswer = true;
        }
        else {
            messageResId = R.string.incorrect_toast;
            mAnswer = false;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        if (mCurrentIndex == mQuestionBank.length-1) {
            if (mAnswer == true)
                final_toast = getString(R.string.correct_count, (mCorrect+1)) + "\n" +
                        getString(R.string.incorrect_count, mIncorrect);
            else
                final_toast = getString(R.string.correct_count, (mCorrect)) + "\n" +
                        getString(R.string.incorrect_count, (mIncorrect+1));
            Toast.makeText(this, final_toast, Toast.LENGTH_LONG).show();

        }
    }
}
