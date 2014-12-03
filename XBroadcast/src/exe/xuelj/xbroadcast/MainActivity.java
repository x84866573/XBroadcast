package exe.xuelj.xbroadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv_battery;
	private int intLevel;
	private int intScale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		// 注册一个系统 BroadcastReceiver，作为访问电池计量之用
		registerReceiver(batInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	// 捕捉到ACTION_BATTERY_CHANGED时要运行的method
	public void onBatteryInfoReceiver(int intLevel, int intScale) {
		// create跳出的对话窗口
		// final Dialog d = new Dialog(Broadcast.this);
		// d.setTitle(R.string.str_dialog_title);
		// d.setContentView(R.layout.mydialog);
		// 创建一个背景模糊的Window，且将对话窗口放在前景
		// Window window = d.getWindow();
		// window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		// WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		// 将取得的电池计量显示于Dialog中
		tv_battery.setText(getResources().getText(R.string.battery_changer) + String.valueOf(intLevel * 100 / intScale) + "%");
	}

	private void initView() {
		tv_battery = (TextView) findViewById(R.id.tv_battery);
	}

	private BroadcastReceiver batInfoReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// 如果捕捉到的action是ACTION_BATTERY_CHANGED，
			// 就运行onBatteryInfoReceiver()
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
				intLevel = intent.getIntExtra("level", 0);
				intScale = intent.getIntExtra("scale", 100);
				onBatteryInfoReceiver(intLevel, intScale);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(batInfoReceiver);
	}
}
