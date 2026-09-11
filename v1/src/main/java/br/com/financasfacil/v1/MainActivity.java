package br.com.financasfacil.v1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView webView;
    private SharedPreferences prefs;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("financas_facil_v1", MODE_PRIVATE);
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

        webView.addJavascriptInterface(new StoreBridge(), "NativeStore");
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
                    "if(!l){l=document.createElement('link');l.id='ff-safe-css';l.rel='stylesheet';l.href='safe-area.css?v=7';document.head.appendChild(l);}" +
                    "document.documentElement.style.setProperty('--android-safe-bottom','" + bottomDp + "px');" +
                    "if(!document.getElementById('ff-compat-script')){var s=document.createElement('script');s.id='ff-compat-script';s.src='compat.js?v=7';document.head.appendChild(s);}" +
                    "})();";
                view.evaluateJavascript(js, null);
            }
        });
    }

    @Override protected void onPause() {
        if (webView != null) {
            webView.evaluateJavascript("if(window.FFNativeFlush){window.FFNativeFlush();}", null);
        }
        super.onPause();
    }

    public class StoreBridge {
        @JavascriptInterface public String load() {
            return prefs.getString("state", "");
        }

        @JavascriptInterface public boolean save(String json) {
            if (json == null) json = "";
            return prefs.edit().putString("state", json).commit();
        }

        @JavascriptInterface public boolean clear() {
            return prefs.edit().remove("state").commit();
        }
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
