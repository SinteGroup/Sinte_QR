package hu.sintegroup.sinte_qr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JavitasokFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JavitasokFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public JavitasokFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JavitasokFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JavitasokFragment newInstance(String param1, String param2) {
        JavitasokFragment fragment = new JavitasokFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_javitasok, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        HashMap<String, List<String>> items=new HashMap<>();

        ExpandableListView javitasokListView=(ExpandableListView) view.findViewById(R.id.JavitasokExpandableListview);
        ExpandableListAdapter adapter=new sinteQrExpandableListviewAdapter(getContext(), items);
        javitasokListView.setAdapter(adapter);
        Log.d("ItemChildViewCreated", "Létrejött: "+javitasokListView.getAdapter().getCount());
        javitasokListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                Log.d("ItemChildViewonExpand", "Lista kibontva: "+String.valueOf(i));
            }
        });
    }
}