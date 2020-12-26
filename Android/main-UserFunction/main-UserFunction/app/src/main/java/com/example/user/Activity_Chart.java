package com.example.user;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.EventLogTags;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Activity_Chart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__chart);

        PieChart pieChart = findViewById(R.id.piechart);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setUsePercentValues(false);

        int totalVisit = 4;

        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new Entry(1f, 1));
        NoOfEmp.add(new Entry(2f, 2));
        NoOfEmp.add(new Entry(1f, 2));
        NoOfEmp.add(new Entry(1f, 3));

        ArrayList cafe = new ArrayList();
        cafe.add("달린다 커피");
        cafe.add("가즈아 커피");
        cafe.add("이다야 커피");
        cafe.add("장커피");


        // 밑에 어떤 색이 어떤 카페이인지 표현해주는 부분
        PieDataSet dataSet = new PieDataSet(NoOfEmp, "Number of Visited Cafe");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        pieChart.setDescription(null);

        PieData data = new PieData(cafe, dataSet); // MPAndroidChart v3.X 오류 발생
        pieChart.setData(data);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        Legend l = pieChart.getLegend();
        l.setEnabled(false);


        data.setValueTextSize(10f); // 차트안 크기 + 색깔
        data.setValueTextColor(Color.WHITE);

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션
        //pieChart.animateXY(5000, 5000);

    }
}
