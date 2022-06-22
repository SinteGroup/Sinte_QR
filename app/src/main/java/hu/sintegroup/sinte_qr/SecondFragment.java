package hu.sintegroup.sinte_qr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

import hu.sintegroup.sinte_qr.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private  ArrayList<Felmeres> felmeres_lista;
    private felmeres_listview_adapter felmeres_adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        felmeres_lista=new ArrayList<>() ;

        for(int i=0; i<=20; i++){       //Feltöltés elemekkel
            Felmeres temp=new Felmeres();
            temp.set_felmeres_neve("Item"+i);
            felmeres_lista.add(temp);
        }

        Log.d("Felmeres_max", String.valueOf(felmeres_lista.size()));

        ListView felmeres_listview=(ListView) view.findViewById(R.id.felmeres_lista_listview);
        felmeres_adapter=new felmeres_listview_adapter(this.getContext(), R.layout.felmeres_listview_item);
        felmeres_adapter.addAll(felmeres_lista);
        felmeres_listview.setAdapter(felmeres_adapter);
        felmeres_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Felmeres felm=(Felmeres) adapterView.getAdapter().getItem(position);

                Log.d("Click_OnItem: ", "Click: "+felm.get_felmeres_neve());

                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_QRFragment);
            }
        });

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