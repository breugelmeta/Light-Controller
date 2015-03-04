package tv.piratemedia.lightcontroler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class WhiteZoneFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    public static final String ARG_COUNT = "count";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.color_zone, container, false);
        final Bundle args = getArguments();

        LinearLayout indicators = (LinearLayout) rootView.findViewById(R.id.indicators);
        for(int i = 0; i < args.getInt(ARG_COUNT); i++) {
            Button b = (Button) inflater.inflate(R.layout.page_indicator, indicators, false);
            if(i == args.getInt(ARG_OBJECT)) {
                b.setAlpha(1);
            }
            indicators.addView(b);
        }

        Button on = (Button) rootView.findViewById(R.id.on);
        Button off = (Button) rootView.findViewById(R.id.off);

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).mGoogleApiClient == null)
                    return;
                final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(((MainActivity) getActivity()).mGoogleApiClient);
                nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult result) {
                        final List<Node> nodes = result.getNodes();
                        if (nodes != null) {
                            for (int i = 0; i < nodes.size(); i++) {
                                final Node node = nodes.get(i);
                                Wearable.MessageApi.sendMessage(((MainActivity) getActivity()).mGoogleApiClient, node.getId(), "/" + args.getInt(ARG_OBJECT) + "/on", null);
                            }
                        }
                    }
                });
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity) getActivity()).mGoogleApiClient == null)
                    return;
                final PendingResult<NodeApi.GetConnectedNodesResult> nodes = Wearable.NodeApi.getConnectedNodes(((MainActivity) getActivity()).mGoogleApiClient);
                nodes.setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult result) {
                        final List<Node> nodes = result.getNodes();
                        if (nodes != null) {
                            for (int i = 0; i < nodes.size(); i++) {
                                final Node node = nodes.get(i);
                                Wearable.MessageApi.sendMessage(((MainActivity) getActivity()).mGoogleApiClient, node.getId(), "/" + args.getInt(ARG_OBJECT) + "/off", null);
                            }
                        }
                    }
                });
            }
        });
        return rootView;
    }
}