package ansteph.cct.healthscreener.slidingmenu.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ansteph.cct.healthscreener.R;

/**
 * Created by loicStephan on 06/06/15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter{


    private Context _context;
    private List<String> _listDataHeader;

    private HashMap<String, List<String[]>>_listDataChild;


    public ExpandableListAdapter(Context cont , List<String> listDataHeader , HashMap <String, List<String[]>> listDataChild)
    {
        this._context = cont;
        this._listDataChild =listDataChild;
        this._listDataHeader = listDataHeader;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition,final int childPosition,
        boolean isLastChild, View convertView, ViewGroup parent) {

            //get the child from object getChild which will be in this case string[]
            final String[] childText = (String[]) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_item, null);
            }

            TextView txtListChild1 = (TextView) convertView
                    .findViewById(R.id.lblListItem1);

            TextView txtListChild2 = (TextView) convertView
                .findViewById(R.id.lblListItem2);

            txtListChild1.setText(childText[0]);
            txtListChild2.setText(childText[1]);

            return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        return convertView;
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }



    @Override
    public boolean hasStableIds() {
        return false;
    }





    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
