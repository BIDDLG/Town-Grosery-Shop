package com.grocery.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.grocery.app.databinding.FragmentProfileBinding;
import com.grocery.app.viewmodel.GroceryViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private GroceryViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(GroceryViewModel.class);

        viewModel.userDetails.observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.nameEdit.setText(user.getName());
                binding.phoneEdit.setText(user.getPhone());
                binding.addressEdit.setText(user.getAddress());
            }
        });

        binding.saveButton.setOnClickListener(v -> {
            String name = binding.nameEdit.getText().toString();
            String phone = binding.phoneEdit.getText().toString();
            String address = binding.addressEdit.getText().toString();

            if (!name.isEmpty() && !phone.isEmpty() && !address.isEmpty()) {
                com.grocery.app.model.User user = new com.grocery.app.model.User();
                user.setName(name);
                user.setPhone(phone);
                user.setAddress(address);
                viewModel.saveUserDetails(user);
                android.widget.Toast.makeText(getContext(), "Profile Saved", android.widget.Toast.LENGTH_SHORT).show();
            } else {
                android.widget.Toast.makeText(getContext(), "Please fill all fields", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
