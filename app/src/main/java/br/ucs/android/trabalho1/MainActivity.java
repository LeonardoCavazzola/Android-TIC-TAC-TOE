package br.ucs.android.trabalho1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //jogo
    private int next; //0 = partida não iniciada | 1 = p1 | 200 = p2
    private final Map<String, ImageButton> gridButtons = new HashMap<>();
    private final Map<String, Integer> gridValues = new HashMap<>();
    private TextView guide;
    private int rounds;

    //p1
    private ImageButton p1Button;
    private Boolean p1Selected = false;
    private Bitmap p1Image;

    //p2
    private ImageButton p2Button;
    private Boolean p2Selected = false;
    private Bitmap p2Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //android
        setContentView(R.layout.activity_main); //android

        initializeComponents();
        clearAll();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            setP1((Bitmap) extras.get("data"));
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            setP2((Bitmap) extras.get("data"));
        }
    }

    public void initializeComponents() {
        //botões dos jogadores
        this.p1Button = findViewById(R.id.p1Button);
        this.p2Button = findViewById(R.id.p2Button);

        //guia
        this.guide = findViewById(R.id.guide);

        //botões da grade
        this.gridButtons.put("a1", findViewById(R.id.a1));
        this.gridButtons.put("a2", findViewById(R.id.a2));
        this.gridButtons.put("a3", findViewById(R.id.a3));

        this.gridButtons.put("b1", findViewById(R.id.b1));
        this.gridButtons.put("b2", findViewById(R.id.b2));
        this.gridButtons.put("b3", findViewById(R.id.b3));

        this.gridButtons.put("c1", findViewById(R.id.c1));
        this.gridButtons.put("c2", findViewById(R.id.c2));
        this.gridButtons.put("c3", findViewById(R.id.c3));

        //setar evento dos botões do grid;
        this.gridButtons.forEach((s, imageButton) -> imageButton.setOnClickListener(v -> setField(s)));
    }

    public void stateInitialSetter() {
        this.guideText("Selecione o primeiro jogador");

        //peso do campos
        this.gridValues.put("a1", 0);
        this.gridValues.put("a2", 0);
        this.gridValues.put("a3", 0);

        this.gridValues.put("b1", 0);
        this.gridValues.put("b2", 0);
        this.gridValues.put("b3", 0);

        this.gridValues.put("c1", 0);
        this.gridValues.put("c2", 0);
        this.gridValues.put("c3", 0);

        //desabilitar grade:
        setEnabledGridButtons(false);

        //remover imagens dos botões
        this.gridButtons.forEach((s, imageButton) -> imageButton.setImageBitmap(null));

        //habilitar botoes de jogador e des-selecionar primeiro jogador
        this.next = 0;
        this.rounds = 0;
        this.p1Button.setEnabled(true);
        this.p2Button.setEnabled(true);

        paintAllButtons();
    }

    private void clearAll(){
        stateInitialSetter();
        this.guideText("Tire as fotos");

        this.p1Selected = false;
        this.p1Image = null;
        this.p1Button.setImageDrawable(getDrawable(R.drawable.p11));

        this.p2Selected = false;
        this.p2Image = null;
        this.p2Button.setImageDrawable(getDrawable(R.drawable.p22));

        this.p1Button.setOnClickListener(this::p1Click);
        this.p2Button.setOnClickListener(this::p2Click);
    }

    public void clearAll(View view) {
        this.clearAll();
    }

    private void guideText(String text) {
        guide.setText(text);
    }

    private void setEnabledGridButtons(Boolean enabled) {
        this.gridButtons.forEach((key, button) -> button.setEnabled(enabled));
    }

    public void p1Click(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 1);
    }

    public void p2Click(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 2);
    }

    public void setP1(Bitmap photo) {
        this.p1Selected = true;
        this.p1Button.setImageBitmap(photo);
        this.p1Image = photo;

        this.setEventToChoosePlayer();
    }

    public void setP2(Bitmap photo) {
        this.p2Selected = true;
        this.p2Button.setImageBitmap(photo);
        this.p2Image = photo;

        this.setEventToChoosePlayer();
    }

    private void disablePlayersButtons() {
        if (p1Selected && isSelectedFirst()) p1Button.setEnabled(false);
        if (p2Selected && isSelectedFirst()) p2Button.setEnabled(false);
    }

    public void setEventToChoosePlayer() {
        if (p1Selected && p2Selected) {
            this.guideText("Selecione o primeiro jogador");

            this.p1Button.setOnClickListener(v -> {
                this.next = 1;
                startMatch();
                this.guideText("Vez do Jogador 1");
            });

            this.p2Button.setOnClickListener(v -> {
                this.next = 200;
                startMatch();
                this.guideText("Vez do Jogador 2");
            });
        }
    }

    private Boolean isReadyToPlay() {
        return isSelectedFirst() && p1Selected && p2Selected;
    }

    private Boolean isSelectedFirst() {
        return this.next != 0;
    }

    private void startMatch() {
        this.disablePlayersButtons();
        if (isReadyToPlay()) {
            setEnabledGridButtons(true);
        }
    }

    private void togglePlayer() {
        if (this.next == 1) {
            this.next = 200;
            guideText("Vez do Jogador 2");
        } else {
            this.next = 1;
            guideText("Vez do Jogador 1");
        }
        this.rounds++;
    }

    private void setField(String key) {
        ImageButton button = this.gridButtons.get(key);

        button.setEnabled(false);
        button.setImageBitmap(this.next == 1 ? this.p1Image : this.p2Image);

        this.gridValues.put(key, this.next);
        togglePlayer();
        check();
    }

    private void checkValue(int value, WinType winType) {
        if (value == 3) endGame(1, winType);
        else if (value == 600) endGame(200, winType);
    }

    private void check() {
        if(this.rounds == 9){
            this.tieGame();
        }

        //Extrair valores
        int a1 = this.gridValues.get("a1");
        int a2 = this.gridValues.get("a2");
        int a3 = this.gridValues.get("a3");

        int b1 = this.gridValues.get("b1");
        int b2 = this.gridValues.get("b2");
        int b3 = this.gridValues.get("b3");

        int c1 = this.gridValues.get("c1");
        int c2 = this.gridValues.get("c2");
        int c3 = this.gridValues.get("c3");

        //Verificar Linhas:
        checkValue(a1 + a2 + a3, WinType.LINHA_A); //Linha A
        checkValue(b1 + b2 + b3, WinType.LINHA_B); //Linha B
        checkValue(c1 + c2 + c3, WinType.LINHA_C); //Linha C

        //Verificar Colunas:
        checkValue(a1 + b1 + c1, WinType.COLUNA_1); //Coluna 1
        checkValue(a2 + b2 + c2, WinType.COLUNA_2); //Coluna 2
        checkValue(a3 + b3 + c3, WinType.COLUNA_3); //Coluna 3

        //Verificar Cruzado:
        checkValue(a1 + b2 + c3, WinType.CRUZADA_A1); // Esquerda-Superior + Meio + Direita-Inferior
        checkValue(c1 + b2 + a3, WinType.CRUZADA_C1); // Esquerda-Inferior + Meio + Direita-Superior
    }

    private void endGame(int winner, WinType winType) {

        this.setEnabledGridButtons(false);
        this.guideText("Fim de Jogo!!!");

        //Extrair valores
        ImageButton a1 = this.gridButtons.get("a1");
        ImageButton a2 = this.gridButtons.get("a2");
        ImageButton a3 = this.gridButtons.get("a3");

        ImageButton b1 = this.gridButtons.get("b1");
        ImageButton b2 = this.gridButtons.get("b2");
        ImageButton b3 = this.gridButtons.get("b3");

        ImageButton c1 = this.gridButtons.get("c1");
        ImageButton c2 = this.gridButtons.get("c2");
        ImageButton c3 = this.gridButtons.get("c3");

        switch (winType){
            case LINHA_A:
                a1.setBackgroundColor(Color.parseColor("#006400"));
                a2.setBackgroundColor(Color.parseColor("#006400"));
                a3.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case LINHA_B:
                b1.setBackgroundColor(Color.parseColor("#006400"));
                b2.setBackgroundColor(Color.parseColor("#006400"));
                b3.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case LINHA_C:
                c1.setBackgroundColor(Color.parseColor("#006400"));
                c2.setBackgroundColor(Color.parseColor("#006400"));
                c3.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case COLUNA_1:
                a1.setBackgroundColor(Color.parseColor("#006400"));
                b1.setBackgroundColor(Color.parseColor("#006400"));
                c1.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case COLUNA_2:
                a2.setBackgroundColor(Color.parseColor("#006400"));
                b2.setBackgroundColor(Color.parseColor("#006400"));
                c2.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case COLUNA_3:
                a3.setBackgroundColor(Color.parseColor("#006400"));
                b3.setBackgroundColor(Color.parseColor("#006400"));
                c3.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case CRUZADA_A1:
                a1.setBackgroundColor(Color.parseColor("#006400"));
                b2.setBackgroundColor(Color.parseColor("#006400"));
                c3.setBackgroundColor(Color.parseColor("#006400"));
                break;
            case CRUZADA_C1:
                c1.setBackgroundColor(Color.parseColor("#006400"));
                b2.setBackgroundColor(Color.parseColor("#006400"));
                a3.setBackgroundColor(Color.parseColor("#006400"));
                break;
        }
        this.winGame(winner);
    }

    private void tieGame(){
        this.guideText("Jogo empatado!!!");
        Handler handler = new Handler();
        handler.postDelayed(this::stateInitialSetter, 3000);
    }

    private void winGame(int winner){
        Intent win = new Intent(this, WinnerActivity.class);

        if(winner == 1){
            win.putExtra("bitmap", this.p1Image);
        } else {
            win.putExtra("bitmap", this.p2Image);
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(win);
            this.stateInitialSetter();
        }, 3000);
    }

    private void paintAllButtons(){
        this.gridButtons.forEach((s, imageButton) -> imageButton.setBackgroundColor(Color.parseColor("#3c3c3c")));
        this.p1Button.setBackgroundColor(Color.parseColor("#3c3c3c"));
        this.p2Button.setBackgroundColor(Color.parseColor("#3c3c3c"));
        findViewById(R.id.btnPlayerClear).setBackgroundColor(Color.parseColor("#3c3c3c"));
    }
}
