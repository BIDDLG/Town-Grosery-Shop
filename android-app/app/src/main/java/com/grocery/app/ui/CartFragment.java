package com.grocery.app.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.grocery.app.databinding.FragmentCartBinding;
import com.grocery.app.viewmodel.GroceryViewModel;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private GroceryViewModel viewModel;

    private CartAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(GroceryViewModel.class);

        adapter = new CartAdapter();
        binding.cartRecyclerView.setAdapter(adapter);
        binding.cartRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(getContext()));

        adapter.setOnCartItemClickListener(cartItem -> {
            viewModel.removeFromCart(cartItem.getProduct());
        });

        viewModel.cartItems.observe(getViewLifecycleOwner(), cartItems -> {
            adapter.setCartItems(cartItems);
            binding.checkoutButton.setEnabled(!cartItems.isEmpty());
        });

        viewModel.cartTotal.observe(getViewLifecycleOwner(), total -> {
            binding.totalText.setText(getString(com.grocery.app.R.string.total, total));
        });

        binding.checkoutButton.setOnClickListener(v -> {
            viewModel.placeOrder();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
