package com.example.user;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
// 맵 info 때문에 만든 Adapter인데
// 맵에서 이용하는 1번 방법 안쓴다면 필요없는 Adapter
public class InfoAdapter implements GoogleMap.InfoWindowAdapter {

    View window;
    CafeData cafeData;

    public InfoAdapter(View window, CafeData cafeData) {
        this.window = window;
        this.cafeData = cafeData; //정보를 담은 객체
    }

    @Override
    public View getInfoWindow(Marker marker) {
        TextView CafeName = window.findViewById(R.id.CafeName);
        TextView CafeStart = window.findViewById(R.id.CafeStart);
        TextView CafeEnd = window.findViewById(R.id.CafeEnd);

        CafeName.setText(cafeData.getCafeName());
        CafeStart.setText(cafeData.getCafeStart());
        CafeEnd.setText(cafeData.getCafeEnd());
        return window;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}

