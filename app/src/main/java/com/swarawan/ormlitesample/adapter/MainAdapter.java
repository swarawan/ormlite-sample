package com.swarawan.ormlitesample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swarawan.ormlitesample.R;
import com.swarawan.ormlitesample.database.entity.Employee;

import java.util.List;

/**
 * Created by rioswarawan on 3/15/17.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Employee> employees;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public MainAdapter(Context context, List<Employee> employees) {
        this.context = context;
        this.employees = employees;
        this.inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Employee employee = employees.get(position);
        ViewHolder body = (ViewHolder) holder;
        body.populate(employee, position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textEmployee;

        ViewHolder(View itemView) {
            super(itemView);
            textEmployee = (TextView) itemView.findViewById(R.id.item);
        }

        public void populate(final Employee employee, int position) {
            textEmployee.setText(employee.getName());
            if (position % 2 == 0)
                textEmployee.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
            else
                textEmployee.setBackgroundColor(context.getResources().getColor(android.R.color.white));

            if (onItemClickListener != null)
                textEmployee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClicked(employee.getId());
                    }
                });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(String id);
    }
}
