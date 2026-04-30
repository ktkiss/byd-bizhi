package com.xiaoke.bizhi;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        // 娣诲姞JS鎺ュ彛鐢ㄤ簬淇濆瓨鍥剧墖
        webView.addJavascriptInterface(new SaveImageInterface(), "AndroidSave");

        // 璁剧疆涓嬭浇鐩戝惉鍣?        webView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                String filename = URLUtil.guessFileName(url, contentDisposition, mimetype);
                if (filename == null || filename.isEmpty()) {
                    filename = "wallpaper_" + System.currentTimeMillis() + ".jpg";
                }
                downloadFile(url, filename, mimetype);
            }
        });

        webView.loadUrl("https://byd.xiaoke.name/");
    }

    // 涓嬭浇鏂囦欢鍒癉ownloads鐩綍 (鍏煎 Android 10+)
    private void downloadFile(String url, String filename, String mimeType) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10+ 浣跨敤 MediaStore API
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, filename);
                values.put(MediaStore.Downloads.MIME_TYPE, mimeType != null ? mimeType : "image/*");
                values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    getContentResolver().openOutputStream(uri).close();
                    // 浣跨敤涓嬭浇绠＄悊鍣?                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setTitle(filename);
                    DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Download started...", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Android 9 鍙婁互涓?                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setMimeType(mimeType);
                request.addRequestHeader("User-Agent", webView.getSettings().getUserAgentString());
                request.setDescription("Downloading...");
                request.setTitle(filename);
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

                DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Download started...", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Download failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // JS鎺ュ彛绫?- 澶勭悊缃戦〉涓殑闀挎寜淇濆瓨
    class SaveImageInterface {
        @JavascriptInterface
        public void saveImage(String url) {
            runOnUiThread(new Runnable() {
                public void run() {
                    String filename = "wallpaper_" + System.currentTimeMillis() + ".jpg";
                    downloadFile(url, filename, "image/jpeg");
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}