package com.example.richtext;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.AttrRes;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {
    private static final String FORMAT_HTML_COLOR = "%06X";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setHtml((WebView) findViewById(R.id.web_view),
                getString(R.string.sample),
                android.R.attr.colorBackground,
                android.R.attr.textColorTertiary,
                android.R.attr.textColorLink,
                getResources().getDimension(R.dimen.text_size),
                getResources().getDimension(R.dimen.activity_horizontal_margin));
    }

    private void setHtml(WebView webView,
                         String html,
                         @AttrRes int backgroundColor,
                         @AttrRes int textColor,
                         @AttrRes int linkColor,
                         float textSize,
                         float margin) {
        if (TextUtils.isEmpty(html)) {
            return;
        }
        webView.setBackgroundColor(ContextCompat.getColor(webView.getContext(),
                getIdRes(webView.getContext(), backgroundColor)));
        webView.getSettings().setBuiltInZoomControls(true); // optional
        webView.loadDataWithBaseURL(null,
                wrapHtml(webView.getContext(), html, textColor, linkColor, textSize, margin),
                "text/html", "UTF-8", null);
    }

    private String wrapHtml(Context context, String html,
                            @AttrRes int textColor,
                            @AttrRes int linkColor,
                            float textSize,
                            float margin) {
        return context.getString(R.string.html,
                html,
                toHtmlColor(context, textColor),
                toHtmlColor(context, linkColor),
                toHtmlPx(context, textSize),
                toHtmlPx(context, margin));
    }

    private String toHtmlColor(Context context, @AttrRes int colorAttr) {
        return String.format(FORMAT_HTML_COLOR, 0xFFFFFF &
                ContextCompat.getColor(context, getIdRes(context, colorAttr)));
    }

    private float toHtmlPx(Context context, float dimen) {
        return dimen / context.getResources().getDisplayMetrics().density;
    }

    @IdRes
    private int getIdRes(Context context, @AttrRes int attrRes) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(new int[]{attrRes});
        int resId = ta.getResourceId(0, 0);
        ta.recycle();
        return resId;
    }
}
