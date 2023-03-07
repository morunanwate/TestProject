package com.example.testproject.ui.image_list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testproject.R
import com.example.testproject.databinding.ActivityImageListBinding
import com.example.testproject.ui.common.util.UIEvent
import com.example.testproject.ui.image_detail.ImageDetailActivity
import com.example.testproject.util.IntentKeyConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageListActivity : AppCompatActivity() {

    private val imageListViewModel by viewModels<ImageListViewModel>()

    private lateinit var binding: ActivityImageListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_list)
        setContentView(binding.root)

        binding.adapter = ImageDataAdapter(listOf(), imageListViewModel)
        binding.viewModel = imageListViewModel
        binding.lifecycleOwner = this


        imageListViewModel.uiEvent.observe(this) { uiEvent ->
            if (uiEvent is UIEvent.ImageClicked) {
                Toast.makeText(this@ImageListActivity, "Clicked", Toast.LENGTH_LONG).show()
                val intent = Intent(this@ImageListActivity, ImageDetailActivity::class.java)
                intent.putExtra(IntentKeyConstants.IMAGE_DETAIL_IMAGE_DATA_KAY, uiEvent.imageData)
                startActivity(intent)
            }
        }
    }
}