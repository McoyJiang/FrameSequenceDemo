/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.dannyjiang.framesequencedemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.danny.framesSquencce.FrameSequence;
import com.danny.framesSquencce.FrameSequenceDrawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class FrameSequenceTest extends Activity {
    private static final String TAG = "FrameSequenceTest";

    FrameSequenceDrawable mDrawable1;
    FrameSequenceDrawable mDrawable2;
    FrameSequenceDrawable mDrawable3;
    private ImageView imageView;

    // This provider is entirely unnecessary, just here to validate the acquire/release process
    public static class CheckingProvider implements FrameSequenceDrawable.BitmapProvider {
        HashSet<Bitmap> mBitmaps = new HashSet<Bitmap>();
        @Override
        public Bitmap acquireBitmap(int minWidth, int minHeight) {
            Bitmap bitmap =
                    Bitmap.createBitmap(minWidth + 1, minHeight + 4, Bitmap.Config.ARGB_8888);
            mBitmaps.add(bitmap);
            return bitmap;
        }

        @Override
        public void releaseBitmap(Bitmap bitmap) {
            if (!mBitmaps.contains(bitmap)) throw new IllegalStateException();
            mBitmaps.remove(bitmap);
            bitmap.recycle();
        }

        public boolean isEmpty() {
            return mBitmaps.isEmpty();
        }
    }

    final CheckingProvider mProvider = new CheckingProvider();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_test_activity);

        imageView = (ImageView) findViewById(R.id.imageview);

        initDrawable();

//        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDrawable.start();
//            }
//        });
//        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDrawable.stop();
//            }
//        });
        findViewById(R.id.vis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mDrawable.setVisible(true, true);
                change(2);
            }
        });
        findViewById(R.id.invis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mDrawable.setVisible(false, true);
                change(3);
            }
        });
    }

    private void initDrawable() {
        try {
            InputStream is1 = getResources().openRawResource(R.raw.ben_neutral_talk_right);

            FrameSequence fs1 = FrameSequence.decodeStream(is1);

            mDrawable1 = new FrameSequenceDrawable(fs1, mProvider);
            mDrawable1.setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
                @Override
                public void onFinished(FrameSequenceDrawable drawable) {
                    Toast.makeText(getApplicationContext(),
                            "The animation has finished", Toast.LENGTH_SHORT).show();
                }
            });

            InputStream is2 = getResources().openRawResource(R.raw.ben_sad_blink_right);
            FrameSequence fs2 = FrameSequence.decodeStream(is2);

            mDrawable2 = new FrameSequenceDrawable(fs2, mProvider);
            mDrawable2.setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
                @Override
                public void onFinished(FrameSequenceDrawable drawable) {
                    Toast.makeText(getApplicationContext(),
                            "The animation has finished", Toast.LENGTH_SHORT).show();
                }
            });

            InputStream is3 = getResources().openRawResource(R.raw.ben_happy_talk_right);
            FrameSequence fs3 = FrameSequence.decodeStream(is3);

            mDrawable3 = new FrameSequenceDrawable(fs3, mProvider);
            mDrawable3.setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
                @Override
                public void onFinished(FrameSequenceDrawable drawable) {
                    Toast.makeText(getApplicationContext(),
                            "The animation has finished", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "e is " + e.getMessage());
        } finally {
        }
    }

    public void change(int sequence) {
        switch (sequence) {
            case 1:
                imageView.setImageDrawable(mDrawable1);

                mDrawable1.start();
                break;
            case 2:
                imageView.setImageDrawable(mDrawable2);
                mDrawable2.start();
                break;
            case 3:
                imageView.setImageDrawable(mDrawable3);
                mDrawable3.start();
                break;
        }


        /**
        if (mDrawable != null && mDrawable.isRunning()) {
            mDrawable.stop();
            mDrawable = null;
        }

        InputStream is = getResources().openRawResource(resId);

        FrameSequence frameSequence = new FrameSequence();

        try {
            FrameSequence fs = frameSequence.decodeStream(is);
            mDrawable = new FrameSequenceDrawable(fs, mProvider);
            mDrawable.setOnFinishedListener(new FrameSequenceDrawable.OnFinishedListener() {
                @Override
                public void onFinished(FrameSequenceDrawable drawable) {
                    Toast.makeText(getApplicationContext(),
                            "The animation has finished", Toast.LENGTH_SHORT).show();
                }
            });
            imageView.setImageDrawable(mDrawable);

            mDrawable.start();
        } finally {
            try {
                is.close();
                is = null;
                frameSequence.destroy();
            } catch (IOException e) {

            }
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
//        super.onPause();
//        ImageView imageView = (ImageView) findViewById(R.id.imageview);
//
//        mDrawable.destroy();
//        if (!mProvider.isEmpty()) throw new IllegalStateException("All bitmaps not recycled");
//
//        mDrawable = null;
//        imageView.setImageDrawable(null);

    }

    public void loadAnimation(View view) {
        change(1);
    }
}
