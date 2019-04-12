package com.example.jogodavelha;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.content.FileProvider;

import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {
    private TextView[][] gameboard = new TextView[3][3];
    private File SelfiePlayer1 = null;
    private File SelfiePlayer2 = null;
    private ImageView imagem;
    private final int GALERIA_IMAGENS = 1;
    private final int CAMERA = 3;
    private final int PERMISSAO_REQUEST = 2;

    //check movment
    private enum TURN {
        CROSS, CIRCLE
    }

    ;
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

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String index = "index_" + i + j;
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
    public void clickGameBoard(View view) {
        TextView txtIndex = ((TextView) view);
        String txt = txtIndex.getText().toString();
        if (!txt.equals("")) {
            return;
        }

        if (turn == TURN.CROSS) {
            turn = TURN.CIRCLE;
            txtIndex.setText("X");
            txtTurn.setText("VEZ DO JOGADOR P2");
        } else if (turn == TURN.CIRCLE) {
            turn = TURN.CROSS;
            txtIndex.setText("O");
            txtTurn.setText("VEZ DO JOGADOR P1");
        }

        txtIndex.setEnabled(false);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("winner");
        dialog.setCancelable(true);
        if (checkResults()) {
            if (winX) {
                dialog.setMessage("P1 GANHOU");
                dialog.create().show();
            } else if (winO) {
                dialog.setMessage("P2 GANHOU");
                dialog.create().show();
            }
        }
    }

    private Boolean checkResults() {

        for (int i = 0; i < 3; i++) {
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

    private File criaArquivo() throws IOException {
        String timeStamp = new
                SimpleDateFormat("yyyyMMdd_Hhmmss").format(
                new Date());
        File pasta = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imagem = new File(pasta.getPath() + File.separator
                + "JPG_" + timeStamp + ".jpg");
        return imagem;
    }

    public void tirarFoto(View view) {
        File arquivoFoto = null;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                arquivoFoto = criaArquivo();
            } catch (IOException ex) {
                mostraAlerta(getString(R.string.erro), getString(
                        R.string.erro_salvando_foto));
            }
            if (arquivoFoto != null) {
                Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                        getBaseContext().getApplicationContext().getPackageName() +
                                ".provider", arquivoFoto);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA);
            }
        }


        switch (view.getId()) {
            case (R.id.player1):
                SelfiePlayer1 = arquivoFoto;
                final ImageButton p1 = (ImageButton) findViewById(R.id.player1);
                Bitmap s1 = BitmapFactory.decodeFile(SelfiePlayer1.getAbsolutePath());
                p1.setImageBitmap(s1);
                break;

            case (R.id.player2):
                SelfiePlayer2 = arquivoFoto;
                final ImageButton p2 = (ImageButton) findViewById(R.id.player2);
                Bitmap s2 = BitmapFactory.decodeFile(SelfiePlayer2.getAbsolutePath());
                p2.setImageBitmap(s2);
                break;
        }

    }

    private void mostraAlerta(String titulo, String mensagem) {
        android.app.AlertDialog alertDialog = new
                android.app.AlertDialog.Builder(GameActivity.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(mensagem);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}
