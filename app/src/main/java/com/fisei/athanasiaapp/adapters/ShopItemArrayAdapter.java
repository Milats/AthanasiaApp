package com.fisei.athanasiaapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fisei.athanasiaapp.R;
import com.fisei.athanasiaapp.objects.ShopCartItem;
import com.fisei.athanasiaapp.services.ImageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopItemArrayAdapter extends ArrayAdapter<ShopCartItem> {

    private static class ViewHolder{
        ImageView shopCartItemImage;
        TextView shopCartItemName;
        TextView shopCartItemUnitPrice;
        TextView shopCartItemQty;
        Button shopCartItemPlusQty;
        Button shopCartItemMinusQty;
    }
    private Map<String, Bitmap> bitmaps = new HashMap<>();
    public ShopItemArrayAdapter(@NonNull Context context, List<ShopCartItem> itemList) {
        super(context, -1, itemList);
    }
    public View getView(int positon, View convertView, ViewGroup parent){
        ShopCartItem item = getItem(positon);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_shop_cart, parent, false);
            viewHolder.shopCartItemImage = (ImageView) convertView.findViewById(R.id.shopItemImageView);
            viewHolder.shopCartItemName = (TextView) convertView.findViewById(R.id.shopItemNameTextView);
            viewHolder.shopCartItemUnitPrice = (TextView) convertView.findViewById(R.id.shopItemQtyTextView);
            viewHolder.shopCartItemMinusQty = (Button) convertView.findViewById(R.id.btnMinusQty);
            viewHolder.shopCartItemPlusQty = (Button) convertView.findViewById(R.id.btnPlusQty);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(bitmaps.containsKey(item.ImageURL)){
            viewHolder.shopCartItemImage.setImageBitmap(
                    bitmaps.get(item.ImageURL));
        } else {
            new LoadImageTask(viewHolder.shopCartItemImage).execute(item.ImageURL);
        }
        viewHolder.shopCartItemName.setText(item.Name);
        viewHolder.shopCartItemUnitPrice.setText(String.format("%s", item.UnitPrice));
        viewHolder.shopCartItemQty.setText("1");
        return convertView;
    }
    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;
        public LoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... params){
            Bitmap bitmap = ImageService.GetImageByURL(params[0]);
            bitmaps.put(params[0], bitmap);
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
        }
    }
}