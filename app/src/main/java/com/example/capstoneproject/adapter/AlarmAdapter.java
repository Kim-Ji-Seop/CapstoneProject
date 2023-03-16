package com.example.capstoneproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.common.DateDiff;
import com.example.capstoneproject.data.users.response.push.GetPushListResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private int oldPosition = -1;
    private int selectedPosition = -1;
    private List<GetPushListResult> result;
    private Context context;

    public AlarmAdapter(List<GetPushListResult> result, Context context) {
        this.result = result;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        TextView date;
        RecyclerView recyclerView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.alarm_layout);
            date = itemView.findViewById(R.id.alarm_day_tv);
            recyclerView = itemView.findViewById(R.id.alarm_detail_recyclerview);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_alarm, parent, false);
        AlarmAdapter.ViewHolder vh = new AlarmAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int touchIndex = holder.getAdapterPosition();
        //서버에서 보낸 날짜
        String getDate = result.get(touchIndex).getDate();
        String[] getSplitDate = getDate.split("-");
        //오늘 날짜
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowConversionDate = simpleDateFormat.format(nowDate);
        String[] nowSplitDate = nowConversionDate.split("-");
        //오늘과 서버에서 보낸 날짜의 차이
        DateDiff dateDiff = new DateDiff();
        int differenceOfDate = dateDiff.getDifferenceOfDate(Integer.parseInt(nowSplitDate[0]), Integer.parseInt(nowSplitDate[1]), Integer.parseInt(nowSplitDate[2]), Integer.parseInt(getSplitDate[0]), Integer.parseInt(getSplitDate[1]), Integer.parseInt(getSplitDate[2]));
        if (differenceOfDate == 0) {
            holder.date.setText("오늘");
        } else if (differenceOfDate == 1) {
            holder.date.setText("어제");
        } else {
            holder.date.setText("일주일");
        }
        //TODO 알림 상세 리사이클러뷰 setAdapter

    }



    @Override
    public int getItemCount() {
        return result.size();
    }
}
