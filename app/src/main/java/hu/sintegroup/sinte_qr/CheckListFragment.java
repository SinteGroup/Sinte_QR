package hu.sintegroup.sinte_qr;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CheckListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckListFragment newInstance(String param1, String param2) {
        CheckListFragment fragment = new CheckListFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView checkListView=(ListView) view.findViewById(R.id.checkLista);
        ArrayList<Javitasok.Check> checkLista=new ArrayList<>();

        checkLista.add(new Javitasok.Check("Mukodik"));
        checkLista.add(new Javitasok.Check("Mukodik"));
        checkLista.add(new Javitasok.Check("Mukodik"));

        ArrayAdapter<Javitasok.Check> checkListViewAdapter=new checkListViewAdapter(checkLista, getContext());

        final Integer[] i = {0};
        Button checkAddItemButton=(Button)view.findViewById(R.id.addCheckItemButton);
        checkAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i[0] +=1;
                Javitasok.Check tempCheck=new Javitasok.Check("Elem "+i[0]);
                checkLista.add(tempCheck);
                checkListViewAdapter.notifyDataSetChanged();
                Log.d("OkButAdapterItemSize", "Adapter_elemszám: "+String.valueOf(checkListViewAdapter.getCount()));
                Log.d("OkBuArraySize","Lista_elemszám: "+String.valueOf(checkLista.size()));
                Log.d("OkButtonAdapterKint", "BentiAdapter");
                for(Javitasok.Check temp : checkLista) {
                    Log.d("OkButElementsList", temp.checkName);
                }
            }
        });
        Log.d("OkButtonAdapterKint", "AlapAdapter");
        checkListView.setAdapter(checkListViewAdapter);


        /*String[] spinnerContent=new String[]{"Válasszon telephelyet"};
        Spinner telephelySpinner=(Spinner) view.findViewById(R.id.CheckListTelephelySpinner);    //Ha kell esetleg manuális telephely választás, akkor használni.
        ArrayAdapter<String> telephelySpinnerAdapter=new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerContent);
        telephelySpinner.setAdapter(telephelySpinnerAdapter);*/
    }
}