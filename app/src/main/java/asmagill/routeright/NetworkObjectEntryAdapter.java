package asmagill.routeright;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by william on 3/13/16.
 */
public final class NetworkObjectEntryAdapter extends ArrayAdapter<NetworkObject>{

    private final int itemLayoutResource;

    public NetworkObjectEntryAdapter(final Context context, final int itemLayoutResource){
        super(context, 0);
        this.itemLayoutResource = itemLayoutResource;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent){

        final View view = getWorkingView(convertView);
        final ViewHolder viewHolder = getViewHolder(view);
        final NetworkObject entry = getItem(position);

        viewHolder.titleview.setText(entry.getTitle());

        viewHolder.infoView.setText(entry.getInfo());

        return view;

    }

    private View getWorkingView(final View convertView){

        View workingView = null;

        if(null == convertView){
            final Context context = getContext();
            final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            workingView = inflater.inflate(itemLayoutResource, null);

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

            viewHolder.titleview = (TextView) workingView.findViewById(R.id.networkobject_title);
            viewHolder.infoView = (TextView) workingView.findViewById(R.id.networkobject_info);

            workingView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) tag;
        }

        return viewHolder;
    }

    private static class ViewHolder {
        public TextView titleview;
        public TextView infoView;
    }

}

