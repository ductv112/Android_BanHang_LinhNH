package app.dft.appbanhang.navmenu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import app.dft.appbanhang.R;

/**
 * Created by ductv on 4/4/2017.
 */
public class CustomDrawerAdapter extends ArrayAdapter {

    Context context;
    List<DrawerItem> drawerItemList;
    int layoutResID;
    OnSelectMenu listener;

    public CustomDrawerAdapter(Context context, int layoutResourceID,
                               List<DrawerItem> listItems, OnSelectMenu listener) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
        this.listener = listener;
    }

    public interface OnSelectMenu {
        void onSelect(int position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        DrawerItemHolder drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);
            drawerHolder.drawer_itemName = (TextView) view
                    .findViewById(R.id.drawer_itemName);
            drawerHolder.drawer_icon = (ImageView) view.findViewById(R.id.drawer_icon);
            drawerHolder.drawer_icon.setVisibility(View.GONE);

            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();

        }

        DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);

        if (dItem.getHasSub()) {
            drawerHolder.drawer_icon.setVisibility(View.VISIBLE);
        } else {
            drawerHolder.drawer_icon.setVisibility(View.GONE);
        }

        drawerHolder.drawer_itemName.setText(dItem.getItemName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSelect(position);
                }
            }
        });

        return view;
    }

    private static class DrawerItemHolder {
        RelativeLayout drawer_layout_count;
        TextView drawer_itemName;
        ImageView drawer_icon;
    }

}
