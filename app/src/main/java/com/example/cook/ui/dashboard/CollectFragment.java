package com.example.cook.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cook.R;
import com.example.cook.adapter.CollectAdapter;
import com.example.cook.entity.CollectCenterEntity;
import com.example.cook.respository.CookCollectViewModal;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.CallAdapter;

public class CollectFragment extends Fragment {
    private static final String TAG = "CollectFragment";
    private RecyclerView recyclerView;
    private CollectAdapter collectAdapter;
    private CookCollectViewModal cookCollectViewModal;
    private LiveData<List<CollectCenterEntity>> allCookCollect;
    private List<CollectCenterEntity> allCollect;

    public CollectFragment() {


        setHasOptionsMenu(true);
    }

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = requireActivity().findViewById(R.id.recycler_collect);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        cookCollectViewModal = ViewModelProviders.of(requireActivity()).get(CookCollectViewModal.class);
        collectAdapter = new CollectAdapter();

        doBide();
        //滑动删除
        deletescroll();
    }

    private void deletescroll() {

//滑动删除
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final CollectCenterEntity delete = allCollect.get(viewHolder.getAdapterPosition());
                cookCollectViewModal.deleteCollect(delete);
                collectAdapter.notifyDataSetChanged();

                Snackbar.make(requireActivity().findViewById(R.id.recycler_collect), "删除了一个收藏", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                cookCollectViewModal.insertCollect(delete);
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);
    }


    private void doBide() {

        allCookCollect = cookCollectViewModal.findAllCollect();
        allCookCollect.observe(requireActivity(), new Observer<List<CollectCenterEntity>>() {
            @Override
            public void onChanged(List<CollectCenterEntity> collectCenterEntities) {
                allCollect = collectCenterEntities;
                collectAdapter.setAllCollect(collectCenterEntities);
                collectAdapter.notifyDataSetChanged();

            }
        });
        recyclerView.setAdapter(collectAdapter);
    }


}
