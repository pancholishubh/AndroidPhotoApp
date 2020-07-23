package com.androidtask.ui.photo

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidtask.BR
import com.androidtask.R
import com.androidtask.data.model.bean.Photo
import com.androidtask.data.model.response.PhotoListResponse
import com.androidtask.databinding.ActivityPhotoListBinding
import com.androidtask.ui.base.BaseActivity
import com.androidtask.ui.photo.adapter.PhotoListAdapter


class PhotoListActivity : BaseActivity<ActivityPhotoListBinding, PhotoListViewModel>(),
    PhotoListNavigator {

    private lateinit var photoListViewModel: PhotoListViewModel
    private var photoListAdapter: PhotoListAdapter? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    var photoList = ArrayList<Photo>()
    private var isLoading = false
    private var page = 1
    private var searchValue: String? = ""
    var isInternetConnection: Boolean = true

    override val bindingVariable: Int
        get() = BR.photoListVM

    override val layoutId: Int
        get() = R.layout.activity_photo_list

    override val viewModel: PhotoListViewModel
        get() {
            photoListViewModel = ViewModelProvider(this).get(PhotoListViewModel::class.java)
            return photoListViewModel
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        viewModel.init()


    }


    override fun init() {

        initAdapter()

        if (checkIfInternetOn()) {
            isInternetConnection = true
            photoListViewModel.callPhotoListAPI(
                false,
                page.toString(),
                searchValue!!
            )
        }else{
            isInternetConnection = false
        }


        searchValue = viewDataBinding!!.etSearch.text.toString()

        viewDataBinding!!.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (checkIfInternetOn()) {
                    page = 1
                    photoList.clear()
                    searchValue = s.toString()
                    viewModel.callPhotoListAPI(
                        false,
                        page.toString(),
                        searchValue!!

                    )

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


    private fun initAdapter() {
        photoList = ArrayList()
        linearLayoutManager = LinearLayoutManager(this)
        viewDataBinding!!.rvPhoto.layoutManager = linearLayoutManager
        photoListAdapter = PhotoListAdapter(this, photoList)
        viewDataBinding!!.rvPhoto.adapter = photoListAdapter

        viewDataBinding!!.rvPhoto.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManager!!.childCount
                val totalItemCount = linearLayoutManager!!.itemCount
                val firstVisibleItemPosition = linearLayoutManager!!.findFirstVisibleItemPosition()

                if (!isLoading) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadMoreItems()
                    }
                }

            }
        })
    }

    override fun clickOnBackButton() {
        onBackPressed()
    }

    fun loadMoreItems() {
        if (checkIfInternetOn()) {
            isLoading = true
            page += 1
            photoListViewModel.callPhotoListAPI(
                true,
                page.toString(),
                searchValue!!
            )
        }
    }

    override fun showPageLoader() {
        viewDataBinding!!.progressBar.visibility = View.VISIBLE
    }

    override fun showHideLoader() {
        viewDataBinding!!.progressBar.visibility = View.GONE
    }

    override fun getPhotoListResponse(photoListResponse: PhotoListResponse) {
        photoList.addAll(photoListResponse.photos!!.photo)
        photoListAdapter!!.notifyDataSetChanged()

        if (photoList.isNotEmpty()) {
            viewDataBinding!!.tvNoRecord.visibility = View.GONE
            viewDataBinding!!.rvPhoto.visibility = View.VISIBLE
        } else {
            viewDataBinding!!.tvNoRecord.visibility = View.VISIBLE
            viewDataBinding!!.rvPhoto.visibility = View.GONE
        }

        isLoading = false
        if (page == photoListResponse.photos!!.total.toInt()) {
            isLoading = true
        }
    }

    override fun showerrormessage(photoListResponse: PhotoListResponse) {
        Toast.makeText(this, photoListResponse.message, Toast.LENGTH_SHORT).show()
    }
}