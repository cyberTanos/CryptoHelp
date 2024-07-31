package com.tanya.finhelp.screens.coinInfo

import android.content.Context.WINDOW_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition.INSIDE_CHART
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tanya.finhelp.R
import com.tanya.finhelp.databinding.BottomSheetCoinInfoBinding
import com.tanya.finhelp.domain.Coin
import com.tanya.finhelp.screens.coins.COIN_KEY
import com.tanya.finhelp.util.LABEL_COUNT_GRAPH
import dagger.hilt.android.AndroidEntryPoint

private const val TOP_INDENTATION = 250
private const val CIRCLE_HOLE_RADIUS = 3f
private const val FILL_ALPHA = 25
private const val LINE_WIDTH = 1f
private const val LINE_CHART_TEXT_SIZE = 12f

@AndroidEntryPoint
class CoinInfoBottomSheet : BottomSheetDialogFragment(R.layout.bottom_sheet_coin_info) {

    private var _binding: BottomSheetCoinInfoBinding? = null
    private val binding get() = _binding!!
    private val vm: CoinInfoVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetCoinInfoBinding.inflate(inflater, container, false)

        bindUI()

        observeVM()
        setupLineChart()

        return binding.root
    }

    private fun bindUI() = with(binding) {
        arguments?.getParcelable<Coin>(COIN_KEY).let { coin ->
            Glide.with(root).load(coin?.image).circleCrop().into(logoImage)
            coin?.id?.let { vm.getHistoryCoin(it) }
            symbolText.text = coin?.symbol
            nameCompanyText.text = coin?.name
            priceText.text = coin?.price
            coin?.priceChange?.let { priceChangeText.setTextColor(it.color) }
            priceChangeText.text = coin?.priceChange?.value
            coin?.priceChangePercentage?.let { priceChangePercentageText.setTextColor(it.color) }
            priceChangePercentageText.text = coin?.priceChangePercentage?.value
        }
    }

    private fun observeVM() {
        vm.state.observe(viewLifecycleOwner) { points ->
            val entries = points.mapIndexed { index, coinInfo ->
                Entry(index.toFloat(), coinInfo.price.toFloat())
            }
            val lineDataSet = LineDataSet(entries, "DAYS").apply {
                color = Color.BLUE
                valueTextColor = Color.BLACK
                setDrawValues(false)
                setDrawCircles(true)
                circleHoleRadius = CIRCLE_HOLE_RADIUS
                fillAlpha = FILL_ALPHA
                lineWidth = LINE_WIDTH
                setDrawFilled(true)
                highLightColor = Color.GRAY
                setDrawHorizontalHighlightIndicator(false)
                val color: Int = binding.priceChangeText.currentTextColor
                setColor(color)
                setCircleColor(color)
                setFillColor(color)
            }

            val dataSet = ArrayList<ILineDataSet>()
            dataSet.add(lineDataSet)

            val lineData = LineData(dataSet)
            binding.lineChart.data = lineData

            binding.lineChart.invalidate()
        }
    }

    private fun setupLineChart() {
        with(binding.lineChart) {
            setTouchEnabled(true)
            setPinchZoom(true)
            description.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.setDrawGridLines(true)
            axisLeft.setDrawGridLines(false)
            axisLeft.setDrawAxisLine(false)
            axisRight.isEnabled = false
            axisLeft.isEnabled = true
            axisLeft.setPosition(INSIDE_CHART)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            xAxis.setDrawAxisLine(false)
            xAxis.setLabelCount(LABEL_COUNT_GRAPH)
            axisLeft.textSize = LINE_CHART_TEXT_SIZE
            xAxis.textSize = LINE_CHART_TEXT_SIZE
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            val displayMetrics = DisplayMetrics()
            val windowsManager = requireContext().getSystemService(WINDOW_SERVICE) as WindowManager
            windowsManager.defaultDisplay.getMetrics(displayMetrics)
            val bottomSheet = it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = displayMetrics.heightPixels - TOP_INDENTATION
        }
        val behavior = BottomSheetBehavior.from(view?.parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
