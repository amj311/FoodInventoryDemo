package com.example.foodinventorydemo.service;

import com.example.foodinventorydemo.model.ProductUnitData;
import com.example.foodinventorydemo.model.ResourceResponseHandler;
import com.example.foodinventorydemo.ui.main.inventory.InventoryFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DummyLookupCodeService extends LookupCodeService {
    private JSONObject fakeResJson = null;
    private List<ProductUnitData> dummyData;

    {
        try {
            String fakeResStr = "{\"success\":true,\"status\":200,\"identifier\":\"022000159335\",\"identifier_type\":\"upc\",\"items\":{\"ean\":\"0022000159335\",\"title\":\"CURIOUSLY STRONG MINTS\",\"description\":\"INGREDIENTS: MADE OF: SUGAR, GUM ARABIC, NATURAL FLAVORS (INCLUDING OIL OF PEPPERMINT), GELATIN.\",\"upc\":\"022000159335\",\"brand\":\"Altoids\",\"mpn\":\"051655\",\"color\":\"Dark Gray\",\"size\":\"can\",\"dimension\":\"2.4 X 3.4 X 3.4 inches\",\"weight\":\"0.05 Kilograms\",\"category\":\"Food, Beverages & Tobacco > Food Items > Candy & Chocolate\",\"currency\":\"\",\"lowest_pricing\":0,\"highest_price\":275,\"images\":[\"https:\\/\\/media.officedepot.com\\/images\\/t_extralarge%2Cf_auto\\/products\\/643660\\/643660_p_altoids_peppermint_mint_tin.jpg\",\"https:\\/\\/i5.walmartimages.com\\/asr\\/7b23e809-1ac7-4dff-8544-5cf38f52e856_1.9e19757343a825b0621180d1b084c43e.jpeg?odnHeight=450&odnWidth=450&odnBg=ffffff\",\"https:\\/\\/pics.drugstore.com\\/prodimg\\/516017\\/450.jpg\",\"http:\\/\\/www.meijer.com\\/assets\\/product_images\\/styles\\/xlarge\\/1001029_022000159335_A_400.jpg\",\"http:\\/\\/c.shld.net\\/rpx\\/i\\/s\\/pi\\/mp\\/3670\\/7862354620?src=http%3A%2F%2Fmedia.mydoitbest.com%2FImageRequest.aspx%3Fsku%3D973450%26size%3D3&d=0d22770c52b0081e75ff45e74fe8b4a0c5befb48\",\"http:\\/\\/c.shld.net\\/rpx\\/i\\/s\\/i\\/spin\\/image\\/spin_prod_ec_767262601\",\"http:\\/\\/ecx.images-amazon.com\\/images\\/I\\/51na%2BetsHUL._SL160_.jpg\",\"https:\\/\\/target.scene7.com\\/is\\/image\\/Target\\/GUEST_ee5e72fb-4e49-44fb-9082-f1ef23cde4e4?wid=1000&hei=1000\",\"http:\\/\\/ct.mywebgrocer.com\\/legacy\\/productimagesroot\\/DJ\\/8\\/519828.jpg\",\"https:\\/\\/images10.newegg.com\\/ProductImageCompressAll200\\/A0TR_1_20120206_1506602.jpg\"],\"pricing\":[{\"seller\":\"Walgreens\",\"website_name\":\"walgreens.com\",\"title\":\"Altoids Mints Peppermint - 1.76 oz\",\"currency\":\"\",\"price\":2.29,\"shipping\":\"US:::5.49 USD\",\"condition\":\"New\",\"link\":\"https:\\/\\/www.walgreens.com\\/store\\/c\\/altoids-mints-peppermint\\/ID=prod6362969-product\",\"date_found\":1599214986},{\"seller\":\"Newegg.com\",\"website_name\":\"newegg.com\",\"title\":\"Liberty Distribution 13201 Altoids (Pack of 12)\",\"ccurrency\":\"\",\"price\":30.18,\"shipping\":\"6.94\",\"condition\":\"New\",\"link\":\"https:\\/\\/www.newegg.com\\/Product\\/Product.aspx?Item=9SIA0SD2MH9808&nm_mc=AFC-C8Junction-MKPL&cm_mmc=AFC-C8Junction-MKPL-_-HW+-+Home+Health+Care-_-Liberty+Distribution-_-9SIA0SD2MH9808\",\"date_found\":1541680781},{\"seller\":\"DollarDays\",\"website_name\":\"dollardays.com\",\"title\":\"Altoids Mints Peppermint\",\"currency\":\"\",\"price\":0.64,\"shipping\":\"\",\"condition\":\"New\",\"link\":\"http:\\/\\/dollardays.com\\/i1334319-wholesale-altoids-peppermint-mints-1-76-ounce-tin.html?pf=cjprod\",\"date_found\":1435972523},{\"seller\":\"Meijer\",\"website_name\":\"meijer.com\",\"title\":\"Altoids Mints - Peppermint - 1 Tin (1.76 oz)\",\"currency\":\"\",\"price\":1.79,\"shipping\":\"Free Shipping\",\"condition\":\"New\",\"link\":\"http:\\/\\/www.meijer.com\\/s\\/altoids-mints-peppermint-1-tin-1-76-oz-\\/_\\/R-117676?cmpid=cacj&CAWELAID=261220745\",\"date_found\":1481163861},{\"seller\":\"Sears\",\"website_name\":\"sears.com\",\"title\":\"Liberty Distribution 13201 Altoids-PEPPERMINT ALTOIDS\",\"currency\":\"\",\"price\":37.66,\"shipping\":\"\",\"condition\":\"New\",\"link\":\"http:\\/\\/www.sears.com\\/shc\\/s\\/p_10153_12605_SPM10064895320\",\"date_found\":1481113697},{\"seller\":\"Kmart\",\"website_name\":\"kmart.com\",\"title\":\"Mints, Peppermint, 1.76 oz (50 g)\",\"currency\":\"\",\"price\":2.29,\"shipping\":\"\",\"condition\":\"New\",\"link\":\"http:\\/\\/www.kmart.com\\/altoids-mints-peppermint-1.76-oz-50-g\\/p-08769207000P\",\"date_found\":1471911211},{\"seller\":\"Office Depot\",\"website_name\":\"officedepot.com\",\"title\":\"Altoids Peppermint Mint Tin, 1.76 Oz Tin\",\"currency\":\"\",\"price\":3.09,\"shipping\":\"US:::9.95 USD\",\"condition\":\"New\",\"link\":\"https:\\/\\/www.officedepot.com\\/a\\/products\\/643660\\/Altoids-Peppermint-Mint-Tin-176-Oz\\/\",\"date_found\":1614257095},{\"seller\":\"Rakuten(Buy.com)\",\"website_name\":\"rakuten.com\",\"title\":\"Altoids 1.76 Oz. Peppermint Mints 13201 Pack of 12\",\"currency\":\"\",\"price\":34.35,\"shipping\":\"\",\"condition\":\"New\",\"link\":\"https:\\/\\/www.rakuten.com\\/shop\\/simsupply\\/product\\/973450\\/?sku=973450&scid=af_feed\",\"date_found\":1598286966},{\"seller\":\"Wal-Mart.com\",\"website_name\":\"walmart.com\",\"title\":\"ALTOIDS Classic Peppermint Breath Mints, 1.76 Ounce Tin\",\"currency\":\"\",\"price\":1.5,\"shipping\":\"5.99\",\"condition\":\"New\",\"link\":\"https:\\/\\/www.walmart.com\\/ip\\/ALTOIDS-Classic-Peppermint-Breath-Mints-1-76-Ounce-Tin\\/38253374&intsrc=CATF_4284\",\"date_found\":1613499804},{\"seller\":\"Amazon Marketplace New\",\"website_name\":\"amazon.com\",\"title\":\"Altoids Mints PACK_12\",\"currency\":\"\",\"price\":49.99,\"shipping\":\"\",\"condition\":\"New\",\"link\":\"https:\\/\\/www.amazon.com\\/gp\\/offer-listing\\/B00EYGN36E\",\"date_found\":1480361079},{\"seller\":\"Target\",\"website_name\":\"target.com\",\"title\":\"Altoids Peppermints 1.7 oz\",\"currency\":\"\",\"price\":1.79,\"shipping\":\"\",\"condition\":\"New\",\"link\":\"https:\\/\\/www.target.com\\/p\\/altoids-peppermint-mint-candies-1-7oz\\/-\\/A-13304773\",\"date_found\":1564715878},{\"seller\":\"Albertsons\",\"website_name\":\"albertsons.com\",\"title\":\"Altoids - Mints - Peppermint 1.76 oz\",\"currency\":\"\",\"price\":0,\"shipping\":\"\",\"condition\":\"New\",\"link\":\"https:\\/\\/www.target.com\\/p\\/altoids-peppermint-mint-candies-1-7oz\\/-\\/A-13304773\",\"date_found\":1484640825},{\"seller\":\"Jet.com\",\"website_name\":\"jet.com\",\"title\":\"Altoids Peppermint Mints, 1.76 oz\",\"currency\":\"\",\"price\":2.85,\"shipping\":\"\",\"condition\":\"New\",\"link\":\"https:\\/\\/jet.com\\/product\\/Altoids-Mints-Peppermint\\/4f588c81c044414c82522deac7c64142\",\"date_found\":1562000830}],\"asin\":\"B00EYGN36E\",\"ebay_id\":\"303737558558\"}}";
            fakeResJson = new JSONObject(fakeResStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dummyData = Arrays.asList(
                super.createProductDataFromResponse(fakeResJson),
                new ProductUnitData(null,"Rice", null, null,null, null,
                        Arrays.asList("https://previews.123rf.com/images/jamakosy/jamakosy1711/jamakosy171100225/90332158-white-rice-in-burlap-sack-bag-isolated-on-white-background.jpg")),
                new ProductUnitData(null,"Noodles", null, null,null, null,
                        Arrays.asList("https://www.dutchmansstore.com/wp-content/uploads/2020/09/DSC_4812-Edit-scaled-e1601073911963.jpg")),
                new ProductUnitData(null,"Dried Apples", null, null,null, null,
                        Arrays.asList("https://nuts.com/images/rackcdn/ed910ae2d60f0d25bcb8-80550f96b5feb12604f4f720bfefb46d.ssl.cf1.rackcdn.com/e8d95ad6e4090391-sXRwtvud-large.jpg")),
                new ProductUnitData(null,"Dried Pears", null, null,null, null,
                        Arrays.asList("https://www.bellaviva.com/assets/images/dried-fruit/pear.jpg")),
                new ProductUnitData(null,"Peanut Butter", null, null,null, null,
                        Arrays.asList("https://www.peanutbutter.com/wp-content/uploads/2019/03/SKIPPY_Product_PB_Spread_Creamy_Peanut_Butter_28oz.png")),
                new ProductUnitData(null,"Bottled Water", null, null,null, null,
                        Arrays.asList("https://images.heb.com/is/image/HEBGrocery/000567987")),
                new ProductUnitData(null,"Dried Pears", null, null,null, null,
                        Arrays.asList("https://www.bellaviva.com/assets/images/dried-fruit/pear.jpg")),
                new ProductUnitData(null,"Toilet Paper", null, null,null, null,
                        Arrays.asList("https://images.homedepot-static.com/productImages/741fbb97-dedc-4571-acf1-9856fe6ec0ea/svn/camco-toilet-paper-40274-64_1000.jpg"))
        );
    }

    @Override
    public void fetchProductData(String c, ResourceResponseHandler<ProductUnitData> handler) {
        Random rand = new Random();
        ProductUnitData data = dummyData.get(rand.nextInt(dummyData.size()));
        handler.handleRes(data);
    }
}
