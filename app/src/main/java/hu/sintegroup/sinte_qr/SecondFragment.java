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
    private  ArrayList<Gepek> gepek_lista;
    private felmeres_listview_adapter felmeres_adapter;
    private String[] alapGepek;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {
        alapGepek=new String[]{
                "Anyagmozgató gép","Tároló eszközök, tartályok","Feldolgozó gépek","Folyadékbedolgozó eszközök",
                "Mérő eszközök", "Fluidszállítás eszközei", "Sűrítettlevegő ellátás", "Adagoló eszközök",
                "Anyagtovábbítás eszközei"
        };

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gepek_lista =new ArrayList<>() ;

        for (String temp_gep: alapGepek) {  //Kidolgozni, hogy hogy kell majd listát cserélni.
            Gepek temp=new Gepek();
            //temp.set_felmeres_neve(temp_gep);
            gepek_lista.add(temp);
        }

        Log.d("Felmeres_max", String.valueOf(gepek_lista.size()));

        ListView felmeres_listview=(ListView) view.findViewById(R.id.felmeres_lista_listview);
        felmeres_adapter=new felmeres_listview_adapter(this.getContext(), R.layout.felmeres_listview_item);
        felmeres_adapter.addAll(gepek_lista);
        felmeres_listview.setAdapter(felmeres_adapter);
        felmeres_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Gepek felm=(Gepek) adapterView.getAdapter().getItem(position);
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_QRGeneratorFragment);
                //Log.d("Click_OnItem: ", "Click: "+felm.get_felmeres_neve());
            }
        });

        Log.d("Adapter_elemszáma: ", String.valueOf(felmeres_adapter.getCount()));
        Log.d("Adapter_lista:", String.valueOf(gepek_lista.size()));
        Log.d("Adapter_context", String.valueOf(felmeres_adapter.getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}