    package com.example.interim;

    import androidx.fragment.app.FragmentStatePagerAdapter;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.viewpager.widget.ViewPager;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.MenuItem;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import java.util.ArrayList;
    import java.util.List;

    public class ActivityNavigation extends AppCompatActivity {

        BottomNavigationView bottomNavigationView;
        private ViewPager viewPager;
        Acceuil_Utilisateur_chercheOffres homeFragment;
        Offres_Utilisateur dashBoardFragment;
        Profile_Utilisateur notificationFragment;
        MenuItem prevMenuItem;
        private static final int MENU_ITEM_SCHEDULE = R.id.menu_item_schedule;
        private static final int MENU_ITEM_CANDIDACY = R.id.menu_item_candidacy;
        private static final int MENU_ITEM_OFFERS = R.id.menu_item_offers;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_navigation);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            if (item.getItemId() == MENU_ITEM_SCHEDULE) {
                                viewPager.setCurrentItem(0);
                            } else if (item.getItemId() == MENU_ITEM_CANDIDACY) {
                                viewPager.setCurrentItem(1);
                            } else if (item.getItemId() == MENU_ITEM_OFFERS) {
                                viewPager.setCurrentItem(2);
                            }
                            return false;
                        }
                    });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                @Override
                public void onPageSelected(int position) {
                    if (prevMenuItem != null) {
                        prevMenuItem.setChecked(false);
                    } else {
                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
                    }
                    Log.d("page", "" + position);
                    bottomNavigationView.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = bottomNavigationView.getMenu().getItem(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {}
            });

            setupViewPager(viewPager);
        }

        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            homeFragment = new Acceuil_Utilisateur_chercheOffres();
            dashBoardFragment = new Offres_Utilisateur();
            notificationFragment = new Profile_Utilisateur();

            adapter.addFragment(homeFragment);
            adapter.addFragment(dashBoardFragment);
            adapter.addFragment(notificationFragment);

            viewPager.setAdapter(adapter);
        }

        private class ViewPagerAdapter extends FragmentStatePagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            }

            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment) {
                mFragmentList.add(fragment);
            }
        }
    }
