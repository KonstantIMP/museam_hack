package org.kimp.witelokk.museum.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.kimp.witelokk.museum.app.R
import org.kimp.witelokk.museum.app.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater)

        binding.mfMapBtn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_menuFragment_to_mapFragment)
        }

        binding.mfStartBtn.setOnClickListener {
            findNavController()
                .navigate(R.id.action_menuFragment_to_questFragment)
        }

        return binding.root
    }
}
