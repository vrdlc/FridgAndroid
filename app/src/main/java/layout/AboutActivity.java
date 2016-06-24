package layout;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delacruz.ramon.fridg.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.emailAddress) TextView emailAddress;
    @Bind(R.id.gitHub) TextView gitHub;
    @Bind(R.id.story) Button story;
    @Bind(R.id.comingSoon) Button comingSoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        emailAddress.setOnClickListener(this);
        gitHub.setOnClickListener(this);
        story.setOnClickListener(this);
        comingSoon.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.story:
                openStoryDialog();
                break;
            case R.id.comingSoon:
                openComingSoonDialog();
                break;
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

    private void openStoryDialog() {
        LayoutInflater inflater = LayoutInflater.from(AboutActivity.this);
        View subView = inflater.inflate(R.layout.fragment_story, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("The Story Behind Fridg");
        builder.setView(subView);


        builder.setPositiveButton("Thanks!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AboutActivity.this, "Hope that answers some questions!", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    private void openComingSoonDialog() {
        LayoutInflater inflater = LayoutInflater.from(AboutActivity.this);
        View subView = inflater.inflate(R.layout.fragment_coming_soon, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Planned Features:");
        builder.setView(subView);

        builder.setPositiveButton("Thanks!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AboutActivity.this, "Let me know if I forgot something!", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

}
