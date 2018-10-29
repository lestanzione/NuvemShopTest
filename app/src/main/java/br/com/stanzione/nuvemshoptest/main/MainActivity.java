package br.com.stanzione.nuvemshoptest.main;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.stanzione.nuvemshoptest.R;
import br.com.stanzione.nuvemshoptest.catlist.CatListFragment;
import br.com.stanzione.nuvemshoptest.doglist.DogListFragment;
import br.com.stanzione.nuvemshoptest.main.adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUi();
    }

    private void setupUi(){
        ButterKnife.bind(this);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addOption(new DogListFragment(), getResources().getString(R.string.option_dog));
        adapter.addOption(new CatListFragment(), getResources().getString(R.string.option_cat));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager, true);
    }
}
