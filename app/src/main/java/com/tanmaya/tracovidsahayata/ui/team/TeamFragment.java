package com.tanmaya.tracovidsahayata.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.tanmaya.tracovidsahayata.R;

public class TeamFragment extends Fragment {
    private TeamViewModel teamViewModel;
    WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    { teamViewModel= ViewModelProviders.of(this).get(TeamViewModel.class);
      View root=inflater.inflate(R.layout.fragment_team,container,false);
        webView = (WebView) root.findViewById(R.id.WebView1);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        // Below required for geolocation
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        // Load google.com
        webView.loadUrl("file:///android_asset/OurTeam.html");
      return root;

    }
}
