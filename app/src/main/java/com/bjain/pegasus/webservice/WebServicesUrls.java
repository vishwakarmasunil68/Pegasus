package com.bjain.pegasus.webservice;

/**
 * Created by sunil on 20-01-2017.
 */

public class WebServicesUrls {


    public static final String BASE_URL="http://www.bjain.com/bookstore/media/catalog/product";
    public static final String IMAGE_BASE_URL="https://www.pegasusforkids.com/media/catalog/product";
    public static final String IMAGE_SLIDER_URL="http://www.bjain.com/pegasus_app/getslider.php";

    public static final String LOGIN_API_URL = "http://www.bjain.com/pegasus_app/login.php";
    public static final String NEW_LOGIN_API_URL = "http://www.pegasusforkids.com/api/pgslogin.php";
    public static final String SEARCH_PRODUCT_URL = "http://www.pegasusforkids.com/api/pgsnewsearch.php";
    public static final String SCANNER_PRODUCT_URL = "http://www.pegasusforkids.com/api/pgsscanner.php";
    public static final String ORDER_LIST_URL = "http://www.bjain.com/pegasus_app/pgsnewoderlist.php";
    public static final String ORDER_DETAIL_API = "http://www.pegasusforkids.com/api/pgsorderdetail.php";

    public static final String SPECIAL_DEAL_URL ="http://www.bjain.com/pegasus_app/pgs_specialdeal.php";
    public static final String CATEGORY_DATA_URL ="https://www.pegasusforkids.com/api/allcategory.php";

    public static final String  REGISTRATION_URL="http://www.bjain.com/pegasus_app/registration.php";

    public static final String NEW_SINGLE_PRODUCT_VIEW_URL="http://www.pegasusforkids.com/api/pgssingleproviewnew.php";

    public static final String ADD_TO_CART_URL ="http://www.bjain.com/pegasus_app/pgsnewcarttable.php";
//    public static final String NEW_CATEGORY_PRODUCT_URL="https://www.bjain.com/pegasus_app/pgsgetcatwisepro.php";
    public static final String NEW_CATEGORY_PRODUCT_URL="http://www.pegasusforkids.com/api/pgsgetcatwisepro.php";

    public static final String RELATED_PRODUCT_URL="https://www.pegasusforkids.com/api/pgsgetrelatedproduct.php";
    public static final String ADD_TO_WISHLIST_URL="http://www.bjain.com/pegasus_app/pgsaddwishlist.php";
    public static final String DELETE_FROM_CART="http://www.bjain.com/pegasus_app/pgsdeletesinglecartitem.php";
    public static final String DELETE_FROM_WISHLIST="http://www.bjain.com/pegasu's_app/pgsdeletewishlist.php";
    public static final String UPDATE_WISHLIST_ITEM="http://www.bjain.com/pegasus_app/pgsupdatewishlist.php";
    public static final String GET_ALL_CART_DATA_URL="http://www.bjain.com/pegasus_app/pgscartlistdata.php";

    public static final String WISH_LIST_URL="http://www.bjain.com/pegasus_app/pgsgetwishlist.php";
    public static final String GET_ADDRESS_URL="http://www.bjain.com/pegasus_app/pgsgetaddress.php";

    public static final String ADD_ADDRESS_URL="http://www.pegasusforkids.com/api/pgsaddressadd.php";
    public static final String CASH_ON_DELIVERY_API="http://www.bjain.com/pegasus_app/cashondelivery.php";
    public static final String MAIL_API="http://www.pegasusforkids.com/api/pgsmail.php";
    public static final String UPDATE_CART_ITEM="http://www.bjain.com/pegasus_app/pgsupdatecart.php";
    public static final String USER_REVIEW_URL = "http://www.bjain.com/pegasus_app/pgsproreview.php";
    public static final String ADD_USER_REVIEW_URL = "http://www.bjain.com/pegasus_app/pgsproeviewsubmit.php";
    public static final String NEW_ARRIVALS_URL = "https://www.pegasusforkids.com/api/pgsnewarrivals.php";
    public static final String BEST_SELLER_URL = "https://www.pegasusforkids.com/api/pgsbestseller.php";
    public static final String BROWSE_BY_AGE_URL = "http://www.pegasusforkids.com/api/browsebyage.php";
    public static final String GRADE_URL= "http://www.pegasusforkids.com/api/pgsgetgread.php";
    public static final String NEW_GRADE_URL= "https://www.pegasusforkids.com/api/pgsactivitypro.php";
    public static final String CONNECT_WITH_US= "http://www.pegasusforkids.com/api/pgsconnect_with_us.php";
    public static final String FORGOT_PASSWORD_URL= "https://www.pegasusforkids.com/api/forgetpassword.php";
    public static final String BOOKITO_PRICE_URL= "http://www.pegasusforkids.com/api/pgsgetprice.php";
    public static final String BOOKITO_GENDER_URL= "http://www.pegasusforkids.com/api/pgsgetgender.php";
    public static final String BOOKITO_AGE_URL= "http://www.pegasusforkids.com/api/pgsagewisepro.php";
    public static final String UPDATE_PROFILE = "http://www.bjain.com/pegasus_app/pgsupdateprofile.php";

    public static String GetImageUrl(String sku){
        return "https://www.pegasusforkids.com/media/import/"+sku+".jpg";
    }
}
