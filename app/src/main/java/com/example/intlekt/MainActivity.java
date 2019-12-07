package com.example.intlekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class MainActivity extends AppCompatActivity {

    //region Ozgaruvchilar
    private int onColor = 0;
    private Double E = 0.001, O = 2.0;
    private int selectcolor = 0;
    private StringBuffer _natija = null, str, str_w, strl;
    private GridLayout gridLayout;
    private Button button, btn1, btn2, btn3;
    private static boolean tekshirish = false;
    private TextView text1, text2, text3;
    private EditText edtText;
    private int n = 68;
    private int count;
    private int[][] matritsa = new int[n][n];
    private int[][] matritsa_w = new int[4][64];
    private int[] array = new int[64];
    private int[] array2 = new int[64];
    private int[] w1 = new int[64];
    private int[] w2 = new int[64];
    private static int SONI = 0;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //region MyFindViewById
        onColor = getResources().getColor(R.color.select);
        _natija = new StringBuffer();
        str = new StringBuffer();
        str_w = new StringBuffer();
        strl = new StringBuffer();
        gridLayout = findViewById(R.id.gridLayout);
        count = gridLayout.getRowCount() * gridLayout.getColumnCount();
        btn1 = findViewById(R.id.SendBtn);
        text1 = findViewById(R.id.natija);
        text2 = findViewById(R.id.text1);
        text3 = findViewById(R.id.w_iiii);
        button = findViewById(R.id.btn_tekshirish);
        btn3 = findViewById(R.id.btn_tozalash);
        btn2 = findViewById(R.id.btn_Son);
        edtText = findViewById(R.id.input_son);
        InitView(1);
//      Dizayin();
        //endregion

        //region Malumotlarni Tozalash
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SONI = 0;
                _natija.delete(0, _natija.length());
                InitView(2);
            }
        });

        //endregion

        //region Malumotlarni oqib olish
        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (edtText.getText().toString().length() > 3) {
                    clearBack();
                    matritsa[SONI][n - 4] = Integer.parseInt(String.valueOf(edtText.getText().toString().charAt(0)));
                    matritsa[SONI][n - 3] = Integer.parseInt(String.valueOf(edtText.getText().toString().charAt(1)));
                    matritsa[SONI][n - 2] = Integer.parseInt(String.valueOf(edtText.getText().toString().charAt(2)));
                    matritsa[SONI][n - 1] = Integer.parseInt(String.valueOf(edtText.getText().toString().charAt(3)));
                    _natija.append(edtText.getText());
                    text1.setText(_natija);
                    SONI++;
                    edtText.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Raqamlarni kiriting", Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion

        //region WniHisoblash1
        btn2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n"})
            @Override
            public void onClick(View v) {

                for (int nk = 0; nk < 4; nk++) {
                    WniHisoblash(nk);
                    str_w.append("\n");
                    for (int i = 0; i < 64; i++) {
                        matritsa_w[nk][i] = w1[i];
                    }
                    str_w.append(Arrays.toString(w1));
                    Tozalash_W();
                }
                text3.setText(str_w);
            }
        });
//endregion

        //region NatijaniTekshirish
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBack();
                strl.delete(0, strl.length());
                Double F = 0.0;
                boolean t;
                if (array.length > 0) {
                    for (int nk = 0; nk < 4; nk++) {
                        for (int i = 0; i < 64; i++) {
                            F += matritsa_w[nk][i] * array[i] - O;
                        }
                        if (F >= E) strl.append("1");
                        else strl.append("0");
                    }
                    text2.setText(strl);

                }
                Tozalash();
            }
        });
//endregion
    }

    //region WniHisoblash
    public void WniHisoblash(int index) {
        tekshirish = true;
        boolean t1, t2;
        Double F = 0.0;
        do {
            t2 = false;
            for (int g = 0; g < SONI; g++) {
                do {
                    t1 = false;
                    F = 0.0;
                    for (int j = 0; j < 64; j++) {
                        F += matritsa[g][j] * w1[j] - O;
                    }
                    if (F <= E && matritsa[g][n - 4 + index] == 1) {
                        for (int l = 0; l < 64; l++) {
                            w1[l] += matritsa[g][l];
                        }
                        t1 = true;

                    } else {
                        if (F > E && matritsa[g][n - 4 + index] == 0) {
                            for (int l = 0; l < 64; l++) {
                                w1[l] -= matritsa[g][l];
                            }
                            t1 = true;
                        }
                    }
                } while (t1);

            }

            int wni_tekshirish = 0;
            for (int k = 0; k < 64; k++) {
                if (w1[k] != w2[k]) {
                    wni_tekshirish = 1;
                }
            }

            if (wni_tekshirish == 1) {
                arraycopy(w1, 0, w2, 0, 64);
                t2 = true;
            }
        } while (t2);

    }
    //endregion

    //region Programma ishlashida korinishi
    private void Dizayin() {
        TapTargetSequence intro_sequence = new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(findViewById(R.id.SendBtn), "Malumotlarni saqlash!!!", "Bu Buttoni bosganingizda tanlangan sohalardagi malumotlar saqlanadi").id(1)
                                .outerCircleColor(android.R.color.holo_blue_bright)
                                .targetCircleColor(android.R.color.holo_blue_dark)
                                .dimColor(android.R.color.holo_blue_light)
                                .textColor(android.R.color.black),
                        TapTarget.forView(findViewById(R.id.btn_tekshirish), "Tekshirish!!!", "Bu button w(i) hisoblangandan keyin tekshirish uchun").id(1)
                                .outerCircleColor(android.R.color.holo_orange_light)
                                .targetCircleColor(android.R.color.holo_orange_dark)
                                .textColor(android.R.color.black),
                        TapTarget.forView(findViewById(R.id.btn_Son), "Organish", "W(i)ni organish ").id(1)
                )
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                });
        intro_sequence.start();
    }
//endregion

    //region WniTozalash
    public void Tozalash_W() {
        for (int i = 0; i < 64; i++) {
            w1[i] = 0;
            w2[i] = 0;
        }
    }
    //endregion

    //region Boshlash
    public void InitView(int index) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matritsa[i][j] = 0;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 64; j++) {
                matritsa_w[i][j] = 0;
            }
        }
        for (int i = 0; i < 64; i++) {
            array[i] = 0;
            array2[i] = 0;
        }
        text1.setText("");
        text2.setText("");
        text3.setText("");
        Tozalash_W();
        if (index == 1) {
            for (int i = 0; i < count; i++) {
                View cardItem = LayoutInflater.from(this).inflate(R.layout.item, gridLayout, false);
                cardItem.setId(i);
                cardItem.setTag(false);
                gridLayout.addView(cardItem);
            }
        }
    }
//endregion

    //region GridLayoutni Tozalash
    public void Tozalash() {
        strl.delete(0, strl.length());
        for (int i = 0; i < 64; i++) {
            array[i] = 0;
            CardView item = (CardView) gridLayout.getChildAt(i);
            item.setTag(false);
            item.setCardBackgroundColor(getResources().getColor(R.color.oq));

        }
    }
//endregion

    //region Kiritgan Malumotlarimizni saqlash
    public void clearBack() {
        if (tekshirish) {
            _natija.append("\n");
            for (int i = 0; i < 64; i++) {
                CardView item = (CardView) gridLayout.getChildAt(i);
                boolean isTrue = (boolean) item.getTag();
                if (isTrue) {
                    item.setTag(false);
                    _natija.append("1");
                    array[i] = 1;
                } else {
                    item.setTag(false);
                    _natija.append("0");
                    array[i] = 0;
                }
                item.setCardBackgroundColor(getResources().getColor(R.color.oq));
            }
        } else {
            _natija.append("\n");
            int s = 0;
            for (int i = 0; i < 64; i++) {
                CardView item = (CardView) gridLayout.getChildAt(s);
                boolean isTrue = (boolean) item.getTag();
                if (isTrue) {
                    _natija.append("1");
                    item.setTag(false);
                    matritsa[SONI][i] = 1;
                } else {
                    item.setTag(false);
                    _natija.append("0");
                    matritsa[SONI][i] = 0;
                }
                item.setCardBackgroundColor(getResources().getColor(R.color.oq));

                s++;
            }
        }

    }


    //endregion

    //region CardViewBosilganda
    public void onItemClick(View view) {
        CardView item = (CardView) view;
        item.setTag(true);
        item.setCardBackgroundColor(onColor);
    }
    //endregion
}
