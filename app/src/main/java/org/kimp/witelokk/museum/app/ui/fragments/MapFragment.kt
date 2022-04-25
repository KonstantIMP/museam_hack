package org.kimp.witelokk.museum.app.ui.fragments

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davemorrissey.labs.subscaleview.ImageSource
import com.google.android.material.button.MaterialButtonToggleGroup
import org.kimp.witelokk.museum.app.R
import org.kimp.witelokk.museum.app.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater)

        binding.mfMapImageView.setImage(getSelectedFloor(binding.mfFloorsGroup.checkedButtonId))
        binding.mfFloorsGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) binding.mfMapImageView.setImage(getSelectedFloor(checkedId))
        }

        return binding.root
    }

    private fun getSelectedFloor(checkedId : Int) : ImageSource {
        return when (checkedId) {
            binding.mfZeroFloorBtn.id -> ImageSource.bitmap(BitmapFactory.decodeResource(resources, R.drawable.floor_0))
            binding.mfFirstFloorBtn.id -> ImageSource.bitmap(BitmapFactory.decodeResource(resources, R.drawable.floor_1))
            binding.mfSecondFloorBtn.id -> ImageSource.bitmap(BitmapFactory.decodeResource(resources, R.drawable.floor_2))
            else -> ImageSource.bitmap(BitmapFactory.decodeResource(resources, R.drawable.floor_3))
        }
    }
}