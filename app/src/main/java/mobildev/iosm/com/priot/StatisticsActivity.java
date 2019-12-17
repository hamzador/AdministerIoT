package mobildev.iosm.com.priot;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

import mobildev.iosm.com.priot.Model.CarteStatistics;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
//import com.xxmassdevelope.mpchartexample.notimportant.DemoBase;


public class StatisticsActivity extends AppCompatActivity {
    BarChart barChart;
    PieChart pieChart;
    ArrayList<CarteStatistics> myProducts = new ArrayList<>();
    ArrayList<CarteStatistics> myProductsPie = new ArrayList<>();
    DatabaseReference Ref;
    DatabaseReference RefPie;
    private String carteID = "";

    BarDataSet set;
    PieDataSet setPie;
    ArrayList<BarEntry> yVals = new ArrayList<>();
    ArrayList<PieEntry>  yValspie = new ArrayList<>(); ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        pieChart = findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

       // pieChart.setDragDecelerationFrictionCoef(0.15f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);






        barChart = findViewById(R.id.bargraph);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
       // barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        final ArrayList<BarEntry> barEntries = new ArrayList<>();
        /************data ***********/

        carteID = getIntent().getStringExtra("cid");
        getPresenceDetailsPiechart(carteID);
        getPresenceDetailsBarChart(carteID);


    }

    private void getPresenceDetailsPiechart(final String carteID){
        RefPie = FirebaseDatabase.getInstance().getReference().child("DureMentuel");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("id_Carte").exists()){
                        if (postSnapshot.child("id_Carte").getValue().equals(carteID)) {
                            CarteStatistics products = new CarteStatistics(postSnapshot.child("AnneMois").getValue().toString(), postSnapshot.child("dure").getValue().toString());
                            myProductsPie.add(products);
                            Log.d("firebasedatPie", products.getAnneeMois());

                        }
                    }

                    //TODO call pie chart function


                }
                chartFuncPie();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };
        RefPie.addValueEventListener(postListener);
    }



    private void chartFuncPie() {

        Log.d("firebasedataFunction", "yes");

        for (CarteStatistics products : myProductsPie) {
            String month;
            String AnneeMois = products.getAnneeMois();
            String[] separated = AnneeMois.split("-");

            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c);
            String[] curerntDate = formattedDate.split("-");

            month = separated[1];
            float dureeH, dureeM, dureeS, duree ;

            String[] timePresence = products.getDure().split(":");
            dureeH = Float.parseFloat(timePresence[0]);
            dureeM = Float.parseFloat(timePresence[1]);
            dureeS = Float.parseFloat(timePresence[2]);
            duree = dureeH * 60 + dureeM + (float) (dureeS / 60.0);
            float pie1=0,pie2=0,pie3=0,pie4=0,pie5=0,pie6=0,pie7=0,pie8=0,pie9=0,pie10=0,pie11=0,pie12=0;
            if (curerntDate[2].equals(separated[0])) {
                switch (month) {
                    case "01":
                        yValspie.add(new PieEntry(duree, "Jan"));
                        Log.d("monthJan", yValspie.toString());
                        break;
                    case "02":
                        yValspie.add(new PieEntry(duree, "Feb"));
                        Log.d("monthFeb", yValspie.toString());
                        break;
                    case "03":
                        yValspie.add(new PieEntry(duree, "Mar"));
                        Log.d("monthMar", "03");
                        break;
                    case "04":
                        yValspie.add(new PieEntry(duree, "Apr"));
                        Log.d("monthApr", yValspie.toString());
                        break;

                    case "09":
                        yValspie.add(new PieEntry(duree, "Sep"));
                        Log.d("monthSep", yValspie.toString());
                        break;
                    case "10":
                        yValspie.add(new PieEntry(duree, "Oct"));
                        Log.d("monthOct", yValspie.toString());
                        break;
                    case "11":
                        yValspie.add(new PieEntry(duree, "Nev"));
                        Log.d("monthNev", yValspie.toString());
                        break;
                    case "12":
                        yValspie.add(new PieEntry(duree, "Dec"));
                        Log.d("monthDec", yValspie.toString());
                        break;
                }

//                yValspie.add(new PieEntry(pie1,"jan"));
//                yValspie.add(new PieEntry(pie2,"Feb"));
//                yValspie.add(new PieEntry(pie3,"Mar"));
//                yValspie.add(new PieEntry(pie4,"Apr"));
//                yValspie.add(new PieEntry(pie5,"Mai"));
//                yValspie.add(new PieEntry(pie6,"Jun"));
//                yValspie.add(new PieEntry(pie7,"Jul"));
//                yValspie.add(new PieEntry(pie8,"Aug"));
//                yValspie.add(new PieEntry(pie9,"Sep"));
//                yValspie.add(new PieEntry(pie10,"Oct"));
//                yValspie.add(new PieEntry(pie11,"Nov"));
//                yValspie.add(new PieEntry(pie12,"Dec"));

            }

        }

        setPie = new PieDataSet(yValspie, "Months");

        setPie.setSliceSpace(1f);
        setPie.setSelectionShift(1f);
       setPie.setColors(ColorTemplate.COLORFUL_COLORS);



        Log.d("piedate",myProductsPie.getClass().toString());

        PieData pieData= new PieData(setPie);

        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.YELLOW);

        //pieData.setValueFormatter();

        pieChart.setData(pieData);
        pieChart.invalidate();
    }



    /********************************************************************************************************************************/


    private void getPresenceDetailsBarChart(final String carteID) {

        Ref = FirebaseDatabase.getInstance().getReference().child("DureMentuel");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("id_Carte").exists()){
                        if (postSnapshot.child("id_Carte").getValue().equals(carteID)) {
                            CarteStatistics products = new CarteStatistics(postSnapshot.child("AnneMois").getValue().toString(), postSnapshot.child("dure").getValue().toString());
                            myProducts.add(products);
                            Log.d("firebasedata1", products.getAnneeMois());

                        }
                    }

                    //TODO call chart function

                        chartFunc();
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException());
            }
        };
        Ref.addValueEventListener(postListener);
    }
   /****/
   private void chartFunc() {
       Log.d("firebasedataFunction", "yes");

       for (CarteStatistics products : myProducts) {
           String month;
           String AnneeMois = products.getAnneeMois();
           String[] separated = AnneeMois.split("-");

           Date c = Calendar.getInstance().getTime();
           //Toast.makeText(StatisticsActivity.this,"date = "+c ,Toast.LENGTH_SHORT).show();
           //System.out.print("date "+c);

           SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
           String formattedDate = df.format(c);
           String[] curerntDate = formattedDate.split("-");

           month = separated[1];
           float dureeH, dureeM, dureeS, duree=0;
           String[] timePresence = products.getDure().split(":");
           dureeH = Float.parseFloat(timePresence[0]);
           dureeM = Float.parseFloat(timePresence[1]);
           dureeS = Float.parseFloat(timePresence[2]);
           duree = dureeH * 60 + dureeM + (float) (dureeS / 60.0);
           if (curerntDate[2].equals(separated[0])) {
               switch (month) {
                   case "01":
                       yVals.add(new BarEntry(0f, duree));
                       break;
                   case "02":
                       yVals.add(new BarEntry(1f, duree));
                       break;
                   case "03":
                       yVals.add(new BarEntry(2f, duree));
                       break;
                   case "04":
                       yVals.add(new BarEntry(3f, duree));
                       break;
                   case "05":
                       yVals.add(new BarEntry(4f, duree));
                       break;
                   case "06":
                       yVals.add(new BarEntry(5f, duree));
                       break;
                   case "07":
                       yVals.add(new BarEntry(6f, duree));
                       break;
                   case "08":
                       yVals.add(new BarEntry(7f, duree));
                       break;
                   case "09":
                       yVals.add(new BarEntry(8f, duree));
                       break;
                   case "10":
                       yVals.add(new BarEntry(9f, duree));
                       break;
                   case "11":
                       yVals.add(new BarEntry(10f, duree));
                       break;
                   case "12":
                       yVals.add(new BarEntry(11f, duree));
                       break;
                   default:
                       Toast.makeText(StatisticsActivity.this,"no time login data " ,Toast.LENGTH_SHORT).show();
               }

           }

       }

       String [] months ={"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
       set = new BarDataSet(yVals, "Months");
       set.setBarBorderColor(5);
       //set.setColors(ColorTemplate.COLORFUL_COLORS);
       set.setDrawValues(true);
       set.setLabel("Statistics for presence of employers");
       BarData data = new BarData(set);


       barChart.setData(data);
       barChart.setHighlightFullBarEnabled(false);

       // barChart.notifyDataSetChanged();



       XAxis xAxis = barChart.getXAxis();
       xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
       xAxis.setPosition(XAxisPosition.BOTTOM);
       xAxis.setCenterAxisLabels(true);
       xAxis.setGranularity(1f);
       xAxis.setCenterAxisLabels(true);
       xAxis.setAxisMinimum(0);
       //xAxis.setXOffset(0f);
       //xAxis.setSpaceMax(4f);




   }

    /*******************************************************************************************************************************************/



    public class MyAxisValueFormatter extends ValueFormatter implements IAxisValueFormatter{
        private  String [] mvalues;
       public  MyAxisValueFormatter(String[] values){
           this.mvalues = values;
       }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mvalues[(int)value];
        }
    }
}