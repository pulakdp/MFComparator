package io.github.pulakdp.mfcomparator.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import io.github.pulakdp.mfcomparator.R;
import io.github.pulakdp.mfcomparator.databinding.ActivityCompareBinding;
import io.github.pulakdp.mfcomparator.viewmodel.FundCompareViewModel;

public class CompareActivity extends AppCompatActivity {

    private ActivityCompareBinding binding;
    private FundCompareViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compare);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = new FundCompareViewModel(this,
                getIntent().getStringExtra("key1"), getIntent().getStringExtra("key2"));
        binding.setVm(viewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
