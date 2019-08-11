package project.iot_software.mykerberos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter {

    private List<ListViewItem> listViewItemList = new ArrayList<>();
    private Context ctx;
    //데이터 소스 여기서 추가?

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List objects, List<ListViewItem> listViewItemList) {
        super(context, resource, objects);
        this.listViewItemList = listViewItemList;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
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
            LayoutInflater inflater = LayoutInflater.from(ctx);
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.video_list_item,parent,false);
        }

        //화면에 표시될 View로부터 위젯에 관한 참조 획득
        ImageView thumbImageView = (ImageView) view.findViewById(R.id.thumbNail);
        TextView titleTextView = (TextView) view.findViewById(R.id.text_videoTime);
        TextView subTextView = (TextView) view.findViewById(R.id.text_isVideoChecked);

        /* 이 아래 부분을 어떻게 수정하고 메인 액티비티의 DB 정보 불러오기 중간에 있는 for문이랑 video_list_item이랑 어떻게 매칭하면 될것 같은데*/
        ListViewItem listViewItem = listViewItemList.get(position);

        thumbImageView.setImageBitmap(listViewItem.getThumbnail());
        titleTextView.setText(listViewItem.getTitleStr());
        subTextView.setText(listViewItem.getCalendar());

        return view;
    }
}
