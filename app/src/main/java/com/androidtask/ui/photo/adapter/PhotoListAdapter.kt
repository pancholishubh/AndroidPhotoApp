package com.androidtask.ui.photo.adapter

import android.content.Context
import android.view.ViewGroup
import com.androidtask.R
import com.androidtask.data.model.bean.Photo
import com.androidtask.databinding.RowItemPhotoBinding
import com.androidtask.util.BaseRecyclerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class PhotoListAdapter(private var context: Context,
    private var photoList: ArrayList<Photo>
) :
    BaseRecyclerAdapter<RowItemPhotoBinding, Any, PhotoListAdapter.GetStaredViewHolder>() {


    override fun onCreateViewHolder(
        viewDataBinding: RowItemPhotoBinding,
        parent: ViewGroup,
        viewType: Int
    ): GetStaredViewHolder {
        return GetStaredViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: GetStaredViewHolder, position: Int, type: Int) {
        holder.bindToDataVM(holder.bindingVariable, holder.viewModel)

        holder.viewDataBinding.titleTV.text = photoList[position].title
        val photoURl =  "https://farm${photoList[position].farm}.staticflickr.com/${photoList[position].server}/${photoList[position].id}_${photoList[position].secret}_m.jpg"

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .dontTransform()

        Glide.with(context)
            .load(photoURl).apply(options)
            .into(holder.viewDataBinding.imageView)




}

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.row_item_photo
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class GetStaredViewHolder(mViewDataBinding: RowItemPhotoBinding) :
        BaseViewHolder(mViewDataBinding) {


        override val viewModel: Any
            get() = Any()


        override val bindingVariable: Int
            get() = 0

    }

}