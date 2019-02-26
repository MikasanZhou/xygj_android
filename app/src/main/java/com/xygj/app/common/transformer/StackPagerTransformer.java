package com.xygj.app.common.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager 切换动画
 * Created by xuyougen on 2018/3/2.
 */

public class StackPagerTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setTranslationX(position < 0 ? 0f : -page.getWidth() * position);

        if (position <= 0 && position >= -1)// a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
        {
            page.setAlpha(1 + (.5f * position));
        } else if (position <= 1) { // (0,1]
            page.setAlpha(1 + (.5f * -position));
            float scale = (1f + (.2f * position));
            page.setScaleX(scale);
            page.setScaleY(scale);

        } else {
            page.setAlpha(0);
        }

    }
}
