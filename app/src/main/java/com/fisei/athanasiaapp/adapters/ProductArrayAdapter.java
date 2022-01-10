package com.fisei.athanasiaapp.adapters;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fisei.athanasiaapp.R;
import com.fisei.athanasiaapp.objects.Product;

public class ProductArrayAdapter extends ArrayAdapter<Product> {

    /*Define los atributos de las vistas de los productos.
        Un ImageView para la imagen del producto y TextViews para campos
        relevantes a mostrar como el nombre, el género y el precio.
        La cantidad no se muestra directamente sino que en detalles de producto.
        */
    private static class ViewHolder{
        ImageView productImageView;
        TextView productNameView;
        TextView productGenreView;
        TextView productUnitPriceView;
    }
    //Renderizar la imagen.
    private Map<String, Bitmap> bitmaps = new HashMap<>();
    //Constructor
    public ProductArrayAdapter(Context context, List<Product> productsList){
        super(context, -1, productsList);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Product product = getItem(position);
        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_product, parent, false);
            viewHolder.productImageView =
                    (ImageView) convertView.findViewById(R.id.productImageView);
            viewHolder.productNameView =
                    (TextView) convertView.findViewById(R.id.productNameTextView);
            viewHolder.productGenreView =
                    (TextView) convertView.findViewById(R.id.productGenreTextView);
            viewHolder.productUnitPriceView =
                    (TextView) convertView.findViewById(R.id.productUnitPriceTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(bitmaps.containsKey(product.iconURL)){
            viewHolder.productImageView.setImageBitmap(
                    bitmaps.get(product.iconURL));
        } else {
            new LoadImageTask(viewHolder.productImageView).execute(product.iconURL);
        }
        //Context context = getContext();
        viewHolder.productNameView.setText(product.name);
        viewHolder.productGenreView.setText(product.genre);
        viewHolder.productUnitPriceView.setText(String.format("%s", product.unitPrice));
        return convertView;
    }
    /*TODO: Este método ha sido copiado y pegado tal cual para que los métodos anteriores
    *  no muestren error por lo que es improtante revisarlo y adaptarlo*/
    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        public LoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... params){
            Bitmap bitmap = null;
            HttpURLConnection connection = null;
            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                try(InputStream inputStream = connection.getInputStream()){
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.put(params[0], bitmap);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            } catch (Exception ex){
                ex.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            imageView.setImageBitmap(bitmap);
        }
    }
}