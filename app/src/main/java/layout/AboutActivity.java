package layout;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delacruz.ramon.fridgandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.emailAddress) TextView emailAddress;
    @Bind(R.id.gitHub) TextView gitHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        emailAddress.setOnClickListener(this);
        gitHub.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.emailAddress:
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("*/*");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"virgilioramon.delacruz@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Let me tell you about FridgAndroid!");
                startActivity(emailIntent);
                break;
            case R.id.gitHub:
                Intent gitHubIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/vrdlc/FridgeAndroidRefactor"));
                startActivity(gitHubIntent);
                break;
        }
    }
}
