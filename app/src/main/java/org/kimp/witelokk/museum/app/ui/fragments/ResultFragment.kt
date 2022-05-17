package org.kimp.witelokk.museum.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import org.kimp.witelokk.museum.app.R
import org.kimp.witelokk.museum.app.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(
            layoutInflater
        )

        binding.rfSkipped.text = arguments?.getInt("skip").toString()
        binding.rfSolved.text = arguments?.getInt("total").toString()

        binding.rfBack.setOnClickListener {
            findNavController()
                .navigate(R.id.action_resultFragment_to_menuFragment, Bundle(), NavOptions.Builder()
                    .setLaunchSingleTop(true).build())
        }

        return binding.root
    }
}