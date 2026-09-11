package br.com.financasfacil.v2;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.WindowInsets;

public class MainActivity extends Activity {
    private WebView webView;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);
        s.setBuiltInZoomControls(false);
        s.setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                applyAndroidSafeArea(view);
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }

    private void applyAndroidSafeArea(final WebView view) {
        view.post(new Runnable() {
            @Override public void run() {
                int bottomPx = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    WindowInsets insets = view.getRootWindowInsets();
                    if (insets != null) bottomPx = insets.getSystemWindowInsetBottom();
                }
                float density = getResources().getDisplayMetrics().density;
                int bottomDp = Math.max(18, Math.round(bottomPx / Math.max(1f, density)));
                String js = "(function(){" +
                    "var l=document.getElementById('ff-safe-css');" +
                    "if(!l){l=document.createElement('link');l.id='ff-safe-css';l.rel='stylesheet';l.href='safe-area.css?v=5';document.head.appendChild(l);}" +
                    "document.documentElement.style.setProperty('--android-safe-bottom','" + bottomDp + "px');" +
                    "if(!document.getElementById('ff-compat-script')){var s=document.createElement('script');s.id='ff-compat-script';s.src='compat.js?v=5';document.head.appendChild(s);}" +
                    "})();";
                view.evaluateJavascript(js, null);
            }
        });
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
