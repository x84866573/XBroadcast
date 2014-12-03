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
		// ע��һ��ϵͳ BroadcastReceiver����Ϊ���ʵ�ؼ���֮��
		registerReceiver(batInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	// ��׽��ACTION_BATTERY_CHANGEDʱҪ���е�method
	public void onBatteryInfoReceiver(int intLevel, int intScale) {
		// create�����ĶԻ�����
		// final Dialog d = new Dialog(Broadcast.this);
		// d.setTitle(R.string.str_dialog_title);
		// d.setContentView(R.layout.mydialog);
		// ����һ������ģ����Window���ҽ��Ի����ڷ���ǰ��
		// Window window = d.getWindow();
		// window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
		// WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		// ��ȡ�õĵ�ؼ�����ʾ��Dialog��
		tv_battery.setText(getResources().getText(R.string.battery_changer) + String.valueOf(intLevel * 100 / intScale) + "%");
	}

	private void initView() {
		tv_battery = (TextView) findViewById(R.id.tv_battery);
	}

	private BroadcastReceiver batInfoReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			// �����׽����action��ACTION_BATTERY_CHANGED��
			// ������onBatteryInfoReceiver()
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
