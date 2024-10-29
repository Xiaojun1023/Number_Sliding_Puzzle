package com.example.numberslidingpuzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    private TextView completionCountTextView, bestTimeTextView, elapsedTimeTextView;
    private long startTime;
    private SharedPreferences sharedPreferences;
    private String playerName;
    private int timesCompleted;
    private long bestTime;
    private GridLayout puzzleGrid;
    private int emptyTilePosition;
    private ArrayList<TextView> tiles = new ArrayList<>();
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private long pausedTimeOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        completionCountTextView = findViewById(R.id.completionCountTextView);
        bestTimeTextView = findViewById(R.id.bestTimeTextView);
        elapsedTimeTextView = findViewById(R.id.elapsedTimeTextView);
        puzzleGrid = findViewById(R.id.puzzleGrid);

        playerName = getIntent().getStringExtra("playerName");
        timesCompleted = getIntent().getIntExtra("timesCompleted", 0);
        bestTime = getIntent().getLongExtra("bestTime", Long.MAX_VALUE);

        sharedPreferences = getSharedPreferences("PuzzleGameStats", Context.MODE_PRIVATE);

        startTime = System.currentTimeMillis();

        updateStats();
        setupPuzzle();
        startTimer();

        Button restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(view -> restartGame());
    }

    private void setupPuzzle() {
        ArrayList<Integer> puzzleNumbers = shufflePuzzle();

        puzzleGrid.removeAllViews();
        tiles.clear();

        for (int i = 0; i < puzzleNumbers.size(); i++) {
            final int tileIndex = i;

            TextView tile = new TextView(this);
            tile.setLayoutParams(new GridLayout.LayoutParams());
            tile.setWidth(325);
            tile.setHeight(325);
            tile.setGravity(Gravity.CENTER);
            tile.setTextSize(30);
            tile.setBackground(ContextCompat.getDrawable(this, R.drawable.block_background));

            if (puzzleNumbers.get(i) != 0) {
                tile.setText(String.valueOf(puzzleNumbers.get(i)));
                tile.setTag(puzzleNumbers.get(i));
            } else {
                tile.setText("");
                tile.setTag(0);
                emptyTilePosition = i;
            }

            tile.setOnClickListener(v -> handleTileClick(tileIndex));
            tiles.add(tile);
            puzzleGrid.addView(tile);
        }
    }

    private ArrayList<Integer> shufflePuzzle() {
        ArrayList<Integer> puzzleNumbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            puzzleNumbers.add(i);
        }
        puzzleNumbers.add(0);

        do {
            Collections.shuffle(puzzleNumbers);
        } while (!isSolvable(puzzleNumbers));

        return puzzleNumbers;
    }

    private boolean isSolvable(ArrayList<Integer> puzzleNumbers) {
        int inversions = 0;
        for (int i = 0; i < puzzleNumbers.size(); i++) {
            for (int j = i + 1; j < puzzleNumbers.size(); j++) {
                if (puzzleNumbers.get(i) > puzzleNumbers.get(j) && puzzleNumbers.get(i) != 0 && puzzleNumbers.get(j) != 0) {
                    inversions++;
                }
            }
        }
        return inversions % 2 == 0;
    }

    private void handleTileClick(int tileIndex) {
        if (canMoveTile(tileIndex)) {
            swapTiles(tileIndex, emptyTilePosition);
            emptyTilePosition = tileIndex;

            if (isPuzzleCompleted()) {
                stopTimer();
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                updateGameStats(elapsedTime);
                showCompletionAnimation();
            }
        }
    }

    private boolean canMoveTile(int tileIndex) {
        int row = tileIndex / 3;
        int col = tileIndex % 3;
        int emptyRow = emptyTilePosition / 3;
        int emptyCol = emptyTilePosition % 3;

        return (Math.abs(row - emptyRow) + Math.abs(col - emptyCol)) == 1;
    }

    private void swapTiles(int tileIndex, int emptyTileIndex) {
        TextView tile = tiles.get(tileIndex);
        TextView emptyTile = tiles.get(emptyTileIndex);

        emptyTile.setText(tile.getText());
        emptyTile.setTag(tile.getTag());

        tile.setText("");
        tile.setTag(0);
    }

    private boolean isPuzzleCompleted() {
        for (int i = 0; i < tiles.size() - 1; i++) {
            if ((int) tiles.get(i).getTag() != i + 1) {
                return false;
            }
        }
        return (int) tiles.get(8).getTag() == 0;
    }

    private void startTimer() {
        if (timerRunnable == null) {
            timerRunnable = new Runnable() {
                @Override
                public void run() {
                    long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
                    elapsedTimeTextView.setText("Elapsed Time: " + elapsedTime + "s");
                    timerHandler.postDelayed(this, 1000);
                }
            };
            timerHandler.post(timerRunnable);
        }
    }

    private void stopTimer() {
        if (timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
            timerRunnable = null;
        }
    }

    private void updateGameStats(long elapsedTime) {
        timesCompleted++;
        AnimatorSet flipAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip);

        if (elapsedTime < bestTime) {
            bestTime = elapsedTime;
            flipAnimator.setTarget(bestTimeTextView);
            flipAnimator.start();
        }

        flipAnimator.setTarget((completionCountTextView));
        flipAnimator.start();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(playerName + "_timesCompleted", timesCompleted);
        editor.putLong(playerName + "_bestTime", bestTime);
        editor.apply();

        completionCountTextView.setText("Completed: " + timesCompleted);
        bestTimeTextView.setText("Best Time: " + bestTime + "s");
    }

    private void showCompletionAnimation() {
        Toast.makeText(this, "Puzzle Completed!", Toast.LENGTH_SHORT).show();
    }

    private void restartGame() {
        pausedTimeOffset = 0;
        startTime = System.currentTimeMillis();
        setupPuzzle();
        startTimer();
    }

    private void updateStats() {
        completionCountTextView.setText("Completed: " + timesCompleted);
        bestTimeTextView.setText("Best Time: " + (bestTime == Long.MAX_VALUE ? "N/A" : bestTime + "s"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausedTimeOffset += System.currentTimeMillis() - startTime;  // Accumulate paused time
        stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTime = System.currentTimeMillis() - pausedTimeOffset;  // Adjust start time by pause duration
        startTimer();
    }
}