package com.sds.study.babyseaterapp.calendar.budget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.sds.study.babyseaterapp.R;


public class ItemInputLayout extends LinearLayout implements OnCheckedChangeListener{
    String TAG;
    static ItemInputLayout inputview;
    TextSwitcher textSwitcher;
    EditText txt_regdate,txt_cost,txt_content;
    Context context;
    String[] credit_company={"현대카드","삼성카드","kb국민카드"};
    String[] check_company={"kb은행","농협","신한 은행"};

    int creditCard=0;
    int checkCard=0;

    RadioButton radio_credit,radio_check;
    RadioGroup radio_group;
    BudgetDAO budgetDAO;
    BudgetDTO budgetDTO;
    //BudgetActivity mainActivity;

    public ItemInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //mainActivity=BudgetActivity.mainActivity;
        this.inputview = this;
        TAG = this.getClass().getName()+"/test";

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_input, this);

        textSwitcher = (TextSwitcher) findViewById(R.id.txt_switcher_card);
        textSwitcher.setFactory(factory);

        txt_content = (EditText) findViewById(R.id.txt_content);
        txt_cost = (EditText) findViewById(R.id.txt_cost);
        txt_regdate = (EditText) findViewById(R.id.txt_regdate);

        radio_credit = (RadioButton) findViewById(R.id.radio_credit);
        radio_check = (RadioButton) findViewById(R.id.radio_check);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        radio_group.check(R.id.radio_cash);
        radio_group.setOnCheckedChangeListener(this);

        budgetDAO =new BudgetDAO();

    }

        private ViewFactory factory = new ViewFactory() {

            public View makeView() {
                TextView t = new TextView(context);
                t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                t.setTextAppearance(context, android.R.style.TextAppearance_Large);
                return t;
            }
        };

    public void dataInput(){
        if(budgetDTO !=null){
            txt_content.setText(budgetDTO.getContent());
            txt_cost.setText(budgetDTO.getCost());
            txt_regdate.setText(budgetDTO.getRegdate());
            textSwitcher.setText(budgetDTO.getCardCompany());
        }

    }

    public void btnClick(View view) {
        switch (view.getId()){
            case R.id.img_card :
                if(radio_credit.isChecked()) {
                    creditCard++;
                    if (creditCard == credit_company.length) {
                        creditCard = 0;
                    }
                    textSwitcher.setText(credit_company[creditCard]);
                }else if(radio_check.isChecked()){
                    checkCard++;
                    if(checkCard == check_company.length){
                        checkCard=0;
                    }
                    textSwitcher.setText(check_company[checkCard]);
                };break;
            case R.id.bt_regist:;break;
            case R.id.bt_list :
                Intent intent=new Intent(context,BudgetListActivity.class);
                Log.d(TAG,"bt_list누름");
                context.startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int group) {
        switch (group){
            case  R.id.radio_credit:
                textSwitcher.setText(credit_company[0]);break;
            case  R.id.radio_cash:
                textSwitcher.setText("");break;
            case R.id.radio_check:
                textSwitcher.setText(check_company[0]);break;

        }
    }

    public void getMsgData(String[] msg){
        // 0 카드 1date 2cost 3content
      /*  textSwitcher.setText(msg[0]);
        txt_regdate.setText(msg[1]);
        txt_cost.setText(msg[2]);
        txt_content.setText(msg[3]);*/
        Log.d(TAG,"getMsgData호출");
        budgetDTO =new BudgetDTO();
        budgetDTO.setCardCompany(msg[0]);
        budgetDTO.setRegdate(msg[1]);
        Log.d(TAG, budgetDTO.getRegdate());
        budgetDTO.setCategory("신용카드");
        Log.d(TAG, budgetDTO.getCategory());
        budgetDTO.setCost(msg[2]);
        Log.d(TAG,"cost는"+ budgetDTO.getCost());
        budgetDTO.setContent(msg[3]);
        Log.d(TAG,"content는"+ budgetDTO.getContent());
        budgetDAO.msgRegist(budgetDTO);
    }

}
