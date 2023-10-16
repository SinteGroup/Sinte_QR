package hu.sintegroup.sinte_qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import hu.sintegroup.sinte_qr.databinding.FragmentSecondBinding;
import hu.sintegroup.sinte_qr.taroloOsztalyok.Gepek;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private  ArrayList<Gepek> gepek_lista;
    private String[] alapGepek;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}