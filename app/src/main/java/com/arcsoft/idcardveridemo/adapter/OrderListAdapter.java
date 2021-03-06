package com.arcsoft.idcardveridemo.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcsoft.idcardveridemo.R;

/**
 * @auther: lijunjie
 * @createDate: 2020/5/18  18:51
 * @purpose：
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {



    private OrderListAdapter.OnItemClickListener mOnItemClickListener = null;



    //bean类未填充，等待网路接入
    public OrderListAdapter(){

    }


    public static interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    public void setmOnItemClickListener(OrderListAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_curstom_item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view,viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if (i==0){
            viewHolder.tv_name.setText("豪华套房");
            viewHolder.tv_money.setText("金额：420");
            viewHolder.tv_time.setText("2019-12-20 —— \n 2019-12-24");
        }else  if (i==1){
            viewHolder.tv_name.setText("双人房");
            viewHolder.tv_money.setText("金额：220");
            viewHolder.tv_time.setText("2019-12-24 —— \n 2019-12-26");
        }else  if (i==2){
            viewHolder.tv_name.setText("特价大床房");
            viewHolder.tv_money.setText("金额：1,500");
            viewHolder.tv_time.setText("2019-12-31 —— \n 2020-1-1");
        }else  if (i==3){
            viewHolder.tv_name.setText("豪华单间");
            viewHolder.tv_money.setText("金额：320");
            viewHolder.tv_time.setText("2020-4-1 —— \n 2020-4-2");
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name;
        ConstraintLayout con;
        TextView tv_money;
        TextView tv_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            con=itemView.findViewById(R.id.con_view);

            tv_money=itemView.findViewById(R.id.tv_money);
            tv_time=itemView.findViewById(R.id.tv_checktime);
        }
    }

}
