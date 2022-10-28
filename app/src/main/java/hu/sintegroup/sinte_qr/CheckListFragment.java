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

public class CheckListFragment extends Fragment {

    public CheckListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            }
        });
        checkListView.setAdapter(checkListViewAdapter);
    }
}