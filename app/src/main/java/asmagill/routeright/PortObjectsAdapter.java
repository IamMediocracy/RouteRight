package asmagill.routeright;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by William on 4/6/2016.
 */
public class PortObjectsAdapter extends ArrayAdapter<PortObjects>{

    private final int itemlayoutresource;

    public PortObjectsAdapter(Context context, int resource) {
        super(context, resource);
        this.itemlayoutresource = resource;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent){

        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final PortObjects entry = getItem(position);

        viewHolder.service_name.setText(entry.getService_name() + "\n" + entry.getAddress());
        viewHolder.tcp.setText(entry.getTCPString());
        viewHolder.udp.setText(entry.getUDPString());

        return view;

    }

    private View getWorkingView(final View convertView){

        View workingView = null;

        if(null == convertView){
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(itemlayoutresource, null);

        } else {
            workingView = convertView;
        }

        return workingView;
    }

    private ViewHolder getViewHolder(final View workingView){


        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;

        if(null == tag || !(tag instanceof ViewHolder)){
            viewHolder = new ViewHolder();

            viewHolder.service_name = (TextView) workingView.findViewById(R.id.service_object_title);
            viewHolder.tcp = (TextView) workingView.findViewById(R.id.service_object_TCP);
            viewHolder.udp = (TextView) workingView.findViewById(R.id.service_object_UDP);
            workingView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) tag;
        }

        return viewHolder;
    }

    private static class ViewHolder {
        public TextView service_name;
        public TextView tcp;
        public TextView udp;
    }
}
