package berry.justin.mycommute;

import roboguice.activity .RoboActivity;
import roboguice.inject.InjectView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends RoboActivity {

    private static final String MONEY_PLACEHOLDER = "{MONEY}";
    
    @InjectView(R.id.did_not_use_myki)
    private TextView savingsLabel;
    
    @InjectView(R.id.i_used_myki)
    private TextView paidLabel;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateLabels();
    }

    private void updateLabels() {
      Float savings = getFromSharedPreferences(R.string.pref_current_savings);
      String savingsString = getString(R.string.youve_saved);
      savingsString = savingsString.replace(MONEY_PLACEHOLDER, Float.toString(savings));
      savingsLabel.setText(savingsString);
      
      Float fares = getFromSharedPreferences(R.string.pref_current_fares);
      String paidString = getString(R.string.youve_paid);
      paidString = paidString.replace(MONEY_PLACEHOLDER, Float.toString(fares));
      paidLabel.setText(paidString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /*
     * Event receievers
     */
    
    public void onNopeClick(View button) {
      Float savings = getFromSharedPreferences(R.string.pref_current_savings);
      savings += Fare.DAILY_FARE;
      saveToSharedPreferences(R.string.pref_current_savings, savings);
      updateLabels();
    }
    
    public void onYupClick(View button) {
      Float fares = getFromSharedPreferences(R.string.pref_current_fares);
      fares += Fare.DAILY_FARE;
      saveToSharedPreferences(R.string.pref_current_fares, fares);
      updateLabels();
    }
    
    public void onResetClick(View button) {
      saveToSharedPreferences(R.string.pref_current_fares, Fare.ZERO);
      saveToSharedPreferences(R.string.pref_current_savings, Fare.ZERO);
      updateLabels();
    }
    
    /*
     * Preferences stuff
     */
    
    private Float getFromSharedPreferences(int resourceId) {
      SharedPreferences preferences = PreferenceUtils.getPreferences(this);
      return preferences.getFloat(getString(resourceId), Fare.ZERO);
    }

    private void saveToSharedPreferences(int resourceId, Float value) {
      SharedPreferences preferences = PreferenceUtils.getPreferences(this);
      Editor editor = preferences.edit();
      editor.putFloat(getString(resourceId), value);
      editor.commit();
    }
}
