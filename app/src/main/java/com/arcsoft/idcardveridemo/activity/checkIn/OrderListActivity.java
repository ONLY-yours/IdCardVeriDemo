package com.arcsoft.idcardveridemo.activity.checkIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arcsoft.idcardveridemo.R;
import com.arcsoft.idcardveridemo.activity.WelcomeActivity;
import com.arcsoft.idcardveridemo.adapter.OrderListAdapter;
import com.arcsoft.idcardveridemo.base.BaseActivity;

public class OrderListActivity extends BaseActivity {

    private RecyclerView rvOrderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_order_list);

        initview();
    }


    void initview(){


        rvOrderList=findViewById(R.id.rv1);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvOrderList.setLayoutManager(manager);

        OrderListAdapter adapter=new OrderListAdapter();
        rvOrderList.setAdapter(adapter);

        adapter.setmOnItemClickListener(myclick);

    }

    public OrderListAdapter.OnItemClickListener myclick =new OrderListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            startActivity(WelcomeActivity.class);
        }
    };


}
