//package com.example.user.myapplication.ai_group;
//
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//
//import com.example.user.myapplication.R;
//
//import java.util.List;
//
//public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
//
//    private List mBeen;
//    private View mFooterView;
//    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
//    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
//
//    public MyAdapter(List been) {
//        this.mBeen = been;
//    }
//
//    public View getFooterView() {
//        return mFooterView;
//    }
//    public void setFooterView(View footerView) {
//        mFooterView = footerView;
//        notifyItemInserted(getItemCount()-1);
//    }
//
//    public int getItemViewType(int position) {
//        if (mFooterView == null){
//            return TYPE_NORMAL;
//        }
//        if (position == getItemCount()-1){
//            //最后一个,应该加载Footer
//            return TYPE_FOOTER;
//        }
//        return TYPE_NORMAL;
//    }
//
//
//    @NonNull
//    @Override
//    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(mFooterView != null && viewType == TYPE_FOOTER){
//            return new MyHolder(mFooterView);
//        }
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ai_group_item, parent,false);
//        return new MyHolder(itemView);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//        if(getItemViewType(position) == TYPE_NORMAL) {
//            if (holder instanceof MyHolder) {
//                holder.bindData((ai_group_item) mBeen.get(position));
//                return;
//            }
//            return;
//        }else {
//            return;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
////        if(mFooterView == null){
////            return mBeen.size();
////        }else{
////            return mBeen.size() + 1;
////        }
//        return mBeen.size()+1;
//    }
//}
