package cu.fiai.uci.somosuci.views.viewpagerindicator

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.viewpager.widget.ViewPager
import cu.fiai.uci.somosuci.R
import java.util.*

class ViewPagerIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {
    private var mItemColor = Color.WHITE
    private var mItemSelectedColor = Color.WHITE
    private var mPageCount = 0
    private var mSelectedIndex = 0
    private var mItemScale = NO_SCALE
    private var mItemSize = DEF_VALUE
    private var mDelimiterSize = DEF_VALUE
    private var mItemIcon = DEF_VALUE
    private val mIndexImages: MutableList<ImageView> =
        ArrayList()
    private var mListener: ViewPager.OnPageChangeListener? = null
    private fun createEditModeLayout() {
        for (i in 0..4) {
            val boxedItem = createBoxedItem(i)
            addView(boxedItem)
            if (i == 1) {
                val item = boxedItem.getChildAt(0)
                val layoutParams = item.layoutParams
                layoutParams.height *= mItemScale.toInt()
                layoutParams.width *= mItemScale.toInt()
                item.layoutParams = layoutParams
            }
        }
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        setPageCount(viewPager.adapter!!.count)
        viewPager.addOnPageChangeListener(OnPageChangeListener())
    }

    fun addOnPageChangeListener(listener: ViewPager.OnPageChangeListener?) {
        mListener = listener
    }

    private fun setSelectedIndex(selectedIndex: Int) {
        if (selectedIndex < 0 || selectedIndex > mPageCount - 1) {
            return
        }
        val unselectedView = mIndexImages[mSelectedIndex]
        unselectedView.animate().scaleX(NO_SCALE)
            .scaleY(NO_SCALE).setDuration(300).start()
        unselectedView.setColorFilter(mItemColor, PorterDuff.Mode.SRC_IN)
        val selectedView = mIndexImages[selectedIndex]
        selectedView.animate().scaleX(mItemScale).scaleY(mItemScale).setDuration(300).start()
        selectedView.setColorFilter(mItemSelectedColor, PorterDuff.Mode.SRC_IN)
        mSelectedIndex = selectedIndex
    }

    private fun setPageCount(pageCount: Int) {
        mPageCount = pageCount
        mSelectedIndex = 0
        removeAllViews()
        mIndexImages.clear()
        for (i in 0 until pageCount) {
            addView(createBoxedItem(i))
        }
        setSelectedIndex(mSelectedIndex)
    }

    private fun createBoxedItem(position: Int): FrameLayout {
        val box = FrameLayout(context)
        val item = createItem()
        box.addView(item)
        mIndexImages.add(item)
        val boxParams = LayoutParams(
            (mItemSize * mItemScale).toInt(),
            (mItemSize * mItemScale).toInt()
        )
        if (position > 0) {
            boxParams.setMargins(mDelimiterSize, 0, 0, 0)
        }
        box.layoutParams = boxParams
        return box
    }

    private fun createItem(): ImageView {
        val index = ImageView(context)
        val indexParams = FrameLayout.LayoutParams(mItemSize, mItemSize)
        indexParams.gravity = Gravity.CENTER
        index.layoutParams = indexParams
        index.setImageResource(mItemIcon)
        index.scaleType = ImageView.ScaleType.FIT_CENTER
        index.setColorFilter(mItemColor, PorterDuff.Mode.SRC_IN)
        return index
    }

    private inner class OnPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            if (mListener != null) {
                mListener!!.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        }

        override fun onPageSelected(position: Int) {
            setSelectedIndex(position)
            if (mListener != null) {
                mListener!!.onPageSelected(position)
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (mListener != null) {
                mListener!!.onPageScrollStateChanged(state)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState())
        bundle.putInt(STATE_INDEX, mSelectedIndex)
        return bundle
    }

    override fun onRestoreInstanceState(parcel: Parcelable) {
        val bundle = parcel as Bundle
        super.onRestoreInstanceState(bundle.getParcelable(STATE_SUPER))
        setSelectedIndex(bundle.getInt(STATE_INDEX))
    }

    companion object {
        private const val STATE_SUPER = "STATE_SUPER"
        private const val STATE_INDEX = "STATE_INDEX"
        private const val NO_SCALE = 1f
        private const val DEF_VALUE = 10
        private const val DEF_ICON = R.drawable.ic_fig_indicator_circle
    }

    init {
        orientation = HORIZONTAL
        val attributes =
            context.theme.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator, 0, 0)
        try {
            mItemSize = attributes.getDimensionPixelSize(
                R.styleable.ViewPagerIndicator_itemSize,
                DEF_VALUE
            )
            mItemScale = attributes.getFloat(
                R.styleable.ViewPagerIndicator_itemScale,
                NO_SCALE
            )
            mItemSelectedColor = attributes.getColor(
                R.styleable.ViewPagerIndicator_itemSelectedTint,
                Color.WHITE
            )
            mItemColor = attributes.getColor(
                R.styleable.ViewPagerIndicator_itemTint,
                Color.WHITE
            )
            mDelimiterSize = attributes.getDimensionPixelSize(
                R.styleable.ViewPagerIndicator_delimiterSize,
                DEF_VALUE
            )
            mItemIcon = attributes.getResourceId(
                R.styleable.ViewPagerIndicator_itemIcon,
                DEF_ICON
            )
        } finally {
            attributes.recycle()
        }
        if (isInEditMode) {
            createEditModeLayout()
        }
    }
}