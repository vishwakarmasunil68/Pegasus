package com.bjain.pegasus.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.bjain.pegasus.R;
import com.bjain.pegasus.adapter.CustomswipeAdapter;
import com.bjain.pegasus.adapter.GradeCategoryAdapter;
import com.bjain.pegasus.adapter.SpecialListAdapter;
import com.bjain.pegasus.database.DatabaseHelper;
import com.bjain.pegasus.fragment.ActivitiesFragment;
import com.bjain.pegasus.fragment.BookitoFragment;
import com.bjain.pegasus.fragment.BrowseByAgeFragment;
import com.bjain.pegasus.fragment.CartFragment;
import com.bjain.pegasus.fragment.CategoryProductFragment;
import com.bjain.pegasus.fragment.CategoryViewFragment;
import com.bjain.pegasus.fragment.CurriculumFragment;
import com.bjain.pegasus.fragment.GradeFragment;
import com.bjain.pegasus.fragment.NewArrivalsFragment;
import com.bjain.pegasus.fragment.PegasusBetterTommorowFragment;
import com.bjain.pegasus.fragment.ProductFragment;
import com.bjain.pegasus.fragment.ScannerFragment;
import com.bjain.pegasus.fragment.SchoolBooksFragment;
import com.bjain.pegasus.fragment.SchoolOpportunityFragment;
import com.bjain.pegasus.fragment.SearchFragment;
import com.bjain.pegasus.fragment.SpecialDealViewAllFragment;
import com.bjain.pegasus.fragment.WishListFragment;
import com.bjain.pegasus.pojo.cart.CartDataPOJO;
import com.bjain.pegasus.pojo.category.CategoryPOJO;
import com.bjain.pegasus.pojo.category.CategoryResultPOJO;
import com.bjain.pegasus.pojo.categoryproduct.CategoryProductPOJO;
import com.bjain.pegasus.pojo.categoryproduct.CategoryProductResultPOJO;
import com.bjain.pegasus.pojo.newcategory.NewCategoryPOJO;
import com.bjain.pegasus.pojo.newcategory.NewCategoryResultPOJO;
import com.bjain.pegasus.pojo.specialdeal.SpecialDeal;
import com.bjain.pegasus.pojo.specialdeal.SpecialDealDataPOJO;
import com.bjain.pegasus.pojo.specialdeal.SpecialDealResultPOJO;
import com.bjain.pegasus.pojo.wishlist.WishListResultPOJO;
import com.bjain.pegasus.utils.Pref;
import com.bjain.pegasus.utils.StringUtils;
import com.bjain.pegasus.utils.TagUtils;
import com.bjain.pegasus.utils.ToastClass;
import com.bjain.pegasus.webservice.GetWebServices;
import com.bjain.pegasus.webservice.WebServiceBase;
import com.bjain.pegasus.webservice.WebServiceCallBackView;
import com.bjain.pegasus.webservice.WebServicesCallBack;
import com.bjain.pegasus.webservice.WebServicesUrls;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bjain.pegasus.webservice.WebServicesUrls.DELETE_FROM_CART;

public class HomeActivity extends AppCompatActivity implements WebServicesCallBack, WebServiceCallBackView,FloatingActionsMenu.OnFloatingActionsMenuUpdateListener {
    private static final String GET_SPECIAL_DEAL = "get_special_deal";
    private static final String GET_CATEGORY_DATA = "get_category_data";
    private static final String SEARCH_PRODUCT_API = "search_product_api";
    private static final String ADD_TO_CART_API = "add_to_cart_api";
    private static final String CATEGORY_API = "category_api";
    private static final String ADD_TO_WISHLIST = "add_to_wishlist";
    private static final String DELETE_FROM_CART_API = "delete_from_cart";
    private static final String DELETE_FROM_WISHLIST_API = "delete_from_wishlist_api";
    private static final String ADD_TO_CART_API_BUYED = "add_to_cart_buyed";
    private final String TAG = getClass().getSimpleName();
    private final String IMAGE_SLIDER_API_CALL = "image_slider_apicall";
    private final static int BARCODE_SCANNER_RESULT = 102;
    DatabaseHelper databaseHelper;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView nvDrawer;
    private Toolbar toolbar;


    @BindView(R.id.ic_ham)
    ImageView ic_ham;
    @BindView(R.id.view_pager)
    ViewPager view_pager;
    @BindView(R.id.ll_account)
    LinearLayout ll_account;
    @BindView(R.id.rv_special_deal)
    RecyclerView rv_special_deal;
    @BindView(R.id.tv_profile_name_toolbar)
    TextView tv_profile_name_toolbar;
    @BindView(R.id.frame_search_container)
    FrameLayout frame_search_container;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.frame_main)
    FrameLayout frame_main;
    @BindView(R.id.iv_cart)
    ImageView iv_cart;


    @BindView(R.id.iv_readers)
    ImageView iv_readers;
    @BindView(R.id.iv_puzzles)
    ImageView iv_puzzles;
    @BindView(R.id.iv_preschools)
    ImageView iv_preschools;
    @BindView(R.id.iv_pictures)
    ImageView iv_pictures;
    @BindView(R.id.iv_knowledge)
    ImageView iv_knowledge;
    @BindView(R.id.iv_encyclopedias)
    ImageView iv_encyclopedias;
    @BindView(R.id.iv_education)
    ImageView iv_education;
    @BindView(R.id.iv_colouring)
    ImageView iv_colouring;
    @BindView(R.id.iv_activity)
    ImageView iv_activity;
    @BindView(R.id.ic_fav)
    ImageView ic_fav;
    @BindView(R.id.addFabLayoutContainer)
    LinearLayout addFabLayoutContainer;
    @BindView(R.id.addFAB)
    FloatingActionsMenu addFabMenu;
    @BindView(R.id.fab_contact_us)
    FloatingActionButton fab_contact_us;
    @BindView(R.id.iv_bookito)
    ImageView iv_bookito;
    @BindView(R.id.iv_best_seller)
    ImageView iv_best_seller;
    @BindView(R.id.iv_new_arrivals)
    ImageView iv_new_arrivals;
    @BindView(R.id.iv_find_us_near)
    ImageView iv_find_us_near;
    @BindView(R.id.iv_business)
    ImageView iv_business;
    @BindView(R.id.iv_opportunity)
    ImageView iv_opportunity;
    @BindView(R.id.range_bar)
    RangeBar range_bar;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.base_frame)
    FrameLayout base_frame;
    @BindView(R.id.tv_special_view_all)
    TextView tv_special_view_all;
    @BindView(R.id.ll_barcode_scan)
    LinearLayout ll_barcode_scan;


    List<Fragment> activeCenterFragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        settingNavDrawer();
        databaseHelper = new DatabaseHelper(this);

        new GetWebServices(this, IMAGE_SLIDER_API_CALL, true).execute(WebServicesUrls.IMAGE_SLIDER_URL);
        new GetWebServices(this, GET_SPECIAL_DEAL, true).execute(WebServicesUrls.SPECIAL_DEAL_URL);
        ll_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                    startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
            }
        });
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchFragment();
            }
        });
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchFragment();
            }
        });
        iv_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("3");
            }
        });
        iv_readers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("70");
            }
        });
        iv_puzzles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("208");
            }
        });
        iv_preschools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("57");
            }
        });
        iv_pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("43");
            }
        });
        iv_knowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("205");
            }
        });
        ll_barcode_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this, ZxingScannerActivity.class);
//                startActivityForResult(i, BARCODE_SCANNER_RESULT);
                showBarcodeFragment();
            }
        });
        iv_encyclopedias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("206");
            }
        });
        iv_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("26");
            }
        });
        iv_colouring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCategoryProductAPI("207");
            }
        });

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCartFragment();
            }
        });
        ic_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWishListFragment();
            }
        });
        fab_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFabMenu.collapse();
                startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
            }
        });
        addFabMenu.setOnFloatingActionsMenuUpdateListener(this);
        iv_new_arrivals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewArrivalFragment(true);
            }
        });

        iv_best_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewArrivalFragment(false);
            }
        });
        iv_find_us_near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,FindUsNearActivity.class));
            }
        });
        range_bar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex,
                                              String leftPinValue, String rightPinValue) {
//                Log.d(TagUtils.getTag(),"Number1:-"+leftPinValue+" , Number2:-"+rightPinValue);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBrowseByAgeFragment(range_bar.getLeftPinValue(),range_bar.getLeftPinValue(),range_bar.getRightPinValue());
                Log.d(TagUtils.getTag(),"Number1:-"+range_bar.getLeftPinValue()+" , Number2:-"+range_bar.getRightPinValue());
            }
        });
        iv_opportunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSchoolOpportunityFragment();
            }
        });
        iv_bookito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookitoFragment();
            }
        });

        try{
            CategoryPOJO categoryPOJO=(CategoryPOJO) Pref.GetPOJO(getApplicationContext(),StringUtils.CATEGORY_TYPE,StringUtils.CATEGORY_TYPE);
            if(categoryPOJO!=null) {
                loadCategory(categoryPOJO);
            }else{
                new GetWebServices(this, GET_CATEGORY_DATA, true).execute(WebServicesUrls.CATEGORY_DATA_URL);
            }
        }catch (Exception e){
            e.printStackTrace();
            new GetWebServices(this, GET_CATEGORY_DATA, true).execute(WebServicesUrls.CATEGORY_DATA_URL);
        }
//        printHashKey();
    }

    @Override
    public void onMenuExpanded() {
        addFabLayoutContainer.setBackgroundColor(Color.parseColor("#00F0F8FF"));
        addFabLayoutContainer.setClickable(true);
        addFabLayoutContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFabMenu.collapse();
            }
        });
    }

    @Override
    public void onMenuCollapsed() {
        addFabLayoutContainer.setBackgroundColor(Color.TRANSPARENT);
        addFabLayoutContainer.setClickable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Quoto id:-" + Pref.GetStringPref(getApplicationContext(), StringUtils.QUOTOID, ""));
        Log.d(TAG, "Entity id:-" + Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, ""));
        changeLoginMenuName();
        setSymbol();
        printHashKey();
    }

    public void changeLoginMenuName() {
        Menu menu = nvDrawer.getMenu();
        MenuItem nav_login = menu.findItem(R.id.nav_login);
        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
            nav_login.setTitle("Logout");
        } else {
            nav_login.setTitle("Login");
        }
        if (tv_profile_name != null & tv_email != null) {
            tv_profile_name.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, "") +
                    " " + Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, ""));
            tv_email.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, ""));
        }
    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.bjain.pegasus",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    LinearLayout ll_nav_cat, ll_header, ll_nav_cat_level0, ll_categories,nav_ll_book_offering,ll_main_nav,ll_grade_category;
    TextView tv_profile_name, tv_email, tv_profile_symbol;
    RecyclerView rv_nav_category;
    List<String> nav_grade_list=new ArrayList<>();
    GradeCategoryAdapter adapter;
    private void settingNavDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupDrawerContent(nvDrawer);
        View headerLayout = nvDrawer.inflateHeaderView(R.layout.home_nav_header);
        final LinearLayout ll_nav_header = (LinearLayout) headerLayout.findViewById(R.id.ll_nav_header);
        ll_nav_cat = (LinearLayout) headerLayout.findViewById(R.id.nav_cat);
        ll_nav_cat_level0 = (LinearLayout) headerLayout.findViewById(R.id.ll_nav_cat_level0);
        ll_categories = (LinearLayout) headerLayout.findViewById(R.id.ll_categories);
        nav_ll_book_offering = (LinearLayout) headerLayout.findViewById(R.id.nav_ll_book_offering);
        ll_main_nav = (LinearLayout) headerLayout.findViewById(R.id.ll_main_nav);
        ll_header = (LinearLayout) headerLayout.findViewById(R.id.ll_header);
        ll_grade_category = (LinearLayout) headerLayout.findViewById(R.id.ll_grade_category);
        rv_nav_category = (RecyclerView) headerLayout.findViewById(R.id.rv_nav_category);
        tv_profile_symbol = (TextView) headerLayout.findViewById(R.id.tv_profile_symbol);
        tv_email = (TextView) headerLayout.findViewById(R.id.tv_email);
        tv_profile_name = (TextView) headerLayout.findViewById(R.id.tv_profile_name);
        tv_profile_name.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, "") +
                " " + Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, ""));
        tv_email.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, ""));

        setSymbol();
        setupDrawerToggle();
//        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(false);

        nvDrawer.setItemIconTintList(null);
//        setFirstItemNavigationView();
        final LinearLayout ll_nav_cat_home = (LinearLayout) findViewById(R.id.ll_nav_cat);
        ll_nav_cat_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCatNavDrawer();
            }
        });
        ic_ham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCatNvDrawer();
            }
        });

        nav_grade_list.add("ALL");
        nav_grade_list.add("ACTIVITY BOOKS");
        nav_grade_list.add("CHARTS");
        nav_grade_list.add("LIBRARY BOOKS");
        nav_grade_list.add("READERS BOOKS");
        nav_grade_list.add("TEXTBOOK");
        nav_grade_list.add("WORKBOOK");
        adapter = new GradeCategoryAdapter(HomeActivity.this, nav_grade_list);
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        rv_nav_category.setLayoutManager(horizontalLayoutManagaer);
//        rv_nav_category.setHasFixedSize(true);
//        rv_nav_category.setItemAnimator(new DefaultItemAnimator());
//        rv_nav_category.setAdapter(adapter);
//        setGradeCategorySelected(0);

        inflateGradeCategory(nav_grade_list);
    }
    List<LinearLayout> listofGradeCategories=new ArrayList<>();
    List<TextView> listofTextGrade=new ArrayList<>();
    public void inflateGradeCategory(List<String> nav_grade_list){
        ll_grade_category.removeAllViews();
        for (int i = 0; i < nav_grade_list.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.inflate_grade_category, null);

            LinearLayout ll_child_nav_category= (LinearLayout) view.findViewById(R.id.ll_child_nav_category);
            TextView tv_grade_category= (TextView) view.findViewById(R.id.tv_grade_category);

            tv_grade_category.setText(nav_grade_list.get(i));

            final int finalI = i;
            ll_child_nav_category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setGradeCategorySelection(finalI,true);
                }
            });

            listofGradeCategories.add(ll_child_nav_category);
            listofTextGrade.add(tv_grade_category);

            ll_grade_category.addView(view);

        }

        setGradeCategorySelection(0,false);
    }

    public void setGradeCategorySelection(int position,boolean passtofrag){
        try{
            for(int i=0;i<7;i++){
                listofGradeCategories.get(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
                listofTextGrade.get(i).setTextColor(Color.parseColor("#000000"));
            }
            listofGradeCategories.get(position).setBackgroundColor(Color.parseColor("#bf1e2d"));
            listofTextGrade.get(position).setTextColor(Color.parseColor("#FFFFFF"));
            mDrawer.closeDrawers();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(passtofrag){
            try{
                gradeFragment.GetSelectedCategory(position);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    public void openCatNvDrawer() {
        mDrawer.openDrawer(GravityCompat.START);
//        ll_nav_cat.setVisibility(View.GONE);
//        ll_header.setVisibility(View.VISIBLE);
    }

    public void setSymbol() {
        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
            try {
                String s = Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, "");
                char ch = s.charAt(0);
                tv_profile_symbol.setText(String.valueOf(ch));
                tv_profile_name_toolbar.setText(String.valueOf(ch));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tv_profile_symbol.setText("");
            tv_profile_name_toolbar.setText("");
        }

    }

    public void openCatNavDrawer() {
        mDrawer.openDrawer(nvDrawer);
        ll_nav_cat.setVisibility(View.VISIBLE);
        ll_header.setVisibility(View.GONE);
        setMenuVisibilityOFF();
    }
    boolean grade_nav_status=false;
    public void showOpportunityNav(){
        setMenuVisibilityOFF();
        grade_nav_status=true;
        nav_ll_book_offering.setVisibility(View.VISIBLE);
        ll_main_nav.setVisibility(View.GONE);

    }
    public void hideOpptunityNav(){
        setMenuVisibilityON();
        gradeFragment=null;
        grade_nav_status=false;
        nav_ll_book_offering.setVisibility(View.GONE);
        ll_main_nav.setVisibility(View.VISIBLE);
    }

    public void setMenuVisibilityOFF() {
        Menu nav_Menu = nvDrawer.getMenu();
        nav_Menu.findItem(R.id.nav_home).setVisible(false);
        nav_Menu.findItem(R.id.nav_account).setVisible(false);
        nav_Menu.findItem(R.id.nav_wish_list).setVisible(false);
        nav_Menu.findItem(R.id.nav_orders).setVisible(false);
        nav_Menu.findItem(R.id.nav_track_orders).setVisible(false);
        nav_Menu.findItem(R.id.nav_shop_cat).setVisible(false);
        nav_Menu.findItem(R.id.nav_ewallet).setVisible(false);
        nav_Menu.findItem(R.id.nav_customer_service).setVisible(false);
        nav_Menu.findItem(R.id.nav_login).setVisible(false);

    }

    public void setMenuVisibilityON() {
        ll_nav_cat.setVisibility(View.GONE);
        ll_header.setVisibility(View.VISIBLE);
        Menu nav_Menu = nvDrawer.getMenu();
        nav_Menu.findItem(R.id.nav_home).setVisible(true);
        nav_Menu.findItem(R.id.nav_account).setVisible(true);
        nav_Menu.findItem(R.id.nav_wish_list).setVisible(true);
        nav_Menu.findItem(R.id.nav_orders).setVisible(true);
        nav_Menu.findItem(R.id.nav_track_orders).setVisible(true);
        nav_Menu.findItem(R.id.nav_shop_cat).setVisible(true);
        nav_Menu.findItem(R.id.nav_ewallet).setVisible(true);
        nav_Menu.findItem(R.id.nav_customer_service).setVisible(true);
        nav_Menu.findItem(R.id.nav_login).setVisible(true);
    }

    private void setupDrawerToggle() {
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                //Log.d(TAG, "drawer closed");
                if(!grade_nav_status) {
                    setMenuVisibilityON();
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here

            }
        };
// Set the drawer toggle as the DrawerListener
        mDrawer.addDrawerListener(drawerToggle);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                removeActiveCenterFragments();
                break;
            case R.id.nav_account:
                if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                    startActivity(new Intent(HomeActivity.this, AccountActivity.class));
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
                break;
            case R.id.nav_wish_list:
                showWishListFragment();
                break;
            case R.id.nav_orders:
                startActivity(new Intent(HomeActivity.this, OrderListActivity.class));
                break;
            case R.id.nav_track_orders:
                break;
            case R.id.nav_shop_cat:
                showCategoryListFragment();
                break;
            case R.id.nav_ewallet:
                break;
            case R.id.nav_customer_service:
                startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
                break;
            case R.id.nav_login:
                if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false);
                    databaseHelper.deleteAllCartItems();
                    databaseHelper.deleteALLWishListData();
                    Pref.clearSharedPreference(getApplicationContext());
                    changeLoginMenuName();
                    setSymbol();
                    removeActiveCenterFragments();
                } else {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                }
                break;

        }
        mDrawer.closeDrawers();
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case IMAGE_SLIDER_API_CALL:
                parseImageSliderData(response);
                break;
            case GET_SPECIAL_DEAL:
                parseSpecialDealResponse(response);
                break;
            case GET_CATEGORY_DATA:
                parseCategoryData(response);
                break;
            case CATEGORY_API:
                parseCategoryProductData(response);
                break;
        }
    }

    public void parseCategoryProductData(String response) {
        Log.d(TAG, "category product response:-" + response);
//        try {
//            Gson gson = new Gson();
//            CategoryProductPOJO categoryProductPOJO = gson.fromJson(response, CategoryProductPOJO.class);
//            if (categoryProductPOJO.getCategoryProductResultPOJOs().size() > 0) {
//                showCategoryProductsFragment(categoryProductPOJO);
//            } else {
//                ToastClass.showShortToast(getApplicationContext(), "No Books Found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try{
            Gson gson=new Gson();
            NewCategoryPOJO newCategoryPOJO=gson.fromJson(response,NewCategoryPOJO.class);
            if(newCategoryPOJO.getSuccess().equals("true")){
                List<String> stringList=newCategoryPOJO.getNewCategoryResultPOJOList();
                List<NewCategoryResultPOJO> newCategoryResultPOJOs=new ArrayList<>();
                for(String s:stringList){
                    if(!s.equals("false")){
                        Gson gson1=new Gson();
                        NewCategoryResultPOJO newCategoryResultPOJO=gson1.fromJson(s,NewCategoryResultPOJO.class);
                        newCategoryResultPOJOs.add(newCategoryResultPOJO);
                    }
                }

                Set<String> productSet=new HashSet<>();
                for(NewCategoryResultPOJO newCategoryResultPOJO:newCategoryResultPOJOs){
                    productSet.add(newCategoryResultPOJO.getProductId());
                }
                List<CategoryProductResultPOJO> categoryProductResultPOJOList=new ArrayList<>();
                for(String s:productSet){
                    CategoryProductResultPOJO categoryResultPOJO=new CategoryProductResultPOJO();
                    for (NewCategoryResultPOJO newCategoryResultPOJO:newCategoryResultPOJOs){
                        if(s.equals(newCategoryResultPOJO.getProductId())){
                            switch (newCategoryResultPOJO.getAttributeId()){
                                case "71":
                                    categoryResultPOJO.setName(newCategoryResultPOJO.getValue());
                                    categoryResultPOJO.setProductId(s);
                                    categoryResultPOJO.setPrice(newCategoryResultPOJO.getMainPrice());
                                    categoryResultPOJO.setMain_price(newCategoryResultPOJO.getMainPrice());
                                    categoryResultPOJO.setDiscount_price(newCategoryResultPOJO.getDiscountPrice());
                                    break;
                                case "85":
                                    categoryResultPOJO.setUrl(newCategoryResultPOJO.getValue());
                                    break;
                            }
                        }
                    }
                    categoryProductResultPOJOList.add(categoryResultPOJO);
                }

                CategoryProductPOJO categoryProductPOJO=new CategoryProductPOJO();
                categoryProductPOJO.setCategoryProductResultPOJOs(categoryProductResultPOJOList);

                Log.d(TAG,"category products:-"+categoryProductPOJO.toString());
                showCategoryProductsFragment(categoryProductPOJO);

            }else{
                ToastClass.showShortToast(getApplicationContext(),"No Product Found");
            }
        }catch (Exception e){
            ToastClass.showShortToast(getApplicationContext(),"No Product Found");
            e.printStackTrace();
        }
    }

    public void showCategoryProductsFragment(CategoryProductPOJO categoryProductPOJO) {
        CategoryProductFragment categoryProductFragment = new CategoryProductFragment(categoryProductPOJO);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, categoryProductFragment, "categoryFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(categoryProductFragment);
    }


    public void showProductViewFragment(String product_id) {
        ProductFragment productFragment = new ProductFragment(product_id);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, productFragment, "productFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(productFragment);
    }

    public void showBookitoFragment() {
        BookitoFragment bookitoFragment = new BookitoFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, bookitoFragment, "bookitofragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(bookitoFragment);
    }

    public void showCartFragment() {
        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {

            CartFragment cartFragment = new CartFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.frame_main, cartFragment, "cartFragment");
            transaction.addToBackStack(null);
            transaction.commit();
            activeCenterFragments.add(cartFragment);

        } else {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    }

    public void showWishListFragment() {
        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {

            WishListFragment wishListFragment = new WishListFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.frame_main, wishListFragment, "wishListFragment");
            transaction.addToBackStack(null);
            transaction.commit();
            activeCenterFragments.add(wishListFragment);

        } else {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    }

    public void showCategoryListFragment() {
//        if (categoryResultPOJOList != null && categoryResultPOJOList.size() > 0) {
            CategoryViewFragment categoryViewFragment = new CategoryViewFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.frame_main, categoryViewFragment, "categroywishlistfragment");
            transaction.addToBackStack(null);
            transaction.commit();
            activeCenterFragments.add(categoryViewFragment);
//        }
    }


    private void removeActiveCenterFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (activeCenterFragments.size() > 0) {
            transaction = manager.beginTransaction();
            for (Fragment activeFragment : activeCenterFragments) {
                transaction.remove(activeFragment);
            }
            activeCenterFragments.clear();
            transaction.commit();
        }
    }

    public void AddtoCartAPI(String product_id, String sku, String product_name, String weight,
                             String qty, String price, String base_price, View view,boolean is_Buyed) {
        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
            String p_name=product_name;
            try{
                if(p_name.contains("\'")){
//                    p_name="1234";
                    p_name=p_name.replace("\'","");
                }
            }catch (Exception e){

            }
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
            nameValuePairs.add(new BasicNameValuePair("sku", sku));
            nameValuePairs.add(new BasicNameValuePair("name", p_name));
            nameValuePairs.add(new BasicNameValuePair("weight", weight));
            nameValuePairs.add(new BasicNameValuePair("qty", qty));
            nameValuePairs.add(new BasicNameValuePair("price", price));
            nameValuePairs.add(new BasicNameValuePair("base_price", base_price));
            nameValuePairs.add(new BasicNameValuePair("quote_id", Pref.GetStringPref(getApplicationContext(), StringUtils.QUOTOID, "")));
            nameValuePairs.add(new BasicNameValuePair("email", Pref.GetStringPref(getApplicationContext(), StringUtils.EMAIL_ID, "")));
            nameValuePairs.add(new BasicNameValuePair("first_name", Pref.GetStringPref(getApplicationContext(), StringUtils.FIRST_NAME, "")));
            nameValuePairs.add(new BasicNameValuePair("middle_name", Pref.GetStringPref(getApplicationContext(), StringUtils.MIDDLE_NAME, "")));
            nameValuePairs.add(new BasicNameValuePair("last_name", Pref.GetStringPref(getApplicationContext(), StringUtils.LAST_NAME, "")));
            if(is_Buyed){
                new WebServiceBase(nameValuePairs, this, ADD_TO_CART_API_BUYED, view).execute(WebServicesUrls.ADD_TO_CART_URL);
            }else {
                new WebServiceBase(nameValuePairs, this, ADD_TO_CART_API, view).execute(WebServicesUrls.ADD_TO_CART_URL);
            }
        } else {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    }

    public void AddToWishListAPI(String product_id, String description, String qty, View view) {
        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
            nameValuePairs.add(new BasicNameValuePair("description", description));
            nameValuePairs.add(new BasicNameValuePair("qty", qty));
            nameValuePairs.add(new BasicNameValuePair("user_id", Pref.GetStringPref(getApplicationContext(), StringUtils.ENTITY_ID, "")));
            new WebServiceBase(nameValuePairs, this, ADD_TO_WISHLIST, view).execute(WebServicesUrls.ADD_TO_WISHLIST_URL);
        } else {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    }


    public void RemoveFromWishList(String wishlist_item_id, View view) {
        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("wishlist_item_id", wishlist_item_id));
            new WebServiceBase(nameValuePairs, this, DELETE_FROM_WISHLIST_API, view).execute(WebServicesUrls.DELETE_FROM_WISHLIST);
        } else {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    }


    public void showSearchFragment() {
        SearchFragment searchFragment = new SearchFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, searchFragment, "searchfragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(searchFragment);
    }

    public void showSpecialViewAll(List<SpecialDealDataPOJO> specialDealDataPOJOList) {
        SpecialDealViewAllFragment specialDealViewAllFragment = new SpecialDealViewAllFragment(specialDealDataPOJOList);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, specialDealViewAllFragment, "specialViewAllFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(specialDealViewAllFragment);
    }



    public void showNewArrivalFragment(boolean is_new_arrival){
        NewArrivalsFragment newArrivalsFragment = new NewArrivalsFragment(is_new_arrival);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, newArrivalsFragment, "newArrivalsFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(newArrivalsFragment);
    }


    public void showBrowseByAgeFragment(String age,String initialvalue,String finalvalue){
        BrowseByAgeFragment browseByAgeFragment= new BrowseByAgeFragment(age,initialvalue,finalvalue);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, browseByAgeFragment, "browseByAgeFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(browseByAgeFragment);
    }
    public void showSchoolOpportunityFragment(){
        SchoolOpportunityFragment schoolOpportunityFragment= new SchoolOpportunityFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, schoolOpportunityFragment, "schoolOpportunityFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(schoolOpportunityFragment);
    }
    public void showBetterTommorowFragment(){
        PegasusBetterTommorowFragment schoolOpportunityFragment= new PegasusBetterTommorowFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, schoolOpportunityFragment, "schoolOpportunityFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(schoolOpportunityFragment);
    }
    ScannerFragment scannerFragment;
    public void showBarcodeFragment(){
        scannerFragment= new ScannerFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, scannerFragment, "scannerFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(scannerFragment);
    }


    GradeFragment gradeFragment;
    public void showGradeFragment(){
        gradeFragment= new GradeFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, gradeFragment, "gradeFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(gradeFragment);
    }

    public void showSchoolBooksFragment(int index) {
        SchoolBooksFragment schoolBooksFragment= new SchoolBooksFragment(index);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, schoolBooksFragment, "schoolBooksFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(schoolBooksFragment);
    }

    public void showActivityFragment() {
        ActivitiesFragment activitiesFragment= new ActivitiesFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, activitiesFragment, "activitiesFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(activitiesFragment);
    }
    public void showCurriculumFragment() {
        CurriculumFragment curriculumFragment= new CurriculumFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_main, curriculumFragment, "curriculumFragment");
        transaction.addToBackStack(null);
        transaction.commit();
        activeCenterFragments.add(curriculumFragment);
    }

    List<CategoryResultPOJO> categoryResultPOJOList;

    public void parseCategoryData(String response) {
        Log.d(TAG, "parse category response:-" + response);
        try {
            Gson gson = new Gson();
            CategoryPOJO categoryPOJO = gson.fromJson(response, CategoryPOJO.class);
            if (categoryPOJO.getSuccess().equals("true")) {
                CategoryResultPOJO categoryResultPOJO = categoryPOJO.getCategoryResultPOJO().
                        getCategoryResultPOJOList().get(0).getCategoryResultPOJOList().get(0);
                Log.d(TAG, "category result:-" + categoryResultPOJO.toString());
                categoryResultPOJOList = categoryResultPOJO.getCategoryResultPOJOList();
                Pref.SavePOJO(getApplicationContext(),StringUtils.CATEGORY_TYPE,categoryPOJO);
//                Pref.SavePOJO(getApplicationContext(),StringUtils.CATEGORY_TYPE,categoryPOJO);
                inflateCategory(categoryResultPOJO.getCategoryResultPOJOList());
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadCategory(CategoryPOJO categoryPOJO){
        if (categoryPOJO.getSuccess().equals("true")) {
            CategoryResultPOJO categoryResultPOJO = categoryPOJO.getCategoryResultPOJO().
                    getCategoryResultPOJOList().get(0).getCategoryResultPOJOList().get(0);
            Log.d(TAG, "category result:-" + categoryResultPOJO.toString());
            categoryResultPOJOList = categoryResultPOJO.getCategoryResultPOJOList();
            Pref.SavePOJO(getApplicationContext(),StringUtils.CATEGORY_TYPE,categoryPOJO);
//                Pref.SavePOJO(getApplicationContext(),StringUtils.CATEGORY_TYPE,categoryPOJO);
            inflateCategory(categoryResultPOJO.getCategoryResultPOJOList());
        } else {

        }
    }

    public void inflateCategory(List<CategoryResultPOJO> categoryResultPOJOList) {
        if (categoryResultPOJOList.size() > 0) {
            ll_nav_cat_level0.removeAllViews();
            for (int i = 0; i < categoryResultPOJOList.size(); i++) {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.inflate_category, null);

                TextView tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);
                ImageView iv_catvisible = (ImageView) view.findViewById(R.id.iv_catvisible);
                final LinearLayout ll_category_data = (LinearLayout) view.findViewById(R.id.ll_category_data);


                tv_category_name.setText(categoryResultPOJOList.get(i).getName());

                iv_catvisible.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ll_category_data.getVisibility() == View.VISIBLE) {
                            ll_category_data.setVisibility(View.GONE);
                        } else {
                            ll_category_data.setVisibility(View.VISIBLE);
                        }
                    }
                });

                final LinearLayout ll_category = (LinearLayout) view.findViewById(R.id.ll_category);

                ll_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ll_category_data.getVisibility() == View.VISIBLE) {
                            ll_category_data.setVisibility(View.GONE);
                        } else {
                            ll_category_data.setVisibility(View.VISIBLE);
                        }
                    }
                });


                inflateCategoryData(ll_category_data, categoryResultPOJOList.get(i).getCategoryResultPOJOList());

                ll_nav_cat_level0.addView(view);
            }
        }
    }

    public void inflateCategoryData(LinearLayout ll_category_data, final List<CategoryResultPOJO> categoryResultPOJOList) {
        if (categoryResultPOJOList.size() > 0) {
            ll_category_data.removeAllViews();
            for (int i = 0; i < categoryResultPOJOList.size(); i++) {
                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.inflate_category_data, null);
                LinearLayout ll_sub_category = (LinearLayout) view.findViewById(R.id.ll_sub_category);
                TextView tv_category_name = (TextView) view.findViewById(R.id.tv_category_name);

                tv_category_name.setText(categoryResultPOJOList.get(i).getName());
                final int finalI = i;
                ll_sub_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callCategoryProductAPI(categoryResultPOJOList.get(finalI).getCategory_id());
                        mDrawer.closeDrawers();
                    }
                });

                ll_category_data.addView(view);
            }
        }
    }

    public void parseSpecialDealResponse(String response) {
        Log.d(TAG, "special deal:-" + response);
        try {
            Gson gson = new Gson();
            SpecialDeal specialDeal = gson.fromJson(response, SpecialDeal.class);
            List<SpecialDealResultPOJO> specialDealResultPOJOs = new ArrayList<>();
            Set<String> entity_ids = new HashSet<>();
            for (String s : specialDeal.getStringList()) {
                if (!s.equals("false")) {
                    Gson gson1 = new Gson();
                    SpecialDealResultPOJO specialDealResultPOJO = gson1.fromJson(s, SpecialDealResultPOJO.class);
                    specialDealResultPOJOs.add(specialDealResultPOJO);
                    entity_ids.add(specialDealResultPOJO.getEntityId());
                }
            }
            final List<SpecialDealDataPOJO> specialDealDataPOJOList = new ArrayList<>();
            for (String s : entity_ids) {
                SpecialDealDataPOJO specialDealDataPOJO = new SpecialDealDataPOJO();
                for (SpecialDealResultPOJO specialDealResultPOJO : specialDealResultPOJOs) {
                    if (specialDealResultPOJO.getEntityId().equals(s)) {
                        switch (specialDealResultPOJO.getAttributeId()) {
                            case "71":
                                specialDealDataPOJO.setProduct_id(specialDealResultPOJO.getProductId());
                                specialDealDataPOJO.setProduct_name(specialDealResultPOJO.getValue());
                                specialDealDataPOJO.setProduct_price(specialDealResultPOJO.getPrice());
                                specialDealDataPOJO.setProduct_description(specialDealResultPOJO.getDescription());
                                specialDealDataPOJO.setDiscount_price(specialDealResultPOJO.getDiscount_price());
                                specialDealDataPOJO.setMain_price(specialDealResultPOJO.getMain_price());
                                break;
                            case "85":
                                specialDealDataPOJO.setProduct_image(specialDealResultPOJO.getValue());
                                break;
                            case "222":
                                specialDealDataPOJO.setProduct_sku(specialDealResultPOJO.getValue());
                                break;
                        }
                    }
                }
                specialDealDataPOJOList.add(specialDealDataPOJO);
            }

            tv_special_view_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSpecialViewAll(specialDealDataPOJOList);
                }
            });

            //add here
            SpecialListAdapter adapter = new SpecialListAdapter(HomeActivity.this, specialDealDataPOJOList);
            LinearLayoutManager horizontalLayoutManagaer
                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            rv_special_deal.setLayoutManager(horizontalLayoutManagaer);
            rv_special_deal.setHasFixedSize(true);
            rv_special_deal.setItemAnimator(new DefaultItemAnimator());
            rv_special_deal.setAdapter(adapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void parseImageSliderData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String Success = jsonObject.optString("Success");
            if (Success.equals("true")) {
//                JSONArray array = jsonObject.optJSONArray("Result");
//                List<String> list_images = new ArrayList<>();
//                for (int i = 0; i < array.length(); i++) {
//                    JSONObject jsonObject1 = array.optJSONObject(i);
//                    String slider = jsonObject1.optString("slider");
//                    list_images.add(slider);
//                }
//
//                if (list_images.size() > 0) {
//                    setUpViewPager(list_images);
//                }
                JSONArray resultobj=new JSONArray(jsonObject.optJSONArray("Result").getJSONObject(0).optString("images"));
                List<String> stringList=new ArrayList<>();
                for(int i=0;i<resultobj.length();i++){
                    stringList.add("http://www.pegasusforkids.com/media/em_minislideshow/"+resultobj.optJSONObject(i).optString("url"));
                }
                if (stringList.size() > 0) {
                    Log.d(TagUtils.getTag(),"images:-"+stringList.toString());
                    setUpViewPager(stringList);
                }
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setUpViewPager(final List<String> imageIdList) {
        view_pager.setAdapter(new CustomswipeAdapter(this, imageIdList,true));
        view_pager.setOffscreenPageLimit(imageIdList.size());
        if (imageIdList.size() > 0) {
            new CountDownTimer(999999999, 5000) {

                @Override
                public void onTick(long l) {
                    if ((view_pager.getCurrentItem() + 1) == imageIdList.size()) {
                        view_pager.setCurrentItem(0);
                    } else {
                        view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
                    }
                }

                @Override
                public void onFinish() {
                    start();
                }
            }.start();
        }
    }

    @Override
    public void onGetMsg(String[] msg, View view) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case ADD_TO_CART_API:
                parseaddToCartResponse(response, view,false);
                break;
            case ADD_TO_WISHLIST:
                parseAddtoWishlistResponse(response, view);
                break;
            case DELETE_FROM_CART_API:
                parseDeleteFromCartResponse(response, view);
                break;
            case DELETE_FROM_WISHLIST_API:
                parseDeleteFromWishListResponse(response, view);
                break;
            case ADD_TO_CART_API_BUYED:
                parseaddToCartResponse(response,view,true);
                break;
        }
    }

    public void parseDeleteFromWishListResponse(String response, View view) {
        Log.d(TAG, "delete from wishlist response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.optString("success");
            if (success.equals("true")) {
                String wishlist_item_id = jsonObject.optString("wishlist_id");
                databaseHelper.deleteWishListItemByItemId(wishlist_item_id);
                ToastClass.showShortToast(getApplicationContext(), "Item Deleted Successfully");
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Failed to delete Item");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (view instanceof Button) {
            Button btn = (Button) view;
            btn.setText("Add To WishList");
        }
    }

    public void parseDeleteFromCartResponse(String response, View view) {
        Log.d(TAG, "delete From Cart Response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {
                databaseHelper.deleteCartData(jsonObject.optString("cart_id"));
                if (view instanceof Button) {
                    Button btn = (Button) view;
                    btn.setText("Add To Cart");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void parseaddToCartResponse(String response, View view,boolean is_buyed) {
        Log.d(TAG, "parse add to cart response:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.optString("success");
            if (success.equals("true")) {
                ToastClass.showShortToast(getApplicationContext(), "Product added to cart successfully");
                JSONArray resultArray=jsonObject.optJSONArray("result");

                JSONObject result=resultArray.optJSONObject(0);
                CartDataPOJO cartDataPOJO = new CartDataPOJO(result.optString("cart_id"),
                        result.optString("product_id"),
                        result.optString("price"),
                        result.optString("qty"),
                        result.optString("name"),
                        result.optString("sku"),
                        "");
                databaseHelper.insertCartData(cartDataPOJO);
                if (view instanceof Button) {
                    Button btn = (Button) view;
                    btn.setText("Remove From Cart");
                } else {
                    view.setVisibility(View.GONE);
                }


                if(is_buyed){
                    showCartFragment();
                }
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Failed to add to cart");
            }

        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    public void parseAddtoWishlistResponse(String response, View view) {
        Log.d(TAG, "add to wishlist rersponse:-" + response);
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.optString("success");
            if (success.equals("true")) {
                if (view instanceof Button) {
                    Button btn = (Button) view;
                    btn.setText("Remove From WishList");
                }
                ToastClass.showShortToast(getApplicationContext(), "Added to wishlist");

                JSONObject result = jsonObject.optJSONObject("result");
                WishListResultPOJO wishListResultPOJO = new WishListResultPOJO(result.optString("product_id"),
                        "", result.optString("wishlist_item_id"), "", "", result.optString("description"),
                        result.optString("qty"), "", "");

                databaseHelper.insertWishListData(wishListResultPOJO);

            } else {
                ToastClass.showShortToast(getApplicationContext(), "Failed to add to wishlist");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "Something went wrong");
        }
    }

    public void callCategoryProductAPI(String category_id) {
//        parseCategoryProductData(Pref.CATEGORY_ACTITIVTY_DATA);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cat_id", category_id));
        new WebServiceBase(nameValuePairs, this, CATEGORY_API).execute(WebServicesUrls.NEW_CATEGORY_PRODUCT_URL);
    }


    public void callRemoveFromCartAPI(String cart_id, String product_id, View view) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cart_id", cart_id));
        nameValuePairs.add(new BasicNameValuePair("product_id", product_id));
        new WebServiceBase(nameValuePairs, this, DELETE_FROM_CART_API, view).execute(DELETE_FROM_CART);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_SCANNER_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
//                Log.d(TagUtils.getTag(),"scanner result:-"+result);
                try{
                    scannerFragment.onActivityResult(requestCode,resultCode,data);
                }catch (Exception e){
                    e.printStackTrace();
                }
//                if (result.equals("cancel")) {
//                    Toast.makeText(getApplicationContext(), "Scan Cancelled", Toast.LENGTH_LONG).show();
//                } else {
//                    String res = "code:-" + data.getStringExtra("code") + "\n" + "format:-" + data.getStringExtra("format");
//                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
//                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
