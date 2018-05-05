package com.stxr.teacher_test.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stxr.teacher_test.R;
import com.stxr.teacher_test.entities.Paper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by stxr on 2018/5/5.
 * 显示试卷
 */

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.ViewHolder> {
    private List<Paper> papers;

    private OnclickListener listener;

    public PaperAdapter(List<Paper> papers) {
        this.papers = papers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paper, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Paper paper = papers.get(position);
        holder.tv_paper_name.setText(paper.getName());
        holder.tv_paper_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(paper);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return papers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_paper_name)
        TextView tv_paper_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnclickListener(OnclickListener listener) {
        this.listener = listener;
    }

    public interface OnclickListener {
        void onClick(Paper paper);
    }

}
