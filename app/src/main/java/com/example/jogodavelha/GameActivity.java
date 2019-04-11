package com.example.jogodavelha;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {
    private TextView [][] gameboard = new TextView[3][3];

    //check movment
    private enum TURN {CROSS, CIRCLE};
    private TURN turn;
    private Boolean winX = false;
    private Boolean winO = false;

    @BindView(R.id.txt_turn)
    TextView txtTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        turn = TURN.CROSS;

        for (int i=0; i <3; i++){
            for (int j=0; j<3;j++){
                String index = "index_"+i+j;
                int ID = getResources().getIdentifier(index, "id", getPackageName());
                gameboard[i][j] = findViewById(ID);
            }
        }

    }

    @OnClick(
            {
                    R.id.index_00,
                    R.id.index_01,
                    R.id.index_02,
                    R.id.index_10,
                    R.id.index_11,
                    R.id.index_12,
                    R.id.index_20,
                    R.id.index_21,
                    R.id.index_22,
            }
    )
    public void clickGameBoard(View view){
        TextView txtIndex = ((TextView) view);
        String txt = txtIndex.getText().toString();
        if (!txt.equals("")){
            return;
        }

        if(turn == TURN.CROSS){
            turn = TURN.CIRCLE;
            txtIndex.setText("X");
            txtTurn.setText("VEZ DO JOGADOR P2");
        } else if(turn == TURN.CIRCLE) {
            turn = TURN.CROSS;
            txtIndex.setText("O");
            txtTurn.setText("VEZ DO JOGADOR P1");
        }

        txtIndex.setEnabled(false);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("winner");
        dialog.setCancelable(true);
        if (checkResults()){
            if (winX){
                dialog.setMessage("P1 GANHOU");
                dialog.create().show();
            } else if (winO){
                dialog.setMessage("P2 GANHOU");
                dialog.create().show();
            }
        }
    }

    private Boolean checkResults(){

        for (int i=0;i<3;i++){
            // horizontal
            if (gameboard[i][0].getText().toString() == "X" &&
                gameboard[i][1].getText().toString() == "X" &&
                gameboard[i][2].getText().toString() == "X") {
                winX = true;
                return winX;
            }
            if (gameboard[i][0].getText().toString() == "O" &&
                    gameboard[i][1].getText().toString() == "O" &&
                    gameboard[i][2].getText().toString() == "O") {
                winO = true;
                return winO;
            }
            //VERTICAL
            if (gameboard[0][i].getText().toString() == "X" &&
                    gameboard[1][i].getText().toString() == "X" &&
                    gameboard[2][i].getText().toString() == "X") {
                winX = true;
                return winX;
            }
            if (gameboard[0][i].getText().toString() == "O" &&
                    gameboard[1][i].getText().toString() == "O" &&
                    gameboard[2][i].getText().toString() == "O") {
                winO = true;
                return winO;
            }

            //DIAGONAL
            if (gameboard[0][0].getText().toString() == "X" &&
                gameboard[1][1].getText().toString() == "X" &&
                gameboard[2][2].getText().toString() == "X") {
                winX = true;
                return winX;
            }
            if (gameboard[0][0].getText().toString() == "O" &&
                gameboard[1][1].getText().toString() == "O" &&
                gameboard[2][2].getText().toString() == "O") {
                winO = true;
                return winO;
            }
            if (gameboard[0][2].getText().toString() == "X" &&
                gameboard[1][1].getText().toString() == "X" &&
                gameboard[2][0].getText().toString() == "X") {
                winX = true;
                return winX;
            }
            if (gameboard[0][2].getText().toString() == "O" &&
                gameboard[1][1].getText().toString() == "O" &&
                gameboard[2][0].getText().toString() == "O") {
                winO = true;
                return winO;
            }
        }

        return false;
    }

}
