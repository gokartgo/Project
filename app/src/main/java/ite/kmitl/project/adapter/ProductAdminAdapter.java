package ite.kmitl.project.adapter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import ite.kmitl.project.R;
import ite.kmitl.project.dao.ProductItemDao;
import ite.kmitl.project.fragment.ShelveManageFragment;
import ite.kmitl.project.holder.ProductViewHolder;

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private List<ProductItemDao> productDataModelList;
    public ProductAdminAdapter(List<ProductItemDao> result) {
        this.productDataModelList = result;
    }

    // select custom view group
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.content_product,parent,false));
    }

    // setup view in this function
    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        ProductItemDao productItemDao = productDataModelList.get(position);
        holder.tvName.setText(productItemDao.getName());
        holder.tvAmount.setText(productItemDao.getAmount()+"");
        // if value in product = 0 set image null
        if(Integer.parseInt(productItemDao.getAmount()+"") <= 0 || productItemDao.getImage().toString().equals("")){
            holder.ivProduct.setImageResource(R.color.transparent);
        } else {
            Picasso.get().load(productItemDao.getImage().toString()).into(holder.ivProduct);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, ShelveManageFragment.newInstance(position+1))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productDataModelList.size();
    }

}
