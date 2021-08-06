package vn.poly.goodfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.poly.goodfood.Adapter.SlideAdapter1;
import vn.poly.goodfood.Adapter.SlideAdapter2;
import vn.poly.goodfood.Slide.SlideItem1;
import vn.poly.goodfood.Slide.SlideItem2;
import vn.poly.goodfood.dao.UserDao;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ActivityGiaoDienChinh extends AppCompatActivity {
    Intent intent;
    String use="";
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ViewPager2 viewPager2,viewPager3;
    private Handler sliderHandler = new Handler();
    ImageView img1,img2,img3,img4,img5,img6,img7,img8;
    TextView txtSeemore1,txtSeemore2,textUser,txt1;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giao_dien_chinh);
        //ÁNH XẠ
//        Toast.makeText(this, userDao.password(), Toast.LENGTH_SHORT).show();
        toolbar = findViewById(R.id.toolbar_GiaoDienChinh);
        drawerLayout = findViewById(R.id.drawer_GiaoDienChinh);
        navigationView = findViewById(R.id.navigation_GiaoDienChinh);
        viewPager2 = findViewById(R.id.slide1);
        viewPager3 = findViewById(R.id.slide2);
        txtSeemore1 = findViewById(R.id.txt_seemore1);
        txtSeemore2 = findViewById(R.id.txt_seemore2);
        img1 = findViewById(R.id.image_monChinh);
        img2 = findViewById(R.id.image_doUong);
        img3 = findViewById(R.id.image_kfc);
        img4 = findViewById(R.id.image_combo);
        img5 = findViewById(R.id.image_monPhu);
        img6 = findViewById(R.id.image_anVat);
        img7 = findViewById(R.id.image_khaiVi);
        img8 = findViewById(R.id.image_healthy);
        txt1 = findViewById(R.id.txt_1);
        toolbar.setTitle("");
        //CHECK LOGIN
        if (checkLoginRemember()<0){
            intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        //SET TEXT
        View heder = navigationView.getHeaderView(0);
        textUser =(TextView)heder.findViewById(R.id.text_user);
        textUser.setText(use);

        ActionBar();
        RunSlider();
        RunSlider2();
        IntenLayout();
        RunNavigation();
        NhungFont();
    }

    public int checkLoginRemember(){
        SharedPreferences sharedPreferences = getSharedPreferences("Use_File.txt",MODE_PRIVATE);
        boolean chk = sharedPreferences.getBoolean("REMEMBER",false);
        if (chk){
            use = sharedPreferences.getString("USENAME","");
            return 1;
        }
        return -1;
    }

//    public void Back(View view) {
//        intent = new Intent(getApplicationContext(),MainActivity.class);
//        startActivity(intent);
//    }

    public void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    public void RunSlider(){
        List<SlideItem1> slideItem1List = new ArrayList<>();
        slideItem1List.add(new SlideItem1(R.drawable.images1,"Cơm tấm",20000));
        slideItem1List.add(new SlideItem1(R.drawable.images2,"Trà sữa socola",40000));
        slideItem1List.add(new SlideItem1(R.drawable.images3,"Pizza 4 loại nấm",160000));
        slideItem1List.add(new SlideItem1(R.drawable.images4,"Gà rán giòn cay",36000));
        slideItem1List.add(new SlideItem1(R.drawable.images5,"Phở trộn gà",30000));
        slideItem1List.add(new SlideItem1(R.drawable.images6,"Sữa chua soài",21000));
        slideItem1List.add(new SlideItem1(R.drawable.images7,"Sữa chua kem",21500));
        viewPager2.setAdapter(new SlideAdapter1(ActivityGiaoDienChinh.this,slideItem1List,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(5));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);
    }

    public void RunSlider2(){
        List<SlideItem2> slideItem2List = new ArrayList<>();
        slideItem2List.add(new SlideItem2(R.drawable.images8,"Sữa chua trái cây",11500));
        slideItem2List.add(new SlideItem2(R.drawable.images9,"Hot Dog",12500));
        slideItem2List.add(new SlideItem2(R.drawable.images10,"Bún nạm",35000));
        slideItem2List.add(new SlideItem2(R.drawable.images11,"Trà sữa chân trâu",40000));
        slideItem2List.add(new SlideItem2(R.drawable.images12,"Khoai tây chiên",30000));
        slideItem2List.add(new SlideItem2(R.drawable.images13,"Trà chanh",25000));
        slideItem2List.add(new SlideItem2(R.drawable.images14,"Trà đào",25000));
        viewPager3.setAdapter(new SlideAdapter2(ActivityGiaoDienChinh.this,slideItem2List,viewPager3));

        viewPager3.setClipToPadding(false);
        viewPager3.setClipChildren(false);
        viewPager3.setOffscreenPageLimit(3);
        viewPager3.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(5));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager3.setPageTransformer(compositePageTransformer);
    }

    public void IntenLayout(){
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityMonChinh.class);
                startActivity(intent);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityDoUong.class);
                startActivity(intent);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityKFC.class);
                startActivity(intent);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityComBo.class);
                startActivity(intent);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityMonPhu.class);
                startActivity(intent);
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityAnVat.class);
                startActivity(intent);
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityKhaiVi.class);
                startActivity(intent);
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityHealthy.class);
                startActivity(intent);
            }
        });

        //INTENT SLIDE
        txtSeemore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityDoAnGiamGia.class);
                startActivity(intent);
            }
        });
        txtSeemore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityDoAnYeuThich.class);
                startActivity(intent);
            }
        });
    }

    private Runnable sliderRunable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem());
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunable,3000);
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_GiaoDienChinh);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void RunNavigation(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.gioHang:
                        Intent intent = new Intent(ActivityGiaoDienChinh.this,ActivityGioHang.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.donHang:
                        Intent intent1 = new Intent(ActivityGiaoDienChinh.this,ActivityDonHang.class);
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.doiMatKhau:
                        Intent intent3 = new Intent(ActivityGiaoDienChinh.this,ActivityDoiMatKhau.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.dangXuat:
                        Intent intent4 = new Intent(ActivityGiaoDienChinh.this, MainActivity.class);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        startActivity(intent4);
                        remoreKey();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return false;
            }
        });
    }

    public void NhungFont(){
        Typeface fint = Typeface.createFromAsset(getAssets(),"VnClarendon.ttf");
        txt1.setTypeface(fint);
    }

    public void remoreKey(){
        SharedPreferences sharedPreferences = getSharedPreferences("Use_File.txt",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

