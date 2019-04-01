package com.xygj.app.jinrirong.widget;

import android.content.Context;

/**
 * Created by Administrator on 2019/3/30 0030.
 */

public class SpreadGridItemDecoration extends GridItemDecoration {
    public SpreadGridItemDecoration(Context context, float lineWidthDp, int colorRGB) {
        super(context, lineWidthDp, colorRGB);
    }

    @Override
    public boolean[] getItemSidesIsHaveOffsets(int itemPosition) {
        //顺序:left, top, right, bottom
        boolean[] booleans = {false, false, false, false};
        switch (itemPosition % 2) {
            case 0:
                //每一行第一个只显示右边距和下边距
                booleans[2] = true;
                break;
            case 1:
                //每一行第二个只显示左边距和下边距
                booleans[0] = true;
                booleans[3] = true;
                break;
            case 3:
                //每一行第二个只显示左边距和下边距
                booleans[0] = true;
                break;
        }
        return booleans;
    }
}
