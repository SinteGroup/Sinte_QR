package hu.sintegroup.sinte_qr;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.sintegroup.sinte_qr.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private  ArrayList<felmeres> felmeres_lista;
    public felmeres_listview_adapter felmeres_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        felmeres_lista=new ArrayList<>() ;
        felmeres elso=new felmeres();
        elso.set_felmeres_neve("elso");
        felmeres_lista.add(elso);
        Log.d("Felmeres_max", String.valueOf(felmeres_lista.size()));

        ListView felmeres_listview=(ListView) view.findViewById(R.id.felmeres_lista_listview);
        felmeres_adapter=new felmeres_listview_adapter(this.getContext(), R.layout.felmeres_listview_item);
        felmeres_adapter.addAll(felmeres_lista);
        felmeres_listview.setAdapter(felmeres_adapter);

        Log.d("Adapter_elemszáma: ", String.valueOf(felmeres_adapter.getCount()));
        Log.d("Adapter_lista:", String.valueOf(felmeres_lista.size()));
        Log.d("Adapter_context", String.valueOf(felmeres_adapter.getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}