package com.example.capstoneproject.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.adapter.RecordRecyclerViewAdapter;
import com.example.capstoneproject.data.users.UserService;
import com.example.capstoneproject.data.users.response.record.GetRecordResult;
import com.example.capstoneproject.view.GetUserRecordView;

import java.util.List;

public class RecordFragment extends Fragment implements GetUserRecordView {


    private RecyclerView recyclerView;
    private RecordRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        initView(root);

        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
        //        profileImage  -> TODO Glide 사용

        getList();

    }

    private void getList() {
        UserService userService = new UserService();
        userService.setUserRecordView(this);
        Log.d("TAG", "getList: "+getJwt());
        userService.getUserRecord(getJwt());
    }
    private String getJwt(){
        SharedPreferences spf = this.getActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    private void initRecyclerView(List<GetRecordResult> result) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecordRecyclerViewAdapter(result);
        recyclerView.setAdapter(adapter);

    }

    private void initView(View root) {
        recyclerView = root.findViewById(R.id.record_recyclerview);
    }

    @Override
    public void onGetMatchRoomSuccess(List<GetRecordResult> result) {

        initRecyclerView(result);
    }

    @Override
    public void onGetMatchRoomFailure() {
        Log.d("TAG", "onGetMatchRoomFailure: "+"실패11111111");

    }
}
