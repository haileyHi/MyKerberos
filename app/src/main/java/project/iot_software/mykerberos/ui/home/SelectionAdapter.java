package project.iot_software.mykerberos.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.load.engine.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import project.iot_software.mykerberos.R;

public class SelectionAdapter extends ArrayAdapter<String> {

    private HashMap<Integer, Boolean> selection = new HashMap<>();

    public SelectionAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public void setNewSelection(int position, boolean value) {
        selection.put(position, value);
        notifyDataSetChanged();
    }

    public boolean isPositionChecked(int position) {
        Boolean result = selection.get(position);
        return result == null ? false : result;
    }

    public Set<Integer> getCurrentCheckedPosition(){
        return selection.keySet();
    }

    public void removeSelection(int position) {
        selection.remove(position);
        notifyDataSetChanged();
    }

    public void clearSelection() {
        selection = new HashMap<Integer, Boolean>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        int defaultColor = ContextCompat.getColor(getContext(), android.R.color.background_light);
        v.setBackgroundColor(defaultColor);

        //선택된 건 아래 색상으로
        if(selection.get(position) != null){
            int selectedColor = ContextCompat.getColor(getContext(), R.color.colorYellowOrange/*android.R.color.holo_red_light*/);
            v.setBackgroundColor(selectedColor);
        }
        return v;
    }
}
