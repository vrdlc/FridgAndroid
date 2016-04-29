
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by androidwarriors on 2/18/2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    Context context;
    List<UserData> dataList = new ArrayList<>();
    LayoutInflater inflater;
    Listener listener;

    public ListAdapter(Context context, List<UserData> dataList1) {

        this.context = context;
        this.dataList = dataList1;
        this.listener= (Listener) context;
        inflater = LayoutInflater.from(context);


    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = inflater.inflate(R.layout.row, parent, false);
        ListViewHolder viewHolder = new ListViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {


        holder.iv_delete.setTag(position);
        holder.tv_name.setText(dataList.get(position).name);

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.nameToChnge(dataList.get((Integer) v.getTag()).name);


            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_delete;

        public ListViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_delete= (ImageView) itemView.findViewById(R.id.iv_delete);

        }
    }


}