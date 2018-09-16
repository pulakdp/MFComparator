package io.github.pulakdp.mfcomparator;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.pulakdp.mfcomparator.databinding.ActivityMfdetailBinding;
import io.github.pulakdp.mfcomparator.viewmodel.FundDetailViewModel;

public class MFDetailActivity extends AppCompatActivity {

    ActivityMfdetailBinding binding;
    private FundDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mfdetail);
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = new FundDetailViewModel(this, getIntent().getStringExtra("key"));
        binding.setVm(viewModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
