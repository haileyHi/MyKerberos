package project.iot_software.mykerberos.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.iot_software.mykerberos.R;

public class ListViewAdapter extends BaseAdapter {

    private Context ctx;
    private List<ListViewItem> mData = new ArrayList<>();

    public ListViewAdapter(List<ListViewItem> data){
        this.mData = data;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.info_list_item,parent,false);
        }

        //화면에 표시될 View로부터 위젯에 관한 참조 획득
        TextView titleTextView = (TextView) view.findViewById(R.id.text_deviceInfo);
        TextView subTextView = (TextView) view.findViewById(R.id.text_deviceValue);

        ListViewItem listViewItem = mData.get(position);
        /* 이 아래 부분을 어떻게 수정하고 메인 액티비티의 DB 정보 불러오기 중간에 있는 for문이랑 video_list_item이랑 어떻게 매칭하면 될것 같은데*/
        titleTextView.setText(listViewItem.getTitleStr());
        subTextView.setText(listViewItem.getValueStr());

        return view;
    }
}
