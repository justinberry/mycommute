package berry.justin.mycommute;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

  public static SharedPreferences getPreferences(Activity context) {
    return context.getPreferences(Context.MODE_PRIVATE);
  }
}
 