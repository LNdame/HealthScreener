package ansteph.cct.healthscreener.slidingmenu.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ansteph.cct.healthscreener.R;
import ansteph.cct.healthscreener.slidingmenu.model.NavDrawerItem;

/**
 * Created by loicStephan on 29/04/2015.
 */
public class NavDrawerListAdapter extends BaseAdapter {

    public NavDrawerListAdapter() {
        // TODO Auto-generated constructor stub
    }

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        // TODO Auto-generated constructor stub
        this.context= context;
        this.navDrawerItems= navDrawerItems;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return navDrawerItems.size();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(convertView==null)
        {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        ImageView imgIcon =(ImageView)convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        txtTitle.setText(navDrawerItems.get(position).getTitle());

        //displaying count
        if(navDrawerItems.get(position).getCounterVisibility()){
            txtCount.setText(navDrawerItems.get(position).getCount());
        }else{
            //hide the counter
            txtCount.setVisibility(View.GONE);
        }

        return convertView;
    }

}
