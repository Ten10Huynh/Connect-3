package spring2024.cs477.project1_thuynh31;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button computerChoice;
    Button partnerChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        computerChoice = findViewById(R.id.computerChoice);
        partnerChoice = findViewById(R.id.partnerChoice);
    }

    public void versusComputer(View v) {
        Intent intent = new Intent(this, PlayingAgainstComputer.class);
        startActivity(intent);
    }


    public void versusPartner(View v) {
        Intent intent = new Intent(this, PlayingAgainstPartner.class);
        startActivity(intent);
    }


}